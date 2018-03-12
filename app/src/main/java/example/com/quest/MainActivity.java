package example.com.quest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import example.com.quest.DatabaseHelpers.SQLiteHelper;
import example.com.quest.Fragments.DatePickerFragment;
import example.com.quest.Fragments.HomeScreenFragment;
import example.com.quest.Fragments.MainQuestFragment;
import example.com.quest.Fragments.RootFragment;
import example.com.quest.Model.MainQuestObject;
import example.com.quest.Model.SideQuestObject;
import example.com.quest.Model.UserProfileObject;
import example.com.quest.Singleton.Singleton;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.OnFragmentInteractionListener {
    SQLiteHelper helper;


    ImageView avatarHead;
    UserProfileObject userProfileObject;
    TextView avatarClass, playerName, playerLevel, tillNextLevel;
    ProgressBar levelProgressBar;
    int levelUp, level, points;
    Boolean islevelup;
    private static final String DIALOG_DATE = "MainActivity.DateDialog";
    //private TabLayout tabLayout;
    Button button;


    public MainActivity() {
    }


    public void updateMainQuestDate(int id, String date){
        helper.updateMainQuestDate(id, date);
    }

    public void setLevelUpSnackbar(){
        View parentLayout = findViewById(android.R.id.content);

        View mRoot = (RelativeLayout) findViewById(R.id.main_content);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(" ").append(" ");
        builder.setSpan(new ImageSpan(Singleton.getInstance().getContext(), R.drawable.ic_king), builder.length() - 1, builder.length(), 0);
        builder.append("You've leveled up!");
        final Snackbar snackbar = Snackbar.make(mRoot, builder, Snackbar.LENGTH_LONG);
        snackbar.show();
        islevelup = false;
    }

    public void setMissionComplete() {

        points = points + 1;
        if(points == levelUp){
            level = level + 1;
            points = 0;
            levelUp = level * 2;
            islevelup = true;

            //Other stuff in OnCreate();
        }

        helper.saveLevel(level, "Justin", levelUp, points);
        userProfileObject.setUserLevel(level);
        userProfileObject.setUserLevelUp(levelUp);
        userProfileObject.setPoints(points);
        setUserCard(userProfileObject);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater =
                (LayoutInflater) Singleton.getInstance().getContext().getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);

        View missionCompleteDialog = inflater.inflate(R.layout.mission_complete_dialog, null);
        builder.setView(missionCompleteDialog);
        builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                changeQuestFragment(new MainQuestFragment());

                if(islevelup){
                    setLevelUpSnackbar();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void changeQuestFragment(Fragment frag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.root_frame, frag);
        fragmentTransaction.commit();
    }

    public int getLevelUp(){
        return helper.getUserProfile().getUserLevelUp();
    }
    public int getLevel(){
        return helper.getUserProfile().getUserLevel();
    }
    public void setBackButton(){
        changeQuestFragment(new MainQuestFragment());
    }
    public void saveUserProfile(UserProfileObject userProfileObject){
        helper.saveLevel(userProfileObject.getUserLevel(), "Justin", userProfileObject.getUserLevel(), userProfileObject.getPoints());
    }
    public void setHelper(){
        helper = new SQLiteHelper(this);
    }
    public void saveQuest(MainQuestObject mainObject){
        setHelper();
        helper.addMainQuest(mainObject);
    }

    public void loadMainQuests(Boolean bool){
        setHelper();
        helper = new SQLiteHelper(this);
        Singleton.getInstance().setMainQuestList(
                helper.getAllMainQuests(bool, "Justin")
        );
    }
    //SQLite Acess to SideQuests
    public void saveSideQuest(SideQuestObject sideQuestObject){
        helper.addSideQuest(sideQuestObject);
    }

    public double getPercentage(MainQuestObject mainQuest){
        double fractionComplete = (double) mainQuest.getCompletedSideQuests()/(double)
                helper.getAllSideQuests(Integer.toString(mainQuest.get_id())).size();
        double percentageComplete = (double)fractionComplete * 100;
        return percentageComplete;
    }
    public void updateMainQuestSize(MainQuestObject mainQuestObject, int mainSize){
        helper.updateMainQuestSize(mainQuestObject.get_id(), mainSize);
        //Toast.makeText(this, Integer.toString(Singleton.getInstance().getmSideQuestList().size()), Toast.LENGTH_SHORT).show();
    }



    public void loadSideQuests(int id){
        setHelper();
        List<SideQuestObject> sideQuestList = helper.getAllSideQuests(Integer.toString(id));
        Singleton.getInstance().setmSideQuestList(sideQuestList);
    }



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Singleton.getInstance().setMainQuest(true);

        TextView barTxt = findViewById(R.id.appbar_text);
        button = findViewById(R.id.side_back_arrow);
        Singleton.getInstance().setTextView(barTxt);
        Singleton.getInstance().setButton(button);

        button = findViewById(R.id.side_back_arrow);



        islevelup = false;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        helper = new SQLiteHelper(this);
        Singleton.getInstance().setHelper(helper);

        if(helper.getUserProfile().getName().contains("null")){

            Intent loginQuestIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginQuestIntent);

        }else{
            userProfileObject = helper.getUserProfile();
            level = userProfileObject.getUserLevel();
            levelUp = userProfileObject.getUserLevelUp();
            points = userProfileObject.getPoints();
            setUserCard(helper.getUserProfile());
        }



        Singleton.getInstance().setActivity(this);

        Singleton.getInstance().setContext(this);

        final UserProfileObject user = helper.getUserProfile();

        Singleton.getInstance().setUserProfileObject(user);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //tabLayout = (TabLayout) findViewById(R.id.tabs);

//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        tabLayout.setSelectedTabIndicatorHeight(0);
        //createTabIcons();
    }

//    private void createTabIcons() {
//
//        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabOne.setText("Quests");
//        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_window_with_different_sections, 0, 0);
//        tabLayout.getTabAt(1).setCustomView(tabOne);
//
//        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabTwo.setText("My Account");
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_user_account_box, 0, 0);
//        tabLayout.getTabAt(0).setCustomView(tabTwo);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main_quest, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;

        }


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter  {



        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Fragment frgmt = null;

            switch (position) {
                case 0:
                    frgmt = new RootFragment();
                    break;
                    /*
                case 1:
                    frgmt = new RootFragment();
                    break;
                    */
                default:
                    break;
            }

            return frgmt;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }
    }
    public void changeBossImage(ImageView imageView, TextView textView) {
        Random rand = new Random();

        int n = rand.nextInt(16) + 1;

        switch (n) {
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_unicorn));
                textView.setText("Evil Unicorn");
                break;
            case 2:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_werewolf));
                textView.setText("Warewolf");

                break;
            case 3:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_witch));
                textView.setText("Witch");

                break;
            case 4:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_yeti));
                textView.setText("Yeti");

                break;
//            case 5:
//                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_cerberus));
//                textView.setText("Ceberus");
//
//                break;
            case 5:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_alien));
                textView.setText("Alien");

                break;
            case 6:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_devil));
                textView.setText("Devil");

                break;
            case 7:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_dinosaur));
                textView.setText("Dinosaur");

                break;
            case 8:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_echidna));
                textView.setText("Echidna");

                break;
            case 9:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_hand));
                textView.setText("Severed Hand");

                break;
            case 10:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_karakasakozou));
                textView.setText("Karakasa Kozo");

                break;
            case 11:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_kraken));
                textView.setText("Kraken");

                break;
            case 12:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_hydra));
                textView.setText("Hydra");

                break;
            case 13:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_loch_ness_monster));
                textView.setText("Loch-Ness Monster");

                break;
            case 14:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_minotaur));
                textView.setText("Minotaur");

                break;
            case 15:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_pirate));
                textView.setText("Pirate");

                break;
            case 16:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_tree));
                textView.setText("Evil Tree");
                break;

            default:
                break;
        }
    }
    public void setUserCard(UserProfileObject userProfileObject){
        avatarHead = findViewById(R.id.player_avatar_head);
        avatarClass = findViewById(R.id.player_avatar_class);
        playerName = findViewById(R.id.player_avatar_name);
        tillNextLevel = findViewById(R.id.till_next_level_main);
        playerLevel = findViewById(R.id.lvl);
        levelProgressBar = findViewById(R.id.level_progress_bar);
        if(points ==0){
            levelProgressBar.setProgress(0);
        }else {
            double fractionComplete = (double) points / (double) levelUp;
            int percentageComplete = (int) round((double) fractionComplete * 100);
            levelProgressBar.setProgress(percentageComplete);
        }


        playerName.setText(userProfileObject.getName());
        avatarClass.setText(userProfileObject.getImage());
        tillNextLevel.setText(Integer.toString(userProfileObject.getUserLevelUp() - userProfileObject.getPoints()) + " Quests Till Level Up");
        playerLevel.setText("Lvl: " + Integer.toString(userProfileObject.getUserLevel()));

        if (userProfileObject.getImage().equals("Knight")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_knight));
            avatarClass.setText("Class: Knight");

        }else if(userProfileObject.getImage().equals("Wizard")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_wizard));
            avatarClass.setText("Class: Wizard");

        }else if(userProfileObject.getImage().equals("Elf Boy")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_elf));
            avatarClass.setText("Class: Elf");

        }else if(userProfileObject.getImage().equals("Elf Girl")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_elf));
            avatarClass.setText("Class: Elf");

        }else if(userProfileObject.getImage().equals("Fairy")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_fairy));
            avatarClass.setText("Class: Fairy");

        }else if(userProfileObject.getImage().equals("Cyclops")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_cyclops));
            avatarClass.setText("Class: Cyclops");

        }else if(userProfileObject.getImage().equals("Vampire")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_vampire));
            avatarClass.setText("Class: Vampire");

        }else if(userProfileObject.getImage().equals("Princess")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_princess));
            avatarClass.setText("Class: Princess");

        }else if(userProfileObject.getImage().equals("Viking")){
            avatarHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_viking));
            avatarClass.setText("Class: Viking");
        }

    }


/*
    public void createEvent(GoogleAccountCredential mCredential) {

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, mCredential)
                .setApplicationName("R_D_Location Callendar")
                .build();

        Event event = new Event()
                .setSummary("Event- April 2016")
                .setLocation("Dhaka")
                .setDescription("New Event 1");

        DateTime startDateTime = new DateTime("2016-04-17T18:10:00+06:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Dhaka");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2016-04-17T18:40:00+06:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Dhaka");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("abir@aksdj.com"),
                new EventAttendee().setEmail("asdasd@andlk.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        try {
            event = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Event created: %s\n", event.getHtmlLink());

    }
    */


 }




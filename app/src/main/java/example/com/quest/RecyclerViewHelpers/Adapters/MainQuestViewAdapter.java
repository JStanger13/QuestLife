package example.com.quest.RecyclerViewHelpers.Adapters;

import android.app.DatePickerDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import example.com.quest.DatabaseHelpers.SQLiteHelper;
import example.com.quest.Fragments.DatePickerFragment;
import example.com.quest.Fragments.MainQuestFragment;
import example.com.quest.Fragments.SideQuestFragment;
import example.com.quest.Fragments.TimePickerFragment;
import example.com.quest.MainActivity;
import example.com.quest.Model.MainQuestObject;
import example.com.quest.R;
import example.com.quest.RecyclerViewHelpers.ViewHolders.MainQuestViewHolder;
import example.com.quest.Singleton.Singleton;

import static android.content.ContentValues.TAG;

/**
 * Created by justinstanger on 3/4/18.
 */

public class MainQuestViewAdapter extends RecyclerView.Adapter <MainQuestViewHolder> implements DatePickerFragment.DateDialogListener, TimePickerFragment.TimeDialogListener{
    private List<MainQuestObject> mainQuestList;
    private SQLiteHelper helper;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String DIALOG_DATE = "MainActivity.DateDialog";
    private static final String DIALOG_TIME = "MainActivity.TimeDialog";
    public MainQuestViewAdapter(final List<MainQuestObject> mList) {
        mainQuestList = mList;
    }

    public void putBundle(final MainQuestObject mainQuest, SideQuestFragment sideQuestFragment) {
        Bundle bundles = new Bundle();
        // ensure your object has not null
        if (mainQuest != null) {
            bundles.putSerializable("mainQuest", mainQuest);
            Log.e("MainQuest", "is valid");
        } else {
            Log.e("MainQuest", "is null");

        }

        Singleton.getInstance().setBundle(true);
        Singleton.getInstance().setMainQuestObject(mainQuest);
        sideQuestFragment.setArguments(bundles);
    }

    public void changeToSideQuestFragment(MainQuestObject mainQuest) {

        FragmentTransaction fragmentTransaction = ((AppCompatActivity) Singleton.getInstance()
                .getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        SideQuestFragment sideQuestFragment = new SideQuestFragment();

        putBundle(mainQuest, sideQuestFragment);

        fragmentTransaction.replace(R.id.root_frame, sideQuestFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @Override
    public MainQuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.custom_main_quest_layout, parent, false);
        MainQuestViewHolder viewHolder = new MainQuestViewHolder(parentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MainQuestViewHolder holder, final int position) {
        final MainQuestObject currentMainQuest = mainQuestList.get(position);
        helper = new SQLiteHelper(Singleton.getInstance().getContext());

        holder.mainDate.setText(currentMainQuest.getMainQuestDate());
        holder.mainTime.setText(currentMainQuest.getMainTime());
        changeList(Singleton.getInstance().getMainQuestList());
        setBossImage(currentMainQuest, holder.bossImage);

        holder.clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.mainQuestObject = currentMainQuest;
                dialog.show(((FragmentActivity) Singleton.getInstance().getContext()).getSupportFragmentManager(), DIALOG_DATE);

            }
        });

        holder.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment dialog = new DatePickerFragment();
                dialog.mainQuestObject = currentMainQuest;
                dialog.show(((FragmentActivity) Singleton.getInstance().getContext()).getSupportFragmentManager(), DIALOG_DATE);
            }
        });



        holder.mainTitle.setText(currentMainQuest.getMainQuestTitle());

        holder.progressBar.setProgress(currentMainQuest.getPercentageComplete());

        holder.deleteMainQuest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.d("id = " + String.valueOf(currentMainQuest.get_id()), "id");

                if (helper.getAllSideQuests(Integer.toString(currentMainQuest.get_id())).isEmpty() || currentMainQuest.isMainQuestComplete()) {
                    mainQuestList.remove(holder.getAdapterPosition());
                    changeList(mainQuestList);
                    helper.deleteMainQuest(currentMainQuest.get_id());
                    helper.deleteAllSideQuest(currentMainQuest.get_id());
                    changeList(mainQuestList);
                    notifyItemRemoved(holder.getAdapterPosition());
                    ((MainActivity) Singleton.getInstance().getContext()).changeQuestFragment(new MainQuestFragment());

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    LayoutInflater inflater =
                            (LayoutInflater) Singleton.getInstance().getContext().getSystemService(Singleton.getInstance().getContext().LAYOUT_INFLATER_SERVICE);

                    View deleteMainQuest = inflater.inflate(R.layout.delete_main_quest_dialog, null);
                    final TextView dialogSize = deleteMainQuest.findViewById(R.id.dialog_quest_size);

                    final ImageView dialogBoss = deleteMainQuest.findViewById(R.id.dialog_boss);
                    final TextView dialogQuestTitle = deleteMainQuest.findViewById(R.id.dialog_quest_title);
                    dialogSize.setText(Integer.toString(currentMainQuest.getQuestSize()) + " Side Quests");
                    dialogQuestTitle.setText(currentMainQuest.getMainQuestTitle());
                    setBossImage(currentMainQuest, dialogBoss);


                    builder.setView(deleteMainQuest);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            helper.deleteMainQuest(currentMainQuest.get_id());
                            helper.deleteAllSideQuest(currentMainQuest.get_id());
                            changeList(mainQuestList);
                            notifyItemRemoved(holder.getAdapterPosition());
                            ((MainActivity) Singleton.getInstance().getContext()).changeQuestFragment(new MainQuestFragment());
                            //Toast.makeText(Singleton.getInstance().getContext(), "You Still Have SideQuests", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        holder.mainSize.setText(
                Integer.toString(
                        helper.getAllSideQuests(Integer.toString(currentMainQuest.get_id())).size()
                )
                        + " side quests");

        holder.mainPercentComplete.setText(
                Integer.toString(currentMainQuest.getPercentageComplete()
                )
                        + "%");

        if(holder.mainPercentComplete.getText().toString().contains("100%")){
            holder.mainSize.setText("MISSION COMPLETE");
            helper.deleteMainQuest(currentMainQuest.get_id());


        }


        holder.goToSideQuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSideQuestFragment(currentMainQuest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainQuestList.size();
    }

    public void changeList(List<MainQuestObject> list){
        mainQuestList = list;
    }


    @Override
    public void onFinishDialog(Date date, MainQuestObject mainQuestObject) {

        helper.updateMainQuestDate(mainQuestObject.get_id(), formatDate(date));
        ((MainActivity) Singleton.getInstance().getContext()).changeQuestFragment(new MainQuestFragment());
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }

    @Override
    public void onFinishDialog(String time,  MainQuestObject mainQuestObject) {
        helper.updateMainQuestTime(mainQuestObject.get_id(), time);
        ((MainActivity) Singleton.getInstance().getContext()).changeQuestFragment(new MainQuestFragment());

    }

    public void setBossImage(MainQuestObject currentMainQuest, ImageView imageView) {
        if (currentMainQuest.getmBossName().equals("Alien")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_alien));

        } else if (currentMainQuest.getmBossName().equals("Cereberus")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_cerberus));

        } else if (currentMainQuest.getmBossName().equals("Devil")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_devil));

        } else if (currentMainQuest.getmBossName().equals("Echidna")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_echidna));

        } else if (currentMainQuest.getmBossName().equals("Severed Hand")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_hand));

        } else if (currentMainQuest.getmBossName().equals("Hydra")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_hydra));

        } else if (currentMainQuest.getmBossName().equals("Karakasa Kozo")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_karakasakozou));

        } else if (currentMainQuest.getmBossName().equals("Kraken")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_kraken));

        } else if (currentMainQuest.getmBossName().equals("Loch-Ness Monster")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_loch_ness_monster));
        } else if (currentMainQuest.getmBossName().equals("Minotaur")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_minotaur));
        } else if (currentMainQuest.getmBossName().equals("Pirate")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_pirate));
        } else if (currentMainQuest.getmBossName().equals("Evil Tree")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_tree));
        } else if (currentMainQuest.getmBossName().equals("Evil Unicorn")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_unicorn));
        } else if (currentMainQuest.getmBossName().equals("Warewolf")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_werewolf));
        } else if (currentMainQuest.getmBossName().equals("Witch")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_witch));
        } else if (currentMainQuest.getmBossName().equals("Yeti")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_yeti));
        } else if (currentMainQuest.getmBossName().equals("Dinosaur")) {
            imageView.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_dinosaur));
        }

    }
}
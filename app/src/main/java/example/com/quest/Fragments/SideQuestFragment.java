
package example.com.quest.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.com.quest.MainActivity;
import example.com.quest.Model.MainQuestObject;
import example.com.quest.Model.SideQuestObject;
import example.com.quest.R;
import example.com.quest.RecyclerViewHelpers.Adapters.SideQuestViewAdapter;
import example.com.quest.Singleton.Singleton;

import static java.lang.Math.round;



public class SideQuestFragment extends Fragment {
    RecyclerView mSideQuestRecyclerView;
    SideQuestViewAdapter mSideQuestAdapter;
    FloatingActionButton mAddSideQuestButton;
    TextView sideTitle, complete;
    MainQuestObject mainQuestObject;
    List<SideQuestObject> sideQuestList;
    ProgressBar progressBar;
    Button backButton;
    ImageView bossImage;
    TextView barText;


    public static SideQuestFragment newInstance() {
        SideQuestFragment sideQuestFragmentNewInstance = new SideQuestFragment();
        return sideQuestFragmentNewInstance;
    }

    public SideQuestFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side_quest, container,false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bossImage = view.findViewById(R.id.side_quest_boss_image);

        barText = Singleton.getInstance().getTextView();
        barText.setText("Side Quests");

        backButton = Singleton.getInstance().getButton();
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setBackButton();
            }
        });

        mainQuestObject = Singleton.getInstance().getMainQuestObject();
        if(mainQuestObject.isMainQuestComplete()){
            Toast.makeText(Singleton.getInstance().getContext(), "IN HERE NOW", Toast.LENGTH_SHORT).show();
        }

        if (mainQuestObject.getmBossName().equals("Alien")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_alien));

        }else if(mainQuestObject.getmBossName().equals("Cereberus")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_cerberus));

        }else if(mainQuestObject.getmBossName().equals("Devil")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_devil));

        }else if(mainQuestObject.getmBossName().equals("Echidna")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_echidna));

        }else if(mainQuestObject.getmBossName().equals("Severed Hand")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_hand));

        }else if(mainQuestObject.getmBossName().equals("Hydra")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_hydra));

        }else if(mainQuestObject.getmBossName().equals("Karakasa Kozo")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_karakasakozou));

        }else if(mainQuestObject.getmBossName().equals("Kraken")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_kraken));

        }else if(mainQuestObject.getmBossName().equals("Loch-Ness Monster")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_loch_ness_monster));
        }else if(mainQuestObject.getmBossName().equals("Minotaur")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_minotaur));
        }else if(mainQuestObject.getmBossName().equals("Pirate")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_pirate));
        }else if(mainQuestObject.getmBossName().equals("Evil Tree")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_tree));
        }else if(mainQuestObject.getmBossName().equals("Evil Unicorn")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_unicorn));
        }else if(mainQuestObject.getmBossName().equals("Warewolf")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_werewolf));
        }else if(mainQuestObject.getmBossName().equals("Witch")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_witch));
        }else if(mainQuestObject.getmBossName().equals("Yeti")){
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_yeti));
        }else if(mainQuestObject.getmBossName().equals("Dinosaur")) {
            bossImage.setImageDrawable(
                    Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.ic_dinosaur));
        }




        mSideQuestRecyclerView = view.findViewById(R.id.side_recycler_view);

        sideTitle = view.findViewById(R.id.side_quest_title);
        complete = view.findViewById(R.id.percent_complete);
        progressBar = view.findViewById(R.id.progress_bar);

        ((MainActivity)getActivity()).loadSideQuests(mainQuestObject.get_id());


        sideQuestList = Singleton.getInstance().getmSideQuestList();

        if(Singleton.getInstance().isBundle()) {
            Bundle bundle = getArguments();
            mainQuestObject = (MainQuestObject) bundle.getSerializable("mainQuest");

            Singleton.getInstance().setBundle(false);
            Singleton.getInstance().setMainQuestObject(mainQuestObject);

        }else{
            mainQuestObject = Singleton.getInstance().getMainQuestObject();
        }

        sideTitle.setText(mainQuestObject.getMainQuestTitle());


        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(Singleton.getInstance().getContext(), LinearLayoutManager.VERTICAL, false);
        mSideQuestRecyclerView.setLayoutManager(linearLayoutManager);

        mSideQuestAdapter = new SideQuestViewAdapter(sideQuestList, mainQuestObject, complete, progressBar);
        mSideQuestRecyclerView.setAdapter(mSideQuestAdapter);


        //RecyclerView Animation
        //int resId = R.anim.layout_animation_fall_down;
        //LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(Singleton.getInstance().getContext(), resId);
        //mSideQuestRecyclerView.setLayoutAnimation(animation);

        mAddSideQuestButton = view.findViewById(R.id.add_side_quest);

        mAddSideQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater =
                        (LayoutInflater) Singleton.getInstance().getContext().getSystemService(Singleton.getInstance().getContext().LAYOUT_INFLATER_SERVICE);

                View createSideQuest = inflater.inflate(R.layout.create_side_quest_dialog, null);
                builder.setView(createSideQuest);


                final EditText mSideTitle =
                        createSideQuest.findViewById(R.id.create_side_quest_title);


                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String userSideTitle = mSideTitle.getText().toString();
                        SideQuestObject userSide = new SideQuestObject(userSideTitle, false, mainQuestObject.get_id(), mainQuestObject.getmUserName());


                        ((MainActivity) getActivity()).saveSideQuest(userSide);
                        ((MainActivity) getActivity()).loadSideQuests(userSide.getMainQuestId());
                        ((MainActivity) getActivity()). getPercentage(mainQuestObject);

                        int mainQuestSize = mainQuestObject.getQuestSize() + 1;
                        mainQuestObject.setQuestSize(mainQuestSize);
                        ((MainActivity)getActivity()).updateMainQuestSize(mainQuestObject, mainQuestSize);

                        //mSideQuestAdapter.changeList(Singleton.getInstance().getmSideQuestList());
                        //mSideQuestAdapter.notifyItemInserted(0);

                        double percentageComplete = ((MainActivity) getActivity()).getPercentage(mainQuestObject);

                        complete.setText(Integer.toString((int)round(percentageComplete)) + "%");
                        progressBar.setProgress((int)round(percentageComplete));


                        ((MainActivity) getActivity()).changeQuestFragment(new SideQuestFragment());
                        if(percentageComplete == 100) {
                            //((MainActivity) getActivity()).changeComplete(mainQuestObject);

                            ((MainActivity) getActivity()).setMissionComplete();
                            ((MainActivity) getActivity()).saveUserProfile(Singleton.getInstance().getUserProfileObject());
                            sideTitle.setText("Mission Complete!");
                            ((MainActivity)getActivity()).changeQuestFragment(new SideQuestFragment());

                        }

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    @Override
    public void onResume() {
        super.onResume();
        mSideQuestAdapter.notifyDataSetChanged();
        //mMainQuestAdapter.changeList(Singleton.getInstance().getMainQuests());

    }
}

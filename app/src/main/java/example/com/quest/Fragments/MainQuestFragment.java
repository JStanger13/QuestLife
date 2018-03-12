package example.com.quest.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SyncInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import example.com.quest.MainActivity;
import example.com.quest.Model.MainQuestObject;
import example.com.quest.R;
import example.com.quest.RecyclerViewHelpers.Adapters.MainQuestViewAdapter;
import example.com.quest.Singleton.Singleton;

import static android.content.ContentValues.TAG;

/**
 * Created by justinstanger on 3/4/18.
 */

public class MainQuestFragment extends Fragment {
    RecyclerView mRecyclerView;
    MainQuestViewAdapter mMainQuestAdapter;
    FloatingActionButton mAddButton;
    List<MainQuestObject> mainQuestList;
    TextView barText;
    Button button;
    RelativeLayout relativeLayout;


    public static MainQuestFragment newInstance() {
        MainQuestFragment mainQuestFragmentNewInstance = new MainQuestFragment();
        return mainQuestFragmentNewInstance;
    }

    public MainQuestFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_quest, container,false);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barText = Singleton.getInstance().getTextView();
        barText.setText("Main Quests");
        relativeLayout = view.findViewById(R.id.no_main_quests_layout);

        Singleton.getInstance().setRelativeLayout(relativeLayout);


        ((MainActivity)getActivity()).loadMainQuests(false);
        mainQuestList = Singleton.getInstance().getMainQuestList();

        mRecyclerView = view.findViewById(R.id.main_recycler_view);



        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(Singleton.getInstance().getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mMainQuestAdapter = new MainQuestViewAdapter(mainQuestList);

        mRecyclerView.setAdapter(mMainQuestAdapter);
        Singleton.getInstance().setAdapter(mMainQuestAdapter);

        mAddButton = view.findViewById(R.id.add_main_quest);

        button = Singleton.getInstance().getButton();
        button.setVisibility(View.INVISIBLE);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater =
                        (LayoutInflater) Singleton.getInstance().getContext().getSystemService(Singleton.getInstance().getContext().LAYOUT_INFLATER_SERVICE);

                View createMainQuest = inflater.inflate(R.layout.create_main_quest_dialog, null);
                builder.setView(createMainQuest);

                ImageView mBossImage = createMainQuest.findViewById(R.id.create_boss);
                final TextView mBossname = createMainQuest.findViewById(R.id.create_boss_name);
                final EditText mMainDescription =
                        createMainQuest.findViewById(R.id.create_main_title);
                ((MainActivity) getActivity()).changeBossImage(mBossImage, mBossname);
                //make this it's own method!!!

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {



                String userMainDescription = mMainDescription.getText().toString();
                String userBossName = mBossname.getText().toString();

                MainQuestObject userMain = new MainQuestObject(userMainDescription, "", "Justin", userBossName);
                Singleton.getInstance().getMainQuestList().add(userMain);

                ((MainActivity) getActivity()).saveQuest(userMain);
                ((MainActivity) getActivity()).loadMainQuests(false);

                mMainQuestAdapter.notifyItemInserted(0);
                ((MainActivity) getActivity()).changeQuestFragment(new MainQuestFragment());


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
        mMainQuestAdapter.notifyDataSetChanged();

    }
}







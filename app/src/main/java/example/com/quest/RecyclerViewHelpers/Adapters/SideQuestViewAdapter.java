package example.com.quest.RecyclerViewHelpers.Adapters;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import example.com.quest.DatabaseHelpers.SQLiteHelper;
import example.com.quest.Fragments.SideQuestFragment;
import example.com.quest.MainActivity;
import example.com.quest.Model.MainQuestObject;
import example.com.quest.Model.SideQuestObject;
import example.com.quest.Model.UserProfileObject;
import example.com.quest.R;
import example.com.quest.RecyclerViewHelpers.ViewHolders.SideQuestViewHolder;
import example.com.quest.Singleton.Singleton;

import static java.lang.Math.round;

/**
 * Created by justinstanger on 3/4/18.
 */

public class SideQuestViewAdapter extends RecyclerView.Adapter<SideQuestViewHolder> {
    List<SideQuestObject> mSideQuestList;
    SQLiteHelper helper;
    MainQuestObject currentMain;
    TextView size, complete;
    ProgressBar progressBar;
    int gold;

    public SideQuestViewAdapter(final List<SideQuestObject> sideQuestList, MainQuestObject main,
                                TextView mComplete, ProgressBar mProgressBar) {
        mSideQuestList = sideQuestList;
        currentMain = main;
        complete = mComplete;
        progressBar = mProgressBar;
    }

    public void getPercentageOfComplete(int position) {
        double fractionComplete = (double) currentMain.getCompletedSideQuests() / (double)
                helper.getAllSideQuests(Integer.toString(currentMain.get_id())).size();
        int percentageComplete = (int) round((double) fractionComplete * 100);


        helper.updateMainQuestPercent(percentageComplete, currentMain.get_id());
        complete.setText(Integer.toString(percentageComplete) + "%");
        notifyItemRemoved(position);
    }

    @Override
    public SideQuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.custom_side_quest_layout, parent, false);
        SideQuestViewHolder viewHolder = new SideQuestViewHolder(parentView);
        return viewHolder;
    }


    public boolean setCheckBox(SideQuestObject userSideQuest, final SideQuestViewHolder holder) {
        if (userSideQuest.isSideQuestComplete()) {

            holder.mCheckSideQuest.setChecked(true);
            holder.mSideQuestTitle.setTextColor(Color.RED);
            holder.mSideQuestTitle.setPaintFlags
                    (holder.mSideQuestTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            checkPercentage(complete.getText().toString());


            return true;
        } else {
            holder.mCheckSideQuest.setChecked(false);
            holder.mSideQuestTitle.setTextColor(Color.BLACK);
            holder.mSideQuestTitle.setPaintFlags
                    (holder.mSideQuestTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            checkPercentage(complete.getText().toString());
            return false;
        }
    }

    public void checkPercentage(String comp) {
        if (comp.contains("100% Complete")) {
            helper.updateMainQuestComplete(true, currentMain.get_id());
        }
    }

    @Override
    public void onBindViewHolder(final SideQuestViewHolder holder, final int position) {
        helper = new SQLiteHelper(Singleton.getInstance().getContext());

        final SideQuestObject currentSideQuest = mSideQuestList.get(position);
        setCheckBox(currentSideQuest, holder);
        holder.mSideQuestTitle.setText(currentSideQuest.getSideQuestTitle());
        holder.mCheckSideQuest.setChecked(setCheckBox(currentSideQuest, holder));


        //make it's own method

        double fractionComplete = (double) currentMain.getCompletedSideQuests() / (double)
                helper.getAllSideQuests(Integer.toString(currentMain.get_id())).size();

        int percentageComplete = (int) round((double) fractionComplete * 100);

        helper.updateMainQuestPercent(percentageComplete, currentMain.get_id());

        complete.setText(Integer.toString(percentageComplete) + "%");

        progressBar.setProgress((int) round(percentageComplete));


        holder.mCheckSideQuest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    currentSideQuest.setSideQuestComplete(true);

                    helper.updateSideCheck(currentSideQuest.isSideQuestComplete(), currentSideQuest.getSideQuestId());
                    helper.addSideQuestsCompleted(currentMain.get_id(), currentMain.getCompletedSideQuests());
                    currentMain.setCompletedSideQuests(currentMain.getCompletedSideQuests() + 1);



                    //make it's own method

                    double fractionComplete = (double) currentMain.getCompletedSideQuests() / (double)
                            helper.getAllSideQuests(Integer.toString(currentMain.get_id())).size();
                    int percentageComplete = (int) round((double) fractionComplete * 100);


                    helper.updateMainQuestPercent(percentageComplete, currentMain.get_id());

                    complete.setText(Integer.toString(percentageComplete) + "%");

                    progressBar.setProgress((int) round(percentageComplete));


                    if (percentageComplete == 100) {
                        helper.updateMainQuestComplete(true, currentMain.get_id());
                        UserProfileObject userProfileObject = Singleton.getInstance().getUserProfileObject();

                        ((MainActivity) Singleton.getInstance().getActivity()).setMissionComplete();
                    }
                    ((MainActivity) Singleton.getInstance().getActivity()).changeQuestFragment(new SideQuestFragment());





                } else {
                    currentSideQuest.setSideQuestComplete(false);
                    helper.updateSideCheck(currentSideQuest.isSideQuestComplete(), currentSideQuest.getSideQuestId());
                    helper.deleteSideQuestsCompleted(currentMain.get_id(), currentMain.getCompletedSideQuests());
                    currentMain.setCompletedSideQuests(currentMain.getCompletedSideQuests() - 1);

                    //getPercentageOfComplete(position);


                    //make it's own method
                    double fractionComplete = (double) currentMain.getCompletedSideQuests() / (double)
                            helper.getAllSideQuests(Integer.toString(currentMain.get_id())).size();

                    int percentageComplete = (int) round((double) fractionComplete * 100);

                    helper.updateMainQuestPercent(percentageComplete, currentMain.get_id());

                    complete.setText(Integer.toString(percentageComplete) + "%");

                    progressBar.setProgress((int) round(percentageComplete));
                    ((MainActivity) Singleton.getInstance().getActivity()).changeQuestFragment(new SideQuestFragment());



                }

            }
        });

        holder.mDeleteSideQuest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // remove your item from data base


                mSideQuestList.remove(position);  // remove the item from list
                changeList(mSideQuestList);
                helper.deleteSideQuest(currentSideQuest.getSideQuestId());



                int sideQuestListSize = helper.getAllSideQuests(Integer.toString(currentMain.get_id())).size();
                //size.setText(sideQuestListSize + " side quests");


                //put in own method
                double fractionComplete = (double) currentMain.getCompletedSideQuests() / (double)
                        helper.getAllSideQuests(Integer.toString(currentMain.get_id())).size();

                int percentageComplete = (int) round((double) fractionComplete * 100);
                helper.updateMainQuestPercent(percentageComplete, currentMain.get_id());
                complete.setText(Integer.toString(percentageComplete) + "%");
                progressBar.setProgress((int) round(percentageComplete));
                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSideQuestList.size();
    }

    public void changeList(List<SideQuestObject> list) {
        mSideQuestList = list;
        notifyDataSetChanged();
    }

}

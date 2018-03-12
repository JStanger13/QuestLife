package example.com.quest.RecyclerViewHelpers.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import example.com.quest.R;

/**
 * Created by justinstanger on 3/4/18.
 */

public class SideQuestViewHolder extends RecyclerView.ViewHolder {
    public CheckBox mCheckSideQuest;
    public TextView mSideQuestTitle;
    public Button mDeleteSideQuest;

    public SideQuestViewHolder(View itemView) {
        super(itemView);
        mSideQuestTitle = itemView.findViewById(R.id.side_quest_title);
        mCheckSideQuest = itemView.findViewById(R.id.check_side_quest);
        mDeleteSideQuest = itemView.findViewById(R.id.delete_side_quest);
    }
}

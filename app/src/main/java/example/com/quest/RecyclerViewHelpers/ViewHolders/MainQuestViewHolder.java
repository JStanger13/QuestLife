package example.com.quest.RecyclerViewHelpers.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import example.com.quest.R;

/**
 * Created by justinstanger on 3/4/18.
 */

public class MainQuestViewHolder extends RecyclerView.ViewHolder {
    public ImageView bossImage;
    public TextView mainTitle, mainDate, mainSize, mainPercentComplete, mainTime, bossName, setDate;
    public CardView mainQuestCardView;
    public ProgressBar progressBar;
    public ImageButton goToSideQuests, deleteMainQuest, calendar, clock;


    public MainQuestViewHolder(View itemView) {
        super(itemView);
        calendar = itemView.findViewById(R.id.calendar_button);
        mainDate = itemView.findViewById(R.id.main_quest_date);
        mainTitle = itemView.findViewById(R.id.main_quest_title);
        mainSize = itemView.findViewById(R.id.main_frag_quest_size);
        mainPercentComplete = itemView.findViewById(R.id.main_quest_completion_percent);
        mainQuestCardView = itemView.findViewById(R.id.main_quest_card_view);
        bossImage = itemView.findViewById(R.id.boss);
        progressBar = itemView.findViewById(R.id.main_quest_progress_bar);
        goToSideQuests = itemView.findViewById(R.id.go_to_side_quests);
        deleteMainQuest = itemView.findViewById(R.id.delete_main_quest);
        clock = itemView.findViewById(R.id.clock_button);
        mainTime = itemView.findViewById(R.id.main_quest_time);
    }
}


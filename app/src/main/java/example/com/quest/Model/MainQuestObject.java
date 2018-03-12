package example.com.quest.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justinstanger on 3/4/18.
 */

public class MainQuestObject implements Serializable {
    int _id, completedSideQuests, percentageComplete, questSize;
    boolean isMainQuestComplete;
    private List<SideQuestObject> mSideQuestList;
    private String mMainQuestTitle, mMainQuestDate, mUserName, mBossName, mMainTime;

    public String getMainTime() {
        return mMainTime;
    }

    public void setMainTime(String mMainTime) {
        this.mMainTime = mMainTime;
    }

    public MainQuestObject(){

    }



    public MainQuestObject(int id,
                           String title,
                           String date,
                           String time,
                           boolean isMainComplete,
                           int completedSideQuests,
                           int percent,
                           String userName,
                           String bossName){
        _id = id;
        mMainQuestTitle = title;
        mMainQuestDate = date;
        mMainTime = time;
        isMainQuestComplete = isMainComplete;
        this.completedSideQuests = completedSideQuests;
        percentageComplete = percent;
        mUserName = userName;
        mSideQuestList = new ArrayList<>();
        questSize = 0;
        mBossName = bossName;
    }


    public MainQuestObject(String userMainQuestTitle,
                           String userMainQuestDate,
                           String userName,
                           String userBossName){
        mMainQuestTitle = userMainQuestTitle;
        mMainQuestDate = userMainQuestDate;
        mSideQuestList = new ArrayList<>();
        completedSideQuests = 0;
        isMainQuestComplete = false;
        percentageComplete = 0;
        mUserName = userName;
        mSideQuestList = new ArrayList<>();
        questSize = 0;
        mBossName = userBossName;
    }



    public boolean isMainQuestComplete() {
        return isMainQuestComplete;
    }

    public void setMainQuestComplete(boolean mainQuestComplete) {
        isMainQuestComplete = mainQuestComplete;
    }
    //public void setSize(int mSize) {
    //   this.mSize = mSize;
    //}

    //public int getSize() {
    //return mSize;
    //}

    public int getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(int percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public String getMainQuestTitle(){
        return mMainQuestTitle;
    }

    public void setMainQuestTitle(String mMainQuestTitle) {
        this.mMainQuestTitle = mMainQuestTitle;
    }

    public String getMainQuestDate(){
        return mMainQuestDate;
    }

    public void setMainQuestDate(String mMainQuestDate) {
        this.mMainQuestDate = mMainQuestDate;
    }

    public List<SideQuestObject> getSideQuestList(){
        return mSideQuestList;
    }

    public void setSideQuestList(List<SideQuestObject> mSideQuestList) {
        this.mSideQuestList = mSideQuestList;
    }

    public int get_id() {return _id;}

    public void set_id(int id){
        this._id = id;
    }


    public int getCompletedSideQuests() {
        return completedSideQuests;
    }
    public void setCompletedSideQuests(int completedSideQuests) {
        this.completedSideQuests = completedSideQuests;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public int getQuestSize() {
        return questSize;
    }

    public void setQuestSize(int questSize) {
        this.questSize = questSize;
    }

    public String getmBossName() {
        return mBossName;
    }

    public void setmBossName(String mBossName) {
        this.mBossName = mBossName;
    }
}

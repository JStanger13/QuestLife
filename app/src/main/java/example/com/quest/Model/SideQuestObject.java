package example.com.quest.Model;

/**
 * Created by justinstanger on 3/4/18.
 */

public class SideQuestObject {
    String mSideQuestTitle, mUserName;
    boolean isSideQuestComplete;
    int mMainQuestId, _id;


    public SideQuestObject (String userSideTitle, boolean isChecked, int id, int mainId, String userName){
        mSideQuestTitle = userSideTitle;
        isSideQuestComplete = isChecked;
        _id = id;
        mMainQuestId = mainId;
        mUserName = userName;
    }

    public SideQuestObject (String userSideTitle, boolean isChecked, int mainId, String userName){
        mSideQuestTitle = userSideTitle;
        isSideQuestComplete = isChecked;
        mMainQuestId = mainId;
        mUserName = userName;
    }



    public SideQuestObject() {

    }


    public String getSideQuestTitle(){return mSideQuestTitle;}
    public void setSideQuestTitle(String mSideQuestTitle) {this.mSideQuestTitle = mSideQuestTitle;}
    public int getMainQuestId() {return mMainQuestId;}
    public void setMainQuestId(int mMainQuestId) {
        this.mMainQuestId = mMainQuestId;
    }
    public int getSideQuestId() {
        return _id;
    }
    public void setSideQuestId(int mSideQuestId) {
        this._id = mSideQuestId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public boolean isSideQuestComplete() {return isSideQuestComplete;}
    public void setSideQuestComplete(boolean sideQuestComplete) {isSideQuestComplete = sideQuestComplete;}
}

package example.com.quest.Singleton;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.quest.DatabaseHelpers.SQLiteHelper;
import example.com.quest.Model.MainQuestObject;
import example.com.quest.Model.SideQuestObject;
import example.com.quest.Model.UserProfileObject;
import example.com.quest.RecyclerViewHelpers.Adapters.MainQuestViewAdapter;

/**
 * Created by justinstanger on 3/4/18.
 */

public class Singleton {
    private static Singleton sInstance;
    private Context context;
    private List<MainQuestObject> mMainQuestList;
    private List<SideQuestObject>mSideQuestList;
    private UserProfileObject userProfileObject;
    private SQLiteHelper helper;
    private MainQuestObject mainQuestObject;
    boolean bundle;
    private SideQuestObject sideQuestObject;
    private Activity activity;
    private boolean isMainQuest;
    private MainQuestViewAdapter adapter;
    private String setDate = "null";
    private TextView textView;
    private Button button;
    private RelativeLayout relativeLayout;

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public MainQuestViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MainQuestViewAdapter adapter) {
        this.adapter = adapter;
    }

    public Singleton() {
        this.mMainQuestList = new ArrayList<>();
    }

    public static Singleton getInstance() {
        if (sInstance == null) {
            sInstance = new Singleton();
        }

        return sInstance;
    }

    public void addMain(MainQuestObject main) {
        helper.addMainQuest(main);
        mMainQuestList.add(0, main);
    }

    public static void setsInstance(Singleton sInstance) {
        Singleton.sInstance = sInstance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<MainQuestObject> getMainQuestList() {
        return mMainQuestList;
    }

    public void setMainQuestList(List<MainQuestObject> mMainQuestList) {
        this.mMainQuestList = mMainQuestList;
    }

    public UserProfileObject getUserProfileObject() {
        return userProfileObject;
    }

    public void setUserProfileObject(UserProfileObject userProfileObject) {
        this.userProfileObject = userProfileObject;
    }

    public SQLiteHelper getHelper() {
        return helper;
    }

    public MainQuestObject getMainQuestObject() {
        return mainQuestObject;
    }

    public void setMainQuestObject(MainQuestObject mainQuestObject) {
        this.mainQuestObject = mainQuestObject;
    }

    public List<SideQuestObject> getmSideQuestList() {
        return mSideQuestList;
    }

    public void setmSideQuestList(List<SideQuestObject> mSideQuestList) {
        this.mSideQuestList = mSideQuestList;
    }

    public boolean isBundle() {
        return bundle;
    }

    public void setBundle(boolean bundle) {
        this.bundle = bundle;
    }

    public SideQuestObject getSideQuestObject() {
        return sideQuestObject;
    }

    public void setSideQuestObject(SideQuestObject sideQuestObject) {
        this.sideQuestObject = sideQuestObject;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    /*
    public void saveLevel(int level, int levelUp){
        helper.saveLevel(level, "Justin", levelUp);
    }
*/
    public void setHelper(SQLiteHelper helper) {
        this.helper = helper;
    }

    public boolean isMainQuest() {
        return isMainQuest;
    }

    public void setMainQuest(boolean mainQuest) {
        this.isMainQuest = mainQuest;
    }
}

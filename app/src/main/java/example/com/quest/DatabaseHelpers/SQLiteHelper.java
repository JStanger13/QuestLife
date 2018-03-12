package example.com.quest.DatabaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.com.quest.Model.MainQuestObject;
import example.com.quest.Model.SideQuestObject;
import example.com.quest.Model.UserProfileObject;
import example.com.quest.Singleton.Singleton;

import static java.lang.Boolean.parseBoolean;

/**
 * Created by justinstanger on 3/4/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Main Quest table name
    private static final String TABLE_MAIN_QUEST = "main_quests";

    //Side Quest table name
    private static final String TABLE_SIDE_QUEST = "side_quests";

    //User Profile
    private static final String TABLE_USER_PROFILE = "user_profile";



    // MainQuest Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MAIN_QUEST_TITLE = "main_quest_title";
    private static final String KEY_MAIN_QUEST_DATE = "main_quest_date";
    private static final String KEY_MAIN_QUEST_TIME = "main_quest_time";

    private static final String KEY_SIDE_QUESTS_COMPLETED = "side_quests_completed";
    private static final String KEY_MAIN_QUEST_COMPLETE = "main_quest_complete";
    private static final String KEY_MAIN_QUEST_SIZE = "main_quest_size";
    private static final String KEY_MAIN_QUEST_PERCENT = "main_quest_percent";
    private static final String KEY_MAIN_QUEST_USERNAME = "main_quest_username";
    private static final String KEY_MAIN_QUEST_BOSS = "main_quest_boss";



    // SideQuest Table Columns names
    private static final String KEY_MAIN_QUEST_ID = "id";
    private static final String KEY_SIDE_QUEST_ID = "side_id";
    private static final String KEY_SIDE_QUEST_TITLE = "side_quest_title";
    private static final String KEY_SIDE_QUEST_CHECK = "side_check";
    private static final String KEY_SIDE_QUEST_USERNAME = "side_quest_username";


    // User Profile Table Colums
    private static final String KEY_USER_PROFILE_ID = "user_id";
    private static final String KEY_USER_LEVEL = "user_level";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_USER_IMAGE = "user_image";
    private static final String KEY_LEVEL_UP = "level_up";
    private static final String KEY_POINTS = "points";




    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAINQUESTS="CREATE TABLE " + TABLE_MAIN_QUEST + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_MAIN_QUEST_TITLE +" TEXT,"
                + KEY_MAIN_QUEST_DATE +" TEXT,"
                + KEY_MAIN_QUEST_TIME +" TEXT,"
                + KEY_SIDE_QUESTS_COMPLETED + " TEXT,"
                + KEY_MAIN_QUEST_COMPLETE + " TEXT,"
                + KEY_MAIN_QUEST_SIZE + " TEXT,"
                + KEY_MAIN_QUEST_PERCENT + " INTEGER,"
                + KEY_MAIN_QUEST_USERNAME + " TEXT,"
                + KEY_MAIN_QUEST_BOSS + " TEXT"
                + ")";

        String CREATE_TABLE_SIDEQUEST="CREATE TABLE " + TABLE_SIDE_QUEST + "("
                + KEY_SIDE_QUEST_ID +" INTEGER PRIMARY KEY,"
                + KEY_MAIN_QUEST_ID +" INTEGER,"
                + KEY_SIDE_QUEST_TITLE +" TEXT,"
                + KEY_SIDE_QUEST_CHECK + " TEXT,"
                + KEY_SIDE_QUEST_USERNAME + " TEXT"
                + ")";

        String CREATE_TABLE_USER_PROFILE = "CREATE TABLE " + TABLE_USER_PROFILE + "("
                + KEY_USER_PROFILE_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT,"
                + KEY_USER_PASSWORD + " TEXT,"
                + KEY_USER_LEVEL + " TEXT,"
                + KEY_USER_IMAGE + " TEXT,"
                + KEY_LEVEL_UP + " TEXT,"
                + KEY_POINTS + " TEXT"
                +")";

        db.execSQL(CREATE_TABLE_MAINQUESTS);
        db.execSQL(CREATE_TABLE_SIDEQUEST);
        db.execSQL(CREATE_TABLE_USER_PROFILE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN_QUEST);

        // Create tables again
        onCreate(db);
    }



    //CRUD
    //USER PROFILE TABLE METHODS
    public void createUserProfile(UserProfileObject user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        values.put(KEY_USER_LEVEL, 0);
        values.put(KEY_LEVEL_UP, 1);

        values.put(KEY_USER_IMAGE, user.getImage());
        values.put(KEY_POINTS, 0);

        db.insert(TABLE_USER_PROFILE, null, values);
        db.close();

    }

    public void saveLevel(int level, String userName, int levelUp, int points){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_USER_LEVEL, level);
        values.put(KEY_LEVEL_UP, levelUp);
        values.put(KEY_POINTS, points);
        db.update(TABLE_USER_PROFILE, values, KEY_USER_NAME + " =?", new String[]{userName});
    }


    public UserProfileObject getUserProfile() {
        String selectQuery = "SELECT  * FROM " + TABLE_USER_PROFILE;
        UserProfileObject userProfileObject = new UserProfileObject();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(0);
                String name = cursor.getString(1);
                String password = cursor.getString(2);
                String level = cursor.getString(3);
                String image = cursor.getString(4);
                String levelUp = cursor.getString(5);
                String points = cursor.getString(6);

                userProfileObject.setName(name);
                userProfileObject.setPassword(password);
                userProfileObject.setUserLevel(Integer.parseInt(level));
                userProfileObject.setImage(image);
                userProfileObject.set_id(_id);
                userProfileObject.setUserLevelUp(Integer.parseInt(levelUp));
                userProfileObject.setPoints(Integer.parseInt(points));

            } while (cursor.moveToNext());
        }else{
            userProfileObject.setName("null");
            userProfileObject.setPassword("null");
            userProfileObject.setUserLevel(-1);
            userProfileObject.setImage("null");
        }

        // return contact list
        return userProfileObject;
    }


    //MAIN QUEST TABLE METHODS
    public void addMainQuest(MainQuestObject mainQuest){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MAIN_QUEST_TITLE, mainQuest.getMainQuestTitle());
        values.put(KEY_MAIN_QUEST_DATE, mainQuest.getMainQuestDate());
        values.put(KEY_SIDE_QUESTS_COMPLETED, Integer.toString(mainQuest.getCompletedSideQuests()));

        values.put(KEY_MAIN_QUEST_SIZE, Integer.toString(mainQuest.getQuestSize()));

        values.put(KEY_MAIN_QUEST_COMPLETE, String.valueOf(mainQuest.isMainQuestComplete()));
        values.put(KEY_MAIN_QUEST_PERCENT, 0);
        values.put(KEY_MAIN_QUEST_USERNAME, mainQuest.getmUserName());

        values.put(KEY_MAIN_QUEST_BOSS, mainQuest.getmBossName());

        db.insert(TABLE_MAIN_QUEST, null, values);
        db.close();

    }

    public List<MainQuestObject> getAllMainQuests(boolean mainIsComplete, String user_Name) {
        List<MainQuestObject> mainQuestList = new ArrayList<MainQuestObject>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_MAIN_QUEST
                + " WHERE " +  KEY_MAIN_QUEST_COMPLETE + " = ?"
                + " AND " + KEY_MAIN_QUEST_USERNAME + " = ?";
        String isComplete = Boolean.toString(mainIsComplete);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{isComplete, user_Name});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                int completed = Integer.valueOf(cursor.getString(4));
                boolean isMainComplete = Boolean.parseBoolean(cursor.getString(5));
                int size = Integer.valueOf(cursor.getString(6));
                int percent = Integer.valueOf(cursor.getString(7));
                String userName = (cursor.getString(8));
                String bossName = (cursor.getString(9));


                MainQuestObject mainQuestObject =
                        new MainQuestObject(id,
                                title,
                                date,
                                time,
                                isMainComplete,
                                completed,
                                percent,
                                userName,
                                bossName);

                mainQuestList.add(mainQuestObject);
                Singleton.getInstance().getRelativeLayout().setVisibility(View.INVISIBLE);

                // Adding contact to list
            } while (cursor.moveToNext());
        }else {
            Singleton.getInstance().getRelativeLayout().setVisibility(View.VISIBLE);
            mainQuestList = new ArrayList<>();
        }
        db.close();
        return mainQuestList;
    }


    public void updateMainQuestComplete(boolean isComplete, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String stringIsChecked = String.valueOf(isComplete);

        values.put(KEY_MAIN_QUEST_COMPLETE, stringIsChecked);
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " + id, null);


    }
    public void updateMainQuestSize(int id, int size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_MAIN_QUEST_SIZE, size);
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " + id, null);

    }

    public void updateMainQuestDate(int id, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        Toast.makeText(Singleton.getInstance().getContext(), "Selected Date :"+ date, Toast.LENGTH_SHORT).show();

        values.put(KEY_MAIN_QUEST_DATE, date);
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " + id, null);

    }
    public void updateMainQuestTime(int id, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //String stringIsChecked = String.valueOf(isComplete);

        Toast.makeText(Singleton.getInstance().getContext(), "Selected Time :" + time, Toast.LENGTH_SHORT).show();

        values.put(KEY_MAIN_QUEST_TIME, time);
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " + id, null);

    }


    public void updateMainQuestPercent(int percent, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String stringIsChecked = String.valueOf(percent);
        //Toast.makeText(Singleton.getContext(), stringIsChecked, Toast.LENGTH_SHORT).show();

        values.put(KEY_MAIN_QUEST_PERCENT, stringIsChecked);
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " + id, null);


    }

    public void deleteMainQuest(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAIN_QUEST, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }



    //SIDE QUEST TABLE METHODS
    public void addSideQuest(SideQuestObject sideQuest){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_MAIN_QUEST_ID, sideQuest.getMainQuestId());
        values.put(KEY_SIDE_QUEST_TITLE, sideQuest.getSideQuestTitle());
        values.put(KEY_SIDE_QUEST_CHECK, String.valueOf(sideQuest.isSideQuestComplete()));
        values.put(KEY_SIDE_QUEST_USERNAME, sideQuest.getmUserName());

        db.insert(TABLE_SIDE_QUEST, null, values);
        db.close();
    }

    public List<SideQuestObject> getAllSideQuests(String id) {
        List<SideQuestObject> sideQuestList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_SIDE_QUEST + " WHERE " +  KEY_MAIN_QUEST_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

        if (cursor.moveToFirst()) {
            do {
                SideQuestObject sideQuestObject = new SideQuestObject();

                sideQuestObject.setSideQuestId(Integer.parseInt(cursor.getString(0)));
                sideQuestObject.setMainQuestId(cursor.getInt(1));
                sideQuestObject.setSideQuestTitle(cursor.getString(2));
                sideQuestObject.setSideQuestComplete(parseBoolean(cursor.getString(3)));
                sideQuestObject.setmUserName(cursor.getString(4));

                sideQuestList.add(sideQuestObject);

            } while (cursor.moveToNext());
        }else{
            sideQuestList = new ArrayList<>();
        }

        return sideQuestList;
    }

    public void updateSideCheck(boolean isChecked, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String stringIsChecked = String.valueOf(isChecked);

        values.put(KEY_SIDE_QUEST_CHECK, stringIsChecked);
        db.update(TABLE_SIDE_QUEST, values, KEY_SIDE_QUEST_ID + " = " +id, null);
    }

    public void addSideQuestsCompleted(int id, int add) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_SIDE_QUESTS_COMPLETED, Integer.toString(add + 1));
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " +id, null);

    }
    public void deleteSideQuestsCompleted(int id, int subtract) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();


        values.put(KEY_SIDE_QUESTS_COMPLETED, Integer.toString(subtract - 1));
        db.update(TABLE_MAIN_QUEST, values, KEY_MAIN_QUEST_ID + " = " +id, null);
    }



    public void deleteSideQuest(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SIDE_QUEST, KEY_SIDE_QUEST_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }
    public void deleteAllSideQuest(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SIDE_QUEST, KEY_MAIN_QUEST_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

}
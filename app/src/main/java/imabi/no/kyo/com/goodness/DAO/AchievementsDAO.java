package imabi.no.kyo.com.goodness.DAO;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import imabi.no.kyo.com.goodness.R;
import imabi.no.kyo.com.goodness.objects.Achievement;
import imabi.no.kyo.com.goodness.objects.Suggestion;

/**
 * Created by gui-wani on 08/11/2015.
 */
public class AchievementsDAO extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "Goodness.db";
    public final Context context;

    public AchievementsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    private Achievement parseSqlResponseToObject(Cursor cursor){
        Achievement achievement = new Achievement();
        achievement.setId(cursor.getLong(cursor.getColumnIndex("id")));
        achievement.setIconName(cursor.getString(cursor.getColumnIndex("icon_name")));
        achievement.setName(cursor.getString(cursor.getColumnIndex("name")));
        achievement.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        achievement.setUnlocked(cursor.getInt(cursor.getColumnIndex("unlocked")));
        achievement.setUnlocked(cursor.getInt(cursor.getColumnIndex("current_achievement")));
        return achievement;
    }

    public ArrayList<Achievement> getListAchivements(){
        ArrayList<Achievement> result = new ArrayList<Achievement>();
        Cursor cursor = getWritableDatabase().query("achievements", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            result.add(parseSqlResponseToObject(cursor));
        }
        cursor.close();
        return result;
    }

    public boolean hasSuggestionActive(){
        // make dbConnectionHelper consult after
        return getCurrentSuggestion() != null;
    }

    public ArrayList<Achievement> getAchievements() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM achievements;", null);
        ArrayList<Achievement> result = new ArrayList<Achievement>();
        while(cursor.moveToNext()){
            result.add(parseSqlResponseToObject(cursor));
        }
        return result;
    }

    public Achievement getCurrentSuggestion() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM achievements WHERE current_achievement = 1;", null);
        ArrayList<Achievement> result = new ArrayList<Achievement>();
        while(cursor.moveToNext()){
            result.add(parseSqlResponseToObject(cursor));
        }
        cursor.close();
        if(result.size() == 0){
            return null;
        } else {
            return result.get(0);
        }
    }

    public void resetAll(){
        ContentValues values =  new ContentValues();
        values.put("unlocked", 0);
        getWritableDatabase().update("achievements", values, null, null);
    }

    public Achievement getNewSuggestion() {

        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM achievements WHERE unlocked = 0;", null);
        ArrayList<Achievement> result = new ArrayList<Achievement>();
        while(cursor.moveToNext()){
            result.add(parseSqlResponseToObject(cursor));
        }
        cursor.close();
        if(result.size() == 0){
            return null;
        } else {
            Random rand = new Random();
            int index = rand.nextInt(result.size());
            Achievement achievement = result.get(index);
            achievement.setCurrentAchievement(1);
            updateSuggestion(achievement);
            return achievement;
        }
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(context.getResources().getString(R.string.create_database));
        createAchievementsList(db);
    }

    private void createAchievementsList(SQLiteDatabase db) {
        db.insert("achievements", null, (new Achievement(){{
            setId((long) 1);
            setIconName("test_icon");
            setName("Ajudar um idoso");
            setDescription("Ajude se puder uma senhora(o) atravessar a rua.");
        }}).toContentValues());

        db.insert("achievements", null, (new Achievement(){{
            setId((long) 2);
            setIconName("test_icon");
            setName("Ajudar um morador de rua");
            setDescription("Compre um lanche/água para um morador de rua. Se possível conheça sua história de vida");
        }}).toContentValues());
    }

    public void updateSuggestion(Achievement achievement){
        getWritableDatabase().update("achievements", achievement.toContentValues(), "id=?", new String[]{
            achievement.getId().toString()
        });
    }

    public void doneCurrentSuggestion() {
        Achievement currentSuggestion = getCurrentSuggestion();
        currentSuggestion.setUnlocked(1);
        currentSuggestion.setCurrentAchievement(0);
        updateSuggestion(currentSuggestion);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(context.getResources().getString(R.string.update_database));
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}

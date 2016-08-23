package kr.edcan.alime.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.Pair;

import kr.edcan.alime.models.User;

/**
 * Created by JunseokOh on 2016. 8. 16..
 */
public class DataManager {
    /* Data Keys */
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_ISADMIN = "user_isadmin";
    public static final String USER_ATTENDTYPE = "user_attendtype";
    public static final String HAS_ACTIVE_USER = "has_active_user";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    public DataManager instance;

    public DataManager() {
    }

    public void initializeManager(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences("Alime", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void save(String key, String data) {
        editor.putString(key, data);
        editor.apply();
    }
    public void save(String key, int data) {
        editor.putInt(key, data);
        editor.apply();
    }

    public void saveUser(User user) {
        editor.putBoolean(HAS_ACTIVE_USER, true);
        editor.putString(USER_NAME, user.getUsername());
        editor.putString(ID, user.getId());
        editor.putString(USER_ID, user.getUserid());
        editor.putBoolean(USER_ISADMIN, user.isAdmin());
        editor.putInt(USER_ATTENDTYPE, user.getAttendType());
        editor.apply();
    }

    public Pair<Boolean, User> getActiveUser() {
        if (preferences.getBoolean(HAS_ACTIVE_USER, false)) {
            int attendType = preferences.getInt(USER_ATTENDTYPE, -1);
            String id = preferences.getString(ID, "");
            String userid = preferences.getString(USER_ID, "");
            String username = preferences.getString(USER_NAME, "");
            boolean isAdmin = preferences.getBoolean(USER_ISADMIN, false);
            User user = new User(id, userid, username, isAdmin, attendType);
            return Pair.create(true, user);
        } else return Pair.create(false, null);
    }

    public void removeAllData() {
        editor.clear();
        editor.apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean isFirst() {
        return preferences.getBoolean("IS_FIRST", true);
    }

    public void notFirst() {
        editor.putBoolean("IS_FIRST", false);
        editor.apply();
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

}

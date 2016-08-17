package kr.edcan.alime.models;

/**
 * Created by JunseokOh on 2016. 8. 16..
 */
public class User {
    private String id, userid, username;
    private boolean isAdmin;
    private int attendType;

    public User(String id, String userid, String username, boolean isAdmin, int attendType) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.isAdmin = isAdmin;
        this.attendType = attendType;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getAttendType() {
        return attendType;
    }
}

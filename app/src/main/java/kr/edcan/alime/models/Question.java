package kr.edcan.alime.models;

import java.util.Date;

/**
 * Created by JunseokOh on 2016. 8. 16..
 */
public class Question {
    private String articleid, title, content, reply, author, password;
    private Date date;

    public Question(String articleid, String title, String content, String reply, String author, String password, Date date) {
        this.articleid = articleid;
        this.title = title;
        this.content = content;
        this.reply = reply;
        this.author = author;
        this.password = password;
        this.date = date;
    }

    public String getArticleid() {
        return articleid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getReply() {
        return reply;
    }

    public String getAuthor() {
        return author;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }
}

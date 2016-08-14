package kr.edcan.alime.models;

/**
 * Created by JunseokOh on 2016. 8. 14..
 */
public class PageList {
    private String title, href, date;

    public PageList(String title, String href, String date) {
        this.title = title;
        this.href = href;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public String getDate() {
        return date;
    }
}
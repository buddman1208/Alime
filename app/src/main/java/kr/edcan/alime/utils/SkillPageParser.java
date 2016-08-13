package kr.edcan.alime.utils;

import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by JunseokOh on 2016. 8. 12..
 */
public class SkillPageParser {

    final private String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48";
    final private String CSS_QUERY = "td>p>nobr>a";
    final private int TIMEOUT = 5000;
    final static public String defaultUrl= "http://skill.hrdkorea.or.kr/information/in001.action?&&page=";
    private Document skillNotice;

    public SkillPageParser(String documentUrl) {
    }

    public ArrayList<PageList> getNoticeList() {
        ArrayList<PageList> arrayList = new ArrayList<>();

    };

    class PageList {
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

    class DocumentGetter extends AsyncTask<String, Void, Document> {
        private String URL= "";
        public void setLink(String url) {
            this.URL = url;
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(url + currentPage)
                        .userAgent(USER_AGENT)
                        .timeout(TIMEOUT)
                        .get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

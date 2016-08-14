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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by JunseokOh on 2016. 8. 12..
 */
public class SkillPageParser {

    final private String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48";
    final private int TIMEOUT = 5000;
    final static public String defaultUrl = "http://skill.hrdkorea.or.kr/information/in001.action?&&page=";
    private Document skillNotice;
    private DocumentGetter getter;

    public SkillPageParser() {
        getter = new DocumentGetter();
    }

    public ArrayList<PageList> getNoticeList(int currentPage) {
        ArrayList<PageList> resultArr = new ArrayList<>();
        Document noticeDoc;

        try {
            noticeDoc = getter.execute(defaultUrl + currentPage).get();
        } catch (InterruptedException e) {
            Log.e("asdf", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            Log.e("asdf", e.getMessage());
            e.printStackTrace();
            return null;
        }
        Elements titles = noticeDoc.select("td.title>p>nobr>a");
        Elements date = noticeDoc.select("tr>td");
        for (int i = 0; i < titles.size(); i++) {
            resultArr.add(new PageList(titles.get(i).text(), date.get(i * 4).text(), titles.get(i).absUrl("href")));
            Log.e("asdf", titles.get(i).absUrl("href"));
        }
        return resultArr;
    }

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

    class DocumentGetter extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... params) {
            String url = params[0];
            try {
                Document doc = Jsoup.connect(url)
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

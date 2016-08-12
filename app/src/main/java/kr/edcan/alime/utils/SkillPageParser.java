package kr.edcan.alime.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by JunseokOh on 2016. 8. 12..
 */
public class SkillPageParser {

    final private String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48";
    final private String CSS_QUERY = "td>p>nobr>a";
    final private int TIMEOUT = 5000;
    public String url = "http://skill.hrdkorea.or.kr/information/in001.action?&&page=";
    private int currentPage = 0;
    private Document skillNotice;

    public SkillPageParser() {
        currentPage = 1;
    }

    class NetworkParsingTask extends AsyncTask<String, Void, String> {1
        @Override
        protected String doInBackground(String... params) {
            try {
                skillNotice = Jsoup.connect(url + currentPage)
                        .userAgent(USER_AGENT)
                        .timeout(TIMEOUT)
                        .get();
                Elements elements = skillNotice.select(CSS_QUERY);
                for (Element e : elements) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            return title;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}

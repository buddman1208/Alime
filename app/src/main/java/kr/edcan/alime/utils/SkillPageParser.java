package kr.edcan.alime.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import kr.edcan.alime.models.PageList;

/**
 * Created by JunseokOh on 2016. 8. 12..
 */
public class SkillPageParser {

    final private String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48";
    final private int TIMEOUT = 5000;
    final static public String defaultUrl = "http://skill.hrdkorea.or.kr/information/in001.action?&&page=";
    private Document skillNotice;
    private Context context;
    private int currentPage = 0;

    public SkillPageParser(Context c) {
        this.context = c;
    }

    public Pair<Integer, ArrayList<PageList>> getNoticeList(int currentPage) {
        this.currentPage = currentPage;
        ArrayList<PageList> resultArr = new ArrayList<>();
        Document noticeDoc;
        int maxSize;
        try {
            noticeDoc = new DocumentGetter().execute(defaultUrl + currentPage).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        try {
            int maxItem = Integer.parseInt(noticeDoc.select("p>b").first().text());
            maxSize = (maxItem / 10) + ((maxItem % 10 == 0) ? 0 : 1);
        } catch (Exception e) {
            Toast.makeText(context, "공지사항을 받아오는 중에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
        Elements titles = noticeDoc.select("td.title>p>nobr>a");
        Elements date = noticeDoc.select("tr>td");
        for (int i = 0; i < titles.size(); i++) {
            resultArr.add(new PageList(titles.get(i).text(), titles.get(i).absUrl("href"), date.get(i * 4).text()));
        }
        return Pair.create(maxSize, resultArr);
    }


    public ArrayList<String> getNoticeContent(String url) {
        ArrayList<String> noticeContent = new ArrayList<>();
        Document noticeDoc;
        try {
            noticeDoc= new DocumentGetter().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        Elements contents = noticeDoc.select("tr>td");
        for (Element e : contents) {
            noticeContent.add(e.text());
        }
        return noticeContent;
    }


    class DocumentGetter extends AsyncTask<String, Void, Document> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            if (currentPage == 0) {
                dialog = new ProgressDialog(context);
                dialog.setTitle("공지사항을 가져오고 있습니다.");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                super.onPreExecute();
            }
        }

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

        @Override
        protected void onPostExecute(Document document) {
            if (currentPage == 0) {
                dialog.dismiss();
                super.onPreExecute();
            }
            super.onPostExecute(document);
        }
    }
}

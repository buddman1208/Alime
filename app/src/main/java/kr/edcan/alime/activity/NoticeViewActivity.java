package kr.edcan.alime.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.ActivityNoticeViewBinding;
import kr.edcan.alime.utils.AlimeUtils;
import kr.edcan.alime.utils.SkillPageParser;

public class NoticeViewActivity extends AppCompatActivity {

    String pageUrl;
    SkillPageParser parser;
    ArrayList<String> pageContent;
    MaterialDialog loading;
    ActivityNoticeViewBinding noticeBinding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeBinding = DataBindingUtil.setContentView(this, R.layout.activity_notice_view);
        setDefault();
        setData();
    }

    private void setDefault() {
        parser = new SkillPageParser(this);
        intent = getIntent();
        pageUrl = intent.getStringExtra("url");
        loading = new MaterialDialog.Builder(this)
                .title("로딩중입니다.")
                .progress(true, 0)
                .show();
        setSupportActionBar(noticeBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pageContent = parser.getNoticeContent(pageUrl);
        loading.dismiss();
    }

    private void setData() {
        noticeBinding.questionTitle.setPrimaryText(pageContent.get(0));
        noticeBinding.questionTitle.setSubText(pageContent.get(1));
        noticeBinding.questionContent.setText(pageContent.get(3));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

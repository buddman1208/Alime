package kr.edcan.alime.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.ActivityNoticeViewBinding;
import kr.edcan.alime.databinding.ActivityQuestionViewBinding;
import kr.edcan.alime.utils.DataManager;
import kr.edcan.alime.utils.NetworkHelper;
import kr.edcan.alime.utils.NetworkInterface;
import kr.edcan.alime.utils.SkillPageParser;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionViewActivity extends AppCompatActivity {

    String pageUrl;
    SkillPageParser parser;
    ArrayList<String> pageContent;
    MaterialDialog loading;
    String noticeid;
    ActivityQuestionViewBinding noticeBinding;
    DataManager manager;
    Toolbar toolbar;
    Intent intent;
    NetworkInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_view);
        manager = new DataManager();
        manager.initializeManager(this);
        service = NetworkHelper.getNetworkInstance();
        setDefault();
    }

    private void setDefault() {
        intent = getIntent();
        String reply = intent.getStringExtra("reply");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noticeBinding.questionTitle.setPrimaryText(intent.getStringExtra("title"));
        noticeBinding.questionTitle.setSubText(intent.getStringExtra("date"));
        noticeid = intent.getStringExtra("noticeid");
        noticeBinding.questionContent.setText(intent.getStringExtra("content"));
        noticeBinding.questionReply.setVisibility(View.VISIBLE);
        noticeBinding.questionReply.setSubText((reply.equals("") ? "관리자로부터 등록된 답변이 없습니다." : reply));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (manager.getActiveUser().second.isAdmin())
            getMenuInflater().inflate(R.menu.question_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.answer:
                final EditText view = (EditText) View.inflate(getApplicationContext(), R.layout.custom_dialog, null);
                new MaterialDialog.Builder(this)
                        .title("답변하기")
                        .positiveColor(getResources().getColor(R.color.colorPrimary))
                        .customView(view, true)
                        .positiveText("확인")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (!view.getText().toString().trim().equals("")) {
                                    Call<ResponseBody> reply = service.replyQuestion(
                                            manager.getActiveUser().second.getId(),
                                            noticeid,
                                            view.getText().toString().trim()
                                    );
                                    reply.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            Log.e("asdf", response.code() + "");
                                            switch (response.code()) {
                                                case 200:
                                                    Toast.makeText(QuestionViewActivity.this, "답변이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Log.e("asdf", t.getMessage());
                                            Toast.makeText(QuestionViewActivity.this, "서버와의 통신에 문제가있습니다\n관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else
                                    Toast.makeText(QuestionViewActivity.this, "공백 없이 입력해주세요!", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

package kr.edcan.alime.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import kr.edcan.alime.R;
import kr.edcan.alime.models.Question;
import kr.edcan.alime.utils.DataManager;
import kr.edcan.alime.utils.NetworkHelper;
import kr.edcan.alime.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewQuestion extends AppCompatActivity {

    Button post;
    EditText title, content;
    NetworkInterface service;
    DataManager manager;
    Toolbar toolbar;
    Call<Question> newQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        setDefault();
    }

    private void setDefault() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        manager = new DataManager();
        manager.initializeManager(this);
        post= (Button) findViewById(R.id.post);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        service = NetworkHelper.getNetworkInstance();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQuestion = service.postQuestion(title.getText().toString().trim(), new Date(System.currentTimeMillis()),
                        content.getText().toString().trim(), manager.getActiveUser().second.getId(), "");
                newQuestion.enqueue(new Callback<Question>() {
                    @Override
                    public void onResponse(Call<Question> call, Response<Question> response) {
                        switch (response.code()){
                            case 200:
                                Toast.makeText(NewQuestion.this, "질문이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                            case 400:
                                Toast.makeText(NewQuestion.this, "문제가 발생했습니다 관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Question> call, Throwable t) {
                        Log.e("asdf", t.getMessage());
                        Toast.makeText(NewQuestion.this, "문제가 발생했습니다 관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

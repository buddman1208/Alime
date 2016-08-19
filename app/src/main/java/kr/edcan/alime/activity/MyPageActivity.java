package kr.edcan.alime.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.MainCommonListviewContentBinding;
import kr.edcan.alime.models.PageList;

public class MyPageActivity extends AppCompatActivity {


    ListView listView;
    ArrayList<String> listArr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        setDefault();
    }

    private void setDefault() {
        listView = (ListView) findViewById(R.id.myPageListView);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        arrayAdapter.add("로그아웃");
        arrayAdapter.add("회원탈퇴");
        listView.setAdapter(arrayAdapter);
    }
}

package kr.edcan.alime.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.MainCommonListviewContentBinding;
import kr.edcan.alime.models.PageList;
import kr.edcan.alime.models.User;
import kr.edcan.alime.utils.DataManager;

public class MyPageActivity extends AppCompatActivity {

    Pair<Boolean, User> activeUser;
    ListView listView;
    DataManager manager;
    ArrayList<String> listArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        setDefault();
    }

    private void setDefault() {
        manager = new DataManager();
        manager.initializeManager(getApplicationContext());
        activeUser = manager.getActiveUser();
        listView = (ListView) findViewById(R.id.myPageListView);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        arrayAdapter.add(activeUser.first ? "로그아웃" : "로그인");
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (activeUser.first) {
                            manager.removeAllData();
                            recreate();
                        } else {
                            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        setDefault();
        super.onResume();
    }
}

package kr.edcan.alime.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.ActivityMainBinding;
import kr.edcan.alime.databinding.MainMainboardBinding;
import kr.edcan.alime.databinding.MainNoticeBinding;
import kr.edcan.alime.databinding.MainPrizeBinding;
import kr.edcan.alime.databinding.MainQuestionBinding;
import kr.edcan.alime.utils.SkillPageParser;

public class MainActivity extends AppCompatActivity {

    int currentNoticePage = 0;
    int maxNoticePage = 0;
    String[] titleArr = new String[]{"메인 보드", "공지사항", "질문과 답변", "시상 및 특전"};
    ActivityMainBinding mainBind;
    ViewPager mainPager;
    DrawerLayout mainDrawer;
    TabLayout mainTabLayout;
    LinearLayout mainToolbar;

    SkillPageParser parser;
    ArrayList<SkillPageParser.PageList> pageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loadDataFromServer();
    }

    private void setDefault() {
        // Widgets
        mainPager = mainBind.mainViewPager;
        mainTabLayout = mainBind.mainTabLayout;
        mainToolbar = mainBind.mainToolbar;
        mainDrawer = mainBind.mainDrawer;

        mainPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mainTabLayout.setupWithViewPager(mainPager);
        mainBind.mainDrawerToggle.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        mainBind.mainDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadDataFromServer() {
        parser = new SkillPageParser(MainActivity.this);
        pageList = parser.getNoticeList(currentNoticePage);
        setDefault();
    }

    public static class MainFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "pageNumber";
        int layout[] = new int[]{R.layout.main_mainboard, R.layout.main_notice, R.layout.main_question, R.layout.main_prize};
        ListView noticeListView, QNAListView;
        RecyclerView prizeView;

        public static MainFragment newInstance(int pageNum) {
            Bundle args = new Bundle();
            MainFragment fragment = new MainFragment();
            args.putInt(ARG_SECTION_NUMBER, pageNum);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final int position = getArguments().getInt(ARG_SECTION_NUMBER);
            View view = DataBindingUtil.inflate(inflater, layout[position], container, false).getRoot();
            setPage(view, position);
            return view;
        }

        public void setPage(View view, int position) {
            switch (position) {
                case 0:

                    break;
                case 1:
                    break;
                case 2:

                    break;
                case 3:

                    break;
            }
        }
    }


    public class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position < titleArr.length)
                return titleArr[position];
            return null;
        }
    }
}

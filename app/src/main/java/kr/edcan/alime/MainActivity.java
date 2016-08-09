package kr.edcan.alime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kr.edcan.alime.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    String[] titleArr = new String[]{"메인 보드", "공지사항", "질문과 답변", "시상 및 특전"};
    ActivityMainBinding mainBind;
    ViewPager mainPager;
    TabLayout mainTabLayout;
    LinearLayout mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setDefault();
    }

    private void setDefault() {
        // Widgets
        mainPager = mainBind.mainViewPager;
        mainTabLayout = mainBind.mainTabLayout;
        mainToolbar = mainBind.mainToolbar;

        mainPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mainTabLayout.setupWithViewPager(mainPager);
    }

    public static class MainFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "pageNumber";

        public static MainFragment newInstance(int pageNum) {
            Bundle args = new Bundle();
            MainFragment fragment = new MainFragment();
            args.putInt(ARG_SECTION_NUMBER, pageNum);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            int layout[] = new int[]{R.layout.main_mainboard, R.layout.main_notice, R.layout.main_question, R.layout.main_prize};
            final int position = getArguments().getInt(ARG_SECTION_NUMBER);
            View view = inflater.inflate(layout[position], container, false);
            return view;
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

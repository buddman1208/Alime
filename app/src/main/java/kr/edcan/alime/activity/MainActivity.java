package kr.edcan.alime.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.adapters.NoticeListAdapter;
import kr.edcan.alime.adapters.PrizeRecyclerView;
import kr.edcan.alime.databinding.ActivityMainBinding;
import kr.edcan.alime.models.PageList;
import kr.edcan.alime.models.PrizeData;
import kr.edcan.alime.utils.SkillPageParser;

public class MainActivity extends AppCompatActivity {

    static int currentNoticePage = 1;
    static int maxNoticePage = 1;
    String[] titleArr = new String[]{"메인 보드", "공지사항", "질문과 답변", "시상 및 특전"};
    ActivityMainBinding mainBind;
    ViewPager mainPager;
    DrawerLayout mainDrawer;
    TabLayout mainTabLayout;
    LinearLayout mainToolbar;

    static SkillPageParser parser;
    static ArrayList<PageList> pageList;

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
        Pair<Integer, ArrayList<PageList>> pair = parser.getNoticeList(currentNoticePage);
        maxNoticePage = pair.first;
        pageList = pair.second;
        setDefault();
    }


    public static class MainFragment extends Fragment {
        int layout[] = new int[]{R.layout.main_mainboard, R.layout.main_notice, R.layout.main_question, R.layout.main_prize};
        boolean mLockListView = false;
        static final String ARG_SECTION_NUMBER = "pageNumber";
        static NoticeListAdapter noticeAdapter;
        LayoutInflater inflater;
        ListView noticeListView, QNAListView;
        Context context;
        RecyclerView prizeView;
        ArrayList<PrizeData> prizeArr = new ArrayList<>();

        public static MainFragment newInstance(int pageNum) {
            Bundle args = new Bundle();
            MainFragment fragment = new MainFragment();
            args.putInt(ARG_SECTION_NUMBER, pageNum);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            prizeArr.add(new PrizeData("1위", "금메달", "1200", "노동부장관상"));
            prizeArr.add(new PrizeData("2위", "은메달", "800", "대회장상"));
            prizeArr.add(new PrizeData("3위", "동메달", "400", "대회장상"));
            prizeArr.add(new PrizeData("우수상", "", "100", "대회장상 - 우수상 대상자 중 최상위"));
            prizeArr.add(new PrizeData("우수상", "", "70", "대회장상 - 우수상 대상자 중 차상위"));
            prizeArr.add(new PrizeData("우수상", "", "50", "대회장상 - 우수상 대상자 중 최하위"));
            final int position = getArguments().getInt(ARG_SECTION_NUMBER);
            this.inflater = inflater;
            this.context = container.getContext();
            View view = DataBindingUtil.inflate(inflater, layout[position], container, false).getRoot();
            setPage(view, position);
            return view;
        }

        public void setPage(View view, int position) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    noticeListView = (ListView) view.findViewById(R.id.mainNoticeListView);
                    noticeAdapter = new NoticeListAdapter(context, pageList);
                    noticeListView.setAdapter(noticeAdapter);
                    noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            startActivity(new Intent(getContext(), NoticeViewActivity.class)
                            .putExtra("url", pageList.get(i).getHref()));
                        }
                    });
                    noticeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView absListView, int i) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            int count = totalItemCount - visibleItemCount;
                            if (firstVisibleItem >= count && totalItemCount != 0 && !mLockListView) {
                                noticeListView.smoothScrollToPosition(pageList.size());
                                appendNoticeData();
                            }
                        }

                    });
                    break;
                case 2:

                    break;
                case 3:
                    prizeView = (RecyclerView) view.findViewById(R.id.mainPrizeRecyclerView);
                    prizeView.setLayoutManager(new LinearLayoutManager(getContext()));
                    prizeView.setAdapter(new PrizeRecyclerView(getContext(), prizeArr));
                    break;
            }
        }

        public void appendNoticeData() {
            mLockListView = true;
            if (currentNoticePage + 1 <= maxNoticePage) {
                currentNoticePage++;
                pageList.addAll(parser.getNoticeList(currentNoticePage).second);
                noticeAdapter.notifyDataSetChanged();
            }
            mLockListView = false;
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

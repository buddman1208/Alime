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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kr.edcan.alime.R;
import kr.edcan.alime.adapters.NoticeListAdapter;
import kr.edcan.alime.adapters.PrizeRecyclerView;
import kr.edcan.alime.adapters.QuestionListAdapter;
import kr.edcan.alime.databinding.ActivityMainBinding;
import kr.edcan.alime.models.PageList;
import kr.edcan.alime.models.PrizeData;
import kr.edcan.alime.models.Question;
import kr.edcan.alime.utils.AlimeUtils;
import kr.edcan.alime.utils.DataManager;
import kr.edcan.alime.utils.ImageSingleton;
import kr.edcan.alime.utils.NetworkHelper;
import kr.edcan.alime.utils.NetworkInterface;
import kr.edcan.alime.utils.SkillPageParser;
import kr.edcan.alime.views.CartaDoubleTextView;
import kr.edcan.alime.views.CartaTagView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static int currentNoticePage = 1;
    static int maxNoticePage = 1;
    String[] titleArr = new String[]{"메인 보드", "공지사항", "질문과 답변", "시상 및 특전"};
    ActivityMainBinding mainBind;
    static public ViewPager mainPager;
    DrawerLayout mainDrawer;
    TabLayout mainTabLayout;

    DataManager manager;
    static SkillPageParser parser;
    static ArrayList<PageList> pageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void setDefault() {
        // Widgets
        mainPager = mainBind.mainViewPager;
        mainTabLayout = mainBind.mainTabLayout;
        setSupportActionBar(mainBind.toolbar);
        manager = new DataManager();
        manager.initializeManager(this);
        mainPager.setCurrentItem(manager.getInt("currentPage"));
        mainPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mainTabLayout.setupWithViewPager(mainPager);
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
        ArrayList<PrizeData> prizeArr;
        DataManager manager;

        public static MainFragment newInstance(int pageNum) {
            Bundle args = new Bundle();
            MainFragment fragment = new MainFragment();
            args.putInt(ARG_SECTION_NUMBER, pageNum);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            prizeArr = new ArrayList<>();
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
            manager = new DataManager();
            manager.initializeManager(getContext());
            setPage(view, position);
            return view;
        }

        public void setPage(View view, int position) {
            switch (position) {
                case 0:
                    TextView time = (TextView) view.findViewById(R.id.mainDashboardTime);
                    Date date = new Date(System.currentTimeMillis());
                    time.setText(((date.getHours() < 10) ? "0" + date.getHours() : date.getHours()) + ":" + ((date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes()));
                    CartaDoubleTextView name, type, timeinfo;
                    timeinfo = (CartaDoubleTextView) view.findViewById(R.id.mainDashboardTimeInfo);
                    Calendar c = Calendar.getInstance();
                    timeinfo.setPrimaryText(((c.AM_PM) == Calendar.AM) ? "AM" : "PM");
                    name = (CartaDoubleTextView) view.findViewById(R.id.mainDashboardUsername);
                    type = (CartaDoubleTextView) view.findViewById(R.id.mainDashboardCategory);
                    if (manager.getActiveUser().first) {
                        timeinfo.setSubText(AlimeUtils.getType()[manager.getActiveUser().second.getAttendType()] + " 분야");
                        name.setSubText(manager.getActiveUser().second.getUsername());
                        type.setSubText(
                                (manager.getActiveUser().second.isAdmin())
                                        ? "관리자"
                                        : AlimeUtils.getType()[manager.getActiveUser().second.getAttendType()]
                        );
                    }
                    RelativeLayout soochick, goodinfo;
                    soochick = (RelativeLayout) view.findViewById(R.id.soochick);
                    goodinfo = (RelativeLayout) view.findViewById(R.id.goodinfo);
                    soochick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getContext(), SoochickActivity.class));
                        }
                    });
                    goodinfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mainPager.setCurrentItem(2, true);
                        }
                    });
                    NetworkImageView im1, im2, im3;
                    im1 = (NetworkImageView) view.findViewById(R.id.network1);
                    im2 = (NetworkImageView) view.findViewById(R.id.network2);
                    im3 = (NetworkImageView) view.findViewById(R.id.network3);
                    im1.setImageUrl("http://skill.hrdkorea.or.kr/servlet/image/compet_picture/201508311623001441005780598.jpg", ImageSingleton.getInstance(context).getImageLoader());
                    im2.setImageUrl("http://skill.hrdkorea.or.kr/servlet/image/compet_picture/201508311622321441005752596.jpg", ImageSingleton.getInstance(context).getImageLoader());
                    im3.setImageUrl("http://skill.hrdkorea.or.kr/servlet/image/compet_picture/201508311631291441006289261.jpg", ImageSingleton.getInstance(context).getImageLoader());
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
                    CartaTagView post = (CartaTagView) view.findViewById(R.id.mainQNAConfirm);
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getContext(), NewQuestion.class));
                        }
                    });
                    final ListView listView = (ListView) view.findViewById(R.id.mainQNAListView);
                    final ArrayList<Question> arr = new ArrayList<>();
                    NetworkInterface service = NetworkHelper.getNetworkInstance();
                    TextView loginRequest = (TextView) view.findViewById(R.id.questionLoginRequest);
                    loginRequest.setVisibility((manager.getActiveUser().first) ? View.GONE : View.VISIBLE);
                    if (manager.getActiveUser().first) {
                        if (manager.getActiveUser().second.isAdmin()) post.setVisibility(View.GONE);
                        final Call<List<Question>> questionList = service.listQuestion();
                        questionList.enqueue(new Callback<List<Question>>() {
                            @Override
                            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                                switch (response.code()) {
                                    case 200:
                                        for (Question q : response.body()) {
                                            arr.add(q);
                                        }
                                        if (arr.size() == 0)
                                            Toast.makeText(getContext(), "등록된 질문이 없습니다", Toast.LENGTH_SHORT).show();
                                        listView.setAdapter(new QuestionListAdapter(getContext(), arr));
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Question>> call, Throwable t) {
                                Log.e("asdf", t.getMessage());
                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                startActivity(new Intent(getContext(), QuestionViewActivity.class)
                                        .putExtra("title", arr.get(i).getTitle())
                                        .putExtra("date", arr.get(i).getDate().toLocaleString())
                                        .putExtra("content", arr.get(i).getContent())
                                        .putExtra("noticeid", arr.get(i).getArticleid())
                                        .putExtra("reply", arr.get(i).getReply()));
                            }
                        });
                    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myPage:
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        manager.save("currentPage", mainPager.getCurrentItem());
        super.onPause();
    }

    @Override
    protected void onResume() {
        loadDataFromServer();
        super.onResume();
    }
}

package kr.edcan.alime.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.databinding.MainCommonListviewContentBinding;
import kr.edcan.alime.models.PageList;
import kr.edcan.alime.models.Question;

/**
 * Created by JunseokOh on 2016. 8. 14..
 */
public class QuestionListAdapter extends ArrayAdapter<Question> {
    ArrayList<Question> arrayList;
    LayoutInflater inflater;
    public QuestionListAdapter(Context context, ArrayList<Question> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question data = arrayList.get(position);
        MainCommonListviewContentBinding listBind = DataBindingUtil.inflate(inflater, R.layout.main_common_listview_content, parent, false);
        listBind.commonDoubleText.setPrimaryText(data.getTitle());
        listBind.commonDoubleText.setSubText(data.getDate().toLocaleString() + " - "+data.getAuthor());
        return listBind.getRoot();
    }
}

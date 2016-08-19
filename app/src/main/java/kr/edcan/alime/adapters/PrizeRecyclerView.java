package kr.edcan.alime.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.edcan.alime.R;
import kr.edcan.alime.models.PrizeData;

/**
 * Created by JunseokOh on 2016. 8. 19..
 */
public class PrizeRecyclerView extends RecyclerView.Adapter<PrizeRecyclerView.ViewHolder> {

    Context context;
    ArrayList<PrizeData> arrayList;
    PrizeData data;

    public PrizeRecyclerView(Context context, ArrayList<PrizeData> items) {
        this.context = context;
        this.arrayList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_prize_recycler_content, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int bg[] = {R.drawable.prize_bg_first, R.drawable.prize_bg_second, R.drawable.prize_bg_third, R.drawable.prize_bg_common};
        data = arrayList.get(position);
        int commonColor = context.getResources().getColor(R.color.notSelectedTextColor);
        holder.prizeName.setText(data.getPrizeName());
        holder.medalName.setText(data.getMedalName());
        holder.prizeMoney.setText(data.getPrizeMoneyCount());
        holder.content.setText(data.getContent());
        Log.e("asdf", data.getContent());
        holder.bg.setBackgroundResource(bg[getPrizeBgPosition(position)]);
        if (!data.getPrizeName().contains("위")) holder.prizeMoneyUnit.setVisibility(View.GONE);
        if (data.getMedalName().equals("")) holder.medalName.setVisibility(View.GONE);
        if (getPrizeBgPosition(position) > 2) {
            holder.divider.setVisibility(View.GONE);
            holder.prizeName.setTextColor(commonColor);
            holder.prizeMoneyUnit.setTextColor(commonColor);
            holder.prizeMoney.setTextColor(commonColor);
            holder.prizeSubname.setTextColor(commonColor);
        }
    }

    public int getPrizeBgPosition(int prize) {
        return (prize > 2) ? 3 : prize;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prizeName, prizeSubname, medalName, prizeMoney, prizeMoneyUnit, content;
        RelativeLayout bg;
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            prizeName = (TextView) itemView.findViewById(R.id.prizeContentNumber);
            prizeSubname = (TextView) itemView.findViewById(R.id.prizeContentNumberText);
            medalName = (TextView) itemView.findViewById(R.id.prizeContentPrizeName);
            prizeMoney = (TextView) itemView.findViewById(R.id.prizeContentMoneyCount);
            prizeMoneyUnit = (TextView) itemView.findViewById(R.id.prizeContentMoneyValue);
            content = (TextView) itemView.findViewById(R.id.prizeContentSubName);
            bg = (RelativeLayout) itemView.findViewById(R.id.prizeBG);
            divider = itemView.findViewById(R.id.prizeDivider);
        }
    }
}
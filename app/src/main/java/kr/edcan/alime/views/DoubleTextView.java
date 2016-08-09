package kr.edcan.alime.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.edcan.alime.R;

/**
 * Created by JunseokOh on 2016. 8. 9..
 */
public class DoubleTextView extends LinearLayout {

    Context c;
    String primaryText, subText;
    int primaryColor, subColor;
    float mainTextSize, subTextSize;
    TextView mainTextView, subTextView;
    LayoutParams subParam;
    Resources res;

    public DoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        res = context.getResources();
        setView();
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DoubleTextView);
        setTypedArray(array);
    }

    private void setTypedArray(TypedArray array) {
        primaryText = array.getString(R.styleable.DoubleTextView_mainText);
        subText = array.getString(R.styleable.DoubleTextView_subText);
        primaryColor = array.getColor(R.styleable.DoubleTextView_mainColor, res.getColor(R.color.notSelectedTextColor));
        subColor = array.getColor(R.styleable.DoubleTextView_subColor, Color.WHITE);
        mainTextSize = array.getDimension(R.styleable.DoubleTextView_mainTextSize, 18);
        subTextSize = array.getDimension(R.styleable.DoubleTextView_subTextSize, 25);

        mainTextView.setText(primaryText);
        mainTextView.setTextColor(primaryColor);
        mainTextView.setTextSize(mainTextSize);
        subTextView.setText(subText);
        subTextView.setTextColor(subColor);
        subTextView.setTextSize(subTextSize);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setView();
        super.onDraw(canvas);
    }

    private void setView() {
        mainTextView = new TextView(c);
        subTextView = new TextView(c);
        mainTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        subParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subParam.setMargins(0, 5, 0, 0);
        subTextView.setLayoutParams(subParam);
        subTextView.setTypeface(null, Typeface.BOLD);
        addView(mainTextView);
        addView(subTextView);
    }

    public String getSubText() {
        return subTextView.getText().toString();
    }

    public String getPrimaryText() {
        return mainTextView.getText().toString();
    }

    public void setSubText(String subText) {
        subTextView.setText(subText);
    }

    public void setPrimaryText(String primaryText) {
        mainTextView.setText(primaryText);
    }
}

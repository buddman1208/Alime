package kr.edcan.alime.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.edcan.alime.R;

/**
 * Created by JunseokOh on 2016. 8. 9..
 */
public class CartaDoubleTextView extends LinearLayout {

    Context c;
    String primaryText, subText;
    int primaryColor, subColor, topMargin;
    float mainTextSize, subTextSize;
    TextView mainTextView, subTextView;
    LayoutParams subParam;
    Resources res;

    public CartaDoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        res = context.getResources();
        setView();
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CartaDoubleTextView);
        setTypedArray(array);
    }

    private void setTypedArray(TypedArray array) {
        primaryText = array.getString(R.styleable.CartaDoubleTextView_mainText);
        subText = array.getString(R.styleable.CartaDoubleTextView_subText);
        primaryColor = array.getColor(R.styleable.CartaDoubleTextView_mainColor, res.getColor(R.color.notSelectedTextColor));
        subColor = array.getColor(R.styleable.CartaDoubleTextView_subColor, Color.WHITE);
        mainTextSize = array.getDimension(R.styleable.CartaDoubleTextView_mainTextSize, 55);
        subTextSize = array.getDimension(R.styleable.CartaDoubleTextView_subTextSize, 75);
        topMargin = array.getLayoutDimension(R.styleable.CartaDoubleTextView_textMargin, 10);

        mainTextView.setText(primaryText);
        mainTextView.setTextColor(primaryColor);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mainTextSize);
        subTextView.setText(subText);
        subTextView.setTextColor(subColor);
        subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTextSize);
        subParam.setMargins(0, topMargin, 0, 0);
        subTextView.setLayoutParams(subParam);
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

    public void setPrimaryColor(int primaryColor) {
        mainTextView.setTextColor(primaryColor);
    }

    public void setSubColor(int subColor) {
        subTextView.setTextColor(subColor);
    }
}

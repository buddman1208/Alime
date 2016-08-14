package kr.edcan.alime.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.edcan.alime.R;

/**
 * Created by JunseokOh on 2016. 8. 9..
 */
public class CartaDoubleTextView extends LinearLayout {

    Context c;
    String primaryText, subText;
    float pxConvert;
    int primaryColor, subColor, topMargin;
    boolean mainSingleLine, subSingleLine;
    boolean mainBold, subBold;
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
        pxConvert = res.getDisplayMetrics().density;
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
        mainTextSize = array.getDimensionPixelSize(R.styleable.CartaDoubleTextView_mainTextSize, 30) / pxConvert;
        subTextSize = array.getDimensionPixelSize(R.styleable.CartaDoubleTextView_subTextSize, 40) / pxConvert;
        topMargin = array.getLayoutDimension(R.styleable.CartaDoubleTextView_textMargin, 10);
        mainSingleLine = array.getBoolean(R.styleable.CartaDoubleTextView_mainSingleLine, true);
        subSingleLine = array.getBoolean(R.styleable.CartaDoubleTextView_subSingleLine, false);
        mainBold = array.getBoolean(R.styleable.CartaDoubleTextView_mainBold, false);
        subBold = array.getBoolean(R.styleable.CartaDoubleTextView_subBold, true);

        mainTextView.setText(primaryText);
        mainTextView.setTextColor(primaryColor);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mainTextSize);
        subTextView.setText(subText);
        subTextView.setTextColor(subColor);
        subTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, subTextSize);
        subParam.setMargins(0, topMargin, 0, 0);
        subTextView.setLayoutParams(subParam);
        if (mainSingleLine) {
            mainTextView.setSingleLine();
            mainTextView.setEllipsize(TextUtils.TruncateAt.END);
        }
        if (subSingleLine) subTextView.setSingleLine();
        if (mainBold) mainTextView.setTypeface(null, Typeface.BOLD);
        if (subBold) subTextView.setTypeface(null, Typeface.BOLD);
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
        mainTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        subParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
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

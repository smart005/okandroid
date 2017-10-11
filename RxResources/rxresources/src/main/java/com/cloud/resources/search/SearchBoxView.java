package com.cloud.resources.search;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-9 下午10:00:53
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class SearchBoxView extends LinearLayout {

    private OnSearchListener mSearchListener = null;
    private String hint = "";
    private int hintTextColor = 0;
    private boolean searchEnabled = true;

    public SearchBoxView(Context context) {
        super(context);
        init(false, null);
    }

    public SearchBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true, attrs);
    }

    /**
     * @param 设置OnSearchListener
     */
    public void setOnSearchListener(OnSearchListener mSearchListener) {
        this.mSearchListener = mSearchListener;
    }

    private void init(boolean flag, AttributeSet attrs) {
        try {
            int backgroundRes = R.drawable.search_box_def_bg;
            if (flag) {
                TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SearchBoxView);
                hint = a.getString(R.styleable.SearchBoxView_searchHint);
                hintTextColor = a.getColor(R.styleable.SearchBoxView_searchHintTextColor, Color.rgb(150, 150, 150));
                searchEnabled = a.getBoolean(R.styleable.SearchBoxView_searchEnabled, true);
                backgroundRes = a.getInt(R.styleable.SearchBoxView_searchBackgroundResource, R.drawable.search_box_def_bg);
                a.recycle();
            }
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.setPadding(PixelUtils.dip2px(getContext(), 8), 0,
                    PixelUtils.dip2px(getContext(), 8), 0);
            this.setBackgroundResource(backgroundRes);
            this.addView(createSearchIcon());
            this.addView(createSearchText());
            this.addView(createClearButton());
        } catch (Exception e) {
            Logger.L.error("init search box view error:", e);
        }
    }

    private ImageView createSearchIcon() {
        LayoutParams ivparam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        ivparam.gravity = Gravity.CENTER_VERTICAL;
        ivparam.setMargins(0, PixelUtils.dip2px(getContext(), 2), 0, 0);
        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(ivparam);
        iv.setId(SearchPkg.SEARCH_ICON_ID);
        iv.setImageResource(R.drawable.search_def_icon);
        return iv;
    }

    private EditText createSearchText() {
        LayoutParams etparam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        etparam.weight = 1;
        etparam.setMargins(0, PixelUtils.dip2px(getContext(), 1), 0, 0);
        EditText et = new EditText(getContext());
        et.setLayoutParams(etparam);
        et.setId(SearchPkg.SEARCH_ET_ID);
        if (hintTextColor != 0) {
            et.setHintTextColor(hintTextColor);
        } else {
            et.setHintTextColor(Color.rgb(150, 150, 150));
        }
        et.setTextColor(Color.rgb(31, 31, 31));
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        et.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        et.setPadding(PixelUtils.dip2px(getContext(), 4),
                PixelUtils.dip2px(getContext(), 7), 0,
                PixelUtils.dip2px(getContext(), 7));
        et.setSingleLine(true);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setOnEditorActionListener(editactionlistener);
        et.addTextChangedListener(searchtextchangelisening);
        et.setBackgroundColor(0);
        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        if (TextUtils.isEmpty(hint)) {
            et.setHint(hint);
        }
        et.setEnabled(searchEnabled);
        return et;
    }

    private ImageButton createClearButton() {
        LayoutParams ibparam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        ibparam.gravity = Gravity.CENTER_VERTICAL;
        ImageButton ib = new ImageButton(getContext());
        ib.setLayoutParams(ibparam);
        ib.setId(SearchPkg.SEARCH_CLEAR_BUTTON_ID);
        ib.setBackgroundResource(R.drawable.search_clear_def_icon);
        ib.setVisibility(View.GONE);
        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(SearchPkg.SEARCH_ET_ID);
                if (et != null) {
                    et.setText("");
                }
                ImageButton ib = (ImageButton) findViewById(SearchPkg.SEARCH_CLEAR_BUTTON_ID);
                if (ib != null) {
                    ib.setVisibility(View.GONE);
                }
            }
        });
        return ib;
    }

    public void setSearchIconResource(int resid) {
        ImageView iv = (ImageView) findViewById(SearchPkg.SEARCH_ICON_ID);
        if (iv != null) {
            iv.setImageResource(resid);
        }
    }

    public void setSearchHintTextColor(int color) {
        EditText et = (EditText) findViewById(SearchPkg.SEARCH_ET_ID);
        if (et != null) {
            et.setHintTextColor(color);
        }
    }

    public void setClearIconResource(int resid) {
        ImageButton ib = (ImageButton) findViewById(SearchPkg.SEARCH_CLEAR_BUTTON_ID);
        if (ib != null) {
            ib.setBackgroundResource(resid);
        }
    }

    public void setSearchHintText(CharSequence hint) {
        EditText et = (EditText) findViewById(SearchPkg.SEARCH_ET_ID);
        if (et != null && hint != null) {
            et.setHint(hint);
        }
    }

    public void setSearchText(CharSequence text) {
        EditText et = (EditText) findViewById(SearchPkg.SEARCH_ET_ID);
        if (et != null) {
            et.setText(text);
        }
    }

    public CharSequence getSearchText() {
        EditText et = (EditText) findViewById(SearchPkg.SEARCH_ET_ID);
        if (et != null) {
            Editable medit = et.getText();
            return medit.toString();
        }
        return "";
    }

    public void setMaxLenght(int maxlenght) {
        EditText et = (EditText) findViewById(SearchPkg.SEARCH_ET_ID);
        if (et != null) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                    maxlenght)});
        }
    }

    private TextWatcher searchtextchangelisening = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            View clearview = findViewById(SearchPkg.SEARCH_CLEAR_BUTTON_ID);
            if (TextUtils.isEmpty(s)) {
                if (clearview != null) {
                    clearview.setVisibility(View.GONE);
                }
                if (mSearchListener != null) {
                    mSearchListener.onSearchTextChanged(true);
                }
            } else {
                if (clearview != null) {
                    clearview.setVisibility(View.VISIBLE);
                }
                if (mSearchListener != null) {
                    mSearchListener.onSearchTextChanged(false);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mSearchListener != null) {
                mSearchListener.afterTextChanged(s);
            }
        }
    };

    private OnEditorActionListener editactionlistener = new OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (mSearchListener != null) {
                    mSearchListener.onSearchDone(getSearchText());
                }
            }
            return false;
        }
    };
}

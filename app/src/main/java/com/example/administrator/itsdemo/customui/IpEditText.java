package com.example.administrator.itsdemo.customui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 组合控件Ip地址输入框
 * @author wangke
 */

public class IpEditText extends LinearLayout {

    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    private EditText mEditText4;
    //EditText在父View中对应的下标的位置
    private int[] edtIndex = new int[]{0, 2, 4, 6};


    public IpEditText(Context context) {
        super(context);
        InitUI();
        //检查Ip地址的输入是否正确
        checkInput();

    }


    public IpEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitUI();
        //检查Ip地址的输入是否正确
        checkInput();
    }

    public IpEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitUI();
        //检查Ip地址的输入是否正确
        checkInput();
    }

    /**
     * 初始化控件布局
     */
    private void InitUI() {

        mEditText1 = new EditText(getContext());
        mEditText1.setTag(0);
        mEditText2 = new EditText(getContext());
        mEditText2.setTag(1);
        mEditText3 = new EditText(getContext());
        mEditText3.setTag(2);
        mEditText4 = new EditText(getContext());
        mEditText4.setTag(3);

        TextView tvPoint1 = new TextView(getContext());
        tvPoint1.setText(".");
        tvPoint1.setTextSize(30);

        TextView tvPoint2 = new TextView(getContext());
        tvPoint2.setText(".");
        tvPoint2.setTextSize(30);

        TextView tvPoint3 = new TextView(getContext());
        tvPoint3.setText(".");
        tvPoint3.setTextSize(30);

        addView(mEditText1); //0
        addView(tvPoint1);
        addView(mEditText2); //2
        addView(tvPoint2);
        addView(mEditText3); //4
        addView(tvPoint3);
        addView(mEditText4); //6

        //遍历子View设置布局样式
        for (int i = 0; i < getChildCount(); i++) {

            if (getChildAt(i) instanceof EditText) {

                ((EditText) getChildAt(i)).setGravity(Gravity.CENTER);
                //设置EditText最大的输入字符数为3
                ((EditText) getChildAt(i)).setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                LinearLayout.LayoutParams params = (LayoutParams) getChildAt(i).getLayoutParams();

                ((EditText) getChildAt(i)).setInputType(InputType.TYPE_CLASS_NUMBER);
                params.weight = 1;
                params.width = 0;

            }
        }
    }


    private int currentFoucsIndex = 0;

    /**
     * 检查Ip地址的输入是否正确
     */
    private void checkInput() {


        for (int i = 0; i < getChildCount(); i++) {

            if (getChildAt(i) instanceof EditText) {

                final EditText edtText = (EditText) getChildAt(i);


                edtText.setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (hasFocus == true) {

                            int tag = (int) v.getTag();
                            //记录当前获取焦点的下标
                            currentFoucsIndex = tag;
                        }


                    }
                });


                /**
                 * EdtText内容发生改变的监听
                 */
                edtText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        Log.i("wk", this.toString() + "改变的:" + s + "count:" + count);


                        if (s.length() > 0) {


                            int ipValue = Integer.valueOf(s.toString());

                            if (ipValue > 255 || ipValue < 0) {

                                Toast.makeText(getContext(), "输入的Ip不合法!", Toast.LENGTH_SHORT).show();

                                //添加警告动画
                                addWarnAnim(edtText);

                            } else {

                                //如果输入的长度为3表示当前Ip的区段已经输入完成，将焦点递给下一个EditText
                                if (s.toString().length() == 3) {
                                    currentFoucsIndex += 1;

                                    if (currentFoucsIndex <= edtIndex.length - 1) {

                                        //申请当前正在输入的下一个EditText获取焦点
                                        getChildAt(edtIndex[currentFoucsIndex]).requestFocus();

                                    }

                                }
                            }


                        } else {


                            Toast.makeText(getContext(), "不能为空!", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }

        }

    }

    /**
     * 创建一个用于提醒输入出错的动画效果
     *
     * @param editText
     */
    private void addWarnAnim(EditText editText) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setRepeatCount(8);
        TranslateAnimation rightAnim = new TranslateAnimation(0, 30, 0, 0);
        rightAnim.setDuration(60);
        TranslateAnimation leftAnim = new TranslateAnimation(30, 0, 0, 0);
        rightAnim.setDuration(60);
        animationSet.addAnimation(rightAnim);
        animationSet.addAnimation(leftAnim);
        editText.startAnimation(animationSet);
    }

    /**
     * 判断当前Ip地址的每个区段是否都不为空
     *
     * @return 为空返回true/不为空返回false
     */
    public Boolean isHaveEmpty() {

        Boolean isHaveEmpty = false;

        for (int i = 0; i < getChildCount(); i++) {

            if (getChildAt(i) instanceof EditText) {

                EditText edt = (EditText) getChildAt(i);

                if (edt.getText().toString().equals("")) {

                    isHaveEmpty = true;

                    break;

                } else {
                    isHaveEmpty = false;
                }
            }

        }
        return isHaveEmpty;
    }

    /**
     * 获取用户当前输入的Ip地址
     *
     * @return
     */
    public String getIpAddress() {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getChildCount(); i++) {

            if (getChildAt(i) instanceof EditText) {

                EditText edtText = (EditText) getChildAt(i);

                sb.append(edtText.getText());
                if (i != getChildCount() - 1) {
                    sb.append(".");
                }
            }
        }
        return sb.toString();
    }
}

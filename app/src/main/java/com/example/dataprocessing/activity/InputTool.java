package com.example.dataprocessing.activity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InputTool {
    static int inputType= InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL;
    //添加的控件
    static LinearLayout[] layouts=null;
    static TextView[] labels=null;
    static EditText[] inputs=null;
    //给容器注入n个输入框
    public static EditText[] addInput(AppCompatActivity context, LinearLayout container, int n, String additionWords){
        container.removeAllViews();
        layouts=new LinearLayout[n];
        labels=new TextView[n];
        inputs=new EditText[n];

        //设置组件样式
        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params3=new LinearLayout.LayoutParams(
                250, ViewGroup.LayoutParams.WRAP_CONTENT);

        inputs[0]=new EditText(context);
        inputs[0].setLayoutParams(params3);
        inputs[0].setGravity(Gravity.CENTER);
        inputs[0].setInputType(inputType);

        //设置监听器
        for(int i=0;i<n;i++){
            layouts[i]=new LinearLayout(context);

            layouts[i].setLayoutParams(params1);
            layouts[i].setGravity(Gravity.CENTER);
            layouts[i].setOrientation(LinearLayout.HORIZONTAL);

            labels[i]=new TextView(context);

            labels[i].setLayoutParams(params2);
            labels[i].setGravity(Gravity.CENTER);
            labels[i].setTextSize(20);
            labels[i].setText("第"+(i+1)+"次"+additionWords+": ");

            if(i+1<n){
                inputs[i+1]=new EditText(context);
                inputs[i+1].setLayoutParams(params3);
                inputs[i+1].setGravity(Gravity.CENTER);
                inputs[i+1].setInputType(inputType);
            }
            inputs[i].addTextChangedListener(new JumpTextWatcher(inputs[i],i!=n-1?inputs[i+1]:null));

            layouts[i].addView(labels[i]);
            layouts[i].addView(inputs[i]);
            container.addView(layouts[i]);
        }
        return inputs;
    }

    //网上复制粘贴的EditText控件跳转到指定控件
    public static class JumpTextWatcher implements TextWatcher {
        private EditText mThisView = null;
        private View mNextView = null;

        public JumpTextWatcher(EditText vThis, View vNext) {
            super();
            mThisView = vThis;
            if (vNext != null) {
                mNextView = vNext;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.indexOf("/r") >= 0 || str.indexOf("\n") >= 0) {
                //如果发现输入回车符或换行符，替换为空字符
                s.replace(0,str.length(),str.replace("/r", "").replace("\n", ""));
                if (mNextView != null) {
                    //如果跳转控件不为空，让下一个控件获得焦点，此处可以直接实现登录功能
                    if (mNextView instanceof EditText) {
                        mNextView.requestFocus();
                        EditText et = (EditText) mNextView;
                        //如果跳转控件为EditText，让光标自动移到文本框文字末尾
                        et.setSelection(et.getText().length());
                    }
                    else if(mNextView instanceof Button){
                        Button confirm=(Button) mNextView;
                        confirm.performClick();
                    }
                }
            }
        }
    }
}

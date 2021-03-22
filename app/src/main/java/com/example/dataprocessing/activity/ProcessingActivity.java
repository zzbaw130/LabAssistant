package com.example.dataprocessing.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dataprocessing.R;
import com.example.dataprocessing.fragment.*;

public class ProcessingActivity extends AppCompatActivity {
    //fragment管理器
    FragmentManager fm=null;
    First first=null;
    Second second=null;
    Third third=null;

    //控件
    TextView[] steps=new TextView[3];
    LinearLayout showDatas=null;

    //数据
    public double[] datas=null;
    public double INS;
    double[] calculations=new double[5]; //分别代表以下数据
//    double avg;
//    double std;
//    double uncertaintyA;
//    double uncertaintyAll;
//    double INS;

    //步骤三的是否可点击
    boolean enable3=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);
        Bundle bundle=getIntent().getExtras();

        //接收main的数据
        datas=bundle.getDoubleArray("datas");
        INS=bundle.getDouble("INS");
        calculations[4]=INS;

        //通过fragment1计算平均值与标准差[默认显示]
        first=First.newInstance(calculations,datas);
        fm=this.getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragmentContainer, first).commitAllowingStateLoss();

        //通过fragment2计算A类不确定度与B类不确定度
        second=Second.newInstance(calculations,datas.length,INS);

        //通过fragment3进行最终答案的输出
        third=Third.newInstance(calculations);

        //为三个步骤添加事件
        steps[0]=findViewById(R.id.step1);
        steps[1]=findViewById(R.id.step2);
        steps[2]=findViewById(R.id.step3);
        setListeners();

        //将输入的数据显示
        int n=datas.length;
        showDatas=findViewById(R.id.showDatas);
        for(int i=0;i<n;i++){
            TextView label=new TextView(ProcessingActivity.this);
            LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            label.setLayoutParams(params2);
            label.setGravity(Gravity.CENTER);
            label.setTextSize(20);
            label.setTextColor(Color.rgb((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
            label.setText("第"+(i+1)+"次: "+datas[i]);
            showDatas.addView(label);
        }
    }

    //为三个按钮统一设置点击事件
    private void setListeners(){
        OnClick onClick=new OnClick();
        for(TextView step:steps){
            step.setOnClickListener(onClick);
        }
    }
    //三个步骤的点击事件,切换颜色+切换fragment
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.step1:
                    steps[0].setBackground(getResources().getDrawable(R.drawable.border1));
                    steps[0].setTextColor(Color.parseColor("#000000"));
                    steps[1].setBackground(getResources().getDrawable(R.drawable.border2));
                    steps[1].setTextColor(Color.parseColor("#CDCDC1"));
                    steps[2].setBackground(getResources().getDrawable(R.drawable.border2));
                    steps[2].setTextColor(Color.parseColor("#CDCDC1"));
                    fm.beginTransaction().replace(R.id.fragmentContainer, first).commitAllowingStateLoss();
                    break;
                case R.id.step2:
                    enable3=true; //设置步骤三可用
                    steps[1].setBackground(getResources().getDrawable(R.drawable.border1));
                    steps[1].setTextColor(Color.parseColor("#000000"));
                    steps[0].setBackground(getResources().getDrawable(R.drawable.border2));
                    steps[0].setTextColor(Color.parseColor("#CDCDC1"));
                    steps[2].setBackground(getResources().getDrawable(R.drawable.border2));
                    steps[2].setTextColor(Color.parseColor("#CDCDC1"));
                    fm.beginTransaction().replace(R.id.fragmentContainer, second).commitAllowingStateLoss();
                    break;
                case R.id.step3:
                    if(!enable3){
                        Toast.makeText(ProcessingActivity.this,"请先完成不确定度的计算!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    steps[2].setBackground(getResources().getDrawable(R.drawable.border1));
                    steps[2].setTextColor(Color.parseColor("#000000"));
                    steps[0].setBackground(getResources().getDrawable(R.drawable.border2));
                    steps[0].setTextColor(Color.parseColor("#CDCDC1"));
                    steps[1].setBackground(getResources().getDrawable(R.drawable.border2));
                    steps[1].setTextColor(Color.parseColor("#CDCDC1"));
                    fm.beginTransaction().replace(R.id.fragmentContainer, third).commitAllowingStateLoss();
                    break;
            }
        }
    }
}

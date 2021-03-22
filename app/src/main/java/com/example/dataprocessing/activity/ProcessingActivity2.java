package com.example.dataprocessing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dataprocessing.R;

public class ProcessingActivity2 extends AppCompatActivity {

    public double[] datasX=null;
    public double[] datasY=null;
    LinearLayout showDatas2=null;
    TextView getX = null;
    TextView getY = null;
    TextView getSxx = null;
    TextView getSyy = null;
    TextView getSxy = null;
    TextView getK = null;
    TextView getB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing2);

        //接受输入数据
        Bundle bundle=getIntent().getExtras();
        datasX=bundle.getDoubleArray("datasX");
        datasY=bundle.getDoubleArray("datasY");

        //将输入的数据显示
        int n=datasX.length;
        showDatas2=findViewById(R.id.showDatas2);
        //多一位用于分割
        for(int i=0;i<2*n+1;i++){
            TextView label=new TextView(ProcessingActivity2.this);
            LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            label.setLayoutParams(params2);
            label.setGravity(Gravity.CENTER);
            label.setTextSize(20);
            label.setTextColor(Color.rgb((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
            if(i<n)
                label.setText("第"+(i+1)+"次X: "+datasX[i]);
            else if (i==n)
                label.setText(" ");
            else
                label.setText("第"+(i-n)+"次Y: "+datasY[i-n-1]);
            showDatas2.addView(label);
        }
        //计算数据
        double X = MathTool.getAvg(datasX);
        double Y = MathTool.getAvg(datasY);
        double Sxx = MathTool.getSXX(datasX,datasX);
        double Syy = MathTool.getSXX(datasY,datasY);
        double Sxy = MathTool.getSXX(datasX,datasY);
        double K = Sxy/Sxx;
        double B = Y-K*X;
        //查找组件
        getX = findViewById(R.id.LS_getX);
        getY = findViewById(R.id.LS_getY);
        getSxx = findViewById(R.id.LS_getSxx);
        getSyy = findViewById(R.id.LS_getSyy);
        getSxy = findViewById(R.id.LS_getSxy);
        getK = findViewById(R.id.LS_getK);
        getB = findViewById(R.id.LS_getB);
        //展示数据
        getX.setText("="+MathTool.remove0(String.format("%.9f",X)));
        getY.setText("="+MathTool.remove0(String.format("%.9f",Y)));
        getSxx.setText("="+MathTool.remove0(String.format("%.9f",Sxx)));
        getSyy.setText("="+MathTool.remove0(String.format("%.9f",Syy)));
        getSxy.setText("="+MathTool.remove0(String.format("%.9f",Sxy)));
        getK.setText("="+MathTool.remove0(String.format("%.9f",K)));
        getB.setText("="+MathTool.remove0(String.format("%.9f",B)));

    }
}
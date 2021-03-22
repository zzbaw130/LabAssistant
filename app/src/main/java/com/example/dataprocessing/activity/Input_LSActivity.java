package com.example.dataprocessing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dataprocessing.R;

public class Input_LSActivity extends AppCompatActivity {
    //数据输入
    static int n = 0;
    double[] datasX =null;
    double[] datasY =null;
    //控件
    EditText[] inputsX=null;
    EditText[] inputsY=null;
    EditText getN = null;
    Button addN = null;
    LinearLayout containerX = null;
    LinearLayout containerY = null;
    Button goProcessing2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_s);

        getN = findViewById(R.id.getN2);
        addN = findViewById(R.id.addN2);
        containerX = findViewById(R.id.containerX);
        containerY = findViewById(R.id.containerY);
        goProcessing2 = findViewById(R.id.goProcessing2);

        //设置添加N个控件的事件
        addN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //或得输入数据
                String num=getN.getText().toString();
                //正则表达式判断数据合法性
                if(!num.matches("(?:[2-9]|1[0-9]|20)"))
                    Toast.makeText(Input_LSActivity.this,"请输入2~20的正整数!",Toast.LENGTH_SHORT).show();
                else if(n!=Integer.parseInt(num)){
                    n = Integer.parseInt(num);
                    datasX = new double[n];
                    datasY = new double[n];
                    inputsX = InputTool.addInput(Input_LSActivity.this,containerX,n,"X");
                    inputsY = InputTool.addInput(Input_LSActivity.this,containerY,n,"Y");
                    goProcessing2.setEnabled(true);
                }
            }
        });

        goProcessing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<n;i++){
                    String dataX=inputsX[i].getText().toString();
                    String dataY=inputsY[i].getText().toString();
                    if(!dataX.matches("\\d+\\.?\\d*")){
                        Toast toast=Toast.makeText(Input_LSActivity.this,"请输入正确的数据!",Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(!dataY.matches("\\d+\\.?\\d*")){
                        Toast toast=Toast.makeText(Input_LSActivity.this,"请输入正确的数据!",Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    datasX[i]=Double.parseDouble(dataX);
                    datasY[i]=Double.parseDouble(dataY);
                }
                Intent intent=new Intent(Input_LSActivity.this, ProcessingActivity2.class);
                Bundle bundle=new Bundle();
                bundle.putDoubleArray("datasX", datasX);
                bundle.putDoubleArray("datasY", datasY);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
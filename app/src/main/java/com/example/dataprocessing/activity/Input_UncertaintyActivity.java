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

public class Input_UncertaintyActivity extends AppCompatActivity {

    //传输数据
    static int n=0;
    double[] datas=null;

    //控件
    EditText[] inputs=null;
    EditText getINS=null;
    EditText getN=null;
    Button addN=null;
    Button goProcessing=null;
    LinearLayout container=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncertainty);
        //获取控件
        getINS=findViewById(R.id.getINS);
        getN=findViewById(R.id.getN1);
        addN=findViewById(R.id.addN1);
        container=findViewById(R.id.container);
        goProcessing=findViewById(R.id.goProcessing);

        //添加一与二之间的跳转,二与按钮的跳转
        getINS.addTextChangedListener(new InputTool.JumpTextWatcher(getINS,getN));
        getN.addTextChangedListener(new InputTool.JumpTextWatcher(getN,addN));

        //设置添加N个控件的事件
        addN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //或得输入数据
                String INS=getINS.getText().toString();
                String num=getN.getText().toString();

                //正则表达式判断数据合法性
                if(!INS.matches("\\d+\\.?\\d*"))
                    Toast.makeText(Input_UncertaintyActivity.this,"请输入正确的误差限值!",Toast.LENGTH_SHORT).show();
                else if(!num.matches("(?:[2-9]|1[0-9]|20)"))
                    Toast.makeText(Input_UncertaintyActivity.this,"请输入2~20的正整数!",Toast.LENGTH_SHORT).show();
                else if(n!=Integer.parseInt(num)){
                    n=Integer.parseInt(num);
                    datas = new double[n];
                    inputs = InputTool.addInput(Input_UncertaintyActivity.this,container,n,"测量");
                    goProcessing.setEnabled(true);
                }
            }
        });

        //设置跳转到数据处理的事件
        goProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<n;i++){
                    String data=inputs[i].getText().toString();
                    if(!data.matches("\\d+\\.?\\d*")){
                        Toast toast=Toast.makeText(Input_UncertaintyActivity.this,"请输入正确的数据!",Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    datas[i]=Double.parseDouble(data);
                }
                Intent intent=new Intent(Input_UncertaintyActivity.this, ProcessingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putDoubleArray("datas",datas);
                bundle.putDouble("INS",Double.parseDouble(getINS.getText().toString()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



}

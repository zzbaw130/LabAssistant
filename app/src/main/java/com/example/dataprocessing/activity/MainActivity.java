package com.example.dataprocessing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dataprocessing.R;

public class MainActivity extends AppCompatActivity {

    Button[] goFun = new Button[2];
    CheckBox isInstruction = null;
    TextView Instruction = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //说明按钮
        Instruction = findViewById(R.id.instruction);
        isInstruction = findViewById(R.id.isInstruction);
        isInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInstruction.isChecked())
                    Instruction.setVisibility(View.VISIBLE);
                else
                    Instruction.setVisibility(View.INVISIBLE);
            }
        });
        goFun[0] = findViewById(R.id.goFun1);
        goFun[1] = findViewById(R.id.goFun2);
        setListeners();
    }

    //为三个按钮统一设置点击事件
    private void setListeners(){
        OnClick onClick=new OnClick();
        for(Button btn:goFun){
            btn.setOnClickListener(onClick);
        }
    }
    //三个步骤的点击事件,切换颜色+切换fragment
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.goFun1:
                    Intent intent1 = new Intent(MainActivity.this, Input_UncertaintyActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.goFun2:
                    Intent intent2 = new Intent(MainActivity.this, Input_LSActivity.class);
                    startActivity(intent2);
                    break;

            }
        }
    }
}
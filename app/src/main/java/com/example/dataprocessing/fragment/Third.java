package com.example.dataprocessing.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.dataprocessing.R;
import com.example.dataprocessing.activity.MathTool;


public class Third extends Fragment {
    //数据
    double[] calculations=null;

    //控件
    TextView answer=null;

    //计算出的数据
    String revisedUncertainty;
    String revisedAvg;

    public Third() { }

    //传递参数
    public static Third newInstance(double[] calculations) {
        Third fragment = new Third();
        Bundle args = new Bundle();
        args.putDoubleArray("calculations", calculations);
        fragment.setArguments(args);
        return fragment;
    }

    //初始化成员变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            calculations = getArguments().getDoubleArray("calculations");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        answer=view.findViewById(R.id.answer);

        //修约并输出
        revisedUncertainty=MathTool.reviseUncertainty(calculations[3]);
        //修约位数为不确定度的小数位数
        revisedAvg=MathTool.reviseAvg(calculations[0],revisedUncertainty.length()-revisedUncertainty.indexOf('.')-1);
        answer.setText(revisedAvg+"±"+revisedUncertainty);
    }
}

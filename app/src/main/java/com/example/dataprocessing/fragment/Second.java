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


public class Second extends Fragment {
    //初始化的数据
    private double[] calculations;
    private int n;
    private double INS;

    //前十个t因子
    private double[] t_n={8.98, 2.48, 1.59, 1.24, 1.05, 0.93, 0.84, 0.77, 0.72};

    //控件
    TextView getUncertaintyA=null;
    TextView getUncertaintyB=null;
    TextView getUncertaintyAll=null;


    public Second() {
    }

    //接收参数
    public static Second newInstance(double[] calculations,int n,double INS) {
        Second fragment = new Second();
        Bundle args = new Bundle();
        args.putDoubleArray("calculations",calculations);
        args.putInt("len",n);
        args.putDouble("INS",INS);
        fragment.setArguments(args);
        return fragment;
    }

    //初始化参数
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            calculations = getArguments().getDoubleArray("calculations");
            n = getArguments().getInt("len");
            INS = getArguments().getDouble("INS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //计算A
        getUncertaintyA=view.findViewById(R.id.getUncertaintyA);
        double uncertaintyA=getUncertaintyA();
        calculations[2]=uncertaintyA;
        getUncertaintyA.setText("="+String.format("%.9f",uncertaintyA));

        //设置B
        getUncertaintyB=view.findViewById(R.id.getUncertaintyB);
        getUncertaintyB.setText("="+Double.toString(INS));

        //计算总
        getUncertaintyAll=view.findViewById(R.id.getAllUncertainty);
        double uncertaintyAll= MathTool.getAll(uncertaintyA,INS);
        calculations[3]=uncertaintyAll;
        getUncertaintyAll.setText("="+String.format("%.9f",uncertaintyAll));
    }

    //计算A类不确定度，大于10的部分
    private double getUncertaintyA(){
        if(n<=10)
            return calculations[1]*(t_n[n-2]);
        else if(n<=15)
            return calculations[1]*(0.72-0.034*(n-10));
        else if(n<=20)
            return calculations[1]*(0.55-0.016*(n-15));
        else
            return 0.45;
    }
}

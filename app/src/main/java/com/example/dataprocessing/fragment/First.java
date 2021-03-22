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

public class First extends Fragment {
    //控件
    TextView getAvg=null;
    TextView getStd=null;

    //传入数据
    private double[] datas;
    private double[] calculations;

    public First() { }

    //初始化数据
    public static First newInstance(double[] calculations,double[] datas) {
        First fragment = new First();
        Bundle args = new Bundle();
        args.putDoubleArray("calculations",calculations);
        args.putDoubleArray("datas", datas);
        fragment.setArguments(args);
        return fragment;
    }

    //初始化成员变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            calculations = getArguments().getDoubleArray("calculations");
            datas = getArguments().getDoubleArray("datas");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //计算平均值
        int n=datas.length;
        double avg=MathTool.getAvg(datas);
        calculations[0]=avg;
        getAvg=view.findViewById(R.id.getAvg);
        getAvg.setText("="+ MathTool.remove0(String.format("%.9f",avg)));

        //计算标准偏差
        double std= MathTool.getStd(avg,datas);
        calculations[1]=std;
        getStd=view.findViewById(R.id.getStd);
        getStd.setText("="+ MathTool.remove0(String.format("%.9f",std)));
    }
}

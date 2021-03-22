package com.example.dataprocessing.activity;

public class MathTool {

    public static double getAvg(double[] datas){
        double sum = 0.0;
        for (double data:datas)
               sum += data;
        return sum/datas.length;
    }
    //移除浮点数末尾的零,若全0则保留一位
    public static String remove0(String str){
        int len=str.length();
        int n=1;
        boolean has0=str.charAt(len-1)=='0';
        if(!has0)
            return str;
        else
            for(int i=str.length()-2;i>=0;i--)
                if(str.charAt(i)=='0')
                    n++;
                else if(str.charAt(i)!='.')
                    return str.substring(0,len-n);
                else
                    return str.substring(0,len-n+1);
        return str;
    }

    //传入平均值和数据，计算标准偏差
    public static double getStd(double avg,double... nums) {
        double result=0.0;
        for (double d : nums)
            result+=((d-avg)*(d-avg));
        return Math.sqrt(result/(nums.length-1));
    }

    //计算平方和的算术平方根,用于计算总不确定度
    public static double getAll(double a,double b) {
        return Math.sqrt(a*a+b*b);
    }

    //计算Sxx
    public static double getSXX(double[] datas1,double[] datas2){
        double SXX = 0.0;
        int n = datas1.length;
        for(int i = 0; i < n; i++)
            SXX += datas1[i]*datas2[i];
        return SXX - n*getAvg(datas1)*getAvg(datas2);
    }

    public static String reviseUncertainty(double uncertainty){
        String str = String.format("%.32f",uncertainty);
        int pos = str.indexOf('.');
        for(int i = pos+1; i<str.length(); i++){
            if(str.charAt(i)=='0')
                pos++;
            else if (str.charAt(i)<'5'){
                return str.substring(0,pos+3);
            }
            else
                return str.substring(0,pos+2);
        }
        return str.substring(0,str.indexOf('.')+2);
    }
    //将平均值修约为n位小数
    public static String reviseAvg(double avg,int n){
        String str = String.format("%."+(n+3)+"f",avg);
        int pos = str.indexOf('.');
        System.out.println(pos + n);
        if(str.charAt(pos+n+2)=='0' && str.charAt(pos+n+1)=='5' && "02468".indexOf(str.charAt(pos+n))!=-1)
            return str.substring(0,pos+n+1);
        return String.format("%."+n+"f",avg);
    }
}

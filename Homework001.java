package com.se.homework;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Homework001 {
    public static String practice(){
        Random random = new Random();
        int de1 = random.nextInt(101);//用于决定运算符个数
        int de2,de3;//用于决定运算符种类

        //决定运算符的个数
        if(de1%2 == 0){//一个运算符

            int num1 = random.nextInt(101);
            int num2 = random.nextInt(101);
            int result;
            char suan;
            //判断运算符种类
            de2 = random.nextInt(2);
            switch(de2){
                case 0  : suan = '+';result = num1+num2;break;
                case 1  : suan = '-';result = num1-num2;break;
                default : suan = '+';result = num1+num2;break;
            }
            if(0<=result && result<=100)
                return "" + num1 + suan + num2 + " = " + result;
            else
                return "error";

        }else{//两个运算符

            int num1 = random.nextInt(101);
            int num2 = random.nextInt(101);
            int num3 = random.nextInt(101);
            int result;
            int temp;
            char suan1,suan2;
            //判断运算符种类
            de2 = random.nextInt(2);
            switch(de2){
                case 0  : suan1 = '+';temp = num1+num2;break;
                case 1  : suan1 = '-';temp = num1-num2;break;
                default : suan1 = '+';temp = num1+num2;break;
            }
            de3 = random.nextInt(4);
            switch(de3){
                case 0  : suan2 = '+';result = temp+num3;break;
                case 1  : suan2 = '-';result = temp-num3;break;
                default : suan2 = '+';result = temp+num3;break;
            }
            if(0<=result && result<=100)
                return "" + num1 + suan1 + num2 + suan2 + num3 + " = " + result;
            else
                return "error";
        }
    }

    public static boolean check(String[] s1,String s2){//判断是否重复出题
        for (int i = 0 ; i<s1.length ; i++){
            if(s2.equals(s1[i])){
                return false;//重复了
            }
        }
        return true;//没有重复
    }

    public static void main(String[] args) {
        System.out.println("请输入今日要刷的题目数量");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] test = new String[n];
        for (int i = 0; i < n; i++) {
            String s = practice();
            if (s.equals("error") || !check(test, s)) {
                i--;
                continue;
            } else {
                test[i] = s;
                System.out.println(s);
            }
        }

        //分别获取题目和答案
        String[] question = new String[n];
        String[] answer = new String[n];
        for (int i = 0; i < n; i++) {
            String[] temp = test[i].split("=");
            question[i] = temp[0];
            answer[i] = temp[1];
        }

        //开始导出题目
        for (int i = 0; i < n; i++) {
            FileWriter out = null;
            try{
                out = new FileWriter("Exercises.txt",true);
                out.write("四则运算题目" + (i+1) + "  ");
                out.write("\t");
                out.write(question[i]);
                out.write("\n");
                out.flush();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if(out != null){
                    try{
                        out.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }


        //开始导出答案
        for (int i = 0; i < n; i++) {
            FileWriter out = null;
            try{
                out = new FileWriter("Answers.txt",true);
                out.write("答案" + (i+1) + "  ");
                out.write("\t");
                out.write(answer[i]);
                out.write("\n");
                out.flush();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if(out != null){
                    try{
                        out.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

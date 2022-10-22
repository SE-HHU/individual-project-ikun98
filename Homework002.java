package com.se.homework.homework002;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Homework002 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("请选择功能！");
        System.out.println("1 生成四则运算题目 2 检查题目");
        int function = sc.nextInt();
        if (function == 1) {//生成四则运算
            System.out.println("请输入所需题目个数");
            int testNum = sc.nextInt();
            System.out.println("请输入题目中自然数的范围");
            System.out.println("请输入最小值");
            int min = sc.nextInt();
            System.out.println("请输入最大值");
            int max = sc.nextInt();
            System.out.println("请输入题目中真分数的范围，范围是(0,1)");
            System.out.println("请输入真分数的最小值");
            double fmin = sc.nextDouble();
            System.out.println("请输入真分数的最大值");
            double fmax = sc.nextDouble();
            System.out.println("请输入真分数分母的范围");
            System.out.println("请输入真分数分母的最小值");
            int dmin = sc.nextInt();
            System.out.println("请输入真分数分母的最大值");
            int dmax = sc.nextInt();
            System.out.println("请问是否需要括号？");
            System.out.println("1 需要 2 不需要");
            int isBrackets = sc.nextInt();

            String[] exercises = new String[testNum];//用来存放已经成功生成的题目
            String[] answers = new String[testNum];//用来存放已经成功生成的答案
            int num = 0;//记录已经生成的题目个数
            for (int i = 0; ; i++) {
                String nextexercises = null;
                String nextanswer = null;
                if (isBrackets == 1) {
                    String a = ThreeOperator(min, max, fmin, fmax, dmin, dmax);//无括号
                    nextexercises = AddBrackets(a);//取括号
                    nextanswer = resultCalculation(nextexercises);
                } else {
                    nextexercises = ThreeOperator(min, max, fmin, fmax, dmin, dmax);
                    nextanswer = resultCalculation(nextexercises);
                }
                int j;
                for (j = 0; j < num; j++) {
                    if (Objects.equals(exercises[j], nextexercises)) {
                        break;
                    }
                }
                if (j == num) {
                    exercises[j] = nextexercises;
                    answers[j] = nextanswer;
                    num++;
                }

                if (num == testNum) {
                    break;
                }
            }
            FileWriter fil = null;
            FileWriter fil2 = null;
            try {
                fil = new FileWriter("Exercises.txt", true);//题目
                fil2 = new FileWriter("Answers.txt", true);//答案
                int sum = 1;//写入序号
                for (int i = 0; i < num; i++) {
                    String sum1 = String.valueOf(sum);//写入序号
                    String xuhao = sum1 + "." + " ";
                    fil.write(xuhao);
                    fil.write(exercises[i]);
                    fil.write("\n");//以上三条为对题目的处理
                    fil.flush();
                    fil2.write(xuhao);
                    fil2.write(answers[i]);
                    fil2.write("\n");//以上三条为答案的处理
                    fil2.flush();
                    sum++;
                }
            } catch (Exception h) {
                System.out.print("erroe!");
            } finally {
                if (fil != null) {
                    try {
                        fil.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fil2 != null) {
                    try {
                        fil2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
//        以上为功能一
//以下为功能二
        else if(function==2){//对文件中的题目进行处理
            Scanner in = new Scanner(System.in);
            System.out.println("请先输入题目文件的地址");
            String exerciseAddress = in.nextLine();
            System.out.println("请输入答案文件的地址");
            String answersAddress = in.nextLine();
            int i = 0;//记录行数
            FileReader reader = null;
            FileReader reader1 = null;
            try {
                reader = new FileReader(exerciseAddress);
                BufferedReader br = new BufferedReader(reader);
                while (null != br.readLine()) {
                    i++;
                }
                br.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] exercises = new String[i];
            String[] answers = new String[i];
            try {
                reader = new FileReader(exerciseAddress);
                reader1 = new FileReader(answersAddress);
                BufferedReader br = new BufferedReader(reader);
                BufferedReader br2 = new BufferedReader(reader1);
                for (int j = 0; j < i; j++) {
                    exercises[j] = br.readLine();
                }
                for (int j = 0; j < i; j++) {
                    answers[j] = br2.readLine();
                }
                br.close();
                br2.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] realexercises = new String[i];//储存去掉序号后的题目
            String[] realanswers = new String[i];//储存去掉序号的题目
            int[] correct = new int[i];//储存正确的题目序号
            int[] wrong = new int[i];//储存错误的题目序号
            int[][] repeat = new int[i][2];//储存重复的题目
            for (int j = 0; j < i; j++)//去掉括号
            {
                int h = 0;//h的作用是找到需要切割的位置，因为序号可能有不同位数
                h = exercises[j].indexOf(" ");
                realexercises[j] = exercises[j].substring(h + 1, exercises[j].length());
                realanswers[j] = answers[j].substring(h + 1, answers[j].length());


            }

            int h = 0;
            int k = 0;
            int p = 0;
            for (int j = 0; j < i; j++)//判断答案对不对,h为正确的题目的序号，k为错误的题目序号,p为重复的题目序号
            {
                String a = resultCalculation(realexercises[j]);
                if (Objects.equals(a, realanswers[j])) {
                    correct[h] = j + 1;
                    h++;
                } else {
                    wrong[k] = j + 1;
                    k++;
                }

            }
            for (int j = 0; j < realexercises.length; j++)//判断是否重复
            {
                for (int g = j + 1; g < realexercises.length; g++) {
                    if (Objects.equals(realanswers[j], realanswers[g]))//先找答案一样
                    {
                        if (IsRepeat(realexercises[j], realexercises[g]) == 1)//重复时
                        {
                            repeat[p][0] = j;
                            repeat[p][1] = g;
                            p++;
                        }
                    }
                }
            }

//            以下为写入Grade.txt文件

            FileWriter fil = null;
            try {
                fil = new FileWriter("Grade.txt", true);
                fil.write("Correct:");
                String corrects = null;
                for (int j = 0; j < h; j++) {
                    if (j == 0) {
                        String v = String.valueOf(correct[j]);
                        String d = String.valueOf(h);
                        corrects = d + "(" + v + ",";
                    } else if (j == h - 1) {
                        String v = String.valueOf(correct[j]);
                        corrects = corrects + v + ")";
                    } else {
                        String v = String.valueOf(correct[j]);
                        corrects = corrects + v + ",";
                    }
                }
                fil.write(corrects);
                fil.write("\n");//以上写入correct
                fil.write("Wrong:");
                String wrongs = null;
                for (int j = 0; j < k; j++) {
                    if (j == 0) {
                        String v = String.valueOf(wrong[j]);
                        String d = String.valueOf(k);
                        wrongs = d + "(" + v + ",";
                    } else if (j == k - 1) {
                        String v = String.valueOf(wrong[j]);
                        wrongs = wrongs + v + ")";
                    } else {
                        String v = String.valueOf(wrong[j]);
                        wrongs = wrongs + v + ",";
                    }
                }
                if(!Objects.equals(wrongs,null)) {
                    fil.write(wrongs);
                }
                fil.write("\n");//以上为写入wrong
                fil.write("Repeat:" + String.valueOf(p));
                fil.write("\n");
                fil.write("RepeatDetail:");
                fil.write("\n");
                for (int j = 0; j < p; j++) {
                    String jj = String.valueOf(j + 1);//记录重复的序号
                    String repeat1 = "(" + jj + ")" + " " + String.valueOf(repeat[j][0] + 1) + "," + realexercises[repeat[j][0]] + " "
                            + "Repeat " + String.valueOf(repeat[j][1] + 1) + "," + realexercises[repeat[j][1]];//要求的输入格式
                    fil.write(repeat1);
                    fil.write("\n");
                }
                fil.flush();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fil != null) {
                    try {
                        fil.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else {
            System.out.println("输入错误！");
        }


    }

    //    以下是随机生成题目的方法
    public static String OneOperator(int min, int max, double fmin, double fmax, int dmin, int dmax) {//一个运算符的方法
        Random rand = new Random();
        String[] s = {"+", "-", "✖", "➗"};
        int kindOperator = rand.nextInt(4);//通过随机数判断运算符，1为+，2为-，3为*，4为/
        return "" + createNum(min, max, fmin, fmax, dmin, dmax) + " " + s[kindOperator] + " " + createNum(min, max, fmin, fmax, dmin, dmax) + " =";

    }

    public static String TwoOperator(int min, int max, double fmin, double fmax, int dmin, int dmax) {//这是两个运算符的情况

        Random rand = new Random();
        String[] s = {"+", "-", "✖", "➗"};
        int kindOperator1 = rand.nextInt(4);//第一个运算符
        int kindOperator2 = rand.nextInt(4);//第二个运算符
        return "" + createNum(min, max, fmin, fmax, dmin, dmax) + " " + s[kindOperator1] + " " + createNum(min, max, fmin, fmax, dmin, dmax) + " "
                + s[kindOperator2] + " " + createNum(min, max, fmin, fmax, dmin, dmax) + " =";

    }

    public static String ThreeOperator(int min, int max, double fmin, double fmax, int dmin, int dmax) {
        Random rand = new Random();
        String[] s = {"+", "-", "✖", "➗"};
        int kindOperator1 = rand.nextInt(4);//第一个运算符
        int kindOperator2 = rand.nextInt(4);//第二个运算符
        int kindOperator3 = rand.nextInt(4);//第三个运算符
        return "" + createNum(min, max, fmin, fmax, dmin, dmax) + " " + s[kindOperator1] + " " + createNum(min, max, fmin, fmax, dmin, dmax) + " "
                + s[kindOperator2] + " " + createNum(min, max, fmin, fmax, dmin, dmax) + " " + s[kindOperator3] + " " + createNum(min, max, fmin, fmax, dmin, dmax) + " =";

    }

    //    以上为随机生成题目的方法
//    以下是随机生成单个运算数的方法
    public static String createNum(int min,int max,double fmin,double fmax,int dmin,int dmax){//这是生成一个运算数的函数
        Random rand =new Random();
        int isInt = rand.nextInt(2);//用于判断生成整数或者分数,
        if(isInt == 1){//整数的情况
            int n = rand.nextInt(min,max+1);
            return String.valueOf(n);
        }else{//生成分数的情况
            while(true) {
                int n2 = rand.nextInt(dmin, dmax + 1);//这是生成分母
                int n1 = rand.nextInt(1, n2);
                double n22 = n2;
                double n11 = n1;
                if((n11/n22) >= fmin && (n11/n22) <= fmax){
                    return "" + n1 + "/" + n2;
                }
            }
        }
    }

    //    以上是随机生成单个运算数的方法
//    以下为添加括号的方法
    public static String AddBrackets(String s)//功能是添加括号，传入的s代表生成的无括号的题目
    {
        Random random = new Random();
        String[] before = s.split(" ");
        int y = before.length;
        int bracketsNum = 0;
        if (y == 6)//判断运算符为两个时的加括号问题，这时只能有一个括号
        {
            bracketsNum = 0;
        } else {
            bracketsNum = random.nextInt(2) + 1;//判断添加的括号个数，1或2
        }

        int[][] brackets = {{0, 3}, {2, 5}};//这是两个运算符时的括号所有可能位置
        int[][] brackets1 = {
                {0, 3}, {0, 5}, {2, 5}, {2, 7}, {4, 7}
        };//这是三个运算符时一个括号的所有可能位置
        int[][] brackets2 = {
                {0, 3, 4, 7}, {0, 5, 0, 3}, {0, 5, 2, 5}, {2, 7, 2, 5}, {2, 7, 4, 7}
        };//这是三个运算符时两个括号的所有可能位置
        int[] local = brackets[random.nextInt(2)];
        int[] local1 = brackets1[random.nextInt(5)];
        int[] local2 = brackets2[random.nextInt(5)];
        if (bracketsNum == 0) {
            String[] after = new String[s.length() + 2];
            int i;//新
            int j;//旧
            for (i = 0, j = 0; i < 8; ) {
                for (int c = 0; c < 2; c++) {
                    if (j == local[c]) {
                        if (c % 2 == 0)//(
                        {
                            after[i] = "(";
                            i++;
                        } else {
                            after[i] = ")";
                            i++;
                        }
                    }
                }
                after[i] = before[j];
                j++;
                i++;

            }

            return "" + after[0] + " " + after[1] + " " + after[2] +
                    " " + after[3] + " " + after[4] + " " + after[5] +
                    " " + after[6] + " " + after[7];
        }
        if (bracketsNum == 1) {//只有一组括号
            String[] after = new String[s.length() + 2];
            int i;//新
            int j;//旧
            for (i = 0, j = 0; i < 10; ) {
                for (int c = 0; c < 2; c++) {
                    if (j == local1[c]) {
                        if (c % 2 == 0) {
                            after[i] = "(";
                            i++;
                        } else {
                            after[i] = ")";
                            i++;
                        }
                    }
                }
                after[i] = before[j];
                j++;
                i++;

            }
            return "" + after[0] + " " + after[1] + " " + after[2] +
                    " " + after[3] + " " + after[4] + " " + after[5] +
                    " " + after[6] + " " + after[7] + " " + after[8] +
                    " " + after[9];
        } else {//有两组括号
            String[] after = new String[s.length() + 4];
            int i;//新
            int j;//旧
            for (i = 0, j = 0; i < 12; ) {
                for (int c = 0; c < 4; c++) {
                    if (j == local2[c]) {
                        if (c % 2 == 0)//(
                        {
                            after[i] = "(";
                            i++;
                        } else {
                            after[i] = ")";
                            i++;
                        }
                    }
                }
                after[i] = before[j];
                j++;
                i++;

            }
            return "" + after[0] + " " + after[1] + " " + after[2] +
                    " " + after[3] + " " + after[4] + " " + after[5] +
                    " " + after[6] + " " + after[7] + " " + after[8] +
                    " " + after[9] + " " + after[10] + " " + after[11];

        }

    }

    //    以上为添加括号的方法
    public static String resultCalculation(String before) {//运算的方法
        String[] after = before.split(" ");
        int khNum = 0;//记录括号的个数
        for (int i = 0; i < after.length; i++)//判断括号的个数
        {
            if (Objects.equals(after[i], "(") || Objects.equals(after[i], ")")) {
                khNum++;
            }
        }
        if (khNum != 0)//有括号的情况
        {
            if (khNum == 4)//有两种括号的情况
            {
                int[] khLocation = new int[khNum];//括号的位置
                int j = 0;
                for (int i = 0; i < after.length; i++)//判断括号的位置，以先后出现顺序储存
                {
                    if (Objects.equals(after[i], "(")) {
                        khLocation[j] = i;
                        j++;
                    }
                    if (Objects.equals(after[i], ")")) {
                        khLocation[j] = i;
                        j++;
                    }
                }


                if (Objects.equals(after[khLocation[1]], "("))//这种是嵌套括号的情况
                {
                    String calculation1 = Calculation(after[khLocation[1] + 1], after[khLocation[1] + 2], after[khLocation[1] + 3]);
                    String calculation2;
                    if (khLocation[0] + 1 == khLocation[1])//((a+b)+c)+d=
                    {
                        calculation2 = Calculation(calculation1, after[khLocation[3] - 2], after[khLocation[3] - 1]);
                    } else {
                        calculation2 = Calculation(after[khLocation[0] + 1], after[khLocation[0] + 2], calculation1);
                    }
                    if (khLocation[0] == 0) {
                        return "" + Calculation(calculation2, after[khLocation[3] + 1], after[khLocation[3] + 2]);
                    } else {
                        return "" + Calculation(after[0], after[1], calculation2);
                    }

                } else//if(Objects.equals(after[khLocation[1]],")"))//这种是不嵌套括号的情况
                {
                    String calculation1 = Calculation(after[1], after[2], after[3]);
                    String calculation2 = Calculation(after[7], after[8], after[9]);
                    return "" + Calculation(calculation1, after[5], calculation2);
                }
            } else {//一个括号的情况
                int[] khLocation = new int[khNum];//括号的位置
                int j = 0;
                for (int i = 0; i < after.length; i++)//判断括号的位置，以先后出现顺序储存
                {
                    if (Objects.equals(after[i], "(")) {
                        khLocation[j] = i;
                        j++;
                    }
                    if (Objects.equals(after[i], ")")) {
                        khLocation[j] = i;
                        j++;
                    }
                }
                if (after.length == 8) {//两个运算符的情况
                    if (khLocation[0] == 0)//(a+b)+c=
                    {
                        String calculation1 = Calculation(after[1], after[2], after[3]);
                        String calculation2 = Calculation(calculation1, after[5], after[6]);
                        return calculation2;
                    } else {
                        String calculation1 = Calculation(after[3], after[4], after[5]);
                        String calculation2 = Calculation(after[0], after[1], calculation1);
                        return calculation2;
                    }
                } else {//三个运算符的情况
                    if (khLocation[1] - khLocation[0] == 4) {//（a+b)+c+d=,a+(b+c)+d,a+b+(c+d)
                        String calculation1 = Calculation(after[khLocation[0] + 1], after[khLocation[0] + 2], after[khLocation[0] + 3]);
                        if (khLocation[0] == 0)//(a+b)+c+d
                        {
                            return "" + Calculation(calculation1, after[5], after[6], after[7], after[8]);
                        } else if (khLocation[0] == 2)//a+(b+c)+d
                        {
                            return "" + Calculation(after[0], after[1], calculation1, after[7], after[8]);
                        } else//a+b+(c+d)
                        {
                            return "" + Calculation(after[0], after[1], after[2], after[3], calculation1);
                        }
                    } else// if(khLocation[1]-khLocation[0]==6)//括号包含三个运算数的情况
                    {
                        String calculation1 = Calculation(after[khLocation[0] + 1], after[khLocation[0] + 2]
                                , after[khLocation[0] + 3], after[khLocation[0] + 4], after[khLocation[0] + 5]);
                        if (khLocation[0] == 0)//(a+b+c)+d
                        {
                            return "" + Calculation(calculation1, after[7], after[8]);
                        } else //if(khLocation[0]==2)//a+(b+c+d)
                        {
                            return "" + Calculation(after[0], after[1], calculation1);
                        }
                    }

                }
            }

        } else {//无括号的情况
            return Calculation(before);
        }
    }


    //    以下是用于两个数的运算
    public static String Calculation(String a, String b, String c) {//这是用于两个数的计算
        int a1, a2;
        ;
        int c1, c2;
        if (a.indexOf("/") == -1) {//如果a是整数
            a1 = Integer.parseInt(a);
            a2 = 1;
        } else {
            a1 = Integer.parseInt(a.substring(0, a.indexOf("/")));
            a2 = Integer.parseInt(a.substring(a.indexOf("/") + 1, a.length()));
        }
        if (c.indexOf("/") == -1) {//如果a是整数
            c1 = Integer.parseInt(c);
            c2 = 1;
        } else {
            c1 = Integer.parseInt(c.substring(0, c.indexOf("/")));
            c2 = Integer.parseInt(c.substring(c.indexOf("/") + 1, c.length()));
        }

        if (Objects.equals(b, "+")) {
            int numerator = a1 * c2 + a2 * c1;
            int denominator = a2 * c2;
            for (int i = numerator; i > 0; i--) {
                if (numerator % i == 0 && denominator % i == 0) {
                    numerator = numerator / i;
                    denominator = denominator / i;
                }
            }
            if (denominator == 1) {
                return "" + numerator;
            }
            return "" + numerator + "/" + denominator;
        } else if (Objects.equals(b, "-")) {
            int numerator = a1 * c2 - a2 * c1;
            int denominator = a2 * c2;
            for (int i = numerator; i > 0; i--) {
                if (numerator % i == 0 && denominator % i == 0) {
                    numerator = numerator / i;
                    denominator = denominator / i;
                }
            }
            if (denominator == 1) {
                return "" + numerator;
            }
            return "" + numerator + "/" + denominator;
        } else if (Objects.equals(b, "✖")) {
            int numerator = a1 * c1;
            int denominator = a2 * c2;
            for (int i = numerator; i > 0; i--) {
                if (numerator % i == 0 && denominator % i == 0) {
                    numerator = numerator / i;
                    denominator = denominator / i;
                }
            }
            if (denominator == 1) {
                return "" + numerator;
            }
            return "" + numerator + "/" + denominator;
        } else {
            int numerator = a1 * c2;
            int denominator = a2 * c1;
            for (int i = numerator; i > 0; i--) {
                if (numerator % i == 0 && denominator % i == 0) {
                    numerator = numerator / i;
                    denominator = denominator / i;
                }
            }
            if (denominator == 1) {
                return "" + numerator;
            }
            return "" + numerator + "/" + denominator;
        }


    }
    //    以上是用于两个数的运算

    //以下用于计算
    public static String Calculation(String a, String b, String c, String d, String e) {//这是三个数有运算先后顺序的运算
        {
            String calculation1;
            String calculation2;
            if (d.equals("✖") && !b.equals("➗")) {
                calculation1 = Calculation(c, d, e);
                return "" + Calculation(a, b, calculation1);
            } else if (d.equals("➗") && (b.equals("+") || b.equals("-"))) {
                calculation1 = Calculation(c, d, e);
                return "" + Calculation(a, b, calculation1);
            } else {
                calculation1 = Calculation(a, b, c);
                return "" + Calculation(calculation1, d, e);
            }
        }

    }

    public static String Calculation(String before) {
        String[] after = before.split(" ");
        int musum = 0;//乘法和除法的总个数
        int jiasum = 0;//加法和减法的总个数

        for (int i = 0; i < after.length; i++) {
            if ("✖".equals(after[i]) || "➗".equals(after[i])) {
                musum++;
            }
            if ("+".equals(after[i]) || "-".equals(after[i])) {
                jiasum++;
            }

        }


        for (int k = 0; k < musum; k++) {
            String[] temp = new String[after.length];
            for (int i = 0; i < after.length; i++) {
                if (Objects.equals(after[i], "✖") || Objects.equals(after[i], "➗")) {
                    String calculation1 = Calculation(after[i - 1], after[i], after[i + 1]);
                    for (int j = 0, h = 0; j < after.length - 2; j++) {
                        if (j == i - 1) {
                            temp[j] = calculation1;
                            h = i + 2;
                        } else {
                            temp[j] = after[h];
                            h++;
                        }
                    }
                    System.arraycopy(temp, 0, after, 0, temp.length);

                }
            }
        }
        for (int i = 0; i < jiasum; i++) {
            String[] temp = new String[after.length];
            for (int k = 0; k < after.length; k++) {
                if (Objects.equals(after[k], "+") || Objects.equals(after[k], "-")) {
                    String calculation1 = Calculation(after[k - 1], after[k], after[k + 1]);
                    for (int j = 0, h = 0; j < after.length - 2; j++) {
                        if (j == k - 1) {
                            temp[j] = calculation1;
                            h = k + 2;
                        } else {
                            temp[j] = after[h];
                            h++;
                        }
                    }
                    System.arraycopy(temp, 0, after, 0, temp.length);
                }
            }
        }
        return after[0];


    }

    //以上用于计算
//以下为判断是否重复的方法
    public static int IsRepeat(String a, String b) {
        String[] a1 = a.split(" ");
        String[] b1 = b.split(" ");
        int khNum = 0;//记录括号的个数
        for (int i = 0; i < a1.length; i++)//判断括号的个数
        {
            if (Objects.equals(a1[i], "(") || Objects.equals(a1[i], ")")) {
                khNum++;
            }
        }
        int repaetSum = 0;//记录重复的总个数
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < b1.length; j++) {
                if (!Objects.equals(a1[i], "(") && !Objects.equals(a1[i], ")"))//当a1中元素不是括号就与b1中的进行比较
                {
                    if (Objects.equals(a1[i], b1[j])) {
                        repaetSum++;
                        b1[j] = null;
                        break;
                    }
                }
            }
        }
        if (repaetSum == a1.length - khNum) {
            return 1;//重复
        } else {
            return 0;
        }


    }
}


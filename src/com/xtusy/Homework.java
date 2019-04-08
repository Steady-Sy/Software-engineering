package com.xtusy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Homework {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入要生成的题目数量：");
        int num = in.nextInt();
        System.out.println("是否要输出带答案的运算符：（1）是（2）否");
        int flag = in.nextInt();
        String[] operator = {"+","-","*","/"};
        Random random = new Random();
        List<String> expression = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int n = 2; //2个运算符
            int[] number = new int[n + 1];
            String ex = new String();
            for (int j = 0; j <= n; j++) {
                number[j] = random.nextInt(100) + 1; //随机产生三个数值
            }

            for (int j = 0; j < n; j++) {
                int s = random.nextInt(4);//随机选择某个运算符
                ex += String.valueOf(number[j]) + operator[s];
                if (s == 3) {
                    number[j + 1] = decide(number[j], number[j + 1]);
                }
            }
            ex += String.valueOf(number[n]);

            ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");// 判断结果是否为0-100整数
            {
                double ans;
                try {
                    ans = Double.valueOf(se.eval(ex).toString());
                    if (ans > 100 || ans < 0 || ans % 1 != 0) {
                        i--;
                        continue;
                    }
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ScriptException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            expression.add(ex);
        }
        Resave("shuchu.txt", calculate(expression, flag));
    }

    /**
     * 随即取x,y为1-100之间，x可以整除y的y值
     *
     * @param x
     * @param y
     * @return
     */
    private static int decide(int x, int y) {
        Random random = new Random();
        if (x % y != 0) {
            y = random.nextInt(100) + 1;
            return decide(x, y);
        } else {
            return y;
        }
    }

    /**
     * 计算每个等式的结果，并返回运算式
     *
     * @param arrayList
     * @return arrayList
     */
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    private static List<String> calculate(List<String> arrayList, int flag) {
        List<String> ArithExpress = new ArrayList<>();
        for (String ax : arrayList) {
            try {
                if (flag == 1) {
                    ax = ax + "=" + jse.eval(ax);
                    System.out.println(ax);
                    ArithExpress.add(ax);
                } else {
                    ax = ax + "=";
                    System.out.println(ax);
                    ArithExpress.add(ax);
                }
            } catch (ScriptException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ArithExpress;
    }
    /**
     * 在路径path下保存文件
     *
     * @param path
     * @param content
     * @return
     */
    private static void Resave(String path, List<String> content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (String con : content) {
                bw.write(con);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

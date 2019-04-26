package com.ooad;

import org.mariuszgromada.math.mxparser.Expression;

public class HelloWorld {

    public static void main(String[] args) {
        String str = "nPk(10, 2)";
        Expression exp = new Expression(str);
        System.out.print(exp.calculate());
    }
}

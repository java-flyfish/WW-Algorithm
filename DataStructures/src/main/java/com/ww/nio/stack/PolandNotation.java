package com.ww.nio.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 计算器
 * 中缀表达式转你波澜表达式再进行计算
 * 逆波兰表达式计算器
 */
public class PolandNotation {

    public static void main(String[] args) {
        //1+((2+3)*4)-5
        //将中缀表达式转成list，即1+((2+3)*4)-5 => [1,+,(,(,2,+,3,),*,4,),-,5]
        String expression = "10+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("转中缀表达数="+expression+infixExpressionList);

        //将中缀表达式对应的list转成后缀表达式list
        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("后缀表达式：" + suffixExpressionList);
        //(3+4)*5-6 => 3 4 + 5 * 6 -
        //String suffixExpression = "3 4 + 5 * 6 - ";
        //3 4 + 5 * 6 -  => [3, 4, +, 5, *, 6, -]
        //List<String> listString = getListString(suffixExpression);
        int calculate = calculate(suffixExpressionList);
        System.out.printf("表达数%s = %d",expression,calculate);
    }

    /**
     * 将中缀表达式对应的list转成后缀表达式list
     * 3 4 + 5 * 6 -  => [3, 4, +, 5, *, 6, -]
     */
    public static List<String> parseSuffixExpressionList(List<String> ls){
        //符号栈
        Stack<String> s1 = new Stack<>();
        //本来是需要s2栈，但是因为s2不会弹出栈，而且最终还需要逆向输出，所以可以使用list代替
        //Stack<String> s2 = new Stack<>();
        //用于存储中间结果
        List<String> s2 = new ArrayList<>();
        for (String item : ls) {
            if (item.matches("\\d+")){
                //如果是数字，就加入s2
                s2.add(item);
            }else if (item.equals("(")){
                //如果是左括号，直接入s1栈
                s1.push(item);
            }else if (item.equals(")")){
                //如果是右括号，则依次弹出s1栈顶的运算符，并放到s2，知道遇到左括号为止，此时将这一对括号丢弃
                while(!s1.peek().equals("(")){
                    s2.add(s1.pop());
                }
                //将左括号弹出，丢弃
                s1.pop();
            }else {
                //优先级的问题
                //当item的优先级小于等于栈顶运算符的优先级，将s1栈顶的运算符加入到s2，再与s1新栈顶的运算符比较
                while(s1.size()!=0 && (prioity(item.charAt(0)) <= prioity(s1.peek().charAt(0)))){
                    s2.add(s1.pop());
                }
                //把当前的item压入到s1
                s1.push(item);
            }
        }
        while(s1.size() > 0){
            s2.add(s1.pop());
        }
        return s2;
    }

    /**
     * 返回运算符的优先级，数字越大，优先级越高
     * 假设目前只有+、-、*、/四种运算符
     */
    public static int prioity(char oper){
        if (oper == '*' || oper == '/'){
            return 2;
        }else if (oper == '+' || oper == '-'){
            return 1;
        }else {
            System.out.println("不存在该运算符");
            return 0;
        }
    }

    /**
     * 将中缀表达式转成对应的lisy
     * @param s 中缀表达式
     */
    public static List<String> toInfixExpressionList(String s){
        List<String> ls = new ArrayList<>();
        //扫描索引指针，用于遍历中缀表达式
        int i = 0;
        //做对多位数的拼接
        String str = "";
        char c;
        do {
            //如果c是一个非数字
            c = s.charAt(i);
            if (c < 48 || c > 57){
                //c不是一个数字
                ls.add(c+"");
                i++;
            }else {
                //c是一个数字,需要考虑多位数的问题
                str = "";
                while (i<s.length() && (c=s.charAt(i))>=48 && (c=s.charAt(i))<=57){
                    str += c;
                    i++;
                }
                ls.add(str);
            }
        }while(i < s.length());
        return ls;
    }

    public static List<String> getListString(String suffixExpression){
        String[] split = suffixExpression.split(" ");
        return Arrays.asList(split);
    }

    /**
     * 计算
     * @param ls suffixExpression转成的list
     * @return 结果
     */
    public static int calculate(List<String> ls){
        Stack<String> stack = new Stack<>();
        //遍历ls
        for (String item : ls) {
            if (item.matches("\\d+")){
                //是数字，入栈
                stack.push(item);
            }else {
                //pop出两个数进行运算
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = cal(num1,num2,item.charAt(0));
                stack.push(res+"");
            }
        }
        return Integer.parseInt(stack.pop());
    }

    /**
     * 计算
     * @param num1 先弹出 数字
     * @param num2 后弹出的数字
     * @param oper 运算符
     * @return 结果
     */
    public static int cal(int num1, int num2, int oper){
        //用于存放计算结果
        int res = 0;

        switch (oper){
            case '+':
                res = num1 + num2;
                break;
            case '-':
                //注意顺序，后面的数字减去前面的数字
                res = num1 - num2;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                //注意顺序，后面的数字减去前面的数字
                res = num1 / num2;
                break;
            default:
                break;
        }
        return res;
    }
}

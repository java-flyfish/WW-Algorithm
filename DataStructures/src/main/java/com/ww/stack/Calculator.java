package com.ww.stack;

/**
 * 计算器实现类
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "70+20*6-2";
        //创建两个栈，数栈和符号栈
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);
        //定义相关变量
        int index = 0;//用于扫描字符串的索引
        int num1 = 0;
        int num2 = 0;
        int res = 0;
        int oper = 0;//操作符
        char ch = ' ';//将每次扫描的char保存到ch
        String keepNum = "";
        //开始while循环扫描expression
        while (true){
            //依次得到expression的每一个字符
            ch = expression.substring(index,index+1).charAt(0);
            //判断ch是什么，做相应的处理
            if (isOper(ch)){
                //判断符号栈是否为空
                if (operStack.isEmpty()){
                    //为空，直接入栈
                    operStack.push(ch);
                }else {
                    //不为空，进行比较，如果当前操作符的优先级小于或者等于栈中的操作符，就需要从数栈中pop出两个数
                    //再从符号栈中pop出一个符号，进行运算，将得到的结果押入数栈，然后将当前符号入符号栈
                    if (prioity(ch) <= prioity(operStack.pick())){
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = cal(num1,num2,oper);
                        numStack.push(res);
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                }
            }else {
                //因为得到的是字符串，他的ASCII码值和真实数字的值之间相差48，所以这里为了得到真实数字，需要-48
                keepNum += ch;
                //如果ch是最后一位，直接入栈
                if (index == expression.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                }else {
                    //判断下一个字符是不是数字，如果是数字，则继续扫描，如果是运算符，则入栈
                    if (isOper(expression.substring(index+1,index+2).charAt(0))){
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = "";
                    }
                }
            }
            index ++;
            //判断是否已经是字符串的最后了
            if (index >= expression.length()){
                break;
            }
        }
        //当表达式扫描完毕，就顺序从数栈和符号栈中pop出相应的数和符号进行运算
        while(true){
            if (operStack.isEmpty()){
                //符号栈为空，计算结束，此时数栈中只有一个数字，这就是结果
                break;
            }else {
                num1 = numStack.pop();
                num2 = numStack.pop();
                oper = operStack.pop();
                res = cal(num1,num2,oper);
                numStack.push(res);
            }
        }

        System.out.printf("表达数%s = %d",expression,numStack.pop());
    }

    /**
     * 返回运算符的优先级，数字越大，优先级越高
     * 假设目前只有+、-、*、/四种运算符
     */
    public static int prioity(int oper){
        if (oper == '*' || oper == '/'){
            return 1;
        }else if (oper == '+' || oper == '-'){
            return 0;
        }else {
            return -1;
        }
    }

    /**
     * 判断一个字符是否是运算符
     */
    public static boolean isOper(char val){
        return val == '+' || val == '-' || val == '*' || val == '/';
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
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                //注意顺序，后面的数字减去前面的数字
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}



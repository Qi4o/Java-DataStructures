package stack;

//有缺陷的计算器

public class Calculator {
    public static void main(String[] args) {
        String expression = "70+2*6-4";

        //创建两个栈，一个数字栈，一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        //定义需要的相关变量
        int index = 0;  //用于扫描
        int num1 = 0, num2 = 0, oper = 0, res = 0;
        char ch = ' ';  //将每次扫描得到的char保存到ch;
        String keepNum = ""; //用与拼接多位数

        //用while语句循环扫描expression
        while (true){
            //依次得到expression的每一个字符
            ch = expression.substring(index, index+1).charAt(0);
            //判断ch是什么，然后做相应的处理
            if(operStack.isOper(ch)){   //如果是运算符
                //判断当前的符号栈是否为空
                if(!operStack.isEmpty()) {   //不为空
                    //如果符号栈有操作符，就进行比较，如果当前的操作符优先级小于或者等于栈中的操作符，就需要从数字栈中pop出两个数字
                    //再从符号栈中pop出一个符号，进行运算，将得到的结果再入数字栈，然后将当前的操作符入符号栈
                    if(operStack.priority(ch) <= operStack.priority(operStack.peek())){
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, (char) oper);
                        //把运算结果入数字栈
                        numStack.push(res);
                        //在把当前的符号符入符号栈
                        operStack.push(ch);
                    } else {
                        //如果当前的符号优先级大于栈中的操作符，就直接入符号栈
                        operStack.push(ch);
                    }
                } else {    //为空直接入栈
                    //如果为空，直接入符号栈
                    operStack.push(ch);
                }
            } else {    //如果是数字，直接入数字栈
                //numStack.push(ch-48); //只能处理一位数字

                //1.当处理多位数时，不能发现是一个数就立即入栈，可能是多位数
                //2.在处理数时，需要想expression的表达式的index后再看一位，如果是数字就继续扫描，一直到扫描到符号为止
                //3.定义一个字符串变量用于拼接数字

                //处理多位数
                keepNum += ch;

                //如果ch已经是expression的最后一位，就直接入栈
                if(index == expression.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    //判断下一个字符是不是数字，如果是数字继续扫描，如果是运算符就入栈
                    if(operStack.isOper(expression.substring(index+1,index+2).charAt(0))) {
                        //如果后一位是运算符则入栈
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = "";   //清空keepNum
                    }
                }

            }

            //让index++，并判断是否扫描到expression最后
            index++;
            if(index >= expression.length()){
                break;
            }
        }

        //顺序出栈会导致表达式逆序计算
        //当表达式扫描完毕，就顺序的从数字栈和符号栈中pop出相应的数字和符号，并运算
        while(true){
            //如果符号栈为空，则计算到最后的结果,数字栈中只有一个数字
            if(operStack.isEmpty()){
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, (char) oper);
            //把运算结果入数字栈
            numStack.push(res);
        }

//        while (true){
//            if (numStack.isEmpty()){
//                break;
//            }
//            numsStack.push(numStack.pop());
//        }
//        while (true){
//            if (operStack.isEmpty()){
//                break;
//            }
//            opersStack.push(operStack.pop());
//        }
//
//        while(true){
//            if(opersStack.isEmpty()){
//                break;
//            }
//            num1 = numsStack.pop();
//            num2 = numsStack.pop();
//            oper = opersStack.pop();
//            res = numsStack.cal(num1,num2,(char)oper);
//            numsStack.push(res);
//        }
        //将数字栈的最后一个数pop出来就是结果
        System.out.println("表达式 " + expression + " = " + numStack.pop());
    }
}


class ArrayStack2 {
    private int maxSize;    //栈的大小
    private int[] stack;    //数组模拟栈，数据放在数组里
    private int top = -1;   //top表示栈顶，初始化为-1

    public ArrayStack2(int maxSize){
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //查看栈的大小
    public int length(){
        return top;
    }

    //查看栈顶数据
    public int peek(){
        return stack[top];
    }

    //栈满
    public boolean isFull(){
        return top == maxSize-1;
    }

    //栈空
    public boolean isEmpty(){
        return top == -1;
    }

    //入栈
    public void push(int value){
        //先判断栈是否满
        if(isFull()){
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    //出栈
    public int pop(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,没有数据");
        }
        int value = stack[top];
        top--;
        return value;
    }

    //遍历栈
    public void list(){
        if(isEmpty()){
            System.out.println("栈空，无法显示");
            return;
        }
        //遍历
        for (int i = top; i >= 0 ; i--) {
            System.out.printf("stack[%d] = %d \n", i, stack[i]);
        }
    }

    //返回运算符的优先级，优先级是由自己定义的，优先级用数字表示
    //数字越大，优先级越高
    public int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        } else if (oper == '+' || oper == '-'){
            return 0;
        } else {
            return -1;  //假定目前表达式只有 + - * /
        }
    }

    //判断是否为一个运算符
    public boolean isOper(char val){
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    //计算方法
    public static int cal(int num1, int num2, char oper) {
        int res = 0;    //存放计算的结果
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;  //注意顺序
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;  //注意顺序
                break;
            default:
                break;
        }
        return res;
    }


}

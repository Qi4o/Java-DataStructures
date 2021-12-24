package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author Qiao
 * @Create 2021/12/16 14:29
 */

//逆波兰计算器
public class PolandNotation {
    public static void main(String[] args) {

//        String expression = "1+((22+3)*4)-5";
//        System.out.println(toInfixExpressionList(expression));
//        // 先定义一个逆波兰表达式
//        // (3+4)*5-6  => 3 4 + 5 * 6 -
//        // 为了方便，逆波兰表达式的数字和符号使用空格隔开
//        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
//
//        // 1.先将 "3 4 + 5 * 6 -" 放到ArrayList中
//        // 2.将 ArrayList 传递给一个方法，遍历 Array List 配合栈完成计算
//        List<String> rpnList = getListString(suffixExpression);
//        System.out.println(calculate(rpnList));
        String expression = "1+((22+3)*4)-5";
        System.out.println(calculate(parseSuffixExpressionList(toInfixExpressionList(expression))));

    }


    //中缀表达式转成List
    public static List<String> toInfixExpressionList(String s){
        //定义一个List，存放中缀表达式对应的内容
        List<String> ls = new ArrayList<>();
        int i = 0;
        String str; //对多位数的拼接
        char c; //每遍历一个字符就放入c中
        do{
            if((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57){
                ls.add(c+"");
                i++;
            } else {    //如果是数字，需要考虑多位数的问题
                str = "";   //先将str置空  '0'[48] -- '9'[57]
                while(i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57){
                    str += c;   //拼接操作
                    i++;
                }
                ls.add(str);
            }
        } while (i < s.length());

        return ls;
    }

    // 中缀转后缀
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        //定义两个结构
        Stack<String> s1 = new Stack<>();   //符号栈
        List<String> s2 = new ArrayList<>();     //存储中间结果

        //遍历ls
        for(String item : ls){
            //如果是数字就入s2
            if(item.matches("\\d+")){
                s2.add(item);
            } else if (item.equals("(")){
                s1.push(item);
            } else if (item.equals(")")){
                // 如果是右括号")"， 则依次弹出s1栈顶的运算符，并压入s2
                // 直到遇到左括号为止，然后将括号丢弃
                while(!s1.peek().equals("(")){
                    s2.add(s1.pop());
                }
                s1.pop();   //将"("弹出s1栈，消除小括号
            } else {
                //当item的优先级小于等于栈顶的运算符时
                //将s1栈顶的运算符弹出并加入s2中
                //再次与新的栈顶运算符比较
                while (s1.size() != 0 && operation(s1.peek()) >= operation(item)){
                    s2.add(s1.pop());
                }
                //将item压入栈
                s1.push(item);
            }
        }

        //将s1中剩余的运算符弹出并加入s2
        while (s1.size() != 0){
            s2.add(s1.pop());
        }

        return s2;  //因为是存入List中的，所以不需要逆序输出
    }

    //判断符号优先级
    public static int operation(String operation){
        switch(operation){
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
        }
        return 0;
    }

    //将一个逆波兰表达式，依次将数据和运算符放到ArrayList中
    public static List<String> getListString(String suffixExpression){
        // 将 suffixExpression 分割
        String[] split =  suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String ele : split){
            list.add(ele);
        }
        return list;
    }

    //完成对逆波兰表达式的运算
    public static int calculate(List<String> ls){
        //创建栈,只需要一个栈
        Stack<String> stack = new Stack<>();
        //遍历ls
        for (String item : ls){
            //使用正则表达式取出数
            if (item.matches("\\d+")){   //匹配多位数字
                //直接入栈
                stack.push(item);
            } else {
                //pop出两个数字并运算,再进行运算
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if(item.equals("+")){
                    res = num1 + num2;
                }else if(item.equals("-")){
                    res = num1 - num2;
                }else if(item.equals("*")){
                    res = num1 * num2;
                }else if(item.equals("/")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("运算符有误");
                }
                //把结果入栈
                stack.push(res+"");
            }
        }
        // 最后留在stack中的就是结果
        return Integer.parseInt(stack.pop());
    }
}

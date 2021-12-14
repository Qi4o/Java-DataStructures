package queue;

import java.util.Scanner;

public class ArrayQueue {
    public static void main(String[] args) {
        ArrQueue queue = new ArrQueue(3);
        char key = ' '; //接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        while(loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据");
            System.out.println("g(get):取出数据");
            System.out.println("h(head):查看队列头");
            key = scanner.next().charAt(0);
            switch (key){
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数字");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try{
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:{
                    System.out.println("输入的字符不对");
                    break;
                }
            }
        }
        System.out.println("程序已退出");
    }
}

//使用数组模拟队列
class ArrQueue{
    private int maxSize; //表示数组最大容量
    private int front;  //表示队列头部
    private int rear;   //表示队列尾部
    private int[] arr;  //该数组用于存放数据，模拟队列

    //创建队列构造器
    public ArrQueue(int maxSize){
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
        front = -1; //指向队列头部，分析出front是指向队列的前一个位置
        rear = -1;  //指向队列尾部，直接指向队列的尾部（即就是队列最好一个数据）
    }

    //判断队列是否为满
    public boolean isFull(){
        return rear == maxSize-1;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        return front == rear;
    }

    //添加数据到队列
    public void addQueue(int n){
        //判断队列是否满
        if(isFull()){
            System.out.println("队列已满，不能再加入数据");
            return;
        }
        rear++; //让rear后移
        arr[rear] = n;
    }

    //获取队列的数据，出队列
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法取出数据"); //抛异常
        }
        front++;    //让front后移
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列为空，没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //显示队列的头数据， 不是取出数据
    public int headQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，没有数据");
        }
        return arr[front+1];
    }
}

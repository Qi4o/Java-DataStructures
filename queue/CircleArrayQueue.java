package queue;

import java.util.Scanner;

public class CircleArrayQueue {
    public static void main(String[] args) {
        CircleArrQueue queue = new CircleArrQueue(3);
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


class CircleArrQueue{
    private int maxSize;    //表示数组的最大容量
    //front变量的一个调整 front就指向队列的第一个元素，也就是arr[front];
    //front的初始值 = 0
    private int front;      //队列头
    private int rear;       //队列尾
    private int[] arr;      //该数据用于存放数据，模拟队列


    public CircleArrQueue(int maxSize){
        this.maxSize = maxSize+1;   //队列有一位空值
        arr = new int[this.maxSize];
    }

    //判断队列是否为满
    public boolean isFull(){
        return (rear+1) % maxSize == front;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据
    public void addQueue(int n){
        if(isFull()){
            System.out.println("队列已满，不能添加数据");
            return;
        }
        //直接将数据加入
        arr[rear] = n;
        //将rear后移一位
        rear = (rear+1) % maxSize;
    }

    //取出数据
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，不能取出数据");
        }
        //要先分析出front是指向队列的第一个元素
        //1.先把front对应的值保存到一个临时的变量
        //2.将front后移
        //3.将临时保存的变量返回
        int temp = arr[front];
        front = (front+1)%maxSize;
        return temp;
    }

    //显示队列所有数据
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列为空，没有数据显示");
            return;
        }
        //遍历终止条件可改为rear > front ? rear : rear + maxSize
        for (int i = front; i < front + this.size(); i++) {
            System.out.printf("arr[%d] = %d \n", i % maxSize, arr[i%maxSize]);
        }
    }

    //求出当前队列有效数据个数
    public int size(){
        return (rear+maxSize-front)%maxSize;
    }

    //显示头元素
    public int headQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，没有数据");
        }
        return arr[front];
    }
}









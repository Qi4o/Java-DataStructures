package linkedlist;


import java.util.Stack;

public class Josephus {
    public static void main(String[] args) {
        //测试
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        //添加节点
        circleSingleLinkedList.add(5);
        //遍历节点
        circleSingleLinkedList.show();

        //测试出圈
        System.out.println("--------------");
        circleSingleLinkedList.countBoy(1, 2, 5);

        Stack stack = new Stack();
    }
}


//创建环形单向链表
class CircleSingleLinkedList{
    //创建first节点
    public Boy first = new Boy(-1);

    //添加节点，构建成环形链表
    public void add(int nums){
        //nums数据校验
        if(nums < 1){
            System.out.println("数据不正确");
            return;
        }

        Boy curBoy = null;  //辅助指针，帮助构建环形链表
        //使用循环创建环形链表
        for (int i = 1; i <= nums; i++) {
            //根据编号，创建节点
            Boy boy = new Boy(i);
            //如果是第一个
            if(i == 1){
                first = boy;
                first.next = first; //构成环状
                curBoy = first; //让curBoy指向第一个节点
            } else {
                curBoy.next = boy;
                boy.next = first;
                curBoy = boy;
            }
        }
    }

    //遍历当前环形链表
    public void show(){
        //判断是否为空
        if(first == null){
            System.out.println("链表为空");
            return;
        }
        //first不能动，因此我们使用一个辅助指针完成遍历
        Boy curBoy = first;
        while (true){
            System.out.println(curBoy.no);
            if(curBoy.next == first){   //遍历完毕
                break;
            }
            curBoy = curBoy.next;
        }
    }

    //出节点顺序
    public void countBoy(int starNo, int countNum, int nums){
        //先对数据进行检验
        if(first == null || starNo < 1 || starNo > nums){
            System.out.println("参数输入有误");
            return;
        }
        //创建一个辅助指针,帮助移出节点
        Boy helper = first;
        //先让helper指向链表最后
        while (true) {
            if(helper.next == first) {  //说明指向了最后的节点
                break;
            }
            helper = helper.next;
        }

        //先让节点移动到开始的位置
        for (int j = 0; j < starNo-1; j++) {
            first = first.next;
            helper = helper.next;
        }

        //移动countNum就移出
        //循环操作，直到圈中只有一个节点
        while (true) {
            if(helper == first){    //说明圈中只有一个节点了
                break;
            }
            //让first和helper同时移动countNum - 1次，然后移出
            for (int i = 0; i < countNum-1; i++) {
                first = first.next;
                helper = helper.next;
            }
            //这时first指向的节点就是要移出的节点
            System.out.println(first.no);
            //将first移出
            first = first.next;
            helper.next = first;
        }
        System.out.println(first.no);
    }
}


//代表节点
class Boy{
    public int no; //编号
    public Boy next;   //指向下一个

    public Boy(int no){
        this.no = no;
    }
}
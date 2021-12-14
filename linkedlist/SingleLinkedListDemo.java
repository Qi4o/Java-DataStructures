package linkedlist;

import java.util.Stack;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        //创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

//        //加入
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);

        //按排名加入
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero3);

        //测试update
        HeroNode newHeroNode = new HeroNode(2, "小卢", "小尾巴");
        singleLinkedList.update(newHeroNode);

        //显示
        singleLinkedList.list();
        System.out.println();

        //测试remove
        singleLinkedList.remove(hero4);
        singleLinkedList.list();

        //测试有效个数
        System.out.println("有效节点个数：" + getLengths(singleLinkedList.getHead()));
        System.out.println("*****");

        //测试反转
        reverseList(singleLinkedList.getHead());
        singleLinkedList.list();
        System.out.println("&****");

        //逆序打印
        System.out.println("反转打印:");
        reversePrint(singleLinkedList.getHead());
        System.out.println("正常打印:");
        singleLinkedList.list();
    }

    //获取链表节点个数
    public static int getLengths(HeroNode head){
        if(head.next == null){
            return 0;
        }
        int length = 0;
        HeroNode cur = head.next;   //没有统计头节点
        while (cur != null){
            length++;
            cur = cur.next;
        }
        return length;
    }

    //查找单链表中倒数第k个节点
    //思路：先遍历得到总长度， 再遍历总长度减去k次
    //找到返回该节点， 找不到返回null
    public static HeroNode findLastIndexNode(HeroNode head, int index){

        if (head.next == null){
            return null;
        }
        int size = getLengths(head);
        if(index > size || index <= 0){
            System.out.println("输入的值有误");
            return null;
        }else{
            HeroNode cur = head.next;
            for (int i = 0; i < size-index; i++) {
                cur = cur.next;
            }
            return cur;
        }
    }

    //单链表的反转
    //思路
    //1.先定义一个节点reverseHead = new HeroNode();
    //2.从头到尾遍历原来的链表,每遍历一个节点,就将其取出,并放在新的链表的最前端
    //3.原来链表的head.next = reverseHead.next;
    public static void reverseList(HeroNode head){
        //如果当前链表为空或者只有一个节点，无需反转，直接返回
        if(head.next == null || head.next.next == null){
            return;
        }
        //定义一个辅助指针变量，帮助遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null;   //指向当前节点[cur]的下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        //从头到尾遍历原来的链表,每遍历一个节点,就将其取出,并放在新的链表的最前端
        while (cur != null){
            next = cur.next;    //先暂时保存当前节点的下一个节点,后面需要使用
            cur.next = reverseHead.next;  //将cur的下一个节点指向新的链表的最前端
            reverseHead.next = cur;
            cur = next; //让cur后移
        }
        //将head.next指向reverseHead.next
        head.next = reverseHead.next;
    }

    //逆序打印链表,没有改变链表本身的情况
    public static void reversePrint(HeroNode head){
        if(head.next == null){
            return; //空链表,不能打印
        }
        //创建一个栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        //将链表所有节点入栈
        while (cur != null){
            stack.push(cur);
            cur = cur.next;
        }
        //出栈打印
        while (stack.size() > 0){
            System.out.println(stack.pop());    //出栈
        }
    }

}

//定义SingleLinkedList管理对象
class SingleLinkedList{
    //先定义一个头节点，头节点不要动,不存放具体数据
    private HeroNode head = new HeroNode(0, "", "");

    //返回头节点
    public HeroNode getHead(){
        return head;
    }

    public void setHead(HeroNode head){
        this.head = head;
    }
    //添加节点到单向链表
    //思路，当不考虑编号顺序时
    //1.找到当前链表的最后节点
    //2.将最后这个节点next指向新的节点
    public void add(HeroNode heroNode){
        //因为head节点不能动,因此我们需要一个辅助指针temp
        HeroNode temp = head;
        //遍历链表，找到最后
        while (temp.next != null){
            temp = temp.next;
        }
        temp.next = heroNode;
    }

    //按照排名添加（如果排名重复则添加失败并给出提醒)
    public void addByOrder(HeroNode heroNode){
        HeroNode temp = head;
        boolean flag = false;
        while(true){
            if(temp.next == null){  //temp已经到链表最后
                break;
            }
            if(temp.next.no > heroNode.no){
                break;
            } else if(temp.next.no == heroNode.no){ //排名已经存在
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //判断flag的值看是否添加
        if(flag){   //排名已存在，不能添加
            System.out.printf("插入的英雄排名%d已存在，不能添加\n", heroNode.no);
        }else{
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点信息，根据no排名来修改，no排名不能更改
    public void update(HeroNode newHeroNode){
        //判断是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点
        HeroNode temp = head.next;  //辅助节点
        boolean flag = false;   //代表是否找到
        while(true){
            if(temp == null){   //链表遍历已经结束
                break;
            }
            if(temp.no == newHeroNode.no){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到了需要修改的节点
        if(flag){
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            System.out.printf("没有找到排名为%d的节点", newHeroNode.no);
        }
    }

    //删除链表节点
    public void remove(HeroNode heroNode){
        HeroNode temp = head;

        while(true){
            if(temp.next == null){  //已经到链表最后
                System.out.println("没有找到要删除的列表");
                return;
            }

            if(temp.next.no == heroNode.no){
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
    }

    //遍历显示链表
    public void list(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }

        HeroNode temp = head.next;
        while(true){
            //判断是否到链表最后
            if (temp == null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

}


//先定义一个HeroNode， 每个HeroNode对象就是一个节点
class HeroNode{
    public int no;  //编号
    public String name; //姓名
    public String nickname; //外号
    public HeroNode next;   //下一个节点

    public HeroNode(int no, String name, String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //重写toString
    @Override
    public String toString(){
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }
}
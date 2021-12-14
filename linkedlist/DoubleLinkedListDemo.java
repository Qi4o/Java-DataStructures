package linkedlist;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //测试
        System.out.println("双向链表测试");

        //创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        //创建双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);
        doubleLinkedList.list();

        //修改测试
        System.out.println("------------------修改测试");
        HeroNode2 newHeroNode = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(newHeroNode);
        doubleLinkedList.list();

        //删除测试
        System.out.println("------------------删除测试");
        doubleLinkedList.del(3);
        doubleLinkedList.list();


    }
}

//创建一个双向链表
class DoubleLinkedList{
    //初始化
    private HeroNode2 head = new HeroNode2(0, "", "");
    //返回头节点
    public HeroNode2 getHead(){
        return head;
    }

    //遍历双向链表
    public void list(){
        //判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        while (true){
            if(temp == null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    //尾插
    public void add(HeroNode2 heroNode2){
        HeroNode2 temp = head;
        //遍历到最后
        while (true){
            if(temp.next == null){
                break;
            }
            temp = temp.next;
        }
        //形成一个双向链表
        temp.next = heroNode2;
        heroNode2.pre = temp;
    }

    //修改一个节点的内容,可以看到双向链表的内容修改和但链表修改一样，只是节点类型不一样
    public void update(HeroNode2 newHeroNode){
        //判断是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点，根据no编号
        //定义一个辅助变量
        HeroNode2 temp = head.next;
        boolean flag = false; //表示是否找到该节点
        while(true){
            if(temp == null){   //遍历完没找到退出
                break;
            }
            if(temp.no == newHeroNode.no){  //找到
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到需要更改的节点
        if (flag){
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        }else{
            System.out.printf("没有找到编号为%d的节点，不能修改\n", newHeroNode.no);
        }
    }

    //删除节点
    //1.对于双向链表，我们可以直接找到需要删除的这个节点
    //2.找到之后直接自我删除
    public void del(int no){
        //判断当前链表是否为空
        if(head.next == null){
            System.out.println("链表为空，无法删除");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true){
            if(temp == null){   //没找到
                break;
            }
            if (temp.no == no){ //找到
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag){
            //temp.next = temp.next.next; //单向链表的删除
            temp.pre.next = temp.next;
            if(temp.next != null){  //防止删除最后一个越界
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.println("没有找到需要删除的节点");
        }
    }


}

//先定义一个HeroNode， 每个HeroNode对象就是一个节点
class HeroNode2{
    public int no;  //编号
    public String name; //姓名
    public String nickname; //外号
    public HeroNode2 next;   //下一个节点
    public HeroNode2 pre;    //上一个节点

    public HeroNode2(int no, String name, String nickname){
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
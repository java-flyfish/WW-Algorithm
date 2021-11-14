package com.ww.search;

/**
 * 二叉树示例
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        //先创建一个二叉树
        HeroNode n1 = new HeroNode(1,"宋江");
        HeroNode n2 = new HeroNode(2,"无用");
        HeroNode n3 = new HeroNode(3,"卢俊义");
        HeroNode n4 = new HeroNode(4,"林冲");
        HeroNode n5 = new HeroNode(5,"张三");

        n1.setLeftNode(n2);
        n2.setLeftNode(n3);
        n1.setRightNpde(n4);
        n4.setLeftNode(n5);

        BinaryTree tree = new BinaryTree();
        tree.setRoot(n1);
        tree.postOrder();

        HeroNode heroNode = tree.preOrderSearch(1);
        System.out.println(heroNode);
    }
}

class BinaryTree{
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void delNode(int no){
        if (root == null){
            return;
        }
        if (this.root.getNo() == no){
            this.root = null;
        }else {
            this.root.delNode(no);
        }
    }

    /**
     * 前
     */
    public void preOrder(){
        if (root != null){
            root.preOrder();
        }
    }

    /**
     * 中
     */
    public void infixOrder(){
        if (root != null){
            root.infixOrder();
        }
    }

    /**
     * 后
     */
    public void postOrder(){
        if (root != null){
            root.postOrder();
        }
    }

    /**
     * 前
     */
    public HeroNode preOrderSearch(int no){
        if (root != null){
            return root.preOrderSearch(no);
        }
        return null;
    }

    /**
     * 中
     */
    public HeroNode infixOrderSearch(int no){
        if (root != null){
            return root.infixOrderSearch(no);
        }
        return null;
    }

    /**
     * 后
     */
    public HeroNode postOrderSeaech(int no){
        if (root != null){
            return root.postOrderSearch(no);
        }
        return null;
    }
}

class HeroNode{
    private int no;
    private String name;

    private HeroNode leftNode;
    private HeroNode rightNpde;

    public HeroNode(int no,String name){
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(HeroNode leftNode) {
        this.leftNode = leftNode;
    }

    public HeroNode getRightNpde() {
        return rightNpde;
    }

    public void setRightNpde(HeroNode rightNpde) {
        this.rightNpde = rightNpde;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    public void delNode(int no){
        if (this.leftNode != null && this.leftNode.no == no){
            this.leftNode = null;
            return;
        }
        if (this.rightNpde != null && this.rightNpde.no == no){
            this.rightNpde = null;
            return;
        }

        if (this.leftNode != null){
            this.leftNode.delNode(no);
        }
        if (this.rightNpde != null){
            this.rightNpde.delNode(no);
        }
    }
    /**
     * 前序遍历当方法
     */
    public void preOrder(){
        System.out.println(this);
        if (this.leftNode != null){
            this.leftNode.preOrder();
        }
        if (this.rightNpde != null){
            this.rightNpde.preOrder();
        }
    }
    /**
     * 中序遍历当方法
     */
    public void infixOrder(){
        if (this.leftNode != null){
            this.leftNode.infixOrder();
        }
        System.out.println(this);
        if (this.rightNpde != null){
            this.rightNpde.infixOrder();
        }
    }
    /**
     * 后续遍历当方法
     */
    public void postOrder(){
        if (this.leftNode != null){
            this.leftNode.postOrder();
        }
        if (this.rightNpde != null){
            this.rightNpde.postOrder();
        }
    }

    /**
     * 前序遍历当方法
     */
    public HeroNode preOrderSearch(int no){
        if (this.no == no){
            return this;
        }
        if (this.leftNode != null){
            return this.leftNode.preOrderSearch(no);
        }
        if (this.rightNpde != null){
            return this.rightNpde.preOrderSearch(no);
        }
        return null;
    }
    /**
     * 中序遍历当方法
     */
    public HeroNode infixOrderSearch(int no){
        if (this.leftNode != null){
            return this.leftNode.infixOrderSearch(no);
        }
        if (this.no == no){
            return this;
        }
        if (this.rightNpde != null){
            return this.rightNpde.infixOrderSearch(no);
        }
        return null;
    }
    /**
     * 后续遍历当方法
     */
    public HeroNode postOrderSearch(int no){
        if (this.leftNode != null){
            return this.leftNode.postOrderSearch(no);
        }
        if (this.rightNpde != null){
            return this.rightNpde.postOrderSearch(no);
        }
        if (this.no == no){
            return this;
        }
        return null;
    }
}
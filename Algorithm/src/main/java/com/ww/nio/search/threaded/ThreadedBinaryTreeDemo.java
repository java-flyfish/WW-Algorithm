package com.ww.nio.search.threaded;

public class ThreadedBinaryTreeDemo {

    public static void main(String[] args) {
        HeroNode n1 = new HeroNode(1,"tom");
        HeroNode n2 = new HeroNode(3,"jack");
        HeroNode n3 = new HeroNode(6,"swtsh");
        HeroNode n4 = new HeroNode(8,"mary");
        HeroNode n5 = new HeroNode(10,"king ");
        HeroNode n6 = new HeroNode(14,"dim");

        n1.setLeftNode(n2);
        n1.setRightNpde(n3);
        n2.setLeftNode(n4);
        n2.setRightNpde(n5);
        n3.setLeftNode(n6);

        //测试线索化
        ThreadedBinaryTree tree = new ThreadedBinaryTree();
        tree.setRoot(n1);
        tree.threadedNodes();

        HeroNode leftNode = n5.getLeftNode();
        HeroNode rightNpde = n5.getRightNpde();
        System.out.println(leftNode);
        System.out.println(rightNpde);

        tree.threadedList();

    }
}

class ThreadedBinaryTree{
    private HeroNode root;

    //为了实现线索化，需要有一个指向前驱节点的引用
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void threadedList(){
        HeroNode node = root;
        while(node != null){
            //循环找到leftType=1的节点，这是最左边的节点
            while (node.getLeftType() == 0){
                node = node.getLeftNode();
            }
            //找到最左边的节点，直接打印
            System.out.println(node);

            //如果右节点是后继节点，就一直输出
            while(node.getRightType() == 1){
                node = node.getRightNpde();
                System.out.println(node);
            }
            node = node.getRightNpde();
        }
    }
    /**
     * 线索化二叉树方法
     */
    public void threadedNodes(){
        this.threadedNodes(root);
    }
    /**
     * 线索化二叉树方法
     * @param node 当前需要线索化的节点
     */
    public void threadedNodes(HeroNode node){
        if(node == null){
            return;
        }
        //中序线索化
        //线索化前驱节点
        threadedNodes(node.getLeftNode());
        //线索化当前节点
        if (node.getLeftNode() == null){
            node.setLeftNode(pre);
            //指向前驱节点
            node.setLeftType(1);
        }

        //处理后继节点
        if (pre != null && pre.getRightNpde() == null){
            pre.setRightNpde(node);
            pre.setRightType(1);
        }

        //把前面的节点设置成当前节点
        pre = node;
        //线索化后续节点
        threadedNodes(node.getRightNpde());
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

    //0指向左子树，1指向前驱节点
    private int leftType;
    //0指向右子树，1指向后继节点
    private int rightType;

    public HeroNode(int no,String name){
        this.no = no;
        this.name = name;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
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
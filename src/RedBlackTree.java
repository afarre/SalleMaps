public class RedBlackTree<T> {
    private MyList<RBTNode> tree;

    public RedBlackTree(){

    }

    public RedBlackTree(MyList<T> list){
        tree = new MyList<>();
        for (int i = 0; i < list.size(); i++){
            RBTNode<T> node = new RBTNode<>(list.get(i));
            insert(node);
        }
    }

    public void insert(RBTNode<T> node){
        //Añadir relaciones = mirar casos y rotaciones
        //TODO: MIRAR DONDE DEBE COLOCARSE EL SIGUIENTE NODE
        tree.add(node);
        checkRotations(node);
    }


    private void checkRotations(RBTNode node) {
        RBTNode parent = node.getParent();
        RBTNode grandDad = parent.getParent();
        RBTNode uncle = null;
        if (grandDad.getLeft().equals(parent)){
            uncle = grandDad.getRight();
        }else{
            uncle = grandDad.getLeft();
        }

        //caso 1:
        if (uncle.getColour() == 0){
            grandDad.setColour((byte) 0);;
            parent.setColour((byte) 1);
            uncle.setColour((byte) 1);
            node.setColour((byte) 0);
            checkRotations(grandDad);
            checkRotations(parent);
        }
        if (!(grandDad.getColour() == 1)){

            //caso 2a:
            if (grandDad.getColour() == 1 && parent.getRight().equals(node) && uncle.getLeft().equals(parent)){
                grandDad.setLeft(node);
                node.setLeft(parent);
                checkRotations(grandDad);
                checkRotations(parent);
            }

            //caso 2b:
            if (grandDad.getColour() == 1 && parent.getRight().equals(node) && uncle.getLeft().equals(parent)){
                grandDad.setLeft(node);
                node.setLeft(parent);
                checkRotations(grandDad);
                checkRotations(parent);
            }

            //caso 3a:
            if (grandDad.getColour() == 1 && grandDad.getLeft().equals(parent) && parent.getLeft().equals(node)){
                parent.setLeft(node);
                parent.setRight(grandDad);
                parent.setColour((byte) 1);
                grandDad.setColour((byte) 0);
                checkRotations(grandDad);
                checkRotations(parent);
            }

            //caso 3b:
            if (grandDad.getColour() == 1 && parent.getRight().equals(node) && grandDad.getRight().equals(parent)){
                parent.setColour((byte) 1);
                parent.setRight(node);
                parent.setLeft(grandDad);
                grandDad.setColour((byte) 0);
                checkRotations(grandDad);
                checkRotations(parent);
            }

        }else{
            //el padrí es null per lo que es un node fulla
            System.out.println("Es un node fulla.");
        }
    }

    private class RBTNode <T>{
        private T t;
        private byte colour; //0 = Red, 1 = Black
        private RBTNode right;
        private RBTNode left;
        private RBTNode parent;

        public RBTNode (T t){
            this.t = t;
            colour = 0;
            right = null;
            left = null;
            parent = null;
        }

        public void setRight(RBTNode right) {
            this.right = right;
        }

        public void setLeft(RBTNode left) {
            this.left = left;
        }

        public void setParent(RBTNode parent) {
            this.parent = parent;
        }

        public void setColour(byte colour) {
            this.colour = colour;
        }

        public byte getColour() {
            return colour;
        }

        public RBTNode getRight() {
            return right;
        }

        public RBTNode getLeft() {
            return left;
        }

        public RBTNode getParent() {
            return parent;
        }
    }
}

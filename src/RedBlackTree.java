public class RedBlackTree {
    private MyList<RBTNode> tree;

    public RedBlackTree(){

    }

    public RedBlackTree(MyList<CityModel> list){
        tree = new MyList<>();
        for (int i = 0; i < list.size(); i++){
            RBTNode node = new RBTNode(list.get(i));
            insert(node);
        }
    }

    public void add (CityModel e){
        insert(new RBTNode(e));
    }

    public void insert(RBTNode node){
        //Añadir relaciones = mirar casos y rotaciones
        wheregoes(node);
        tree.add(node);
        checkRotations(node);
    }

    private void wheregoes (RBTNode node){
        RBTNode first = tree.get(0);
        boolean placed  = false;

        while (!placed) {
            if (Integer.parseInt(String.valueOf(node.getCityModel().getName().charAt(0))) -
                    Integer.parseInt(String.valueOf(first.getCityModel().getName().charAt(0))) > 0) {
                if (first.getRight() != null){
                    first = first.getRight();
                } else {
                    first.setRight(node);
                    node.setParent(first);
                    placed = true;
                }
            } else {
                if (first.getLeft() != null){
                    first = first.getLeft();
                } else {
                    first.setLeft(node);
                    node.setParent(first);
                    placed = true;
                }
            }
        }

        if (Integer.parseInt(String.valueOf(node.getCityModel().getName().charAt(0))) -
                Integer.parseInt(String.valueOf(first.getCityModel().getName().charAt(0))) == 0){
            if (Integer.parseInt(String.valueOf(node.getCityModel().getName().charAt(1))) -
                    Integer.parseInt(String.valueOf(first.getCityModel().getName().charAt(1))) >= 0) {
                if (first.getRight() != null){
                    first = first.getRight();
                } else {
                    first.setRight(node);
                    node.setParent(first);
                    placed = true;
                }
            } else {
                if (first.getLeft() != null){
                    first = first.getLeft();
                } else {
                    first.setLeft(node);
                    node.setParent(first);
                    placed = true;
                }
            }
        }
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

    private class RBTNode {
        private CityModel cm;
        private byte colour; //0 = Red, 1 = Black
        private RBTNode right;
        private RBTNode left;
        private RBTNode parent;

        public RBTNode (CityModel cm){
            this.cm = cm;
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

        public CityModel getCityModel() {
            return cm;
        }
    }
}

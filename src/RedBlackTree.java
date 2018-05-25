public class RedBlackTree {
    private MyList<RBTNode> tree;

    public RedBlackTree(){

    }

    public RedBlackTree(MyList<Node> list){
        tree = new MyList<>();
        for (int i = 0; i < list.size(); i++){
            RBTNode node = new RBTNode(list.get(i));
            insert(node);
        }
    }

    public void add (Node e){
        insert(new RBTNode(e));
    }

    public void insert(RBTNode node){
        //Añadir relaciones = mirar casos y rotaciones
        wheregoes(node);
        tree.add(node);
        //checkRotations(node);
    }

    private int nameToInt(Node n){
        String name = n.getCity().getName().toLowerCase();
        return (int) name.charAt(0) * 4 + (int) name.charAt(1) * 2 +(int) name.charAt(2);
    }

    private void wheregoes (RBTNode node){
        RBTNode first = tree.get(0);
        boolean placed  = false;

        if (first == null){
            placed = true;
        }

        while (!placed) {
            if (nameToInt(node.getNode()) >= nameToInt(first.getNode())) {
                if (first.getRight() != null) {
                    first = first.getRight();
                } else {
                    first.setRight(node);
                    node.setParent(first);
                    placed = true;
                }
            } else {
                if (first.getLeft() != null) {
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

        //TODO: comprobar que existem los nodos antes de comprobar las rotaciones

        //caso 1:
        if (uncle.getColour() == 0){
            grandDad.setColour((byte) 0);;
            parent.setColour((byte) 1);
            uncle.setColour((byte) 1);
            node.setColour((byte) 0);
            checkRotations(grandDad);
            checkRotations(parent);
        }
        if (uncle == null){

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

    public boolean searchCity(Node node) {
        RBTNode first = tree.get(0);
        while (true) {
            if (node.getCity().getName().equals(first.getNode().getCity().getName())){
                return true;
            }
            if (nameToInt(node) >= nameToInt(first.getNode())) {
                if (first.getRight() != null) {
                    first = first.getRight();
                } else {
                    return false;
                }
            } else {
                if (first.getLeft() != null) {
                    first = first.getLeft();
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    public boolean searchRoute(String from, String to, int type) {
        return false;
    }

    public void calculateRoute(String from, String to, int type) {
        if (type == 1){
            //shortest route

        }else if (type == 2){
            //fastest route

        }
    }

    private class RBTNode {
        private Node node;
        private byte colour; //0 = Red, 1 = Black
        private RBTNode right;
        private RBTNode left;
        private RBTNode parent;

        public RBTNode (Node node){
            this.node = node;
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

        public Node getNode() {
            return node;
        }
    }
}

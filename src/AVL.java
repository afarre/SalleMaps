public class AVL {
    private AVLNode node;

    public AVL()  {

    }

    public AVL(int size){
        for (int i = 0; i < size; i++){
            add(i);
        }
    }

    private void add(int i) {
        if (node == null){
            node = new AVLNode(i, null);
            return;
        }

        AVLNode aux = node;
        AVLNode parent = node;
        while (aux != null){
            if (aux.getElement() > i){
                parent = aux;
                aux = aux.getLeft();
            } else {
                parent = aux;
                aux = aux.getRight();
            }
        }
        AVLNode nuevo = new AVLNode(i, parent);
        checkRotations(nuevo);
    }

    private void checkRotations(AVLNode n) {
        int value = checkHeight(n);
        while (value < 2 && value > -2){
            n = n.getParent();
            if (n == null){
                return;
            } else {
                value = checkHeight(n);
            }
        }
        rotate(n, value);
    }

    private void rotate(AVLNode n, int value) {
        //LR
        if (value == 2 && n.getLeft().getLeft().getLeft() == null && n.getLeft().getLeft().getRight() == null){
            //Nuevo nodo insertado a la izquierda
            if (n.getLeft().getRight().getLeft() != null){
                //1er paso
                n.getLeft().getRight().setRight(n.getLeft().getRight().getLeft());
                n.getLeft().getRight().setLeft(null);
                AVLNode aux = n.getLeft();
                n.setLeft(aux.getRight());
                n.getLeft().setLeft(aux);
                aux.setRight(null);
                //2o paso
                n.getLeft().getLeft().setRight(n.getLeft().getRight());
                n.getLeft().setRight(n);
                n.setLeft(null);
                //Nuevo nodo insertado a la derecha
            }else if (n.getLeft().getRight().getRight() != null){
                //1er paso
                AVLNode aux = n.getLeft();
                n.setLeft(aux.getRight());
                aux.setRight(null);
                n.getLeft().setLeft(aux);
                //2o paso
                AVLNode aux1 = n.getLeft().getRight();
                n.getLeft().setRight(n);
                n.setLeft(aux1);
            }
        //LL
        }else if (value == 2 && n.getLeft().getRight().getRight() == null && n.getLeft().getRight().getLeft() == null){
            AVLNode aux = n.getLeft().getRight();
            n.getLeft().setRight(n);
            n.setLeft(aux);
        //RL
        }else if (value == -2 && n.getRight().getRight().getRight() == null && n.getRight().getRight().getLeft() == null){
            //Nuevo nodo insertado a la izquierda
            if (n.getLeft().getLeft().getLeft() != null){
                //1er paso
                AVLNode aux = n.getRight();
                n.setRight(aux.getLeft());
                aux.setLeft(null);
                n.getRight().setRight(aux);
                //2o paso
                AVLNode aux1 = n.getRight().getLeft();
                n.getRight().setLeft(n);
                n.setRight(aux1);
            //Nuevo nodo insertado a la derecha
            }else if (n.getRight().getLeft().getRight() != null){
                //1er paso
                n.getRight().getLeft().setLeft(n.getRight().getLeft().getRight());
                n.getRight().getLeft().setRight(null);
                AVLNode aux = n.getRight();
                n.setRight(aux.getLeft());
                n.getRight().setRight(aux);
                aux.setLeft(null);
                //2o paso
                n.getRight().getRight().setLeft(n.getRight().getLeft());
                n.getRight().setLeft(n);
                n.setRight(null);
            }

        //RR
        }else if (value == -2 && n.getRight().getLeft().getLeft() == null && n.getRight().getLeft().getRight() == null ){
            AVLNode aux = n.getRight().getLeft();
            n.getRight().setLeft(n);
            n.getLeft().setRight(aux);
        }
    }

    private int checkHeight(AVLNode n) {
        if (n == null){
            return 1;
        }
        return checkHeight(n.getLeft()) - checkHeight(n.getRight());
    }

    private class AVLNode{
        private int element;
        private AVLNode right;
        private AVLNode left;
        private AVLNode parent;

        public AVLNode(int element, AVLNode parent){
            this.element = element;
            this.parent = parent;
        }

        public int getElement() {
            return element;
        }

        public void setElement(int element) {
            this.element = element;
        }

        public AVLNode getRight() {
            return right;
        }

        public void setRight(AVLNode right) {
            this.right = right;
        }

        public AVLNode getLeft() {
            return left;
        }

        public void setLeft(AVLNode left) {
            this.left = left;
        }

        public AVLNode getParent() {
            return parent;
        }

        public void setParent(AVLNode parent) {
            this.parent = parent;
        }
    }
}

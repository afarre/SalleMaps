import java.util.ArrayList;

public class AVL {
    private AVLNode raiz;
    private ArrayList<AVLNode> AVLTree;
    private Graph graph;
    private boolean firstLink;
    private boolean secondLink;

    private final static Integer EARTH_LONG = 400750000;

    public AVL(Graph graph){
        AVLTree = new ArrayList<>();
        int size = graph.size();
        /*for (int i = 0; i < size; i++){
            int value = 0;
            for (char ch: graph.get(i).getCity().getName().replaceAll("\\s+","").toCharArray()) {
                value = value + ch - 'a' + 1;
            }
            System.out.println(value);
            add(value);
        }*/
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        System.out.println("    " + raiz.getElement());
        System.out.println(raiz.getLeft().getElement() + "          " + raiz.getRight().getElement());
        System.out.println("    " + raiz.getRight().getRight().getElement() + "          " + raiz.getRight().getRight().getRight().getElement());
//        System.out.println("    " + raiz.getRight().getLeft().getElement() + "          " + raiz.getRight().getRight().getElement());
    }

    public void add(int element) {
        if (raiz == null){
            raiz = new AVLNode(element, null);
            AVLTree.add(raiz);
            return;
        }

        AVLNode nuevo = new AVLNode(element, null);
        AVLNode aux = raiz;
        AVLNode parent = raiz;
        while (aux != null){
            if (element < aux.getElement()){
                parent = aux;
                aux = aux.getLeft();
                if (aux == null){
                    System.out.println("Fico " + nuevo.getElement() + " com a fill esquerra de " + parent.getElement());
                    parent.setLeft(nuevo);
                    nuevo.setParent(parent);
                }

            } else {
                parent = aux;
                aux = aux.getRight();
                if (aux == null){
                    System.out.println("Fico " + nuevo.getElement() + " com a fill dret de " + parent.getElement());
                    parent.setRight(nuevo);
                    nuevo.setParent(parent);
                }
            }
        }

        AVLTree.add(nuevo);
        checkRotations(nuevo);
    }

    private void checkRotations(AVLNode n) {
        int value = checkHeight(n);
        while (value < 2 && value > -2){
            n = n.getParent();
            if (n == null){
                return;
            } else {
                value = value + checkHeight(n);
                System.out.println("Value del node " + n.getElement() + ": " + value);
            }
        }
        rotate(n, value);
    }

    private void rotate(AVLNode n, int value) {
        System.out.println("Entro a rotar en base al node " + n.getElement());
        if (value == -2) {
            //Nuevo nodo insertado a la izquierda
            if (n.getLeft().getLeft() == null) {
                //LR
                if (n.getParent() != null){
                    System.out.println("Un LR sense canvi de raiz (node " + n.getElement() + ")");
                    //1er paso
                    AVLNode aux = n.getLeft();
                    n.setLeft(aux.getRight());
                    aux.setRight(null);
                    n.getLeft().setLeft(aux);
                    //2o paso
                    AVLNode aux1 = n.getLeft().getRight();
                    n.getLeft().setRight(n);
                    n.setLeft(aux1);
                }else {
                    //hay que modificar raiz
                    System.out.println("Un LR amb canvi de raiz (node " + n.getElement() + ")");

                }
            } else {
                //LL
                if (n.getParent() != null){
                    System.out.println("Un LL sense canvi de raiz (node " + n.getElement() + ")");
                    AVLNode aux = n.getLeft().getRight();
                    n.getLeft().setRight(n);
                    n.setLeft(aux);
                }else {
                    //hay que modificar raiz
                    System.out.println("Un LL amb canvi de raiz (node " + n.getElement() + ")");

                }
            }
        }else if (value == 2){
            if (n.getRight().getRight() == null){
                //RL
                if (n.getParent() != null){
                    System.out.println("Un RL sense canvi de raiz (node " + n.getElement() + ")");
                    //1er paso
                    AVLNode aux = n.getRight();
                    n.setRight(aux.getLeft());
                    aux.setLeft(null);
                    n.getRight().setRight(aux);
                    //2o paso
                    AVLNode aux1 = n.getRight().getLeft();
                    n.getRight().setLeft(n);
                    n.setRight(aux1);
                }else {
                    //hay que modificar raiz
                    System.out.println("Un RL amb canvi de raiz (node " + n.getElement() + ")");

                }
            }else {
                //RR
                if (n.getParent() != null){
                    System.out.println("Un RR sense canvi de raiz (node " + n.getElement() + ")");
                    n.getRight().setLeft(n);
                    n.getParent().setRight(n.getRight());
                    n.setRight(null);
                }else {
                    System.out.println("Un RR amb canvi de raiz (node " + n.getElement() + ")");
                    //hay que modificar raiz
                    n.getRight().setLeft(n);
                    raiz = n.getRight();
                    n.setRight(null);
                }
            }
        }
    }

    private int checkHeight(AVLNode n) {
        if (n == null){
            return 1;
        }
        return checkHeight(n.getLeft()) - checkHeight(n.getRight());
    }

    public void searchRoute(String from, String to, int type){
        int fromInt = castToInteger(from);
        int toInt = castToInteger(to);
        if (existsCity(fromInt) && existsCity(toInt)){
            dijkstra(from, to, true);
        }else {
            System.out.println("Either origin or destiny city don't exist!");
        }
    }

    private boolean existsCity(int nameInt) {

        return false;
    }

    public void dijkstra (String from, String to, boolean T_NotD){
        //Inicialización

        Node nfrom = null, nto = null;
        int pfrom = 0, pto = 0;
        for (int i = 0; i < graph.size(); i++){
            if (from.toLowerCase().equals(graph.get(i).getCity().getName().toLowerCase())){
                nfrom = graph.get(i);
                pfrom = i;
            }
            if (to.toLowerCase().equals(graph.get(i).getCity().getName().toLowerCase())){
                nto = graph.get(i);
                pto = i;
            }
        }
        int[] value = new int[graph.size()];
        for (int i = 0; i < value.length; i++){
            value[i] = EARTH_LONG;
        }
        value[pfrom] = 0;
        int[] way = new int [graph.size()];
        way[pfrom] = -1;

        //Dijkstra

        while(!(pfrom == pto)){
            int vlow = EARTH_LONG;
            int plow = 0;
            for (int i = 0; i < graph.size(); i++){
                if (value[i] != -1 && i != pfrom) {
                    for (int j = 0; j < nfrom.getConnections().size(); j++) {
                        if (graph.get(i).getCity().getName().toLowerCase().equals(nfrom.getConnections().get(j).getTo().toLowerCase())) {
                            if (T_NotD) {
                                if (value[i] > nfrom.getConnections().get(j).getDuration() + value[pfrom]) {
                                    value[i] = nfrom.getConnections().get(j).getDuration() + value[pfrom];
                                    way[i] = pfrom;
                                }
                            } else {
                                if (value[i] > nfrom.getConnections().get(j).getDistance() + value[pfrom]) {
                                    value[i] = nfrom.getConnections().get(j).getDistance() + value[pfrom];
                                    way[i] = pfrom;
                                }
                            }
                        }
                        if (value[i] < vlow){
                            plow = i;
                        }
                    }
                }
            }
            value[pfrom] = -1;
            pfrom = plow;
            nfrom = graph.get(pfrom);
        }

        System.out.print("Camino: ");
        boolean finish = false;
        String stringway = graph.get(pto).getCity().getName();
        while  (!finish){
            pto = way[pto];
            stringway = stringway + "," + graph.get(pto).getCity().getName();
            if (way[pto] == -1){
                finish = true;
            }
        }
        String [] yaw = stringway.split(",");
        for (int i = yaw.length; i > 1; i--){
            System.out.print(yaw[i-1] + ", ");
        }
        System.out.println(yaw[0] + ".");
        System.out.println("Value=" + value[pfrom]);

    }


    private int castToInteger(String name){
        int value = 0;
        for (char ch: name.replaceAll("\\s+","").toCharArray()) {
            value = value + ch - 'a' + 1;
        }
        return value;
    }

    public void calculateRoute(String from, String to, int type) {

    }

    public boolean searchCity(String name) {
        return false;
    }

    private class AVLNode{
        private int element;
        private AVLNode right;
        private AVLNode left;
        private AVLNode parent;
        private int balanceFactor;

        public AVLNode(int element, AVLNode parent){
            this.element = element;
            this.parent = parent;
            balanceFactor = 0;
        }

        public int getBalanceFactor() {
            return balanceFactor;
        }

        public void setBalanceFactor(int balanceFactor) {
            this.balanceFactor = balanceFactor;
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

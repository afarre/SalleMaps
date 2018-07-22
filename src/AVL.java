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

/*RR CON CAMBIO DE RAIZ Y NODO CAMBIA RAMA
        add(4);
        add(3);
        add(6);
        add(5);
        add(8);
        add(7);
        --
        add(4);
        add(3);
        add(7);
        add(2);
        add(6);
        add(9);
        add(5);
        add(8);
        add(10);
        add(11);
        */

/*RR CON CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(1);
        add(2);
        add(3);
        */

/*RR SIN CAMBIO DE RAIZ Y NODO CAMBIA RAMA
        add(5);
        add(4);
        add(7);
        add(2);
        add(6);
        add(9);
        add(8);
        add(11);
        add(12);
        */
/*RR SIN CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(2);
        add(1);
        add(3);
        add(4);
        add(5);
        */
/*RL CON CAMBIO DE RAIZ Y NODO CAMBIA RAMA (DERECHO)
        add(5);
        add(4);
        add(10);
        add(6);
        add(12);
        add(7);
/*RL CON CAMBIO DE RAIZ Y NODO CAMBIA RAMA (IZQUIERDO)
        add(5);
        add(4);
        add(10);
        add(7);
        add(12);
        add(6);
        */
/*RL CON CAMBIO DE RAIZ Y NODO NO CAMBIA DE RAMA
        add(5);
        add(7);
        add(6);
        */
/*RL SIN CAMBIO DE RAIZ Y NODO CAMBIA RAMA (DERECHO)
        add(3);
        add(2);
        add(5);
        add(1);
        add(4);
        add(9);
        add(7);
        add(10);
        add(8);
        */
/*RL SIN CAMBIO DE RAIZ Y NODO CAMBIA RAMA (IZQUIERDO)
        add(3);
        add(2);
        add(5);
        add(1);
        add(4);
        add(10);
        add(9);
        add(11);
        add(8);
        */
/*RL SIN CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(6);
        add(5);
        add(10);
        add(12);
        add(11);
        */
/*LR CON CAMBIO DE RAIZ Y NODO CAMBIA RAMA (DERECHO)
        add(7);
        add(3);
        add(8);
        add(2);
        add(4);
        add(5);
        */
/*LR CON CAMBIO DE RAIZ Y NODO CAMBIA RAMA (IZQUIERDO)
        add(7);
        add(3);
        add(8);
        add(2);
        add(5);
        add(4);
        */
/*LR CON CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(5);
        add(3);
        add(4);
        */
/*LR SIN CAMBIO DE RAIZ Y NODO CAMBIA RAMA (DERECHO)
        add(9);
        add(7);
        add(12);
        add(3);
        add(8);
        add(10);
        add(13);
        add(2);
        add(4);
        add(5);
        */
/*LR SIN CAMBIO DE RAIZ Y NODO CAMBIA RAMA (IZQUIERDO)
        add(9);
        add(7);
        add(12);
        add(3);
        add(8);
        add(10);
        add(13);
        add(2);
        add(5);
        add(4);
        */
/*LR SIN CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(6);
        add(4);
        add(7);
        add(2);
        add(3);
        */
/*LL CON CAMBIO DE RAIZ Y NODO CAMBIA RAMA
        add(6);
        add(4);
        add(7);
        add(3);
        add(5);
        add(2);
        */
/*LL CON CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(3);
        add(2);
        add(1);
        */
/*LL SIN CAMBIO DE RAIZ Y NODO CAMBIA RAMA
        add(8);
        add(5);
        add(9);
        add(3);
        add(6);
        add(10);
        add(2);
        add(4);
        add(1);
        */
/*LL SIN CAMBIO DE RAIZ Y NODO NO CAMBIA RAMA
        add(4);
        add(3);
        add(5);
        add(2);
        add(1);
        */
        add(11);
        add(5);
        add(13);
        add(4);
        add(9);
        add(12);
        add(14);
        add(7);
        add(10);
        add(8);
    }

    private void printa(){
        System.out.println("    Printo la informacio dels " + AVLTree.size() + " nodes:");
        for (int i = 0; i < AVLTree.size(); i++){
            AVLNode n = AVLTree.get(i);
            try {
                System.out.println("Per al node " + n.getElement() + " el seu fill dret es " + n.getRight().getElement() + " i el seu esquerre es " + n.getLeft().getElement() + " i el seu pare " + n.getParent().getElement());
            }catch (NullPointerException ignored){
                try {
                    System.out.println("Per al node " + n.getElement() + " el seu fill dret es " + n.getRight().getElement() + " i el seu esquerre es " + n.getLeft().getElement());
                }catch (NullPointerException ignored2){
                    try {
                        System.out.println("Per al node " + n.getElement() + " el seu fill dret es " + n.getRight().getElement() + " i el seu pare " + n.getParent().getElement());
                    }catch (NullPointerException ignored3){
                        try {
                            System.out.println("Per al node " + n.getElement() + " el seu fill esquerre es " + n.getLeft().getElement() + " i el seu pare " + n.getParent().getElement());
                        }catch (NullPointerException ignored4){
                            try {
                                System.out.println("Per al node " + n.getElement() + " el seu fill dret es " + n.getRight().getElement());
                            }catch (NullPointerException e){
                                try {
                                    System.out.println("Per al node " + n.getElement() + " el seu fill esquerre es " + n.getLeft().getElement());
                                }catch (NullPointerException ee){
                                    try {
                                        System.out.println("Per al node " + n.getElement() + " el seu pare " + n.getParent().getElement());
                                    }catch (NullPointerException eee){
                                        try{
                                            System.out.println("Per al nodes " + n.getElement());
                                            System.out.println(" el seu fill dret es " + n.getRight().getElement());
                                            System.out.println(" i el seu esquerre es " + n.getLeft().getElement());
                                            System.out.println(" i el seu pare " + n.getParent().getElement());
                                        }catch (NullPointerException eeeeee){
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("raiz = " + raiz.getElement());
    }

    public void add(int element) {
        if (raiz == null){
            raiz = new AVLNode(element, null);
            //System.out.println("Inserto el node " + raiz.getElement());
            AVLTree.add(raiz);
            checkRotations(raiz);
            return;
        }

        AVLNode nuevo = new AVLNode(element, null);
        nuevo.setRight(null);
        nuevo.setLeft(null);
        AVLNode aux = raiz;
        AVLNode parent = raiz;
        while (aux != null){
            if (element < aux.getElement()){
                parent = aux;
                aux = aux.getLeft();
                if (aux == null){
                    //System.out.println("Fico " + nuevo.getElement() + " com a esquerra de " + parent.getElement());
                    parent.setLeft(nuevo);
                    nuevo.setParent(parent);
                }

        } else {
                parent = aux;
                aux = aux.getRight();
                if (aux == null){
                    //System.out.println("Fico " + nuevo.getElement() + " com a dreta de " + parent.getElement());
                    parent.setRight(nuevo);
                    nuevo.setParent(parent);
                }
            }
        }

        AVLTree.add(nuevo);
        checkRotations(nuevo);
    }

    private void checkRotations(AVLNode n) {
        int value = checkHeight(n.getLeft()) - checkHeight(n.getRight());
        while (value < 2 && value > -2){
            n = n.getParent();
            if (n == null){
                return;
            } else {
                value = checkHeight(n.getLeft()) - checkHeight(n.getRight());
                //System.out.println("Value del node " + n.getElement() + " es " + value);
            }
        }
        rotate(n, value);
    }

    private int checkHeight(AVLNode n) {
        if (n == null){
            return 0;
        }
        int chr = checkHeight(n.getRight());
        int chl = checkHeight(n.getLeft());
        if (chr >= chl){
            return chr + 1;
        } else {
            return chl + 1;
        }
    }

    private void rotate(AVLNode n, int value){
        int subValue = 0;
        if (value == 2){
            if (n.getLeft().getRight() != null && n.getLeft().getLeft() != null){
                subValue = checkHeight(n.getLeft().getLeft()) - checkHeight(n.getLeft().getRight());
                System.out.println("Subvalue-1: " + (checkHeight(n.getLeft().getLeft()) - checkHeight(n.getLeft().getRight())));
            }else if (n.getLeft().getRight() != null){
                subValue = -checkHeight(n.getLeft().getRight());
                System.out.println("Subvalue-2: " + (-checkHeight(n.getLeft().getRight())));
            }else if (n.getLeft().getLeft() != null){
                subValue = checkHeight(n.getLeft().getLeft());
                System.out.println("Subvalue-3: " + checkHeight(n.getLeft().getLeft()));
            }

            System.out.println("value = " + value);
            System.out.println("subValue = " + subValue);
            System.out.println("Entro a rotar en base al node " + n.getElement());
            switch (subValue){
                case 1:
                    LL(n);
                    break;
                case -1:
                    LR(n);
                    break;

            }
        }else {
            if (n.getRight().getRight() != null && n.getRight().getLeft() != null){
                subValue = checkHeight(n.getRight().getLeft()) - checkHeight(n.getRight().getRight());
                System.out.println("Subvalue-4: " + (checkHeight(n.getRight().getLeft()) - checkHeight(n.getRight().getRight())));
            }else if (n.getRight().getRight() != null){
                subValue = -checkHeight(n.getRight().getRight());
                System.out.println("Subvalue-5: " + (-checkHeight(n.getRight().getRight())));
            }else if (n.getRight().getLeft() != null){
                subValue = checkHeight(n.getRight().getLeft());
                System.out.println("Subvalue-6: " + checkHeight(n.getRight().getLeft()));
            }

            System.out.println("value = " + value);
            System.out.println("subValue = " + subValue);
            System.out.println("Entro a rotar en base al node " + n.getElement());
            switch (subValue){
                case 1:
                    RL(n);
                    break;
                case -1:
                    RR(n);
                    break;

            }
        }
        printa();
    }

    private void LL(AVLNode n) {
        if (n.getParent() != null){
            if (n.getLeft().getRight() != null){
                System.out.println("Un LL sense canvi de raiz i node canvia de branca (node " + n.getElement() + ")");
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getLeft());
                }else {
                    n.getParent().setLeft(n.getLeft());
                }
                n.getLeft().setParent(n.getParent());
                n.setParent(n.getLeft());
                n.getLeft().getRight().setParent(n);
                n.setLeft(n.getLeft().getRight());
                n.getParent().setRight(n);
            }else {
                System.out.println("Un LL sense canvi de raiz i node no canvia de branca (node " + n.getElement() + ")");
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getLeft());
                }else {
                    n.getParent().setLeft(n.getLeft());
                }
                n.getLeft().setParent(n.getParent());
                n.getLeft().setRight(n);
                n.setParent(n.getLeft());
                n.setLeft(null);
            }
        }else {
            //hay que modificar raiz
            if (n.getLeft().getRight() != null){
                System.out.println("Un LL amb canvi de raiz i node canvia de branca (node " + n.getElement() + ")");
                raiz = n.getLeft();
                n.setLeft(raiz.getRight());
                n.getRight().setParent(n);
                raiz.setRight(n);
                n.setParent(raiz);
                raiz.setParent(null);
                n.getLeft().setParent(n);
            }else {
                System.out.println("Un LL amb canvi de raiz i node no canvia de branca (node " + n.getElement() + ")");
                n.getLeft().setRight(n);
                n.setParent(n.getLeft());
                raiz = n.getParent();
                raiz.setParent(null);
                raiz.getRight().setLeft(null);
            }
        }
    }

    private void LR(AVLNode n) {
        if (n.getParent() != null){
            if (n.getLeft().getRight().getRight() != null){
                System.out.println("Un LR sense canvi de raiz i un node (dret) canvia de branca (node " + n.getElement() + ")");
                //1er paso
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getLeft().getRight());
                }else {
                    n.getParent().setLeft(n.getLeft().getRight());
                }
                AVLNode aux = n.getLeft();
                aux.setParent(aux.getRight());
                n.setLeft(aux.getRight());
                n.getLeft().setParent(n.getParent());
                aux.setRight(null);
                n.getLeft().setRight(n.getLeft().getRight());
                //2o paso
                AVLNode aux1 = n.getLeft().getRight();
                n.setParent(n.getLeft());
                n.getLeft().setRight(n);
                n.getLeft().setLeft(aux);
                n.setLeft(aux1);
                n.getLeft().setParent(n);
            }else if (n.getLeft().getRight().getLeft() != null){
                System.out.println("Un LR sense canvi de raiz i un node (esquerra) canvia de branca (node " + n.getElement() + ")");
                //1er paso
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getLeft().getRight());
                }else {
                    n.getParent().setLeft(n.getLeft().getRight());
                }
                AVLNode aux = n.getLeft();
                aux.setParent(aux.getRight());
                n.setLeft(aux.getRight());
                n.getLeft().setParent(n.getParent());
                aux.setRight(aux.getRight().getLeft());
                aux.getRight().setParent(aux);
                n.getLeft().setRight(n.getLeft().getRight());
                //2o paso
                n.setParent(n.getLeft());
                n.getLeft().setRight(n);
                n.getLeft().setLeft(aux);
                n.setLeft(null);
                n.getRight().setParent(n);
            }else {
                System.out.println("Un LR sense canvi de raiz i cap node canvia de branca (node " + n.getElement() + ")");
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getLeft().getRight());
                }else {
                    n.getParent().setLeft(n.getLeft().getRight());
                }
                n.getLeft().getRight().setParent(n.getParent());
                n.setParent(n.getLeft().getRight());
                n.getLeft().setParent(n.getLeft().getRight());
                n.getLeft().getRight().setLeft(n.getLeft());
                n.getParent().setRight(n);
                n.getLeft().setRight(null);
                n.setLeft(null);
            }
        }else {
            if (n.getRight() == null){
                //hay que modificar raiz y no se cambia un nodo de rama
                System.out.println("Un LR amb canvi de raiz i cap node canvia de branca (node " + n.getElement() + ")");
                n.getLeft().getRight().setRight(n);
                n.setParent(n.getLeft().getRight());
                raiz = n.getLeft().getRight();
                raiz.setParent(null);
                n.getLeft().setRight(null);
                raiz.setLeft(n.getLeft());
                n.getLeft().setParent(raiz);
                n.setLeft(null);
            }else {
                if (n.getLeft().getRight().getRight() != null){
                    //hay que modificar raiz y se cambia un nodo de rama
                    System.out.println("Un LR amb canvi de raiz i un node (el dret) canvia de branca (node " + n.getElement() + ")");
                    //1er paso
                    AVLNode aux = n.getLeft();
                    aux.setParent(aux.getRight());
                    n.setLeft(aux.getRight());
                    n.getLeft().setParent(n.getParent());
                    aux.setRight(null);
                    n.getLeft().setRight(n.getLeft().getRight());
                    //2o paso
                    AVLNode aux1 = n.getLeft().getRight();
                    n.setParent(n.getLeft());
                    n.getLeft().setRight(n);
                    n.getLeft().setLeft(aux);
                    n.setLeft(aux1);
                    raiz = n.getParent();
                    n.getLeft().setParent(n);
                }else {
                    //hay que modificar raiz y se cambia un nodo de rama
                    System.out.println("Un LR amb canvi de raiz i un node (el esquerre) canvia de branca (node " + n.getElement() + ")");
                    //1er paso
                    AVLNode aux = n.getLeft();
                    aux.setParent(aux.getRight());
                    n.setLeft(aux.getRight());
                    aux.getRight().getLeft().setParent(aux);
                    aux.setRight(aux.getRight().getLeft());
                    //2o paso
                    n.setParent(n.getLeft());
                    n.getLeft().setRight(n);
                    n.getLeft().setLeft(aux);
                    n.setLeft(null);
                    raiz = n.getParent();
                    raiz.setParent(null);
                    n.getRight().setParent(n);
                }
            }
        }
    }

    private void RL(AVLNode n) {
        if (n.getParent() != null){
            if (n.getRight().getLeft().getRight() != null){
                System.out.println("Un RL sense canvi de raiz i un node (dret) canvia de branca (node " + n.getElement() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(n.getRight().getRight());
                n.getRight().getRight().setParent(aux);
                n.getRight().setRight(aux);
                //2o paso
                AVLNode aux1 = n.getRight().getLeft();
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getRight());
                }else {
                    n.getParent().setLeft(n.getRight());
                }
                n.setParent(n.getRight());
                n.getRight().setLeft(n);
                n.setRight(aux1);
            }else if (n.getRight().getLeft().getLeft() != null){
                System.out.println("Un RL sense canvi de raiz i un node (esquerra) canvia de branca (node " + n.getElement() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(null);
                n.getRight().setRight(aux);
                //2o paso
                AVLNode aux1 = n.getRight().getLeft();
                n.getParent().setRight(n.getRight());
                n.setParent(n.getRight());
                n.getRight().setLeft(n);
                n.setRight(aux1);
                n.getRight().setParent(n);
            }else {
                System.out.println("Un RL sense canvi de raiz i cap node canvia de branca (node " + n.getElement() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(null);
                n.getRight().setRight(aux);
                //2o paso
                AVLNode aux1 = n.getRight().getLeft();
                n.getParent().setRight(n.getRight());
                n.setParent(n.getRight());
                n.getRight().setLeft(n);
                n.setRight(aux1);
            }
        }else {
            if (n.getLeft() == null){
                //hay que modificar raiz y no se cambia un nodo de rama
                System.out.println("Un RL amb canvi de raiz i cap node canvia de branca (node " + n.getElement() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(null);
                n.getRight().setRight(aux);
                //2o paso
                AVLNode aux1 = n.getRight().getLeft();
                n.setParent(n.getRight());
                n.getRight().setLeft(n);
                n.setRight(aux1);
                raiz = n.getParent();
            }else {
                if (n.getRight().getLeft().getLeft() != null){
                    //hay que modificar raiz y se cambia un nodo de rama
                    System.out.println("Un RL amb canvi de raiz i un node (el esquerre) canvia de branca (node " + n.getElement() + ")");
                    //1er paso
                    AVLNode aux = n.getRight();
                    aux.setParent(aux.getLeft());
                    n.setRight(aux.getLeft());
                    n.getRight().setParent(n.getParent());
                    aux.setLeft(null);
                    n.getRight().setRight(aux);
                    //2o paso
                    AVLNode aux1 = n.getRight().getLeft();
                    n.setParent(n.getRight());
                    n.getRight().setLeft(n);
                    n.setRight(aux1);
                    raiz = n.getParent();
                    n.getRight().setParent(n);
                }else {
                    //hay que modificar raiz y se cambia un nodo de rama
                    System.out.println("Un RL amb canvi de raiz i un node (el dret) canvia de branca (node " + n.getElement() + ")");
                    //1er paso
                    AVLNode aux = n.getRight();
                    aux.setParent(aux.getLeft());
                    n.setRight(aux.getLeft());
                    n.getRight().setParent(n.getParent());
                    aux.setLeft(n.getRight().getRight());
                    n.getRight().getRight().setParent(aux);
                    n.getRight().setRight(aux);
                    //2o paso
                    AVLNode aux1 = n.getRight().getLeft();
                    n.setParent(n.getRight());
                    n.getRight().setLeft(n);
                    n.setRight(aux1);
                    raiz = n.getParent();
                }

            }
        }
    }

    private void RR(AVLNode n) {
        if (n.getParent() != null){
            if (n.getLeft() == null) {
                System.out.println("Un RR sense canvi de raiz i sense canvi de node (node " + n.getElement() + ")");
                n.getRight().setLeft(n);
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getRight());
                }else {
                    n.getParent().setLeft(n.getRight());
                }
                n.getRight().setParent(n.getParent());
                n.setParent(n.getRight());
                n.setRight(null);
            }else {
                System.out.println("Un RR sense canvi de raiz i amb canvi de node (node " + n.getElement() + ")");
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getRight());
                }else {
                    n.getParent().setLeft(n.getRight());
                }
                n.getRight().setParent(n.getParent());
                n.setParent(n.getRight());
                n.getRight().getLeft().setParent(n);
                n.setRight(n.getRight().getLeft());
                n.getParent().setLeft(n);
            }

        }else {
            if (n.getRight().getLeft() != null){
                System.out.println("Un RR amb canvi de raiz i amb canvi de node (node " + n.getElement() + ")");
                //hay que modificar raiz
                AVLNode aux = n.getRight().getLeft();
                n.getRight().setLeft(n);
                raiz = n.getRight();
                n.setParent(n.getRight());
                n.setRight(aux);
                aux.setParent(n);
                raiz.setParent(null);
            }else {
                System.out.println("Un RR amb canvi de raiz i sense canvi de node (node " + n.getElement() + ")");
                //hay que modificar raiz
                n.getRight().setLeft(n);
                raiz = n.getRight();
                n.setParent(n.getRight());
                n.setRight(n.getRight().getLeft());
                raiz.setParent(null);
                n.setRight(null);
            }
        }
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
        private int height;

        public AVLNode(int element, AVLNode parent){
            this.element = element;
            this.parent = parent;
            height = 0;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
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

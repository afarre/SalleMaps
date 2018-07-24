public class AVL {
    private AVLNode raiz;
    private MyList<AVLNode> AVLTree;
    private Graph graph;
    private boolean firstLink;
    private boolean secondLink;

    private final static Integer EARTH_LONG = 400750000;

    public AVL(Graph graph){
        AVLTree = new MyList<>();
        int size = graph.size();
        for (int i = 0; i < size; i++){
            //for (char ch: graph.get(i).getCity().getName().replaceAll("\\s+","").toCharArray()) {
              //  value = value + ch - 'a' + 1;
            //}
            add(graph.get(i));
        }


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

    }

    private void printa(){
        System.out.println("    Printo la informacio dels " + AVLTree.size() + " nodes:");
        for (int i = 0; i < AVLTree.size(); i++){
            AVLNode n = AVLTree.get(i);
            try {
                System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu fill dret es " + n.getRight().getElement().getCity().getName() + " i el seu esquerre es " + n.getLeft().getElement().getCity().getName() + " i el seu pare " + n.getParent().getElement().getCity().getName());
            }catch (NullPointerException ignored){
                try {
                    System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu fill dret es " + n.getRight().getElement().getCity().getName() + " i el seu esquerre es " + n.getLeft().getElement().getCity().getName());
                }catch (NullPointerException ignored2){
                    try {
                        System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu fill dret es " + n.getRight().getElement().getCity().getName() + " i el seu pare " + n.getParent().getElement().getCity().getName());
                    }catch (NullPointerException ignored3){
                        try {
                            System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu fill esquerre es " + n.getLeft().getElement().getCity().getName() + " i el seu pare " + n.getParent().getElement().getCity().getName());
                        }catch (NullPointerException ignored4){
                            try {
                                System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu fill dret es " + n.getRight().getElement().getCity().getName());
                            }catch (NullPointerException e){
                                try {
                                    System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu fill esquerre es " + n.getLeft().getElement().getCity().getName());
                                }catch (NullPointerException ee){
                                    try {
                                        System.out.println("Per al node " + n.getElement().getCity().getName() + " el seu pare " + n.getParent().getElement().getCity().getName());
                                    }catch (NullPointerException eee){
                                        try{
                                            System.out.println("Per al nodes " + n.getElement().getCity().getName());
                                            System.out.println(" el seu fill dret es " + n.getRight().getElement().getCity().getName());
                                            System.out.println(" i el seu esquerre es " + n.getLeft().getElement().getCity().getName());
                                            System.out.println(" i el seu pare " + n.getParent().getElement().getCity().getName());
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
        System.out.println("raiz = " + raiz.getElement().getCity().getName());
    }

    public void add(Node element) {
        if (raiz == null){
            raiz = new AVLNode(element, null);
            //System.out.println("Inserto el node " + raiz.getElement().getCity().getName());
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
            if (element.getCity().getName().charAt(0) <= aux.getElement().getCity().getName().charAt(0)){
                if (element.getCity().getName().equals(aux.getElement().getCity().getName())){
                    //System.out.println("Misma ciudad (repeticion). Descarte. Nodo " + element.getCity().getName());
                    return;
                }
                parent = aux;
                aux = aux.getLeft();
                if (aux == null){
                    //System.out.println("Fico " + nuevo.getElement().getCity().getName() + " com a esquerra de " + parent.getElement().getCity().getName());
                    parent.setLeft(nuevo);
                    nuevo.setParent(parent);
                    System.out.println("Adding " + nuevo.getElement().getCity().getName() + ".");
                }

            } else {
                parent = aux;
                aux = aux.getRight();
                if (aux == null){
                    //System.out.println("Fico " + nuevo.getElement().getCity().getName() + " com a dreta de " + parent.getElement().getCity().getName());
                    parent.setRight(nuevo);
                    nuevo.setParent(parent);
                    System.out.println("Adding " + nuevo.getElement().getCity().getName() + ".");
                }
            }
        }

        AVLTree.add(nuevo);
        checkRotations(nuevo);
        //printa();
    }

    private void checkRotations(AVLNode n) {
        int value = checkHeight(n.getLeft()) - checkHeight(n.getRight());
        while (value < 2 && value > -2){
            n = n.getParent();
            if (n == null){
                return;
            } else {
                value = checkHeight(n.getLeft()) - checkHeight(n.getRight());
                //System.out.println("Trobo un node que s'ha de rotar. Value del node " + n.getElement().getCity().getName() + " es " + value);
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
        if (value >= 2){
            if (n.getLeft().getRight() != null && n.getLeft().getLeft() != null){
                subValue = checkHeight(n.getLeft().getLeft()) - checkHeight(n.getLeft().getRight());
            }else if (n.getLeft().getRight() != null){
                subValue = -checkHeight(n.getLeft().getRight());
            }else if (n.getLeft().getLeft() != null){
                subValue = checkHeight(n.getLeft().getLeft());
            }

            //System.out.println("value = " + value);
            //System.out.println("subValue = " + subValue);
            //System.out.println("Entro a rotar en base al node " + n.getElement().getCity().getName());
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
            }else if (n.getRight().getRight() != null){
                subValue = -checkHeight(n.getRight().getRight());
            }else if (n.getRight().getLeft() != null){
                subValue = checkHeight(n.getRight().getLeft());
            }

            //System.out.println("value = " + value);
            //System.out.println("subValue = " + subValue);
            //System.out.println("Entro a rotar en base al node " + n.getElement().getCity().getName());
            switch (subValue){
                case 1:
                    RL(n);
                    break;
                case -1:
                    RR(n);
                    break;
            }
        }
    }

    private void LL(AVLNode n) {
        if (n.getParent() != null){
            if (n.getLeft().getRight() != null){
                //System.out.println("Un LL sense canvi de raiz i node canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un LL sense canvi de raiz i node no canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un LL amb canvi de raiz i node canvia de branca (node " + n.getElement().getCity().getName() + ")");
                raiz = n.getLeft();
                n.setLeft(raiz.getRight());
                n.getRight().setParent(n);
                raiz.setRight(n);
                n.setParent(raiz);
                raiz.setParent(null);
                n.getLeft().setParent(n);
            }else {
                //System.out.println("Un LL amb canvi de raiz i node no canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un LR sense canvi de raiz i un node (dret) canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un LR sense canvi de raiz i un node (esquerra) canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un LR sense canvi de raiz i cap node canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un LR amb canvi de raiz i cap node canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                    //System.out.println("Un LR amb canvi de raiz i un node (el dret) canvia de branca (node " + n.getElement().getCity().getName() + ")");
                    //1er paso
                    AVLNode aux = n.getLeft();
                    aux.setParent(aux.getRight());
                    n.setLeft(aux.getRight());
                    n.getLeft().setParent(n.getParent());
                    if (aux.getRight().getLeft() != null){
                        aux.getRight().getLeft().setParent(aux);
                        aux.setRight(aux.getRight().getLeft());
                    }else {
                        aux.setRight(null);
                    }
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
                    //System.out.println("Un LR amb canvi de raiz i un node (el esquerre) canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un RL sense canvi de raiz i un node (dret) canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un RL sense canvi de raiz i un node (esquerra) canvia de branca (node " + n.getElement().getCity().getName() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(null);
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
                n.getRight().setParent(n);
            }else {
                //System.out.println("Un RL sense canvi de raiz i cap node canvia de branca (node " + n.getElement().getCity().getName() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(null);
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
            }
        }else {
            if (n.getLeft() == null){
                //hay que modificar raiz y no se cambia un nodo de rama
                //System.out.println("Un RL amb canvi de raiz i cap node canvia de branca (node " + n.getElement().getCity().getName() + ")");
                //1er paso
                AVLNode aux = n.getRight();
                aux.setParent(aux.getLeft());
                n.setRight(aux.getLeft());
                n.getRight().setParent(n.getParent());
                aux.setLeft(null);
                n.getRight().setRight(aux);
                //2o paso
                AVLNode aux1 = n.getRight().getLeft();
                if (n == n.getParent().getRight()){
                    n.getParent().setRight(n.getRight());
                }else {
                    n.getParent().setLeft(n.getRight());
                }
                n.getRight().setLeft(n);
                n.setRight(aux1);
                raiz = n.getParent();
            }else {
                if (n.getRight().getLeft().getLeft() != null){
                    //hay que modificar raiz y se cambia un nodo de rama
                    //System.out.println("Un RL amb canvi de raiz i un node (el esquerre) canvia de branca (node " + n.getElement().getCity().getName() + ")");
                    //1er paso
                    AVLNode aux = n.getRight();
                    aux.setParent(aux.getLeft());
                    n.setRight(aux.getLeft());
                    n.getRight().setParent(null);
                    if (aux.getLeft().getRight() != null){
                        aux.getLeft().getRight().setParent(aux);
                        aux.setLeft(aux.getLeft().getRight());
                    }else {
                        aux.setLeft(null);
                    }
                    n.getRight().setRight(aux);
                    //2o paso
                    AVLNode aux1 = n.getRight().getLeft();
                    n.setParent(n.getRight());
                    if (n == n.getParent().getRight()){
                        n.getParent().setRight(n);
                    }else {
                        n.getParent().setLeft(n);
                    }
                    n.setRight(aux1);
                    raiz = n.getParent();
                    n.getRight().setParent(n);
                }else {
                    //hay que modificar raiz y se cambia un nodo de rama
                    //System.out.println("Un RL amb canvi de raiz i un node (el dret) canvia de branca (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un RR sense canvi de raiz i sense canvi de node (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un RR sense canvi de raiz i amb canvi de node (node " + n.getElement().getCity().getName() + ")");
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
                //System.out.println("Un RR amb canvi de raiz i amb canvi de node (node " + n.getElement().getCity().getName() + ")");
                //hay que modificar raiz
                AVLNode aux = n.getRight().getLeft();
                n.getRight().setLeft(n);
                raiz = n.getRight();
                n.setParent(n.getRight());
                n.setRight(aux);
                aux.setParent(n);
                raiz.setParent(null);
            }else {
                //System.out.println("Un RR amb canvi de raiz i sense canvi de node (node " + n.getElement().getCity().getName() + ")");
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

    public AVLNode existsCity(String name) {
        AVLNode aux = raiz;
        while (aux != null){
            if (name.charAt(0) <= aux.getElement().getCity().getName().charAt(0)){
                if (name.equals(aux.getElement().getCity().getName())){
                    return aux;
                }
                aux = aux.getLeft();
            } else {
                aux = aux.getRight();
            }
        }
        return null;
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

    public <T> MyList<AVLNode> getTree() {
        return AVLTree;
    }

    public class AVLNode{
        private Node element;
        private AVLNode right;
        private AVLNode left;
        private AVLNode parent;
        private int height;

        public AVLNode(Node element, AVLNode parent){
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

        public Node getElement() {
            return element;
        }

        public void setElement(Node element) {
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

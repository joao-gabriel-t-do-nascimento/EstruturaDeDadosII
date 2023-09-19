package AVL;

public class AVLTree<T extends Comparable<T>> {
    private AVLNode<T> root = null;
    private boolean estado = true;

    public void insert(T info) {
        if (root == null)
            this.root = new AVLNode<T>(info);
        else
            insertNode(null, this.root, info);
    }

    private void insertNode(AVLNode<T> previous, AVLNode<T> actual, T info) {
        int cmp = info.compareTo(actual.getInfo());
        if (cmp <= 0) {
            if (actual.getLeft() == null) {
                actual.setLeft(new AVLNode<>(info));
                this.estado = true;
            } else {
                insertNode(actual, actual.getLeft(), info);
            }

            if (estado) {
                if (actual.getFatBal() == 1) {
                    actual.setFatBal(0);
                    // os nós acima não se alteram
                    this.estado = false;
                } else if (actual.getFatBal() == 0) {
                    // os nós acima se alteram
                    actual.setFatBal(-1);
                } else if (actual.getFatBal() == -1) {
                    // há uma rotação simples a direta ou uma rotação dupla a direita
                    rotateRight(previous, actual, actual.getLeft());
                    this.estado = false;
                }
            }

        } else {
            if (actual.getRight() == null) {
                actual.setRight(new AVLNode<>(info));
                this.estado = true;
            } else {
                insertNode(actual, actual.getRight(), info);
            }

            if (estado) {
                if (actual.getFatBal() == 1) {
                    rotateLeft(previous, actual, actual.getRight());
                    // os nós acima não se alteram
                    this.estado = false;
                } else if (actual.getFatBal() == 0) {
                    // os nós acima se alteram
                    actual.setFatBal(1);
                } else if (actual.getFatBal() == -1) {
                    // há uma rotação simples a direta ou uma rotação dupla a direita
                    actual.setFatBal(0);
                    this.estado = false;
                }
            }
        }
    }

    private void rotateLeft(AVLNode<T> previous, AVLNode<T> a, AVLNode<T> b) {
        if (b.getFatBal() > 0) {
            if (previous == null) {
                this.root = b;
            } else {
                if (previous.getLeft() == a)
                    previous.setLeft(b); // essencial!!!
                else
                    previous.setRight(b);
            }
            a.setRight(b.getLeft());
            b.setLeft(a);
            a.setFatBal(0);
            b.setFatBal(0);
        } else if (b.getFatBal() < 0) {
            AVLNode<T> c = b.getLeft();
            if (previous == null){
                this.root = c;
                System.out.println("RDE");
            }
            else {
                if (previous.getLeft() == a)
                    previous.setLeft(c); // essencial!!!
                else
                    previous.setRight(c);
            }
            b.setLeft(c.getRight());
            c.setRight(b);
            a.setRight(c.getLeft());
            c.setLeft(a);

            switch (c.getFatBal()) {
                case 0:
                    a.setFatBal(0);
                    b.setFatBal(0);
                    c.setFatBal(0);
                    break;
                case 1:
                    a.setFatBal(-1);
                    b.setFatBal(0);
                    c.setFatBal(0);
                    break;
                case -1:
                    a.setFatBal(0);
                    b.setFatBal(1);
                    c.setFatBal(0);
                    break;
                default:
                    break;
            }
        }
    }

    private void rotateRight(AVLNode<T> previous, AVLNode<T> a, AVLNode<T> b) {
        if (b.getFatBal() < 0) {
            if (previous == null) {
                this.root = b;
            } else {
                if (previous.getLeft() == a)
                    previous.setLeft(b);
                else
                    previous.setRight(b);
            }
            a.setLeft(b.getRight());
            b.setRight(a);
            a.setFatBal(0);
            b.setFatBal(0);
        } else if (b.getFatBal() > 0) {
            AVLNode<T> c = b.getRight();
            if (previous == null)
                this.root = c;
            else {
                if (previous.getLeft() == a)
                    previous.setLeft(c);
                else
                    previous.setRight(c);
            }
            b.setRight(c.getLeft());
            c.setLeft(b);
            a.setLeft(c.getRight());
            c.setRight(a);
            switch (c.getFatBal()) {
                case 0:
                    a.setFatBal(0);
                    b.setFatBal(0);
                    c.setFatBal(0);
                    break;
                case 1:
                    a.setFatBal(0);
                    b.setFatBal(-1);
                    c.setFatBal(0);
                    break;
                case -1:
                    a.setFatBal(1);
                    b.setFatBal(0);
                    c.setFatBal(0);
                    break;
                default:
                    break;
            }
        }
    }

    public void delete(T info) {
        if (this.root == null)
            System.out.println("árvore vazia");
        else if (root.getInfo() == info) {
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getLeft() == null) {
                root = root.getRight();
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else {
                AVLNode<T> newNode = max(root.getLeft());
                AVLNode<T> oldLeft = root.getLeft();
                AVLNode<T> oldRight = root.getRight();
                deleteMax(root, root.getLeft());
                this.root = newNode;
                if (oldLeft != newNode)
                    newNode.setLeft(oldLeft);
                newNode.setRight(oldRight);
                newNode.setFatBal(altura(newNode.getRight()) - altura(newNode.getLeft()));
            }
        } else {
            int cmp = info.compareTo(root.getInfo());
            if (cmp == 1)
                delete(root, root.getRight(), info);
            else
                delete(root, root.getLeft(), info);
        }
        root.setFatBal(altura(root.getRight()) - altura(root.getLeft()));
        if (root.getFatBal() < -1) {
            rotateRight(null, root, root.getLeft());
        } else if (root.getFatBal() > 1) {
            rotateLeft(null, root, root.getRight());
        }
    }

    private void delete(AVLNode<T> previous, AVLNode<T> actual, T info) {
        if (actual == null) {
            System.out.println("nnn");
        } else {
            int cmp = info.compareTo(actual.getInfo());
            if (cmp == 0) {
                if (actual.getLeft() == null && actual.getRight() == null) {
                    if (previous.getLeft() == actual)
                        previous.setLeft(null);
                    else
                        previous.setRight(null);

                } else if (actual.getLeft() == null) {
                    if (previous.getLeft() == actual)
                        previous.setLeft(actual.getRight());
                    else
                        previous.setRight(actual.getRight());

                } else if (actual.getRight() == null) {
                    if (previous.getLeft() == actual)
                        previous.setLeft(actual.getLeft());
                    else
                        previous.setRight(actual.getLeft());

                } else {
                    if (previous.getLeft() == actual) {
                        AVLNode<T> newNode = max(actual.getLeft());
                        AVLNode<T> oldLeft = actual.getLeft();
                        AVLNode<T> oldRight = actual.getRight();
                        deleteMax(actual, actual.getLeft());
                        previous.setLeft(newNode);
                        if (oldLeft != newNode)
                            newNode.setLeft(oldLeft);
                        newNode.setRight(oldRight);
                        newNode.setFatBal(altura(newNode.getRight()) - altura(newNode.getLeft()));
                    } else {
                        AVLNode<T> newNode = max(actual.getLeft());
                        AVLNode<T> oldLeft = actual.getLeft();
                        AVLNode<T> oldRight = actual.getRight();
                        deleteMax(actual, actual.getLeft());
                        previous.setRight(newNode);
                        if (oldLeft != newNode)
                            newNode.setLeft(oldLeft);
                        newNode.setRight(oldRight);
                        newNode.setFatBal(altura(newNode.getRight()) - altura(newNode.getLeft()));
                    }
                }
            } else if (cmp > 0) {
                delete(actual, actual.getRight(), info);
                actual.setFatBal(altura(actual.getRight()) - altura(actual.getLeft()));
                if (actual.getFatBal() < -1) {
                    rotateRight(previous, actual, actual.getLeft());
                    
                } else if (actual.getFatBal() > 1) {
                    rotateLeft(previous, actual, actual.getRight());
                }
            } else if (cmp < 0) {
                delete(actual, actual.getLeft(), info);
                actual.setFatBal(altura(actual.getRight()) - altura(actual.getLeft()));
                if (actual.getFatBal() < -1) {
                    rotateRight(previous, actual, actual.getLeft());
                } else if (actual.getFatBal() > 1) {
                    rotateLeft(previous, actual, actual.getRight());
                }
            }
        }
    }

    public int altura(AVLNode<T> node) {
        if(node == null) {
            return -1;
        } else {
            int esq = altura(node.getLeft());
            int dir = altura(node.getRight());
            if(esq > dir) {
                return esq + 1;
            } else {
                return dir + 1;
            }
        }
    }

    public AVLNode<T> min(AVLNode<T> node) {
        if (node.getLeft() == null)
            return node;
        else
            return min(node.getLeft());
    }

    public AVLNode<T> max(AVLNode<T> node) {
        if (node.getRight() == null)
            return node;
        else
            return max(node.getRight());
    }

    private void deleteMax(AVLNode<T> previous, AVLNode<T> start) {
        if (start.getRight() == null) {
            if (previous.getLeft() == start)
                previous.setLeft(start.getLeft());
            else
                previous.setRight(start.getLeft());
        } else {
            deleteMax(start, start.getRight());
        }
    }

    public void inOrder() {
        if (this.root == null)
            System.out.println("Árvore vazia");
        else {
            if (this.root.getLeft() != null)
                inOrder(this.root.getLeft());
            System.out.println(root.getInfo() + " - " + this.root.getFatBal());
            if (this.root.getRight() != null)
                inOrder(this.root.getRight());
        }
    }

    private void inOrder(AVLNode<T> node) {
        if (node.getLeft() != null)
            inOrder(node.getLeft());
        System.out.println(node.getInfo() + " - " + node.getFatBal());
        if (node.getRight() != null)
            inOrder(node.getRight());
    }

    public void postOrder() {
        if (this.root == null)
            System.out.println("Árvore vazia");
        else {
            if (this.root.getLeft() != null)
                inOrder(this.root.getLeft());
            if (this.root.getRight() != null)
                inOrder(this.root.getRight());
            System.out.println(root.getInfo() + " - " + this.root.getFatBal());
        }
    }

    private void postOrder(AVLNode<T> node) {
        if (node.getLeft() != null)
            inOrder(node.getLeft());
        if (node.getRight() != null)
            inOrder(node.getRight());
        System.out.println(node.getInfo() + " - " + node.getFatBal());
    }
    
    public AVLNode<T> getRoot() {
        return root;
    }
}

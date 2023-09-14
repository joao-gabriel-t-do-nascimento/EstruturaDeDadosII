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
            if (previous == null)
                this.root = c;
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
}

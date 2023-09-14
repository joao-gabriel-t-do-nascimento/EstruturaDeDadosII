package AVL;

public class test {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        Integer[] infos = {11, 20, 32, 43, 50, 61, 70, 99, 98, 97, 95, 93, 92, 91, 80};
        for(Integer i: infos) tree.insert(i);
        tree.inOrder();
    }
}


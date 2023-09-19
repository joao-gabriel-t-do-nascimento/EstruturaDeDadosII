package AVL;

public class test {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        // {8, 3, 21, 1, 6, 11, 23, 2, 5, 7, 10, 16, 22, 25, 4, 9, 13, 18, 24, 12, 14, 17, 19}
        Integer[] infos = {8, 3, 21, 1, 6, 11, 23, 2, 5, 7, 10, 16, 22, 25, 4, 9, 13, 18, 24, 12, 14, 17, 19};
        for(Integer i: infos) tree.insert(i);
        tree.delete(7);
        tree.delete(9);
        tree.inOrder();
    }
}


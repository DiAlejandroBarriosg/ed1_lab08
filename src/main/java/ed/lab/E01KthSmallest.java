package ed.lab;

public class E01KthSmallest {

    private int contador;
    private int respuesta;

    public int kthSmallest(TreeNode<Integer> root, int k) {
        contador = 0;
        respuesta = 0;
        inorder(root, k);
        return respuesta;
    }

    private void inorder(TreeNode<Integer> nodo, int k) {
        if (nodo == null || contador >= k) {
            return;
        }

        inorder(nodo.left, k);

        if (contador >= k) {
            return;
        }

        contador++;
        if (contador == k) {
            respuesta = nodo.value;
            return;
        }

        inorder(nodo.right, k);
    }

}
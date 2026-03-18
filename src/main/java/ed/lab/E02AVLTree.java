package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private final Comparator<T> comparator;
    private NodoAVL<T> root;
    private int size;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    public T search(T value) {
        NodoAVL<T> actual = root;

        while (actual != null) {
            int comparacion = comparator.compare(value, actual.value);

            if (comparacion == 0) {
                return actual.value;
            } else if (comparacion < 0) {
                actual = actual.left;
            } else {
                actual = actual.right;
            }
        }

        return null;
    }

    public int height() {
        return altura(root);
    }

    public int size() {
        return size;
    }

    private NodoAVL<T> insert(NodoAVL<T> nodo, T value) {
        if (nodo == null) {
            size++;
            return new NodoAVL<>(value);
        }

        int comparacion = comparator.compare(value, nodo.value);

        if (comparacion < 0) {
            nodo.left = insert(nodo.left, value);
        } else if (comparacion > 0) {
            nodo.right = insert(nodo.right, value);
        } else {
            return nodo;
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private NodoAVL<T> delete(NodoAVL<T> nodo, T value) {
        if (nodo == null) {
            return null;
        }

        int comparacion = comparator.compare(value, nodo.value);

        if (comparacion < 0) {
            nodo.left = delete(nodo.left, value);
        } else if (comparacion > 0) {
            nodo.right = delete(nodo.right, value);
        } else {
            if (nodo.left == null && nodo.right == null) {
                size--;
                return null;
            }

            if (nodo.left == null) {
                size--;
                return nodo.right;
            }

            if (nodo.right == null) {
                size--;
                return nodo.left;
            }

            NodoAVL<T> sucesor = minimo(nodo.right);
            nodo.value = sucesor.value;
            nodo.right = delete(nodo.right, sucesor.value);
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private NodoAVL<T> minimo(NodoAVL<T> nodo) {
        while (nodo.left != null) {
            nodo = nodo.left;
        }
        return nodo;
    }

    private NodoAVL<T> balancear(NodoAVL<T> nodo) {
        int balance = factorBalance(nodo);

        if (balance > 1) {
            if (factorBalance(nodo.left) < 0) {
                nodo.left = rotacionIzquierda(nodo.left);
            }
            return rotacionDerecha(nodo);
        }

        if (balance < -1) {
            if (factorBalance(nodo.right) > 0) {
                nodo.right = rotacionDerecha(nodo.right);
            }
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL<T> rotacionDerecha(NodoAVL<T> nodo) {
        NodoAVL<T> nuevaRaiz = nodo.left;
        NodoAVL<T> subArbol = nuevaRaiz.right;

        nuevaRaiz.right = nodo;
        nodo.left = subArbol;

        actualizarAltura(nodo);
        actualizarAltura(nuevaRaiz);

        return nuevaRaiz;
    }

    private NodoAVL<T> rotacionIzquierda(NodoAVL<T> nodo) {
        NodoAVL<T> nuevaRaiz = nodo.right;
        NodoAVL<T> subArbol = nuevaRaiz.left;

        nuevaRaiz.left = nodo;
        nodo.right = subArbol;

        actualizarAltura(nodo);
        actualizarAltura(nuevaRaiz);

        return nuevaRaiz;
    }

    private void actualizarAltura(NodoAVL<T> nodo) {
        nodo.height = 1 + Math.max(altura(nodo.left), altura(nodo.right));
    }

    private int factorBalance(NodoAVL<T> nodo) {
        return nodo == null ? 0 : altura(nodo.left) - altura(nodo.right);
    }

    private int altura(NodoAVL<T> nodo) {
        return nodo == null ? 0 : nodo.height;
    }

    private static class NodoAVL<T> {
        T value;
        NodoAVL<T> left;
        NodoAVL<T> right;
        int height;

        NodoAVL(T value) {
            this.value = value;
            this.height = 1;
        }
    }
}
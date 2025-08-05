package Proyecto2;

import java.util.Arrays;
import java.util.Random;

public class AVL {
    private Node root;

    private class Node {
        private int value;
        private Node left, right;
        private int height;
        private int n;

        public Node(int value) {
            this.value = value;
            this.height = 1; // Altura inicial de un nodo
            this.n = 1;      // Tamaño inicial del subárbol
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        return (x == null) ? 0 : x.n;
    }

    private int height(Node x) {
        return (x == null) ? 0 : x.height;
    }

    private int balanceFactor(Node x) {
        return (x == null) ? 0 : height(x.left) - height(x.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotación
        x.right = y;
        y.left = T2;

        // Actualizar alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Actualizar tamaños
        y.n = size(y.left) + size(y.right) + 1;
        x.n = size(x.left) + size(x.right) + 1;

        return x; // Nueva raíz
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotación
        y.left = x;
        x.right = T2;

        // Actualizar alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Actualizar tamaños
        x.n = size(x.left) + size(x.right) + 1;
        y.n = size(y.left) + size(y.right) + 1;

        return y; // Nueva raíz
    }

    public void put(int value) {
        root = put(root, value);
    }

    private Node put(Node x, int value) {
        if (x == null) return new Node(value);

        int cmp = value - x.value;
        if (cmp < 0) {
            x.left = put(x.left, value);
        } else if (cmp > 0) {
            x.right = put(x.right, value);
        } else {
            // Ya existe, no se inserta duplicado
            return x;
        }

        // Actualizar altura y tamaño
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.n = size(x.left) + size(x.right) + 1;

        // Balancear el nodo
        return balance(x);
    }

    private Node balance(Node x) {
        int balanceFactor = balanceFactor(x);

        // Caso izquierda-izquierda
        if (balanceFactor > 1 && balanceFactor(x.left) >= 0) {
            return rotateRight(x);
        }

        // Caso izquierda-derecha
        if (balanceFactor > 1 && balanceFactor(x.left) < 0) {
            x.left = rotateLeft(x.left);
            return rotateRight(x);
        }

        // Caso derecha-derecha
        if (balanceFactor < -1 && balanceFactor(x.right) <= 0) {
            return rotateLeft(x);
        }

        // Caso derecha-izquierda
        if (balanceFactor < -1 && balanceFactor(x.right) > 0) {
            x.right = rotateRight(x.right);
            return rotateLeft(x);
        }

        return x; // Ya está balanceado
    }

    public void print() {
        if (root == null) {
            System.out.println("Árbol vacío");
        } else {
            print(root, "", true);
        }
    }

    private void print(Node node, String prefix, boolean isTail) {
        if (node != null) {
            // Imprimir el nodo actual
            System.out.println(prefix + (isTail ? "+-- " : "|-- ") + node.value);

            // Construir el prefijo para los hijos
            String newPrefix = prefix + (isTail ? "    " : "|   ");

            // Recorrer recursivamente hijos izquierdo y derecho
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    print(node.right, newPrefix, false); // Subárbol derecho
                }
                if (node.left != null) {
                    print(node.left, newPrefix, true); // Subárbol izquierdo
                }
            }
        }
    }


    public static void main(String[] args) {
        AVL myAVL = new AVL();

        int[] myArray = new Random().ints(20, 1, 101).toArray();
        System.out.println(Arrays.toString(myArray));

        for (int e : myArray) {
            myAVL.put(e);
        }

        System.out.println("\nArbol AVL:");
        myAVL.print();
    }
}

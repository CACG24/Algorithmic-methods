package Proyecto2;

public class Problema2 {

    //Metodo principal
    public static void quickSort(int[] A) {
        quickSort(A, 0, A.length-1);
    }

    //Metodo recursivo
    public static void quickSort(int[] A, int left, int right) {
        if (left<right) {
            //Dividir el arreglo y encontrar el índice del pivote
            int pivotIndex = partition(A, left, right);
            
            //Ordenar recursivamente
            quickSort(A, left, pivotIndex-1);
            quickSort(A, pivotIndex+1, right);
        }
    }
    

    //Metodo para dividir el arreglo y encontrar el pivote
    private static int partition(int[] A, int left, int right) {
        int pivot = A[right];
        int i = left-1;
        for (int x=left;x<right;x++) {
            if (A[x]<=pivot) {
                i++;
                swap(A, i, x);
            }
        }
        swap(A, i+1, right);
        return i+1;
    }

    //Metodo para intercambiar elementos
    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }


    
    public static void main(String[] args) {
        int[] array = {10, 7, 8, 9, 1, 5, 17, 6};

        System.out.println("Arreglo original:");
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();

        quickSort(array);

        System.out.println("Arreglo ordenado:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}

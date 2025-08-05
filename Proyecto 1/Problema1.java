package Proyecto;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Problema1 {
	
	public static int mov;

    //Metodo para ordenar los puntos
    public static List<Pair<Integer, Integer>> ordenarPuntos(List<Pair<Integer, Integer>> puntos) {
        Pair<Double, Double> centroide=calcularCentroide(puntos);

        //Ordena los puntos por angulo polar con respecto al centroide por medio de mergesort
        List<Pair<Integer, Integer>> puntosOrdenados = mergeSort(puntos, centroide);

        return puntosOrdenados;
    }


    //Calcula el centroide del conjunto de puntos
    public static Pair<Double, Double> calcularCentroide(List<Pair<Integer, Integer>> puntos) {
        double sumaX=0, sumaY=0;
        for (Pair<Integer, Integer> punto:puntos) {
            sumaX+=punto.getKey();
            sumaY+=punto.getValue();
        }
        return new Pair<>(sumaX/puntos.size(), sumaY/puntos.size());
    }

    
    //Calcula el angulo polar de un punto con respecto al centroide
    public static double calcularAngulo(Pair<Double, Double> centroide, Pair<Integer, Integer> punto) {
        double deltaX=punto.getKey() - centroide.getKey();
        double deltaY=punto.getValue() - centroide.getValue();
        return Math.atan2(deltaY, deltaX);
    }
    
    
    //Implementacion de mergesort
    public static List<Pair<Integer, Integer>> mergeSort(List<Pair<Integer, Integer>> puntos, Pair<Double, Double> centroide) {
        mov++;
    	if (puntos.size()<=1) {
            return puntos;
        }
        
        //Divide la lista en dos mitades
        int mitad=puntos.size()/2;
        List<Pair<Integer, Integer>> izquierda=new ArrayList<>(puntos.subList(0, mitad));
        List<Pair<Integer, Integer>> derecha=new ArrayList<>(puntos.subList(mitad, puntos.size()));

        //Ordena ambas mitades
        izquierda=mergeSort(izquierda, centroide);
        derecha=mergeSort(derecha, centroide);

        //Mezcla ambas mitades ordenadas
        return merge(izquierda, derecha, centroide);
    }

    
    //Metodo para mezclar dos listas ordenadas
    public static List<Pair<Integer, Integer>> merge(List<Pair<Integer, Integer>> izquierda, List<Pair<Integer, Integer>> derecha, Pair<Double, Double> centroide) {
        List<Pair<Integer, Integer>> resultado=new ArrayList<>();
        int i=0, j=0;

        while (i<izquierda.size() && j<derecha.size()) {
            double anguloIzquierda=calcularAngulo(centroide, izquierda.get(i));
            double anguloDerecha=calcularAngulo(centroide, derecha.get(j));

            if (anguloIzquierda<=anguloDerecha) {
                resultado.add(izquierda.get(i));
                i++;
            } else {
                resultado.add(derecha.get(j));
                j++;
            }
        }

        //Añade los elementos restantes
        while (i<izquierda.size()) {
            resultado.add(izquierda.get(i));
            i++;
        }
        while (j<derecha.size()) {
            resultado.add(derecha.get(j));
            j++;
        }

        return resultado;
    }
    
    
    public static List<Pair<Integer, Integer>> generarPuntosAleatorios(int n, int rangoX, int rangoY) {
        List<Pair<Integer, Integer>> puntos=new ArrayList<>();
        Random random=new Random();
        for (int i=0;i<n;i++) {
            int x=random.nextInt(rangoX);
            int y=random.nextInt(rangoY);
            puntos.add(new Pair<>(x, y));
        }
        return puntos;
    }
    

    public static void main(String[] args) {
        int n=50;
        int rangoX=100;
        int rangoY=100;

        List<Pair<Integer, Integer>> puntos=generarPuntosAleatorios(n, rangoX, rangoY);
        
        //Puntos sin ordenar
        System.out.println("Puntos sin ordenar:");
        for (Pair<Integer, Integer> punto:puntos) {
            System.out.println(punto.getKey()+"\t"+punto.getValue());
        }

        List<Pair<Integer, Integer>> curva=ordenarPuntos(puntos);

        //Puntos ordenados
        System.out.println("\nPuntos ordenados:");
        for (Pair<Integer, Integer> punto:curva) {
            System.out.println(punto.getKey()+"\t"+punto.getValue());
        }
        
        //Tiempo de ejecución
        System.out.println("\nIteraciones contra tiempo y operaciones:");
        long start,end;
        for (int i=1;i<101;i++) {
        	mov=0;
        	List<Pair<Integer, Integer>> l1=generarPuntosAleatorios(100*i, rangoX, rangoY);
        	start=System.currentTimeMillis();
        	l1=ordenarPuntos(l1);
        	end=System.currentTimeMillis();
        	System.out.println(100*i+"\t"+(end-start)+"\t"+mov);
        }
    }
}

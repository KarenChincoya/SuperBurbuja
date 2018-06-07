/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velasco.karen.test1;

import java.util.Random;

import archivosutilidades.ArchivoUtilidades;

/**
 *
 * @author karen
 */
public class Burbuja {

    BurbujaHilo[] hiloBurbuja;
    Integer[] list;

    public Burbuja(Integer hilos, Integer longitud, Integer limiteSuperior) {
        String dir = System.getProperty("user.dir");
        Random random = new Random();
        Integer n;
        list = new Integer[longitud];
        //1. Random numbers list
        for (int i = 0; i < longitud; i++) {
            n = random.nextInt(limiteSuperior);
            list[i] = n;
        }
        System.out.println("Array list generado");
        try {
            //2. Generar el archivo de los random
            String[] cadena = new String[longitud];
            String strNumber;
            for (int i = 0; i < list.length; i++) {
                strNumber = String.valueOf(list[i]);
                cadena[i] = strNumber;
            }

            ArchivoUtilidades.escribirLineas(dir + "/src/randomNumbers.txt", cadena);

            System.out.println("Archivo escrito");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Cantidad de numeros: " + longitud);

        //Obtener el maximo y el minimo del array list 
        Integer max = null, min = null, aux;

        for (int i = 0; i < list.length; i++) {
            // System.out.println("Dentro de la lista: "+lista.get(i));
            if (i == 0) {
                max = list[i];
                min = list[i];
            } else { //ya hay max y min
                if (list[i] < min) {
                    min = list[i];
                }
                if (list[i] > max) {
                    max = list[i];
                }
            }
        }

        System.out.println("Maximo = " + max);
        System.out.println("Minimo = " + min);

        Integer rango = ((max - min) - ((max - min) % hilos)) / hilos;
        System.out.println("Segmentos = " + rango);

        hiloBurbuja = new BurbujaHilo[hilos];

//reparticion de limites
        Integer wall = min;
        Integer limInf;
        Integer limSup;

        for (int i = 0; i < hilos; i++) {
            limInf = wall;
            if (i == (hilos - 1)) {//el ultimo
                limSup = max;
            } else {
                wall += rango; //RANGO
                limSup = wall - 1;
            }
            hiloBurbuja[i] = new BurbujaHilo(i, list, limInf, limSup);
        }

        //ahora a imprimir los limites
        for (int i = 0; i < hilos; i++) {
            System.out.println("Hilo "+i+" ---->rangos:"+hiloBurbuja[i].getRango());
        }
        
        //ver cuantos numeros le toca a cada hilo 
        Integer[] tamanios = new Integer[hilos];
        
        for(int i=0; i<hilos;i++){
            tamanios[i] = 0;
        }
        
        Integer numero;
        for (int i = 0; i < longitud; i++) {
            numero = list[i];
            //System.out.println("Reparticion -----> numero = "+numero);
            for (int j = 0; j < hilos; j++) {
                if (hiloBurbuja[j].dentroDelRango(numero) == true) {
                    tamanios[j]++;
                    // System.out.println(numero+" se agrego al hilo de rango "+hiloBurbuja[j].getRango());
                }
            }
        }
        for (int j = 0; j < hilos; j++) {
            hiloBurbuja[j].setTamanio(tamanios[j]);
            hiloBurbuja[j].setLista(tamanios[j]); 
                  
         }
        
        //repartirlos en los hilos  **************+++REPARTICION
        for (int i = 0; i < longitud; i++) {
            numero = list[i];
            //System.out.println("Reparticion -----> numero = "+numero);

            for (int j = 0; j < hilos; j++) {
                if (hiloBurbuja[j].dentroDelRango(numero) == true) {
                    hiloBurbuja[j].addNumber(numero);
                    // System.out.println(numero+" se agrego al hilo de rango "+hiloBurbuja[j].getRango());
                }
            }

        }
        System.out.println("Los numeros fueron distribuidos");
        Integer suma = 0;
        for(int i=0; i<hilos;i++){
            System.out.println("Hilo "+i+" -----> tamanio = "+hiloBurbuja[i].getTamanio());
            suma+=hiloBurbuja[i].getTamanio();
        }
        System.out.println("Numeros totales = "+suma);
        wall = 0;
        //asignar pIncial pFinal
        for (int i = 0; i < hilos; i++) {
            hiloBurbuja[i].setpInicial(wall);
            wall = wall + hiloBurbuja[i].getTamanio();
            hiloBurbuja[i].setpFinal(wall - 1);
        }

        /*/imprimir los numeros de cada lista
        for (int i = 0; i < hilos; i++) {
            System.out.println("Hilo " + i + " ----->  Rango: " + hiloBurbuja[i].getRango() + "----->Posiciones dentro de la lista " + hiloBurbuja[i].getPosiciones());
            for (int j = 0; j < hiloBurbuja[i].getLista().size(); j++) {
                System.out.print("   " + hiloBurbuja[i].getLista().get(j));
            }
            System.out.println("\n");
        }
         */
 /*imprimir la lista desordenada
        System.out.println("Desordenada -- antes de los hilos --");
        for (int i = 0; i < lista.size(); i++) {
           System.out.print("   " + lista.get(i) + " , ");
        }
        System.out.println("\n");
         */
        //encender los hilos 
        for (int i = 0; i < hilos; i++) {
            hiloBurbuja[i].start();
        }
        int cont = 0;
        boolean continuar = true;
        
        
        while (continuar == true) {
            cont = 0;
            for (int i = 0; i < hilos; i++) {
                if (hiloBurbuja[i].isTareaFinalizada()) {
                    cont++;
                }
            }
            if (cont == hilos) {
                String[] resultado = new String[longitud];
                //imprimir la lista ordenada
//                System.out.println("Ordenada");
                for (int i = 0; i < longitud; i++) {
                    resultado[i] = String.valueOf(list[i]);
                    // System.out.print("   " + lista.get(i) + " , ");
                }

                ArchivoUtilidades.escribirLineas(dir + "/src/resultado.txt", resultado);
                continuar = false;
            }
            try {
                Thread.sleep(1000*10);
            } catch (Exception e) {
            }
        }
        System.out.println("Tarea finalizada.");

    }

    public static void main(String[] args) {
        Integer hilos = 100;
        Integer longitudLista = 1000000;
        Integer limSuperior = 1000;
        Burbuja burbuja = new Burbuja(hilos, longitudLista, limSuperior);//100,000 y 100 hilos 2 segundosos
        //el numero mas grande debe ser mayor que la cantidad de hilos
    }

}

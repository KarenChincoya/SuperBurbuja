/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velasco.karen.controller;

import java.util.Random;

import archivosutilidades.ArchivoUtilidades;
import velasco.karen.view.MainFrame;

/**
 *
 * @author karen
 */
public class SuperBurbuja extends Thread {

	private BurbujaHilo[] hiloBurbuja;
	private Integer[] list;
	private Integer id;
	private Integer longitud;
	private Integer hilos;
	private Integer limiteSuperior;
	private String dir;
	private boolean status;
	private MainFrame oFrame;

	public SuperBurbuja(Integer id, Integer hilos, Integer longitud, Integer limiteSuperior, MainFrame mainFrame) {
		this.id = id;
		this.longitud = longitud;
		this.hilos = hilos;
		this.limiteSuperior = limiteSuperior;
		this.dir = System.getProperty("user.dir");
		this.status = false;
		this.oFrame = mainFrame;

		Random random = new Random();
		Integer n;
		list = new Integer[longitud];
		// 1. Random numbers list
		for (int i = 0; i < longitud; i++) {
			n = random.nextInt(limiteSuperior);
			list[i] = n;
		}
		System.out.println("Array list generado");
		try {
			// 2. Generar el archivo de los random
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

	}

	public SuperBurbuja(Integer[] list) {

		this.list = list;
		Integer longitud = list.length;
		Integer hilos = 10;
		System.out.println("Cantidad de numeros: " + longitud);
		this.status = false;
		this.id = 0;

	}

	public Integer getMax() {
		Integer maxLocal = null;

		for (int i = 0; i < list.length; i++) {
			// System.out.println("Dentro de la lista: "+lista.get(i));
			if (i == 0) {
				maxLocal = list[0];
			} else { // ya hay max y min
				if (list[i] > maxLocal) {
					maxLocal = list[i];
				}
			}
		}
		return maxLocal;
	}

	public Integer getMin() {
		Integer minLocal = null;

		for (int i = 0; i < list.length; i++) {
			// System.out.println("Dentro de la lista: "+lista.get(i));
			if (i == 0) {
				minLocal = list[0];
			} else { // ya hay max y min
				if (list[i] < minLocal) {
					minLocal = list[i];
				}
			}
		}
		return minLocal;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		System.out.println("Cantidad de numeros: " + longitud);
		// Obtener el maximo y el minimo del array list
		if(list.length==0 || list==null) {
			try {
				Thread.interrupted();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		Integer max = getMax();
		Integer min = getMin();
		Integer aux;

		System.out.println("Maximo = " + max);
		System.out.println("Minimo = " + min);
		
		Integer rango;
		
		if((max-min)>hilos) {
			rango = (((max - min) - ((max - min) % hilos)) / hilos)+1;	
		}else {
			rango = hilos;
		}
		
		System.out.println("Segmentos = " + rango);

		hiloBurbuja = new BurbujaHilo[hilos];

		// reparticion de limites
		Integer wall = min;
		Integer limInf;
		Integer limSup;

		for (int i = 0; i < hilos; i++) {
			limInf = wall;
			if (i == (hilos - 1)) {// el ultimo
				limSup = max;
			} else {
				wall += rango; // RANGO
				limSup = wall - 1;
			}
			hiloBurbuja[i] = new BurbujaHilo(i, list, limInf, limSup);
		}

		// ahora a imprimir los limites
		for (int i = 0; i < hilos; i++) {
			System.out.println("Hilo " + i + " ---->rangos:" + hiloBurbuja[i].getRango());
		}

		// ver cuantos numeros le toca a cada hilo
		Integer[] tamanios = new Integer[hilos];

		for (int i = 0; i < hilos; i++) {
			tamanios[i] = 0;
		}

		Integer numero;
		for (int i = 0; i < longitud; i++) {
			numero = list[i];
			// System.out.println("Reparticion -----> numero = "+numero);
			for (int j = 0; j < hilos; j++) {
				if (hiloBurbuja[j].dentroDelRango(numero) == true) {
					tamanios[j]++;
					// System.out.println(numero+" se agrego al hilo de rango
					// "+hiloBurbuja[j].getRango());
				}
			}
		}
		for (int j = 0; j < hilos; j++) {
			hiloBurbuja[j].setTamanio(tamanios[j]);
			hiloBurbuja[j].setLista(tamanios[j]);

		}

		// repartirlos en los hilos **************+++REPARTICION
		for (int i = 0; i < longitud; i++) {
			numero = list[i];
			// System.out.println("Reparticion -----> numero = "+numero);

			for (int j = 0; j < hilos; j++) {
				if (hiloBurbuja[j].dentroDelRango(numero) == true) {
					hiloBurbuja[j].addNumber(numero);
					// System.out.println(numero+" se agrego al hilo de rango
					// "+hiloBurbuja[j].getRango());
				}
			}

		}
		System.out.println("Los numeros fueron distribuidos");
		Integer suma = 0;
		for (int i = 0; i < hilos; i++) {
			System.out.println("Hilo " + i + " -----> tamanio = " + hiloBurbuja[i].getTamanio());
			suma += hiloBurbuja[i].getTamanio();
		}
		System.out.println("Numeros totales = " + suma);
		wall = 0;
		// asignar pIncial pFinal
		for (int i = 0; i < hilos; i++) {
			hiloBurbuja[i].setpInicial(wall);
			wall = wall + hiloBurbuja[i].getTamanio();
			hiloBurbuja[i].setpFinal(wall - 1);
		}

		// encender los hilos
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
				if (this.id == -1) {
					String[] resultado = new String[longitud];
					// imprimir la lista ordenada
					// System.out.println("Ordenada");
					for (int i = 0; i < longitud; i++) {
						resultado[i] = String.valueOf(list[i]);
						// System.out.print(" " + lista.get(i) + " , ");
					}

					ArchivoUtilidades.escribirLineas(dir + "/src/resultado.txt", resultado);
					this.status = true;
				}
				continuar = false;
			}
			try {
				Thread.sleep(1000 * 5);
			} catch (Exception e) {
			}
		}
		if (this.id == -1) {
			System.out.println("Tarea finalizada.");
			long endTime = System.currentTimeMillis();
			oFrame.setTiempoEjecucion(startTime, endTime);
			oFrame.setLblTareaTerminada();
		}
	}

	/*public static void main(String[] args) {
		Integer hilosFinal = 100;
		Integer longitudLista = 1000000;
		Integer limSuperior = 1000;
		
		//EL limite superior debe ser mayor a la cantidad de hilos
		
		SuperBurbuja burbuja = new SuperBurbuja(-1, hilosFinal, longitudLista, limSuperior);// 100,000 y 100 hilos 2
																						// segundosos
		burbuja.start();
		// el numero mas grande debe ser mayor que la cantidad de hilos
	}
*/
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}

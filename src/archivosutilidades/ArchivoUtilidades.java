/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivosutilidades;

/**
 *
 * @author Karen Velasco
 * 
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;


public class ArchivoUtilidades {

    /**
     * @param args the command line arguments
     */
    	
	public static String leer(String nombre) {
		File archivo = new File(nombre);

		if (!archivo.exists()) {
			return null;
		}
		
		try (FileReader fr = new FileReader(archivo);
				BufferedReader br = new BufferedReader(fr)) {
			String contenido = "";
			String temporal = br.readLine();
			
			while (temporal != null) {
				contenido += temporal;
				temporal = br.readLine();
			}
			
			return contenido;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

        public static void escribirLineas(String nombre, String[] lineas) {
		File archivo = new File(nombre);

		try (FileWriter fw = new FileWriter(archivo);
				BufferedWriter bw = new BufferedWriter(fw)) {
			for (String string : lineas) {
				bw.append(string + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
        
        public static String leerRenglon(Integer n, String nombre) {
		File archivo = new File(nombre);

		if (!archivo.exists()) {
			return null;
		}
		
		try (FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr)) {
			
                    String contenido = "";
		
                    String temporal = br.readLine();
			
                    int contador = 0;
                    while (temporal != null && contador<=n) {
                        if(contador == n) contenido += temporal;
                        temporal = br.readLine();
                        contador++;
		    }
			
			return contenido;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
        
        public static void escribirObjeto(String nombre, Object contenido) {
		File archivo = new File(nombre);

		try (FileOutputStream fos = new FileOutputStream(archivo);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(contenido);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] leerLineas(String nombre) {
		File archivo = new File(nombre);

		try (FileReader fr = new FileReader(archivo);
				BufferedReader br = new BufferedReader(fr)) {

			String[] lineas = new String[contarLineas(archivo)];

			for (int i = 0; i < lineas.length; i++) {
				lineas[i] = br.readLine();
			}

			return lineas;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	public static int contarLineas(File archivo) {
		try (FileReader fis = new FileReader(archivo);
				BufferedReader br = new BufferedReader(fis)) {
			int cuenta = 0;
			while (br.readLine() != null) {
				++cuenta;
			}

			return cuenta;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}
    
}

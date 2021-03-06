package tema1;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

public class tarea2 {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*
		 * el programa te pide la ruta de un fichero y si se ha modificado en los ultimos 10 dias lo pone de solo lectura.
		 * 
		 *
		 * 
		 */
		System.out.println("Escribe un directorio colgado de la raiz");
		Scanner sc = new Scanner(System.in);
		String ruta = sc.nextLine();
		String mensaje = "";
		long tiempo= new Date().getTime();
		
		// Utilizamos el String que contiene la ruta para
		// un objeto de la clase File
		File carpeta = new File(ruta);
		boolean existe = carpeta.exists();
		boolean esRoot = false;
		File[] root = carpeta.listRoots();
		try {
			while (!existe) {
				System.out.println("el directorio no existe");
				for (int i = 0; i < root.length; i++) {

					if (ruta.startsWith(root[i].getAbsolutePath())) {
						esRoot = true;
					}
				}

				if (esRoot == false) {
					throw new GestionExcepciones("no cuelga de la raiz por lo que no se puede crear " + carpeta.getAbsolutePath());

				}
				if (carpeta.mkdir()) {
					System.out.println("directorio creado " + carpeta.getAbsolutePath());
					System.out.println("directorio vacio");
					existe = true;
				} else {
					throw new GestionExcepciones("no se ha podido crear el directorio " + carpeta.getAbsolutePath());

				}

			}
			// Listo los archivos y los guardo en un Array de tipo File

			File[] archivos = carpeta.listFiles();
			// Recorro el array

			for (int i = 0; i < archivos.length; i++) {
				File archivoActual = archivos[i];
				// Si el File es un arvhivo se muestra el nombre
				// Si es una carpeta se muestra el s?mbolo '?' delante
				mensaje = archivoActual.getName() + "--- ";
				if (archivoActual.canWrite()) {
					mensaje += " se puede escribir,";
				} else {
					mensaje += " no se puede escribir,";
				}
				if (archivoActual.isHidden()) {
					mensaje += " esta oculto,";
				} else {
					mensaje += " no esta oculto,";
				}
				if(archivoActual.isFile()&& archivoActual.lastModified()+864000000 > tiempo ) {
					if(archivoActual.setReadOnly()==true) {
					
					System.out.println("El fichero: "+archivoActual.getName()+ " es solo de lectura");
					}else {
						System.out.println("El fichero: "+archivoActual.getName()+ "  no se ha podido poner en solo lectura");
					}
				}
				System.out.println(mensaje + " " + archivoActual.getTotalSpace() + " bytes");
				
			}
			
		} catch(GestionExcepciones e) {
			System.err.println(e.getMessage());
			existe = true;

		}
		catch (Exception e) {

			e.printStackTrace();
			existe = true;

		}
		
	}

}

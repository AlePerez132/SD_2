/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorbibliotecacliente;

import gestorbibliotecacomun.GestorBibliotecaIntf;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.Naming;
import gestorbibliotecacomun.TLibro;

/**
 *
 * @author alepd
 */
public class GestorBibliotecaCliente {

    static Scanner sc = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int Puerto = 8000;
        String Host = "localhost";
        int opcionElegida = -1;
        String contrasenha = "";
        int idAdministrador = 0;
        String nombreFichero = "";
        int NumLibros = 0;
        TLibro libro = new TLibro();
        String isbn = "";
        String autor = "";
        String titulo = "";
        int anio = 0;
        String pais = "";
        String idioma = "";
        TLibro nuevoLibro = new TLibro();//En C lo declarabámos como TNuevo, pero ya no existen esas estructuras en la versión de Java.
        int campoElegido = 0;
        String textoABuscar = "";
        char codigoBusqueda = '\0';
        int posicion = 0;
        String textoCampo = "";
        String isbnCompra = "";
        char confirmacionCompra = '\0';
        int numeroLibrosComprados = 0;
        int numeroLibrosRetirados = 0;

        //Declaramos variables result como en la práctica de C:
        int result_1 = -1;
        boolean result_2 = false;
        int result_3 = -1;
        int result_4 = -1;
        int result_5 = -1;
        int result_6 = -1;
        int result_7 = -1;
        int result_8 = -1;
        int result_9 = -1;
        int result_10 = -1;
        int result_11 = -1;
        int result_12 = -1;
        int result_13 = -1;
        int result_14 = -1;
        int result_15 = -1;

        try {
            GestorBibliotecaIntf GestorStub = (GestorBibliotecaIntf) Naming.lookup("rmi://" + Host + ":" + Puerto + "/GestorBiblioteca");// Obtiene el stub del rmiregistry
            do {
                opcionElegida = menuPrincipal();
                switch (opcionElegida) {
                    case 0: {
                        System.out.print("Cerrando programa...\n");
                        break;
                    }
                    case 1: {
                        System.out.print("Por favor inserte la contraseña de Administracion:\n");
                        contrasenha = sc.nextLine();
                        result_1 = GestorStub.Conexion(pais);
                        //En Java los métodos del stub no pueden devolver nulo como en C, saltaría a la excepción en tal caso.
                        if (result_1 == -2) {
                            System.out.print("ERROR: la contrasenha introducida es incorrecta\n");
                        } else if (result_1 == -1) {
                            System.out.print("ERROR: ya hay un administrador logueado\n");
                        } else {
                            idAdministrador = result_1;
                            System.out.print("*** Contraseña correcta, puede acceder al menu de Administracion.**\n");
                            esperarEntradaPorConsola(); // Esperamos a que el usuario pulse cualquier tecla.
                            do {
                                opcionElegida = menuAdministracion();
                                switch (opcionElegida) {
                                    case 0: {
                                        result_2 = GestorStub.Desconexion(idAdministrador);
                                        if (result_2 == false) {
                                            System.out.print("ERROR: el id administrador no coincide con el del servidor\n");
                                        } else {
                                            System.out.print("Ha cerrado sesion con exito\n");
                                        }
                                        break;
                                    }
                                    case 1: {
                                        System.out.print("Introduce el nombre del fichero de datos:\n");
                                        nombreFichero = sc.nextLine();
                                        result_3 = GestorStub.AbrirRepositorio(idAdministrador, nombreFichero);
                                        if (result_3 == -1) {
                                            System.out.print("ERROR: ya hay un administrador logueado\n");
                                        } else if (result_3 == 0) {
                                            System.out.print("ERROR: error al cargar los datos\n");
                                        } else if (result_3 == 1) {
                                            System.out.print("Datos cargados y ordenados correctamente\n");
                                        }
                                        break;
                                    }
                                }
                            } while (opcionElegida != 0);
                            opcionElegida = -1; // Si salimos del menú de administración, reseteamos la variable. Esto lo hacemos para evitar salir del menú principal.
                        }
                        break;
                    }
                }
            } while (opcionElegida != 0);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    static int menuPrincipal() {
        int opcionElegida = -1;
        do {
            System.out.println("\f");//https://stackoverflow.com/questions/10241217/how-to-clear-console-in-java
            System.out.print("GESTOR BIBLIOTECARIO 1.0 (M. PRINCIPAL)\n");
            System.out.print("***************************************\n");
            System.out.print("\t1.- M. Administracion\n");
            System.out.print("\t2.- Consulta de libros\n");
            System.out.print("\t3.- Prestamo de libros\n");
            System.out.print("\t4.- Devolucion de libros\n");
            System.out.print("\t0.- Salir\n");
            System.out.print("\n");
            System.out.print("  Elige opcion:\n");
            opcionElegida = sc.nextInt();
        } while (opcionElegida < 0 || opcionElegida > 4);
        return opcionElegida;
    }

    static int menuAdministracion() {
        int opcionElegida = -1;
        do {
            System.out.println("\f");//https://stackoverflow.com/questions/10241217/how-to-clear-console-in-java
            System.out.print("GESTOR BIBLIOTECARIO 1.0 (M. ADMINISTRACION)\n");
            System.out.print("********************************************\n");
            System.out.print("\t1.- Cargar Repositorio\n");
            System.out.print("\t2.- Guardar Repositorio\n");
            System.out.print("\t3.- Nuevo libro\n");
            System.out.print("\t4.- Comprar libros\n");
            System.out.print("\t5.- Retirar libros\n");
            System.out.print("\t6.- Ordenar libros\n");
            System.out.print("\t7.- Buscar libros\n");
            System.out.print("\t8.- Listar libros\n");
            System.out.print("\t0.- Salir\n");
            System.out.print("  Elige opcion:\n");
            opcionElegida = sc.nextInt();
        } while (opcionElegida < 0 || opcionElegida > 8);
        return opcionElegida;
    }

    static void esperarEntradaPorConsola() {
        System.out.print("Introduzca cualquier numero para continuar.....\n");
        sc.nextInt();
    }
}

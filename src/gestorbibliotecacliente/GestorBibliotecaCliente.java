/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorbibliotecacliente;

import gestorbibliotecacomun.GestorBibliotecaIntf;
import gestorbibliotecacomun.TDatosRepositorio;
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

    static Scanner scInt = new Scanner(System.in);
    static Scanner scString = new Scanner(System.in);

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
        TLibro libro = null;
        String isbn = "";
        String autor = "";
        String titulo = "";
        int anio = 0;
        String pais = "";
        String idioma = "";
        TLibro nuevoLibro = null;//En C lo declarabámos como TNuevo, pero ya no existen esas estructuras en la versión de Java.
        int campoElegido = 0;
        String textoABuscar = "";
        char codigoBusqueda = '\0';
        int posicion = 0;
        String textoCampo = "";
        String isbnCompra = "";
        char confirmacionCompra = '\0';
        int numeroLibrosComprados = 0;
        int numeroLibrosRetirados = 0;
        int numeroRepositorios = 0;
        TDatosRepositorio repositorio = null;
        int repositorioElegido = 0;
        int numeroLibrosInicial = 0;

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
                        System.out.print("Por favor inserte la contrasenha de Administracion:\n");
                        contrasenha = scString.nextLine();
                        System.out.println("La contrasenha introducida es igual a " + contrasenha);
                        result_1 = GestorStub.Conexion(contrasenha);
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
                                        nombreFichero = scString.nextLine();
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
                                    case 2: {
                                        numeroRepositorios = GestorStub.NRepositorios(idAdministrador);
                                        System.out.println("El numero de repositorios es "+numeroRepositorios);
                                        System.out.println("POS\tNOMBRE\tDIRECCION\tNº LIBROS");
                                        System.out.println("*********************************");
                                        for (int i = 1; i <= numeroRepositorios; i++) {
                                            repositorio = GestorStub.DatosRepositorio(idAdministrador, i);
                                            if (repositorio != null) {
                                                System.out.println(i + "\t" + repositorio.getNombre() + "\t" + repositorio.getDireccion() + "\t" + repositorio.getNumLibros());
                                            }
                                        }
                                        System.out.println("Elige repositorio:");
                                        repositorioElegido = scInt.nextInt();
                                        result_4 = GestorStub.GuardarRepositorio(idAdministrador, repositorioElegido);
                                        if (result_4 == -1) {
                                            System.out.print("ERROR: ya hay un administrador logueado\n");
                                        } else if (result_4 == -2) {
                                            System.out.print("ERROR: el repositorio no existe\n");
                                        } else if (result_4 == 0) {
                                            System.out.print("ERROR: no se ha podido guardar a fichero el/los repositorios\n");
                                        } else if (result_4 == 1) {
                                            System.out.print("Datos guardados correctamente\n");
                                        }
                                        break;
                                    }
                                    case 3: {
                                        // Pedimos los datos del nuevo libro:
                                        System.out.print("Introduce el Isbn:\n");
                                        isbn = scString.nextLine();
                                        System.out.print("Introduce el Autor:\n");
                                        autor = scString.nextLine();
                                        System.out.print("Introduce el Titulo:\n");
                                        titulo = scString.nextLine();
                                        System.out.print("Introduce el anio:\n");
                                        anio = scInt.nextInt();
                                        System.out.print("Introduce el Pais:\n");
                                        pais = scString.nextLine();
                                        System.out.print("Introduce el Idioma:\n");
                                        idioma = scString.nextLine();
                                        System.out.print("Introduce Numero de Libros inicial:\n");
                                        numeroLibrosInicial = scInt.nextInt();
                                        numeroRepositorios = GestorStub.NRepositorios(idAdministrador);
                                        System.out.println("POS\tNOMBRE\tDIRECCION\tNº LIBROS");
                                        System.out.println("*********************************");
                                        for (int i = 1; i <= numeroRepositorios; i++) {
                                            repositorio = GestorStub.DatosRepositorio(idAdministrador, i);
                                            if (repositorio != null) {
                                                System.out.println(i + "\t" + repositorio.getNombre() + "\t" + repositorio.getDireccion() + "\t" + repositorio.getNumLibros());
                                            }
                                        }
                                        System.out.println("Elige repositorio:");
                                        repositorioElegido = scInt.nextInt();
                                        // Llenamos la variable libro:
                                        libro.setIsbn(isbn);
                                        libro.setAutor(autor);
                                        libro.setTitulo(titulo);
                                        libro.setAnio(anio);
                                        libro.setPais(pais);
                                        libro.setIdioma(idioma);

                                        // Inicializamos los valores no pedidos por consola:
                                        libro.setNoLibros(0);
                                        libro.setNoListaEspera(0);
                                        libro.setNoPrestados(0);

                                        result_5 = GestorStub.NuevoLibro(idAdministrador, libro, repositorioElegido);

                                        if (result_5 == -1) {
                                            System.out.print("ERROR: ya hay un administrador logueado\n");
                                        } else if (result_5 == 0) {
                                            System.out.print("ERROR: ya hay un libro registrado con el ISBN dado\n");
                                        } else if (result_5 == -2) {
                                            System.out.print("ERROR: el repositorio no existe\n");
                                        } else if (result_5 == 1) {
                                            System.out.print("Se ha anhadido el nuevo libro correctamente\n");
                                        }
                                        break;
                                    }
                                    case 4: {
                                        System.out.print("Introduce Isbn a Buscar:\n");
                                        isbnCompra = scString.nextLine();
                                        // Por ISBN.
                                        result_10 = GestorStub.Buscar(idAdministrador, isbnCompra);
                                        if (result_10 == -1) {
                                            System.out.print("ERROR: no se ha encontrado ningun libro\n");
                                        } else if (result_10 == -2) {
                                            System.out.print("ERROR: ya hay un administrador logueado\n");
                                        } else {
                                            // Tenemos la posición del libro buscado en result_10.
                                            
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
            opcionElegida = scInt.nextInt();
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
            opcionElegida = scInt.nextInt();
        } while (opcionElegida < 0 || opcionElegida > 8);
        return opcionElegida;
    }

    static void esperarEntradaPorConsola() {
        System.out.print("Introduzca cualquier numero para continuar.....\n");
        scInt.nextInt();
    }
}

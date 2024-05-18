/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorbibliotecacliente;

import gestorbibliotecacomun.GestorBibliotecaIntf;
import gestorbibliotecacomun.TDatosRepositorio;
import java.util.Scanner;
import java.rmi.Naming;
import gestorbibliotecacomun.TLibro;

/**
 *
 * @author alepd
 */
public class GestorBibliotecaCliente {

    static Scanner scInt = new Scanner(System.in);
    static Scanner scString = new Scanner(System.in);
    static Scanner scChar = new Scanner(System.in);

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
        boolean punteroAlgunaCoincidencia[] = {false, false, false, false, false}; // Array de booleanos para la búsqueda en todos los campos (*).

        //Declaramos variables result como en la práctica de C:
        int result_1 = -1;
        boolean result_2 = false;
        int result_3 = -1;
        int result_4 = -1;
        int result_5 = -1;
        int result_6 = -1;
        int result_7 = -1;
        boolean result_8 = false;
        int result_9 = -1;
        int result_10 = -1;
        TLibro result_11 = null;
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
                                    case 0: { //Desconexion
                                        result_2 = GestorStub.Desconexion(idAdministrador);
                                        if (result_2 == false) {
                                            System.out.print("ERROR: el id administrador no coincide con el del servidor\n");
                                        } else {
                                            System.out.print("Ha cerrado sesion con exito\n");
                                        }
                                        break;
                                    }
                                    case 1: { //Abrir repositorio
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
                                    case 2: { // Guardar repositorio
                                        numeroRepositorios = GestorStub.NRepositorios(idAdministrador);
                                        System.out.println("El numero de repositorios es " + numeroRepositorios);
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
                                    case 3: { //Nuevo libro
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
                                        // Reservamos memroia y llenamos la variable libro:
                                        libro = new TLibro();
                                        libro.setIsbn(isbn);
                                        libro.setAutor(autor);
                                        libro.setTitulo(titulo);
                                        libro.setAnio(anio);
                                        libro.setPais(pais);
                                        libro.setIdioma(idioma);

                                        // Inicializamos los valores no pedidos por consola:
                                        libro.setNoLibros(numeroLibrosInicial);
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
                                    case 4: { //Comprar libro
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
                                            result_11 = GestorStub.Descargar(idAdministrador, -1, result_10);
                                            if (result_11 == null) {
                                                System.out.print("ERROR: no se ha podido descargar el libro\n");
                                            } else {
                                                System.out.print(libro.getTitulo() + "\t" + libro.getIsbn() + "\t" + libro.getNoLibros() + "\t" + libro.getNoPrestados() + "\t" + libro.getNoListaEspera() + "\n");
                                                System.out.print(libro.getAutor() + "\t" + libro.getPais() + "\t" + libro.getIdioma() + "\t" + libro.getAnio());
                                                System.out.print("¿ Es este el libro al que desea comprar más unidades (s/n) ?\n");
                                                confirmacionCompra = scChar.next().charAt(0);
                                                if (confirmacionCompra != 's') { // Si el usuario no ha confirmado con s:
                                                    System.out.print("*** Compra abortada ***\n");
                                                } else { // Si el usuario ha confirmado con s:
                                                    System.out.print("Introduce Numero de Libros comprados:\n");
                                                    numeroLibrosComprados = scInt.nextInt();
                                                    // Pasamos los parámetros:
                                                    result_6 = GestorStub.Comprar(idAdministrador, isbnCompra, numeroLibrosComprados);
                                                    if (result_6 == -1) {
                                                        System.out.print("ERROR: ya hay un administrador logueado\n");
                                                    } else if (result_6 == 0) {
                                                        System.out.print("ERROR: no se ha encontrado el libro\n");
                                                    } else if (result_6 == 1) {
                                                        System.out.print("*** Se han agregado los nuevos ejemplares del libro y los datos están ordenados ***\n");
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case 5: {//Retirar libro.
                                        System.out.print("Introduce Isbn a Buscar:\n");
                                        isbnCompra = scString.nextLine();
                                        // Por ISBN.
                                        result_10 = GestorStub.Buscar(idAdministrador, isbnCompra);
                                        if (result_10 == -2) {
                                            System.out.print("ERROR: ya hay un administrador logueado");
                                        } else if (result_10 == -1) {
                                            System.out.println("No se ha encontrado ningun libro");
                                        } else { //mostrar libro
                                            result_11 = GestorStub.Descargar(idAdministrador, -1, result_10 + 1);
                                            if (result_11 != null) {
                                                // Hemos recibido el resultado bien, podemos guardarlo en libro y escribir por pantalla.
                                                libro = result_11;
                                                System.out.print((result_10 + 1) + "\t" + libro.getTitulo() + "\t" + libro.getIsbn() + "\t" + libro.getNoLibros() + "\t" + libro.getNoListaEspera() + "\n");
                                                System.out.print(libro.getAutor() + "\t" + libro.getPais() + "\t" + libro.getIdioma() + "\t" + libro.getAnio() + "\n");
                                            }
                                        }
                                        System.out.println("Es este el libro que quieres retirar(s/n)?");
                                        confirmacionCompra = scChar.next().charAt(0);
                                        if (confirmacionCompra == 's') {
                                            System.out.println("Introduce el numero de unidades a retirar:");
                                            numeroLibrosRetirados = scInt.nextInt();
                                            result_7 = GestorStub.Retirar(idAdministrador, isbnCompra, numeroLibrosRetirados);
                                            if (result_7 == -1) {
                                                System.out.print("ERROR: ya hay un administrador logueado");
                                            } else if (result_7 == 0) {
                                                System.out.print("ERROR: no se ha encontrado ningun libro");
                                            } else if (result_7 == 2) {
                                                System.out.println("No hay suficientes ejemplares para retirar");
                                            } else if (result_7 == 1) {
                                                System.out.println("Se han reducido el número de ejemplares disponibles y se han ordenado los datos");
                                            }
                                        }
                                        break;
                                    }
                                    case 6: {//Ordenar.
                                        System.out.println("0.- Por Isbn\n");
                                        System.out.println("1.- Por Titulo\n");
                                        System.out.println("2.- Por Autor\n");
                                        System.out.println("3.- Por Anho\n");
                                        System.out.println("4.- Por Pais\n");
                                        System.out.println("5.- Por Idioma\n");
                                        System.out.println("6.- Por nº de libros Disponibles\n");
                                        System.out.println("7.- Por nº de libros Prestados\n");
                                        System.out.println("8.- Por nº de libros en Espera\n");
                                        System.out.println("Elige el campo que ordenara los libros:\n");
                                        campoElegido = scInt.nextInt();
                                        // Llamamos a ordenar en el servidor:
                                        result_8 = GestorStub.Ordenar(idAdministrador, campoElegido);
                                        if (campoElegido >= 0 && campoElegido <= 8) {
                                            if (!result_8) {
                                                System.out.println("ERROR: el id administrador no coincide con el del servidor\n");
                                            } else if (result_8) {
                                                System.out.println("Se ha ordenado correctamente el vector\n");
                                            }
                                        }
                                        break;
                                    }
                                    case 7: {//Buscar.
                                        System.out.print("Introduce el texto a buscar:\n");
                                        textoABuscar = scString.nextLine();
                                        System.out.print("I.- Por Isbn\n");
                                        System.out.print("T.- Por Titulo\n");
                                        System.out.print("A.- Por Autor\n");
                                        System.out.print("P.- Por Pais\n");
                                        System.out.print("D.- Por Idioma\n");
                                        System.out.print("*.- Por todos los campos\n");
                                        System.out.print("Introduce el codigo de busqueda\n");
                                        codigoBusqueda = scChar.next().charAt(0);
                                        for (int i = 1; i <= numeroRepositorios; i++) {
                                            repositorio = GestorStub.DatosRepositorio(idAdministrador, i);
                                            if (repositorio != null) {
                                                System.out.println(i + "\t" + repositorio.getNombre() + "\t" + repositorio.getDireccion() + "\t" + repositorio.getNumLibros());
                                            }
                                        }
                                        result_9 = GestorStub.NLibros(-1); // Recogemos el nº de libros del servidor para todos los repos.
                                        System.out.println("0\tTodos los repositorios\t\t" + result_9);
                                        System.out.println("Elige repositorio:");
                                        repositorioElegido = scInt.nextInt();
                                        if (repositorioElegido == 0) {
                                            repositorioElegido = -1;//El servidor entiende todos los repos como -1, el cliente lo ve como 0. De ahí esta condición y asignación.
                                        }
                                        //Descargaremos cada libro y filtraremos:
                                        result_9 = GestorStub.NLibros(repositorioElegido); // Recogemos el nº de libros del servidor.
                                        if (result_9 == -1) {
                                            System.out.print("ERROR: el repositorio no existe\n");
                                        } else {
                                            System.out.print("POS\tTITULO\tISBN\tDIS\tPRE\tPOS\n");
                                            System.out.print("\tAUTOR\tPAIS (IDIOMA)\tANIO\n");
                                            System.out.print("*********************************************************************************************\n");

                                            NumLibros = result_9;

                                            // Descargaremos cada libro del servidor. Si pasa el filtrado, lo mostraremos por pantalla:
                                            for (int i = 0; i < NumLibros; i++) {
                                                result_11 = GestorStub.Descargar(idAdministrador, repositorioElegido, i);
                                                if (result_11 != null) {
                                                    libro = result_11;// Hemos recibido el resultado bien, podemos guardarlo en libro.
                                                    // Solo mostraremos el libro si aparece la cadena buscada en los campos deseados.
                                                    // Usaremos contains.
                                                    // Para la búsqueda monocampo, emplearemos el puntero respectivo: punteroAlgunaCoincidencia[i]. i valdrá lo que diga el mapa: {Isbn:0, Titulo:1, Autor:2, Pais:3, Idioma:4}.
                                                    // Para la búsqueda multicampo (*), emplearemos todo el array de punteros: punteroAlgunaCoincidencia.
                                                    switch (codigoBusqueda) {
                                                        case 'I': {
                                                            punteroAlgunaCoincidencia[0] = libro.getIsbn().contains(textoABuscar); // Isbn:0.
                                                            punteroAlgunaCoincidencia[1] = false;
                                                            punteroAlgunaCoincidencia[2] = false;
                                                            punteroAlgunaCoincidencia[3] = false;
                                                            punteroAlgunaCoincidencia[4] = false;
                                                            break;
                                                        }
                                                        case 'T': {
                                                            punteroAlgunaCoincidencia[1] = libro.getTitulo().contains(textoABuscar); // Titulo:1.
                                                            // Inicializo el resto de punteros a false:
                                                            punteroAlgunaCoincidencia[0] = false;
                                                            punteroAlgunaCoincidencia[2] = false;
                                                            punteroAlgunaCoincidencia[3] = false;
                                                            punteroAlgunaCoincidencia[4] = false;
                                                            break;
                                                        }
                                                        case 'A': {
                                                            punteroAlgunaCoincidencia[2] = libro.getAutor().contains(textoABuscar); // Autor:2.
                                                            // Inicializo el resto de punteros a false:
                                                            punteroAlgunaCoincidencia[0] = false;
                                                            punteroAlgunaCoincidencia[1] = false;
                                                            punteroAlgunaCoincidencia[3] = false;
                                                            punteroAlgunaCoincidencia[4] = false;
                                                            break;
                                                        }
                                                        case 'P': {
                                                            punteroAlgunaCoincidencia[3] = libro.getPais().contains(textoABuscar); // Pais:3.
                                                            // Inicializo el resto de punteros a false:
                                                            punteroAlgunaCoincidencia[0] = false;
                                                            punteroAlgunaCoincidencia[1] = false;
                                                            punteroAlgunaCoincidencia[2] = false;
                                                            punteroAlgunaCoincidencia[4] = false;
                                                            break;
                                                        }
                                                        case 'D': {
                                                            punteroAlgunaCoincidencia[4] = libro.getIdioma().contains(textoABuscar); // Idioma:4.
                                                            // Inicializo el resto de punteros a false:
                                                            punteroAlgunaCoincidencia[0] = false;
                                                            punteroAlgunaCoincidencia[1] = false;
                                                            punteroAlgunaCoincidencia[2] = false;
                                                            punteroAlgunaCoincidencia[3] = false;
                                                            break;
                                                        }
                                                        case '*': {
                                                            punteroAlgunaCoincidencia[0] = libro.getIsbn().contains(textoABuscar); // Isbn:1.
                                                            punteroAlgunaCoincidencia[1] = libro.getTitulo().contains(textoABuscar); // Titulo:1.
                                                            punteroAlgunaCoincidencia[2] = libro.getAutor().contains(textoABuscar); // Autor:2.
                                                            punteroAlgunaCoincidencia[3] = libro.getPais().contains(textoABuscar); // Pais:3.
                                                            punteroAlgunaCoincidencia[4] = libro.getIdioma().contains(textoABuscar); // Idioma:4.
                                                            break;
                                                        }
                                                    }
                                                    if (punteroAlgunaCoincidencia[0] != false || punteroAlgunaCoincidencia[1] != false || punteroAlgunaCoincidencia[2] != false || punteroAlgunaCoincidencia[3] != false || punteroAlgunaCoincidencia[4] != false) {
                                                        System.out.print(i + "\t" + libro.getTitulo() + "\t" + libro.getIsbn() + "\t" + libro.getNoLibros() + "\t" + libro.getNoListaEspera() + "\n");
                                                        System.out.print(libro.getAutor() + "\t" + libro.getPais() + "\t" + libro.getIdioma() + "\t" + libro.getAnio() + "\n");
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    }

                                    case 8: { //Listar libro
                                        // Recogemos del servidor el numero de libros:
                                        result_9 = GestorStub.NLibros(-1); // Recogemos el nº de libros del servidor (todos los repositorios con *).
                                        if (result_9 == -1) {
                                            System.out.print("ERROR: el repositorio no existe\n");
                                        } else {
                                            System.out.print("POS\tTITULO\tISBN\tDIS\tPRE\tPOS\n");
                                            System.out.print("\tAUTOR\tPAIS (IDIOMA)\tANIO\n");
                                            System.out.print("*********************************************************************************************\n");

                                            NumLibros = result_9;
                                            //MODIFICADO TEMPORALMENTE PARA ACCEDER AL PRIMER REPOSITORIO
                                            for (int i = 1; i <= NumLibros; i++) {
                                                result_11 = GestorStub.Descargar(idAdministrador, -1, i);
                                                if (result_11 != null) {
                                                    // Hemos recibido el resultado bien, podemos guardarlo en libro y escribir por pantalla.
                                                    libro = result_11;
                                                    System.out.print(i + "\t" + libro.getTitulo() + "\t" + libro.getIsbn() + "\t" + libro.getNoLibros() + "\t" + libro.getNoListaEspera() + "\n");
                                                    System.out.print(libro.getAutor() + "\t" + libro.getPais() + "\t" + libro.getIdioma() + "\t" + libro.getAnio() + "\n");
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                                esperarEntradaPorConsola(); // Esperamos a que el usuario pulse cualquier tecla.
                            } while (opcionElegida != 0);
                            opcionElegida = -1; // Si salimos del menú de administración, reseteamos la variable. Esto lo hacemos para evitar salir del menú principal.
                        }
                        break;
                    }
                }
                if (opcionElegida != -1) {								// Si la opción elegida se ha introducido:
                    esperarEntradaPorConsola(); // Esperamos a que el usuario pulse cualquier tecla.
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

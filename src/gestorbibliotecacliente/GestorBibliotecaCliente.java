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

/**
 *
 * @author alepd
 */
public class GestorBibliotecaCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int Puerto = 8000;
            String Host = "localhost";
            Scanner Teclado = new Scanner(System.in);

            // Obtiene el stub del rmiregistry
            GestorBibliotecaIntf GestorStub = (GestorBibliotecaIntf) Naming.lookup("rmi://" + Host + ":" + Puerto + "/GestorBiblioteca");

            System.out.println("Conectado");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}

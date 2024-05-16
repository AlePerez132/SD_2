/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorbibliotecacliente;

/**
 *
 * @author alepd
 */
public class Util {

    /**
     * *************************************************************
     * Código para mostrar un libro y su cabecera en caso necesario.
     * *************************************************************
     */
    private String Ajustar(String S, int Ancho) {
        byte v[] = S.getBytes();
        int c = 0;
        int len = 0;
        int uin;
        for (int i = 0; i < v.length; i++) {
            uin = Byte.toUnsignedInt(v[i]);
            if (uin > 128) {
                c++;
            }
        }

        len = c / 2;

        for (int i = 0; i < len; i++) {
            S = S + " ";
        }

        return S;
    }

    /*public void Mostrar(int Pos, boolean Cabecera) {
        if (Cabecera) {
            System.out.println(String.format("%-5s%-58s%-18s%-4s%-4s%-4s", "POS", "TITULO", "ISBN", "DIS", "PRE", "RES"));
            System.out.println(String.format("     %-30s%-28s%-12s", "AUTOR", "PAIS (IDIOMA)", "AÑO"));
            for (int i = 0; i < 93; i++) {
                System.out.print("*");
            }
            System.out.print("\n");
        }

        String T = Ajustar(String.format("%-58s", Titulo), 58);
        String A = Ajustar(String.format("%-30s", Autor), 30);
        String PI = Ajustar(String.format("%-28s", Pais + " (" + Idioma + ")"), 28);

        System.out.println(String.format("%-5d%s%-18s%-4d%-4d%-4d", Pos + 1, T, Isbn, NoLibros, NoPrestados, NoListaEspera));
        System.out.println(String.format("     %s%s%-12d", A, PI, Anio));
    }*/
}

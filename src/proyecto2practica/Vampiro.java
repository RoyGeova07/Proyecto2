/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2practica;

import javax.swing.ImageIcon;


import javax.swing.ImageIcon;

public class Vampiro {
    private int puntosAtaque;
    private int puntosVida;
    private int puntosEscudo;
    private ImageIcon imagenBlanca;
    private ImageIcon imagenNegra;

    public Vampiro() {
        this.puntosAtaque = 3;
        this.puntosVida = 4;
        this.puntosEscudo = 5;
        this.imagenBlanca = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Vampiro Blanco.jpeg"));
        this.imagenNegra = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Vampiro Negro.jpeg"));
    }

    public ImageIcon getImagenBlanca() {
        return imagenBlanca;
    }

    public ImageIcon getImagenNegra() {
        return imagenNegra;
    }

    public int getPuntosAtaque() {
        return puntosAtaque;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getPuntosEscudo() {
        return puntosEscudo;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

    public void setPuntosEscudo(int puntosEscudo) {
        this.puntosEscudo = puntosEscudo;
    }

    // Método para chupar sangre (ataque especial del Vampiro)
    public void chuparSangre(Pieza enemigo) {
        if (enemigo instanceof Pieza) {
            enemigo.recibirAtaque(puntosAtaque); // Restar puntos de vida al enemigo
            this.setPuntosVida(this.getPuntosVida() + 1); // Recuperar un punto de vida al Vampiro
        } else {
            System.out.println("El Vampiro solo puede chupar sangre de un Hombre Lobo.");
        }
    }
}
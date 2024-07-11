/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2practica;

import javax.swing.ImageIcon;

/**
 *
 * @author royum
 */
import javax.swing.ImageIcon;

/**
 *
 * @author royum
 */
public class Zombie {
    private int puntosAtaque;
    private int puntosVida;
    private int puntosEscudo;
    private ImageIcon ImagenBlanca;
    private ImageIcon ImagenNegra;

    public Zombie() {
        this.puntosAtaque = 1;
        this.puntosVida = 1;
        this.puntosEscudo = 0;
        this.ImagenBlanca = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Imagen Zombie.jpg"));
        this.ImagenNegra = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Zombie Negro.jpg"));
    }
    
     public ImageIcon getImagenBlanca() {
        return ImagenBlanca;
    }

    public ImageIcon getImagenNegra() {
        return ImagenNegra;
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
}

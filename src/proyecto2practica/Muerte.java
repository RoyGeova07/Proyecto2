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
public class Muerte {
    private int puntosAtaque;
    private int puntosVida;
    private int puntosEscudo;
    private ImageIcon ImagenBlanca;
    private ImageIcon ImagenNegra;

    public Muerte() {
        this.puntosAtaque = 4;
        this.puntosVida = 3;
        this.puntosEscudo = 1;
        this.ImagenBlanca = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Muerte Blanco.jpeg"));
        this.ImagenNegra = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Muerte Negro.jpeg"));
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

    public void ataqueEspecial(HombreLobo enemigo) {
        enemigo.recibirAtaque(4); // Ataque especial de la Muerte contra un Hombre Lobo
    }
}
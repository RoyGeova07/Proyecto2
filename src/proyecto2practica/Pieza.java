/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2practica;
                                        
public class Pieza {
    private String nombre;
    private int puntosAtaque;
    private int puntosVida;
    private int puntosEscudo;
    private String color;

    public Pieza(String nombre, int puntosAtaque, int puntosVida, int puntosEscudo, String color) {
        this.nombre = nombre;
        this.puntosAtaque = puntosAtaque;
        this.puntosVida = puntosVida;
        this.puntosEscudo = puntosEscudo;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
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

    public String getColor() {
        return color;
    }

    public void recibirAtaque(int puntos) {
        if (puntosEscudo > 0) {
            puntosEscudo -= puntos;
            if (puntosEscudo < 0) {
                puntosVida += puntosEscudo;
                puntosEscudo = 0;
            }
        } else {
            puntosVida -= puntos;
        }
    }

    public boolean estaViva() {
        return puntosVida > 0;
    }

    public int[][] movimientosValidos(int x, int y, Pieza[][] tablero) {
        int[][] direcciones = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
        int[][] movimientos = new int[8][2];
        int contador = 0;

        for (int[] dir : direcciones) {
            int nuevoX = x + dir[0];
            int nuevoY = y + dir[1];
            if (esMovimientoValido(nuevoX, nuevoY, tablero)) {
                movimientos[contador++] = new int[]{nuevoX, nuevoY};
            }
        }

        int[][] result = new int[contador][2];
        System.arraycopy(movimientos, 0, result, 0, contador);
        return result;
    }

    private boolean esMovimientoValido(int x, int y, Pieza[][] tablero) {
        return x >= 0 && x < 6 && y >= 0 && y < 6 && tablero[x][y] == null;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2practica;

import java.util.Date;

/**
 *
 * @author royum
 */
public class Jugador {

    private String nombreUsuario;
    private String contraseña;
    private int puntos;
    private Date fechaRegistro;
    private boolean activo;

    public Jugador(String nombreUsuario, String contraseña, int puntos, Date fechaRegistro, boolean activo) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.puntos = puntos;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }
}

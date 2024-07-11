/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2practica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Tablero {

    private JButton[][] botones;
    private Pieza[][] piezas;
    private JPanel panelRuleta;
    private Jugador jugadorLogueado;
    private Jugador oponente;
    private JLabel labelJugadorBlanco;
    private JLabel labelJugadorNegro;
    private JLabel labelTurno;
    private int seleccionX = -1;
    private int seleccionY = -1;
    private String piezaPermitida = null;
    private int turno; // 0 para el jugador blanco, 1 para el jugador negro

    public Tablero(Jugador jugadorLogueado, Jugador oponente) {
        this.jugadorLogueado = jugadorLogueado;
        this.oponente = oponente;
        botones = new JButton[6][6];
        piezas = new Pieza[6][6];
        panelRuleta = new JPanel();
        turno = 0; // Empieza el jugador blanco
    }

    public JPanel crearTableroPanel() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelTablero = new JPanel(new GridLayout(6, 6));

        Color color1 = Color.WHITE;
        Color color2 = Color.BLACK;

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                botones[fila][columna] = new JButton();
                if ((fila + columna) % 2 == 0) {
                    botones[fila][columna].setBackground(color1);
                } else {
                    botones[fila][columna].setBackground(color2);
                }
                final int f = fila;
                final int c = columna;
                botones[fila][columna].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        manejarClic(f, c);
                    }
                });
                panelTablero.add(botones[fila][columna]);
            }
        }

        // aqui se configura el panel de la ruleta y los nombres de los jugadores
        panelRuleta.setPreferredSize(new Dimension(200, 600));
        panelRuleta.setBorder(BorderFactory.createTitledBorder("Ruleta y Jugadores"));

        JPanel panelNombres = new JPanel(new GridLayout(2, 1));
        labelJugadorBlanco = new JLabel("Jugador Blanco: " + jugadorLogueado.getNombreUsuario(), SwingConstants.CENTER);
        labelJugadorNegro = new JLabel("Jugador Negro: " + oponente.getNombreUsuario(), SwingConstants.CENTER);
        panelNombres.add(labelJugadorBlanco);
        panelNombres.add(labelJugadorNegro);

        // Etiqueta del turno
        labelTurno = new JLabel("Turno de: " + jugadorLogueado.getNombreUsuario(), SwingConstants.CENTER);

        // Añadir panel de nombres y ruleta al panel principal
        JPanel panelRuletaYNombres = new JPanel(new BorderLayout());
        panelRuletaYNombres.add(panelRuleta, BorderLayout.CENTER);
        panelRuletaYNombres.add(panelNombres, BorderLayout.NORTH);

        // Crear el botón de "Abandonar Partida"
        JButton botonAbandonar = new JButton("Abandonar Partida");
        botonAbandonar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(panelPrincipal, "¿Estás seguro de que deseas abandonar la partida?", "Confirmar Abandono", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    abandonarPartida();
                }
            }
        });

        panelPrincipal.add(panelRuletaYNombres, BorderLayout.EAST);
        panelPrincipal.add(panelTablero, BorderLayout.CENTER);
        panelPrincipal.add(labelTurno, BorderLayout.SOUTH);
        panelPrincipal.add(botonAbandonar, BorderLayout.NORTH); // Añadir el botón de "Abandonar Partida"

        SwingUtilities.invokeLater(() -> { // este es es responsable de manejar todos los eventos de la interfaz de usuario, como clics de botones y actualizaciones de componentes.
            inicializarPiezas();
            cargarImagenes();
            inicializarRuleta();
        });

        return panelPrincipal;
    }

    private void manejarClic(int fila, int columna) {
        if (seleccionX == -1 && seleccionY == -1) {
            if (piezas[fila][columna] != null && piezas[fila][columna].getColor().equals(turno == 0 ? "Blanco" : "Negro")) {
                seleccionX = fila;
                seleccionY = columna;
                if (piezaPermitida == null || piezas[fila][columna].getNombre().contains(piezaPermitida)) {
                    marcarMovimientosValidos(seleccionX, seleccionY); // Indicar selección
                }
            }
        } else {
            Pieza piezaSeleccionada = piezas[seleccionX][seleccionY];
            if (piezaSeleccionada != null && esMovimientoValido(seleccionX, seleccionY, fila, columna)) {
                piezas[fila][columna] = piezas[seleccionX][seleccionY];
                piezas[seleccionX][seleccionY] = null;
                botones[fila][columna].setText(botones[seleccionX][seleccionY].getText());
                botones[seleccionX][seleccionY].setText("");
                botones[fila][columna].setIcon(botones[seleccionX][seleccionY].getIcon());
                botones[seleccionX][seleccionY].setIcon(null);
                botones[seleccionX][seleccionY].setBackground((seleccionX + seleccionY) % 2 == 0 ? Color.WHITE : Color.BLACK);
                limpiarIndicaciones();
                seleccionX = -1;
                seleccionY = -1;

                cambiarTurno();
            }
        }
    }

    private void marcarMovimientosValidos(int x, int y) {
        Pieza pieza = piezas[x][y];
        if (pieza != null) {
            int[][] movimientos = pieza.movimientosValidos(x, y, piezas);
            for (int[] mov : movimientos) {
                int fila = mov[0];
                int columna = mov[1];
                botones[fila][columna].setBackground(Color.RED);
            }
        }
    }

    private boolean esMovimientoValido(int x1, int y1, int x2, int y2) {
        Pieza pieza = piezas[x1][y1];
        if (pieza != null) {
            int[][] movimientos = pieza.movimientosValidos(x1, y1, piezas);
            for (int[] mov : movimientos) {
                if (mov[0] == x2 && mov[1] == y2) {
                    return true;
                }
            }
        }
        return false;
    }

    private void limpiarIndicaciones() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                botones[fila][columna].setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.BLACK);
            }
        }
    }

    private void cambiarTurno() {
        turno = (turno + 1) % 2;
        String nombreJugadorTurno = turno == 0 ? jugadorLogueado.getNombreUsuario() : oponente.getNombreUsuario();
        labelTurno.setText("Turno de: " + nombreJugadorTurno);
        piezaPermitida = null;
    }

    private void inicializarPiezas() {
        Pieza hombreLoboBlanco = new HombreLobo("Blanco");
        Pieza vampiroBlanco = new Pieza("Vampiro Blanco", 3, 4, 5, "Blanco");
        Pieza muerteBlanco = new Pieza("Muerte Blanco", 4, 3, 1, "Blanco");
        Pieza hombreLoboNegro = new HombreLobo("Negro");
        Pieza vampiroNegro = new Pieza("Vampiro Negro", 3, 4, 5, "Negro");
        Pieza muerteNegro = new Pieza("Muerte Negro", 4, 3, 1, "Negro");

        Pieza[] piezasBlancas = {
            hombreLoboBlanco, vampiroBlanco, muerteBlanco,
            muerteBlanco, vampiroBlanco, hombreLoboBlanco
        };
        Pieza[] piezasNegras = {
            hombreLoboNegro, vampiroNegro, muerteNegro,
            muerteNegro, vampiroNegro, hombreLoboNegro
        };

        for (int indice = 0; indice < 6; indice++) {
            botones[0][indice].setText(piezasBlancas[indice].getNombre());
            botones[0][indice].setForeground(Color.BLACK);
            botones[0][indice].setBackground(Color.WHITE);
            piezas[0][indice] = piezasBlancas[indice];

            botones[5][indice].setText(piezasNegras[indice].getNombre());
            botones[5][indice].setForeground(Color.WHITE);
            botones[5][indice].setBackground(Color.BLACK);
            piezas[5][indice] = piezasNegras[indice];
        }
    }

    private void cargarImagenes() {
        ImageIcon hombreLoboBlancoIcon = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Hombre Lobo Blanco.jpeg"));
        ImageIcon vampiroBlancoIcon = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Vampiro Blanco.jpeg"));
        ImageIcon muerteBlancoIcon = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Muerte Blanco.jpeg"));
        ImageIcon hombreLoboNegroIcon = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Hombre Lobo Negro.jpeg"));
        ImageIcon vampiroNegroIcon = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Vampiro Negro.jpeg"));
        ImageIcon muerteNegroIcon = new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Muerte Negro.jpeg"));

        botones[0][0].setIcon(resizeImageIcon(hombreLoboBlancoIcon, botones[0][0]));
        botones[0][1].setIcon(resizeImageIcon(vampiroBlancoIcon, botones[0][1]));
        botones[0][2].setIcon(resizeImageIcon(muerteBlancoIcon, botones[0][2]));
        botones[0][3].setIcon(resizeImageIcon(muerteBlancoIcon, botones[0][3]));
        botones[0][4].setIcon(resizeImageIcon(vampiroBlancoIcon, botones[0][4]));
        botones[0][5].setIcon(resizeImageIcon(hombreLoboBlancoIcon, botones[0][5]));

        botones[5][0].setIcon(resizeImageIcon(hombreLoboNegroIcon, botones[5][0]));
        botones[5][1].setIcon(resizeImageIcon(vampiroNegroIcon, botones[5][1]));
        botones[5][2].setIcon(resizeImageIcon(muerteNegroIcon, botones[5][2]));
        botones[5][3].setIcon(resizeImageIcon(muerteNegroIcon, botones[5][3]));
        botones[5][4].setIcon(resizeImageIcon(vampiroNegroIcon, botones[5][4]));
        botones[5][5].setIcon(resizeImageIcon(hombreLoboNegroIcon, botones[5][5]));
    }

    private ImageIcon resizeImageIcon(ImageIcon originalIcon, JButton button) {
        int width = button.getWidth();
        int height = button.getHeight();
        if (width == 0 || height == 0) {
            width = 100;
            height = 100;
        }

        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon resizeRuletaImageIcon(ImageIcon originalIcon, int width, int height) {
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void inicializarRuleta() {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // este  se utiliza para especificar la posición central en un área, lo que es útil para alinear componentes horizontalmente al centro

        JButton startButton = new JButton("Iniciar");
        JButton stopButton = new JButton("Detener");

        int ruletaImageWidth = 180;
        int ruletaImageHeight = 180;

        Icon[] images = new Icon[3];
        images[0] = resizeRuletaImageIcon(new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Hombre Lobo Blanco.jpeg")), ruletaImageWidth, ruletaImageHeight);
        images[1] = resizeRuletaImageIcon(new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Vampiro Blanco.jpeg")), ruletaImageWidth, ruletaImageHeight);
        images[2] = resizeRuletaImageIcon(new ImageIcon(getClass().getResource("/proyecto2practica/igu/Imagenes/Muerte Blanco.jpeg")), ruletaImageWidth, ruletaImageHeight);

        imageLabel.setIcon(images[0]);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        panelRuleta.setLayout(new BorderLayout());
        panelRuleta.add(imageLabel, BorderLayout.CENTER);
        panelRuleta.add(buttonPanel, BorderLayout.SOUTH);

        Random random = new Random();
        Timer timer = new Timer(100, new ActionListener() {
            int currentIndex;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = random.nextInt(images.length);
                imageLabel.setIcon(images[currentIndex]);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                piezaPermitida = imageLabel.getIcon().toString().contains("Hombre Lobo") ? "Hombre Lobo"
                        : imageLabel.getIcon().toString().contains("Vampiro") ? "Vampiro"
                        : imageLabel.getIcon().toString().contains("Muerte") ? "Muerte" : null;
            }
        });
    }

    private void abandonarPartida() {
        String ganador = turno == 0 ? oponente.getNombreUsuario() : jugadorLogueado.getNombreUsuario();
        if (turno == 0) {
            oponente.sumarPuntos(3);
        } else {
            jugadorLogueado.sumarPuntos(3);
        }
        JOptionPane.showMessageDialog(null, "JUGADOR " + (turno == 0 ? jugadorLogueado.getNombreUsuario() : oponente.getNombreUsuario()) + " SE HA RETIRADO, FELICIDADES  JUGADOR " + ganador + ", HAS GANADO 3 PUNTOS");
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelRuleta);
        topFrame.dispose();
    }
}

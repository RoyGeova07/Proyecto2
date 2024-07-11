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

public class Ruleta extends JFrame {

    private JLabel imageLabel;
    private JButton startButton;
    private JButton stopButton;
    private Timer timer;
    private Random random;
    private int currentIndex;
    private Icon[] images;

    public Ruleta() {
        super("Ruleta de Imágenes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        // Cargar imágenes
        images = new Icon[3];
        for (int i = 0; i < images.length; i++) {
            images[0] = new ImageIcon(Ruleta.class.getResource("/proyecto2practica/igu/Imagenes/Hombre Lobo Blanco.jpeg"));
            images[1] = new ImageIcon(Ruleta.class.getResource("/proyecto2practica/igu/Imagenes/Vampiro Blanco.jpeg"));
            images[2] = new ImageIcon(Ruleta.class.getResource("/proyecto2practica/igu/Imagenes/Muerte Blanco.jpeg"));
        }
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setIcon(images[0]);

        startButton = new JButton("Iniciar");
        stopButton = new JButton("Detener");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(imageLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        random = new Random();
        timer = new Timer(100, new ActionListener() {
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
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Ruleta().setVisible(true);
            }
        });
    }
}

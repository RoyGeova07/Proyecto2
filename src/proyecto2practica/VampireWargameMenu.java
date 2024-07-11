/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2practica;

import javax.swing.*; // libreria de swing
import java.awt.*; // este proporciona las herramientas necesarias para crear interfaces graficas, dibujar graficos y manejar eventos en aplicaciones java
import java.awt.event.ActionEvent; // este se utliza para notificar cuando ocurre una accion signficativa, en un componente, como boton presionado o elemento de menu seleccionado 
import java.awt.event.ActionListener; // esta libreria permite recibir eventos de accion, este invoca cuando cuando ocurre una accion
import java.util.Date;
import java.awt.event.ComponentAdapter; // este es un componente abstracto, ayuda a poner la pantalla completa
import java.awt.event.ComponentEvent; // se utiliza para notificar cambios en la posición, tamaño o visibilidad de un componente en una interfaz gráfica de usuario

/**
 *
 * @author royum
 */
//----------------------------INICIO DE JUEGO CLASE-------------------------------------------------------------------------------------------------------------------------------------------------------------------
public class VampireWargameMenu {

    // este define la capacidad capacidad maxima de jugadores que pueden ser registrados en el sistema..
    // y tambien se usa como limitar el tamaño del array de jugadores y prevenir desbordamientos.
    private static final int MAX_JUGADORES = 100;

    // aqui se declara un array de Jugador con tamaño fijo, determinado por MAX_JUGADORES
    // en este array se almacena las instancias de Jugador creadas
    private static Jugador[] jugadores = new Jugador[MAX_JUGADORES];

    // esta funcion Lleva la cuenta del número actual de jugadores registrados en el sistema
    // tambien se incrementa cada vez que se crea un nuevo jugador y se decrementa cuando se elimina uno
    private static int numJugadores = 0;

    // Mantiene una referencia al jugador que ha iniciado sesión en el sistema
    // Es null cuando ningun jugador ha iniciado sesion
    private static Jugador jugadorLogueado = null;

    // final Indica que max_logs es una constante y su valor no puede cambiar después de la inicialización.
    private static final int Max_Logs = 100;

    //  String[] logs: Declaración de un arreglo de tipo String para almacenar los mensajes de log.
    // new String[MAX_LOGS]: Inicializa logs con un arreglo de tamaño MAX_LOGS, que es 100 en este caso.
    private static String[] logs = new String[Max_Logs];

    //numerosLogs es importante para mantener el seguimiento de cuantos mensajes de log se han almacenado en el arreglo logs.
    //Se incrementa cada vez que se añade un nuevo log y se utiliza para controlar el acceso y la gestión de los mensajes de log almacenados.
    private static int numerosLogs = 0;

    public static void main(String[] args) {
        mostrarMenuInicio();
    }
//-------------------------------MENU INICIOOOOOOOOOOOOOOOOOOOOOOOOOOOO-----------------------------------------------------------------------------------------------------------------------------------------------

    public static void mostrarMenuInicio() {
        JFrame frame = new JFrame("Vampire Wargame - Menú de Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Este funcion se asegura que cuando el usuario cierre la ventana, la aplicación se termine de manera adecuada y no quede corriendo en segundo plano.
        frame.setSize(1200, 800); // Ajustar el tamaño del frame a la imagen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // este sive para que salga la pantalla completa

        JLabel labelFondo = new JLabel();
        frame.setContentPane(labelFondo); // se utiliza para establecer el panel de contenido del JFrame. 

        // Crear panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(null); // Usamos null layout para posiciones absolutas
        panelBotones.setOpaque(false); // Hacemos la imagen visible

        // Crear y configurar los botones
        JButton botonInicioSesion = new JButton("INICIAR SESIÓN");
        botonInicioSesion.setBounds(500, 485, 430, 50); // establecer posición y tamanio del boton
        botonInicioSesion.setContentAreaFilled(false);
        botonInicioSesion.setOpaque(false);
        botonInicioSesion.setBorderPainted(false);
        botonInicioSesion.setFocusPainted(false); //  este Desactiva la pintura del borde de enfoque

        //codigo para hacer el color transparente
        Color originalColor = botonInicioSesion.getForeground();
        Color colorTransparente = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), 0);
        botonInicioSesion.setForeground(colorTransparente); // este es para hacer transparente el texto del boton

        botonInicioSesion.addActionListener(new ActionListener() { //  se utiliza para suscribir un objeto a recibir eventos de acción de un componente de la interfaz de usuario, como un botón 
            public void actionPerformed(ActionEvent e) {
                mostrarMenuInicioSesion(frame);
            }
        });

        JButton botonCrearJugador = new JButton("CREAR JUGADOR");
        botonCrearJugador.setBounds(500, 575, 430, 50);
        botonCrearJugador.setContentAreaFilled(false);
        botonCrearJugador.setOpaque(false);
        botonCrearJugador.setBorderPainted(false);
        botonCrearJugador.setFocusPainted(false); // este elimina el enfoque prederminado de la lineas del boton, para que se ponga transparente
        botonCrearJugador.setForeground(colorTransparente); // se aplica lo del codigo tranparente

        botonCrearJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuCrearJugador(frame);
            }
        });

        JButton botonSalir = new JButton("SALIR");
        botonSalir.setBounds(500, 660, 430, 50);
        botonSalir.setContentAreaFilled(false);
        botonSalir.setOpaque(false);
        botonSalir.setBorderPainted(false);
        botonSalir.setFocusPainted(false);
        botonSalir.setForeground(colorTransparente);
        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // estos sirven para añadir botones al panel de botones
        panelBotones.add(botonInicioSesion);
        panelBotones.add(botonCrearJugador);
        panelBotones.add(botonSalir);

        // este sirve para añadir el panel de botones al label de fondo
        labelFondo.add(panelBotones);

        // Listener para ajustar el tamaño de la imagen cuando el frame cambia de tamaño
        frame.addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent e) {
                ImageIcon originalImageIcon = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Menu Inicio imagen.jpeg"));
                Image originalImage = originalImageIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
                labelFondo.setIcon(new ImageIcon(scaledImage));
            }
        });

        // este añade los botones a su lugar enl menu inicio
        panelBotones.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.setVisible(true); // hace visible el label principal

    }
//-------------------------MENU INICIO SESIONNNNNNNNNNNNNNNNNNN-------------------------------------------------------------------------------------------------------

    public static void mostrarMenuInicioSesion(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        // Cargar la imagen de fondo
        ImageIcon imagenFondo = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Inicio.jpg"));
        Image originalImage = imagenFondo.getImage();
        // Redimensionar la imagen para que coincida con el tamaño del JFrame
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        // esta funcion establece la imagen escalada como icono del label
        JLabel FondoInicio = new JLabel(new ImageIcon(scaledImage));
        FondoInicio.setLayout(new GridBagLayout()); // este es un gestor de diseño, permite organizar, los componentes de la interfaz grafica.
        FondoInicio.setOpaque(true); // Asegurarse de que el JLabel sea opaco para que se vea la imagen

        JLabel etiquetaUsuario = new JLabel("Nombre de Usuario:");
        JTextField textoUsuario = new JTextField();
        etiquetaUsuario.setForeground(Color.WHITE); // este sirve para poner el texfield en color blanco

        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        JPasswordField textoContraseña = new JPasswordField();
        etiquetaContraseña.setForeground(Color.WHITE);

        JButton botonInicioSesion = new JButton("INICIAR SESIÓN");
        botonInicioSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = textoUsuario.getText();
                String contraseña = new String(textoContraseña.getPassword());
                boolean usuarioEncontrado = false;

                // Recorrer la lista de jugadores para verificar el nombre de usuario y la contraseña
                for (Jugador jugador : jugadores) {
                    // Asegúrate de que jugador no sea null antes de intentar acceder a sus métodos
                    if (jugador != null && jugador.getNombreUsuario() != null && jugador.getContraseña() != null
                            && jugador.getNombreUsuario().equals(nombreUsuario) && jugador.getContraseña().equals(contraseña)) {
                        // Si se encuentra una coincidencia se establece el jugador logueado
                        jugadorLogueado = jugador;
                        usuarioEncontrado = true;
                        JOptionPane.showMessageDialog(frame, "INICIO DE SESION HECHO CORRECTAMENTE");
                        mostrarMenuPrincipal(frame);
                        break;
                    }
                }

                // Si no se encontró al usuario, mostrar mensaje de error
                if (!usuarioEncontrado) {
                    JOptionPane.showMessageDialog(frame, "Nombre de usuario o contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    mostrarMenuInicioSesion(frame); // Mostrar el menu de inicio de sesion nuevamente
                }
            }
        });

        JButton botonVolver = new JButton("VOLVER");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuInicio();
            }
        });

        panel.setOpaque(false); // Hacer el panel transparente para que se vea el fondo
        panel.add(etiquetaUsuario);
        panel.add(textoUsuario);
        panel.add(etiquetaContraseña);
        panel.add(textoContraseña);
        panel.add(botonInicioSesion);
        panel.add(botonVolver);

        FondoInicio.add(panel);

        frame.setVisible(true);

        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        //  Añadir el panel al JFrame
        frame.add(FondoInicio, BorderLayout.CENTER); // Añadir el fondo con el panel incluido
        // Solicitar una nueva validación del diseño y repintado del panel
        frame.revalidate(); //  se utiliza para solicitar una nueva validación del diseño de un componente
        frame.repaint(); //se utiliza para solicitar la actualización de la visualización de un componente en la interfaz gráfica de usuario
    }
//---------------------------------------MENU CREAR JUGADORRRRRRRRRRRRRRR------------------------------------------------------------------------------------------

    public static void mostrarMenuCrearJugador(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 20, 20)); // Espacio reducido entre componentes

        // Fondo de pantalla
        ImageIcon FondoCrear = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/ImagenPlayer.jpeg"));
        Image originalImage = FondoCrear.getImage();
        // Redimensionar el tamaño de la imagen para que coincida con el JFrame
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        // Establecer la imagen escalada como icono del JLabel
        JLabel FondoPlayer = new JLabel(new ImageIcon(scaledImage));
        FondoPlayer.setLayout(new GridBagLayout());
        FondoPlayer.setOpaque(true);

        // Creación de componentes
        JLabel etiquetaUsuario = new JLabel("Nombre del jugador:");
        JTextField textoUsuario1 = new JTextField();
        etiquetaUsuario.setForeground(Color.WHITE);

        JLabel etiquetaContraseña1 = new JLabel("Contraseña (5 caracteres):");
        JPasswordField textoContraseña1 = new JPasswordField();
        etiquetaContraseña1.setForeground(Color.WHITE);

        JButton botonCrearJugador = new JButton("CREAR JUGADOR");
        botonCrearJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (numJugadores >= MAX_JUGADORES) {
                    JOptionPane.showMessageDialog(frame, "No se pueden crear más jugadores. Límite alcanzado.");
                    return;
                }
                String nombreUsuario = textoUsuario1.getText();
                String contraseña = new String(textoContraseña1.getPassword());
                if (contraseña.length() != 5) {
                    JOptionPane.showMessageDialog(frame, "La contraseña debe tener exactamente 5 caracteres.");
                    return;
                }
                for (int numjuga = 0; numjuga < numJugadores; numjuga++) {
                    if (jugadores[numjuga].getNombreUsuario().equals(nombreUsuario)) {
                        JOptionPane.showMessageDialog(frame, "El nombre de usuario ya existe.");
                        return;
                    }
                }
                Jugador nuevoJugador = new Jugador(nombreUsuario, contraseña, 0, new Date(), true);
                jugadores[numJugadores] = nuevoJugador;
                numJugadores++;
                jugadorLogueado = nuevoJugador;
                JOptionPane.showMessageDialog(frame, "JUGADOR #" + numJugadores + " CREADO CORRECTAMENTE");
                mostrarMenuInicio();
            }
        });

        // Configuración de estilo y diseño
        panel.setOpaque(false);
        panel.add(etiquetaUsuario);
        panel.add(textoUsuario1);
        panel.add(etiquetaContraseña1);
        panel.add(textoContraseña1);
        panel.add(botonCrearJugador);

        // Añadir componentes al fondo de pantalla
        FondoPlayer.add(panel);

        // Mostrar el marco
        frame.setVisible(true);

        // Establecer el tamaño del panel
        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // Añadir el fondo de pantalla al centro del JFrame
        frame.add(FondoPlayer, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
//----------------------------------MENU PRINCIPALLLLLLLLLLLLLLLLLLLLLL------------------------------------------------------------------------------------------

    public static void mostrarMenuPrincipal(JFrame frame) {
        JFrame frame2 = new JFrame("Vampire Wargame - Menú Principal");
        frame.getContentPane().removeAll();
        frame.setSize(1200, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();

        ImageIcon imagenPrincipal = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Principall.jpg"));
        Image originalImage = imagenPrincipal.getImage();
        // Redimensionar la imagen para que coincida con el tamaño del JFrame
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        // esta funcion establece la imagen escalada como icono del label
        JLabel FondoPrincipal = new JLabel(new ImageIcon(scaledImage));
        FondoPrincipal.setOpaque(true);

        // Crear botones
        JButton botonJugar = new JButton("JUGAR VAMPIRE WARGAME");
        botonJugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MostrarMenuJugar(frame);
            }
        });

        JButton botonCuenta = new JButton("MI CUENTA");
        botonCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuCuenta(frame);
            }
        });

        JButton botonReportes = new JButton("REPORTES");
        botonReportes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuReportes(frame);
            }
        });

        JButton botonCerrarSesion = new JButton("CERRAR SESIÓN");
        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jugadorLogueado = null;
                mostrarMenuInicio();
            }
        });

        JButton botonVolver = new JButton("VOLVER");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuInicio();
            }
        });

        panel.setOpaque(false);

        // Añadir botones al panel en orden centrado horizontalmente
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue()); // Espacio desde el centro hacia la izquierda

        panel.add(botonCuenta);
        panel.add(Box.createHorizontalStrut(20));

        panel.add(botonReportes);
        panel.add(Box.createHorizontalStrut(20));

        panel.add(botonJugar);
        panel.add(Box.createHorizontalStrut(20));

        panel.add(botonCerrarSesion);
        panel.add(Box.createHorizontalStrut(20));

        panel.add(botonVolver);

        panel.add(Box.createHorizontalGlue()); // Espacio desde el centro hacia la derecha

        FondoPrincipal.add(panel);

        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.add(FondoPrincipal, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }
// ------------------MENU PARA JUGARRRRRRRRRRR-----------------------------------------------------------------------------------------------------------------------

    public static void MostrarMenuJugar(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setSize(1200, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();

        ImageIcon imagenPrincipal = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Principall.jpg"));
        Image originalImage = imagenPrincipal.getImage();
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(scaledImage));
        labelImagen.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton botonNuevaPartida = new JButton("NUEVA PARTIDA");
        botonNuevaPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verificar si hay suficientes jugadores para iniciar una partida
                if (numJugadores <= 1) {
                    JOptionPane.showMessageDialog(frame, "No hay suficientes jugadores para iniciar una partida. Debes crear más jugadores.");
                    mostrarMenuCrearJugador(frame);
                    return; // Salir del método si no hay suficientes jugadores
                }

                // Creamos un array de nombres de jugadores que no incluyen al jugador logueado
                String[] nombresJugadores = new String[numJugadores - 1];
                int index = 0;
                for (int njugadores = 0; njugadores < numJugadores; njugadores++) {
                    if (!jugadores[njugadores].getNombreUsuario().equals(jugadorLogueado.getNombreUsuario())) {
                        nombresJugadores[index++] = jugadores[njugadores].getNombreUsuario();
                    }
                }

                // Mostrar un cuadro de diálogo para que el jugador seleccione un oponente
                String oponenteSeleccionado = (String) JOptionPane.showInputDialog(
                        frame,
                        "Selecciona a tu oponente:",
                        "Seleccionar Oponente",
                        JOptionPane.QUESTION_MESSAGE,
                        null, // icono por defecto
                        nombresJugadores, // lista de opciones
                        nombresJugadores[0] // opción por defecto
                );

                // Verificar si el jugador seleccionó un oponente
                if (oponenteSeleccionado == null) {
                    mostrarMenuPrincipal(frame);
                    return; // Salimos del metodo si no se selecciono un oponente
                }

                // Buscar el oponente seleccionado en la lista de jugadores
                Jugador oponente = null;
                for (int buscar = 0; buscar < numJugadores; buscar++) {
                    if (jugadores[buscar].getNombreUsuario().equals(oponenteSeleccionado)) {
                        oponente = jugadores[buscar];
                        break;
                    }
                }

                // Verificar si el oponente es válido
                if (oponente != null) {
                    // Iniciar la partida con el oponente seleccionado
                    iniciarPartida(frame, jugadorLogueado, oponente);
                } else {
                    JOptionPane.showMessageDialog(frame, "Oponente no válido.");
                    mostrarMenuPrincipal(frame);
                }
            }
        });
        labelImagen.add(botonNuevaPartida, gbc);

        gbc.gridy++;
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuPrincipal(frame);
            }
        });
        labelImagen.add(botonVolver, gbc);

        frame.add(labelImagen, BorderLayout.CENTER);
        frame.setVisible(true);
    }
// ---------------------LOGICA INICIAR PARTIDAAAAAAAAAAAAAAAAAAAAAAAAAAAA--------------------------------------------------------------------------------------------------------------------------------

    public static void iniciarPartida(JFrame frame, Jugador jugadorLogueado, Jugador oponente) {
        JFrame tableroFrame = new JFrame("Tablero de Vampire Wargame");
        tableroFrame.setSize(800, 800);
        tableroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear y configurar el tablero
        Tablero tablero = new Tablero(jugadorLogueado, oponente);
        tableroFrame.add(tablero.crearTableroPanel());  // Agregar el panel del tablero

        // Maximizar el JFrame y mostrarlo
        tableroFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tableroFrame.setLocationRelativeTo(null);
        tableroFrame.setVisible(true);
    }
// --------------------------MENU CUENTAAAAA------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void mostrarMenuCuenta(JFrame frame) {
        JFrame frame2 = new JFrame("Vampire Wargame - Menú Principal");
        frame.getContentPane().removeAll();
        frame.setSize(1200, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();

        ImageIcon imagenPrincipal = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Principall.jpg"));
        Image originalImage = imagenPrincipal.getImage();
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(scaledImage));
        labelImagen.setLayout(new GridBagLayout()); // con este se establece el layout label

        GridBagConstraints gbc = new GridBagConstraints(); // restricciones del GRIDBAG
        gbc.insets = new Insets(10, 10, 10, 10); // con este se añade los márgenes
        gbc.gridx = 0; // columna inicial
        gbc.gridy = 0; // fila inicial

        JButton botonInformacionJugador = new JButton("Ver Información del Jugador");
        botonInformacionJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarInformacionJugador(frame);
            }
        });
        labelImagen.add(botonInformacionJugador, gbc); // boton del label 

        gbc.gridy++; // este sirve para que se incremente la fila y no se peguen los botones, esta es otra forma de ordenar los botones
        JButton botonCambiarContraseña = new JButton("Cambiar Contraseña");
        botonCambiarContraseña.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarContraseña(frame);
            }
        });
        labelImagen.add(botonCambiarContraseña, gbc); // label 

        gbc.gridy++;
        JButton botonCerrarCuenta = new JButton("Cerrar Cuenta");
        botonCerrarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarCuenta(frame);
            }
        });
        labelImagen.add(botonCerrarCuenta, gbc);

        gbc.gridy++;
        JButton BotonVolver = new JButton("VOLVER MENU PRINCIPAL");
        BotonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuPrincipal(frame);
            }
        });
        labelImagen.add(BotonVolver, gbc);

        frame.add(labelImagen, BorderLayout.CENTER);
        frame.setVisible(true);
    }

// -----------------------INFORMACION DEL JUGADOR--------------------------------------------------------------------------------------------------------------------------------------------------
    private static void mostrarInformacionJugador(JFrame frame) {
        // informacion del jugador logueado 
        String mensaje = "Nombre de usuario: " + jugadorLogueado.getNombreUsuario() + "\n"
                + "Puntuación: " + jugadorLogueado.getPuntos() + "\n"
                + "Fecha de creación: " + jugadorLogueado.getFechaRegistro() + "\n"
                + "Estado: " + (jugadorLogueado.isActivo() ? "Activo" : "Inactivo");
        // mesanje de la informacion del jugador 
        JOptionPane.showMessageDialog(frame, mensaje, "Información del Jugador", JOptionPane.INFORMATION_MESSAGE);
    }
// --------------------CAMBIAR CONTRASEÑA-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void cambiarContraseña(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel etiquetaContraseñaActual = new JLabel("Contraseña Actual:");
        JPasswordField textoContraseñaActual = new JPasswordField();

        JLabel etiquetaNuevaContraseña = new JLabel("Nueva Contraseña:");
        JPasswordField textoNuevaContraseña = new JPasswordField();

        JLabel etiquetaConfirmarContraseña = new JLabel("Confirmar Nueva Contraseña:");
        JPasswordField textoConfirmarContraseña = new JPasswordField();

        panel.add(etiquetaContraseñaActual);
        panel.add(textoContraseñaActual);
        panel.add(etiquetaNuevaContraseña);
        panel.add(textoNuevaContraseña);
        panel.add(etiquetaConfirmarContraseña);
        panel.add(textoConfirmarContraseña);

        // con esto se muestra un cuadro de dialogo para el cambio de contrasena
        int resultado = JOptionPane.showConfirmDialog(frame, panel, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            // con este se obtiene la contrasenas ingresadas por el usuario
            String contraseñaActual = new String(textoContraseñaActual.getPassword());
            String nuevaContraseña = new String(textoNuevaContraseña.getPassword());
            String confirmarContraseña = new String(textoConfirmarContraseña.getPassword());

            if (!contraseñaActual.equals(jugadorLogueado.getContraseña())) {
                JOptionPane.showMessageDialog(frame, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //con este se verifica  si la contraseña actual ingresada coincide con la contraseña del jugador logueado
            if (!nuevaContraseña.equals(confirmarContraseña)) {
                JOptionPane.showMessageDialog(frame, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // se cambio la contrasena correctamente
            jugadorLogueado.setContraseña(nuevaContraseña);
            JOptionPane.showMessageDialog(frame, "Contraseña cambiada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
// -------------------CERRAR CUENTA---------------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void cerrarCuenta(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que quieres cerrar tu cuenta?", "Confirmar Cierre de Cuenta", JOptionPane.YES_NO_OPTION);
        // con este yes  
        if (result == JOptionPane.YES_OPTION) {
            // Buscar el índice del jugador logueado en el array de jugadores
            int indiceJugador = -1;
            for (int bucarindice = 0; bucarindice < numJugadores; bucarindice++) {
                if (jugadores[bucarindice].equals(jugadorLogueado)) {
                    indiceJugador = bucarindice;
                    break;
                }
            }

            if (indiceJugador != -1) {
                // aqui se mueve los elementos del array hacia la izquierda para cubrir el espacio del jugador eliminado
                for (int moverindice = indiceJugador; moverindice < numJugadores - 1; moverindice++) {
                    jugadores[moverindice] = jugadores[moverindice + 1];
                }
                // Aqui eliminamos el último elemento del array
                jugadores[numJugadores - 1] = null;
                numJugadores--;
                jugadorLogueado = null;
                JOptionPane.showMessageDialog(frame, "Cuenta cerrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarMenuInicio();
            } else {
                JOptionPane.showMessageDialog(frame, "Error al cerrar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
// ---------------------MENU DE REPORTESSSSSSSSSSSSSSSS-----------------------------------------------------------------------------------------------------------------------------

    public static void mostrarMenuReportes(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setSize(1200, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();

        ImageIcon imagenPrincipal = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Principall.jpg"));
        Image originalImage = imagenPrincipal.getImage();
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(scaledImage));
        labelImagen.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton botonRanking = new JButton("Ranking de Jugadores");
        botonRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RankinJugadores(frame);
            }
        });
        labelImagen.add(botonRanking, gbc);

        gbc.gridy++;
        JButton botonLogs = new JButton("Logs de mis ultimos juegos");
        botonLogs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogsUltimosJuegos(frame);
            }
        });
        labelImagen.add(botonLogs, gbc);

        gbc.gridy++;
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuPrincipal(frame);
            }
        });
        labelImagen.add(botonVolver, gbc);

        frame.add(labelImagen, BorderLayout.CENTER);
        frame.setVisible(true);
    }

//---------------------RANKING JUAGADORES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        public static void RankinJugadores(JFrame frame) {
    try {
        // Ordenar jugadores por puntos (burbuja)
        for (int i = 0; i < numJugadores - 1; i++) {
            for (int j = 0; j < numJugadores - i - 1; j++) {
                if (jugadores[j].getPuntos() < jugadores[j + 1].getPuntos()) {
                    Jugador temp = jugadores[j];
                    jugadores[j] = jugadores[j + 1];
                    jugadores[j + 1] = temp;
                }
            }
        }

        // Cargar y escalar imagen
        ImageIcon imagenPrincipal = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Principall.jpg"));
        Image originalImage = imagenPrincipal.getImage();
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(scaledImage));
        labelImagen.setLayout(new GridBagLayout());

        // Crear panel para mostrar el ranking
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Títulos de columnas
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Posición"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("Nombre de los jugadores"), gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Puntos"), gbc);

        // Añadir jugadores
        for (int i = 0; i < numJugadores; i++) {
            Jugador jugador = jugadores[i];
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(new JLabel(String.valueOf(i + 1)), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(jugador.getNombreUsuario()), gbc);
            gbc.gridx = 2;
            panel.add(new JLabel(String.valueOf(jugador.getPuntos())), gbc);
        }

        // Crear y agregar botón de volver
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuPrincipal(frame);
                frame.dispose(); // Cerrar la ventana actual para evitar conflictos
            }
        });

        gbc.gridx = 1;
        gbc.gridy = numJugadores + 1;
        gbc.gridwidth = 2;  // Ajustar el botón para que se extienda sobre dos columnas
        panel.add(volverButton, gbc);

        // Añadir el panel al label de la imagen
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        labelImagen.add(panel, gbcLabel);

        // Mostrar el label con la imagen y el panel en el frame
        frame.setContentPane(labelImagen);
        frame.revalidate();
        frame.repaint();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}   
//---------------LOGS ULTIMOS JUEGOS-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void LogsUltimosJuegos(JFrame frame) {
        // Ordenar jugadores por puntos (burbuja)
        for (int i = 0; i < numJugadores - 1; i++) {
            for (int j = 0; j < numJugadores - i - 1; j++) {
                if (jugadores[j].getPuntos() < jugadores[j + 1].getPuntos()) {
                    Jugador temp = jugadores[j];
                    jugadores[j] = jugadores[j + 1];
                    jugadores[j + 1] = temp;
                }
            }
        }

        // Cargar y escalar imagen
        ImageIcon imagenPrincipal = new ImageIcon(VampireWargameMenu.class.getResource("/proyecto2practica/igu/Imagenes/Imagen Principall.jpg"));
        Image originalImage = imagenPrincipal.getImage();
        Image scaledImage = originalImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(scaledImage));
        labelImagen.setLayout(new GridBagLayout());

        // Crear panel para mostrar el ranking
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Títulos de columnas
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Posición"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel("Nombre de los jugadores"), gbc);
        

       
        for (int i = 0; i < numJugadores; i++) {
            Jugador jugador = jugadores[i];
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(new JLabel(String.valueOf(i + 1)), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(jugador.getNombreUsuario()), gbc);
            i++;    
            gbc.gridx = 2;
            panel.add(new JLabel(jugador.getNombreUsuario()), gbc);
            
        }

        // Crear y agregar botón de volver
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMenuPrincipal(frame);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = numJugadores + 1;
        gbc.gridwidth = 2;  // Ajustar el botón para que se extienda sobre dos columnas
        panel.add(volverButton, gbc);

        // Añadir el panel al label de la imagen
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        labelImagen.add(panel, gbcLabel);

        // Mostrar el label con la imagen y el panel en el frame
        frame.setContentPane(labelImagen);
        frame.revalidate();
        frame.repaint();
    }
}    
//-----------FIN DE MENU INICIO Y MENU PRINCIPAL------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
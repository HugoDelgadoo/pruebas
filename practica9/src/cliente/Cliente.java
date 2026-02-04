package cliente;

import comun.EstadoJuego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

public class Cliente extends JFrame {
    // Configuración de red
    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;
    
    // Identificación del jugador
    private String miNombre;
    
    // Componentes de red
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    // Estado del juego (Datos compartidos)
    private EstadoJuego estadoJuego;
    
    // Componentes visuales
    private PanelJuego panelJuego;

    public Cliente(String nombre) {
        this.miNombre = nombre;
        this.estadoJuego = new EstadoJuego(); // Estado inicial vacío

        // 1. Configuración de la Ventana (JFrame)
        setTitle("Carrera - Jugador: " + nombre);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 2. Inicializar el panel de dibujo personalizado
        panelJuego = new PanelJuego();
        add(panelJuego);

        // 3. Eventos de Ratón (Lógica del Juego)
        panelJuego.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moverJugador(e.getX(), e.getY());
            }
        });

        // 4. Conectar al Servidor
        conectarServidor();
        
        setVisible(true);
    }

    private void conectarServidor() {
        try {
            socket = new Socket(HOST, PUERTO);
            // IMPORTANTE: El orden es clave. Primero Output, flush, y luego Input.
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            // Registrarse en el juego (enviamos nuestra primera posición)
            // Generamos una posición aleatoria o fija al inicio
            estadoJuego.actualizarPosicion(miNombre, 50, 50); 
            enviarEstado();

            // 5. Iniciar hilo que escucha al servidor
            // Usamos un hilo aparte para no congelar la ventana mientras esperamos datos
            new Thread(new EscuchaServidor()).start();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor.");
            System.exit(1);
        }
    }

    // Método con la lógica para actualizar posición y enviar
    private void moverJugador(int x, int y) {
        // Actualizamos SOLO nuestra posición en el objeto estado
        estadoJuego.actualizarPosicion(miNombre, x, y);
        
        // Enviamos el objeto actualizado al servidor
        enviarEstado();
    }

    private void enviarEstado() {
        try {
            out.writeObject(estadoJuego);
            out.flush();
            out.reset(); // Vital para que reenvíe el objeto actualizado y no una referencia vieja
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- CLASE INTERNA: Panel para dibujar (Java 2D) ---
    private class PanelJuego extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Limpia el fondo
            
            // Convertimos a Graphics2D para mejores gráficos
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar a todos los jugadores que estén en el estado
            Map<String, Point> posiciones = estadoJuego.getPosiciones();
            
            for (String jugador : posiciones.keySet()) {
                Point p = posiciones.get(jugador);
                
                // Si soy yo, me pinto azul, si es otro, rojo
                if (jugador.equals(miNombre)) {
                    g2d.setColor(Color.BLUE);
                    g2d.drawString("YO (" + jugador + ")", p.x, p.y - 10);
                } else {
                    g2d.setColor(Color.RED);
                    g2d.drawString(jugador, p.x, p.y - 10);
                }
                
                // Dibujamos el coche como un círculo
                g2d.fillOval(p.x, p.y, 30, 30);
            }
        }
    }

    // --- CLASE INTERNA: Hilo para recibir datos ---
    private class EscuchaServidor implements Runnable {
        @Override
        public void run() {
            try {
                Object obj;
                // Bucle infinito leyendo objetos que manda el servidor
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof EstadoJuego) {
                        // Recibimos el estado actualizado de la carrera
                        EstadoJuego nuevoEstado = (EstadoJuego) obj;
                        
                        // IMPORTANTE: Fusionamos o reemplazamos el estado
                        // Aquí simplemente reemplazamos para ver lo que ven todos
                        estadoJuego = nuevoEstado; 
                        
                        // Pedimos a la ventana que se vuelva a pintar con los nuevos datos
                        panelJuego.repaint();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Desconectado del servidor.");
            }
        }
    }

    // Main para probar el cliente individualmente
    public static void main(String[] args) {
        // Pedimos el nombre al iniciar para diferenciar clientes
        String nombre = JOptionPane.showInputDialog("Introduce tu nombre de piloto:");
        if (nombre != null && !nombre.isEmpty()) {
            SwingUtilities.invokeLater(() -> new Cliente(nombre));
        }
    }
}
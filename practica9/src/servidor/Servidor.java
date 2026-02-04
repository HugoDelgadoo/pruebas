package servidor;

import comun.EstadoJuego; // Asegúrate de importar tu clase común
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static final int PUERTO = 5000;
    // Set sincronizado para guardar los clientes
    private static Set<ClientHandler> clientes = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor de Carreras iniciado en el puerto " + PUERTO);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientes.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CLASE INTERNA PARA MANEJAR CADA CLIENTE
    static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in; // CAMBIO 1: Necesitamos ObjectInputStream

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // CAMBIO 2: Inicialización de flujos de objetos
                // IMPORTANTE: Crear el ObjectOutputStream PRIMERO y hacer flush()
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush(); 
                in = new ObjectInputStream(socket.getInputStream());

                // CAMBIO 3: Bucle de lectura de OBJETOS, no de texto
                Object objetoRecibido;
                while ((objetoRecibido = in.readObject()) != null) {
                    
                    if (objetoRecibido instanceof EstadoJuego) {
                        EstadoJuego estado = (EstadoJuego) objetoRecibido;
                        System.out.println("Recibido estado de: " + estado.getPosiciones()); // Debug
                        
                        // Reenviar a todos (Broadcast)
                        broadcastEstado(estado);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                // Es normal que salte error cuando un cliente se desconecta bruscamente
                System.out.println("Cliente desconectado."); 
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientes.remove(this);
            }
        }

        // CAMBIO 4: Método para enviar objetos (no Strings)
        public void enviarObjeto(Object obj) {
            try {
                out.writeObject(obj);
                out.flush(); // Importante para asegurarse de que se envía
                out.reset(); // Importante para evitar que se envíen referencias viejas en caché
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // CAMBIO 5: Broadcast de objetos
    public static void broadcastEstado(Object estado) {
        synchronized (clientes) { // Sincronizamos para evitar problemas concurrentes
            for (ClientHandler cliente : clientes) {
                cliente.enviarObjeto(estado);
            }
        }
    }
}
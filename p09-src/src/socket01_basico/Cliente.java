package socket01_basico;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5000;
        
        try {
            Socket socket = new Socket(HOST, PUERTO);
            
            // Flujos de entrada y salida
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Enviar mensaje al servidor
            salida.println("Hola servidor, soy el cliente.");
            
            // Leer respuesta del servidor
            String respuestaServidor = entrada.readLine();
            System.out.println("Respuesta del servidor: " + respuestaServidor);
            
            // Cerrar conexiones
            salida.close();
            entrada.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


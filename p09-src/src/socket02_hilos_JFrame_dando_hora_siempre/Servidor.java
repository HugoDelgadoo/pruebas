/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socket02_hilos_JFrame_dando_hora_siempre;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 5000;
        
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            
            while (true) {
                Socket socket = serverSocket.accept();
                // Cada vez que se conecta un cliente, el servidor crea hilo de la clase AntederCliente.
                new AntederCliente(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class AntederCliente extends Thread {
        
        private Socket clientSocket;
        
        public AntederCliente(Socket socket) {
            this.clientSocket = socket;
        }
        
        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if ("GET_TIME".equals(inputLine)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(new Date());
                        out.println(currentTime);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


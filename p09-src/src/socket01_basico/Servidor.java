package socket01_basico;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 5000;
        
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            
            System.out.println("Servidor esperando conexiones en el puerto " + PUERTO);
            // El servidor se queda escuchando y esperando conexiones entrantes. Cuando las acepta crea el socket.
            // En unallmaada telefonica, "serverSocket.accept()" espera que suene el telefono
            // y cuando la accpeta, se crea la llamada (el socket)
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado desde " + socket.getInetAddress());
            
            /*
            Flujos de entrada (con getInputStream) y salida (con getOutputStream)
            getInputStream() y getOutputStream() NO son flujos de texto ni de datos, son flujos binarios puros.
            
            Despúes se decide como tratarlos (Tipo / Clases / ¿Para qué?):
            Bytes	InputStream / OutputStream              Binarios
            Texto	BufferedReader / PrintWriter            Mensajes
            Datos	DataInputStream / DataOutputStream	Primitivos
            Objetos	ObjectInputStream / ObjectOutputStream	Objetos Java
            */
                    
            InputStream  in  = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(in));
            PrintWriter salida = new PrintWriter(out, true);
            
            /* "true" activa el auto-flush:
                Un flujo NO siempre se envía inmediatamente.Normalmente el texto se guarda en un buffer (memoria)
                y se envía cuando el buffer se llena, llamas a flush() y cierras el flujo (auto-flus).
            */
            
//            // Las 4 líneas de código anteriores en una línea
//            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            
            
            // Lee el mensaje del cliente. Se queda esperando hasta que recibe algo del cliente
            String mensajeCliente = entrada.readLine();
            System.out.println("Mensaje recibido del cliente: " + mensajeCliente);
            
            // Envia la respuesta al cliente
            salida.println("Hola cliente, tu mensaje fue recibido.");
            
            // Cerrar conexiones
            entrada.close();
            salida.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


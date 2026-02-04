package socket04_objetos_adivida_automatico;

import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameClient
{
    public static void main(String[] args) throws IOException 
    {
        try 
        {
            Socket gameSocket = new Socket("127.0.0.1", 4444); 		 //The IP address of the server and port number
            System.out.println("Connection established");	   		 //Let the user know connection has been made
            
            
            PrintWriter outSkString = new PrintWriter(gameSocket.getOutputStream(),true); //Output from the socket
            ObjectInputStream inSkObj = new ObjectInputStream(gameSocket.getInputStream());
            ObjectOutputStream outSkObj = new ObjectOutputStream(gameSocket.getOutputStream());
            
            int miNumero;
            
            //Para pedir el nombre por teclado.
            Scanner inTeclado = new Scanner(System.in); 				 //Sets the keyboard as input for user
            System.out.println("Welcome to the game server!");
            System.out.print("What is your name? ");				 //Prompt for username
            String nombre = inTeclado.nextLine();
            System.out.println("You entered string " + nombre);
            
            //Enviar el nombre (un String)
            outSkString.println(nombre);
            
                        
            while (true) {            
                EstadoJuego eJuego = (EstadoJuego) inSkObj.readObject();
                if (eJuego.esAcertado()){
                    System.out.println("GANASTE, el numero secreto es: "+eJuego.getNumeroSecreto()+" con "+eJuego.getIntentos()+" intentos.");
                    break;
                } else {
                    System.out.println("Adivina un numero entre "+eJuego.getNumeroMin()+" y "+eJuego.getNumeroMax());
                    miNumero = (int) (Math.random() * (eJuego.getNumeroMax()-eJuego.getNumeroMin())+eJuego.getNumeroMin());                     
                    eJuego.setNumeroIntentado(miNumero);
                    System.out.println(eJuego);
                    outSkObj.writeObject(eJuego);
                }
                
            }
            System.out.println("Thank you for playing.");
            //Close all connections and sockets to server
            outSkObj.close();
            inSkObj.close();
            outSkString.close();
            gameSocket.close();
        } 
        catch (ConnectException e) //Will throw error if there inSkObj no connection to the server
        {
            System.err.println("Could not connect. Ensure server is running.");
            System.exit(1);
        } 
        catch (IOException e) 	   //Will throw error if there inSkObj no input
        {
            System.err.println("Couldn't get I/O for connection");
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
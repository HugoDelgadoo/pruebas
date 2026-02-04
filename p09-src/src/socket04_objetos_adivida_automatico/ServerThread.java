      package socket04_objetos_adivida_automatico;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread
{
	private Socket sckt = null;				//Initialise socket as null
	
	public ServerThread(Socket socket)
	{
		this.sckt = socket;     //es el socket.accept();
	}
	
        @Override
	public void run()
	{
		System.err.println("Client has connected successfully");	//Print to server screen
		
		String inputLine;			//Para la entrada como String
                int numMax=10000;
		int numIntentado;                       
			
		int numSecreto =(int) (Math.random() * 100);            //Crea un número Secreto entre 0 and 100
                System.err.println("Mi numeor es "+numSecreto);
                EstadoJuego estJuego = new EstadoJuego("Ana",1,numMax,numSecreto,1);       //Primer Objeto para envar al socket cleinte
                System.out.println(estJuego);

		try {

                    //Preparando la E/S de este socket (servidor). Lo primero.
                    ObjectOutputStream outS = new ObjectOutputStream(sckt.getOutputStream());   //Para enviar objetos del socket ciente
                    ObjectInputStream inS = new ObjectInputStream(sckt.getInputStream());       //Para recivir objetos del scoket cliente
                    Scanner in = new Scanner(sckt.getInputStream());                            //Para recivir String del socket cliente
                    System.err.println("Server OK");
                    
                    inputLine = in.nextLine();                                  //Esperando datos (String) desde el socket clente
                    System.err.println("El nombre es "+inputLine);
                    estJuego.setNombre(inputLine);
                    boolean jugar = true;

                    while (jugar) {
                        outS.writeObject(estJuego);
                        estJuego = (EstadoJuego) inS.readObject();
                        numIntentado = estJuego.getNumeroIntentado();
                        System.err.println(estJuego);

                        if (numIntentado == numSecreto) {
                            estJuego.setAcertado(true);
                            outS.writeObject(estJuego);     //Hay que envarlo. El cliente está esperando.
                            jugar=false;
                            //Or break;	//Break out of the While loop					
                        }
                        else 
                            if (numIntentado > numSecreto) {
                                estJuego.setNumeroMax(numIntentado);
                            }
                            else 
                                if (numIntentado < numSecreto) {
                                    estJuego.setNumeroMin(numIntentado);                                    
                                }
                        estJuego.setIntentos(estJuego.getIntentos()+1);
                        Thread.sleep(2000);
                    }
                    
                    //Close all connections
                    in.close();
                    inS.close();
                    outS.close();
                    this.sckt.close();
		}
		catch (Exception e)				//If the client has disconnected from the server
		{
			System.err.println("Client connection termintated");	//Print error message to server screen
		}
	}
}
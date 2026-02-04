package socket04_objetos_adivida_automatico;

import java.net.ServerSocket;

public class Server
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket sSocket = new ServerSocket(4444);		//Create new server socket, port 4444
		System.out.println("Welcome to the server!");
		System.out.println("");
		boolean allOK = true;								//Set as true on start, go to while loop
		
		while (allOK)
		{
			new ServerThread(sSocket.accept()).start();		//Create a new instance of ServerThread with each new connection
		}
		
		sSocket.close();
		System.out.println("All done");
	}
}
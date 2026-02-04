package socket05_adivina_automativo_multithread_Client;

public class Clients {
    	public static void main(String[] args) throws Exception
	{
		ClientThread ana = new ClientThread("Ana");
                ClientThread jaime = new ClientThread("Jaime");
                ClientThread maria = new ClientThread("Maria");
                ClientThread luis = new ClientThread("Luis");
                ClientThread manuel = new ClientThread("Manuel");
                ClientThread sara = new ClientThread("Sara");
                ClientThread paco = new ClientThread("Paco");
                ClientThread lucia = new ClientThread("Lucia");
                
                ana.start();
                jaime.start();
                maria.start();
                luis.start();
                manuel.start();
                sara.start();
                paco.start();
                lucia.start();
                
		System.out.println("Welcome to the server!");

	}
    
}

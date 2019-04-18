package last;
import java.io.*;
import java.net.*;
import java.lang.*;
public class Server {
	public static void main(String[] args) throws IOException {
		final int port = 444;
		System.out.println("Server waiting for connection on port "+port);
		ServerSocket ss = new ServerSocket(port);
		Socket clientSocket = ss.accept();
		System.out.println("Recieved connection from "+clientSocket.getInetAddress()+" on port "+clientSocket.getPort());
		
		RecieveFromClientThread recieve = new RecieveFromClientThread(clientSocket);
		Thread thread = new Thread(recieve);
		thread.start();
		SendToClientThread send = new SendToClientThread(clientSocket);
		Thread thread2 = new Thread(send);
		thread2.start();
	}}
class RecieveFromClientThread implements Runnable
{
	Socket clientSocket=null;
	BufferedReader brBufferedReader = null;
	
	public RecieveFromClientThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}//end constructor
	public void run() {
		try{
		brBufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));		
		
		String messageString;
		while(true){
		while((messageString = brBufferedReader.readLine())!= null){
			if(messageString.equals("EXIT"))
			{
				break;
			}
			System.out.println("From Client: " + messageString);
			System.out.println("Please enter something to send back to client..");
		}
		this.clientSocket.close();
		System.exit(0);
	}
		
	}
	catch(Exception ex){System.out.println(ex.getMessage());}
	}
}
class SendToClientThread implements Runnable
{
	PrintWriter pwPrintWriter;
	Socket clientSock = null;
	
	public SendToClientThread(Socket clientSock)
	{
		this.clientSock = clientSock;
	}
	public void run() {
		try{
		pwPrintWriter =new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));
		
		while(true)
		{
			String msgToClientString = null;
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			msgToClientString = input.readLine();
			
			pwPrintWriter.println(msgToClientString);
			pwPrintWriter.flush();
			System.out.println("Please enter something to send back to client..");
		}
		}
		catch(Exception ex){System.out.println(ex.getMessage());}	
	}
}

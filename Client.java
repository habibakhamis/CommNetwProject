package last;

import java.io.*;
import java.net.*;
public class Client {
	public static void main(String[] args)
	{
		BufferedReader brinput;
		while(true){
			System.out.println("Type 'CONNECT' to connect to server");
			brinput = new BufferedReader(new InputStreamReader(System.in));
			String msgtoServerString=null;
			try {
				msgtoServerString = brinput.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(msgtoServerString.equals("CONNECT"))
			break;
			}
		
		try {
			Socket sock = new Socket("127.0.0.1",444);
			SendThread sendThread = new SendThread(sock);
			Thread thread = new Thread(sendThread);thread.start();
			RecieveThread recieveThread = new RecieveThread(sock);
			Thread thread2 =new Thread(recieveThread);thread2.start();
		} catch (Exception e) {System.out.println(e.getMessage());} 
	}
}
class RecieveThread implements Runnable
{
	Socket sock=null;
	BufferedReader recieve=null;
	
	public RecieveThread(Socket sock) {
		this.sock = sock;
	}
	public void run() {
		try{
		recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		String msgRecieved = null;
		while((msgRecieved = recieve.readLine())!= null)
		{
			System.out.println("From Server: " + msgRecieved);
			System.out.println("Please enter something to send to server..");
		}
		}catch(Exception e){System.out.println(e.getMessage());}
	}
}
class SendThread implements Runnable
{
	Socket sock=null;
	PrintWriter print=null;
	BufferedReader brinput=null;
	
	public SendThread(Socket sock)
	{
		this.sock = sock;
	}
	public void run(){
		try{
		if(sock.isConnected())
		{
			System.out.println("Client connected to "+sock.getInetAddress() + " on port "+sock.getPort());
			this.print = new PrintWriter(sock.getOutputStream(), true);	
		while(true){
			System.out.println("Type your message to send to server..type 'bye' to exit");
			brinput = new BufferedReader(new InputStreamReader(System.in));
			String msgtoServerString=null;
			msgtoServerString = brinput.readLine();
			this.print.println(msgtoServerString);
			this.print.flush();
		
			if(msgtoServerString.equals("bye"))
			break;
			}
		sock.close();}}catch(Exception e){System.out.println(e.getMessage());}
	}
}
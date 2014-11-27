import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPclient {
	public final static int
		defaultCreatePort = 777,
		defaultSearchPort = 778; //Dados partilhados com o servidor
	public final static String host = "localhost";
	public static String message = "A minha nova sala!";
	private static Socket client;
	
	 public static void main(String argv[]){
		 createRoom();
	 }
	 
	 // Retorna -1 se houver problemas com a ligação, 0 se com o servidor, e 1 se correr tudo bem
	 public static int searchRooms(){
		 ArrayList<String> rooms;
		 int data = -2;
		 
		 try {
			 client = new Socket(host, defaultSearchPort);
			 System.out.print("Conected. Awaiting for room names...\n\n");
			 InputStream in = client.getInputStream();
			 OutputStream out = client.getOutputStream();
			 
			 rooms = new ArrayList<String>();
			 String roomName = "";
			 
			 do{
				 data = in.read();
				 if (data == '\n') {
					 if (roomName == "")
						 break;
					 else{
						 rooms.add(roomName);
						 roomName = "";
					 }
				 }
				 else if (data != -1)
					 roomName += (char) data;
			 } while (data != -1);
			 
			 for(int i = 0; i<rooms.size(); i++){
				 System.out.print("\n\nRoom " +i+ "\nName: " +rooms.get(i));
			 }
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 
		 if (data == -1){
			 System.out.print("Problem with server. Please try again in a few minutes..\n");
			 return 0;
		 }
		 else if (data == -2){
			 System.out.print("Not connecting with server. Pleas check your internet connection..");
			 return -1;
		 }
		 else
			 return 1;
	 }
	 
	 public static void createRoom(){
		 try {
															System.out.print("trying to connect to " + host + " with port " + defaultCreatePort + "...\n");
			client = new Socket(host, defaultCreatePort);
															System.out.print("_client connected_\n");
			OutputStream out = client.getOutputStream();
															System.out.print("_output stream created_\n");
			
			// Send data:
			//java.io.DataOutputStream dataOut = new java.io.DataOutputStream(out);
			//dataOut.writeInt(12);
			
			// Hopefully, recieves the http data from google.pt
		 	InputStream in = client.getInputStream();
		 													System.out.print("_input stream created_\n");
		  	String mensagem = "";
		  	int data;
		  													System.out.print("_start reading: ");
		 	do{
		 		data = in.read();
	 														System.out.print((char)data);
		 		if (data != -1)
		 			mensagem += (char) data;
		 	} while (data != -1);
		 	
		 	client.close();
		 													System.out.print("\n\nReaded:\n" + mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}
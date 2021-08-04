package Test.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {

		//socket
		int port = 1989;
		ServerSocket serverSocket = new ServerSocket(port);
		System.err.println("Serveur lancé sur le port : " + port);

		// repeatedly wait for connections, and process
		while (true) {

			// get stuck on waiting for a customer request 
	        Socket clientSocket = serverSocket.accept();
	        System.err.println("New Connection");

	        //open a conversation flow 

	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

	     // each time a piece of data is read on the network, it is sent to
	     // the write stream.
	     // the data read is therefore returned to exactly the same client. 
	        String s;
	        while ((s = in.readLine()) != null) {
	            System.out.println(s);
	            if (s.isEmpty()) {
	                break;
	            }
			}

	     // we close the flows. 
			System.err.println("Connection terminated");
			out.close();
			in.close();
			clientSocket.close();
		}
	}
}

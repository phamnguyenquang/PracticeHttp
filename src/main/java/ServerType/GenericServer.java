package ServerType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GenericServer {
	private int port;
	private ServerSocket serverSocket;

	public GenericServer(int listenPort) {
		port = listenPort;
		initialize();
		try {
			doListen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("listening on port : " + port);
	}

	private void doListen() throws IOException {
		while (true) {

			// get stuck on waiting for a customer request
			Socket clientSocket = serverSocket.accept();
			System.err.println("New Connection");

			// open a conversation flow

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

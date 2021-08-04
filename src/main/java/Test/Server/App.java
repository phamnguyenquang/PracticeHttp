package Test.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ServerType.GenericServer;
import ServerType.HttpReqServer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {

		new HttpReqServer(8080);
//		new GenericServer(8080);
	}
}

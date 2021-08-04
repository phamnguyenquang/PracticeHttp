package ServerType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import Handler.Handler;

public class HttpReqServer {
	private int port;
	private HttpServer server;
	private static int BACKLOG = 1;

	public HttpReqServer(int listenPort) {
		port = listenPort;
		InetSocketAddress inet = new InetSocketAddress(port);
		try {
			server = HttpServer.create(inet, BACKLOG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startServer();
	}

	private void startServer() {
		System.out.println("server listening on port: " + port);
//		HttpContext context = server.createContext("/");
		HttpContext context = server.createContext("/json");
		context.setHandler(Handler::HandleJSONRequest);

		server.start();
	}

}

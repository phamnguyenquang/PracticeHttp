package Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class Handler {

	private static final String HEADER_ALLOW = "Allow";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private static final int STATUS_OK = 200;
	private static final int STATUS_METHOD_NOT_ALLOWED = 405;

	private static final int NO_RESPONSE_LENGTH = -1;

	private static final String METHOD_GET = "GET";
	private static final String METHOD_OPTIONS = "OPTIONS";
	private static final String ALLOWED_METHODS = METHOD_GET + "," + METHOD_OPTIONS;

	public static void HandleRequest(HttpExchange exchange) {

		try {
			String respone = "test";
			exchange.sendResponseHeaders(200, respone.getBytes().length);
			OutputStream OS = exchange.getResponseBody();
			OS.write(respone.getBytes());
			OS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void HandleJSONRequest(HttpExchange exchange) throws IOException {

		try {
			final Headers headers = exchange.getResponseHeaders();
			final String requestMethod = exchange.getRequestMethod().toUpperCase();
			System.out.println(exchange.getRequestMethod().toString());
			switch (requestMethod) {
			case METHOD_GET:
				System.out.println("new connection, get: ");
				headers.set(HEADER_ALLOW, ALLOWED_METHODS);
				exchange.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
				break;
			case METHOD_OPTIONS:
				System.out.println("new connection, opption: ");
				headers.set(HEADER_ALLOW, ALLOWED_METHODS);
				exchange.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
				break;
			default:
				System.out.println("new connection, defaullt: ");
				InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
				BufferedReader br = new BufferedReader(reader);
				String JsonLine = br.readLine();
				System.out.println(JsonLine);
				// do something with the request parameters
				JsonProcess(JsonLine);
				// send a response
				final String responseBody = "['hello world!']";
				headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
				final byte[] rawResponseBody = responseBody.getBytes(CHARSET);
				exchange.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
				exchange.getResponseBody().write(rawResponseBody);
				break;
			}
		} finally {
			exchange.close();
		}

	}

	public static void JsonProcess(String json) {
		System.out.println("processing Json");
		JsonElement tree = new JsonParser().parse(json);
		JsonObject object = tree.getAsJsonObject();
		System.out.println("TestJson " + object.get("customerID"));
	}
}

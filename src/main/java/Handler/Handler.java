package Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
				final Map<String, List<String>> requestParameters = getRequestParameters(exchange.getRequestURI());
				// do something with the request parameters
				final String responseBody = "['hello world!']";
				headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
				final byte[] rawResponseBody = responseBody.getBytes(CHARSET);
				exchange.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
				exchange.getResponseBody().write(rawResponseBody);
				break;
			case METHOD_OPTIONS:
				System.out.println("new connection, opption: ");
				headers.set(HEADER_ALLOW, ALLOWED_METHODS);
				exchange.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
				break;
			default:
				System.out.println("new connection, defaullt: ");
				headers.set(HEADER_ALLOW, ALLOWED_METHODS);
				exchange.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
				break;
			}
		} finally {
			exchange.close();
		}

	}

	private static Map<String, List<String>> getRequestParameters(final URI requestUri) {
		final Map<String, List<String>> requestParameters = new LinkedHashMap<>();
		final String requestQuery = requestUri.getRawQuery();
		System.out.println(requestQuery);
		if (requestQuery != null) {
			final String[] rawRequestParameters = requestQuery.split("[&;]", -1);
			for (final String rawRequestParameter : rawRequestParameters) {
				final String[] requestParameter = rawRequestParameter.split("=", 2);
				final String requestParameterName = decodeUrlComponent(requestParameter[0]);
				requestParameters.putIfAbsent(requestParameterName, new ArrayList<>());
				final String requestParameterValue = requestParameter.length > 1
						? decodeUrlComponent(requestParameter[1])
						: null;
				requestParameters.get(requestParameterName).add(requestParameterValue);
			}
		}
		return requestParameters;
	}

	private static String decodeUrlComponent(final String urlComponent) {
		try {
			return URLDecoder.decode(urlComponent, CHARSET.name());
		} catch (final UnsupportedEncodingException ex) {
			throw new InternalError(ex);
		}
	}
}

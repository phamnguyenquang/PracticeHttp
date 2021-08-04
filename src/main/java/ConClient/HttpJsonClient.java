package ConClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class HttpJsonClient {

	private String sampleJson = "{\"customerID\":1,\"tagID\":2,\"userID\":\"aaaaaaaa-bbbb-cccc-1111-222222222222\",\"remoteIP\":\"123.234.56.78\",\"timestamp\":1500000000}";

	public HttpJsonClient() {
		try {
			System.out.println(sampleJson);
			doWork();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doWork() throws IOException {
		int timeout = 5;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		try {
			HttpPost request = new HttpPost("http://localhost:8080");

			StringEntity params = new StringEntity(sampleJson);
			params.setContentType("application/json;charset=UTF-8");
			request.setEntity(params);
			params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

			request.addHeader("content-type", "application/json");

			System.out.println("request: " + request.toString());

			HttpResponse response = httpClient.execute(request);
			// handle response
			System.out.println("response receivved");
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;

			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
	}

}

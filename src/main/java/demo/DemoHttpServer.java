package demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Instant;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class DemoHttpServer {

	final static int HTTP_SERVER_PORT = 8080;
	final static int HTTP_OK = 200;

	public static void main(String[] args) {
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(HTTP_SERVER_PORT), 0);
			HttpContext context = server.createContext("/gettime");
			context.setHandler(DemoHttpServer::handleTimeRequest);
			server.start();
			System.out.println("Started HTTP server on port " + HTTP_SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Failed to start HTTP server on port " + HTTP_SERVER_PORT);
			System.exit(1);
		}
	}

	private static void handleTimeRequest(HttpExchange exchange) throws IOException {
		String message = "<h1>The time is now " + Instant.now() + "</h1>";
		exchange.sendResponseHeaders(HTTP_OK, message.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(message.getBytes());
		os.close();
	}
}

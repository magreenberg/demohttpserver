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

	final static int DEFAULT_HTTP_SERVER_PORT = 8080;
	final static int HTTP_OK = 200;

	public static void main(String[] args) {
		HttpServer server;
		int port = DEFAULT_HTTP_SERVER_PORT;

		String sport = System.getenv("HTTP_SERVER_PORT");
		if (sport != null && sport.length() > 0) {
			try {
				port = Integer.parseInt(sport);
			} catch (Exception e) {
				System.out.println("Invalid port specified: " + sport);
				System.exit(1);
			}
		}

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			HttpContext rootContext = server.createContext("/");
			rootContext.setHandler(DemoHttpServer::handleRootRequest);
			HttpContext context = server.createContext("/gettime");
			context.setHandler(DemoHttpServer::handleTimeRequest);
			server.start();
			System.out.println("Started HTTP server on port " + port);
		} catch (IOException e) {
			System.out.println("Failed to start HTTP server on port " + port);
			System.exit(1);
		}
	}
	
	private static void handleRootRequest(HttpExchange exchange) throws IOException {
		String message = "<h1>Hello! Append /gettime for a unique page</h1>";
		exchange.sendResponseHeaders(HTTP_OK, message.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(message.getBytes());
		os.close();
	}

	private static void handleTimeRequest(HttpExchange exchange) throws IOException {
		String message = "<h1>The time is now " + Instant.now() + "</h1>";
		exchange.sendResponseHeaders(HTTP_OK, message.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(message.getBytes());
		os.close();
	}
}

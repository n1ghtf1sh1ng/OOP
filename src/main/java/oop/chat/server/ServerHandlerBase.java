package oop.chat.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import oop.chat.core.JsonObject;

public class ServerHandlerBase implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException { }

	public JsonObject getInputJson(HttpExchange exchange) throws IOException {
		String inData = StandardCharsets.UTF_8.decode(
				ByteBuffer.wrap(exchange.getRequestBody().readAllBytes()))
				.toString();

		return JsonObject.fromSource(inData);
	}


	public void sendOutputJson(HttpExchange exchange, JsonObject json) throws IOException {
		String outData = json.toSource();
		byte[] outBytes = outData.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().put("Content-Type",
				Collections.singletonList("application/json"));
		exchange.sendResponseHeaders(200, outBytes.length);
		try (OutputStream out = exchange.getResponseBody()) {
			out.write(outBytes);
		}
		exchange.close();
	}
}

package oop.chat.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import oop.chat.core.ChatUser;
import oop.chat.core.JsonObject;

public class ServerHandlerJoin implements HttpHandler {
	ChatServer server;

	public ServerHandlerJoin(ChatServer server) {
		this.server = server;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getHttpContext().getPath();
		if ("/join".equals(path)) {
			JsonObject input = getInputJson(exchange);
			InetSocketAddress senderAddress = exchange.getRemoteAddress();
			InetAddress address = senderAddress.getAddress();
			String name = input.getString("name");
			ChatUser foundUser = server.findUser(input);
			ChatUser createdUser = server.createUser(foundUser, name, address);
			JsonObject out;
			out = new JsonObject();
			if (createdUser == null) {
				out.putBoolean("success", false);
			} else {
				out.putBoolean("success", true);
				out.putString("id", createdUser.getId().toString());
				out.putLong("clock", createdUser.getLastAccessClock());
			}
			sendOutputJson(exchange, out);
		}
	}

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

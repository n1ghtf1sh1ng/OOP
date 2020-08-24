package oop.chat.server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import oop.chat.core.ChatUser;
import oop.chat.core.JsonObject;

public class ServerHandlerLeave extends ServerHandlerBase {
	ChatServer server;

	public ServerHandlerLeave(ChatServer server) {
		this.server = server;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getHttpContext().getPath();
		if ("/leave".equals(path)) {
			JsonObject input = getInputJson(exchange);
			String id = input.getString("id");
			ChatUser foundUser = server.findUserById(id);
			boolean success = server.deleteUser(foundUser, id);
			JsonObject out = new JsonObject();
			out.putBoolean("success", success);
			sendOutputJson(exchange, out);
		}
	}
}

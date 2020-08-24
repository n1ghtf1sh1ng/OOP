package oop.chat.server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import oop.chat.core.ChatMessage;
import oop.chat.core.ChatUser;
import oop.chat.core.JsonObject;

public class ServerHandlerPost extends ServerHandlerBase {
	ChatServer server;
	public ServerHandlerPost(ChatServer server) {
		this.server = server;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getHttpContext().getPath();
		if ("/post".equals(path)) {
			JsonObject input = getInputJson(exchange);
			String id = input.getString("id");
			String messageStr = input.getString("message");
			ChatUser foundUser = server.findUserById(id);
			JsonObject out;
			if (foundUser != null) {
				ChatMessage message = server.createMessage(foundUser, messageStr);
				out = new JsonObject();
				out.putBoolean("success", true);
				out.putObject("message", message.toJson());
			} else {
				out = new JsonObject();
				out.putBoolean("success", false);
			}
			sendOutputJson(exchange, out);
		}
	}
}

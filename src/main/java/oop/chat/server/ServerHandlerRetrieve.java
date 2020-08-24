package oop.chat.server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.List;
import oop.chat.core.ChatMessage;
import oop.chat.core.ChatUser;
import oop.chat.core.JsonList;
import oop.chat.core.JsonObject;

public class ServerHandlerRetrieve extends ServerHandlerBase {
	ChatServer server;

	public ServerHandlerRetrieve(ChatServer server) {
		this.server = server;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getHttpContext().getPath();
		if ("/retrieve".equals(path)) {
			JsonObject input = getInputJson(exchange);
			String id = input.getString("id");
			long clock = input.getLong("clock");
			ChatUser foundUser = server.findUserById(id);
			JsonObject out;
			if (foundUser != null) {
				long currentClock = server.getServerClock();
				List<ChatMessage> messages = server.fetchMessages();
				foundUser.setLastAccessClock(currentClock);

				out = new JsonObject();
				out.putBoolean("success", true);
				out.putLong("clock", currentClock);
				JsonList messagesJson = out.putList("messages");

				for (ChatMessage message : messages) {
					messagesJson.addObject(message.toJson());
				}
			} else {
				out = new JsonObject();
				out.putBoolean("success", false);
				out.putLong("clock", -1L);
				out.putList("messages"); //no entries
			}
			sendOutputJson(exchange, out);
		}
	}
}

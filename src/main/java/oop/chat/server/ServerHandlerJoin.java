package oop.chat.server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import oop.chat.core.ChatUser;
import oop.chat.core.JsonObject;

public class ServerHandlerJoin extends ServerHandlerBase {
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
}

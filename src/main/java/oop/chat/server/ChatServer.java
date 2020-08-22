package oop.chat.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import oop.chat.core.ChatMessage;
import oop.chat.core.ChatUser;
import oop.chat.core.JsonList;
import oop.chat.core.JsonObject;
import oop.nonmod.chat.NonModChatServerApp;

public class ChatServer {
	public NonModChatServerApp app;
	public HttpServer server;
	public HttpContext joinContext;
	public HttpContext leaveContext;
	public HttpContext retrieveContext;
	public HttpContext postContext;

	public List<ChatUser> users = new ArrayList<>();
	public List<ChatMessage> timeline = new ArrayList<>();
	public long serverClock;


	public ChatServer(NonModChatServerApp app) {
		this.app = app;
	}

	public void startServer(String host, int port) {
		try {
			if (server != null) {
				stop();
			}
			server = HttpServer.create(new InetSocketAddress(host, port), 0);

			joinContext = server.createContext("/join", (HttpHandler) this);
			leaveContext = server.createContext("/leave", (HttpHandler) this);
			retrieveContext = server.createContext("/retrieve", (HttpHandler) this);
			postContext = server.createContext("/post", (HttpHandler) this);

			server.setExecutor(null);

			server.start();
			log("server started: host=%s, port=%d", host, port);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public synchronized boolean stop() {
		if (server != null) {
			log("stop server");
			server.stop(0);
			server = null;
			users.clear();
			timeline.clear();
			serverClock = 0;
			log("server stopped");

			// app.updateUsers(users);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
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

	/**
	 * <h3>for /join</h3>
	 * input:
	 * <pre>
	 *     {
	 *         "name": "userName"
	 *     }
	 * </pre>
	 *
	 * output:
	 * <pre>
	 *     {
	 *         "id": "userId",
	 *         "success": true,
	 *         "clock": serverClock
	 *     }
	 * </pre>
	 *
	 * <hr>
	 *
	 * <h3>for /leave</h3>
	 * input:
	 * <pre>
	 *     {
	 *          "id": "userId"
	 *     }
	 * </pre>
	 *
	 * output:
	 * <pre>
	 *     {
	 *         "success": true
	 *     }
	 * </pre>
	 *
	 * <hr>
	 *  <h3>for /retrieve</h3>
	 *  input:
	 * <pre>
	 *     {
	 *         "id": "userId", //must be logged-in
	 *         "clock": serverClockLong //requested clock on the server
	 *     }
	 * </pre>
	 *
	 *  output:
	 * <pre>
	 *     {
	 *         "success": true,
	 *         "clock": serverClockLong, //current clock on the server
	 *         "messages": [
	 *              {
	 *                  "name": "messageSender,
	 *                  "message": "messageStr",
	 *                  "time": offsetDateTime,
	 *                  "clock": messageServerClockLong
	 *              },
	 *              //list of messages. sorted by clock
	 *         ]
	 *     }
	 * </pre>
	 *
	 *  <hr>
	 *      <h3>for /post</h3>
	 * <pre>
	 *     {
	 *         "id": "userId", //must be logged-in
	 *         "message": "messageStr",
	 *     }
	 * </pre>
	 *
	 * <pre>
	 *     {
	 *         "success": true,
	 *         "message" : {
	 *             "name": "messageSender,
	 *             "message": "messageStr",
	 *             "time": offsetDateTime,
	 *             "clock": messageServerClockLong
	 *         }
	 *     }
	 * </pre>
	 *
	 */
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getHttpContext().getPath();
		switch (path) {
			case "/join": {
				JsonObject input = getInputJson(exchange);
				InetSocketAddress senderAddress = exchange.getRemoteAddress();
				InetAddress address = senderAddress.getAddress();
				String name = input.getString("name");
				ChatUser foundUser = null;
				synchronized (this) {
					for (ChatUser user : users) {
						if (user.getName().equals(name)) {
							foundUser = user;
							break;
						}
					}
				}
				ChatUser createdUser;
				if (foundUser == null) {
					synchronized (this) {
						createdUser = new ChatUser(name, address, OffsetDateTime.now(), serverClock,
								UUID.randomUUID());
						users.add(createdUser);
						log("user created: user=(%s,%s), addr=%s", name, createdUser.getId().toString(),
								address);
						// app.updateUsers(users);
					}
				} else {
					createdUser = null;
				}

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

				break;
			}
			case "/leave": {
				JsonObject input = getInputJson(exchange);
				String id = input.getString("id");
				ChatUser foundUser = null;
				synchronized (this) {
					for (int i = 0; i < users.size(); ++i) {
						ChatUser user = users.get(i);
						if (user.getId().toString().equals(id)) {
							foundUser = user;
							break;
						}
					}
				}
				boolean success;
				if (foundUser != null) {
					synchronized (this) {
						users.remove(foundUser);
						log("leave: user=(%s,%s)", foundUser.getName(), foundUser.getId());
					}
					success = true;
				} else {
					log("leave: no such user-id=%s", id);
					success = false;
				}

				if (success) {
					// app.updateUsers(users);
				}

				JsonObject out = new JsonObject();
				out.putBoolean("success", success);
				sendOutputJson(exchange, out);

				break;
			}
			case "/retrieve": {
				JsonObject input = getInputJson(exchange);
				String id = input.getString("id");
				long clock = input.getLong("clock");
				ChatUser foundUser = null;
				synchronized (this) {
					for (ChatUser user : users) {
						if (user.getId().toString().equals(id)) {
							foundUser = user;
							break;
						}
					}
				}

				JsonObject out;
				if (foundUser != null) {
					long currentClock = serverClock;
					List<ChatMessage> messages = new ArrayList<>();
					synchronized (this) {
						int index = this.timeline.size() - 1;
						while (index >= 0) {
							ChatMessage message = this.timeline.get(index);
							if (currentClock < message.getClock()) {
								messages.add(message);
								index--;
							} else {
								break;
							}
						}
						Collections.reverse(messages);
					}
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

				break;
			}
			case "/post": {
				JsonObject input = getInputJson(exchange);
				String id = input.getString("id");
				String messageStr = input.getString("message");

				ChatUser foundUser = null;
				synchronized (this) {
					for (ChatUser user : users) {
						if (user.getId().toString().equals(id)) {
							foundUser = user;
							break;
						}
					}
				}

				JsonObject out;
				if (foundUser != null) {
					ChatMessage message;
					synchronized (this) {
						++serverClock;
						message = new ChatMessage(messageStr, serverClock, foundUser, OffsetDateTime.now());
						timeline.add(message);
						log("new message: user=(%s,%s), message=%s", foundUser.getName(), foundUser.getId(),
								messageStr);
					}
					out = new JsonObject();
					out.putBoolean("success", true);
					out.putObject("message", message.toJson());
				} else {
					out = new JsonObject();
					out.putBoolean("success", false);
				}
				sendOutputJson(exchange, out);
				break;
			}
		}
	}
	public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

	public synchronized void log(String format, Object... args) {
		String str = String.format(format, args);
		String str2 = String.format("[%s: %d]: %s", OffsetDateTime.now().format(timeFormatter), serverClock, str);
		System.err.println(str2);
		app.addMessage(str2);
	}

}

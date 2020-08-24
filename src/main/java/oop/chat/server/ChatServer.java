package oop.chat.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import oop.chat.core.ChatMessage;
import oop.chat.core.ChatUser;
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
	private List<ChatMessage> timeline = new ArrayList<>();
	private long serverClock;

	public ChatServer(NonModChatServerApp app) {
		this.app = app;
	}

	public void startServer(String host, int port) {
		try {
			if (server != null) {
				stop();
			}
			server = HttpServer.create(new InetSocketAddress(host, port), 0);

			joinContext = server.createContext("/join", new ServerHandlerJoin(this));
			leaveContext = server.createContext("/leave", new ServerHandlerLeave(this));
			retrieveContext = server.createContext("/retrieve", new ServerHandlerRetrieve(this));
			postContext = server.createContext("/post", new ServerHandlerPost(this));

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
	public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

	public synchronized void log(String format, Object... args) {
		String str = String.format(format, args);
		String str2 = String.format("[%s: %d]: %s", OffsetDateTime.now().format(timeFormatter), serverClock, str);
		System.err.println(str2);
		app.addMessage(str2);
	}
  public ChatUser findUser(JsonObject input) {
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
		return foundUser;
	}
	public ChatUser findUserById(String id) {
		ChatUser foundUser = null;
		synchronized (this) {
			for (ChatUser user : users) {
				if (user.getId().toString().equals(id)) {
					foundUser = user;
					break;
				}
			}
		}
		return foundUser;
	}
	public ChatUser createUser(ChatUser foundUser, String name, InetAddress address) {
		ChatUser createdUser;
		if (foundUser == null) {
			synchronized (this) {
				createdUser = new ChatUser(name, address, OffsetDateTime.now(), serverClock,
						UUID.randomUUID());
				users.add(createdUser);
				log("user created: user=(%s,%s), addr=%s", name, createdUser.getId().toString(), address);
				// app.updateUsers(users);
			}
		} else {
			createdUser = null;
		}
		return createdUser;
	}
	public boolean deleteUser(ChatUser foundUser, String id) {
		boolean isSuccess;
		if (foundUser != null) {
			synchronized (this) {
				users.remove(foundUser);
				log("leave: user=(%s,%s)", foundUser.getName(), foundUser.getId());
			}
			isSuccess = true;
		} else {
			log("leave: no such user-id=%s", id);
			isSuccess = false;
		}

		if (isSuccess) {
			// app.updateUsers(users);
		}
		 return isSuccess;
	}
	public ChatMessage createMessage(ChatUser foundUser, String messageStr) {
		ChatMessage message;
		synchronized (this) {
			++serverClock;
			message = new ChatMessage(messageStr, serverClock, foundUser, OffsetDateTime.now());
			timeline.add(message);
			log("new message: user=(%s,%s), message=%s", foundUser.getName(), foundUser.getId(),
					messageStr);
		}
		return message;
	}
	public List<ChatMessage> fetchMessages() {
		List<ChatMessage> messages = new ArrayList<>();
		synchronized (this) {
			int index = timeline.size() - 1;
			while (index >= 0) {
				ChatMessage message = timeline.get(index);
				if (serverClock < message.getClock()) {
					messages.add(message);
					index--;
				} else {
					break;
				}
			}
			Collections.reverse(messages);
		}
		return messages;
	}
	public long getServerClock() {
		return serverClock;
	}
	public List<ChatMessage> getTimeline() {
		return timeline;
	}
}

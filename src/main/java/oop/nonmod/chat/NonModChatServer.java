package oop.nonmod.chat;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import oop.chat.core.JsonList;
import oop.chat.core.JsonObject;

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

public class NonModChatServer implements HttpHandler {
    public NonModChatServerApp app;
    public HttpServer server;
    public HttpContext joinContext;
    public HttpContext leaveContext;
    public HttpContext retrieveContext;
    public HttpContext postContext;

    public List<NonModChatUser> users = new ArrayList<>();
    public List<NonModChatMessage> timeline = new ArrayList<>();
    public long serverClock;


    public NonModChatServer(NonModChatServerApp app) {
        this.app = app;
    }

    public void startServer(String host, int port) {
        try {
            if (server != null) {
                stop();
            }
            server = HttpServer.create(new InetSocketAddress(host, port), 0);

            joinContext = server.createContext("/join", this);
            leaveContext = server.createContext("/leave", this);
            retrieveContext = server.createContext("/retrieve", this);
            postContext = server.createContext("/post", this);

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

            app.updateUsers(users);
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
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getHttpContext().getPath();
        if (path.equals("/join")) {
            JsonObject input = getInputJson(exchange);
            InetSocketAddress senderAddress = exchange.getRemoteAddress();
            InetAddress address = senderAddress.getAddress();
            String name = input.getString("name");
            NonModChatUser foundUser = null;
            synchronized (this) {
                for (int i = 0; i < users.size(); ++i) {
                    NonModChatUser user = users.get(i);
                    if (user.name.equals(name)) {
                        foundUser = user;
                        break;
                    }
                }
            }
            NonModChatUser createdUser;
            if (foundUser == null) {
                synchronized (this) {
                    createdUser = new NonModChatUser();
                    createdUser.name = name;
                    createdUser.address = address;
                    createdUser.loginTime = OffsetDateTime.now();
                    createdUser.id = UUID.randomUUID();
                    createdUser.lastAccessClock = serverClock;
                    users.add(createdUser);
                    log("user created: user=(%s,%s), addr=%s", name, createdUser.id.toString(), address);
                    app.updateUsers(users);
                }
            } else {
                createdUser = null;
            }

            JsonObject out;
            if (createdUser == null) {
                out = new JsonObject();
                out.putBoolean("success", false);
            } else {
                out = new JsonObject();
                out.putBoolean("success", true);
                out.putString("id", createdUser.id.toString());
                out.putLong("clock", createdUser.lastAccessClock);
            }
            sendOutputJson(exchange, out);

        } else if (path.equals("/leave")) {
            JsonObject input = getInputJson(exchange);
            String id = input.getString("id");
            NonModChatUser foundUser = null;
            synchronized (this) {
                for (int i = 0; i < users.size(); ++i) {
                    NonModChatUser user = users.get(i);
                    if (user.id.toString().equals(id)) {
                        foundUser = user;
                        break;
                    }
                }
            }
            boolean success;
            if (foundUser != null) {
                synchronized (this) {
                    users.remove(foundUser);
                    log("leave: user=(%s,%s)", foundUser.name, foundUser.id);
                }
                success = true;
            } else {
                log("leave: no such user-id=%s", id);
                success = false;
            }

            if (success) {
                app.updateUsers(users);
            }

            JsonObject out = new JsonObject();
            out.putBoolean("success", success);
            sendOutputJson(exchange, out);

        } else if (path.equals("/retrieve")) {
            JsonObject input = getInputJson(exchange);
            String id = input.getString("id");
            long clock = input.getLong("clock");
            NonModChatUser foundUser = null;
            synchronized (this) {
                for (int i = 0; i < users.size(); ++i) {
                    NonModChatUser user = users.get(i);
                    if (user.id.toString().equals(id)) {
                        foundUser = user;
                        break;
                    }
                }
            }

            JsonObject out;
            if (foundUser != null) {
                long currentClock = serverClock;
                List<NonModChatMessage> messages = new ArrayList<>();
                synchronized (this) {
                    int index = this.timeline.size() - 1;
                    while (index >= 0) {
                        NonModChatMessage message = this.timeline.get(index);
                        if (currentClock < message.clock) {
                            messages.add(message);
                            index--;
                        } else {
                            break;
                        }
                    }
                    Collections.reverse(messages);
                }
                foundUser.lastAccessClock = currentClock;

                out = new JsonObject();
                out.putBoolean("success", true);
                out.putLong("clock", currentClock);
                JsonList messagesJson = out.putList("messages");

                for (int i = 0; i < messages.size(); ++i) {
                    NonModChatMessage message = messages.get(i);
                    JsonObject messageJson = new JsonObject();

                    messageJson.putString("name", message.user.name);
                    messageJson.putString("message", message.message);
                    messageJson.putLong("clock", message.clock);
                    messageJson.putTime("time", message.time);
                    messagesJson.addObject(messageJson);
                }
            } else {
                out = new JsonObject();
                out.putBoolean("success", false);
                out.putLong("clock", -1L);
                out.putList("messages"); //no entries
            }
            sendOutputJson(exchange, out);

        } else if (path.equals("/post")) {
            JsonObject input = getInputJson(exchange);
            String id = input.getString("id");
            String messageStr = input.getString("message");

            NonModChatUser foundUser = null;
            synchronized (this) {
                for (int i = 0; i < users.size(); ++i) {
                    NonModChatUser user = users.get(i);
                    if (user.id.toString().equals(id)) {
                        foundUser = user;
                        break;
                    }
                }
            }

            JsonObject out;
            if (foundUser != null) {
                NonModChatMessage message = new NonModChatMessage();
                synchronized (this) {
                    ++serverClock;
                    message.clock = serverClock;
                    message.message = messageStr;
                    message.user = foundUser;
                    message.time = OffsetDateTime.now();
                    timeline.add(message);
                    log("new message: user=(%s,%s), message=%s", foundUser.name, foundUser.id, messageStr);
                }
                out = new JsonObject();
                out.putBoolean("success", true);

                JsonObject messageJson = new JsonObject();
                messageJson.putString("name", message.user.name);
                messageJson.putString("message", message.message);
                messageJson.putLong("clock", message.clock);
                messageJson.putTime("time", message.time);
                out.putObject("message", messageJson);
            } else {
                out = new JsonObject();
                out.putBoolean("success", false);
            }
            sendOutputJson(exchange, out);
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

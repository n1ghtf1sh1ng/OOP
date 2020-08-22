package oop.nonmod.chat;

import oop.chat.core.JsonObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NonModChatClient {
    String serverHost;
    int serverPort;
    String name;
    String id;
    long clock;
    List<NonModChatMessage> timeline = new ArrayList<>();

    public NonModChatClient(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    public String getServerUrl(String path) {
        return "http://" + serverHost + ":" + serverPort + "/" + path;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public long getClock() {
        return clock;
    }

    public JsonObject transfer(String path, JsonObject json) {
        try {
            HttpURLConnection con = (HttpURLConnection) URI.create(getServerUrl(path)).toURL().openConnection();
            try {
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                con.connect();
                try (OutputStream out = con.getOutputStream()) {
                    out.write(json.toSource().getBytes(StandardCharsets.UTF_8));
                }
                int res = con.getResponseCode();
                if (res == HttpURLConnection.HTTP_OK) {
                    System.err.println("OK");
                    try (InputStream in = con.getInputStream()) {
                        return JsonObject.fromSource(
                                StandardCharsets.UTF_8.decode(
                                        ByteBuffer.wrap(in.readAllBytes())).toString());
                    }
                } else {
                    throw new RuntimeException("connection failure: " + res);
                }
            } finally {
                con.disconnect();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean login(String name) {
        this.name = name;
        JsonObject input = new JsonObject();
        input.putString("name", name);

        JsonObject out = transfer("join", input);
        boolean success = out.getBoolean("success");
        if (success) {
            id = out.getString("id");
            clock = out.getLong("clock");
            return true;
        } else {
            return false;
        }
    }

    public List<NonModChatMessage> retrieveMessages() {
        JsonObject input = new JsonObject();
        input.putString("id", id);
        input.putLong("clock", clock);

        JsonObject output = transfer("retrieve", input);
        boolean success = output.getBoolean("success");
        if (success) {
            long newClock = output.getLong("clock");
            List<NonModChatMessage> messages = new ArrayList<>();
            List<JsonObject> outputMessagesList = output.getList("messages").getListAsObjects();
            for (int i = 0; i < outputMessagesList.size(); ++i) {
                JsonObject messageJson = outputMessagesList.get(i);
                NonModChatMessage m = new NonModChatMessage();
                m.message = messageJson.getString("message");
                m.clock = messageJson.getLong("clock");

                NonModChatUser user = new NonModChatUser();
                user.name = messageJson.getString("name");
                m.user = user;

                m.time = messageJson.getTime("time");
                messages.add(m);
            }
            System.err.println("clock: " + newClock + ", messages=" + messages.size());

            //merging
            List<NonModChatMessage> newMessages = new ArrayList<>();
            int localIndex = timeline.size() - 1;
            int retIndex = messages.size() - 1;
            while (retIndex >= 0) {
                NonModChatMessage newMessage = messages.get(retIndex);

                boolean added = false;
                while (localIndex >= 0) {
                    NonModChatMessage localMessage = timeline.get(localIndex);
                    if (localMessage.clock == newMessage.clock &&
                            localMessage.user.name.equals(newMessage.user.name)) {
                        //already exists in the local timeline
                        added = true;
                        break;
                    } else if (localMessage.clock <= newMessage.clock) {
                        timeline.add(localIndex + 1, newMessage);
                        newMessages.add(0, newMessage);
                        added = true;
                        break;
                    }
                    localIndex--;
                }
                if (!added) {
                    timeline.add(0, newMessage);
                }
                retIndex--;
            }
            this.clock = newClock;
            return newMessages;
        } else {
            return Collections.emptyList();
        }
    }

    public NonModChatMessage post(String message) {
        JsonObject input = new JsonObject();
        input.putString("id", id);
        input.putString("message", message);

        JsonObject output = transfer("post", input);
        boolean success = output.getBoolean("success");
        if (success) {
            List<NonModChatMessage> messages = new ArrayList<>();

            JsonObject messageJson = output.getObject("message");
            NonModChatMessage m = new NonModChatMessage();
            m.message = messageJson.getString("message");
            m.clock = messageJson.getLong("clock");

            NonModChatUser user = new NonModChatUser();
            user.name = messageJson.getString("name");
            m.user = user;

            m.time = messageJson.getTime("time");
            messages.add(m);


            //merging
            List<NonModChatMessage> newMessages = new ArrayList<>();
            int localIndex = timeline.size() - 1;
            int retIndex = messages.size() - 1;
            while (retIndex >= 0) {
                NonModChatMessage newMessage = messages.get(retIndex);

                boolean added = false;
                while (localIndex >= 0) {
                    NonModChatMessage localMessage = timeline.get(localIndex);
                    if (localMessage.clock == newMessage.clock &&
                            localMessage.user.name.equals(newMessage.user.name)) {
                        //already exists in the local timeline
                        added = true;
                        break;
                    } else if (localMessage.clock <= newMessage.clock) {
                        timeline.add(localIndex + 1, newMessage);
                        newMessages.add(0, newMessage);
                        added = true;
                        break;
                    }
                    localIndex--;
                }
                if (!added) {
                    timeline.add(0, newMessage);
                }
                retIndex--;
            }
            return m;
        } else {
            return null;
        }
    }

    public boolean leave() {
        JsonObject input = new JsonObject();
        input.putString("id", id);
        JsonObject out = transfer("leave", input);
        return out.getBoolean("success");
    }
}

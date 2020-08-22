package oop.chat.core;

import java.time.OffsetDateTime;

public class ChatMessage {
	private String message;
	private long clock;
	private ChatUser user;
	private OffsetDateTime time;

	public ChatMessage(String message, long clock, ChatUser user, OffsetDateTime time) {
		this.message = message;
		this.clock = clock;
		this.user = user;
		this.time = time;
	}

	// Create ChatMessage from JSON
	public static ChatMessage fromJson(JsonObject json) {
		return new ChatMessage(json.getString("message"), json.getLong("clock"), new ChatUser(json.getString("name")), json.getTime("time"));
	}
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.putString("name", user.getName());
		jsonObject.putString("message", message);
		jsonObject.putLong("clock", clock);
		jsonObject.putTime("time", time);
		return jsonObject;
	}
	public String getMessage() {
		return this.message;
	}
	public long getClock() {
		return clock;
	}
	public ChatUser getUser() {
		return user;
	}
	public OffsetDateTime getTime() {
		return time;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setClock(long clock) {
		this.clock = clock;
	}
	public void setUser(ChatUser user) {
		this.user = user;
	}
	public void setTime(OffsetDateTime time) {
		this.time = time;
	}
}

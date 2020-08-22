package oop.chat.core;

import java.net.InetAddress;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ChatUser {
	private String name;
	private InetAddress address;
	private OffsetDateTime loginTime;
	private long lastAccessClock;
	private UUID id;

	public ChatUser(String name) {
		this.name = name;
	}
	public ChatUser(String name, InetAddress address, OffsetDateTime loginTime, long lastAccessClock,
			UUID id) {
		this.name = name;
		this.address = address;
		this.loginTime = loginTime;
		this.lastAccessClock = lastAccessClock;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public InetAddress getAddress() {
		return address;
	}
	public OffsetDateTime getLoginTime() {
		return loginTime;
	}
	public long getLastAccessClock() {
		return lastAccessClock;
	}
	public UUID getId() {
		return id;
	}
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	public void setLoginTime(OffsetDateTime loginTime) {
		this.loginTime = loginTime;
	}
	public void setLastAccessClock(long lastAccessClock) {
		this.lastAccessClock = lastAccessClock;
	}
	public void setId(UUID id) {
		this.id = id;
	}
}

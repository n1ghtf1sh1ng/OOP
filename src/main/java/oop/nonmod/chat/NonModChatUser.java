package oop.nonmod.chat;

import java.net.InetAddress;
import java.time.OffsetDateTime;
import java.util.UUID;

public class NonModChatUser {
    public String name;
    public InetAddress address;
    public OffsetDateTime loginTime;
    public long lastAccessClock;
    public UUID id;
}

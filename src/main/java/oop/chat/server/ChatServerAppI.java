package oop.chat.server;

import java.util.List;
import oop.chat.core.ChatUser;

public interface ChatServerAppI {
	public void addMessage(String message);
	public void updateUsers(List<ChatUser> users);
}

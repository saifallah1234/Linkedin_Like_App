package Elkhadema.khadema.Service.ServiceInterfaces;

import java.util.List;

import Elkhadema.khadema.domain.Message;
import Elkhadema.khadema.domain.User;

public interface MessageService {
    void sendMessage(User reciver, Message message);
    void deleteMessage(Message message);
    List<Message> chat(User currentUser, User otherUser);
    // this service gives you a list of all chats between the current user and another user with the most recent message
    List<Message> listOfChats(User user);
    void MessageRead(Message message,User user);
}

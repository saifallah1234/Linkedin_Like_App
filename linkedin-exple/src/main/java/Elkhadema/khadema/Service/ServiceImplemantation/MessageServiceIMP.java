package Elkhadema.khadema.Service.ServiceImplemantation;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import Elkhadema.khadema.DAO.DAOImplemantation.MessageDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.UserDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.MessageService;
import Elkhadema.khadema.domain.Message;
import Elkhadema.khadema.domain.MessageReceiver;
import Elkhadema.khadema.domain.User;

public class MessageServiceIMP implements MessageService {
	MessageDAO mdao= new MessageDAO();
	UserDAO uDao = new UserDAO();
	@Override
	public void sendMessage(User reciever, Message message) {
		message.setCreationDate(new Date());
		MessageReceiver mr = new MessageReceiver(message, reciever, 0, 0);
		try {
			mdao.save(message, mr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMessage(Message message) {
		mdao.delete(message);
	}

	@Override
	public List<Message> chat(User currentUser, User otherUser) {
		List<Message> collect = mdao.getconversation(currentUser, otherUser).stream()
		.map(t -> {if(t.getSender().getId()==currentUser.getId()) {
				t.setSender(currentUser);
				}
				else {t.setSender(otherUser);
				}return t;
		}).collect(Collectors.toList());
		return collect;



	}

	@Override
	public List<Message> listOfChats(User user) {
		return mdao.getMessageByUserId(user.getId());
	}
	public List<User> getListoflastmessagers(User user){
		return mdao.getlistofChatsByUserId(user.getId()).stream().map(t -> uDao.get(t.getId()).get()).collect(Collectors.toList());
	}
	@Override
	public void MessageRead(Message message,User user){
		MessageReceiver messageReceiver=new MessageReceiver(message, user, 1);
		mdao.updateReciverMessage(new MessageReceiver(message, user, 0), messageReceiver);
	}

}

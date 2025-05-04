package Elkhadema.khadema.DAO.DAOImplemantation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Message;
import Elkhadema.khadema.domain.MessageReceiver;

import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.*;

public class MessageDAO {
	private static Connection connection = ConexDB.getInstance();

	public void save(Message t, MessageReceiver mr) throws IOException {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`messages` (`message_id`, `content`, `sender_id`, `creation_date`, `parent_message_id` ,`image`) VALUES (NULL, ?, ?, ?, ?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, t.getContent());
			pstmt.setInt(2, t.getSender().getId());
			pstmt.setTimestamp(3, new Timestamp(t.getCreationDate().getTime()));
			if (t.getParentMessageId() != 0) {
				pstmt.setInt(4, t.getParentMessageId());
			} else
				pstmt.setNull(4, java.sql.Types.INTEGER);
			if(t.getImage()!=null)
			pstmt.setBlob(5, new ByteArrayInputStream(t.getImage().ImageCompression()));
			else pstmt.setNull(5, java.sql.Types.BLOB);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				t.setId(rs.getInt(1));
			}
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`message_receiver` (`id`, `user_id`, `message_id`, `is_read`) VALUES (NULL, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(2, t.getId());
			pstmt.setInt(1, mr.getUser().getId());
			pstmt.setInt(3, mr.isRead());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void update(Message t, Message newT) {
		try {
			String sql = "UPDATE `khademadb`.`messages` SET  `content` = ?, `creation_date` = ?,`parent_message_id`=? WHERE `messages`.`message_id` = "
					+ t.getId() + ";";

			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, newT.getContent());
			p.setDate(2, (Date) newT.getCreationDate());
			p.setString(3, newT.getContent());
			p.setLong(4, newT.getParentMessageId());
			p.executeUpdate();
			p.getGeneratedKeys();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void updateReciverMessage(MessageReceiver mr, MessageReceiver newmr) {
		try {
			String sql = "UPDATE `khademadb`.`message_receiver` SET  `is_read` = ?, `user_id` = ?,`message_id`=?  WHERE `message_id` = "
					+ mr.getMessage().getId() + ";";

			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, newmr.isRead());
			p.setInt(2, newmr.getUser().getId());
			p.setLong(3, newmr.getMessage().getId());
			p.executeUpdate();
			p.getGeneratedKeys();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void delete(Message t) {
		try {
			connection.createStatement().execute("DELETE FROM `messages` WHERE  `messages`.`message_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public List<Message> getconversation(User u1, User u2) {
		List<Message> messages = new ArrayList<>();
		String sql = "SELECT * FROM messages m INNER JOIN message_receiver mr ON m.message_id = mr.message_id WHERE( m.sender_id = ? OR m.sender_id = ?) AND (mr.user_id = ? OR mr.user_id = ?) ORDER BY creation_date ";

		try {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, u1.getId());
				statement.setInt(2, u2.getId());
				statement.setInt(3, u1.getId());
				statement.setInt(4, u2.getId());
				Message message;
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						message=new Message(resultSet.getLong("message_id"), new User(resultSet.getInt("sender_id"), "", ""),
								resultSet.getString("content"), resultSet.getDate("creation_date"), resultSet.getInt("parent_message_id"));
						if (resultSet.getBytes("image")==null) {
							message.setImage(null);
						}
						else
						message.setImage(new Media(null, resultSet.getBytes("image"),"img"));
						messages.add(message);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	public Optional<MessageReceiver> getRecieverMessagesById(Message m) {
		String sql = "SELECT * FROM `message_receiver` WHERE `message_id`=" + m.getId();
		MessageReceiver message = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				message = new MessageReceiver(m, new User(rs.getInt("user_id"), "", ""), rs.getInt("is_read"),
						rs.getInt("id"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(message);
	}

	public List<Message> getMessageByUserId(int id) {

		String sql = "SELECT * FROM `messages`m ,`message_receiver` mr WHERE mr.message_id=m.message_id and `sender_id`=" + id;
		List<Message> messages = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				Message message=new Message(rs.getLong("message_id"), new User(rs.getInt("sender_id"), "", ""),
						rs.getString("content"), rs.getDate("creation_date"), rs.getInt("parent_message_id"));
						message.setRead(rs.getInt("is_read"));
				message.setImage(new Media(null,rs.getBytes("image"),"img"));

				messages.add(message);
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return messages;
	}
	public List<Message> getMessageByReciverId(int id) {

		String sql = "SELECT * FROM `messages`m ,`message_receiver` mr,`user` WHERE mr.message_id=m.message_id and `user`.user_id=mr.user_id  `user_id`=" + id;
		List<Message> messages = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				Message message=new Message(rs.getLong("message_id"), new User(rs.getInt("sender_id"), "", rs.getString("username")),
						rs.getString("content"), rs.getDate("creation_date"), rs.getInt("parent_message_id"));
						message.setRead(rs.getInt("is_read"));
				message.setImage(new Media(null,rs.getBytes("image"),"img"));

				messages.add(message);
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return messages;
	}

	public List<User> getlistofChatsByUserId(int id) {
		String sql = "SELECT DISTINCT m.user_id FROM message_receiver m JOIN messages e ON e.message_id = m.message_id WHERE e.sender_id ="
				+ id + " ORDER BY e.creation_date ASC";
		List<User> users = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				users.add(new User(rs.getInt("user_id"), null, null));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return users;
	}
}

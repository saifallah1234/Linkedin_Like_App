package Elkhadema.khadema.DAO.DAOImplemantation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class UserDAO  {
	private static Connection connection = ConexDB.getInstance();

	public Optional<User> get(long id) {
		String sql = "SELECT *  FROM `user`,`contact_info` WHERE `user_id` = " + id
				+ " and user.`contact_info_id`=contact_info.contact_info_id";
		User user = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				user = new User(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")),
						rs.getString("userName"), rs.getDate("creationdate"),
						Date.from(rs.getTimestamp("last_login").toInstant())	,new Media(null,(rs.getBytes("photo")),"img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(user);
	}

	public List<User> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		String SQL = "SELECT * FROM `user`";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				users.add(new User(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")),
						rs.getString("userName"), rs.getDate("creationdate"),
						Date.from(rs.getTimestamp("last_login")
								.toInstant()),new Media(null,(rs.getBytes("photo")),"img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return users;
	}

	public void save(User t) throws IOException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			ContactInfoDAO ci = new ContactInfoDAO();
			System.out.println(t.getContactInfo().getEmail());
			System.out.println(t.getCreationDate());
			ci.save(t.getContactInfo());
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`user` (`password_encrypted`, `contact_info_id`, `username`, `creationdate`, `last_login`, `banned`, `is_active`,`photo`) VALUES (?, ?, ?, ?, ?, ?, ?,?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(3, t.getUserName());
			pstmt.setString(1, t.getPassword());
			pstmt.setDate(4,new java.sql.Date(t.getCreationDate().getTime()));
			pstmt.setLong(2, t.getContactInfo().getId());
			pstmt.setTimestamp(5, new java.sql.Timestamp(t.getLastloginDate().getTime()));
			pstmt.setBoolean(6, false);
			pstmt.setBoolean(7, false);
			if (t.getPhoto()==null) {

				pstmt.setNull(8, java.sql.Types.BLOB);
			}
			else
			pstmt.setBlob(8,  new ByteArrayInputStream(t.getPhoto().ImageCompression()));
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				t.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void update(User t, User newT) {
		try {
			String sql = "UPDATE `khademadb`.`user` SET `password_encrypted` = ?, `username` = ?, `creationdate` = ?, `last_login` = ?, `banned` = ?, `is_active` = ?,`photo`=? WHERE `user`.`user_id` = "
					+ t.getId() + ";";

			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setString(2, newT.getUserName());
			p.setString(1, newT.getPassword());
			p.setDate(3, new java.sql.Date(t.getCreationDate().getTime()));
			p.setTimestamp(4,new java.sql.Timestamp(t.getLastloginDate().getTime()));
			p.setBoolean(5, newT.is_banned);
			p.setBoolean(6, newT.is_active);
			p.setBlob(7,  new ByteArrayInputStream(t.getPhoto().ImageCompression()));
			p.executeUpdate();
			p.getGeneratedKeys();
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public void delete(User t) {
		try {
			connection.createStatement().execute("DELETE FROM `user` WHERE `user_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Optional<User> Login(String username,String password) {
		String sql = "SELECT *  FROM `user` WHERE `username`='"+username+"'AND `password_encrypted`='"+password+"'" ;
		User user = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {

				user = new User(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")),
						rs.getString("userName"), rs.getDate("creationdate"),
						rs.getDate("last_login"),new Media(null,(rs.getBytes("photo")),"img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active"));
						  }

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(user);
	}
}

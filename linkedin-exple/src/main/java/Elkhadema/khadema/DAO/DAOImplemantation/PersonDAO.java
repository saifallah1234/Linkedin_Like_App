package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.util.ConexDB;

public class PersonDAO {
	private static Connection connection = ConexDB.getInstance();

	public Optional<Person> get(long id) {
		String sql = "SELECT *  FROM `user`,`person` WHERE `user`.`user_id` = " + id
				+ " and `user`.user_id=`person`.user_id";
		Person person = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				person = new Person(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")),
						rs.getString("userName"), rs.getDate("creationdate"),
						rs.getDate("last_login"), new Media(null, (rs.getBytes("photo")), "img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getInt("age"), rs.getString("job"),
						rs.getString("sexe"), rs.getString("about"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(person);
	}

	public List<Person> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		List<Person> persons = new ArrayList<>();
		String SQL = "SELECT *  FROM `user`,person WHERE `user`.user_id=`person`.user_id ";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Person person = new Person(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")),
						rs.getString("userName"), rs.getDate("creationdate"),
						rs.getDate("last_login"), new Media(null, (rs.getBytes("photo")), "img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getInt("age"), rs.getString("job"),
						rs.getString("sexe"), rs.getString("about"));
				persons.add(person);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return persons;
	}

	public void save(Person t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`person` (`user_id`,`first_name`,`last_name`,`job`,`sexe`,`age`,`about`) VALUES (?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, t.getId());
			pstmt.setString(2, t.getFirstName());
			pstmt.setString(3, t.getLastName());
			pstmt.setString(4, t.getJob());
			pstmt.setString(5, t.getSexe());
			pstmt.setInt(6, t.getAge());
			pstmt.setString(7, t.getAbout());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void update(Person t, Person newT) {
		try {
			String sql = "UPDATE `khademadb`.`person` SET `first_name`=?,`last_name`=?,`job`=?, `sexe`=?, `age`=?,`about`=? WHERE `person`.`user_id` = "
					+ t.getId() + ";";

			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setString(2, newT.getFirstName());
			p.setString(1, newT.getLastName());
			p.setString(3, t.getJob());
			p.setString(4, t.getSexe());
			p.setInt(5, t.getAge());
			p.setString(6, t.getAbout());

			p.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void delete(Person t) {
		try {
			connection.createStatement().execute("DELETE FROM `person` WHERE `user_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

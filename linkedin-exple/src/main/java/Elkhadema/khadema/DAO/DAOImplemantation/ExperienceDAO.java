package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Elkhadema.khadema.domain.Experience;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class ExperienceDAO {
	private static Connection connection = ConexDB.getInstance();

	public List<Experience> getAll(User user) {
		Statement stmt = null;
		ResultSet rs = null;
		List<Experience> experiences = new ArrayList<>();
		String SQL = "SELECT *  FROM `user_experience`,experience WHERE `user_experience`.experience_id=`experience`.experience_id AND user_id= "
				+ user.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				experiences.add(new Experience(rs.getLong("experience_id"), rs.getString("description"),
						rs.getString("mission"), rs.getString("type"), rs.getDate("start_date").toLocalDate(),
						rs.getDate("end_date").toLocalDate(),
						rs.getString("technologie")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return experiences;
	}

	public void save(Experience t, User user) {
		PreparedStatement pstmt = null;
		long id = 0;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`experience` (`experience_id`, `description`, `mission`, `type`, `technologie`,`start_date`,`end_date`) VALUES (NULL, ?, ?, ?, ?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, t.getDescription());
			pstmt.setString(2, t.getMission());
			pstmt.setString(3, t.getType());
			pstmt.setString(4, t.getTechnologie());
			pstmt.setDate(5, Date.valueOf(t.getStartDate()) );
			pstmt.setDate(6, Date.valueOf(t.getEndDate()) );
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}

			pstmt = connection.prepareStatement(
				"INSERT INTO `khademadb`.`user_experience` (`experience_id`, `user_id`) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
					pstmt.setLong(2, user.getId());
			pstmt.setLong(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void update(Experience t, Experience newT) {
		try {
			String sql = "UPDATE `khademadb`.`experience` SET `description` = ?, `mission` = ?, `type` = ?, `technologie` = ? ,`start_date`=?,`end_date`=? WHERE `competance`.`competance_id` =  "
			+ t.getId() + ";";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getDescription());
			pstmt.setString(2, newT.getMission());
			pstmt.setString(3, newT.getType());
			pstmt.setString(4, newT.getTechnologie());
			pstmt.setDate(5, Date.valueOf(t.getStartDate()) );
			pstmt.setDate(6, Date.valueOf(t.getEndDate()) );
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void delete(Experience t) {
		try {
			connection.createStatement().execute("DELETE FROM `experience` WHERE `experience_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

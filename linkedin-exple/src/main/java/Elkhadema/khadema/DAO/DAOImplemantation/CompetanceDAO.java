package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.Competance;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class CompetanceDAO {
	private static Connection connection = ConexDB.getInstance();

	public List<Competance> getAll(User user) {
		Statement stmt = null;
		ResultSet rs = null;
		List<Competance> competances = new ArrayList<>();
		String SQL = "SELECT *  FROM `user_competance_details`,competance WHERE `competance`.competance_id=`user_competance_details`.competance_id AND user_id= "+user.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				competances.add(new Competance(rs.getLong("competance_id"),rs.getString("titre"),rs.getString("technologie"),rs.getString("description"),rs.getInt("niveau")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return competances;
	}

	public void save(Competance t,User user) {
		PreparedStatement pstmt = null;
		long id=0;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`competance` (`competance_id`, `titre`, `technologie`, `description`, `niveau`) VALUES (NULL, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, t.getTitre());
			pstmt.setString(2, t.getTechnologie());
			pstmt.setString(3, t.getDescription());
			pstmt.setInt(4, t.getNiveau());
			pstmt.executeUpdate();
			ResultSet rs=pstmt.getGeneratedKeys();
			while (rs.next()) {
				id=rs.getLong(1);
			}

			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`user_competance_details` (`user_id`, `competance_id`) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, user.getId());
			pstmt.setLong(2,id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void update(Competance t, Competance newT) {
		try {
			String sql = "UPDATE `khademadb`.`competance` SET `titre` = ?, `technologie` = ?, `description` = ?, `niveau` = ? WHERE `competance`.`competance_id` =  "
					+ t.getId() + ";";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getTitre());
			pstmt.setString(2, newT.getTechnologie());
			pstmt.setString(3, newT.getDescription());
			pstmt.setInt(4, newT.getNiveau());

			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void delete(Company t) {
		try {
			connection.createStatement().execute("DELETE FROM `competance` WHERE `competance_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Elkhadema.khadema.domain.Follow;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class FollowDAO  {
	private static Connection connection = ConexDB.getInstance();
	public List<Follow> getAllFollowersById(long id) {

		Statement stmt = null;
		ResultSet rs = null;
		List<Follow> follows = new ArrayList<>();
		String SQL = "SELECT * FROM `followers` where `follower_id`="+id;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				follows.add(new Follow(new User((int)id, null, null),new User(rs.getInt("followed_id"), null, null)));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return follows;
	}

	public void save(Follow t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`followers` (`follower_id`, `followed_id`) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, t.getFollower().getId());
			pstmt.setInt(2, t.getFollowing().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void delete(Follow t) {
		try {
			connection.createStatement().execute("DELETE FROM `followers` WHERE  `followers`.`follower_id` =" + t.getFollower().getId()+" and `followers`.`followed_id`="+t.getFollowing().getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public List<Follow> getfollowingByid(long id) {
		Statement stmt = null;
		ResultSet rs = null;
		List<Follow> follows = new ArrayList<>();
		String SQL = "SELECT * FROM `followers` where `followed_id`="+id;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				follows.add(new Follow(new User(rs.getInt("follower_id"), null, null),new User((int)id, null, null)));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return follows;
	}

}

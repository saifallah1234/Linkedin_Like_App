package Elkhadema.khadema.DAO.DAOImplemantation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class ReportDAO {
	private static Connection connection = ConexDB.getInstance();
	public static void save(Post p,String description) throws IOException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`report` (`post_id`, `description`) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setLong(1, p.getId());
			pstmt.setString(2, description);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	public static List<Post> getAllReportedPost() {
		Statement stmt = null;
		ResultSet rs = null;
		List<Post> users = new ArrayList<>();
		String SQL = "SELECT r.`post_id`,count(*) t,p.user_id,p.creationdate,username FROM `report` r,posts p,user WHERE p.`post_id`=r.`post_id` AND p.user_id=user.user_id group by `post_id` order by t DESC";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				users.add(new Post(new User(rs.getInt("user_id"), null, rs.getString("username")), null, null, 0, null, rs.getDate("creationdate"), rs.getInt("post_id")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return users;
	}
	public static List<String> getdescriptionReportfromPost(Post post) {
		Statement stmt = null;
		ResultSet rs = null;
		List<String> users = new ArrayList<>();
		String SQL = "SELECT description FROM `report` WHERE `post_id`="+post.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				users.add(rs.getString("description"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return users;
	}
	public static void delete(Post post) {
		try {
			connection.createStatement().execute("DELETE FROM `report` WHERE `post_id`= "+post.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

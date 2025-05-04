package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.Reaction;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class ReactionDAO {
	private static Connection connection = ConexDB.getInstance();

	public Optional<Reaction> get(User user, Post post) {
		String sql = "SELECT  post_reaction.* , username FROM `post_reaction` , user WHERE `post_reaction`.`post_id` = "
				+ user.getId() + " AND `post_reaction`.`user_id` = " + post.getId()
				+ " AND post_reaction.`user_id` = user.user_id";
		Reaction reaction = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				reaction = new Reaction(
						new User(rs.getInt("user_id"), "", rs.getString("username")),
						new Post(rs.getInt("post_id")), rs.getString("reactiontype"), rs.getDate("creationdate"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(reaction);
	}

	public List<Reaction> getAll(Post post) {
		Statement stmt = null;
		ResultSet rs = null;
		List<Reaction> reactions = new ArrayList<>();
		String SQL = "SELECT *  FROM post_reaction p ,user u WHERE u.user_id=p.user_id and  post_id = "
				+ post.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				reactions.add(new Reaction(
						new User(rs.getInt("user_id"), "", rs.getString("username")),
						new Post(rs.getInt("post_id")), rs.getString("reactiontype"), rs.getDate("creationdate")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return reactions;
	}

	public void save(Reaction t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`post_reaction` (`post_id`, `user_id`, `reactiontype`, `creationdate`) VALUES (?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, t.getPost().getId());
			pstmt.setLong(2, t.getUser().getId());
			pstmt.setString(3, t.getType());
			pstmt.setDate(4, new Date (t.getCreationDate().getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void update(Reaction t, Reaction newT) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"UPDATE `khademadb`.`post_reaction` SET `reactiontype` = ?, `creationdate` = ? WHERE `post_reaction`.`post_id` = ? AND `post_reaction`.`user_id` = ?;",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getType());
			pstmt.setDate(2, (Date) newT.getCreationDate());
			pstmt.setLong(3, t.getPost().getId());
			pstmt.setLong(4, t.getUser().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void delete(Reaction t) {
		try {
			connection.createStatement().execute("DELETE FROM `post_reaction` WHERE `post_id`=" + t.getPost().getId()
					+ " AND `user_id`=" + t.getUser().getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

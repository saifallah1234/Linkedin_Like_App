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

import Elkhadema.khadema.domain.Comment;
import Elkhadema.khadema.domain.CommentReaction;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

@Deprecated
public class ReactionCommentDAO {
	private static Connection connection = ConexDB.getInstance();

	public Optional<CommentReaction> get(User user, Comment comment) {
		String sql = "SELECT *  FROM `comment_reaction` WHERE `comment_id` = " + user.getId() + " AND `user_id` = "
				+ comment.getId();
		CommentReaction reaction = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				reaction = new CommentReaction(user, comment, rs.getString("reactiontype"), rs.getDate("creationdate"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(reaction);
	}

	public List<CommentReaction> getAll(Comment comment) {
		Statement stmt = null;
		ResultSet rs = null;
		List<CommentReaction> reactions = new ArrayList<>();
		String SQL = "SELECT  post_reaction . * , username  FROM user,post_reaction WHERE post_reaction.`user_id` = user.user_id  ORDER BY post_id";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				reactions.add(
						new CommentReaction(
								new User(rs.getInt("user_id"), "", rs.getString("username")), comment,
								rs.getString("reactiontype"), rs.getDate("creationdate")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return reactions;
	}

	public void save(CommentReaction t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`comment_reaction` (`comment_id`, `user_id`, `reactiontype`, `creationdate`) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, t.getComment().getId());
			pstmt.setLong(2, t.getUser().getId());
			pstmt.setString(3, t.getType());
			pstmt.setDate(4, (Date) t.getCreationDate());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void update(CommentReaction t, CommentReaction newT) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"UPDATE `khademadb`.`comment_reaction` SET `reactiontype` = ?, `creationdate` = ? WHERE `comment_reaction`.`comment_id` = ? AND `comment_reaction`.`user_id` = ?;;",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getType());
			pstmt.setDate(2, (Date) newT.getCreationDate());
			pstmt.setLong(3, t.getComment().getId());
			pstmt.setLong(4, t.getUser().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	void delete(CommentReaction t) {
		try {
			connection.createStatement().execute("DELETE FROM `comment_reaction` WHERE `comment_id`="
					+ t.getComment().getId() + " AND `user_id`=" + t.getUser().getId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

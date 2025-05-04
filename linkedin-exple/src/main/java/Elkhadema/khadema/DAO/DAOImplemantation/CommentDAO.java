package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Elkhadema.khadema.DAO.DAOInterfaces.Dao;
import Elkhadema.khadema.domain.Comment;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

@Deprecated
public class CommentDAO implements Dao<Comment> {
	private static Connection connection = ConexDB.getInstance();

	@Override
	public Optional<Comment> get(long id) {
		String sql = "SELECT user.user_id,comments.*  FROM `comments`,user WHERE `comment_id` = " + id
				+ " AND user.user_id=comments.user_id";
		Comment comment = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				comment = new Comment(rs.getInt("comment_id"), rs.getString("content"), new Post(null, "", null, 0, "", null, 0),
						new User(rs.getInt("user_id"), "", ""),
						rs.getString("typecontent"), null);
			}
		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(comment);
	}

	@Override
	public List<Comment> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		List<Comment> comment = new ArrayList<>();
		String SQL = "SELECT user.user_id,comments.*  FROM `comments`,user WHERE user.user_id=comments.user_id order by post_id";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				comment.add(
						new Comment(rs.getInt("comment_id"), rs.getString("content"), new Post(null, "", null, 0, "", null, 0),
								new User(rs.getInt("user_id"), "", ""),
								rs.getString("typecontent"), null));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return comment;
	}

	@Override
	public void save(Comment t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`comments` (`comment_id`, `post_id`, `user_id`, `content`, `typecontent`) VALUES (NULL, ?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(4, t.getContenttype());
			pstmt.setLong(1, t.getPost().getId());
			pstmt.setLong(2, t.getUser().getId());
			pstmt.setString(3, t.getContent());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void update(Comment t, Comment newT) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"UPDATE `khademadb`.`comments` SET `content` = ?, `typecontent` = ? WHERE `comments`.`comment_id` = "
							+ t.getId() + ";",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getContent());
			pstmt.setString(2, newT.getContenttype());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void delete(Comment t) {
		try {
			connection.createStatement().execute("DELETE FROM `comments` WHERE `comment_id`=" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

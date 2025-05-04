package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import Elkhadema.khadema.domain.Admin;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class AdminDAO {

	private static Connection connection = ConexDB.getInstance();
	/**
	 * @param user to check if admin
	 * @return the user as optional of Admin else empty optional
	 */
	public Optional<Admin> isAdmin(User user) {
		String sql = "SELECT *  FROM `user` u,`admin` a WHERE u.user_id=a.user_id and u.`user_id` = " + user.getId();
		Admin admin = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				admin = new Admin(user.getId(),user.getPassword(),user.getContactInfo(),user.getUserName(),user.getCreationDate(),user.getLastloginDate(),user.getPhoto(),user.is_banned,user.is_active,rs.getInt("admin_level"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(admin);
	}
	public void makeAdmin(User user,int level) {
		try {
			PreparedStatement ptsm=connection.prepareStatement("INSERT INTO `khademadb`.`admin` (`user_id`, `admin_level`) VALUES (?, ?)");
			ptsm.setLong(1, user.getId());
			ptsm.setInt(2, level);
			ptsm.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void changeAdminLevel(User user,int level) {
		try {
			PreparedStatement ptsm=connection.prepareStatement("UPDATE `khademadb`.`admin` SET `admin_level` = ? WHERE `user_id` ="+user.getId());
			ptsm.setInt(1, level);
			ptsm.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

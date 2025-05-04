package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.util.ConexDB;

public class ContactInfoDAO {
	private static Connection connection = ConexDB.getInstance();

	public Optional<ContactInfo> get(long id) {
		String sql = "SELECT *  FROM `contact_info` WHERE `contact_info_id` = " + id;
		ContactInfo Ci = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				Ci = new ContactInfo(id,rs.getString("email"),rs.getString("address"),rs.getInt("phone number"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(Ci);
	}

	
	public void save(ContactInfo ci) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`contact_info` (`contact_info_id`, `email`, `phone number`, `address`) VALUES (NULL, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, ci.getEmail());
			pstmt.setInt(2, ci.getPhoneNumber());
			pstmt.setString(3,ci.getAddress());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				ci.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}


	
	public void updateContactinfo(ContactInfo t, ContactInfo ci) {
		try {
			String sql = "UPDATE `khademadb`.`contact_info` SET `email` = ?, `phone number` = ?, `address` = ? WHERE `contact_info`.`contact_info_id` = "
					+ t.getId() + ";";

			PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, ci.getEmail());
			p.setInt(2, ci.getPhoneNumber());
			p.setString(3, ci.getAddress());
			p.executeUpdate();
			p.getGeneratedKeys();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	
	public void delete(ContactInfo t) {
		try {
			connection.createStatement().execute("DELETE FROM `contact_info` WHERE `contact_info_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
}

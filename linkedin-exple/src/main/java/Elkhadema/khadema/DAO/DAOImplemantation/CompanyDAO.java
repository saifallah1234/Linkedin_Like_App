package Elkhadema.khadema.DAO.DAOImplemantation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.util.ConexDB;

public class CompanyDAO {

	private static Connection connection = ConexDB.getInstance();

	public Optional<Company> get(long id) {
		String sql = "SELECT *  FROM `user`,`company` WHERE `user`.`user_id` = " + id
				+ " and `user`.user_id=`company`.user_id";
		Company company = null;
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				company = new Company(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")), rs.getString("userName"),
						rs.getDate("creationdate"), rs.getDate("last_login"),
						new Media(null,(rs.getBytes("photo")), "img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active"), rs.getString("company_name"),
						rs.getString("description"), rs.getString("industry"), rs.getString("website"),
						rs.getInt("company_size"), rs.getString("address"), rs.getString("speciality"));
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return Optional.ofNullable(company);
	}

	public List<Company> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		List<Company> companies = new ArrayList<>();
		String SQL = "SELECT *  FROM `user`,company WHERE `user`.user_id=`company`.user_id ";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				companies.add(new Company(rs.getInt("user_id"), rs.getString("password_encrypted"),
						new ContactInfo(rs.getInt("contact_info_id")), rs.getString("userName"),
						rs.getDate("creationdate"), rs.getDate("last_login"),
						new Media(null, (rs.getBytes("photo")), "img"),
						rs.getBoolean("banned"), rs.getBoolean("is_active"), rs.getString("company_name"),
						rs.getString("description"), rs.getString("industry"), rs.getString("website"),
						rs.getInt("company_size"), rs.getString("address"), rs.getString("speciality")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return companies;
	}

	public void save(Company t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`company` (`user_id`, `company_name`, `description`, `industry`, `website`, `company_size`, `speciality`, `address`) VALUES (?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, t.getId());
			pstmt.setString(2, t.getCompanyName());
			pstmt.setString(3, t.getDescription());
			pstmt.setString(4, t.getIndustry());
			pstmt.setString(5, t.getWebsite());
			pstmt.setLong(6, t.getComapnySize());
			pstmt.setString(7, t.getSpeciality());
			pstmt.setString(8, t.getAddress());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void update(Company t, Company newT) {
		try {
			String sql = "UPDATE `khademadb`.`company` SET `company_name`=?,`description`=?,`industry`=?,`website`=?,`company_size`=?,`speciality`=?,`address`=? WHERE `company`.`user_id` = "
					+ t.getId() + ";";

			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getCompanyName());
			pstmt.setString(2, newT.getDescription());
			pstmt.setString(3, newT.getIndustry());
			pstmt.setString(4, newT.getWebsite());
			pstmt.setLong(5, newT.getComapnySize());
			pstmt.setString(6, newT.getSpeciality());
			pstmt.setString(7, newT.getAddress());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void delete(Company t) {
		try {
			connection.createStatement().execute("DELETE FROM `company` WHERE `user_id` =" + t.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

package Elkhadema.khadema.DAO.DAOImplemantation;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.JobRequest;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.SavedJob;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.ConexDB;

public class JobsDAO{
	private static Connection connection = ConexDB.getInstance();

	//JOB OFFER SECTION
	public List<JobOffre> getAllJobOffresByCompany(Company company) {
		Statement stmt = null;
		ResultSet rs = null;
		List<JobOffre> jobOffres = new ArrayList<>();
		String SQL = "SELECT * FROM `job_offers` WHERE `company_id`="+company.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				jobOffres.add(new JobOffre(rs.getLong("offer_id"),company,rs.getString("summary"),rs.getString("position"),rs.getDouble("pay_range"),rs.getString("employment_type"),rs.getString("location")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return jobOffres;
	}

	public void saveJobOffre(JobOffre t) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`job_offers` (`offer_id`, `company_id`, `position`, `location`, `pay_range`, `summary`, `employment_type`) VALUES (null, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, t.getCompany().getId());
			pstmt.setString(2, t.getPostion());
			pstmt.setString(3, t.getLocation());
			pstmt.setDouble(4, t.getPayRange());
			pstmt.setString(5, t.getSummary());
			pstmt.setString(6, t.getEmploymentType());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateJobOffre(JobOffre t, JobOffre newT) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"UPDATE `job_offers` SET `position`=?,`location`=?,`pay_range`=?,`summary`=?,`employment_type`=? WHERE `offer_id`=?",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getPostion());
			pstmt.setDouble(2, newT.getPayRange());
			pstmt.setString(3, newT.getSummary());
			pstmt.setString(4, newT.getEmploymentType());
			pstmt.setLong(5, t.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteJobOffer(JobOffre t) {
		try {
			connection.createStatement().execute("DELETE FROM `job_offers` WHERE `offer_id`="+t.getId());

		} catch (Exception e) {
			System.out.println(e);

	}
}
	public Optional<JobOffre> getJobOfferByid(long id) {
		Statement stmt = null;
		ResultSet rs = null;
		JobOffre jobOffre=null;
		String SQL = "SELECT * FROM `job_offers` WHERE `offer_id`="+id;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				jobOffre=new JobOffre(id,new Company(rs.getInt("company_id"), null, null),rs.getString("summary"),rs.getString("position"),rs.getDouble("pay_range"),rs.getString("employment_type"),rs.getString("location"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return Optional.ofNullable(jobOffre);
	}
	//JOB REQUEST SECTION
	public void saveJobRequest(JobRequest jr) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`job_request` (`offer_id`, `user_id`, `status`,`pdf`) VALUES (?, ?, ?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, jr.getJobOffre().getId());
			pstmt.setLong(2, jr.getUser().getId());
			pstmt.setString(3, jr.getEtat());
			pstmt.setBlob(4, new ByteArrayInputStream(jr.getPdf()));
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public List<JobRequest> getJobRequestsByUser(User user) {
		Statement stmt = null;
		ResultSet rs = null;
		List<JobRequest> jobRequests = new ArrayList<>();
		String SQL = "SELECT `offer_id`, `user_id`, `status` FROM `job_request` WHERE `user_id`="+user.getId();
		JobRequest jr;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				jr=(new JobRequest(user,new JobOffre(rs.getLong("offer_id")),rs.getString("status")));
				jobRequests.add(jr);
				
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return jobRequests;
	}
	public List<JobRequest> getJobRequestsByCompany(Company company) {
		Statement stmt = null;
		ResultSet rs = null;
		JobRequest jr;
		List<JobRequest> jobRequests = new ArrayList<>();
		String SQL = "SELECT r.`offer_id`,r.`user_id`, r.`status` FROM `job_request` r,job_offers  o WHERE r.`offer_id`=o.offer_id and o.company_id="+company.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				jr=(new JobRequest(new User(rs.getInt("user_id"),null,null),new JobOffre(rs.getLong("offer_id")),rs.getString("status")));
				jobRequests.add(jr);
				
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return jobRequests;
	}
	public byte[] getResumeFromRequestById(JobRequest jr) {
		Statement stmt = null;
		ResultSet rs = null;
		byte[] media=null;
		List<JobRequest> jobRequests = new ArrayList<>();
		String SQL = "SELECT r.`pdf` FROM `job_request` r,job_offers  o WHERE r.`offer_id`=o.offer_id and o.company_id="+jr.getJobOffre().getCompany().getId()+" and `user_id`="+jr.getUser().getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				media=rs.getBytes("pdf");
				
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return media;
	}
	
	public void updateJobRequest(JobRequest t, JobRequest newT) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"UPDATE `job_request` SET`status`=? WHERE `offer_id`=? and `user_id`=?",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newT.getEtat());
			System.out.println(t.getJobOffre().getId());
			pstmt.setLong(2, t.getJobOffre().getId());
			pstmt.setLong(3, t.getUser().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteJobRequest(JobRequest t) {
		try {
			connection.createStatement().execute("DELETE FROM `job_request` WHERE `offer_id`="+t.getJobOffre().getId()+" and `user_id`="+t.getUser().getId());

		} catch (Exception e) {
			System.out.println(e);

	}}
	//SAVED JOB SECTION
	public void saveJob(SavedJob job) {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(
					"INSERT INTO `khademadb`.`saved_jobs` (`offer_id`, `user_id`) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, job.getJobOffre().getId());
			pstmt.setLong(2, job.getUser().getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public List<SavedJob> getSavedJobsByUser(User user) {
		Statement stmt = null;
		ResultSet rs = null;
		List<SavedJob> savedjobs = new ArrayList<>();
		String SQL = "SELECT `offer_id`, `user_id` FROM `saved_jobs` WHERE `user_id`="+user.getId();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				savedjobs.add(new SavedJob(user,new JobOffre(rs.getLong("offer_id"))));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return savedjobs;
	}
	public void deleteSavedJobs(SavedJob sj) {
		try {
			connection.createStatement().execute("DELETE FROM `saved_jobs` WHERE `user_id`="+sj.getUser().getId()+" and `offer_id`="+sj.getJobOffre().getId());

		} catch (Exception e) {
			System.out.println(e);
	}
	}
	public List<JobOffre> getAllJobOffres() {
		Statement stmt = null;
		ResultSet rs = null;
		List<JobOffre> jobOffres = new ArrayList<>();
		String SQL = "SELECT * FROM `job_offers`";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				jobOffres.add(new JobOffre(rs.getLong("offer_id"),new Company(rs.getInt("company_id"), null, null),rs.getString("summary"),rs.getString("position"),rs.getDouble("pay_range"),rs.getString("employment_type"),rs.getString("location")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return jobOffres;
	}
	
}
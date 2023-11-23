package org.kainos.ea.db;

import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.exception.FailedtoDeleteException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDao {

    public List<Job> getAllJobs(Connection connection) throws SQLException {

        // Check database connection
        if (connection == null) {
            System.err.println("Failed to connect to database");
            throw new SQLException("Failed to connect to database");
        }

        // Query the database for all jobs
        String selectQuery = "SELECT job_id, job_title, job_spec_summary, capability.name AS capability, job_URL, job_band_name" +
                " FROM job " +
                "INNER JOIN job_band USING (job_band_id)" +
                "INNER JOIN capability USING (capability_id)";

        PreparedStatement statement = connection.prepareStatement(selectQuery);

        ResultSet results = statement.executeQuery();

        // Create a list of Job objects from the results
        List<Job> allJobs = new ArrayList<>();
        while (results.next()) {
            Job job = new Job(
                    results.getInt("job_id"),
                    results.getString("job_title"),
                    results.getString("capability"),
                    results.getString("job_spec_summary"),
                    results.getString("job_url"),
                    results.getString("job_band_name")
            );
            allJobs.add(job);

        }
        return allJobs;
    }

    public Job getJobById(Connection connection, int id) throws SQLException {
        String SQL = "SELECT job_id, job_title, job_spec_summary, capability.name AS capability, job_URL, job_band_name " +
            "FROM job " +
            "INNER JOIN job_band USING (job_band_id)" +
            "INNER JOIN capability USING (capability_id)" +
            "WHERE job_id = ?";


        // Check database connection
        if (connection == null) {
            System.err.println("Failed to connect to database");
            throw new SQLException("Failed to connect to database");
        }


        if(connection.isClosed()){
            System.err.println("Database connection was closed");
            throw new SQLException("Database connection was closed");
        }

        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setInt(1,id);
        ResultSet results = statement.executeQuery();

        // Create a list of Job objects from the results

        if (results.next()) {
            return new Job(
                    results.getInt("job_id"),
                    results.getString("job_title"),
                    results.getString("capability"),
                    results.getString("job_spec_summary"),
                    results.getString("job_url"),
                    results.getString("job_band_name")
            );
        }

        return null;
    }

    public int createJobRole(JobRequest job, Connection connection) throws SQLException{
        // Check database connection
        if (connection == null) {
            System.err.println("Failed to connect to database");
            throw new SQLException("Failed to connect to database");
        }
        String insertStatement = "INSERT INTO `job` (`job_title`, `job_spec_summary`, `job_url`, `capability_id`, `job_band_id`) VALUES (?,?,?,?,?)";

        PreparedStatement st = connection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, job.getTitle());
        st.setString(2, job.getJobSpec());
        st.setString(3, job.getJobURL());
        st.setInt(4, job.getCapabilityID());
        st.setInt(5, job.getJobBandID());


        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }

    public void deleteJob(Connection connection, int id) throws SQLException {
        String responsibilitiesSQL = "DELETE FROM job_responsibility WHERE job_id = ?";
        String SQL = "DELETE FROM job WHERE job_id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);
        PreparedStatement responsibilitiesStmt = connection.prepareStatement(responsibilitiesSQL);

        responsibilitiesStmt.setInt(1,id);
        statement.setInt(1,id);

        responsibilitiesStmt.executeUpdate();
        statement.executeUpdate();
    }
}

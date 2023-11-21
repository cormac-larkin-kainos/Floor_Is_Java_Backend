package org.kainos.ea.db;

import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.JobRequest;

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

    public int createJobRole(JobRequest job, Connection connection) throws SQLException{
        // Check database connection
        if (connection == null) {
            System.err.println("Failed to connect to database");
            throw new SQLException("Failed to connect to database");
        }
        String insertStatement = "INSERT INTO `job` (`job_title`, `job_spec_summary`, `job_url`, `capability_id`, `job_band_id`) VALUES (?,?,?,?,?)";

        PreparedStatement st = connection.prepareStatement(insertStatement, Statement. RETURN_GENERATED_KEYS);

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

}

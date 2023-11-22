package org.kainos.ea.db;

import org.kainos.ea.cli.Job;
import org.kainos.ea.exception.FailedtoDeleteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void deleteJob(Connection connection, int id) throws SQLException {
        String SQL = "DELETE FROM job WHERE job_id = ?";

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

        statement.executeUpdate();
    }
}

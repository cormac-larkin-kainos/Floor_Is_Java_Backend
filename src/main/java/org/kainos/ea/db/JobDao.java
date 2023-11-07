package org.kainos.ea.db;

import org.kainos.ea.cli.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDao {

    public List<Job> getAllJobs() throws SQLException {

        // Get database connection
        Connection conn = DatabaseConnector.getConnection();
        if(conn == null) {
            System.err.println("Failed to connect to database");
            throw new SQLException("Failed to connect to database");
        }

        String selectQuery = "SELECT job_id, job_title FROM job";

        PreparedStatement statement = conn.prepareStatement(selectQuery);

        ResultSet results = statement.executeQuery();

        // Create a list of jobs from the results
        List<Job> allJobs = new ArrayList<>();
        while(results.next()) {
            Job job = new Job(
                    results.getInt("job_id"),
                    results.getString("job_title")
            );
            allJobs.add(job);

        }

        return allJobs;

    }

}

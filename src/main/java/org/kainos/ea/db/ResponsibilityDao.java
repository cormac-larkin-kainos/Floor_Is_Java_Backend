package org.kainos.ea.db;

import org.kainos.ea.cli.Responsibility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResponsibilityDao {

    //method to check if job exists before getting responsibilities
    public boolean doesJobExist(Connection connection, int jobId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM job WHERE job_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, jobId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

    public List<Responsibility> getResponsibilitiesForJob(Connection connection, int jobId) throws SQLException {
        String selectQuery = "SELECT r.responsibility_id, r.responsibility_desc " +
                "FROM responsibility r " +
                "INNER JOIN job_responsibility jr ON r.responsibility_id = jr.responsibility_id " +
                "WHERE jr.job_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, jobId);

            try (ResultSet results = statement.executeQuery()) {
                List<Responsibility> responsibilities = new ArrayList<>();
                while (results.next()) {
                    Responsibility responsibility = new Responsibility(
                            results.getInt("responsibility_id"),
                            results.getString("responsibility_desc")
                    );
                    responsibilities.add(responsibility);
                }
                return responsibilities;
            }
        }
    }
}



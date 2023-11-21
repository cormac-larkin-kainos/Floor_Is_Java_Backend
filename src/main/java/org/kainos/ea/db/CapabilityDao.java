package org.kainos.ea.db;

import org.kainos.ea.cli.Capability;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CapabilityDao {

    public List<Capability> getAllCapabilities(Connection connection) throws SQLException {

        // Check database connection
        if (connection == null) {
            System.err.println("Failed to connect to database");
            throw new SQLException("Failed to connect to database");
        }

        // Query the database for all capabilities
        String selectQuery = "SELECT capability_id, name FROM capability;";

        PreparedStatement statement = connection.prepareStatement(selectQuery);

        ResultSet results = statement.executeQuery();

        // Create a list of Capability objects from the results
        List<Capability> allCapabilities = new ArrayList<>();
        while (results.next()) {
            Capability capability = new Capability(
                    results.getInt("capability_id"),
                    results.getString("name")
            );
            allCapabilities.add(capability);

        }

        return allCapabilities;

    }

}

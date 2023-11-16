package org.kainos.ea.db;

import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.cli.Login;
import org.kainos.ea.exception.FailedToGenerateTokenException;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthDao {

    private final DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean validLogin(Login login) throws SQLException {

        Connection conn = databaseConnector.getConnection();

        String sqlQuery = "SELECT password FROM `user` WHERE username = ?";
        PreparedStatement st = conn.prepareStatement(sqlQuery);

        st.setString(1, login.getUsername());

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return rs.getString("password").equals(login.getPassword());
        }
        return false;
    }
}


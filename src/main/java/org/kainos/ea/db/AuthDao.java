package org.kainos.ea.db;

import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Login;
import org.kainos.ea.exception.AuthenticationException;
import org.kainos.ea.exception.FailedToGenerateTokenException;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class AuthDao {

    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean validLogin(Login login) {
        try {
            Connection conn = databaseConnector.getConnection();
            String SQL = "SELECT password FROM `user` WHERE username = ?";
            PreparedStatement st = conn.prepareStatement(SQL);

            st.setString(1, login.getUsername());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return rs.getString("password").equals(login.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return false;
    }

    public String generateToken(String username) throws SQLException {

            String token = UUID.randomUUID().toString();
            Date expiry = DateUtils.addHours(new Date(), 8);

            Connection c = databaseConnector.getConnection();

            String insertStatement = "INSERT INTO Token(username,token, expiry) VALUES(?,?,?)";

            PreparedStatement st = c.prepareStatement(insertStatement);

            st.setString(1, username);
            st.setString(2, token);
            st.setTimestamp(3, new java.sql.Timestamp(expiry.getTime()));

            st.executeUpdate();

            return token;

        }
    }


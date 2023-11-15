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

    //generate JWT token method needed here
//    public String generateToken(String username) throws SQLException {
//            // generate JWT token not a string
//        String token = UUID.randomUUID().toString();
//        Date expiry = DateUtils.addHours(new Date(), 8);
//
//        Connection c = databaseConnector.getConnection();
//
//        String insertStatement = "INSERT INTO token(username, token, expiry) VALUES(?,?,?)";
//
//        PreparedStatement st = c.prepareStatement(insertStatement);
//
//        st.setString(1, username);
//        st.setString(2, token);
//        st.setTimestamp(3, new java.sql.Timestamp(expiry.getTime()));
//
//        st.executeUpdate();
//
//        return token;
//    }
    public String generateToken(String username) throws FailedToGenerateTokenException {
        try {
            JwtGenerator generator = new JwtGenerator();

            Map<String, String> claims = new HashMap<>();

            claims.put("username", username);

            String token = generator.generateJwt(claims);
            System.out.println(token);
            return token;
        } catch (Exception e) {
            throw new FailedToGenerateTokenException();
        }
    }
}


package org.kainos.ea.db;

import org.kainos.ea.cli.UserRole;
import org.kainos.ea.exception.AuthenticationException;
import org.kainos.ea.cli.HashedPassword;

import java.sql.*;

public class AuthDao implements IAuthSource {

    private final DatabaseConnector databaseConnector = new DatabaseConnector();

    @Override
    public HashedPassword getPasswordForUser(String username) throws AuthenticationException {
        String SQL = "SELECT password_hash, password_salt, password_hash_iterations FROM user WHERE email = ?";

        try {
            PreparedStatement st = databaseConnector.getConnection().prepareStatement(SQL);
            st.setString(1,username);

            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return new HashedPassword(rs.getBytes("password_hash"), rs.getInt("password_hash_iterations"), rs.getBytes("password_salt"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AuthenticationException("Could not retrieve info for user");
        }

        return null;
    }

    @Override
    public UserRole getRoleForUser(String username) throws AuthenticationException {
        String SQL = "SELECT name, role_id FROM role JOIN user USING(role_id) WHERE email = ?";

        try {
            PreparedStatement st = databaseConnector.getConnection().prepareStatement(SQL);
            st.setString(1,username);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return UserRole.valueOf(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AuthenticationException("Could not retrieve info for user");
        }

        return null;
    }
}


package com.futurecapsule.dao;

import com.futurecapsule.model.User;
import com.futurecapsule.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User findUserByEmail(String email) {

        String sql = """
            SELECT id, name, email, password
            FROM users
            WHERE email = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find user."
            );

            e.printStackTrace();
        }

        return null;
    }


    /*
     * Find a user using their ID.
     *
     * This is used by the capsule delivery system
     * to find the email address of the capsule owner.
     */
    public User findUserById(int userId) {

        String sql = """
            SELECT id, name, email, password
            FROM users
            WHERE id = ?
            """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find user by ID."
            );

            e.printStackTrace();
        }

        return null;
    }


    public boolean createUser(
            String name,
            String email,
            String password) {

        String sql = """
            INSERT INTO users (name, email, password)
            VALUES (?, ?, ?)
            """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);

            statement.setString(2, email);

            statement.setString(3, password);

            int rowsAffected =
                    statement.executeUpdate();

            System.out.println(
                    "Rows inserted: " + rowsAffected
            );

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Failed to create user."
            );

            e.printStackTrace();

            return false;
        }
    }
}
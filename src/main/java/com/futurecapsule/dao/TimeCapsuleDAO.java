package com.futurecapsule.dao;

import com.futurecapsule.model.TimeCapsule;
import com.futurecapsule.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TimeCapsuleDAO {

    // =========================================================
    // DELETE CAPSULE
    // =========================================================

    public boolean deleteCapsule(
            int capsuleId,
            int userId) {

        String sql = """
                DELETE FROM time_capsules
                WHERE id = ?
                  AND user_id = ?
                  AND status = 'PENDING'
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, capsuleId);
            statement.setInt(2, userId);

            int rowsAffected =
                    statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Failed to delete capsule."
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // UPDATE CAPSULE
    // =========================================================

    public boolean updateCapsule(
            int capsuleId,
            int userId,
            String title,
            String message,
            LocalDateTime deliveryDate) {

        String sql = """
                UPDATE time_capsules
                SET title = ?,
                    message = ?,
                    delivery_date = ?
                WHERE id = ?
                  AND user_id = ?
                  AND status = 'PENDING'
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, title);

            statement.setString(2, message);

            /*
             * LocalDateTime represents the user's wall-clock time.
             * Convert directly to Timestamp without applying another
             * timezone conversion.
             */
            statement.setTimestamp(
                    3,
                    java.sql.Timestamp.valueOf(deliveryDate)
            );

            statement.setInt(4, capsuleId);

            statement.setInt(5, userId);

            int rowsAffected =
                    statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Failed to update capsule."
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // FIND CAPSULE BY ID
    // =========================================================

    public TimeCapsule findCapsuleById(
            int capsuleId) {

        String sql = """
                SELECT id,
                       user_id,
                       title,
                       message,
                       delivery_date,
                       status,
                       created_at
                FROM time_capsules
                WHERE id = ?
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, capsuleId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new TimeCapsule(
                            resultSet.getInt("id"),

                            resultSet.getInt("user_id"),

                            resultSet.getString("title"),

                            resultSet.getString("message"),

                            resultSet.getTimestamp(
                                    "delivery_date"
                            ).toLocalDateTime(),

                            resultSet.getString("status"),

                            resultSet.getTimestamp(
                                    "created_at"
                            ).toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find capsule."
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // CREATE CAPSULE
    // =========================================================

    public int createCapsule(TimeCapsule capsule) {

        String sql = """
            INSERT INTO time_capsules
            (user_id, title, message, delivery_date, status)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, capsule.getUserId());

            statement.setString(2, capsule.getTitle());

            statement.setString(3, capsule.getMessage());

            // Preserve the user's selected wall-clock time exactly.
            statement.setObject(
                    4,
                    capsule.getDeliveryDate()
            );

            statement.setString(5, capsule.getStatus());

            System.out.println(
                    "CREATE CAPSULE DEBUG: deliveryDate = "
                            + capsule.getDeliveryDate()
            );

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected == 0) {
                return -1;
            }

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to create time capsule."
            );

            e.printStackTrace();
        }

        return -1;
    }

    // =========================================================
    // FIND CAPSULES BY USER
    // =========================================================

    public List<TimeCapsule> findCapsulesByUser(
            int userId) {

        String sql = """
                SELECT id,
                       user_id,
                       title,
                       message,
                       delivery_date,
                       status,
                       created_at
                FROM time_capsules
                WHERE user_id = ?
                ORDER BY delivery_date ASC
                """;

        List<TimeCapsule> capsules =
                new ArrayList<>();

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    TimeCapsule capsule =
                            new TimeCapsule(

                                    resultSet.getInt(
                                            "id"
                                    ),

                                    resultSet.getInt(
                                            "user_id"
                                    ),

                                    resultSet.getString(
                                            "title"
                                    ),

                                    resultSet.getString(
                                            "message"
                                    ),

                                    resultSet.getTimestamp(
                                            "delivery_date"
                                    ).toLocalDateTime(),

                                    resultSet.getString(
                                            "status"
                                    ),

                                    resultSet.getTimestamp(
                                            "created_at"
                                    ).toLocalDateTime()
                            );

                    capsules.add(capsule);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find capsules."
            );

            e.printStackTrace();
        }

        return capsules;
    }


    // =========================================================
    // FIND DUE CAPSULES
    // =========================================================

    public List<TimeCapsule> findDueCapsules() {

        String sql = """
                SELECT id,
                       user_id,
                       title,
                       message,
                       delivery_date,
                       status,
                       created_at
                FROM time_capsules
                WHERE status = 'PENDING'
                  AND delivery_date <= NOW()
                ORDER BY delivery_date ASC
                """;

        List<TimeCapsule> capsules =
                new ArrayList<>();

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                TimeCapsule capsule =
                        new TimeCapsule(

                                resultSet.getInt(
                                        "id"
                                ),

                                resultSet.getInt(
                                        "user_id"
                                ),

                                resultSet.getString(
                                        "title"
                                ),

                                resultSet.getString(
                                        "message"
                                ),

                                resultSet.getTimestamp(
                                        "delivery_date"
                                ).toLocalDateTime(),

                                resultSet.getString(
                                        "status"
                                ),

                                resultSet.getTimestamp(
                                        "created_at"
                                ).toLocalDateTime()
                        );

                capsules.add(capsule);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find due capsules."
            );

            e.printStackTrace();
        }

        return capsules;
    }


    // =========================================================
    // MARK CAPSULE AS DELIVERED
    // =========================================================

    public boolean markAsDelivered(
            int capsuleId) {

        String sql = """
                UPDATE time_capsules
                SET status = 'DELIVERED'
                WHERE id = ?
                  AND status = 'PENDING'
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    capsuleId
            );

            int rowsAffected =
                    statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Failed to mark capsule as delivered."
            );

            e.printStackTrace();

            return false;
        }
    }
}


package com.futurecapsule.dao;

import com.futurecapsule.model.TimeCapsule;
import com.futurecapsule.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TimeCapsuleDAO {

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");


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

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, capsuleId);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

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

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, title);
            statement.setString(2, message);

            // delivery_date is a wall-clock value in Asia/Kolkata.
            // Keep it as LocalDateTime; do not convert it to an Instant/UTC.
            statement.setObject(
                    3,
                    deliveryDate
            );

            statement.setInt(4, capsuleId);
            statement.setInt(5, userId);

            return statement.executeUpdate() > 0;

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

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, capsuleId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return mapCapsule(resultSet);
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

    public int createCapsule(
            TimeCapsule capsule) {

        String sql = """
                INSERT INTO time_capsules
                (
                    user_id,
                    title,
                    message,
                    delivery_date,
                    status
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    capsule.getUserId()
            );

            statement.setString(
                    2,
                    capsule.getTitle()
            );

            statement.setString(
                    3,
                    capsule.getMessage()
            );

            // Store the exact date/time selected by the user.
            // The application uses Asia/Kolkata consistently, so no UTC
            // conversion is performed here.
            statement.setObject(
                    4,
                    capsule.getDeliveryDate()
            );

            statement.setString(
                    5,
                    capsule.getStatus()
            );

            System.out.println(
                    "CREATE CAPSULE:"
            );

            System.out.println(
                    "User IST = "
                            + capsule.getDeliveryDate()
            );

            System.out.println(
                    "Stored IST = "
                            + capsule.getDeliveryDate()
            );

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected == 0) {
                return -1;
            }

            try (
                    ResultSet generatedKeys =
                            statement.getGeneratedKeys()
            ) {

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

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    capsules.add(
                            mapCapsule(resultSet)
                    );
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

        // Compare the stored IST wall-clock value with the current IST
        // wall-clock value supplied by Java. This avoids any dependency on
        // the Railway/MySQL server timezone.
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
                  AND delivery_date <= ?
                ORDER BY delivery_date ASC
                """;

        List<TimeCapsule> capsules =
                new ArrayList<>();

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setObject(
                    1,
                    LocalDateTime.now(IST)
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    capsules.add(
                            mapCapsule(resultSet)
                    );
                }

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

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    capsuleId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Failed to mark capsule as delivered."
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // MAP DATABASE ROW -> TIME CAPSULE
    // =========================================================

    private TimeCapsule mapCapsule(
            ResultSet resultSet)
            throws SQLException {

        /*
         * Both date/time fields are read as LocalDateTime so the exact
         * database wall-clock value is preserved. No UTC/IST conversion
         * is performed here.
         */
        LocalDateTime deliveryDate =
                resultSet.getObject(
                        "delivery_date",
                        LocalDateTime.class
                );

        LocalDateTime createdAt =
                resultSet.getObject(
                        "created_at",
                        LocalDateTime.class
                );

        return new TimeCapsule(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("title"),
                resultSet.getString("message"),
                deliveryDate,
                resultSet.getString("status"),
                createdAt
        );
    }
}
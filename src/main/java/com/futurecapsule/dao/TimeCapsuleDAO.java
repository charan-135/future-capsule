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
             * IMPORTANT:
             *
             * delivery_date is a MySQL DATETIME.
             *
             * LocalDateTime represents the exact wall-clock
             * time selected by the user.
             *
             * Use setObject(LocalDateTime) directly.
             * Do NOT convert through java.sql.Timestamp.
             */
            statement.setObject(
                    3,
                    deliveryDate
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

                    /*
                     * Read MySQL DATETIME directly as
                     * LocalDateTime.
                     *
                     * This prevents JDBC timezone conversion.
                     */
                    LocalDateTime deliveryDate =
                            resultSet.getObject(
                                    "delivery_date",
                                    LocalDateTime.class
                            );

                    return new TimeCapsule(
                            resultSet.getInt("id"),

                            resultSet.getInt("user_id"),

                            resultSet.getString("title"),

                            resultSet.getString("message"),

                            deliveryDate,

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

    public int createCapsule(
            TimeCapsule capsule) {

        String sql = """
                INSERT INTO time_capsules
                (user_id, title, message, delivery_date, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

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

            /*
             * IMPORTANT:
             *
             * MySQL column:
             *
             *     delivery_date DATETIME
             *
             * LocalDateTime is intentionally written directly.
             *
             * Example:
             *
             *     2026-10-04T15:41
             *
             * becomes:
             *
             *     2026-10-04 15:41:00
             *
             * No timezone conversion is performed.
             */
            LocalDateTime deliveryDate =
                    capsule.getDeliveryDate();

            System.out.println(
                    "CREATE CAPSULE DEBUG: deliveryDate = "
                            + deliveryDate
            );

            statement.setObject(
                    4,
                    deliveryDate
            );

            statement.setString(
                    5,
                    capsule.getStatus()
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

                    /*
                     * Read DATETIME directly as LocalDateTime.
                     * No timezone conversion.
                     */
                    LocalDateTime deliveryDate =
                            resultSet.getObject(
                                    "delivery_date",
                                    LocalDateTime.class
                            );

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

                                    deliveryDate,

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

        /*
         * delivery_date is stored as IST wall-clock time.
         *
         * Railway/MySQL may use UTC as its system timezone.
         *
         * Therefore do NOT compare directly with NOW().
         *
         * Convert the current UTC time to IST explicitly:
         *
         * UTC + 05:30
         */
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
                  AND delivery_date <=
                      (UTC_TIMESTAMP() + INTERVAL 5 HOUR + INTERVAL 30 MINUTE)
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

                /*
                 * Read DATETIME directly as LocalDateTime.
                 */
                LocalDateTime deliveryDate =
                        resultSet.getObject(
                                "delivery_date",
                                LocalDateTime.class
                        );

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

                                deliveryDate,

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
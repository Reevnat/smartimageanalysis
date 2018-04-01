package dao;

import entities.Result;
import entities.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public UserDao(final String url) throws SQLException{
        dataSource.setUrl(url);
        final String createTableSql = "CREATE TABLE IF NOT EXISTS Users ( id INT NOT NULL AUTO_INCREMENT, "
                + "email NVARCHAR(255), password NVARCHAR(255),PRIMARY KEY (id))";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long create(User user) throws SQLException {
        final String createBookString = "INSERT INTO Users "
                + "(email, password) "
                + "VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement userInsertStatement = conn.prepareStatement(createBookString,
                     Statement.RETURN_GENERATED_KEYS)) {
            userInsertStatement.setString(1, user.getEmail());

            userInsertStatement.executeUpdate();
            try (ResultSet keys = userInsertStatement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    public void deleteUser(Long userId) throws SQLException {
        final String deleteUserQuery = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement deleteStatement = conn.prepareStatement(deleteUserQuery)) {
            deleteStatement.setLong(1, userId);
            deleteStatement.executeUpdate();
        }
    }

    public void updateUser(Long userId, String password, boolean isAdmin) throws SQLException{
        final String updateUserQuery = "UPDATE Users SET password = ?, isAdmin = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement updateUserStatement = conn.prepareStatement(updateUserQuery)) {
            updateUserStatement.setString(1, password);
            updateUserStatement.setBoolean(2, isAdmin);
            updateUserStatement.setLong(3, userId);
            updateUserStatement.executeUpdate();
        }
    }

    public Result<User> listUses(String cursor) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String listBooksString = "SELECT id, email, password, isAdmin FROM Users ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listBooksStmt = conn.prepareStatement(listBooksString)) {
            List<User> results = new ArrayList<>();
            try (ResultSet rs = listBooksStmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId((rs.getInt("id")));
                    user.setEmail((rs.getString("email")));
                    user.setPassword((rs.getString("password")));
                    user.setAdmin(rs.getBoolean("isAdmin"));
                    results.add(user);
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT FOUND_ROWS()")) {
                int totalNumRows = 0;
                if (rs.next()) {
                    totalNumRows = rs.getInt(1);
                }
                if (totalNumRows > offset + 10) {
                    return new Result<>(results, Integer.toString(offset + 10));
                } else {
                    return new Result<>(results);
                }
            }
        }
    }

    public User getUser (long userId) throws SQLException{
        User entity = null;
        final String query = "SELECT id, email, password, isAdmin FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement findStatement = conn.prepareStatement(query)) {
            findStatement.setLong(1, userId);
            try (ResultSet rs = findStatement.executeQuery()) {
                if (rs.next()) {
                    entity = new User();
                    entity.setId((rs.getInt("id")));
                    entity.setEmail(rs.getString("email"));
                    entity.setPassword((rs.getString("password")));
                    entity.setAdmin(rs.getBoolean("isAdmin"));
                }
            }
        }
        return  entity;
    }
}

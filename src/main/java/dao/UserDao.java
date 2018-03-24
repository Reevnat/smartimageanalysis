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
        final String createTableSql = "CREATE TABLE IF NOT EXISTS Users ( id INT NOT NULL, "
                + "email NVARCHAR(255), PRIMARY KEY (id))";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long createBook(User user) throws SQLException {
        final String createBookString = "INSERT INTO Users "
                + "(id, email) "
                + "VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement userInsertStatement = conn.prepareStatement(createBookString,
                     Statement.RETURN_GENERATED_KEYS)) {
            userInsertStatement.setInt(1, user.getId());
            userInsertStatement.setString(2, user.getEmail());

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

    public Result<User> listUses(String cursor) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String listBooksString = "SELECT id, email FROM Users ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listBooksStmt = conn.prepareStatement(listBooksString)) {
            List<User> results = new ArrayList<>();
            try (ResultSet rs = listBooksStmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId((rs.getInt("id")));
                    user.setEmail((rs.getString("email")));
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
}

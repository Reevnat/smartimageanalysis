package dao;

import entities.Category;
import entities.Result;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public CategoryDao(final String url) throws SQLException
    {
        dataSource.setUrl(url);
        final String createTableSql = "CREATE TABLE IF NOT EXISTS Categories ( id INT NOT NULL AUTO_INCREMENT, "
                + "name NVARCHAR(255),  PRIMARY KEY (id))";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long create(Category image) throws SQLException {
        final String query = "INSERT INTO Categories "
                + "(name) "
                + "VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement insertStatement = conn.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, image.getName());

            insertStatement.executeUpdate();
            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    public void delete(Long imageId) throws SQLException {
        final String query = "DELETE FROM Categories WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement deleteStatement = conn.prepareStatement(query)) {
            deleteStatement.setLong(1, imageId);
            deleteStatement.executeUpdate();
        }
    }

    public Category get(Long imageId) throws  SQLException{
        Category entity = null;
        final String query = "SELECT id, name FROM Categories WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement findStatement = conn.prepareStatement(query)) {
            findStatement.setLong(1, imageId);
            try (ResultSet rs = findStatement.executeQuery()) {
                if (rs.next()) {
                    entity = new Category();
                    entity.setId((rs.getInt("id")));
                    entity.setName((rs.getString("name")));
                }
            }
        }

        return  entity;
    }

    public Result<Category> list(String cursor) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String query = "SELECT id, name FROM Categories ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            List<Category> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    Category entity = new Category();
                    entity.setId((rs.getInt("id")));
                    entity.setName((rs.getString("name")));
                    results.add(entity);
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


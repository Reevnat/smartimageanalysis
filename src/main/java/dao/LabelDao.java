package dao;

import entities.Category;
import entities.Label;
import entities.Result;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public LabelDao(final String url) throws SQLException
    {
        dataSource.setUrl(url);
        final String createTableSql = "CREATE TABLE IF NOT EXISTS Labels ( id INT NOT NULL AUTO_INCREMENT, "
                + "description NVARCHAR(255), threshold double, categoryId int,  PRIMARY KEY (id), CONSTRAINT `FK->categoryId->id` FOREIGN KEY (`categoryId`)"
                + " REFERENCES `smia`.`Categories` (`id`)"
                + " ON DELETE CASCADE"
                + " ON UPDATE CASCADE)";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long create(Label label) throws SQLException {
        final String query = "INSERT INTO Labels "
                + "(description, threshold, categoryId) "
                + "VALUES (?,?,?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement insertStatement = conn.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, label.getDescription());
            insertStatement.setDouble(2, label.getThreshold());
            insertStatement.setInt(3, label.getCategoryId());

            insertStatement.executeUpdate();
            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    public void delete(Long imageId) throws SQLException {
        final String query = "DELETE FROM Labels WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement deleteStatement = conn.prepareStatement(query)) {
            deleteStatement.setLong(1, imageId);
            deleteStatement.executeUpdate();
        }
    }

    public Result<Label> list(String cursor, Category category) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String query = "SELECT id, description, threshold, categoryId FROM Labels WHERE categoryId = ? ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            listStatement.setInt(1,category.getId());
            List<Label> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    Label entity = new Label();
                    entity.setId((rs.getInt("id")));
                    entity.setDescription((rs.getString("description")));
                    entity.setThreshold(rs.getDouble("threshold"));
                    entity.setCategoryId(rs.getInt("categoryId"));
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

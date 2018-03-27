package dao;

import entities.Image;
import entities.LabelAnnotation;
import entities.Result;
import entities.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelAnnotationDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public LabelAnnotationDao(final String url) throws SQLException {
        dataSource.setUrl(url);
        final String createTableSql = "CREATE TABLE IF NOT EXISTS LabelAnnotations ( id INT NOT NULL AUTO_INCREMENT, "
                + "description NVARCHAR(255), score double, imageId int, PRIMARY KEY (id), CONSTRAINT `FK->imageId->id` FOREIGN KEY (`imageId`)" +
                " REFERENCES `smia`.`Images` (`id`)" +
                " ON DELETE CASCADE" +
                " ON UPDATE CASCADE)";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long create(LabelAnnotation entity) throws SQLException {
        final String query = "INSERT INTO LabelAnnotations "
                + "(description, score, imageId) "
                + "VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement insertStatement = conn.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, entity.getDescription());
            insertStatement.setDouble(2, entity.getScore());
            insertStatement.setInt(3, entity.getImageId());

            insertStatement.executeUpdate();
            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    public Result<LabelAnnotation> list(String cursor, Image image) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String query = "SELECT id, description, score, imageId FROM LabelAnnotations" +
                " WHERE imageId = ? ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            listStatement.setInt(1,image.getId());
            List<LabelAnnotation> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    LabelAnnotation entity = new LabelAnnotation();
                    entity.setId(rs.getInt("id"));
                    entity.setDescription((rs.getString("description")));
                    entity.setScore(rs.getDouble("score"));
                    entity.setImageId(rs.getInt("imageId"));
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

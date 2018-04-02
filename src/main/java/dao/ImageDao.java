package dao;

import entities.Image;
import entities.Result;
import entities.SimilarityScore;
import entities.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public ImageDao(final String url) throws SQLException
    {
        dataSource.setUrl(url);
        final String createTableSql = "CREATE TABLE IF NOT EXISTS Images ( id INT NOT NULL AUTO_INCREMENT, "
                + "url NVARCHAR(255), uploadedById int,  PRIMARY KEY (id), CONSTRAINT `FK->uploadedById->id` FOREIGN KEY (`uploadedById`)"
                + " REFERENCES `smia`.`Users` (`id`)"
                + " ON DELETE CASCADE"
                + " ON UPDATE CASCADE)";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long createImage(Image image, User uploadedBy) throws SQLException {
        final String query = "INSERT INTO Images "
                + "(url, uploadedById) "
                + "VALUES (?,?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement insertStatement = conn.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, image.getUrl());
            insertStatement.setInt(2, uploadedBy.getId());

            insertStatement.executeUpdate();
            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    public Result<Image> listImages(String cursor, User uploadedBy) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String query = "SELECT id, url, uploadedById FROM Images WHERE uploadedById = ? ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            listStatement.setInt(1, uploadedBy.getId());
            List<Image> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    Image entity = new Image();
                    entity.setId((rs.getInt("id")));
                    entity.setUrl((rs.getString("url")));
                    entity.setUploadedById(rs.getInt("uploadedById"));
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

    public void deleteImage(Long imageId) throws SQLException {
        final String query = "DELETE FROM Images WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement deleteStatement = conn.prepareStatement(query)) {
            deleteStatement.setLong(1, imageId);
            deleteStatement.executeUpdate();
        }
    }

    public Image getImage(Long imageId) throws  SQLException{
        Image entity = null;
        final String query = "SELECT id, url, uploadedById FROM Images WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement findStatement = conn.prepareStatement(query)) {
            findStatement.setLong(1, imageId);
            try (ResultSet rs = findStatement.executeQuery()) {
                if (rs.next()) {
                    entity = new Image();
                    entity.setId((rs.getInt("id")));
                    entity.setUrl((rs.getString("url")));
                    entity.setUploadedById(rs.getInt("uploadedById"));
                }
            }
        }

        return  entity;
    }

    public Result<SimilarityScore> similarityScoreList(Image image) throws SQLException {
        final  String query = "SELECT A.id AS categoryId, A.name AS category,  Count(C.score >= B.threshold)/Count(A.id) AS score" +
                " FROM (Categories AS A" +
                " INNER JOIN Labels AS B" +
                " ON A.id = B.categoryId)" +
                " LEFT JOIN LabelAnnotations AS C" +
                " ON (B.description = C.description AND C.imageId = ? )" +
                " GROUP BY A.id, A.name" +
                " HAVING score >= 0.5" +
                " ORDER BY score DESC" +
                " LIMIT 5";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement1 = conn.prepareStatement(query)) {
            listStatement1.setInt(1, image.getId());
            List<SimilarityScore> results = new ArrayList<>();
            try (ResultSet rs = listStatement1.executeQuery()) {

                while (rs.next()) {
                    SimilarityScore entity = new SimilarityScore();
                    entity.setCategoryId((rs.getInt("categoryId")));
                    entity.setCategory((rs.getString("category")));
                    entity.setScore(rs.getDouble("score"));
                    results.add(entity);
                }
            }

            return new Result<>(results);
        }
    }

    public Result<Image> listImages() throws SQLException {

        final String query = "SELECT id, url, uploadedById FROM Images ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            List<Image> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    Image entity = new Image();
                    entity.setId((rs.getInt("id")));
                    entity.setUrl((rs.getString("url")));
                    entity.setUploadedById(rs.getInt("uploadedById"));
                    results.add(entity);
                }
            }

            return  new Result<>(results);
        }
    }
}

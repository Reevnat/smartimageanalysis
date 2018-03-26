package dao;

import entities.Image;
import entities.Result;
import entities.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public ImageDao(final String url) throws SQLException
    {
        dataSource.setUrl(url);
        final String createTableSql = "CREATE TABLE IF NOT EXISTS Images ( id INT NOT NULL AUTO_INCREMENT, "
                + "url NVARCHAR(255),  PRIMARY KEY (id))";
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate(createTableSql);
        }
    }

    public Long createImage(Image image) throws SQLException {
        final String query = "INSERT INTO Images "
                + "(url) "
                + "VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement insertStatement = conn.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, image.getUrl());

            insertStatement.executeUpdate();
            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    public Result<Image> listImages(String cursor) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }
        final String query = "SELECT id, url FROM Images ORDER BY id ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            List<Image> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    Image entity = new Image();
                    entity.setId((rs.getInt("id")));
                    entity.setUrl((rs.getString("url")));
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
        final String query = "SELECT id, url FROM Images WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement findStatement = conn.prepareStatement(query)) {
            findStatement.setLong(1, imageId);
            try (ResultSet rs = findStatement.executeQuery()) {
                if (rs.next()) {
                    entity = new Image();
                    entity.setId((rs.getInt("id")));
                    entity.setUrl((rs.getString("url")));
                }
            }
        }

        return  entity;
    }
}

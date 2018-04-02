package dao;

import entities.Result;
import entities.SearchResult;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchDao {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public SearchDao(final String url) throws SQLException
    {
        dataSource.setUrl(url);
    }

    public Result<SearchResult> search(int categoryId, String keywords, String cursor, int itemsToFetch) throws SQLException {
        int offset = 0;
        if (cursor != null && !cursor.equals("")) {
            offset = Integer.parseInt(cursor);
        }

        String[] itemsToSearch = (keywords == null || keywords.isEmpty()) ? new String[0] : keywords.split(" ");

        String descriptionFilter = "";
        if(itemsToSearch.length > 0 ){
            descriptionFilter = "B.description IN (";
            for(int i=0;i<itemsToSearch.length;i++){
                descriptionFilter += "?,";
            }
            //remove extra comma at the end
            descriptionFilter = descriptionFilter.substring(0,descriptionFilter.length()-1);
            descriptionFilter += ")";
        }
        else{
            descriptionFilter = "1 = 1";
        }


        final String query = "SELECT A.id,url, email AS uploadedBy FROM Images as A" +
                " INNER JOIN Labelannotations as B" +
                " ON A.id = B.imageId" +
                " INNER JOIN Users AS C" +
                " ON A.uploadedById = C.id"+
                " WHERE (" + descriptionFilter + ")" +
                " ORDER BY A.id ASC" +
                " LIMIT ? OFFSET ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement listStatement = conn.prepareStatement(query)) {
            int parameterCount = 0;
            for(int i=0;i<itemsToSearch.length;i++){
                parameterCount += 1;
                listStatement.setString(parameterCount, itemsToSearch[i]);
            }
            listStatement.setInt(parameterCount + 1, itemsToFetch);
            listStatement.setInt(parameterCount + 2, offset);
            List<SearchResult> results = new ArrayList<>();
            try (ResultSet rs = listStatement.executeQuery()) {
                while (rs.next()) {
                    SearchResult entity = new SearchResult();
                    entity.setImageId(rs.getInt("id"));
                    entity.setImageUrl((rs.getString("url")));
                    entity.setUploadedBy(rs.getString("uploadedBy"));
                    results.add(entity);
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT FOUND_ROWS()")) {
                int totalNumRows = 0;
                if (rs.next()) {
                    totalNumRows = rs.getInt(1);
                }
                if (totalNumRows > offset + itemsToFetch) {
                    return new Result<>(results, Integer.toString(offset + itemsToFetch));
                } else {
                    return new Result<>(results);
                }
            }
        }
    }
}

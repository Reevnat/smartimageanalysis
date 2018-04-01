package entities;

import java.util.List;

public class Image {

    int id;
    String url;
    String category;
    int uploadedById;
    List<LabelAnnotation> annotations;
    List<SimilarityScore> similarityScores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<LabelAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<LabelAnnotation> annotations) {
        this.annotations = annotations;
    }

    public int getUploadedById() {
        return uploadedById;
    }

    public void setUploadedById(int uploadedById) {
        this.uploadedById = uploadedById;
    }

    public List<SimilarityScore> getSimilarityScores() {
        return similarityScores;
    }

    public void setSimilarityScores(List<SimilarityScore> similarityScores) {
        this.similarityScores = similarityScores;
    }
}

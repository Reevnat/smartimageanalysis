package entities;

import java.util.List;

public class SearchResult {
    int imageId;
    String imageUrl;
    String category;
    String uploadedBy;
    List<LabelAnnotation> annotations;
    List<SimilarityScore> similarityScores;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public List<LabelAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<LabelAnnotation> annotations) {
        this.annotations = annotations;
    }

    public List<SimilarityScore> getSimilarityScores() {
        return similarityScores;
    }

    public void setSimilarityScores(List<SimilarityScore> similarityScores) {
        this.similarityScores = similarityScores;
    }
}

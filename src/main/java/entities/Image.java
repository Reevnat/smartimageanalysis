package entities;

import java.util.List;

public class Image {

    int id;
    String url;
    String category;
    List<LabelAnnotation> annotations;

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
}

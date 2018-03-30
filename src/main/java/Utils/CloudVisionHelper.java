package Utils;

import com.google.cloud.vision.v1.*;
import entities.LabelAnnotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CloudVisionHelper {

    public static List<LabelAnnotation> detectLabels(String gcsPath) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        List<LabelAnnotation> result = new ArrayList<>();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    return Collections.emptyList();
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {

                    LabelAnnotation label = new LabelAnnotation();
                    label.setDescription(annotation.getDescription());
                    label.setScore(annotation.getScore());

                    result.add(label);
                }
            }
        }

        return result;
    }
}

package Utils;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.Descriptors;
import entities.LabelAnnotation;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CloudVisionHelper {
    public static List<LabelAnnotation> detectLabelsGcs(String gcsPath, PrintStream out, int imageId) throws Exception,
            IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        List<LabelAnnotation> result = new ArrayList<>();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return Collections.emptyList();
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {

                    Map<Descriptors.FieldDescriptor,Object> fields = annotation.getAllFields();
                    LabelAnnotation label = new LabelAnnotation();
                    label.setDescription(fields.get("Description").toString());
                    label.setScore((double)fields.get("score"));
                    label.setImageId(imageId);

                    result.add(label);
                }
            }
        }

        return result;
    }
}

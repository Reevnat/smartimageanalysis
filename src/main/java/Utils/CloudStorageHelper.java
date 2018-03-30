package Utils;

import com.google.cloud.storage.*;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.servlet.ServletException;

public class CloudStorageHelper {

    private static Storage storage = null;

    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadFile(MultipartFile filePart, final String bucketName) throws IOException {
        final String fileName = "image-" + UUID.randomUUID();

        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, fileName)
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                                .build(),
                        filePart.getInputStream());

        return blobInfo.getMediaLink();
    }

    public String getImageUrl(MultipartFile file, final String bucket) throws IOException, ServletException {
        final String fileName = file.getName();
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
            for (String s : allowedExt) {
                if (extension.equals(s)) {
                    return this.uploadFile(file, bucket);
                }
            }
            throw new ServletException("file must be an image");
        }

        return fileName;
    }

    public void deleteImageUrl(String path, final String bucket){
        BlobId blobId = BlobId.of(bucket, path);
        storage.delete(blobId);
    }

    public String getGcsPath(String path, final String bucket){
        BlobId blobId = BlobId.of(bucket, path);
        URI uri = URI.create(blobId.getName());
        String filename = Paths.get(uri.getPath()).getFileName().toString();

        return "gs://" + blobId.getBucket() + "/" + filename;
    }
}

package Utils;

import com.google.cloud.storage.*;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class CloudStorageHelper {

    private static Storage storage = null;


    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadFile(MultipartFile filePart, final String bucketName) throws IOException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        String dtString = dt.toString(dtf);
        final String fileName = filePart.getName() + dtString;

        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, fileName)
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                                .build(),
                        filePart.getInputStream());
        return blobInfo.getMediaLink();
    }
    public String getImageUrl(MultipartFile file,
                              final String bucket) throws IOException, ServletException {
        final String fileName = file.getName();
        // Check extension of file
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
}

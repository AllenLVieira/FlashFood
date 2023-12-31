package br.com.allen.flashfood.infrastructure.service;

import br.com.allen.flashfood.core.storage.StorageProperties;
import br.com.allen.flashfood.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3PhotoStorageService implements PhotoStorageService {

  private final AmazonS3 amazonS3;
  private final StorageProperties storageProperties;

  @Override
  public void store(NewPhoto newPhoto) {
    try {
      String filePath = getFilePath(newPhoto.getFilename());
      var objectMetadata = new ObjectMetadata();

      objectMetadata.setContentType(newPhoto.getContentType());
      var putObjectRequest =
          new PutObjectRequest(
                  storageProperties.getS3().getBucket(),
                  filePath,
                  newPhoto.getInputStream(),
                  objectMetadata)
              .withCannedAcl(CannedAccessControlList.PublicRead);

      amazonS3.putObject(putObjectRequest);
    } catch (Exception e) {
      throw new StorageException("Cannot send image to Amazon S3", e);
    }
  }

  @Override
  public void remove(String filename) {
    try {
      String filePath = getFilePath(filename);
      var deleteObjectRequest =
          new DeleteObjectRequest(storageProperties.getS3().getBucket(), filePath);

      amazonS3.deleteObject(deleteObjectRequest);
    } catch (Exception e) {
      throw new StorageException("Cannot delete image on Amazon S3", e);
    }
  }

  @Override
  public RetrievedPhoto retrieve(String filename) {
    String filePath = getFilePath(filename);
    URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);

    return RetrievedPhoto.builder().url(url.toString()).build();
  }

  private String getFilePath(String filename) {
    return String.format("%s/%s", storageProperties.getS3().getPhotoCatalogPath(), filename);
  }
}

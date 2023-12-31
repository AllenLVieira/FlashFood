package br.com.allen.flashfood.core.storage;

import br.com.allen.flashfood.domain.service.PhotoStorageService;
import br.com.allen.flashfood.infrastructure.service.PhotoLocalStorageService;
import br.com.allen.flashfood.infrastructure.service.S3PhotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {
  private final StorageProperties storageProperties;

  @Bean
  public AmazonS3 amazonS3() {
    var credentials =
        new BasicAWSCredentials(
            storageProperties.getS3().getAccessKey(), storageProperties.getS3().getSecretKey());
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(storageProperties.getS3().getRegion())
        .build();
  }

  @Bean
  public PhotoStorageService photoStorageService(AmazonS3 amazonS3) {
    if (StorageProperties.StorageType.S3.equals(storageProperties.getType())) {
      return new S3PhotoStorageService(amazonS3, storageProperties);
    } else {
      return new PhotoLocalStorageService(storageProperties);
    }
  }
}

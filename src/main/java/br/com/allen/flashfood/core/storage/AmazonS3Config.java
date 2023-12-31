package br.com.allen.flashfood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmazonS3Config {
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
}

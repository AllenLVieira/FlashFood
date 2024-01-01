package br.com.allen.flashfood.core.storage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import br.com.allen.flashfood.infrastructure.service.PhotoLocalStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Region;
import org.junit.jupiter.api.Test;

class StorageConfigTest {
  /** Method under test: {@link StorageConfig#amazonS3()} */
  @Test
  void testAmazonS3() {

    // Arrange
    StorageProperties.S3 s3 = new StorageProperties.S3();
    s3.setAccessKey("EXAMPLEakiAIOSFODNN7");
    s3.setBucket("s3://bucket-name/object-key");
    s3.setRegion("us-east-2");
    s3.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

    StorageProperties storageProperties = new StorageProperties();
    storageProperties.setS3(s3);

    // Act
    AmazonS3 actualAmazonS3Result = (new StorageConfig(storageProperties)).amazonS3();

    // Assert
    assertEquals("s3", ((AmazonS3Client) actualAmazonS3Result).getEndpointPrefix());
    assertEquals("s3", ((AmazonS3Client) actualAmazonS3Result).getServiceName());
    assertNull(((AmazonS3Client) actualAmazonS3Result).getRequestMetricsCollector());
    assertNull(((AmazonS3Client) actualAmazonS3Result).getSignerOverride());
    assertEquals(Region.US_East_2, actualAmazonS3Result.getRegion());
    assertTrue(((AmazonS3Client) actualAmazonS3Result).getMonitoringListeners().isEmpty());
  }

  /** Method under test: {@link StorageConfig#photoStorageService(AmazonS3)} */
  @Test
  void testPhotoStorageService() {

    // Arrange
    StorageConfig storageConfig = new StorageConfig(new StorageProperties());

    // Act and Assert
    assertInstanceOf(PhotoLocalStorageService.class, storageConfig.photoStorageService(new AmazonS3Client()));
  }

  /** Method under test: {@link StorageConfig#photoStorageService(AmazonS3)} */
  @Test
  void testPhotoStorageService2() {

    // Arrange, Act and Assert
    assertInstanceOf(PhotoLocalStorageService.class, (new StorageConfig(new StorageProperties())).photoStorageService(mock(AmazonS3Client.class)));
  }
}

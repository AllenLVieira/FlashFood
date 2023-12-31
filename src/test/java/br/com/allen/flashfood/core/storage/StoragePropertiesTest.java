package br.com.allen.flashfood.core.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class StoragePropertiesTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link StorageProperties#setLocal(StorageProperties.Local)}
   *   <li>{@link StorageProperties#setS3(StorageProperties.S3)}
   *   <li>{@link StorageProperties#setType(StorageProperties.StorageType)}
   *   <li>{@link StorageProperties#getLocal()}
   *   <li>{@link StorageProperties#getS3()}
   *   <li>{@link StorageProperties#getType()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    StorageProperties storageProperties = new StorageProperties();
    StorageProperties.Local local = new StorageProperties.Local();

    // Act
    storageProperties.setLocal(local);
    StorageProperties.S3 s3 = new StorageProperties.S3();
    s3.setAccessKey("EXAMPLEakiAIOSFODNN7");
    s3.setBucket("s3://bucket-name/object-key");
    s3.setRegion("us-east-2");
    s3.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
    storageProperties.setS3(s3);
    storageProperties.setType(StorageProperties.StorageType.LOCAL);
    StorageProperties.Local actualLocal = storageProperties.getLocal();
    StorageProperties.S3 actualS3 = storageProperties.getS3();

    // Assert that nothing has changed
    assertEquals(StorageProperties.StorageType.LOCAL, storageProperties.getType());
    assertSame(local, actualLocal);
    assertSame(s3, actualS3);
  }
}

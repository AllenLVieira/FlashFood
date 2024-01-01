package br.com.allen.flashfood.infrastructure.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.allen.flashfood.core.storage.StorageProperties;
import br.com.allen.flashfood.domain.service.PhotoStorageService;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S3PhotoStorageService.class, StorageProperties.class})
@ExtendWith(SpringExtension.class)
class S3PhotoStorageServiceTest {
  @MockBean private AmazonS3 amazonS3;

  @Autowired private S3PhotoStorageService s3PhotoStorageService;

  @Autowired private StorageProperties storageProperties;

  /** Method under test: {@link S3PhotoStorageService#store(PhotoStorageService.NewPhoto)} */
  @Test
  void testStore() throws SdkClientException, UnsupportedEncodingException {
    // Arrange
    PutObjectResult putObjectResult = new PutObjectResult();
    putObjectResult.setBucketKeyEnabled(true);
    putObjectResult.setContentMd5("MjdjN2NmNDAwMjI5MTAzZTAwYzZkODgzMDAyOWUyOWI=");
    putObjectResult.setETag("E Tag");
    putObjectResult.setExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    putObjectResult.setExpirationTimeRuleId("42");
    putObjectResult.setMetadata(new ObjectMetadata());
    putObjectResult.setRequesterCharged(true);
    putObjectResult.setSSEAlgorithm("Algorithm");
    putObjectResult.setSSECustomerAlgorithm("Algorithm");
    putObjectResult.setSSECustomerKeyMd5("27c7cf400229103e00c6d8830029e29b");
    putObjectResult.setVersionId("42");
    when(amazonS3.putObject(Mockito.any())).thenReturn(putObjectResult);
    PhotoStorageService.NewPhoto.NewPhotoBuilder filenameResult =
        PhotoStorageService.NewPhoto.builder().contentType("text/plain").filename("foo.txt");
    PhotoStorageService.NewPhoto newPhoto =
        filenameResult.inputStream(new ByteArrayInputStream("AXAXAXAX".getBytes(StandardCharsets.UTF_8))).build();

    // Act
    s3PhotoStorageService.store(newPhoto);

    // Assert
    verify(amazonS3).putObject(Mockito.any());
  }

  /** Method under test: {@link S3PhotoStorageService#store(PhotoStorageService.NewPhoto)} */
  @Test
  void testStore2() throws SdkClientException, UnsupportedEncodingException {
    // Arrange
    when(amazonS3.putObject(Mockito.any()))
        .thenThrow(new StorageException("An error occurred"));
    PhotoStorageService.NewPhoto newPhoto = mock(PhotoStorageService.NewPhoto.class);
    when(newPhoto.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes(StandardCharsets.UTF_8)));
    when(newPhoto.getContentType()).thenReturn("text/plain");
    when(newPhoto.getFilename()).thenReturn("foo.txt");

    // Act and Assert
    assertThrows(StorageException.class, () -> s3PhotoStorageService.store(newPhoto));
    verify(newPhoto).getContentType();
    verify(newPhoto).getFilename();
    verify(newPhoto).getInputStream();
    verify(amazonS3).putObject(Mockito.any());
  }

  /** Method under test: {@link S3PhotoStorageService#remove(String)} */
  @Test
  void testRemove() throws SdkClientException {
    // Arrange
    doNothing().when(amazonS3).deleteObject(Mockito.any());

    // Act
    s3PhotoStorageService.remove("foo.txt");

    // Assert
    verify(amazonS3).deleteObject(Mockito.any());
  }

  /** Method under test: {@link S3PhotoStorageService#remove(String)} */
  @Test
  void testRemove2() throws SdkClientException {
    // Arrange
    doThrow(new StorageException("An error occurred"))
        .when(amazonS3)
        .deleteObject(Mockito.any());

    // Act and Assert
    assertThrows(StorageException.class, () -> s3PhotoStorageService.remove("foo.txt"));
    verify(amazonS3).deleteObject(Mockito.any());
  }

  /** Method under test: {@link S3PhotoStorageService#retrieve(String)} */
  @Test
  void testRetrieve() throws MalformedURLException {
    Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt");
    URL expectedUrl = tempFilePath.toUri().toURL();

    // Arrange
    when(amazonS3.getUrl(Mockito.any(), Mockito.any())).thenReturn(expectedUrl);

    // Act
    PhotoStorageService.RetrievedPhoto actualRetrieveResult =
        s3PhotoStorageService.retrieve("foo.txt");

    // Assert
    verify(amazonS3).getUrl(Mockito.any(), Mockito.any());
    assertEquals(expectedUrl.toString(), actualRetrieveResult.getUrl());
    assertNull(actualRetrieveResult.getInputStream());
  }

  /** Method under test: {@link S3PhotoStorageService#retrieve(String)} */
  @Test
  void testRetrieve2() {
    // Arrange
    when(amazonS3.getUrl(Mockito.any(), Mockito.any()))
        .thenThrow(new StorageException("An error occurred"));

    // Act and Assert
    assertThrows(StorageException.class, () -> s3PhotoStorageService.retrieve("foo.txt"));
    verify(amazonS3).getUrl(Mockito.any(), Mockito.any());
  }
}

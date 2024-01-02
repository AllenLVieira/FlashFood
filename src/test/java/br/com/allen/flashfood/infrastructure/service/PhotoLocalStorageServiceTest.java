package br.com.allen.flashfood.infrastructure.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.allen.flashfood.core.storage.StorageProperties;
import br.com.allen.flashfood.domain.service.PhotoStorageService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PhotoLocalStorageService.class, StorageProperties.class})
@ExtendWith(SpringExtension.class)
class PhotoLocalStorageServiceTest {
  @Autowired private PhotoLocalStorageService photoLocalStorageService;

  @Autowired private StorageProperties storageProperties;

  @Test
  void testStoreSuccess() throws IOException {
    // Arrange
    StorageProperties.Local local = mock(StorageProperties.Local.class);
    when(local.getPhotoPath()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir")));
    storageProperties.setLocal(local);

    ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] {});
    PhotoStorageService.NewPhoto newPhoto =
        PhotoStorageService.NewPhoto.builder()
            .filename("test.jpg")
            .contentType("image/jpeg")
            .inputStream(inputStream)
            .build();

    // Act
    photoLocalStorageService.store(newPhoto);

    // Assert
    verify(local).getPhotoPath();
  }

  @Test
  void testStoreFailure() {
    // Arrange
    StorageProperties.Local local = mock(StorageProperties.Local.class);
    when(local.getPhotoPath()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir")));
    storageProperties.setLocal(local);

    ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] {});
    PhotoStorageService.NewPhoto newPhoto =
        PhotoStorageService.NewPhoto.builder()
            .filename("test.jpg")
            .contentType("image/jpeg")
            .inputStream(inputStream)
            .build();

    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.any()))
          .thenThrow(new IOException("Test exception"));

      // Act & Assert
      assertThrows(StorageException.class, () -> photoLocalStorageService.store(newPhoto));
    }

    verify(local).getPhotoPath();
  }

  /** Method under test: {@link PhotoLocalStorageService#remove(String)} */
  @Test
  void testRemove() {

    // Arrange
    StorageProperties.Local local = mock(StorageProperties.Local.class);
    when(local.getPhotoPath())
        .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

    StorageProperties storageProperties = new StorageProperties();
    storageProperties.setLocal(local);

    // Act
    (new PhotoLocalStorageService(storageProperties)).remove("foo.txt");

    // Assert that nothing has changed
    verify(local).getPhotoPath();
  }

  /** Method under test: {@link PhotoLocalStorageService#remove(String)} */
  @Test
  void testRemove2() throws IOException {
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.deleteIfExists(Mockito.any()))
          .thenThrow(new IOException("foo"));
      StorageProperties.Local local = mock(StorageProperties.Local.class);
      when(local.getPhotoPath()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), ""));
      StorageProperties storageProperties = new StorageProperties();
      storageProperties.setLocal(local);
      assertThrows(
          StorageException.class,
          () -> (new PhotoLocalStorageService(storageProperties)).remove(""));
      verify(local).getPhotoPath();
      mockFiles.verify(() -> Files.deleteIfExists(Mockito.any()));
    }
  }

  /** Method under test: {@link PhotoLocalStorageService#remove(String)} */
  @Test
  void testRemove3() throws IOException {
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.deleteIfExists(Mockito.any()))
          .thenThrow(new StorageException("An error occurred"));
      StorageProperties.Local local = mock(StorageProperties.Local.class);
      when(local.getPhotoPath()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), ""));
      StorageProperties storageProperties = new StorageProperties();
      storageProperties.setLocal(local);
      assertThrows(
          StorageException.class,
          () -> (new PhotoLocalStorageService(storageProperties)).remove(""));
      verify(local).getPhotoPath();
      mockFiles.verify(() -> Files.deleteIfExists(Mockito.any()));
    }
  }

  /** Method under test: {@link PhotoLocalStorageService#retrieve(String)} */
  @Test
  void testRetrieve() {

    // Arrange
    StorageProperties.Local local = mock(StorageProperties.Local.class);
    when(local.getPhotoPath())
        .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

    StorageProperties storageProperties = new StorageProperties();
    storageProperties.setLocal(local);

    // Act and Assert
    assertThrows(
        StorageException.class,
        () -> (new PhotoLocalStorageService(storageProperties)).retrieve("foo.txt"));
    verify(local).getPhotoPath();
  }
}

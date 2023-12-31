package br.com.allen.flashfood.infrastructure.service;

import br.com.allen.flashfood.core.storage.StorageProperties;
import br.com.allen.flashfood.domain.service.PhotoStorageService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
@RequiredArgsConstructor
public class PhotoLocalStorageService implements PhotoStorageService {

  private final StorageProperties storageProperties;

  @Override
  public void store(NewPhoto newPhoto) {
    Path filePath = getFilePath(newPhoto.getFilename());
    try {
      FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
    } catch (IOException e) {
      throw new StorageException("The file cannot be stored.", e);
    }
  }

  @Override
  public void remove(String filename) {
    Path filepath = getFilePath(filename);
    try {
      Files.deleteIfExists(filepath);
    } catch (IOException e) {
      throw new StorageException("The file cannot be deleted.", e);
    }
  }

  @Override
  public InputStream retrieve(String filename) {
    Path filepath = getFilePath(filename);
    try {
      return Files.newInputStream(filepath);
    } catch (IOException e) {
      throw new StorageException("Cannot retrieve the file.", e);
    }
  }

  private Path getFilePath(String filename) {
    return storageProperties.getLocal().getPhotoPath().resolve(Path.of(filename));
  }
}

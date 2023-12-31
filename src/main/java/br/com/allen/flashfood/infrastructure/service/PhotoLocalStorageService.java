package br.com.allen.flashfood.infrastructure.service;

import br.com.allen.flashfood.domain.service.PhotoStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class PhotoLocalStorageService implements PhotoStorageService {

  @Value("${flashfood.storage.local.photos-directory}")
  private Path fileStoragePath;

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

  private Path getFilePath(String filename) {
    return fileStoragePath.resolve(Path.of(filename));
  }
}

package br.com.allen.flashfood.domain.service;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
  void store(NewPhoto newPhoto);

  void remove(String filename);

  RetrievedPhoto retrieve(String filename);

  default void replaceName(String old, NewPhoto newPhoto) {
    this.store(newPhoto);
    if (old != null) {
      this.remove(old);
    }
  }

  default String generateFileName(String originalFileName) {
    return UUID.randomUUID() + "_" + originalFileName;
  }

  @Builder
  @Getter
  class NewPhoto {
    private String filename;
    private String contentType;
    private InputStream inputStream;
  }

  @Builder
  @Getter
  class RetrievedPhoto {
    private InputStream inputStream;
    private String url;

    public boolean urlExists() {
      return url != null;
    }

    public boolean inputStreamExists() {
      return inputStream != null;
    }
  }
}

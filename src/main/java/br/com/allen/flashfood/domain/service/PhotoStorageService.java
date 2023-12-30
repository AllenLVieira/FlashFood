package br.com.allen.flashfood.domain.service;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
  void store(NewPhoto newPhoto);

  default String generateFileName(String originalFileName) {
    return UUID.randomUUID() + "_" + originalFileName;
  }

  @Builder
  @Getter
  class NewPhoto {
    private String filename;
    private InputStream inputStream;
  }
}

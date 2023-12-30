package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.PhotoProduct;

public interface ProductRepositoryQueries {
  PhotoProduct save(PhotoProduct photo);

  void delete(PhotoProduct photo);
}

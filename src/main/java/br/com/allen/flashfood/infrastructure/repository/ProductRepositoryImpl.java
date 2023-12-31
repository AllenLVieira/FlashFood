package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.repository.ProductRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {
  @PersistenceContext private EntityManager entityManager;

  @Override
  @Transactional
  public PhotoProduct save(PhotoProduct photo) {
    return entityManager.merge(photo);
  }

  @Override
  @Transactional
  public void delete(PhotoProduct photo) {
    entityManager.remove(photo);
  }
}

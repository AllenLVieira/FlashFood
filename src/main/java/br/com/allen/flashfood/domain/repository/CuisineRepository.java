package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
    List<Cuisine> findByNameContainingIgnoreCase(String name);
}

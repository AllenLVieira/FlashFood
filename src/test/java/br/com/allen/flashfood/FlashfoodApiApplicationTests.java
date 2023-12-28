package br.com.allen.flashfood;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlashfoodApiApplicationTests {

  @Autowired private CuisineRepository cuisineRepository;

  @Test
  void contextLoads() {
    assertNotNull(cuisineRepository);
  }
}

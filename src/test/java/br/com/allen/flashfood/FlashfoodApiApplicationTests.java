package br.com.allen.flashfood;

import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FlashfoodApiApplicationTests {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Test
    void contextLoads() {
        assertNotNull(cuisineRepository);
    }

}

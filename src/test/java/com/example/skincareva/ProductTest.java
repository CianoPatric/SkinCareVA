package com.example.skincareva;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void testProductCreation() {
        Product product = new Product("Крем", "Уход", "Описание", "Сухая", "any", 18);

        assertEquals("Крем", product.getName());
        assertEquals("Сухая", product.getSkinType());
        assertEquals(18, product.getMinAge());
    }
}
package com.example.skincareva;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class LogicTest {
    @Test
    void testFilteringLogic() {
        // 1. Создаем список "фейковых" продуктов
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Детский крем", "Крем", "...", "Сухая", "any", 0));
        mockProducts.add(new Product("Мужской гель", "Гель", "...", "Жирная", "male", 18));
        mockProducts.add(new Product("Anti-age 45+", "Крем", "...", "Сухая", "female", 45));

        // 2. Имитируем пользователя: Мужчина, 20 лет, Жирная кожа
        String selectedSkinType = "Жирная";
        String userGender = "male";
        int userAge = 20;

        List<Product> filtered = mockProducts.stream()
                .filter(p -> p.getSkinType().equalsIgnoreCase(selectedSkinType))
                .filter(p -> p.getGender().equalsIgnoreCase("any") || p.getGender().equalsIgnoreCase(userGender))
                .filter(p -> userAge >= p.getMinAge())
                .collect(Collectors.toList());

        assertEquals(1, filtered.size(), "Должен быть найден ровно 1 продукт");
        assertEquals("Мужской гель", filtered.get(0).getName());
    }
}
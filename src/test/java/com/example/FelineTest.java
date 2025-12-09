package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FelineTest {

    @Test
    void eatMeatPredatorFood() throws Exception {
        Feline feline = new Feline();
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        List<String> actualFood = feline.eatMeat();
        assertEquals(expectedFood, actualFood);
    }

    @Test
    void getFamilyWithoutParameterCats() {
        Feline feline = new Feline();
        String family = feline.getFamily();
        assertEquals("Кошачьи", family);
    }

    @Test
    void getKittensWithoutParameterOne() {
        Feline feline = new Feline();
        int kittens = feline.getKittens();
        assertEquals(1, kittens);
    }

    @Test
    void getKittensWithParameterFive() {
        Feline feline = new Feline();
        int expectedCount = 5;
        int actualCount = feline.getKittens(expectedCount);
        assertEquals(expectedCount, actualCount);
    }

    @ParameterizedTest
    @MethodSource("foodProvider")
    void getFoodWithIncorrectParametersNotEqual(List<String> expectedFood) throws Exception {
        Feline feline = new Feline();
        List<String> actualFood = feline.eatMeat();
        assertNotEquals(expectedFood, actualFood);
    }

    private static Stream<Arguments> foodProvider() {
        return Stream.of(
                Arguments.of(List.of("Животные", "Птицы", "Осетр")),
                Arguments.of(List.of("Мясо", "Курица", "Индейка")),
                Arguments.of(List.of("Рыба", "Молоко"))
        );
    }
}

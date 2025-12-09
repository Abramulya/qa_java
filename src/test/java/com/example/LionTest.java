package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LionTest {

    @Mock
    private Feline mockFeline;

    @ParameterizedTest
    @CsvSource({
            "Самец, true",
            "Самка, false"
    })
    void lionConstructorValidSex(String sex, boolean expectedHasMane) throws Exception {
        Lion lion = new Lion(sex, mockFeline);
        assertEquals(expectedHasMane, lion.doesHaveMane());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Неизвестно", "Мужской", "Женский", "Male", "Female"})
    void lionConstructorInvalidSexThrowsException(String invalidSex) {
        Exception exception = assertThrows(Exception.class,
                () -> new Lion(invalidSex, mockFeline));

        assertTrue(exception.getMessage().contains("Используйте допустимые значения пола"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3, 10})
    void getKittensWithValidParameters(int kittensCount) throws Exception {
        when(mockFeline.getKittens()).thenReturn(kittensCount);
        Lion lion = new Lion("Самец", mockFeline);
        int actualKittens = lion.getKittens();
        assertEquals(kittensCount, actualKittens);
    }

    @Test
    void doesHaveManeValid() throws Exception {
        Lion lion = new Lion("Самец", mockFeline);
        boolean hasMane = lion.doesHaveMane();
        assertTrue(hasMane);
    }

    @Test
    void getFoodValidParameterValidWork() throws Exception {
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        when(mockFeline.getFood("Хищник")).thenReturn(expectedFood);
        Lion lion = new Lion("Самка", mockFeline);
        List<String> actualFood = lion.getFood();
        assertEquals(expectedFood, actualFood);
    }

    @Test
    void getFoodThrowsException() throws Exception {
        when(mockFeline.getFood("Хищник")).thenThrow(new Exception("Ошибка"));
        Lion lion = new Lion("Самец", mockFeline);
        assertThrows(Exception.class, () -> lion.getFood());
    }

}
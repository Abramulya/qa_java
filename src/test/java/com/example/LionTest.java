package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

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
    void testLionConstructorValidSex(String sex, boolean expectedHasMane) throws Exception {
        // Act
        Lion lion = new Lion(sex, mockFeline);

        // Assert
        assertEquals(expectedHasMane, lion.doesHaveMane());
        verifyNoInteractions(mockFeline);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Неизвестно", "Мужской", "Женский", "Male", "Female"})
    void testLionConstructorInvalidSexThrowsException(String invalidSex) {
        // Act & Assert
        Exception exception = assertThrows(Exception.class,
                () -> new Lion(invalidSex, mockFeline));

        assertTrue(exception.getMessage().contains("Используйте допустимые значения пола"));
        verifyNoInteractions(mockFeline);
    }

    @ParameterizedTest
    @MethodSource("kittensCountProvider")
    void testGetKittens(int kittensCount) throws Exception {
        // Arrange
        when(mockFeline.getKittens()).thenReturn(kittensCount);
        Lion lion = new Lion("Самец", mockFeline);

        // Act
        int actualKittens = lion.getKittens();

        // Assert
        assertEquals(kittensCount, actualKittens);
        verify(mockFeline).getKittens();
    }

    @Test
    void testDoesHaveMane() throws Exception {
        // Arrange
        Lion lion = new Lion("Самец", mockFeline);

        // Act
        boolean hasMane = lion.doesHaveMane();

        // Assert
        assertTrue(hasMane);
        verifyNoInteractions(mockFeline);
    }

    @ParameterizedTest
    @MethodSource("foodProvider")
    void testGetFood(List<String> expectedFood) throws Exception {
        // Arrange
        when(mockFeline.getFood("Хищник")).thenReturn(expectedFood);
        Lion lion = new Lion("Самка", mockFeline);

        // Act
        List<String> actualFood = lion.getFood();

        // Assert
        assertEquals(expectedFood, actualFood);
        verify(mockFeline).getFood("Хищник");
    }

    @Test
    void testGetFoodThrowsException() throws Exception {
        // Arrange
        when(mockFeline.getFood("Хищник")).thenThrow(new Exception("Ошибка"));
        Lion lion = new Lion("Самец", mockFeline);

        // Act & Assert
        assertThrows(Exception.class, () -> lion.getFood());
        verify(mockFeline).getFood("Хищник");
    }

    private static Stream<Arguments> kittensCountProvider() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(3),
                Arguments.of(10)
        );
    }

    private static Stream<Arguments> foodProvider() {
        return Stream.of(
                Arguments.of(List.of("Животные", "Птицы", "Рыба")),
                Arguments.of(List.of("Мясо")),
                Arguments.of(List.of("Говядина", "Баранина", "Оленина"))
        );
    }
}
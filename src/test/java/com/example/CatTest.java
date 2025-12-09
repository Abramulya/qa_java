package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatTest {

    @Mock
    private Feline mockFeline;

    @Test
    void GetSoundRightSoundMyau() {
        Cat cat = new Cat(mockFeline);
        String sound = cat.getSound();
        assertEquals("Мяу", sound);
        verifyNoInteractions(mockFeline);
    }

    @ParameterizedTest
    @MethodSource("foodProvider")
    void testGetFood(List<String> expectedFood) throws Exception {
        when(mockFeline.eatMeat()).thenReturn(expectedFood);
        Cat cat = new Cat(mockFeline);

        // Act
        List<String> actualFood = cat.getFood();

        // Assert
        assertEquals(expectedFood, actualFood);
        verify(mockFeline).eatMeat();
    }

    @Test
    void testGetFoodThrowsException() throws Exception {
        // Arrange
        when(mockFeline.eatMeat()).thenThrow(new Exception("Ошибка"));
        Cat cat = new Cat(mockFeline);

        // Act & Assert
        assertThrows(Exception.class, () -> cat.getFood());
        verify(mockFeline).eatMeat();
    }

    private static Stream<Arguments> foodProvider() {
        return Stream.of(
                Arguments.of(List.of("Животные", "Птицы", "Рыба")),
                Arguments.of(List.of("Мясо", "Курица", "Индейка")),
                Arguments.of(List.of("Рыба", "Молоко"))
        );
    }
}
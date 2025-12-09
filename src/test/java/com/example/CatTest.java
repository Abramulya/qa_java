package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatTest {

    @Mock
    private Feline mockFeline;

    @Test
    void getSoundWithoutParameterRightSoundMyau() {
        Cat cat = new Cat(mockFeline);
        String sound = cat.getSound();
        assertEquals("Мяу", sound);
    }

    @Test
    void getFoodWithIncorrectParametersNotEqual() throws Exception {
        List<String> expectedFood = List.of("Животные", "Птицы", "Осетр");
        Cat cat = new Cat(mockFeline);
        List<String> actualFood = cat.getFood();
        assertNotEquals(expectedFood, actualFood);
    }

    @Test
    void getFoodWithCorrectParameterEqual() throws Exception {
        Feline feline = new Feline();
        Cat cat = new Cat(feline);
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        List<String> actualFood = cat.getFood();
        assertEquals(expectedFood, actualFood);
    }

    @Test
    void getFoodThrowsException() throws Exception {
        when(mockFeline.eatMeat()).thenThrow(new Exception("Ошибка"));
        Cat cat = new Cat(mockFeline);
        assertThrows(Exception.class, () -> cat.getFood());
        verify(mockFeline).eatMeat();
    }

}
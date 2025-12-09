package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FelineTest {

    @Mock
    private Feline feline;

    @Mock
    private Animal animal;

    @Test
    void EatMeatPredatorFood() throws Exception {
        Feline felineMy = new Feline();
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        // when(animal.getFood("Хищник")).thenReturn(expectedFood);
        List<String> actualFood = felineMy.eatMeat();
        assertEquals(expectedFood, actualFood);
      //  verify(animal).getFood("Хищник");
    }

    @Test
    void EatMeatThrowsPredatorException() throws Exception {
        when(animal.getFood("Хищник")).thenThrow(new Exception("Ошибка"));
        assertThrows(Exception.class, () -> feline.eatMeat());
        verify(animal).getFood("Хищник");
    }

    @Test
    void GetFamilyWithoutParameterCats() {
        String family = feline.getFamily();
        assertEquals("Кошачьи", family);
        verifyNoInteractions(animal);
    }

    @Test
    void testGetKittensWithoutParameterOne() {
        int kittens = feline.getKittens();
        assertEquals(1, kittens);
    }

    @Test
    void testGetKittensWithParameterFiveShowsOk() {
        int expectedCount = 5;
        int actualCount = feline.getKittens(expectedCount);
        assertEquals(expectedCount, actualCount);
    }
}
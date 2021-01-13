package com.github.snowhite93.bankingapp.ui;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.snowhite93.bankingapp.ui.Inputs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InputsTest {

    @Mock
    private Input input;

    @Test
    public void readIntTest() {
        when(input.readLine())
                .thenReturn("eggs", "dress", " ", "123");

        int readInt = readInt(input);
        assertEquals(123, readInt);
    }

    @Test
    public void readDateTest() throws ParseException {
        when(input.readLine())
                .thenReturn("212333-2333", "1990-11-20");

        Date readDate = readDate(input);

        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd")
                .parse("1990-11-20");

        assertEquals(expectedDate, readDate);
    }

    @Test
    public void readDoubleTest() {
        when(input.readLine())
                .thenReturn("eggs", "dress", " ", "22.3");

        double readDouble = readDouble(input);
        assertEquals(22.3, readDouble);
    }

}

package com.advancestores.alexa.order.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ServiceExceptionTest {

    @Test
    public void serviceException_test(){
        ServiceException e1 = new ServiceException("error message");
        assertNotNull(e1);
        assertEquals("error message", e1.getMessage());

        e1 = new ServiceException(new NullPointerException());
        assertNotNull(e1);
        assertEquals("java.lang.NullPointerException", e1.getCause().getClass().getName());

        e1 = new ServiceException("error message", new NullPointerException());
        assertNotNull(e1);
        assertEquals("error message", e1.getMessage());
        assertEquals("java.lang.NullPointerException", e1.getCause().getClass().getName());
    }
}
package com.advancestores.alexa.order.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

public abstract class AbstractJavaBeanTester<T> {

    @Test
    @SuppressWarnings("unchecked")
    public void thatBeanisSerializable() {
        final T myBean = getBeanInstance();
        final byte[] serializedMyBean = SerializationUtils.serialize((Serializable) myBean);
        final T deserializedBean = (T) SerializationUtils.deserialize(serializedMyBean);
        assertThat("De-serialized version of the Product bean matches original.",
                myBean, is(deserializedBean));
    }

    @Test
    public void getterSetter(){
        assertPojoMethodsFor(getBeanInstance().getClass()).areWellImplemented();
    }
    
    public abstract T getBeanInstance();
}
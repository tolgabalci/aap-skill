package com.advancestores.alexa.order.persistence.entity;

import com.advancestores.alexa.order.domain.AbstractJavaBeanTester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CatalogTest extends AbstractJavaBeanTester<Catalog> {
	@Override
	public Catalog getBeanInstance() {
		return Catalog.builder().id(1L).domain("domain").summary("summary").build();
	}

	@Test
	public void builderTest() {
		Catalog catalog = new Catalog(10L, "domain", "summary");
		Assertions.assertNotNull(catalog);

		Assertions.assertThrows(NullPointerException.class, () -> Catalog.builder().build());
	}
}
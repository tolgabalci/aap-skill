package com.advancestores.alexa.order.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CatalogSummaryTest extends AbstractJavaBeanTester<CatalogSummary> {
    @Override
    public CatalogSummary getBeanInstance() {
        return CatalogSummary
            .builder()
            .summary("summary")
            .build();
    }
    
    @Test
	public void builderTest() {
        CatalogSummary catalogSummary = new CatalogSummary("summary");
		Assertions.assertNotNull(catalogSummary);

		Assertions.assertThrows(NullPointerException.class, () -> CatalogSummary.builder().build());
	}
}
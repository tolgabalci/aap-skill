/*
package com.advancestores.alexa.order.service;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;

import com.advancestores.alexa.order.integration.kafka.ProductKafkaProducer;
import com.advancestores.alexa.order.domain.CatalogSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.advancestores.alexa.order.persistence.entity.Catalog;
import com.advancestores.alexa.order.persistence.CatalogRepository;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {

    @InjectMocks
    private CatalogService service;

    @Mock
    private CatalogRepository repository;

    @Mock
    private ProductKafkaProducer producer;

    @Test
    public void whenValidDomain_thenReturnCatalogSummary() {
        Mockito.when(repository.findByDomain("domain1")).thenReturn(Optional.of(Arrays.asList(new Catalog(1L, "domain1", "summary1"), new Catalog(2L, "domain1", "summary2"))));
        Mockito.doNothing().when(producer).publish(Mockito.any(CatalogSummary.class));
        Optional<List<CatalogSummary>> catalogSummariesOpt = service.retrieveSummaries("domain1");
        Assertions.assertTrue(catalogSummariesOpt.isPresent());
        List<CatalogSummary> catalogSummaries = catalogSummariesOpt.get();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(catalogSummaries));

        assertThat(catalogSummaries, Matchers.hasItems(Matchers.hasProperty("summary", Matchers.is("summary1"))));

        Mockito.verify(repository).findByDomain("domain1");
        Mockito.verify(producer, Mockito.times(2)).publish(Mockito.any(CatalogSummary.class));
    }

    @Test
    public void whenInvalidDomain_thenReturnNoCatalogSummary() {
        Mockito.when(repository.findByDomain("invalid domain")).thenReturn(Optional.empty());

        Assertions.assertFalse(service.retrieveSummaries("invalid domain").isPresent());

        Mockito.verify(repository).findByDomain("invalid domain");
        Mockito.verify(producer, Mockito.never()).publish(Mockito.any(CatalogSummary.class));
    }
}*/

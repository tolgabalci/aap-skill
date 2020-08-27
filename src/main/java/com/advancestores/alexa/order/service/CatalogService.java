/**
 * $Id$
 *//*

package com.advancestores.alexa.order.service;

import com.advancestores.alexa.order.integration.kafka.ProductKafkaProducer;
import com.advancestores.alexa.order.persistence.entity.Catalog;
import com.advancestores.alexa.order.domain.CatalogSummary;
import com.advancestores.alexa.order.persistence.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

*/
/**
 * Service component that facilitates the transformation from the Omni-channel catalog source table to a
 * format more suitable for display by front end systems.
 *//*

@Slf4j
@Service
public class CatalogService {

    @Autowired
    private CatalogRepository repository;

    @Autowired(required = false)
    private ProductKafkaProducer productKafkaProducer;

    public Optional<List<CatalogSummary>> retrieveSummaries(String domain) {
        Optional<List<Catalog>> catalogOptList = repository.findByDomain(domain);

        Optional<List<CatalogSummary>> catalogSummaries = catalogOptList.map(catalogs ->
                catalogs.stream().map(catalog -> {
                    CatalogSummary summary = new CatalogSummary();
                    summary.setSummary(catalog.getSummary());
                    return summary;
                }).collect(Collectors.toList())
        );

        if(null != productKafkaProducer) {
            catalogSummaries.ifPresent(c -> c.forEach(cc -> productKafkaProducer.publish(cc)));
        }

        return catalogSummaries;
    }
}
*/

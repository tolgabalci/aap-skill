package com.advancestores.alexa.order.persistence;

import com.advancestores.alexa.order.persistence.entity.Catalog;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
public class CatalogRepositoryTest {

    @Autowired
    private CatalogRepository repository;

    @Test
    void findByDomain() {
        Optional<List<Catalog>> catalogOpt = repository.findByDomain("domain1");
        Assertions.assertTrue(catalogOpt.isPresent());
        List<Catalog> catalogs = catalogOpt.get();
        assertThat(catalogs, Matchers.hasItems(Matchers.hasProperty("summary", is("summary1"))));
    }
}
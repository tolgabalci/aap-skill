package com.advancestores.alexa.order.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.advancestores.alexa.order.persistence.entity.Catalog;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<List<Catalog>> findByDomain(String domain);
}

package org.iacg.iacgservice.repository;

import org.iacg.iacgservice.model.AcgProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AcgProductRepository extends MongoRepository<AcgProduct, String> {
    List<AcgProduct> findByNameContainingIgnoreCase(String name);
    List<AcgProduct> findByBigType(int bigType);
}

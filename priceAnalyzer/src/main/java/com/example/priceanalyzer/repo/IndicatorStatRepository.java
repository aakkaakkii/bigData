package com.example.priceanalyzer.repo;

import com.example.priceanalyzer.entitys.IndicatorStatEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicatorStatRepository extends MongoRepository<IndicatorStatEntity, String> {
    IndicatorStatEntity findBySymbol(String symbol);
}

package com.example.priceanalyzer.repo;

import com.example.priceanalyzer.entitys.CoinStatEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinStatRepository extends MongoRepository<CoinStatEntity, String> {
    CoinStatEntity findBySymbol(String symbol);
}

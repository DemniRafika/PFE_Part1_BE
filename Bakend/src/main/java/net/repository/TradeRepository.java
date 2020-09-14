package net.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import net.model.Trade;


    @Repository
    public interface TradeRepository  extends MongoRepository<Trade, Long>{

}



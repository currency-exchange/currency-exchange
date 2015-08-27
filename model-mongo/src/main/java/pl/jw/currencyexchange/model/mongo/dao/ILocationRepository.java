package pl.jw.currencyexchange.model.mongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.jw.currencyexchange.model.mongo.data.Location;

public interface ILocationRepository extends MongoRepository<Location, String> {
}

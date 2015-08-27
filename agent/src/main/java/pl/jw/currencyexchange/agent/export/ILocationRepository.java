package pl.jw.currencyexchange.agent.export;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.jw.currencyexchange.agent.export.data.Location;

public interface ILocationRepository extends MongoRepository<Location, String> {
}

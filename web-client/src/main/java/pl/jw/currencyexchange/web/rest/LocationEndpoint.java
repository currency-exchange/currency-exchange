package pl.jw.currencyexchange.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.jw.currencyexchange.model.mongo.dao.ILocationRepository;
import pl.jw.currencyexchange.model.mongo.data.Location;

@RestController
public class LocationEndpoint {

	@Autowired
	private ILocationRepository locationRepository;

	@RequestMapping("/locations")
	public List<Location> home() {

		return locationRepository.findAll();
	}
}

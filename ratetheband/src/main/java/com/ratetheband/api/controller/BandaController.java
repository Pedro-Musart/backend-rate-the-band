package com.ratetheband.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ratetheband.api.model.Banda;
import com.ratetheband.api.repository.BandaRepository;

@RestController
@RequestMapping("/banda")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BandaController {

	@Autowired
	private BandaRepository bandaRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Banda>> getAll() {
		return ResponseEntity.ok(bandaRepository.findAll());
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Banda>> getAverageRatingByBandaIds(@RequestParam List<Long> ids) {
	    return ResponseEntity.ok(bandaRepository.findBandasByIds(ids));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Banda> getById(@PathVariable Long id){
		return bandaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(404).build());
	}
}

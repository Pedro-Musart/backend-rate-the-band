package com.ratetheband.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ratetheband.api.model.Avaliacao;
import com.ratetheband.api.repository.AvaliacaoRepository;
import com.ratetheband.api.service.AvaliacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacaoController {
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	
	@Autowired
	private AvaliacaoService avaliacaoService;

	
	@GetMapping("/all")
	public ResponseEntity<List<Avaliacao>> getAll() {
		return ResponseEntity.ok(avaliacaoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Avaliacao> getById(@PathVariable Long id){
		return avaliacaoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(404).build());
	}
	
	
	@GetMapping("/banda/{id}/usuario/{userId}")
	public ResponseEntity<List<Avaliacao>> getByBandaIdAndUsuarioId(@PathVariable Long id, @PathVariable Long userId){
		return ResponseEntity.ok(avaliacaoRepository.findAllByUsuarioIdAndBandaId(id, userId));
	}
	
	@GetMapping("/banda/{id}/media")
	public ResponseEntity<Double> getMediaAvaliacoesBanda(@PathVariable Long id){
		return ResponseEntity.ok(avaliacaoRepository.findAverageRatingByBandaId(id));
	}
	
	@PostMapping
	public ResponseEntity<Object> post(@Valid @RequestBody Avaliacao avaliacao, @RequestHeader("Authorization") String token) {
	    try {
	        avaliacaoService.cadastrarAvaliacao(avaliacao, token);
	        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    } catch (ResponseStatusException e) {
	        if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode())) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } else if (HttpStatus.CONFLICT.equals(e.getStatusCode())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a avaliação");
	        }
	    }
	}



	
	@PutMapping
	public ResponseEntity<Object> put(@Valid @RequestBody Avaliacao avaliacao, @RequestHeader("Authorization") String token) {
	        try {
	            avaliacaoService.atualizarAvaliacao(avaliacao, token);
	            return ResponseEntity.status(HttpStatus.OK).body(avaliacao);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	        } catch (ResponseStatusException e) {
	            if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode())) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	            } else if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
	            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	            } else {
	            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a atualização da avaliação");
	            }
	        }
	}

	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);
		if(avaliacao.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		avaliacaoRepository.deleteById(id);
	}
	
}

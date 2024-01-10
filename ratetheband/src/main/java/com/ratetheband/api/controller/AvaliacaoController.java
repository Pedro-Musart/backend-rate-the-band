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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ratetheband.api.model.Avaliacao;
import com.ratetheband.api.repository.AvaliacaoRepository;
import com.ratetheband.api.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacaoController {
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	
	@PostMapping
	public ResponseEntity<Avaliacao> post(@Valid @RequestBody Avaliacao avaliacao){
		if(usuarioRepository.existsById(avaliacao.getUsuario().getId()))
			return ResponseEntity.status(201)
				.body(avaliacaoRepository.save(avaliacao));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliaçao precisa de um usuário válido!", null);
	}
	
	@PutMapping
	public ResponseEntity<Avaliacao> put(@Valid @RequestBody Avaliacao avaliacao){
		if(avaliacaoRepository.existsById(avaliacao.getId())) {
			
			if(usuarioRepository.existsById(avaliacao.getUsuario().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(avaliacaoRepository.save(avaliacao));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliaçao precisa de um usuário válido!", null);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

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

import com.ratetheband.api.model.Comentario;
import com.ratetheband.api.repository.ComentarioRepository;
import com.ratetheband.api.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComentarioController {

	
	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Comentario>> getAll() {
		return ResponseEntity.ok(comentarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comentario> getById(@PathVariable Long id){
		return comentarioRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(404).build());
	}
	
	@PostMapping
	public ResponseEntity<Comentario> post(@Valid @RequestBody Comentario comentario){
		if(usuarioRepository.existsById(comentario.getUsuario().getId()))
			return ResponseEntity.status(201)
				.body(comentarioRepository.save(comentario));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O comentario precisa de um usuário válido!", null);
	}
	
	@PutMapping
	public ResponseEntity<Comentario> put(@Valid @RequestBody Comentario comentario){
		if(comentarioRepository.existsById(comentario.getId())) {
			
			if(usuarioRepository.existsById(comentario.getUsuario().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(comentarioRepository.save(comentario));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O comentário precisa de um usuário válido!", null);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Comentario> avaliacao = comentarioRepository.findById(id);
		
		if(avaliacao.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		comentarioRepository.deleteById(id);
	}
}

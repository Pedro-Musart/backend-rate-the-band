package com.ratetheband.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ratetheband.api.model.Usuario;
import com.ratetheband.api.model.UsuarioDTO;
import com.ratetheband.api.model.UsuarioLogin;
import com.ratetheband.api.repository.UsuarioRepository;
import com.ratetheband.api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	
	@GetMapping("/all")
	public ResponseEntity <List<UsuarioDTO>> getAll(){
		
		return ResponseEntity.ok(usuarioService.buscarTodosUsuarios());
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
		return usuarioService.buscarUsuario(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nomes/{nome}")
	public ResponseEntity<List<UsuarioDTO>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(usuarioService.buscarUsuariosPorNome(nome));
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		
		return usuarioService.autenticarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
    

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid Usuario usuario) {

		return usuarioService.cadastrarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	@PutMapping("/atualizar")
	public ResponseEntity<Object> putUsuario(@Valid @RequestBody Usuario usuario, @RequestHeader("Authorization") String token) {
        try {
            usuarioService.atualizarUsuario(usuario, token);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (ResponseStatusException e) {
            if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } else if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else if (HttpStatus.FORBIDDEN.equals(e.getStatusCode())) {
            	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }else {
            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a atualização da avaliação");
            }
        }
	}

}
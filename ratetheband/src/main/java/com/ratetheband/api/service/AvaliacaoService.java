package com.ratetheband.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ratetheband.api.model.Avaliacao;
import com.ratetheband.api.repository.AvaliacaoRepository;
import com.ratetheband.api.repository.UsuarioRepository;
import com.ratetheband.api.security.JwtService;

@Service
public class AvaliacaoService {

	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao, String token) {
	    token = token.substring(7);
	    Long tokenUserId = jwtService.extractUserId(token);
	    Long bandaId = avaliacao.getBandaId();

	    if(usuarioRepository.existsById(avaliacao.getUsuario().getId())) {
		    if (avaliacao.getUsuario().getId().equals(tokenUserId)) {
		        if (avaliacaoRepository.findByBandaIdAndUsuarioId(bandaId, tokenUserId).size() == 0) {
		            return avaliacaoRepository.save(avaliacao);
		        } else {
		        	throw new ResponseStatusException(HttpStatus.CONFLICT, "O usuário já avaliou está banda!");
		        }
		    }
		    
		    throw new IllegalArgumentException("Permissão inválida: não é possível criar uma avaliação para outro usuário");
	    }
	    
	   
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliaçao precisa de um usuário válido!", null);
	}
	
	public Avaliacao atualizarAvaliacao(Avaliacao avaliacao, String token) {
	    token = token.substring(7);
	    Long tokenUserId = jwtService.extractUserId(token);
			    
	    	if (usuarioRepository.existsById(avaliacao.getUsuario().getId())) {
		        if (avaliacao.getUsuario().getId().equals(tokenUserId)) {
		            return avaliacaoRepository.save(avaliacao);
		        } else {
		            throw new IllegalArgumentException("Permissão inválida: não é possível atualizar uma avaliação de outro usuário");
		        }
	    	}
	    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliaçao precisa de um usuário válido!", null);
	}
	
	
	
	
}
	
	


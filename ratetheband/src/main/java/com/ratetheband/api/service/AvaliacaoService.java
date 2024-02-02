package com.ratetheband.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ratetheband.api.model.Album;
import com.ratetheband.api.model.Avaliacao;
import com.ratetheband.api.model.Banda;
import com.ratetheband.api.repository.AvaliacaoRepository;
import com.ratetheband.api.repository.BandaRepository;
import com.ratetheband.api.repository.UsuarioRepository;

@Service
public class AvaliacaoService {

	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BandaRepository bandaRepository;
	 
	@Autowired
	private BandaService bandaService;
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao, String token) {
	    Long userId = avaliacao.getUsuario().getId();
	    Banda banda = avaliacao.getBanda();
	    Long bandaId = banda.getId();
	    List<Album> albuns = avaliacao.getBanda().getAlbuns();

	    if (!usuarioRepository.existsById(userId)) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliação precisa de um usuário válido!");
	    }

	    if (!usuarioService.tokenUserIdEqualsUserId(avaliacao.getUsuario(), token)) {
	        throw new IllegalArgumentException("Permissão inválida: não é possível cadastrar uma avaliação para outro usuário");
	    }

	    if (avaliacaoRepository.findAllByUsuarioIdAndBandaId(bandaId, userId).size() >= 1) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "O usuário já avaliou esta banda!");
	    }

	    if (!bandaRepository.existsById(bandaId)) {
	        bandaService.cadastrarBanda(banda);
	        albumService.cadastrarAlbuns(albuns, banda);
	    }

	    avaliacaoRepository.save(avaliacao);
	    bandaService.atualizarBanda(banda, albuns);

	    return avaliacao;
	}
	
	
	public Avaliacao atualizarAvaliacao(Avaliacao avaliacao, String token) {
	    List<Album> albuns = avaliacao.getBanda().getAlbuns();
	    Banda banda = avaliacao.getBanda();
			    
	    if (!avaliacaoRepository.existsById(avaliacao.getId())) {
	    	 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada!", null);
	    }
	    
    	if (!usuarioRepository.existsById(avaliacao.getUsuario().getId())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliaçao precisa de um usuário válido!", null);
    	}
		
    	if (!usuarioService.tokenUserIdEqualsUserId(avaliacao.getUsuario(), token)) {
    		throw new IllegalArgumentException("Permissão inválida: não é possível atualizar uma avaliação para outro usuário");
    	}
    	
    	avaliacaoRepository.save(avaliacao);
    	bandaService.atualizarBanda(banda, albuns);
        return avaliacao;
			        
			       
	}
		    	

		
	   
	
	

}
	
	


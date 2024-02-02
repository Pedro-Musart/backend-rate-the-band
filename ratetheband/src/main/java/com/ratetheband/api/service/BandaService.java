package com.ratetheband.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ratetheband.api.model.Album;
import com.ratetheband.api.model.Banda;
import com.ratetheband.api.repository.AlbumRepository;
import com.ratetheband.api.repository.AvaliacaoRepository;
import com.ratetheband.api.repository.BandaRepository;

@Service
public class BandaService {

	@Autowired
	private BandaRepository bandaRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	public Banda cadastrarBanda(Banda banda) {
		banda.setAlbuns(null);
		return bandaRepository.save(banda);
	}
	
	public Banda atualizarBanda(Banda banda, List<Album> albuns) {
		banda.setId(banda.getId());
		banda.setMediaNota(avaliacaoRepository.findAverageRatingByBandaId(banda.getId()));
		banda.setQtdAvaliacoes(avaliacaoRepository.findNumberOfRatingsByBandaId(banda.getId()));
		banda.setAvaliacoes(avaliacaoRepository.findAllByBandaId(banda.getId()));
		banda.setAlbuns(albumRepository.findAllByBandaId(banda.getId()));
		
		return bandaRepository.save(banda);
	}
	
	
	
}

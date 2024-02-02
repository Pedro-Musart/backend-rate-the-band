package com.ratetheband.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ratetheband.api.model.Album;
import com.ratetheband.api.model.Banda;
import com.ratetheband.api.repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	private AlbumRepository albumRepository;

	public Album cadastrarAlbum(Album album, Banda banda ) {
		album.setBanda(banda);
		
		return albumRepository.save(album);
	}
	
	public List<Album> cadastrarAlbuns(List<Album> albuns, Banda banda ) {
		
    	for(int i = 0; i != albuns.size() ; i++) {
    		Album album = albuns.get(i);
    		if(!albumRepository.existsById(album.getId())) {
    	   		album.setBanda(banda);
        		albumRepository.save(album);
    		}
    		
    	}
		
		return albuns;
	}
	
	
}

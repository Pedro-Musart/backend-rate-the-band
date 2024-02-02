package com.ratetheband.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ratetheband.api.model.Album;

public interface AlbumRepository  extends JpaRepository<Album, Long> {
	
	@Query("SELECT a FROM Album a WHERE a.banda.id = :bandaId")
	public List<Album> findAllByBandaId(@Param("bandaId")Long bandaId);

}

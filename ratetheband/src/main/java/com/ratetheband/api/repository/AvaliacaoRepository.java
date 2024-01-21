package com.ratetheband.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ratetheband.api.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	public List<Avaliacao> findAllByBandaId(@Param("bandaId")Long bandaId);
	
	public List<Avaliacao> findByBandaIdAndUsuarioId(@Param("bandaId") Long bandaId, @Param("usuarioId") Long userId);
	
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.bandaId = :bandaId")
    Double findAverageRatingByBandaId(@Param("bandaId") Long bandaId);
}

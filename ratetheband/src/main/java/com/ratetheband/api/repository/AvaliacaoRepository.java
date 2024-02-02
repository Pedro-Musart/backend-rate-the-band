package com.ratetheband.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ratetheband.api.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	@Query("SELECT a FROM Avaliacao a WHERE a.banda.id = :bandaId")
	public List<Avaliacao> findAllByBandaId(@Param("bandaId")Long bandaId);
	
    @Query("SELECT a FROM Avaliacao a WHERE a.usuario.id = :usuarioId AND a.banda.id = :bandaId")
    List<Avaliacao> findAllByUsuarioIdAndBandaId(@Param("bandaId") Long bandaId, @Param("usuarioId") Long usuarioId);
	
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.banda.Id = :bandaId")
    Double findAverageRatingByBandaId(@Param("bandaId") Long bandaId);
    
    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.banda.id = :bandaId")
    Long findNumberOfRatingsByBandaId(@Param("bandaId") Long bandaId);
 
    
    

}

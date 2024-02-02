package com.ratetheband.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ratetheband.api.model.Banda;

public interface BandaRepository extends JpaRepository<Banda, Long> {
	
	@Query("SELECT b FROM Banda b WHERE b.id IN :bandaIds")
	List<Banda> findBandasByIds(@Param("bandaIds") List<Long> bandaIds);


}

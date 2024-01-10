package com.ratetheband.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ratetheband.api.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	
}

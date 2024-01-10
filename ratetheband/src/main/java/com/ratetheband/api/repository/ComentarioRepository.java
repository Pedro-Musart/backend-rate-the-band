package com.ratetheband.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ratetheband.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	
}

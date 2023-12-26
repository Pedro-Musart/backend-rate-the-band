package com.ratetheband.api.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "tb_comentarios")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message = "O campo descrição não pode estar vazio")
	private String descricao;
	
	@UpdateTimestamp
	private LocalDateTime createdDate;
	
	@NotNull(message = "o campo bandaId não pode estar vazio!")
	private Long bandaId;
	
//	@ManyToOne
//	@JsonIgnoreProperties("avaliacoes")
//	private Usuario usuario;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Long getBandaId() {
		return bandaId;
	}

	public void setBandaId(Long bandaId) {
		this.bandaId = bandaId;
	}
	

	
	
}

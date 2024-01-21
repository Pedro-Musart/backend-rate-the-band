package com.ratetheband.api.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table (name= "tb_avaliacoes")
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@PositiveOrZero(message = "O valor nota precisa ser igual ou maior que zero")
	@Min(value = 1, message = "O valor mínimo permitido para uma avaliação é 1")
	@Max(value = 5, message = "O valor máximo permitido para uma avaliação é 5")
	private Long nota = 0L;
	
	@Size(max = 3000, message = "O atributo descrição deve conter no máximo 3000 caracteres")
	private String descricao;
	
	@UpdateTimestamp
	private LocalDateTime createdDate;
	
	@NotNull(message = "o campo bandaId não pode estar vazio!")
	private Long bandaId;
	
	@ManyToOne
	@JsonIgnoreProperties("avaliacoes")
	private Usuario usuario;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNota() {
		return nota;
	}

	public void setNota(Long nota) {
		this.nota = nota;
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
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

	
	
}

package com.ratetheband.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="tb_bandas")
public class Banda {
	
	@Id
	@Min(value = 1, message = "O valor mínimo permitido para o id é 1")
	@NotNull(message = "o campo banda.id não pode estar vazio!")
	private Long id;
	
	@NotNull(message = "O Atributo Nome é Obrigatório!")
	@Size(min = 1,  message = "O banda.nome deve ter no minimo 1 e no máximo 75 caracteres")
	private String nome;
	
	@PositiveOrZero(message = "O valor banda.mediaNota precisa ser igual ou maior que zero")
	private Double mediaNota = 0D;
	
	@PositiveOrZero(message = "O valor banda.qtdAvaliacoes precisa ser igual ou maior que zero")
	private Long qtdAvaliacoes = 1L;
	
	@OneToMany
	@JsonIgnoreProperties("banda")
	private List<Avaliacao> avaliacoes;
	
	@OneToMany
	@JsonIgnoreProperties("banda")
	private List<Album> albuns;

    public Banda() {
    }


    public Banda(Long id, Double media, Long qtdAvaliacoes) {
        this.id = id;
        this.mediaNota = media;
        this.qtdAvaliacoes = qtdAvaliacoes;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Double getMediaNota() {
		return mediaNota;
	}

	public void setMediaNota(Double mediaNota) {
		this.mediaNota = mediaNota;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	public List<Album> getAlbuns() {
		return albuns;
	}


	public void setAlbuns(List<Album> albuns) {
		this.albuns = albuns;
	}


	public Long getQtdAvaliacoes() {
		return qtdAvaliacoes;
	}

	public void setQtdAvaliacoes(Long qtdAvaliacoes) {
		this.qtdAvaliacoes = qtdAvaliacoes;
	}
	
	
	
	

}

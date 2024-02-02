package com.ratetheband.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="tb_albuns")
public class Album {

	
	@Id
	@Min(value = 1, message = "O valor mínimo permitido para o id é 1")
	@NotNull(message = "o campo album.id não pode estar vazio!")
	private Long id;
	
	@NotNull(message = "O Atributo Nome é Obrigatório!")
	@Size(min = 1, message = "O album.nome deve ter no minimo 1 caracteres")
	private String nome;
	
	@Size(max = 5000, message = "O album.imagem não pode ser maior do que 5000 caracteres")
	private String imagem;
	
	@Size(max = 5000, message = "O album.imagem_medium não pode ser maior do que 5000 caracteres")
	private String imagem_medium;
	
	@Size(max = 5000, message = "O album.imagem_big não pode ser maior do que 5000 caracteres")
	private String imagem_big;
	
	@ManyToOne
	@JsonIgnoreProperties("albuns")
	private Banda banda;

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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getImagem_medium() {
		return imagem_medium;
	}

	public void setImagem_medium(String imagem_medium) {
		this.imagem_medium = imagem_medium;
	}

	public String getImagem_big() {
		return imagem_big;
	}

	public void setImagem_big(String imagem_big) {
		this.imagem_big = imagem_big;
	}

	public Banda getBanda() {
		return banda;
	}

	public void setBanda(Banda banda) {
		this.banda = banda;
	}
	
	
	
	

}

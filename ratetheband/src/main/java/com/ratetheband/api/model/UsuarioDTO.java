package com.ratetheband.api.model;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioDTO {

	private Long id;
	private String nome;
	private String username;
	private String foto;
	private LocalDateTime createdDate;
	private List<Avaliacao> avaliacoes;
	
	
	public UsuarioDTO(Long id, String nome, String username, String foto,LocalDateTime createdDate) {
		super();
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.createdDate = createdDate;
		this.foto = foto;
	}

	public UsuarioDTO(Long id, String nome, String username, String foto, LocalDateTime createdDate,
			List<Avaliacao> avaliacoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.foto = foto;
		this.createdDate = createdDate;
		this.avaliacoes = avaliacoes;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
		
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}
	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	
	

}

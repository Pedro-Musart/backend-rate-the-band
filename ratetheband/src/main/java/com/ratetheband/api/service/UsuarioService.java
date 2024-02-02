package com.ratetheband.api.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ratetheband.api.model.Usuario;
import com.ratetheband.api.model.UsuarioDTO;
import com.ratetheband.api.model.UsuarioLogin;
import com.ratetheband.api.repository.UsuarioRepository;
import com.ratetheband.api.security.JwtService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    
    public List<UsuarioDTO> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getUsername(),
                    usuario.getFoto(),
                    usuario.getCreatedDate()
            );
            usuariosDTO.add(usuarioDTO);
        }

        return usuariosDTO;
    }

    
    public Optional<UsuarioDTO> buscarUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getUsername(),
                    usuario.getFoto(),
                    usuario.getCreatedDate(),
                    usuario.getAvaliacoes()
            );

            return Optional.of(usuarioDTO);
        } else {
            return Optional.empty();
        }
    }
    
    public List<UsuarioDTO> buscarUsuariosPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findAllByNomeContainingIgnoreCase(nome);
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getUsername(),
                    usuario.getFoto(),
                    usuario.getCreatedDate()
            );
            usuariosDTO.add(usuarioDTO);
        }

        return usuariosDTO;
    }


	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		
		return Optional.of(usuarioRepository.save(usuario));
	
	}
	
	public Boolean tokenUserIdEqualsUserId(Usuario usuario, String token){
		token = token.substring(7);
		Long tokenUserId = jwtService.extractUserId(token);
		
		return usuario.getId().equals(tokenUserId);
	}

	public Usuario atualizarUsuario(Usuario usuario,  String token) {

		
		if(!usuarioRepository.findById(usuario.getId()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!", null);
		}
		if(!tokenUserIdEqualsUserId(usuario,token)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão inválida: não é possível atualizar outro usuário");
		}
	
		Optional<Usuario> buscaEmail = usuarioRepository.findByEmail(usuario.getEmail());

		if ( (buscaEmail.isPresent()) && ( buscaEmail.get().getId() != usuario.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já existe!", null);
		}

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return (usuarioRepository.save(usuario));
		
	
	}	

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
        
        // Gera o Objeto de autenticação
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getSenha());
		
        // Autentica o Usuario
		Authentication authentication = authenticationManager.authenticate(credenciais);
        
        // Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
			Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLogin.get().getEmail());
			

            // Se o usuário foi encontrado
			if (usuario.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados 
				usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setUsername(usuario.get().getUsername());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getId()));
                usuarioLogin.get().setSenha("");
				
                 // Retorna o Objeto preenchido
			   return usuarioLogin;
			
			}

        } 
            
		return Optional.empty();

    }

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}

	private String gerarToken(String userEmail, Long userId) {
		return "Bearer " + jwtService.generateToken(userEmail, userId);
	}

}
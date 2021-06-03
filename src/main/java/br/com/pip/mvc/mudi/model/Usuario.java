package br.com.pip.mvc.mudi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Usuario {
	
	public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean habilitado = true;
	private String nome;
	private String email;
	
	@SuppressWarnings("unused")
	private String senha;
	
	@ElementCollection
	private List<String> papeis = new ArrayList<>();
	
	// mappedBy = o dono do relacionamento bidireiconal é a outra entidade, com atributo "usuario"
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Usuario() {
		papeis.add("USUARIO");
	}
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSenha(String senha) {
		this.senha = passwordEncoder.encode(senha);
	}
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public UserDetails toUserDetails() {
		UserDetails details =
				 User.builder()
					.username(email)
					.password(senha)
					.roles(papeis.toArray(new String[0]))
					.build();
		return details;
	}	
	
}

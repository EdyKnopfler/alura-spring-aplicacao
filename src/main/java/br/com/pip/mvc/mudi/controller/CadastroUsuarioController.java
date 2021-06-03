package br.com.pip.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pip.mvc.mudi.dto.RequisicaoNovoUsuario;
import br.com.pip.mvc.mudi.model.Usuario;
import br.com.pip.mvc.mudi.repository.UsuarioRepository;

@Controller
@RequestMapping("/signup")
public class CadastroUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public String formCadastro(RequisicaoNovoUsuario dados) {
		return "usuario/cadastro";
	}

	@PostMapping
	public String salvar(@Valid RequisicaoNovoUsuario requisicao, BindingResult validacao) {
		if (validacao.hasErrors()) {
			return "usuario/cadastro";
		}
		
		Usuario usuario = requisicao.toUsuario();
		usuarioRepository.save(usuario);
		
		// Já logamos o usuário :)
		UserDetails userDetails = usuario.toUserDetails();
		Authentication auth = 
				  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return "redirect:/usuario/pedidos";
	}
	
	
}

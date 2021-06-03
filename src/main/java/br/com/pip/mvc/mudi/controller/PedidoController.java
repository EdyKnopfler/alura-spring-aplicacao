package br.com.pip.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pip.mvc.mudi.dto.RequisicaoNovoPedido;
import br.com.pip.mvc.mudi.model.Pedido;
import br.com.pip.mvc.mudi.model.Usuario;
import br.com.pip.mvc.mudi.repository.PedidoRepository;
import br.com.pip.mvc.mudi.repository.UsuarioRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("novo")
	public String novo(RequisicaoNovoPedido requisicao) {  // Só pedir uma novinha para o Spring
		return "pedido/formulario";
	}
	
	@PostMapping("novo")
	public String criar(@Valid RequisicaoNovoPedido requisicao, BindingResult validacao) {
		if (validacao.hasErrors()) {
			// O objeto volta para a página com o nome "requisicaoNovoPedido" (o nome da classe)
			// (o cara não criou esse objeto no link GET do botão Novo na mesma aula)
			return "pedido/formulario";
		}
		
		// Outra forma de obter o usuário logado 
		// (lembrar que estamos usando o e-mail como username no Spring Security)
		String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
		
		Pedido pedido = requisicao.toPedido();
		pedido.setUsuario(usuario);
		pedidoRepository.save(pedido);
		return "redirect:/usuario/pedidos";
	}

}

package br.com.pip.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pip.mvc.mudi.model.Pedido;
import br.com.pip.mvc.mudi.model.StatusPedido;
import br.com.pip.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/usuario/pedidos")
public class UserHomeController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
		
	@GetMapping
	public String todos(Model model, Principal principal) {
		
		// Obtendo o usuário logado 
		// (é o e-mail que é fornecido como username em WebSecurityConfig.java)
		String email = principal.getName();
		
		List<Pedido> pedidos = pedidoRepository.findAllByUsuarioEmail(email);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", "");
		return "usuario/pedidos";
	}

	// Parâmetros na URL
	@GetMapping("/{status}")
	public String porStatus(@PathVariable("status") String status, Principal principal, Model model) {
		String email = principal.getName();
		List<Pedido> pedidos = pedidoRepository.findByStatusAndUsuarioEmail(
				StatusPedido.valueOf(status.toUpperCase()), email);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", status);
		return "usuario/pedidos";
	}
	
	// Em caso de status inválido na URL
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/";
	}
	
}

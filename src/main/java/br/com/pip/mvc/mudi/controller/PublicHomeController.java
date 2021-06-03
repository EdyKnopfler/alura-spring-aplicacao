package br.com.pip.mvc.mudi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.pip.mvc.mudi.model.Pedido;
import br.com.pip.mvc.mudi.model.StatusPedido;
import br.com.pip.mvc.mudi.repository.PedidoRepository;

@Controller
public class PublicHomeController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("/")
	public String paginaInicial(Model model) {
		int pagina = 1;
		List<Pedido> pedidos = consultarEntregues(pagina);
		exibirDados(model, pagina, pedidos);
		return "inicial";
	}

	@GetMapping("/home/{pagina}")
	public String mostrarPagina(@PathVariable("pagina") Integer pagina, Model model) {
		List<Pedido> pedidos = consultarEntregues(pagina);
		exibirDados(model, pagina, pedidos);
		return "inicial";
	}

	public List<Pedido> consultarEntregues(Integer pagina) {
		Sort sort = Sort.by("dataEntrega").descending();
		StatusPedido status = StatusPedido.ENTREGUE;
		PageRequest page = PageRequest.of(pagina - 1, 10, sort);
		List<Pedido> pedidos = pedidoRepository.findByStatus(status, page);
		return pedidos;
	}

	private void exibirDados(Model model, int pagina, List<Pedido> pedidos) {
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("pagina", pagina);
	}

}

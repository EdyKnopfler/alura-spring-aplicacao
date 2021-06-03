package br.com.pip.mvc.mudi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pip.mvc.mudi.model.Pedido;
import br.com.pip.mvc.mudi.model.StatusPedido;
import br.com.pip.mvc.mudi.repository.PedidoRepository;

// Devolve os dados como JSON
@RestController
@RequestMapping("/api/pedidos")
public class PedidosRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	// Aqui devolvo o tipo que quiser :)
	@GetMapping("/aguardando")
	private List<Pedido> getPedidosAguardandoOfertas() {
		Sort sort = Sort.by("id").descending();
		StatusPedido status = StatusPedido.AGUARDANDO;
		PageRequest page = PageRequest.of(0, 10, sort);
		
		// A conversão para JSON não funciona porque a relação com Usuario é LAZY, então temos como opções:
		// - trocar para EAGER
		// - criar um DTO para devolver na requisição
		// - anotar o usuário com @JsonIgnore no pedido (escolhi esta!)
		return pedidoRepository.findByStatus(status, page);
	}

}

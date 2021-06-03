package br.com.pip.mvc.mudi.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pip.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.pip.mvc.mudi.model.Oferta;
import br.com.pip.mvc.mudi.model.Pedido;
import br.com.pip.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {

	@Autowired
	private PedidoRepository pedidoRepository;

	// Estava tomando 403 mesmo com o padrão /api/** autorizado por causa do CSRF!
	// Idealmente eu faria isto autenticado, associado ao usuário que está logado.
	// Não é o tipo de caso de uso para uma API pública, acho.
	@PostMapping
	public Oferta criaOferta(@RequestBody @Valid RequisicaoNovaOferta requisicao) {
		
		System.out.println("chamando api de ofertas...");
		
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
		
		if (!pedidoBuscado.isPresent()) {
			return null;
		}

		Pedido pedido = pedidoBuscado.get();
		Oferta oferta = requisicao.toOferta();
		oferta.setPedido(pedido);
		
		// Já salva a oferta por causa do CASCADE
		pedidoRepository.save(pedido);
		
		return oferta;
	}

}

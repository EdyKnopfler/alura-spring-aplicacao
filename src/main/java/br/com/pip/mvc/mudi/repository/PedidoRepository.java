package br.com.pip.mvc.mudi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pip.mvc.mudi.model.Pedido;
import br.com.pip.mvc.mudi.model.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	/*** equivalentes ****/
	// leiteCondensadoCaramelizadoComFlocosCrocantesCobertoComDeliciosoChocolateNestrê
	//List<Pedido> findByStatusOrderByDataEntregaDesc(StatusPedido status);
	
	//@Query("SELECT p FROM Pedido p WHERE p.status = 'ENTREGUE' ORDER BY p.dataEntrega DESC")
	//List<Pedido> listaEntregues();
	
	// Requer @EnableCaching na classe da aplicação (main).
	// Veja mais no pom.xml, ehcache.xml e application.properties.
	// Se muitos usuários estão batendo na home page, podemos entregar um cache e expirá-lo de vez em quando.
	// Também é possível invalidar o cache se o dado mudar (e houver necessidade de sempre mostrar o último dado).
	@Cacheable(value = "paginaHome")
	List<Pedido> findByStatus(StatusPedido status, Pageable page); 
	
	//@Query("SELECT p FROM Pedido p JOIN p.usuario u WHERE u.email = :email")
	//List<Pedido> findAllByUsuarioEmail(@Param("email") String email);
	List<Pedido> findAllByUsuarioEmail(String email);
	
	List<Pedido> findByStatusAndUsuarioEmail(StatusPedido status, String email);
	
}

/*
@Repository
public class PedidoRepository {
	
	@PersistenceContext
	EntityManager em;
	
	public List<Pedido> recuperaTodosPedidos() {
		return em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
	}

}
*/

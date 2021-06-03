package br.com.pip.mvc.mudi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import br.com.pip.mvc.mudi.model.Oferta;

public class RequisicaoNovaOferta {
	
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@NotNull
	private Long pedidoId;

	@NotNull @Pattern(regexp = "^\\d+(\\.\\d{2})?$")
	private String valor;
	
	@NotNull @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}")
	private String dataEntrega;
	
	private String comentario;
	
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public Oferta toOferta() {
		Oferta oferta = new Oferta();
		oferta.setComentario(comentario);
		oferta.setDataEntrega(LocalDate.parse(dataEntrega, dateFormatter));
		oferta.setValor(new BigDecimal(valor));
		return oferta;
	}
	
	

}

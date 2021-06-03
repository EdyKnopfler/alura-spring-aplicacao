
function ofertaLoaded() {
	var app = new Vue({
	    el: '#oferta',
	    data: {
	        pedidos: []
	    },
	    mounted() {
		    axios
		      	.get('/api/pedidos/aguardando')
		      	.then(response => {
		      		// O Vue.js só processa se todas as variáveis já existirem
		      		// (podia aproveitar melhor o JavaScript...)
		      		response.data.forEach(pedido => {
		      			pedido.ofertaEnviada = false;
		      			pedido.erros = {valor: '', dataEntrega: ''};
		      		});
		      		this.pedidos = response.data;
		      	})
		      	.catch(error => {
					console.log('ERRO', error);
				});
		},
		methods: {
			enviarOferta(pedido) {
				axios
					.post('/api/ofertas', {
						pedidoId: pedido.id,
						valor: pedido.valorNegociado,
						dataEntrega: pedido.dataEntrega,
						comentario: pedido.comentario
					})
					.then(response => {
						pedido.ofertaEnviada = true;
						pedido.erros = {valor: '', dataEntrega: ''};
					})
					.catch(error => {
						error.response.data.errors.forEach(error => {
							pedido.erros[error.field] = error.defaultMessage;
						});
					});
			}
		}
	});
}

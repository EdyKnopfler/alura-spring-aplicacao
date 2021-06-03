package br.com.pip.mvc.mudi.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

// Configuração na classe WebConfig do pacote da aplicação

// HandlerInterceptorAdapter está depreciado!
// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html
@Configuration
public class InterceptadorAcessos implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Acesso acesso = new Acesso();
		acesso.path = request.getRequestURI();
		acesso.data = LocalDateTime.now();
		
		request.setAttribute("acesso", acesso);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		Acesso acesso = (Acesso)request.getAttribute("acesso");
		acesso.duracao = Duration.between(acesso.data, LocalDateTime.now());
		
		// Poderia colocar em banco de dados
		// acessoRepository.save(acesso);
		System.out.println(acesso.data + " - " + acesso.duracao + " - " + acesso.path);
	}
	
	class Acesso {
		private String path;
		private LocalDateTime data;
		private Duration duracao;
	}

}

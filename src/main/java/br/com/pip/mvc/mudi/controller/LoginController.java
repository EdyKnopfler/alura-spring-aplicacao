package br.com.pip.mvc.mudi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	// O Spring Security já sabe receber um POST para /login
	// (veja WebSecurityConfig, no pacote principal)
	
}

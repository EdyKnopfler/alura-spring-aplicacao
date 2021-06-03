package br.com.pip.mvc.mudi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.pip.mvc.mudi.model.Usuario;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers(
					"/", 
					"/home/**", 
					"/signup", 
					"/css/*.css",
					"/js/*.js", 
					"/font/*.ttf", 
					"/api/**"
			)
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().ignoringAntMatchers("/api/**")
			.and()
		.formLogin()
			.loginPage("/login")
			
			// Sem isto ele sempre voltava para a página anterior (fiquei horas batendo cabeça) 
			.defaultSuccessUrl("/usuario/pedidos", true)
			
			.permitAll()
			.and()
		.logout()
			.logoutSuccessUrl("/")
			.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(Usuario.passwordEncoder)
			
			// Informando o schema pro PostgreSQL (com o SQL default o schema não é informado).
			// Já que é assim, crio o modelo do usuário como eu quiser!
			.usersByUsernameQuery(
                    "select email, senha, habilitado from mudi.usuario where email=?")
            .authoritiesByUsernameQuery(
                    "select u.email, p.papeis " +
                    "from mudi.usuario u join mudi.usuario_papeis p " +
                    "   on u.id = p.usuario_id " +
                    "where u.email=?");
		
	}
	
}

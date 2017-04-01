package es.urjc.code.daw.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RESTSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		http.antMatcher("/api/**");
    	
    	// Public pages
		http.authorizeRequests().anyRequest().permitAll();
        
        // Métodos que requieren autenticación
        
        		/* Usuario */
        http.authorizeRequests().antMatchers(HttpMethod.PUT ,"/api/users/avatar").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/users/{id}").hasRole("ADMIN");
        
        		/* Tags */
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/tags/{id}").hasAnyRole("ADMIN"); //Los tags no pueden estar vacios o sino peta
        
        		/* Comentario */
        http.authorizeRequests().antMatchers(HttpMethod.PUT ,"/api/comentarios/{id}").hasAnyRole("USER","ADMIN"); //Hay que comprobar si el propietario del comentario lo está usando
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/comentarios/{id}").hasAnyRole("USER","ADMIN"); //Necesita comprobar que el usuario original lo están borrando
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/comentarios/vineta/{id}").hasAnyRole("USER","ADMIN");
        
        		/* Viñetas */
        http.authorizeRequests().antMatchers(HttpMethod.POST ,"/api/vinetas").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/vinetas/{id}").hasAnyRole("USER", "ADMIN");
        
        // Disable CSRF protection (it is difficult to implement with ng2)
     	http.csrf().disable();
     	
     	//Login con Basic Auth
     	http.httpBasic();
     	

     	// Do not redirect when logout
     	http.logout().logoutSuccessHandler((rq, rs, a) -> {	});

    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}

}

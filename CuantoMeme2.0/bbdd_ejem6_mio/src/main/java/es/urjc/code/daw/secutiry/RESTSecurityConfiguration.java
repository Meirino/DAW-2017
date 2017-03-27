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
		
				/* Usuario */
        http.authorizeRequests().antMatchers(HttpMethod.POST ,"/api/signup").permitAll();
        
        		/* Tags */
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/tags").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/tags/{nombre}").permitAll(); //Da problemas
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/tags/{id}").permitAll();
        
        		/* Comentario */
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/comentarios").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/comentarios/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/comentariosByUser/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/comentariosByVineta/{id}").permitAll();
        
        		/* Viñetas */
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/vinetaspage/").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET ,"/api/vinetas/{id}").permitAll();
        
        // Métodos que requieren autenticación
        
        		/* Tags */
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/tags/{id}").hasAnyRole("ADMIN"); //Hay que comprobar si el propietario del comentario lo está usando
        
        		/* Comentario */
        http.authorizeRequests().antMatchers(HttpMethod.PUT ,"/api/comentarios/{id}").hasAnyRole("ADMIN"); //Hay que comprobar si el propietario del comentario lo está usando
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/api/comentarios/{id}").hasAnyRole("ADMIN"); //Necesita comprobar que el usuario original lo están borrando
        
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

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
		
		http.antMatcher("/api/**").csrf().disable();
    	
    	// Public pages
        http.authorizeRequests().antMatchers(HttpMethod.POST ,"/api/signup").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/upload").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/test").permitAll();
       
        // Private pages (all other pages)
        
        // Disable CSRF protection (it is difficult to implement with ng2)
     	http.csrf().disable();

     	// Do not redirect when logout
     	http.logout().logoutSuccessHandler((rq, rs, a) -> {	});

    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}

}

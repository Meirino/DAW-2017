package es.urjc.code.daw.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/registro").permitAll();
        http.authorizeRequests().antMatchers("/signup").permitAll();
       
        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/home").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/crearComentario/vineta/{id}").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/likevineta/{id}").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/dislikevineta/{id}").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/hacerfavorita/{id}").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/mislikes").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/misdislikes").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/misfavoritos").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/seguirperfil/{id}").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/dejarseguirperfil/{id}").hasAnyRole("USER");  
        
        //Admin  authorize request:
        http.authorizeRequests().antMatchers("/eliminarperfil/{id}").hasAnyRole("ADMIN");  
        http.authorizeRequests().antMatchers("/eliminarcomentario/{id}").hasAnyRole("ADMIN"); 
        http.authorizeRequests().antMatchers("/eliminarvineta/{id}").hasAnyRole("ADMIN"); 

        
        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        //http.formLogin().defaultSuccessUrl("/home");
        http.formLogin().failureUrl("/loginerror");

        // Logout
        http.logout().logoutUrl("/logout").deleteCookies("JSESSIONID", "remember-me");
        http.logout().logoutSuccessUrl("/");
        //http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }
}

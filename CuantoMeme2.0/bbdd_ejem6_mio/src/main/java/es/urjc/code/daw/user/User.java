
package es.urjc.code.daw.user;
import es.urjc.code.daw.vineta.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import es.urjc.code.daw.comentario.*;

@Entity
public class User {
	public interface BasicAtt {}
	public interface VinetaAtt{}
	public interface ComentarioAtt{}
	public interface SeguidoresAtt{}
	
	@Id
	
	@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@JsonView(BasicAtt.class)
	private String username;
	@JsonIgnore
	private String passwordHash;
	@JsonIgnore
	//@JsonView(BasicAtt.class)
	private String email;
	@JsonIgnore
	//@JsonView(BasicAtt.class)
	private String AvatarURL;
	@JsonIgnore
	//@JsonView(VinetaAtt.class)	
	@OneToMany(mappedBy="autor", cascade=CascadeType.ALL)//, cascade=CascadeType.ALL)
	private List<Vineta> vinetas_subidas = new ArrayList<>();
	@JsonIgnore
	@ManyToMany(mappedBy="following")
	private List<User> followers = new ArrayList<>();
	@JsonIgnore
	@ManyToMany
	private List<User> following = new ArrayList<>();

	@JsonIgnore
	//@JsonView(ComentarioAtt.class)
	@OneToMany(mappedBy="autor_comentario", cascade = CascadeType.ALL)//, cascade=CascadeType.ALL)
	private List<Comentario> comentarios = new ArrayList<>();
	
	@JsonIgnore
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	@JsonIgnore
	//@JsonView(VinetaAtt.class)
	@ManyToMany
	private List<Vineta> vinetas_favoritas = new ArrayList<>();
	@JsonIgnore
	//@JsonView(VinetaAtt.class)
	@ManyToMany
	private List<Vineta> vinetas_gustadas = new ArrayList<>();
	@JsonIgnore
	//@JsonView(VinetaAtt.class)
	@ManyToMany
	private List<Vineta> vinetas_odiadas = new ArrayList<>();
	
	protected User(){}
	
	public User(String username, String password, String email, String... roles){
		this.username = username;
		this.passwordHash  = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.roles = new ArrayList<>(Arrays.asList(roles));
		this.AvatarURL = "http://blog.davidbyrnedesign.com/wp-content/uploads/2015/04/twitter-avatar.jpg";
	}
	
	public String getAvatarURL() {
		return AvatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		AvatarURL = avatarURL;
	}

	public List<Vineta> getVinetas_subidas() {
		return vinetas_subidas;
	}

	public void setVinetas_subidas(List<Vineta> vinetas_subidas) {
		this.vinetas_subidas = vinetas_subidas;
	}

	public List<Vineta> getVinetas_favoritas() {
		return vinetas_favoritas;
	}

	public void setVinetas_favoritas(List<Vineta> vinetas_favoritas) {
		this.vinetas_favoritas = vinetas_favoritas;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Vineta> getViñetas() {
		return vinetas_subidas;
	}

	public void setViñetas(List<Vineta> viñetas) {
		this.vinetas_subidas = viñetas;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public void setPassword(String password) {
		this.passwordHash = password;
	}

	public List<Vineta> getVinetas_gustadas() {
		return vinetas_gustadas;
	}

	public void setVinetas_gustadas(List<Vineta> vinetas_gustadas) {
		this.vinetas_gustadas = vinetas_gustadas;
	}

	public List<Vineta> getVinetas_odiadas() {
		return vinetas_odiadas;
	}

	public void setVinetas_odiadas(List<Vineta> vinetas_odiadas) {
		this.vinetas_odiadas = vinetas_odiadas;
	}
	
	public void addVineta(Vineta v){
		this.vinetas_subidas.add(v);
	}
	public void addFollower(User follower) {
		this.followers.add(follower);
	}
	public void addFollowing(User following) {
		if (!ifollow(following)){
			//System.out.println("aun no se sigue este usuario");
			this.following.add(following);
		}

	}
	
	public boolean ifollow(User usuario){
		return this.following.indexOf(usuario) != -1;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", email=" + email
				+ ", AvatarURL=" + AvatarURL + ", vinetas_subidas=" + vinetas_subidas + ", seguidores=" + followers
				+ ", comentarios=" + comentarios + ", roles=" + roles + ", vinetas_favoritas=" + vinetas_favoritas
				+ ", vinetas_gustadas=" + vinetas_gustadas + ", vinetas_odiadas=" + vinetas_odiadas + "]";
	}
	
	
	
}

package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMORoom;

import model.characters.MUFRCharacter;
import model.characters.MUFRJewels;

@Entity
@Table(name="user_info")
public class MUFRUser extends IDAO{
	
		@Id
	    @Column(name = "user_id")
	    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	    private Long id;
	 
	    @Basic
	    @Column(name = "user_name", nullable = false, length = 16, unique = true)
	    private String name;
	 
	    @Basic
	    @Column(name = "user_pwd", nullable = false, length = 256)
	    private String password;
	 
	    @Basic
	    @Column(name = "email", nullable = false, length = 50)
	    private String email;
	 
		@Basic
	    @Column(name = "country", nullable = true, length = 2)
	    private String countryCode;
	 	 
	    @Temporal(TemporalType.DATE)
	    @Column(name = "creation_date")
	    private Date creationDate;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "last_login")
	    private Date lastLogin;
	    
	    @Basic
	    @Column(name = "last_ip", length = 16)
	    private String lastIp;
	    
		@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
		private List<MUFRCharacter> characters;
		
		@OneToOne(mappedBy = "owner")
		private MUFRJewels jewels;
		
		@Basic
	    @Column(name = "acc_level", nullable = false,  length = 1)
	    private int account_level;

		public int getAccount_level() {
			return account_level;
		}

		public void setAccount_level(int account_level) {
			this.account_level = account_level;
		}

		public MUFRJewels getJewels() {
			return jewels;
		}

		public void setJewels(MUFRJewels jewels) {
			this.jewels = jewels;
		}

		@Transient
		private MUFRCharacter selectedCharacter;
		
		@Transient
		private MUFRUserVariables userVariables;
		
		@Transient 
		private User user;


		public MUFRUser() {
			userVariables = new MUFRUserVariables();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCountryCode() {
			return countryCode;
		}

		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public Date getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}

		public String getLastIp() {
			return lastIp;
		}

		public void setLastIp(String lastIp) {
			this.lastIp = lastIp;
		}
		
	    public List<MUFRCharacter> getCharacters() {
			return characters;
		}

		public void setCharacters(List<MUFRCharacter> characters) {
			this.characters = characters;
		}

		public MUFRCharacter getSelectedCharacter() {
			return selectedCharacter;
		}

		public void setSelectedCharacter(MUFRCharacter selectedCharacter) {
			this.selectedCharacter = selectedCharacter;
		}

		public MUFRUserVariables getUserVariables() {
			return userVariables;
		}

		public void setUserVariables(MUFRUserVariables userVariables) {
			this.userVariables = userVariables;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		
}

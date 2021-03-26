package model.characters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.MUFRUser;

@Entity
@Table(name="user_jewels")
public class MUFRJewels {
		
	@Id
	@OneToOne
	@JoinColumn(name = "owner", nullable = false)
	private MUFRUser owner;
	
	@Column(name = "zen", nullable = false)
	protected int zen;
	
	@Column(name = "chaos", nullable = false)
	protected int chaos;
	
	@Column(name = "bless", nullable = false)
	private int bless;
	
	public MUFRUser getOwner() {
		return owner;
	}

	public void setOwner(MUFRUser owner) {
		this.owner = owner;
	}

	public int getZen() {
		return zen;
	}

	public void setZen(int zen) {
		this.zen = zen;
	}

	public int getChaos() {
		return chaos;
	}

	public void setChaos(int chaos) {
		this.chaos = chaos;
	}

	public int getBless() {
		return bless;
	}

	public void setBless(int bless) {
		this.bless = bless;
	}

	public int getSoul() {
		return soul;
	}

	public void setSoul(int soul) {
		this.soul = soul;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	@Column(name = "soul", nullable = false)
	private int soul;
	
	@Column(name = "life", nullable = false)
	private int life;

}

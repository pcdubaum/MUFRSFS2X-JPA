package model.characters;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.PrivateOwned;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.mmo.MMORoom;

import logic.MUFRExtension;
import model.IDAO;
import model.MUFRItem;
import model.MUFRUser;
import model.MUFRUserVariables;

@Entity
@Table(name="char_info")
public class MUFRCharacter extends IDAO{
	
	@Id
	@Column(name = "char_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "cclass", nullable = false, length = 2)
	private int cclass;
	
	@Column(name = "level", nullable = false)
	private int level;
	
	@Column(name = "totalxp", nullable = false)
	private Long totalxp;
	
	@Column(name = "name", nullable = false, length = 16, unique = true)
	private String name;
	
	@Column(name = "strenght", nullable = false)
	protected int strenght;
	
	@Column(name = "vitality", nullable = false)
	private int vitality;
	
	@Column(name = "agility", nullable = false)
	private int agility;
	
	@Column(name = "energy", nullable = false)
	private int energy;
	
	@Column(name = "mana")
	private Long mana;
	
	@Column(name = "life")
	private Long life;
	
	@Column(name = "map", nullable = false)
	private int map;
	
	@Column(name = "posx", nullable = false, precision = 1, scale = 1)
	private double posx;
	
	@Column(name = "posy", nullable = false, precision = 1, scale = 1)
	private double posy;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner", nullable = false)
	private List<MUFRItem> item;
	
	@ManyToOne
	@JoinColumn(name = "owner", nullable = false)
	private MUFRUser owner;
	
	@Column(name = "pointsToAdd", nullable = false)
	private int pointsToAdd;
	
	@Column(name = "total_items", nullable = false)
	private int totalItems;
	
	@Column(name = "excelent_items", nullable = false)
	private int excelentItems;
	
	@Column(name = "legendary_items", nullable = false)
	private int legendary_items;
	
	@Column(name = "total_hours", nullable = false)
	private int totalHours;
	
	@Column(name = "monsters_count", nullable = false)
	private int monsters_count;
	
	@Column(name = "players_count", nullable = false)
	private int players_count;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;
		
 	@Transient
 	private User user;
 	
 	@Transient
 	private MMORoom room;
 	
 	@Transient
 	private MUFRItem startingWeapom;
 	
 	@Transient
 	private MUFRItem weapon;
 	
 	@Transient
 	private MUFRItem gloves;
 	
 	@Transient
 	private MUFRItem boots;
 	
 	@Transient
 	private MUFRItem pants;
 	
 	@Transient
 	private MUFRItem chest;
 	
 	@Transient
 	private MUFRItem helm;
 	
	@Transient
 	private int defense;
	
	@Transient
	private boolean alive;
 	
 	@Transient
 	private int totalHeath;

 	@Transient
 	private int totalMana;
 	
 	@Transient
 	private int heathRegen;
 	
 	@Transient
 	private int manaRegen;
 	
 	@Transient
 	private int nextLevelXp;
 	
 	@Transient 
 	private double deadCooldown;
 	
 	@Transient
 	private MUFRUserVariables variables;
 	
 	@Transient
	protected int minDamage;
 	
 	@Transient
 	protected int maxDamage;
 	
 	@Transient
	private static Random randomGenerator;
 	
 	public MUFRItem getWeapon() {
		return weapon;
	}

	public void setWeapon(MUFRItem weapon) {
		this.weapon = weapon;
	}

	public MUFRItem getGloves() {
		return gloves;
	}

	public void setGloves(MUFRItem gloves) {
		this.gloves = gloves;
	}

	public MUFRItem getBoots() {
		return boots;
	}

	public void setBoots(MUFRItem boots) {
		this.boots = boots;
	}

	public MUFRItem getPants() {
		return pants;
	}

	public void setPants(MUFRItem pants) {
		this.pants = pants;
	}

	public MUFRItem getChest() {
		return chest;
	}

	public void setChest(MUFRItem chest) {
		this.chest = chest;
	}

	public MUFRItem getHelm() {
		return helm;
	}

	public void setHelm(MUFRItem helm) {
		this.helm = helm;
	}

	public static Random getRandomGenerator() {
		return randomGenerator;
	}

	public static void setRandomGenerator(Random randomGenerator) {
		MUFRCharacter.randomGenerator = randomGenerator;
	}
	
	public int getNextLevelXp() {
		return nextLevelXp;
	}

	public void setNextLevelXp(int nextLevelXp) {
		this.nextLevelXp = nextLevelXp;
	}

	public MUFRUserVariables getVariables() {
		return variables;
	}

	public void setVariables(MUFRUserVariables variables) {
		this.variables = variables;
	}

	public MUFRCharacter()
	{
		CalculeNextLevelXp();
		randomGenerator = new Random();
	}
 	
 	public void calculateTotalDefense()
 	{
 		defense = 0;
 	
 		if(boots != null) {
 			defense += boots.getDefense();
 			defense += boots.getOpts();
 		}
 		
 		
 		if(gloves != null) {
 			defense += gloves.getDefense();
 			defense += gloves.getOpts();
 		}
 		
 		if(pants != null) {
 			defense += pants.getDefense();
 			defense += pants.getOpts();
 		}
 		
 		if(chest != null) {
 			defense += chest.getDefense();
 			defense += chest.getOpts();
 		}
 		
 		if(helm != null)
 		{
 			defense += helm.getDefense();
 			defense += helm.getOpts();
 		}
 		
 		
 	}
 	 	
	
 	public boolean addXp(int xp)
 	{
 		totalxp += xp;
 		
 		while(getTotalxp() >= nextLevelXp)
 		{
 			level++;
 			CalculeNextLevelXp();
 			pointsToAdd += 5;
 			
 			return true;
 		}
 		
 		return false;
 	}
 	
 	public void Init()
 	{
 		if(item == null)
 			item = new ArrayList<MUFRItem>();
 		
 		variables = new MUFRUserVariables();
 		//variables.GenerateVariables(this);
 		
 		CalculeNextLevelXp();
 		CalculeAtributes();
 		CalculeBaseDamage();
 		calculateTotalDefense();
 	}
 	
 	public void CalculeBaseDamage()
 	{
 		if(cclass == 1)
 		{
 			minDamage = strenght / 6;
 			maxDamage = strenght / 4;
 		}
 		else if(cclass == 2)
 		{
 			minDamage = energy / 9;
 			maxDamage = energy / 4;
 		}
 		else if(cclass == 3)
 		{
 			minDamage = (strenght + agility) / 14;
 			maxDamage = (strenght + agility) / 8;
 		}
 	}
 	
 	public void InitItem()
 	{
 		item = new LinkedList<MUFRItem>();
 	}
 	
 	public int ReturnBaseDamage()
 	{
 		int value = randomGenerator.nextInt(maxDamage - minDamage) + minDamage;
 		
 		if(getCclass() == 1 || getCclass() == 3)
		{
			if(getWeapom() != null)
			{
				MUFRItem weapon = getWeapom();
				
				value += randomGenerator.nextInt(weapon.getMaxAttack() - weapon.getMinAttack()) + weapon.getMinAttack();
			
				value += weapon.getOpts();
				
				weapon = null;
			}
		}
		else if(getCclass() == 2)
		{
			if(getWeapom() != null)
			{
				MUFRItem weapon = getWeapom();
				
				value += randomGenerator.nextInt(weapon.getMaxAttack() - weapon.getMinAttack()) + weapon.getMinAttack();
			
				value += weapon.getOpts();
				
				weapon = null;
			}
		}
 		
 		return value;
 	}
 	
 	public void CalculeAtributes()
 	{
 		CalculeHealthMana();
 	}
 	
 	public void CalculeHealthMana()
 	{
 		if(cclass == 1)
 		{
 			 totalHeath = 35 + (level - 1) * 2 + vitality * 3;
 			 
 			 if(life < 1)
 				 life = (long) totalHeath;
 			 
 			 totalMana = 10 + (level - 1) / 2 + energy;
 			 
             mana = (long) totalMana;
             
             manaRegen = (int)((float)totalMana / 27.5f);
 		} 
 		else if(cclass == 2)
 		{
            totalHeath = 30 + (level - 1) + vitality * 2;
            
            if(life < 1)
            	life = (long) totalHeath;
            	
            totalMana = (energy + level - 1) * 2;
            
            mana = (long) totalMana;
            
            manaRegen = (int)((float)totalMana / 27.5f);
 		} 
 		else if(cclass == 3)
 		{
            totalHeath = 40 + (level - 1) + vitality * 2;
            
            if(life < 1)
            	life = (long) totalHeath;
            
            totalMana = (int)((float)6 + (float)(level + energy) * 1.5f);
            
            mana = (long) totalMana;
            
            manaRegen = (int)((float)totalMana / 27.5f);
 		}
 	}
 	
 	public void CalculeSdAg()
 	{
 		if(cclass == 1)
 		{
 			
 		} 
 		else if(cclass == 2)
 		{
 			
 		} 
 		else if(cclass == 3)
 		{
           
 		}
 	}
 	
 	private void CalculeNextLevelXp()
 	{
 		nextLevelXp = (100 * level + level * 10 * (level - 1)) * level;
 	}
 	
 	public int getPointsToAdd() {
		return pointsToAdd;
	}

	public void setPointsToAdd(int pointsToAdd) {
		this.pointsToAdd = pointsToAdd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MMORoom getRoom() {
		return room;
	}

	public void setRoom(MMORoom room) {
		this.room = room;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getTotalHeath() {
		return totalHeath;
	}

	public void setTotalHeath(int totalHeath) {
		this.totalHeath = totalHeath;
	}

	public int getTotalMana() {
		return totalMana;
	}

	public void setTotalMana(int totalMana) {
		this.totalMana = totalMana;
	}

	public int getHeathRegen() {
		return heathRegen;
	}

	public void setHeathRegen(int heathRegen) {
		this.heathRegen = heathRegen;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
	}


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCclass() {
		return cclass;
	}

	public void setCclass(int cclass) {
		this.cclass = cclass;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Long getTotalxp() {
		return totalxp;
	}

	public void setTotalxp(Long totalxp) {
		this.totalxp = totalxp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrenght() {
		return strenght;
	}

	public void setStrenght(int strenght) {
		this.strenght = strenght;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public Long getMana() {
		return mana;
	}

	public void setMana(Long mana) {
		this.mana = mana;
	}

	public Long getLife() {
		return life;
	}

	public void setLife(Long life) {
		this.life = life;
	}

	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}
	

	public List<MUFRItem> getItem() {
		return item;
	}

	public void setItem(List<MUFRItem> item) {
		this.item = item;
	}



	public MUFRUser getOwner() {
		return owner;
	}

	public void setOwner(MUFRUser owner) {
		this.owner = owner;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public MUFRItem getStartingWeapom() {
		return startingWeapom;
	}

	public void setStartingWeapom(MUFRItem startingWeapom) {
		this.startingWeapom = startingWeapom;
	}

	public MUFRItem getWeapom() {
		return weapon;
	}

	public void setWeapom(MUFRItem weapom) {

		if(weapom == null)
			weapon = null;
		else
		{
			weapon = weapom;
		}
		
	}

	public double getDeadCooldown() {
		return deadCooldown;
	}

	public void setDeadCooldown(double deadCooldown) {
		this.deadCooldown = deadCooldown;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

}

package model;

import java.util.LinkedList;
import java.util.List;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMORoom;
import com.smartfoxserver.v2.mmo.Vec3D;

import model.enums.EnemyType;
import model.enums.MonsterBehaviour;

public class MUFRMonster {

	private MonsterBehaviour behaviour;
    private EnemyType enemyType;
    private boolean eventMonster;
    private int eventNumber;
    private String Name;
    private int id;
    private int gameId;
    
    private MUFRDropTable drops;
    
    public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	private int level;
    private int minDmg;
    private int maxDmg;
    private int life;
    private int currentLife;
    private int defense;
    private int defenseRate;
   
	private int attackType;
    private Double range;
    private Double attackCoolDown;
    private Double deadColldown;
    
    public Double getDeadColldown() {
		return deadColldown;
	}

	public void setDeadColldown(Double deadColldown) {
		this.deadColldown = deadColldown;
	}

	private double posX;
    private double posY;
    private int map;
    private Vec3D originalPos;
    
    public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	private boolean alive;
    private MMORoom room;
    
    private MMOItem item;
    
    
	public MonsterBehaviour getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(MonsterBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public MMORoom getRoom() {
		return room;
	}

	public void setRoom(MMORoom room) {
		this.room = room;
	}

	public MUFRMonster() {
		attackCoolDown = 1.0;
	}
	
	public MUFRMonster(MUFRMonster newMonster, Vec3D pos, int tmap)
	{
		drops = new MUFRDropTable();
		enemyType = newMonster.enemyType;
		eventMonster = newMonster.eventMonster;
		eventNumber = newMonster.eventNumber;
		Name = newMonster.Name;
		id = newMonster.id;
		level = newMonster.level;
		minDmg = newMonster.minDmg;
		maxDmg = newMonster.maxDmg;
		life = newMonster.life;
		currentLife = life;
		defense = newMonster.defense;
		defenseRate = newMonster.defenseRate;
		attackType = newMonster.attackType;
		range = newMonster.range;
		
		alive = true;
		
		posX = pos.intX();
		posY = pos.intZ();
		map = tmap;
	}
	
	public EnemyType getEnemyType() {
		return enemyType;
	}
	public void setEnemyType(EnemyType enemyType) {
		this.enemyType = enemyType;
	}
	public Boolean getEventMonster() {
		return eventMonster;
	}
	public void setEventMonster(Boolean eventMonster) {
		this.eventMonster = eventMonster;
	}
	public int getEventNumber() {
		return eventNumber;
	}
	public void setEventNumber(int eventNumber) {
		this.eventNumber = eventNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMinDmg() {
		return minDmg;
	}
	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}
	public int getMaxDmg() {
		return maxDmg;
	}
	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getCurrentLife() {
		return currentLife;
	}
	public void setCurrentLife(int currentLife) {
		this.currentLife = currentLife;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getDefenseRate() {
		return defenseRate;
	}
	public void setDefenseRate(int defenseRate) {
		this.defenseRate = defenseRate;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	
    public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public Double getRange() {
		return range;
	}

	public void setRange(double d) {
		this.range = d;
	}


	public Double getAttackCoolDown() {
		return attackCoolDown;
	}

	public void setAttackCoolDown(Double attackCoolDown) {
		this.attackCoolDown = attackCoolDown;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public MMOItem getItem() {
		return item;
	}

	public void setItem(MMOItem item) {
		this.item = item;
	}

	public void setEventMonster(boolean eventMonster) {
		this.eventMonster = eventMonster;
	}

	public Vec3D getOriginalPos() {
		return originalPos;
	}

	public void setOriginalPos(Vec3D originalPos) {
		this.originalPos = originalPos;
	}

	public void setRange(Double range) {
		this.range = range;
	}

	public MUFRDropTable getDrops() {
		return drops;
	}

	public void setDrops(MUFRDropTable drops) {
		this.drops = drops;
	}
    
}

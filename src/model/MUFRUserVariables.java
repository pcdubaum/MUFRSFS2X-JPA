package model;

import java.util.Arrays;
import java.util.List;

import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.mmo.MMOItemVariable;

import model.characters.MUFRCharacter;

public class MUFRUserVariables {

	private UserVariable userNameVar;
	private UserVariable classeVar;
	private UserVariable charIdVar;
	private UserVariable levelVar;
	private UserVariable totalXpVar;
	
	private UserVariable isAlive;
	private UserVariable heath;
	private UserVariable mana;
	
	private UserVariable strenghtVar;
	private UserVariable vitalityVar;
	private UserVariable agilityVar;
	private UserVariable energyVar;

	private UserVariable mapVar;
	private UserVariable posXVar;
	private UserVariable posZVar;
	
	private UserVariable pointsToAdd;

	private UserVariable weaponVar;
	//private UserVariable headVar;
	//private UserVariable chestVar;
	//private UserVariable legsVar;
	//private UserVariable footsVar;
	//private UserVariable handsVar;
	//private UserVariable offhandVar;

	public MUFRUserVariables() {

	}

	public void GenerateVariables(MUFRCharacter newChar) {
		
		charIdVar = new SFSUserVariable("charId", newChar.getId());
		userNameVar = new SFSUserVariable("userName", newChar.getName());
		classeVar = new SFSUserVariable("cc", newChar.getCclass());
		levelVar = new SFSUserVariable("lv", newChar.getLevel());
		totalXpVar = new SFSUserVariable("xp", newChar.getTotalxp());
		pointsToAdd = new SFSUserVariable("pa", newChar.getPointsToAdd());
		
		isAlive = new SFSUserVariable("ia", newChar.isAlive());
		heath = new SFSUserVariable("he", newChar.getLife().intValue());
		mana = new SFSUserVariable("ma", newChar.getMana().intValue());

		// Character Status
		strenghtVar = new SFSUserVariable("ss", newChar.getStrenght());
		vitalityVar = new SFSUserVariable("sv", newChar.getVitality());
		agilityVar = new SFSUserVariable("sa", newChar.getAgility());
		energyVar = new SFSUserVariable("se", newChar.getEnergy());
			
		if(newChar.getWeapom() != null)
			weaponVar = new SFSUserVariable("we", newChar.getWeapom().GetSFSObject());
		else
		{
			SFSObject returnObject = new SFSObject();
			
			returnObject.putInt("st", 0);
			returnObject.putInt("ty", 0);
			returnObject.putInt("id", -1);
			returnObject.putInt("sl", -1);
			returnObject.putInt("dbid", -1);
			returnObject.putInt("lv", 0);
			returnObject.putInt("op", 0);
			returnObject.putBool("lc", false);
			
			weaponVar = new MMOItemVariable("we", returnObject);
		}
		
		// Character Items
		//setWeaponVar(new SFSUserVariable("we", weapon.GetSFSObject()));
		//setHeadVar(new SFSUserVariable("e7", head.GetSFSObject()));
		//setChestVar(new SFSUserVariable("e8", chest.GetSFSObject()));
		//setLegsVar(new SFSUserVariable("e9", legs.GetSFSObject()));
		//setFootsVar(new SFSUserVariable("e11", foots.GetSFSObject()));
		//setHandsVar(new SFSUserVariable("e10", hands.GetSFSObject()));
		//setOffhandVar(new SFSUserVariable("oh", offhand.GetSFSObject()));
		
		posXVar = new SFSUserVariable("x", newChar.getPosx());
		posZVar = new SFSUserVariable("z", newChar.getPosy());
		mapVar = new SFSUserVariable("map", newChar.getMap());
		
		
		// Make some variables private
		// Private variable is no broadcasted to others user
		totalXpVar.setPrivate(true);
		pointsToAdd.setPrivate(true);
		
		heath.setPrivate(true);
		mana.setPrivate(true);
		
		strenghtVar.setPrivate(true);
		vitalityVar.setPrivate(true);
		agilityVar.setPrivate(true);
		energyVar.setPrivate(true);
		
		mapVar.setPrivate(true);
		
	}


	public List<UserVariable> GetUserVariables() {
		List<UserVariable> returnVars = Arrays.asList(
				getUserNameVar(), getStrenghtVar(),
				getVitalityVar(), getAgilityVar(), getEnergyVar(), 
				getClasseVar(), getWeaponVar(),
				/*getHeadVar(), getChestVar(), getLegsVar(), getFootsVar(), getHandsVar(),
				getWeaponVar(), getOffhandVar(), */
				getCharIdVar(), getPosXVar(), getPosZVar(), getLevelVar(),
				getMapVar(), getIsAlive(), getHeath(), getMana() ,getPointsToAdd());
		
		return returnVars;
	}

	public UserVariable getUserNameVar() {
		return userNameVar;
	}

	public void setUserNameVar(UserVariable userNameVar) {
		this.userNameVar = userNameVar;
	}

	public UserVariable getClasseVar() {
		return classeVar;
	}

	public void setClasseVar(UserVariable classeVar) {
		this.classeVar = classeVar;
	}

	public UserVariable getCharIdVar() {
		return charIdVar;
	}

	public void setCharIdVar(UserVariable charIdVar) {
		this.charIdVar = charIdVar;
	}

	public UserVariable getLevelVar() {
		return levelVar;
	}

	public void setLevelVar(UserVariable levelVar) {
		this.levelVar = levelVar;
	}

	public UserVariable getTotalXpVar() {
		return totalXpVar;
	}

	public void setTotalXpVar(UserVariable totalXpVar) {
		this.totalXpVar = totalXpVar;
	}

	public UserVariable getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(UserVariable isAlive) {
		this.isAlive = isAlive;
	}

	public UserVariable getHeath() {
		return heath;
	}

	public void setHeath(UserVariable heath) {
		this.heath = heath;
	}

	public UserVariable getMana() {
		return mana;
	}

	public void setMana(UserVariable mana) {
		this.mana = mana;
	}

	public UserVariable getStrenghtVar() {
		return strenghtVar;
	}

	public void setStrenghtVar(UserVariable strenghtVar) {
		this.strenghtVar = strenghtVar;
	}

	public UserVariable getVitalityVar() {
		return vitalityVar;
	}

	public void setVitalityVar(UserVariable vitalityVar) {
		this.vitalityVar = vitalityVar;
	}

	public UserVariable getAgilityVar() {
		return agilityVar;
	}

	public void setAgilityVar(UserVariable agilityVar) {
		this.agilityVar = agilityVar;
	}

	public UserVariable getEnergyVar() {
		return energyVar;
	}

	public void setEnergyVar(UserVariable energyVar) {
		this.energyVar = energyVar;
	}

	public UserVariable getMapVar() {
		return mapVar;
	}

	public void setMapVar(UserVariable mapVar) {
		this.mapVar = mapVar;
	}

	public UserVariable getPosXVar() {
		return posXVar;
	}

	public void setPosXVar(UserVariable posXVar) {
		this.posXVar = posXVar;
	}

	public UserVariable getPosZVar() {
		return posZVar;
	}

	public void setPosZVar(UserVariable posZVar) {
		this.posZVar = posZVar;
	}

	public UserVariable getPointsToAdd() {
		return pointsToAdd;
	}

	public void setPointsToAdd(UserVariable pointsToAdd) {
		this.pointsToAdd = pointsToAdd;
	}

	public UserVariable getWeaponVar() {
		return weaponVar;
	}

	public void setWeaponVar(UserVariable weaponVar) {
		this.weaponVar = weaponVar;
	}

}
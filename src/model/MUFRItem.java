package model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.mmo.IMMOItemVariable;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;

import model.characters.MUFRCharacter;
import model.enums.*;


@Entity
@Table(name="char_item")
public class MUFRItem  extends IDAO{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long dbid;
	
	@Column(name = "equipId")
	private long equipId;
	
	public long getEquipId() {
		return equipId;
	}

	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}
	
 	// Item Slot 
 	//Weapon_MainHand = 0,
 	//Weapon_OffHand = 6,
 	//Wings = 12,
 	//Head = 7,
 	//Necklace = 4,
 	//Chest = 8,
 	//Pants = 9,
 	//Boots = 11,
 	//Gloves = 10,
 	//Finger = 3,
 	//Skill = 15,
 	//Jewell = 17,
 	//Pets = 13,
 	//Consumable_Helth = 14,
 	//Consumable_Mana = 15

	@Column(name = "supert", nullable = false)
	private int superType;
	
	@Column(name = "subt", nullable = false)
	private int subType;
	
	@Column(name = "level", nullable = false)
	private int level;
	
	@Column(name = "opts", nullable = false)
	private int opts;
	
	@Column(name = "luck")
	private boolean luck;
	
	@Column(name = "excelent", nullable = true)
	private List<ExcelentOptions> excOpts;
	
	@Column(name = "slot", nullable = false)
	private int slot;
	
	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	@ManyToOne
	@JoinColumn(name = "owner", nullable = true)
	private MUFRCharacter owner;
		
	@Transient
	private String name;
	
	@Transient
	private List<IMMOItemVariable> variables;
	
	public List<IMMOItemVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<IMMOItemVariable> variables) {
		this.variables = variables;
	}

	@Transient
	private int dk;
	
	@Transient
	private int dw;
	
	@Transient
	private int elf;
	
	@Transient
	private int minAttack;
	
	@Transient
	private int maxAttack;
	
	@Transient
	private int defense;
	
	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}
	


	public long getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getOpts() {
		return opts;
	}

	public void setOpts(int opts) {
		this.opts = opts;
	}

	public boolean isLuck() {
		return luck;
	}

	public List<ExcelentOptions> getExcOpts() {
		return excOpts;
	}

	public void setExcOpts(List<ExcelentOptions> excOpts) {
		this.excOpts = excOpts;
	}

	public void setLuck(boolean luck) {
		this.luck = luck;
	}

	public MUFRCharacter getOwner() {
		return owner;
	}

	public void setOwner(MUFRCharacter owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSuperType() {
		return superType;
	}

	public void setSuperType(int superType) {
		this.superType = superType;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public MUFRItem()
	{
		
	}
	
	public MUFRItem(MUFRItem item)
	{
		super();
		this.level = item.level;
		this.opts = item.opts;
		this.luck = item.luck;
		this.name = item.name;
		this.superType = item.superType;
		this.subType = item.subType;
		this.equipId = item.equipId;
		this.maxAttack = item.maxAttack;
		this.minAttack = item.minAttack;
		this.defense = item.defense;
		
		this.excOpts = new LinkedList<ExcelentOptions>();
	}
	
	public MUFRItem(int itemId, int level, int opts, boolean luck, String name, int superType, int subType) {
		super();
		this.level = level;
		this.opts = opts;
		this.luck = luck;
		this.name = name;
		this.superType = superType;
		this.subType = subType;
	}

	public MUFRItem(String _name)
	{

	}
	
	public SFSObject GetSFSObject()
	{
		SFSObject returnObject = new SFSObject();
		
		returnObject.putInt("st", superType);
		returnObject.putInt("ty", subType);
		returnObject.putInt("id", (int)equipId);
		returnObject.putInt("sl", slot);
		returnObject.putInt("dbid", (int)dbid);
		returnObject.putInt("lv", level);
		returnObject.putInt("op", opts);
		returnObject.putBool("lc", luck);
		
		
		SFSObject excelentOpts = new SFSObject();
		
		if(excelentOpts.size() > 0)
		{
			int optCount = 0;
			
			for(ExcelentOptions opt: excOpts)
			{
				//excelentOpts.putInt("opt" + optCount, opt);
				
				optCount++;
			}
		}
		
		return returnObject;
	}
	
	public void SetSFSObject(ISFSObject isfsObject)
	{
		superType = isfsObject.getInt("st");
		subType = isfsObject.getInt("ty");
	}

	public int getDk() {
		return dk;
	}

	public void setDk(int dk) {
		this.dk = dk;
	}

	public int getDw() {
		return dw;
	}

	public void setDw(int dw) {
		this.dw = dw;
	}

	public int getElf() {
		return elf;
	}

	public void setElf(int elf) {
		this.elf = elf;
	}

	public int getMinAttack() {
		return minAttack;
	}

	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}

	public int getMaxAttack() {
		return maxAttack;
	}

	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}
}

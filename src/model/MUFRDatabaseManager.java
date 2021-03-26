package model;


import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import infra.MUFRFileManager;


public class MUFRDatabaseManager {
	
	private static String dataPath = "extensions/MUFRExtension/Data/";

	// Item databases
	private MUFRItemDatabase swordsDataBase;
	private MUFRItemDatabase staffsDataBase;
	private MUFRItemDatabase bowsDataBase;
	private MUFRItemDatabase axesDataBase;
	private MUFRItemDatabase shildDataBase;
	private MUFRItemDatabase quiversDataBase;
	private MUFRItemDatabase orbsDataBase;
	private MUFRItemDatabase helmDataBase;
	private MUFRItemDatabase chestDataBase;
	private MUFRItemDatabase pantDataBase;
	private MUFRItemDatabase bootsDataBase;
	private MUFRItemDatabase glovesDataBase;
	
	MUFRFileManager fileManager;

	public static String getDataPath() {
		return dataPath;
	}

	public MUFRItemDatabase getSwordsDataBase() {
		return swordsDataBase;
	}

	public MUFRItemDatabase getStaffsDataBase() {
		return staffsDataBase;
	}

	public MUFRItemDatabase getBowsDataBase() {
		return bowsDataBase;
	}

	public MUFRItemDatabase getAxesDataBase() {
		return axesDataBase;
	}

	public MUFRItemDatabase getShildDataBase() {
		return shildDataBase;
	}

	public MUFRItemDatabase getQuiversDataBase() {
		return quiversDataBase;
	}

	public MUFRItemDatabase getOrbsDataBase() {
		return orbsDataBase;
	}

	public MUFRItemDatabase getHelmDataBase() {
		return helmDataBase;
	}

	public MUFRItemDatabase getChestDataBase() {
		return chestDataBase;
	}

	public MUFRItemDatabase getPantDataBase() {
		return pantDataBase;
	}

	public MUFRItemDatabase getBootsDataBase() {
		return bootsDataBase;
	}

	public MUFRItemDatabase getGlovesDataBase() {
		return glovesDataBase;
	}

	// Constructor
	public MUFRDatabaseManager() {
		swordsDataBase = new MUFRItemDatabase();
		bootsDataBase = new MUFRItemDatabase();
		glovesDataBase = new MUFRItemDatabase();
		pantDataBase = new MUFRItemDatabase();
		chestDataBase = new MUFRItemDatabase();
		helmDataBase = new MUFRItemDatabase();
		axesDataBase = new MUFRItemDatabase();
		shildDataBase = new MUFRItemDatabase();
		orbsDataBase = new MUFRItemDatabase();
		
		fileManager = new MUFRFileManager();
		JSONParser jsonParser = new JSONParser();
		
		LoadAxesData(jsonParser);
		LoadSwordsData(jsonParser);
		LoadStaffsData(jsonParser);
		LoadBowsData(jsonParser);
		LoadQuiversData(jsonParser);
		LoadOrbsData(jsonParser);
		LoadHelmData(jsonParser);
		LoadChestsData(jsonParser);
		LoadPantsData(jsonParser);
		LoadGlovesData(jsonParser);
		LoadBootsData(jsonParser);
	}

	// Get a new item.
	public MUFRItem getNewItem(int superType, int subType, int equipId) {
		MUFRItem newItem = null;

		if (superType == 0) {
			return getNewWeapon(newItem, subType, equipId);
		} else if (superType == 6) {
			return getNewOffhand(newItem, subType, equipId);
		}else if (superType == 7) {
			return helmDataBase.GetByEquipID(equipId);
		}else if (superType == 8) {
			return chestDataBase.GetByEquipID(equipId);
		}else if (superType == 9) {
			return pantDataBase.GetByEquipID(equipId);
		}else if (superType == 10) {
			return glovesDataBase.GetByEquipID(equipId);
		}else if (superType == 11) {
			return bootsDataBase.GetByEquipID(equipId);
		}

		return null;
	}

	// Get Weapon
	/*
	 * Staff1H = 100, Staff2H = 110, Sword1H = 1, Sword2H = 2, Bow = 3, Crossbow = 4, Axe1H = 6, Axe2H = 7
	 */
	private MUFRItem getNewWeapon(MUFRItem newItem, int subType, int equipId) {
		if (subType == 1 || subType == 2) {
			newItem = new MUFRItem(swordsDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 3 || subType == 4) {
			newItem = new MUFRItem(bowsDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 6 || subType == 7) {
			newItem = new MUFRItem(axesDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 100 || subType == 110) {
			newItem = new MUFRItem(staffsDataBase.GetByEquipID(equipId));
			return newItem;
		}

		return null;
	}

	// Get offhand
	/*
	 * quiver = 1, orb = 2, sword = 3, shild = 4, axe = 5
	 */
	private MUFRItem getNewOffhand(MUFRItem newItem, int subType, int equipId) {
		if (subType == 1) {
			newItem = new MUFRItem(quiversDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 3) {
			newItem = new MUFRItem(orbsDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 3) {
			newItem = new MUFRItem(swordsDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 4) {
			newItem = new MUFRItem(shildDataBase.GetByEquipID(equipId));
			return newItem;
		} else if (subType == 5) {
			newItem = new MUFRItem(axesDataBase.GetByEquipID(equipId));
			return newItem;
		}

		return null;
	}
	
	private MUFRItem getNewEquip(MUFRItem item)
	{
		if(item.getSuperType() == 7){
			item = new MUFRItem(helmDataBase.GetByEquipID((int) item.getEquipId()));
			return item;
		}
		else if(item.getSuperType() == 8) {
			item = new MUFRItem(chestDataBase.GetByEquipID((int) item.getEquipId()));
			return item;
		}
		else if(item.getSuperType() == 9) {
			item = new MUFRItem(pantDataBase.GetByEquipID((int) item.getEquipId()));
			return item;
		}
		else if(item.getSuperType() == 10) {
			item = new MUFRItem(glovesDataBase.GetByEquipID((int) item.getEquipId()));
			return item;
		}
		else if(item.getSuperType() == 11) {
			item = new MUFRItem(bootsDataBase.GetByEquipID((int) item.getEquipId()));
			return item;
		}
		
		return item;
	}
	
	public void regenerateItemInfo(MUFRItem item)
	{
		MUFRItem thisItem = null;
		
		if(item.getSuperType() == 0)
		{
			if(item.getSubType() == 1 || item.getSubType() == 2)
				thisItem = new MUFRItem(swordsDataBase.GetByEquipID((int) item.getEquipId()));
			else if(item.getSubType() == 3 || item.getSubType() == 4)
				thisItem = new MUFRItem(bowsDataBase.GetByEquipID((int) item.getEquipId()));
			else if(item.getSubType() == 6 || item.getSubType() == 7)
				thisItem = new MUFRItem(axesDataBase.GetByEquipID((int) item.getEquipId()));
			else if(item.getSubType() == 100 || item.getSubType() == 110)
				thisItem = new MUFRItem(staffsDataBase.GetByEquipID((int) item.getEquipId()));
			
			item.setMinAttack(thisItem.getMinAttack());
			item.setMaxAttack(thisItem.getMaxAttack());
		}
		else if (item.getSuperType() == 6)
		{
			if(item.getSubType() == 1)
				thisItem = new MUFRItem(quiversDataBase.GetByEquipID((int) item.getEquipId()));
			else if(item.getSubType() == 2)
				thisItem = new MUFRItem(orbsDataBase.GetByEquipID((int) item.getEquipId()));
			else if(item.getSubType() == 4)
				thisItem = new MUFRItem(shildDataBase.GetByEquipID((int) item.getEquipId()));

			item.setDefense(thisItem.getDefense());
			item.setMinAttack(thisItem.getMinAttack());
			item.setMaxAttack(thisItem.getMaxAttack());
		}
		else if(item.getSuperType() == 7 || item.getSuperType() == 8 || item.getSuperType() == 9 || item.getSuperType() == 10 || item.getSuperType() == 11)
		{
			item.setDefense(getNewEquip(item).getDefense());
		}
		
		//item.setDbid(thisItem.getDbid());
		//item.setName(thisItem.getName());
	}
	

	
	/*
	 * // Get a formated JSON file private JSONArray GetFile(String url, JSONParser
	 * jsonParser) { JSONArray jsonArray = null;
	 * 
	 * // Parsing the contents of the JSON file JSONObject data; try {
	 * 
	 * data = (JSONObject) jsonParser.parse(new FileReader(dataPath + url));
	 * jsonArray = (JSONArray) data.get("$values");
	 * 
	 * } catch (IOException | NoSuchFieldError |
	 * org.json.simple.parser.ParseException e) { e.printStackTrace(); }
	 * 
	 * return jsonArray; }
	 */
	
	private MUFRItemDatabase loadItem(MUFRFileManager _fileManager, JSONParser jsonParser, String path)
	{
		MUFRItemDatabase databaseReturn = new MUFRItemDatabase();
		
		JSONArray jsonArray = _fileManager.GetFile(path, jsonParser);

		Iterator<?> iterator = jsonArray.iterator();

		while (iterator.hasNext()) {
			JSONObject jsonObject = (JSONObject) iterator.next();

			MUFRItem thisItem = new MUFRItem();

			thisItem.setEquipId(((Long) jsonObject.get("id")).intValue());
			thisItem.setName((String) jsonObject.get("name"));
			thisItem.setSuperType(((Long) jsonObject.get("EquipType")).intValue());
			thisItem.setSubType(((Long) jsonObject.get("SubType")).intValue());
			thisItem.setDk(((Long) jsonObject.get("dk")).intValue());
			thisItem.setDw(((Long) jsonObject.get("dw")).intValue());
			thisItem.setElf(((Long) jsonObject.get("elf")).intValue());
			thisItem.setDefense(((Long) jsonObject.get("defense")).intValue());
			thisItem.setMinAttack(((Long) jsonObject.get("minDamage")).intValue());
			thisItem.setMaxAttack(((Long) jsonObject.get("maxDamage")).intValue());

			databaseReturn.items.add(thisItem);

		}
		
		return databaseReturn;
	}
	
	private void LoadSwordsData(JSONParser jsonParser) {
		swordsDataBase = loadItem(fileManager, jsonParser, "swords.json");
	}
	
	private void LoadQuiversData(JSONParser jsonParser) {
		quiversDataBase = loadItem(fileManager, jsonParser, "quivers.json");
	}
	
	private void LoadOrbsData(JSONParser jsonParser) {
		orbsDataBase = loadItem(fileManager, jsonParser, "orbs.json");
	}
	
	private void LoadHelmData(JSONParser jsonParser)
	{
		helmDataBase = loadItem(fileManager, jsonParser, "helms.json");
	}
	
	private void LoadChestsData(JSONParser jsonParser)
	{
		chestDataBase = loadItem(fileManager, jsonParser, "chests.json");
	}
	
	private void LoadPantsData(JSONParser jsonParser)
	{
		pantDataBase = loadItem(fileManager, jsonParser, "pants.json");
	}
	
	private void LoadGlovesData(JSONParser jsonParser)
	{
		glovesDataBase = loadItem(fileManager, jsonParser, "gloves.json");
	}
	
	private void LoadBootsData(JSONParser jsonParser)
	{
		bootsDataBase = loadItem(fileManager, jsonParser, "boots.json");
	}
	
	private void LoadStaffsData(JSONParser jsonParser)
	{
		staffsDataBase = loadItem(fileManager, jsonParser, "staffs.json");
	}
	
	private void LoadBowsData(JSONParser jsonParser)
	{
		bowsDataBase = loadItem(fileManager, jsonParser, "bows.json");
	}
	
	private void LoadAxesData(JSONParser jsonParser)
	{
		axesDataBase = loadItem(fileManager, jsonParser, "axes.json");
	}
}

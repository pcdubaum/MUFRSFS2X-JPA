package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.Vec3D;

import infra.MUFRFileManager;
import model.characters.MUFRCharacterInfo;
import model.enums.EnemyType;

public class MUFRServerConfigs {
	
	private String serverName;
	private int xpMultiplier;
	private int dropRate;
	private int excDropRate;
	private int pointsPerLevel;
	
	private MUFRUserDatabase users;
	private List<MUFRCharacterInfo> characters;
	
	private MUFRMapDatabase mapsDatabase;
	private MUFRMonsterDatabase monsterDatabase;
	private MUFRMonsterDatabase activeMonsterDatabase;
	private MUFRDropTables dropTables;
	
	private MUFRDatabaseManager dataBase;
	private MUFRFileManager fileManager;
	
	public List<MMOItem> items;
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getXpMultiplier() {
		return xpMultiplier;
	}

	public void setXpMultiplier(int xpMultiplier) {
		this.xpMultiplier = xpMultiplier;
	}

	public int getDropRate() {
		return dropRate;
	}

	public void setDropRate(int dropRate) {
		this.dropRate = dropRate;
	}

	public int getExcDropRate() {
		return excDropRate;
	}

	public void setExcDropRate(int excDropRate) {
		this.excDropRate = excDropRate;
	}

	public int getPointsPerLevel() {
		return pointsPerLevel;
	}

	public void setPointsPerLevel(int pointsPerLevel) {
		this.pointsPerLevel = pointsPerLevel;
	}

	public MUFRUserDatabase getUsers() {
		return users;
	}

	public void setUsers(MUFRUserDatabase users) {
		this.users = users;
	}

	public List<MUFRCharacterInfo> getCharacters() {
		return characters;
	}

	public void setCharacters(List<MUFRCharacterInfo> characters) {
		this.characters = characters;
	}

	public MUFRMapDatabase getMapsDatabase() {
		return mapsDatabase;
	}

	public void setMapsDatabase(MUFRMapDatabase mapsDatabase) {
		this.mapsDatabase = mapsDatabase;
	}

	public MUFRMonsterDatabase getMonsterDatabase() {
		return monsterDatabase;
	}

	public void setMonsterDatabase(MUFRMonsterDatabase monsterDatabase) {
		this.monsterDatabase = monsterDatabase;
	}

	public MUFRMonsterDatabase getActiveMonsterDatabase() {
		return activeMonsterDatabase;
	}

	public void setActiveMonsterDatabase(MUFRMonsterDatabase activeMonsterDatabase) {
		this.activeMonsterDatabase = activeMonsterDatabase;
	}

	public MUFRDropTables getDropTables() {
		return dropTables;
	}

	public void setDropTables(MUFRDropTables dropTables) {
		this.dropTables = dropTables;
	}

	public List<MMOItem> getItems() {
		return items;
	}

	public void setItems(List<MMOItem> items) {
		items = items;
	}

	public MUFRServerConfigs(MUFRDatabaseManager dataBase) {
		/*
		 * this.serverName = serverName; this.xpMultiplier = xpMultiplier; this.dropRate
		 * = dropRate; this.excDropRate = excDropRate; this.pointsPerLevel =
		 * pointsPerLevel;
		 */
		
		serverName = "MU Fã Remake";
		xpMultiplier = 5;
		dropRate = 75;
		excDropRate = 4;
		pointsPerLevel = 5;
		
		
		this.dataBase = dataBase;
		
		users = new MUFRUserDatabase();
		characters = new ArrayList<MUFRCharacterInfo>();
		mapsDatabase = new MUFRMapDatabase();
		monsterDatabase = new MUFRMonsterDatabase();
		activeMonsterDatabase = new MUFRMonsterDatabase();
		dropTables = new MUFRDropTables();
		items = new LinkedList<MMOItem>();
		
		fileManager = new MUFRFileManager();
		JSONParser jsonParser = new JSONParser();
		
		LoadCharactersInfo(jsonParser);
		LoadDropTables(jsonParser);
		LoadMaps(jsonParser);
		LoadMobs(jsonParser);
		LoadMonsterSpots(jsonParser);
	}
	
	private void LoadCharactersInfo(JSONParser jsonParser) {
		try {

			String fileName = "characters.json";

			// Parsing the contents of the JSON file
			JSONObject data = (JSONObject) jsonParser.parse(new FileReader(dataBase.getDataPath() + fileName));

			JSONArray jsonArray = (JSONArray) data.get("$values");

			Iterator iterator = jsonArray.iterator();

			while (iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject) iterator.next();

				MUFRCharacterInfo thisCharacter = new MUFRCharacterInfo();
				
				thisCharacter.charClass = ((Long) jsonObject.get("id")).intValue();
				thisCharacter.className = (String) jsonObject.get("ClassName");
				thisCharacter.strenght = ((Long) jsonObject.get("Strenght")).intValue();
				thisCharacter.vitality = ((Long) jsonObject.get("Vitality")).intValue();
				thisCharacter.agility = ((Long) jsonObject.get("Agility")).intValue();
				thisCharacter.energy = ((Long) jsonObject.get("Energy")).intValue();
				thisCharacter.map = ((Long) jsonObject.get("Map")).intValue();
				thisCharacter.posx = ((Long) jsonObject.get("PosX")).intValue();
				thisCharacter.posz = ((Long) jsonObject.get("PosZ")).intValue();
				
				JSONObject weaponObject = null;
				
				if(jsonObject.containsKey("weapon"))
					weaponObject = (JSONObject) jsonObject.get("weapon");
				
				//JSONObject entryData = (JSONObject) jsonObject.get("entryPoints");
				
				MUFRItem startingWeapon = null;
				
				if(weaponObject != null)
				{
					int supert = ((Long) weaponObject.get("EquipType")).intValue();
					int subt = ((Long) weaponObject.get("SubType")).intValue();
					int id =  ((Long) weaponObject.get("id")).intValue();
					boolean luck = ((Boolean) weaponObject.get("Luck")).booleanValue();
					int opts =  ((Long) weaponObject.get("optionLevel")).intValue();

					
					startingWeapon = dataBase.getNewItem(supert, subt, id);
					/*if(supert == 0)
					{
						if(subt == 1 || subt == 2)
						{
							startingWeapon = new MUFRItem(swordsDataBase.Get(id));
						}
						else if (subt == 3 || subt == 4)
						{
							startingWeapon = new MUFRItem(bowsDataBase.Get(id));
						}
						else if (subt == 100 || subt == 110)
						{
							startingWeapon = new MUFRItem(staffsDataBase.Get(id));
						}
					}*/
				}	
				
				thisCharacter.startingWeapon = startingWeapon;
				
				// thisItem.setItemId(((Long)slide.get("Energy")).intValue());

				System.out.print(thisCharacter.charClass + " - ");
				System.out.print(thisCharacter.className + " ");
				System.out.print(thisCharacter.strenght + " ");
				System.out.print(thisCharacter.vitality + " ");
				System.out.print(thisCharacter.agility + " ");
				System.out.println(thisCharacter.energy);

				characters.add(thisCharacter);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchFieldError e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} finally {
			// trace("Characters Loaded: " + characters.size());
		}
	}
	
	private void LoadDropTables(JSONParser jsonParser) {
		JSONArray jsonArray = fileManager.GetFile("dropTable.json", jsonParser);

		Iterator<?> iterator = jsonArray.iterator();

		while (iterator.hasNext()) {
			JSONObject jsonObject = (JSONObject) iterator.next();
			{
				MUFRDropTable dropTable = new MUFRDropTable();

				int id = ((Long) jsonObject.get("id")).intValue();
				dropTable.setId(id);

				JSONObject itemObject = (JSONObject) jsonObject.get("rates");
				JSONObject entryArray = (JSONObject) itemObject.get("basicDropTables");
				JSONArray values = (JSONArray) entryArray.get("$values");

				Iterator<?> valueIterator = values.iterator();

				while (valueIterator.hasNext()) {
					JSONObject jsonValues = (JSONObject) valueIterator.next();
					{
						MUFRDrop drop = new MUFRDrop();

						drop.setId(((Long) jsonValues.get("id")).intValue());
						drop.setSuperType(((Long) jsonValues.get("superType")).intValue());
						drop.setSubtype(((Long) jsonValues.get("subtype")).intValue());
						drop.setJewelenum(((Long) jsonValues.get("jewelenum")).intValue());
						drop.setRate(((Long) jsonValues.get("rate")).intValue());

						dropTable.getDrops().add(drop);
					}
				}

				dropTables.getDropTables().add(dropTable);
			}

		}
	}
	
	private void LoadMaps(JSONParser jsonParser) {

		JSONArray jsonArray = fileManager.GetFile("maps.json", jsonParser);

		Iterator<?> iterator = jsonArray.iterator();

		while (iterator.hasNext()) {
			JSONObject jsonObject = (JSONObject) iterator.next();

			MUFRMap thisMap = new MUFRMap();

			thisMap.setId(((Long) jsonObject.get("id")).intValue());
			thisMap.setName((String) jsonObject.get("name"));
			thisMap.setSize(new Vec3D((Long) jsonObject.get("lenghtx"), (float) 1.0, (Long) jsonObject.get("lenghtz")));
			JSONObject entryData = (JSONObject) jsonObject.get("entryPoints");

			if (entryData != null) {
				JSONArray entryArray = (JSONArray) entryData.get("$values");

				Iterator<?> iterator2 = entryArray.iterator();

				while (iterator2.hasNext()) {
					JSONObject jsonObject2 = (JSONObject) iterator2.next();
					// trace("id:" + (Long)jsonObject2.get("id") + " x:" +
					// (Long)jsonObject2.get("x") + " z:" + (Long)jsonObject2.get("z"));
				}
			}
			mapsDatabase.maps.add(thisMap);

		}
	}

	private void LoadMobs(JSONParser jsonParser) {

		JSONArray jsonArray = fileManager.GetFile("monsters.json", jsonParser);

		Iterator<?> iterator = jsonArray.iterator();

		while (iterator.hasNext()) {

			JSONObject jsonObject = (JSONObject) iterator.next();

			MUFRMonster thisMob = new MUFRMonster();

			thisMob.setId(((Long) jsonObject.get("id")).intValue());
			thisMob.setName((String) jsonObject.get("Name"));
			thisMob.setLevel(((Long) jsonObject.get("level")).intValue());
			thisMob.setMinDmg(((Long) jsonObject.get("minDmg")).intValue());
			thisMob.setMaxDmg(((Long) jsonObject.get("maxDmg")).intValue());
			thisMob.setLife(((Long) jsonObject.get("life")).intValue());
			thisMob.setDefense(((Long) jsonObject.get("defense")).intValue());
			thisMob.setDefenseRate(((Long) jsonObject.get("defenseRate")).intValue());
			thisMob.setAttackType(((Long) jsonObject.get("attackType")).intValue());
			thisMob.setRange(((Double) jsonObject.get("range")).doubleValue());

			String type = (String) jsonObject.get("enemyType");

			if (type == "Normal")
				thisMob.setEnemyType(EnemyType.Normal);
			else if (type == "Elite")
				thisMob.setEnemyType(EnemyType.Elite);
			else if (type == "Golden")
				thisMob.setEnemyType(EnemyType.Golde2);
			else if (type == "Boss")
				thisMob.setEnemyType(EnemyType.Boss);

			monsterDatabase.monsters.add(thisMob);

		}
	}

	private void LoadMonsterSpots(JSONParser jsonParser) {
		JSONArray jsonArray = fileManager.GetFile("mobs/monsters2.json", jsonParser);

		Iterator<?> iterator = jsonArray.iterator();

		while (iterator.hasNext()) {
			JSONObject jsonObject = (JSONObject) iterator.next();

			int id = ((Long) jsonObject.get("id")).intValue();
			Vec3D pos = new Vec3D(((Long) jsonObject.get("posx")).intValue(), 0,
					((Long) jsonObject.get("posy")).intValue());
			int room = ((Long) jsonObject.get("map")).intValue();

			MUFRMonster thisMob = new MUFRMonster(monsterDatabase.Get(id), pos, room);

			activeMonsterDatabase.monsters.add(thisMob);
		}
	}
}

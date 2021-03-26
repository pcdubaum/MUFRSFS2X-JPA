package logic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.SFSRoomEvents;
import com.smartfoxserver.v2.extensions.SFSExtension;

import logic.requests.AddPoint;
import logic.requests.AttackRequestHandler;
import logic.requests.ChangeMapRequestHandler;
import logic.requests.Consume;
import logic.requests.CreateCharRequestHandler;
import logic.requests.InventoryHandler;
import logic.requests.PickLoot;
import logic.requests.SelectCharRequestHandler;
import logic.room.MMORoomDrop;
import model.MUFRDatabaseManager;

import model.MUFRServerConfigs;
import model.MUFRUser;


public class MUFRExtension extends SFSExtension {

	//private static String dataPath = "extensions/MUFRExtension/Data/";

	public static String DATABASE_ID = "dbId";
	public static String mufrUser = "mufrUser";

	/*
	 * public static MUFRUserDatabase users;
	 * 
	 * public static List<MUFRCharacterInfo> characters;
	 */

	// Item databases
	/*
	 * public static MUFRItemDatabase swordsDataBase; public static MUFRItemDatabase
	 * staffsDataBase; public static MUFRItemDatabase bowsDataBase; public static
	 * MUFRItemDatabase axesDataBase; public static MUFRItemDatabase shildDataBase;
	 * public static MUFRItemDatabase quiversDataBase; public static
	 * MUFRItemDatabase orbsDataBase; public static MUFRItemDatabase helmDataBase;
	 * public static MUFRItemDatabase chestDataBase; public static MUFRItemDatabase
	 * pantDataBase; public static MUFRItemDatabase bootsDataBase; public static
	 * MUFRItemDatabase glovesDataBase;
	 */

	public MUFRDatabaseManager databaseManager;

	/*
	 * public static MUFRMapDatabase mapsDatabase; public static MUFRMonsterDatabase
	 * monsterDatabase; public static MUFRMonsterDatabase activeMonsterDatabase;
	 * public static MUFRDropTables dropTables;
	 * 
	 * public static List<MMOItem> items;
	 */

	public static MUFRServerConfigs configs;

	private static final String PERSISTENCE_UNIT_NAME = "MUFRSFS2X-JPA";
	private EntityManagerFactory emf;
	private EntityManager em;

	@Override
	public void init() {

		/*
		 * users = new MUFRUserDatabase(); characters = new
		 * ArrayList<MUFRCharacterInfo>(); mapsDatabase = new MUFRMapDatabase();
		 * monsterDatabase = new MUFRMonsterDatabase(); activeMonsterDatabase = new
		 * MUFRMonsterDatabase();
		 */

		

		/*
		 * swordsDataBase = new MUFRItemDatabase(); bootsDataBase = new
		 * MUFRItemDatabase(); glovesDataBase = new MUFRItemDatabase(); pantDataBase =
		 * new MUFRItemDatabase(); chestDataBase = new MUFRItemDatabase(); helmDataBase
		 * = new MUFRItemDatabase(); axesDataBase = new MUFRItemDatabase();
		 * shildDataBase = new MUFRItemDatabase(); orbsDataBase = new
		 * MUFRItemDatabase();
		 */
//		dropTables = new MUFRDropTables();
//		
//		items = new LinkedList<MMOItem>();

		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();

		databaseManager = new MUFRDatabaseManager();
		configs = new MUFRServerConfigs(databaseManager);

		// Load all data
		// LoadData();

		// Activates the login routing to the Extension without manual configuration
		getParentZone().setCustomLogin(true);
		
		addEventHandlers();
		addRequestHandlers();

		// Add event handler for login events
		/*
		 * addEventHandler(SFSEventType.USER_LOGIN, new LoginEventHandler(em));
		 * addEventHandler(SFSEventType.USER_JOIN_ZONE, new JoinZoneHandler(em,
		 * databaseManager)); addEventHandler(SFSEventType.USER_JOIN_ROOM,
		 * JoinRoomHandler.class); addEventHandler(SFSEventType.SERVER_READY, new
		 * ServerReadyHandler(databaseManager, configs));
		 * addEventHandler(SFSEventType.USER_DISCONNECT, new DisconnectHandler(em));
		 */

		// Register client requests
		/*
		 * addRequestHandler("selectChar", new SelectCharRequestHandler(configs));
		 * addRequestHandler("createChar", new CreateCharRequestHandler(em,
		 * databaseManager, configs)); // addRequestHandler("storeItems",
		 * StoreItemsHandler.class); addRequestHandler("changeMap",
		 * ChangeMapRequestHandler.class); addRequestHandler("attack", new
		 * AttackRequestHandler(configs)); addRequestHandler("addpoints",
		 * AddPoint.class); addRequestHandler("consume", Consume.class);
		 * addRequestHandler("pick", new PickLoot(databaseManager, configs));
		 * addRequestHandler("inventory", InventoryHandler.class);
		 */

		

	}

	@Override
	public void destroy() {
		
		for(MUFRUser user : configs.getUsers().getUsers())
		{
			user.getUser().isBeingKicked();
		}
		em.close();
		emf.close();
		trace("Extension stopped.");
		super.destroy();
	}
	
	private void addEventHandlers()
	{
		addEventHandler(SFSEventType.USER_LOGIN, new LoginEventHandler(em));
		addEventHandler(SFSEventType.USER_JOIN_ZONE, new JoinZoneHandler(databaseManager));
		addEventHandler(SFSEventType.USER_JOIN_ROOM, JoinRoomHandler.class);
		addEventHandler(SFSEventType.SERVER_READY, new ServerReadyHandler(databaseManager, configs));
		addEventHandler(SFSEventType.USER_DISCONNECT, new DisconnectHandler(em));
		
		//addEventHandler(SFSRoomEvents., new DisconnectHandler(em));
	}
	
	private void addRequestHandlers()
	{
		addRequestHandler("selectChar", new SelectCharRequestHandler(configs));
		addRequestHandler("createChar", new CreateCharRequestHandler(em, databaseManager, configs));
		// addRequestHandler("storeItems", StoreItemsHandler.class);
		addRequestHandler("changeMap", new ChangeMapRequestHandler(configs));
		addRequestHandler("attack", new AttackRequestHandler(configs));
		addRequestHandler("addpoints", AddPoint.class);
		addRequestHandler("consume", Consume.class);
		addRequestHandler("pick", new PickLoot(em, databaseManager, configs));
		addRequestHandler("inventory", InventoryHandler.class);
		addRequestHandler("drop", new MMORoomDrop(configs));
	}

	/*
	 * private void LoadData() { JSONParser jsonParser = new JSONParser();
	 * 
	 * // LoadAxesData(jsonParser); // LoadSwordsData(jsonParser); //
	 * LoadStaffsData(jsonParser); // LoadBowsData(jsonParser); //
	 * LoadQuiversData(jsonParser); // LoadOrbsData(jsonParser); //
	 * LoadHelmData(jsonParser); // LoadChestsData(jsonParser); //
	 * LoadPantsData(jsonParser); // LoadGlovesData(jsonParser); //
	 * LoadBootsData(jsonParser); LoadMaps(jsonParser); LoadMobs(jsonParser);
	 * LoadMonsterSpots(jsonParser); LoadCharactersInfo(jsonParser);
	 * LoadDropTables(jsonParser); // LoadNPCs(jsonParser); }
	 */

	/*
	 * private void LoadCharactersInfo(JSONParser jsonParser) { try {
	 * 
	 * String fileName = "characters.json";
	 * 
	 * // Parsing the contents of the JSON file JSONObject data = (JSONObject)
	 * jsonParser.parse(new FileReader(dataPath + fileName));
	 * 
	 * JSONArray jsonArray = (JSONArray) data.get("$values");
	 * 
	 * Iterator iterator = jsonArray.iterator();
	 * 
	 * while (iterator.hasNext()) { JSONObject jsonObject = (JSONObject)
	 * iterator.next();
	 * 
	 * MUFRCharacterInfo thisCharacter = new MUFRCharacterInfo();
	 * 
	 * thisCharacter.charClass = ((Long) jsonObject.get("id")).intValue();
	 * thisCharacter.className = (String) jsonObject.get("ClassName");
	 * thisCharacter.strenght = ((Long) jsonObject.get("Strenght")).intValue();
	 * thisCharacter.vitality = ((Long) jsonObject.get("Vitality")).intValue();
	 * thisCharacter.agility = ((Long) jsonObject.get("Agility")).intValue();
	 * thisCharacter.energy = ((Long) jsonObject.get("Energy")).intValue();
	 * thisCharacter.map = ((Long) jsonObject.get("Map")).intValue();
	 * thisCharacter.posx = ((Long) jsonObject.get("PosX")).intValue();
	 * thisCharacter.posz = ((Long) jsonObject.get("PosZ")).intValue();
	 * 
	 * JSONObject weaponObject = null;
	 * 
	 * if(jsonObject.containsKey("weapon")) weaponObject = (JSONObject)
	 * jsonObject.get("weapon");
	 * 
	 * //JSONObject entryData = (JSONObject) jsonObject.get("entryPoints");
	 * 
	 * MUFRItem startingWeapon = null;
	 * 
	 * if(weaponObject != null) { int supert = ((Long)
	 * weaponObject.get("EquipType")).intValue(); int subt = ((Long)
	 * weaponObject.get("SubType")).intValue(); int id = ((Long)
	 * weaponObject.get("id")).intValue(); boolean luck = ((Boolean)
	 * weaponObject.get("Luck")).booleanValue(); int opts = ((Long)
	 * weaponObject.get("optionLevel")).intValue();
	 * 
	 * 
	 * startingWeapon = databaseManager.getNewItem(supert, subt, id); if(supert ==
	 * 0) { if(subt == 1 || subt == 2) { startingWeapon = new
	 * MUFRItem(swordsDataBase.Get(id)); } else if (subt == 3 || subt == 4) {
	 * startingWeapon = new MUFRItem(bowsDataBase.Get(id)); } else if (subt == 100
	 * || subt == 110) { startingWeapon = new MUFRItem(staffsDataBase.Get(id)); } }
	 * }
	 * 
	 * thisCharacter.startingWeapon = startingWeapon;
	 * 
	 * // thisItem.setItemId(((Long)slide.get("Energy")).intValue());
	 * 
	 * System.out.print(thisCharacter.charClass + " - ");
	 * System.out.print(thisCharacter.className + " ");
	 * System.out.print(thisCharacter.strenght + " ");
	 * System.out.print(thisCharacter.vitality + " ");
	 * System.out.print(thisCharacter.agility + " ");
	 * System.out.println(thisCharacter.energy);
	 * 
	 * characters.add(thisCharacter);
	 * 
	 * }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } catch (NoSuchFieldError e) {
	 * e.printStackTrace(); } catch (org.json.simple.parser.ParseException e) {
	 * e.printStackTrace(); } finally { // trace("Characters Loaded: " +
	 * characters.size()); } }
	 */

	/*
	 * private void LoadDropTables(JSONParser jsonParser) { JSONArray jsonArray =
	 * GetFile("dropTable.json", jsonParser);
	 * 
	 * Iterator<?> iterator = jsonArray.iterator();
	 * 
	 * while (iterator.hasNext()) { JSONObject jsonObject = (JSONObject)
	 * iterator.next(); { MUFRDropTable dropTable = new MUFRDropTable();
	 * 
	 * int id = ((Long) jsonObject.get("id")).intValue(); dropTable.setId(id);
	 * 
	 * JSONObject itemObject = (JSONObject) jsonObject.get("rates"); JSONObject
	 * entryArray = (JSONObject) itemObject.get("basicDropTables"); JSONArray values
	 * = (JSONArray) entryArray.get("$values");
	 * 
	 * Iterator<?> valueIterator = values.iterator();
	 * 
	 * while (valueIterator.hasNext()) { JSONObject jsonValues = (JSONObject)
	 * valueIterator.next(); { MUFRDrop drop = new MUFRDrop();
	 * 
	 * drop.setId(((Long) jsonValues.get("id")).intValue());
	 * drop.setSuperType(((Long) jsonValues.get("superType")).intValue());
	 * drop.setSubtype(((Long) jsonValues.get("subtype")).intValue());
	 * drop.setJewelenum(((Long) jsonValues.get("jewelenum")).intValue());
	 * drop.setRate(((Long) jsonValues.get("rate")).intValue());
	 * 
	 * dropTable.getDrops().add(drop); } }
	 * 
	 * dropTables.getDropTables().add(dropTable); }
	 * 
	 * } }
	 */

	/*
	 * private MUFRItemDatabase loadItem(JSONParser jsonParser, String path) {
	 * MUFRItemDatabase databaseReturn = new MUFRItemDatabase();
	 * 
	 * JSONArray jsonArray = GetFile(path, jsonParser);
	 * 
	 * Iterator<?> iterator = jsonArray.iterator();
	 * 
	 * while (iterator.hasNext()) { JSONObject jsonObject = (JSONObject)
	 * iterator.next();
	 * 
	 * MUFRItem thisItem = new MUFRItem();
	 * 
	 * thisItem.setEquipId(((Long) jsonObject.get("id")).intValue());
	 * thisItem.setName((String) jsonObject.get("name"));
	 * thisItem.setSuperType(((Long) jsonObject.get("EquipType")).intValue());
	 * thisItem.setSubType(((Long) jsonObject.get("SubType")).intValue());
	 * thisItem.setDk(((Long) jsonObject.get("dk")).intValue());
	 * thisItem.setDw(((Long) jsonObject.get("dw")).intValue());
	 * thisItem.setElf(((Long) jsonObject.get("elf")).intValue());
	 * thisItem.setDefense(((Long) jsonObject.get("defense")).intValue());
	 * thisItem.setMinAttack(((Long) jsonObject.get("minDamage")).intValue());
	 * thisItem.setMaxAttack(((Long) jsonObject.get("maxDamage")).intValue());
	 * 
	 * databaseReturn.items.add(thisItem);
	 * 
	 * }
	 * 
	 * return databaseReturn; }
	 */

	/*
	 * private void LoadMaps(JSONParser jsonParser) {
	 * 
	 * JSONArray jsonArray = GetFile("maps.json", jsonParser);
	 * 
	 * Iterator<?> iterator = jsonArray.iterator();
	 * 
	 * while (iterator.hasNext()) { JSONObject jsonObject = (JSONObject)
	 * iterator.next();
	 * 
	 * MUFRMap thisMap = new MUFRMap();
	 * 
	 * thisMap.setId(((Long) jsonObject.get("id")).intValue());
	 * thisMap.setName((String) jsonObject.get("name")); thisMap.setSize(new
	 * Vec3D((Long) jsonObject.get("lenghtx"), (float) 1.0, (Long)
	 * jsonObject.get("lenghtz"))); JSONObject entryData = (JSONObject)
	 * jsonObject.get("entryPoints");
	 * 
	 * if (entryData != null) { JSONArray entryArray = (JSONArray)
	 * entryData.get("$values");
	 * 
	 * Iterator<?> iterator2 = entryArray.iterator();
	 * 
	 * while (iterator2.hasNext()) { JSONObject jsonObject2 = (JSONObject)
	 * iterator2.next(); // trace("id:" + (Long)jsonObject2.get("id") + " x:" + //
	 * (Long)jsonObject2.get("x") + " z:" + (Long)jsonObject2.get("z")); } }
	 * mapsDatabase.maps.add(thisMap);
	 * 
	 * }
	 * 
	 * trace("Maps Loaded: " + mapsDatabase.maps.size());
	 * 
	 * }
	 * 
	 * private void LoadMobs(JSONParser jsonParser) {
	 * 
	 * JSONArray jsonArray = GetFile("monsters.json", jsonParser);
	 * 
	 * Iterator<?> iterator = jsonArray.iterator();
	 * 
	 * while (iterator.hasNext()) {
	 * 
	 * JSONObject jsonObject = (JSONObject) iterator.next();
	 * 
	 * MUFRMonster thisMob = new MUFRMonster();
	 * 
	 * thisMob.setId(((Long) jsonObject.get("id")).intValue());
	 * thisMob.setName((String) jsonObject.get("Name")); thisMob.setLevel(((Long)
	 * jsonObject.get("level")).intValue()); thisMob.setMinDmg(((Long)
	 * jsonObject.get("minDmg")).intValue()); thisMob.setMaxDmg(((Long)
	 * jsonObject.get("maxDmg")).intValue()); thisMob.setLife(((Long)
	 * jsonObject.get("life")).intValue()); thisMob.setDefense(((Long)
	 * jsonObject.get("defense")).intValue()); thisMob.setDefenseRate(((Long)
	 * jsonObject.get("defenseRate")).intValue()); thisMob.setAttackType(((Long)
	 * jsonObject.get("attackType")).intValue()); thisMob.setRange(((Double)
	 * jsonObject.get("range")).doubleValue());
	 * 
	 * String type = (String) jsonObject.get("enemyType");
	 * 
	 * if (type == "Normal") thisMob.setEnemyType(EnemyType.Normal); else if (type
	 * == "Elite") thisMob.setEnemyType(EnemyType.Elite); else if (type == "Golden")
	 * thisMob.setEnemyType(EnemyType.Golde2); else if (type == "Boss")
	 * thisMob.setEnemyType(EnemyType.Boss);
	 * 
	 * monsterDatabase.monsters.add(thisMob);
	 * 
	 * } trace("Monsters Loaded: " + monsterDatabase.monsters.size()); }
	 * 
	 * private void LoadMonsterSpots(JSONParser jsonParser) { JSONArray jsonArray =
	 * GetFile("mobs/monsters2.json", jsonParser);
	 * 
	 * Iterator<?> iterator = jsonArray.iterator();
	 * 
	 * while (iterator.hasNext()) { JSONObject jsonObject = (JSONObject)
	 * iterator.next();
	 * 
	 * int id = ((Long) jsonObject.get("id")).intValue(); Vec3D pos = new
	 * Vec3D(((Long) jsonObject.get("posx")).intValue(), 0, ((Long)
	 * jsonObject.get("posy")).intValue()); int room = ((Long)
	 * jsonObject.get("map")).intValue();
	 * 
	 * MUFRMonster thisMob = new MUFRMonster(monsterDatabase.Get(id), pos, room);
	 * 
	 * activeMonsterDatabase.monsters.add(thisMob); }
	 * 
	 * trace("Monster Spots Loaded: " + activeMonsterDatabase.monsters.size()); }
	 */

	/*
	 * private JSONArray GetFile(String url, JSONParser jsonParser) { JSONArray
	 * jsonArray = null;
	 * 
	 * // Parsing the contents of the JSON file JSONObject data; try {
	 * 
	 * data = (JSONObject) jsonParser.parse(new FileReader(dataPath + url));
	 * jsonArray = (JSONArray) data.get("$values");
	 * 
	 * } catch (IOException | NoSuchFieldError |
	 * org.json.simple.parser.ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return jsonArray; }
	 * 
	 * public void throwError(String ex) { trace(ex); }
	 */
}

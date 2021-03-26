package logic;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.mmo.CreateMMORoomSettings;
import com.smartfoxserver.v2.mmo.CreateMMORoomSettings.MapLimits;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;
import com.smartfoxserver.v2.mmo.MMORoom;
import com.smartfoxserver.v2.mmo.Vec3D;

import model.MUFRDatabaseManager;
import model.MUFRDrop;
import model.MUFRDropTable;
import model.MUFRMap;
import model.MUFRMonster;
import model.MUFRServerConfigs;
import model.Tile;

public class ServerReadyHandler extends BaseServerEventHandler{

	private ISFSMMOApi mmoAPi;
	
	private NPCRunner npcRunner;
	private PlayerRunner playerRunner;
	private ItemRunner itemRunner;
	
	private ScheduledFuture<?> npcRunnerTask;
	private ScheduledFuture<?> playerRunnerTask;
	private ScheduledFuture<?> itemTask;
	
	private final MUFRDatabaseManager dbManager;
	private MUFRServerConfigs configs;
	
	public ServerReadyHandler(MUFRDatabaseManager database, MUFRServerConfigs configs) {
		this.dbManager = database;
		this.configs = configs;
	}
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		int i = 0;
		
		// Create default character selection room
		CreateRoomSettings rcfg = new CreateRoomSettings();
		rcfg.setName("Selection");
		rcfg.setDynamic(false);
		rcfg.setGame(false);
		
		try
		{
			getApi().createRoom(getParentExtension().getParentZone(), rcfg, null);
		}
		catch (SFSCreateRoomException ex)
		{
		    ex.printStackTrace();
		}
		
		CreateMMORoomSettings cfg = null;
			
		for (MUFRMap map : configs.getMapsDatabase().maps) 
		{
			cfg = new CreateMMORoomSettings();
			MapLimits mapLimits = new MapLimits(new Vec3D(0, 0, 0), new Vec3D(map.getSize().intX(), 1, map.getSize().intZ()));
			
			cfg.setName(map.getName());
			cfg.setMaxUsers(200);
			cfg.setDynamic(false);
			cfg.setGame(true);
			cfg.setDefaultAOI(new Vec3D(20, 1, 20));
			
			Tile[][] tiles = new Tile[map.getSize().intX()][map.getSize().intZ()];
			map.setTiles(tiles);
			
			cfg.setMapLimits(mapLimits);
			
			cfg.setExtension( new CreateRoomSettings.RoomExtensionSettings("MUFRExtension", "logic.room.MMORoomExtension"));
			
			try
			{
				getApi().createRoom(getParentExtension().getParentZone(), cfg, null);
			}
			catch (SFSCreateRoomException ex)
			{
			    ex.getStackTrace();
			}
		}
		
		for(MUFRDropTable dropTable: configs.getDropTables().getDropTables())
		{
			for(MUFRDrop drop: dropTable.getDrops())
			{
				drop.setItem(dbManager.getNewItem(drop.getSuperType(), drop.getSubtype(), drop.getId()));
				
				// Weapons
				/*
				 * if(drop.getSuperType() == 0) {
				 * drop.setItem(dbManager.getNewItem(drop.getSuperType(), drop.getSubtype(),
				 * equipId)); //Staff1H = 100, Staff2H = 110, Sword1H = 1, Sword2H = 2, Bow = 3,
				 * Crossbow = 4, Shild = 5, Axe1H = 6, Axe2H = 7 if(subType == 1 || subType ==
				 * 2) drop.setItem(MUFRExtension.swordsDataBase.GetByEquipID(id));
				 * 
				 * else if(subType == 100 || subType == 110)
				 * drop.setItem(MUFRExtension.staffsDataBase.GetByEquipID(id));
				 * 
				 * else if(subType == 3 || subType == 4)
				 * drop.setItem(MUFRExtension.bowsDataBase.GetByEquipID(id));
				 * 
				 * else if(subType == 6 || subType == 7)
				 * drop.setItem(MUFRExtension.axesDataBase.GetByEquipID(id)); } // Head == 7,
				 * Armor == 8, Pants == 9, Gloves == 10, Boots == 11 else if(drop.getSuperType()
				 * == 7 || drop.getSuperType() == 8 || drop.getSuperType() == 9 ||
				 * drop.getSuperType() == 10 || drop.getSuperType() == 11) {
				 * if(drop.getSuperType() == 7)
				 * drop.setItem(MUFRExtension.helmDataBase.GetByEquipID(id));
				 * 
				 * else if(drop.getSuperType() == 8)
				 * drop.setItem(MUFRExtension.chestDataBase.GetByEquipID(id));
				 * 
				 * else if(drop.getSuperType() == 9)
				 * drop.setItem(MUFRExtension.pantDataBase.GetByEquipID(id));
				 * 
				 * else if(drop.getSuperType() == 10)
				 * drop.setItem(MUFRExtension.glovesDataBase.GetByEquipID(id));
				 * 
				 * else if(drop.getSuperType() == 11)
				 * drop.setItem(MUFRExtension.bootsDataBase.GetByEquipID(id)); }
				 */		
			}
		}
		
		for(MUFRMonster monster: configs.getActiveMonsterDatabase().monsters)
		{
			MMOItemVariable mmoitemType = new MMOItemVariable("ty", 0);
			MMOItemVariable mmoItemId = new MMOItemVariable("id", monster.getId());
			MMOItemVariable mmoItemCl = new MMOItemVariable("cl", monster.getCurrentLife());
			MMOItemVariable mmoItemdi = new MMOItemVariable("di", i);
		
			MMOItem mmoItem = new MMOItem();
			mmoItem.setVariable(mmoitemType);
			mmoItem.setVariable(mmoItemId);
			mmoItem.setVariable(mmoItemCl);
			mmoItem.setVariable(mmoItemdi);
				
			monster.setAlive(true);
			monster.setItem(mmoItem);
			monster.setAttackCoolDown(0.0);
			monster.setOriginalPos(new Vec3D((int)monster.getPosX(), 1, (int)monster.getPosY()));
			monster.setGameId(i);
			monster.setDrops(configs.getDropTables().GetByID(monster.getId()));
			monster.setRoom((MMORoom) getParentExtension().getParentZone().getRoomById(monster.getMap()));
				
			mmoAPi.setMMOItemPosition(mmoItem, monster.getOriginalPos(), monster.getRoom());
	
			i++;
		}
		
		npcRunner = new NPCRunner(mmoAPi, configs);
		playerRunner = new PlayerRunner(mmoAPi, configs);
		itemRunner = new ItemRunner(mmoAPi, configs);
		try
		{
			simulateMonsters();
			simulatePlayers();
			CleanItens();
		}
		catch (Exception e)
	    {
			trace("error: " + e.getStackTrace());
			e.printStackTrace();
	    }
	}
	
	private void CleanItens() throws Exception
	{
		// Start NPC Task
		itemTask = SmartFoxServer.getInstance().getTaskScheduler().scheduleAtFixedRate
		(
			itemRunner, 			
			0, 							// 0 initial delay
			1000, 						// run every 100ms
			TimeUnit.MILLISECONDS		
		);
	}
	
	private void simulateMonsters() throws Exception 
	{
		// Start NPC Task
		npcRunnerTask = SmartFoxServer.getInstance().getTaskScheduler().scheduleAtFixedRate
		(
			npcRunner, 			
			0, 							// 0 initial delay
			100, 						// run every 100ms
			TimeUnit.MILLISECONDS		
		);
	}
	
	private void simulatePlayers() throws Exception 
	{
		// Start NPC Task
		playerRunnerTask = SmartFoxServer.getInstance().getTaskScheduler().scheduleAtFixedRate
		(
			playerRunner, 			
			0, 							// 0 initial delay
			250, 						// run every 100ms
			TimeUnit.MILLISECONDS		
		);
	}
	
}

package logic.requests;

import javax.persistence.EntityManager;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;

import infra.DAO;
import infra.MUFRItemDAO;
import logic.MUFRExtension;
import model.MUFRDatabaseManager;
import model.MUFRItem;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class PickLoot  extends BaseClientRequestHandler  {

	private ISFSMMOApi mmoAPi;
	private final EntityManager em;
	private final MUFRDatabaseManager dbManager;
	private MUFRServerConfigs configs;
	
	public PickLoot(EntityManager em, MUFRDatabaseManager database, MUFRServerConfigs configs)
	{
		this.em = em;
		this.dbManager = database;
		this.configs = configs;
	}
	
	@Override
	public void handleClientRequest(User user, ISFSObject args) {
		
		MUFRUser thisUser = ((MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser));
		MUFRCharacter character = thisUser.getSelectedCharacter(); 
		ISFSObject returnObject = new SFSObject();
		
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		int id = args.getInt("id");
		int slot = args.getInt("sl");
		
		MMOItem removedItem = null;

		for(MMOItem item: configs.getItems())
		{
			if(item.getVariable("id").getIntValue() == id)
			{
				MUFRItem newItem = dbManager.getNewItem(item.getVariable("sp").getIntValue(), item.getVariable("su").getIntValue(), item.getVariable("ei").getIntValue());
				//newItem.setMmoItem(item);
				/*
				 * if (item.getVariable("sp").getIntValue() == 0) {
				 * if(item.getVariable("su").getIntValue() == 1 ||
				 * item.getVariable("su").getIntValue() == 2){ newItem = new
				 * MUFRItem(MUFRExtension.swordsDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } else if(item.getVariable("su").getIntValue() == 3 ||
				 * item.getVariable("su").getIntValue() == 4){ newItem = new
				 * MUFRItem(MUFRExtension.bowsDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } else if(item.getVariable("su").getIntValue() == 100 ||
				 * item.getVariable("su").getIntValue() == 110){ newItem = new
				 * MUFRItem(MUFRExtension.staffsDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } } else { if (item.getVariable("sp").getIntValue() == 7) { newItem = new
				 * MUFRItem(MUFRExtension.helmDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } else if (item.getVariable("sp").getIntValue() == 8) { newItem = new
				 * MUFRItem(MUFRExtension.chestDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } else if (item.getVariable("sp").getIntValue() == 9) { newItem = new
				 * MUFRItem(MUFRExtension.pantDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } else if (item.getVariable("sp").getIntValue() == 10) { newItem = new
				 * MUFRItem(MUFRExtension.glovesDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } else if (item.getVariable("sp").getIntValue() == 11) { newItem = new
				 * MUFRItem(MUFRExtension.bootsDataBase.GetByEquipID((int)
				 * item.getVariable("ei").getIntValue()));
				 * 
				 * } }
				 */
				
				
				MUFRItemDAO dao = new MUFRItemDAO(MUFRItem.class, em);
								
				//newItem.setDbid(item.getVariable("id").getIntValue());
				newItem.setLevel(item.getVariable("lv").getIntValue());
				newItem.setOpts(item.getVariable("op").getIntValue());
				newItem.setLuck(item.getVariable("lc").getBoolValue());
				newItem.setSlot(slot);
				
				returnObject.putInt("id", (int)newItem.getDbid());
				returnObject.putInt("sp", item.getVariable("sp").getIntValue());
				returnObject.putInt("su",item.getVariable("su").getIntValue());
				returnObject.putInt("lv",item.getVariable("lv").getIntValue());
				returnObject.putInt("op",item.getVariable("op").getIntValue());
				returnObject.putBool("lc",item.getVariable("lc").getBoolValue());
				returnObject.putInt("ei",item.getVariable("ei").getIntValue());
				returnObject.putInt("sl",slot);
				
				newItem.setOwner(character);
				character.getItem().add(newItem);				
				removedItem = item;
				
				dao.openTransaction().persist(newItem).closeTransaction();
				
				mmoAPi.removeMMOItem(item);
				
				send("pick", returnObject, user);
				
				break;
			}
		}
		
		configs.getItems().remove(removedItem);
		
	}
}

package logic.room;

import java.util.LinkedList;
import java.util.List;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.IMMOItemVariable;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;
import com.smartfoxserver.v2.mmo.Vec3D;

import infra.DAO;
import infra.MUFRCharacterDAO;
import logic.MUFRExtension;
import model.MUFRItem;
import model.MUFRItemDatabase;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.characters.MUFRCharacter;
import model.enums.ExcelentOptions;

public class MMORoomDrop extends BaseClientRequestHandler {

	private ISFSMMOApi mmoAPi;
	private final MUFRServerConfigs configs;
	
    public MMORoomDrop(MUFRServerConfigs em)
    {
        this.configs = em;
    }
	
	@Override
	public void handleClientRequest(User user, ISFSObject args) {
		
		MUFRUser thisUser = ((MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser));
		MUFRCharacter character = thisUser.getSelectedCharacter(); 
		ISFSObject returnObject = new SFSObject();
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();	
			
		int id = args.getInt("id");
		int slot = args.getInt("sl");
		int posX = args.getInt("px");
		int posZ = args.getInt("pz");
		
		MUFRItem removedItem = null;
		
		for(MUFRItem item: character.getItem())
		{
			if(item.getDbid() == id)
			{			
				removedItem = item;
				item.setOwner(null);
				
				MMOItem mmoItem = new MMOItem();

				MMOItemVariable mmoitemType = new MMOItemVariable("ty", 2);
				MMOItemVariable mmoitemSuperType = new MMOItemVariable("sp", (int)item.getSuperType());
				MMOItemVariable mmoitemSubType = new MMOItemVariable("su", (int)item.getSubType());
				MMOItemVariable mmoItemId = new MMOItemVariable("id", (int)mmoItem.getId());
				MMOItemVariable mmoItemdi = new MMOItemVariable("ei", (int)item.getEquipId());
				MMOItemVariable mmoItemopt = new MMOItemVariable("op", (int)item.getOpts());
				MMOItemVariable mmoItemLvl = new MMOItemVariable("lv", (int)item.getLevel());
				MMOItemVariable mmoItemLck = new MMOItemVariable("lc", item.isLuck());
				
				MMOItemVariable mmoItemTime = new MMOItemVariable("ti", 30 + ((int)item.getOpts() * 5));

				mmoItem.setVariable(mmoitemType);
				mmoItem.setVariable(mmoItemId);
				mmoItem.setVariable(mmoItemdi);
				mmoItem.setVariable(mmoitemSuperType);
				mmoItem.setVariable(mmoitemSubType);
				mmoItem.setVariable(mmoItemopt);
				mmoItem.setVariable(mmoItemLvl);
				mmoItem.setVariable(mmoItemLck);
				mmoItem.setVariable(mmoItemTime);
				
				MUFRItem mufrItem = new MUFRItem();
				mufrItem.setSuperType(item.getSuperType());
				mufrItem.setSubType(item.getSubType());
				mufrItem.setEquipId((int)item.getEquipId());
				mufrItem.setOpts((int)item.getOpts());
				mufrItem.setLevel((int)item.getLevel());
				mufrItem.setLuck(item.isLuck());
				
				MMOItemVariable mmoIDBID = new MMOItemVariable("id", (int)mufrItem.getDbid());
				
				mmoItem.setVariable(mmoIDBID);
				
				//mufrItem.setVariables(mmoItem.getVariables());
				
				
				//List<ExcelentOptions> exc = new LinkedList<ExcelentOptions>();
				//exc.add(ExcelentOptions.increaseHp);
				//mufrItem.setExcOpts(exc);
				//MMOItemVariable mmoItemExc = new MMOItemVariable("ex", exc);
				//mmoItem.setVariable(mmoItemExc);
				
				mmoAPi.setMMOItemPosition(mmoItem,	new Vec3D(posX, 1, posZ), user.getCurrentMMORoom());
				
				configs.getItems().add(mmoItem);
				
				returnObject.putInt("remove", slot);
	
				send("removeItem", returnObject, user);
				
				/*removedItem = item;
				
				MMOItem mmoItem = new MMOItem();
				mmoItem.setVariables(item.getVariables());
				
				mmoAPi.setMMOItemPosition(mmoItem, new Vec3D(posX, 1, posZ), user.getCurrentMMORoom());
				configs.getItems().add(mmoItem);
				
				returnObject.putInt("remove", slot);
				
				character.getItem().remove(removedItem);
				
				send("removeItem", returnObject, user);*/
				
				break;
			}
		}
		
		if(removedItem != null)
			character.getItem().remove(removedItem);

	}
}

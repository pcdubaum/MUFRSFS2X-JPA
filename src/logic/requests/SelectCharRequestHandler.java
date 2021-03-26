package logic.requests;

import java.util.List;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSJoinRoomException;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;
import com.smartfoxserver.v2.mmo.Vec3D;

import logic.MUFRExtension;
import model.MUFRDatabaseManager;
import model.MUFRItem;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.MUFRUserVariables;
import model.characters.MUFRCharacter;
import model.characters.MUFRDk;
import model.characters.MUFRDw;
import model.characters.MUFRElf;

public class SelectCharRequestHandler extends BaseClientRequestHandler {

	private MUFRServerConfigs configs;
	
	public SelectCharRequestHandler(MUFRServerConfigs configs)
	{
		this.configs = configs;
	}
	
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		
		// extract data from params
		MUFRUser thisUser = (MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser);
		int charID = params.getInt("id");
			
		for(MUFRCharacter character: thisUser.getCharacters())
		{
			if(charID == character.getId())
			{

				thisUser.setSelectedCharacter( character);

				
				MUFRCharacter thisCharacter =  thisUser.getSelectedCharacter();
				
				thisCharacter.setId(character.getId());
				thisCharacter.setName(character.getName());
				thisCharacter.setLevel(character.getLevel());
				thisCharacter.setCclass(character.getCclass());
				thisCharacter.setAlive(true);
				thisCharacter.setDeadCooldown(5.0);
				
				thisCharacter.setStrenght(character.getStrenght());
				thisCharacter.setVitality(character.getVitality()); 
				thisCharacter.setAgility(character.getAgility()); 
				thisCharacter.setEnergy(character.getEnergy()); 

				thisCharacter.setPosx(character.getPosx()); 
				thisCharacter.setPosy(character.getPosy());
				thisCharacter.setMap(character.getMap());
				thisCharacter.setUser(user);
						
				if(thisCharacter.getItem() != null)
					
				for(MUFRItem item: thisCharacter.getItem())
				{
					MMOItem mmoItem = new MMOItem();

					MMOItemVariable mmoitemType = new MMOItemVariable("ty", 2);
					MMOItemVariable mmoitemSuperType = new MMOItemVariable("sp", (int)item.getSuperType());
					MMOItemVariable mmoitemSubType = new MMOItemVariable("su",(int) item.getSubType());
					MMOItemVariable mmoItemId = new MMOItemVariable("id", (int)item.getDbid());
					MMOItemVariable mmoItemdi = new MMOItemVariable("ei", (int)item.getEquipId());
					MMOItemVariable mmoItemopt = new MMOItemVariable("op", (int)item.getOpts());
					MMOItemVariable mmoItemLvl = new MMOItemVariable("lv", (int)item.getLevel());
					MMOItemVariable mmoItemLck = new MMOItemVariable("lc", item.isLuck());
					
					mmoItem.setVariable(mmoitemType);
					mmoItem.setVariable(mmoItemId);
					mmoItem.setVariable(mmoItemdi);
					mmoItem.setVariable(mmoitemSuperType);
					mmoItem.setVariable(mmoitemSubType);
					mmoItem.setVariable(mmoItemopt);
					mmoItem.setVariable(mmoItemLvl);
					mmoItem.setVariable(mmoItemLck);
					
					//item.setMmoItem(mmoItem);
					item.setVariables(mmoItem.getVariables());
					
					if(item.getSlot() == 0)
						thisCharacter.setWeapom(item);
					
					if(item.getSlot() == 6)
						thisCharacter.setWeapom(item);
					
					else if(item.getSlot()  == 10)
						character.setGloves(item);
					
					else if(item.getSlot()  == 11)
						character.setBoots(item);
					
					else if(item.getSlot()  == 9)
						character.setPants(item);
					
					else if(item.getSlot()  == 8)
						character.setChest(item);
					
					else if(item.getSlot()  == 7)
						character.setHelm(item);
				}
				
				thisCharacter.Init();
				
				configs.getUsers().getUsers().add(thisUser);
				
				thisCharacter.getVariables().GenerateVariables(thisCharacter);
				
				try {
					user.setVariables(thisCharacter.getVariables().GetUserVariables());
					getApi().setUserVariables(user, thisCharacter.getVariables().GetUserVariables());
				} catch (SFSVariableException e1) {
					e1.printStackTrace();
				}
				
				Room nextRoom = getParentExtension().getParentZone().getRoomById(character.getMap());
				
				// Join the user
				try {
					getApi().joinRoom(user, nextRoom);
				} catch (SFSJoinRoomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
					
				break;
			}
		}
	}
}
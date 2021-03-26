package logic.requests;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.MMOItem;

import logic.MUFRExtension;
import model.MUFRItem;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class InventoryHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject args) {

		MUFRUser thisUser = ((MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser));
		MUFRCharacter character = thisUser.getSelectedCharacter(); 
		ISFSObject returnObject = new SFSObject();
		
		int id = args.getInt("id");
		int slot = args.getInt("sl");
		
		for(MUFRItem item: character.getItem())
		{
			if(item.getDbid() == id)
			{
				item.setSlot(slot);
				
				if(slot == 0)
					character.setWeapom(item);
				
				else if(slot == 10)
					character.setGloves(item);
				
				else if(slot == 11)
					character.setBoots(item);
				
				else if(slot == 9)
					character.setPants(item);
				
				else if(slot == 8)
					character.setChest(item);
				
				else if(slot == 7)
					character.setHelm(item);
				
				character.calculateTotalDefense();
			}
		}
	}
}

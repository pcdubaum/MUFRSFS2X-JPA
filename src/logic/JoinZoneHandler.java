package logic;

import java.util.List;

import javax.persistence.EntityManager;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import model.MUFRDatabaseManager;
import model.MUFRItem;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class JoinZoneHandler extends BaseServerEventHandler {

	//private final EntityManager em;
	private final MUFRDatabaseManager dbManager;

	public JoinZoneHandler(MUFRDatabaseManager database) {
		//this.em = em;
		this.dbManager = database;
	}

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {

		User thisUser = (User) event.getParameter(SFSEventParam.USER);
		ISFSObject allchars = new SFSObject();
		int counter = 0;

		List<MUFRCharacter> characters = ((MUFRUser) thisUser.getSession().getProperty(MUFRExtension.mufrUser))
				.getCharacters();

		for (MUFRCharacter character : characters) {

			// Create a local char
			ISFSObject thisChar = new SFSObject();
			ISFSArray itemsArray = new SFSArray();

			Long id = (Long) thisUser.getSession().getProperty(MUFRExtension.DATABASE_ID);

			thisChar.putFloat("dbid", id);
			thisChar.putFloat("charId", character.getId());
			thisChar.putInt("Class", character.getCclass());
			thisChar.putInt("Level", character.getLevel());
			thisChar.putFloat("totalxp", character.getTotalxp());
			thisChar.putUtfString("Name", character.getName());

			thisChar.putInt("Strength", character.getStrenght());
			thisChar.putInt("Vitality", character.getVitality());
			thisChar.putInt("Agility", character.getAgility());
			thisChar.putInt("Energy", character.getEnergy());

			thisChar.putFloat("Life", character.getLife());
			thisChar.putFloat("Mana", character.getMana());
			thisChar.putInt("Map", character.getMap());
			thisChar.putDouble("posx", character.getPosx());
			thisChar.putDouble("posz", character.getPosy());

			if (character.getItem() != null)
				
				for (MUFRItem item : character.getItem()) {
					
					itemsArray.addSFSObject(item.GetSFSObject());
					
					//MUFRItem thisItem = null;
					
					dbManager.regenerateItemInfo(item);

					/*if (item.getSuperType() == 0) {
						if(item.getSubType() == 1 || item.getSubType() == 2){
							
							thisItem = new MUFRItem(MUFRExtension.swordsDataBase.GetByEquipID((int) item.getEquipId()));
							item.setMinAttack(thisItem.getMinAttack());
							item.setMaxAttack(thisItem.getMaxAttack());
						} 
						else if(item.getSubType() == 3 || item.getSubType() == 4){
							thisItem = new MUFRItem(MUFRExtension.bowsDataBase.GetByEquipID((int) item.getEquipId()));
							item.setMinAttack(thisItem.getMinAttack());
							item.setMaxAttack(thisItem.getMaxAttack());
						} 
						else if(item.getSubType() == 100 || item.getSubType() == 110){
							thisItem = new MUFRItem(MUFRExtension.staffsDataBase.GetByEquipID((int) item.getEquipId()));
							item.setMinAttack(thisItem.getMinAttack());
							item.setMaxAttack(thisItem.getMaxAttack());
						}
					}
					else
					{
						if (item.getSuperType() == 7) {
							thisItem = new MUFRItem(MUFRExtension.helmDataBase.GetByEquipID((int) item.getEquipId()));
							item.setDefense(thisItem.getDefense());
						}
						else if (item.getSuperType() == 8) {
							thisItem = new MUFRItem(MUFRExtension.chestDataBase.GetByEquipID((int) item.getEquipId()));
							item.setDefense(thisItem.getDefense());
						}
						else if (item.getSuperType() == 9) {
							thisItem = new MUFRItem(MUFRExtension.pantDataBase.GetByEquipID((int) item.getEquipId()));
							item.setDefense(thisItem.getDefense());
						}
						else if (item.getSuperType() == 10) {
							thisItem = new MUFRItem(MUFRExtension.glovesDataBase.GetByEquipID((int) item.getEquipId()));
							item.setDefense(thisItem.getDefense());
						}
						else if (item.getSuperType() == 11)	{ 
							thisItem = new MUFRItem(MUFRExtension.bootsDataBase.GetByEquipID((int) item.getEquipId()));
							item.setDefense(thisItem.getDefense());
						}
					}*/
					
				}

			thisChar.putSFSArray("items", itemsArray);

			allchars.putSFSObject(String.valueOf(counter), thisChar);
			counter++;

			send("Characters", thisChar, thisUser);
		}

		send("Finish", null, thisUser);

		// Join the user
		Room selectCharRoom = getParentExtension().getParentZone().getRoomByName("Selection");

		if (selectCharRoom == null)
			throw new SFSException(
					"The Select Char Room was not found! Make sure a Room called 'Selection' exists in the Zone to make this example work correctly.");

		getApi().joinRoom(thisUser, selectCharRoom);

	}

}

package logic.requests;

import javax.persistence.EntityManager;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

import infra.DAO;
import infra.MUFRCharacterDAO;
import logic.MUFRExtension;
import model.MUFRDatabaseManager;
import model.MUFRItem;
import model.MUFRItemDatabase;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.characters.MUFRCharacter;
import model.characters.MUFRCharacterInfo;
import model.characters.MUFRDk;

public class CreateCharRequestHandler extends BaseClientRequestHandler {

	private final EntityManager em;
	private final MUFRDatabaseManager dbManager;
	private MUFRServerConfigs configs;
	
    public CreateCharRequestHandler(EntityManager em, MUFRDatabaseManager database, MUFRServerConfigs configs)
    {
        this.em = em;
        this.dbManager = database;
        this.configs = configs;
    }
    
	@Override
	public void handleClientRequest(User user, ISFSObject sfObject) {
		
		MUFRUser thisUser = (MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser);
		
		String charName = sfObject.getUtfString("charName");
		int cclass = sfObject.getInt("charClass");
		
		MUFRCharacterDAO dao = new MUFRCharacterDAO(MUFRCharacter.class, em);
			
		try
		{
			if (charName.isEmpty() || charName.length() < 4) {
				ISFSObject returnData = new SFSObject();

				if (charName.isEmpty())
					returnData.putUtfString("badName", "Char name is empty.");
				else
					returnData.putUtfString("badName", "Char name must be 4 char length at least.");

				send("badNameLenght", returnData, user);

				throw new SFSException("Can't create new char. Bad name");
			}
			
			
			MUFRCharacter character = dao.getByName(charName);
			
			if(character != null)
			{				
				ISFSObject returnData = new SFSObject();
				returnData.putUtfString("badName", "Char name is already in use.");
				
				send("badName", returnData, user);
				
				throw new SFSException("Can't create new char. Char name is already in use");
			}
			
			character = new MUFRCharacter();
			
			for (MUFRCharacterInfo thisCharacter : configs.getCharacters()) {
				if (cclass == thisCharacter.charClass) {

					character.setOwner((MUFRUser)user.getSession().getProperty(MUFRExtension.mufrUser));
					//character.setOwner(MUFRExtension.users.getUserByID((Long) user.getSession().getProperty(MUFRExtension.DATABASE_ID)));
					character.setCclass(thisCharacter.charClass);
					character.setLevel(1);
					character.setTotalxp(0L);
					character.setName(charName);

					character.setStrenght(thisCharacter.strenght);
					character.setVitality(thisCharacter.vitality);
					character.setAgility(thisCharacter.agility);
					character.setEnergy(thisCharacter.energy);
					character.setMap(thisCharacter.map);
					character.setPosx(thisCharacter.posx);
					character.setPosy(thisCharacter.posz);
					
					character.setLife(110L);
					character.setMana(20L);
					
					MUFRItem weapon = null;
					
					weapon = dbManager.getNewItem(thisCharacter.startingWeapon.getSuperType(), thisCharacter.startingWeapon.getSubType(), (int)thisCharacter.startingWeapon.getEquipId());
					
					/*
					 * if(thisCharacter.startingWeapon.getSubType() == 1) weapon = new
					 * MUFRItem(MUFRExtension.swordsDataBase.Get(0)); else
					 * if(thisCharacter.startingWeapon.getSubType() == 100 ||
					 * thisCharacter.startingWeapon.getSubType() == 110) weapon = new
					 * MUFRItem(MUFRExtension.staffsDataBase.Get(0)); else
					 * if(thisCharacter.startingWeapon.getSubType() == 3) weapon = new
					 * MUFRItem(MUFRExtension.bowsDataBase.Get(0));
					 */
					
					weapon.setOwner(character);
					
					if(character.getItem() == null)
						character.InitItem();
					
					character.getItem().add(weapon);

					dao.openTransaction()
						.persist(character)
						.closeTransaction();	
					
					thisUser.getCharacters().add(character);					
					// Create a local char
					ISFSObject thisChar = new SFSObject();
					
					Long id = (Long) user.getSession().getProperty(MUFRExtension.DATABASE_ID);
					
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
					
					// Add weapon
					MUFRItem thisItem = new MUFRItem();
					ISFSArray itemsArray = new SFSArray();
					
					SFSObject sfsObject = new SFSObject();
					
					for (MUFRItem item : character.getItem()) {
						
						itemsArray.addSFSObject(item.GetSFSObject());
					}

					/*
					 * if(character.getItem() != null) for (MUFRItem item : character.getItem()) {
					 * if (item.getSlot() == 0) {
					 * 
					 * itemsArray.addSFSObject(item.GetSFSObject());
					 * 
					 * if(item.getSubType() == 0 && item.getSubType() == 0) {
					 * thisChar.putNull("we"); } else { MUFRItem weapon2 = new
					 * MUFRItem(MUFRExtension.swordsDataBase.GetByEquipID((int)item.getEquipId()));
					 * item.setMinAttack(weapon2.getMinAttack());
					 * item.setMaxAttack(weapon2.getMaxAttack()); } } }
					 */
					
					thisChar.putSFSArray("items", itemsArray);
					
					thisChar.putSFSObject("we", weapon.GetSFSObject());
										
					send("Characters", thisChar, user);
					
					break;
				}
			}
			
		} catch (SFSException e) {
			e.printStackTrace();
		}
	}
}

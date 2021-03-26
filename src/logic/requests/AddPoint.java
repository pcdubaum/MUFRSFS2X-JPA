package logic.requests;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

import logic.MUFRExtension;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class AddPoint extends BaseClientRequestHandler {

	private ISFSMMOApi mmoAPi;

	@Override
	public void handleClientRequest(User user, ISFSObject args) {

		MUFRUser thisUser = ((MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser));
		MUFRCharacter character = thisUser.getSelectedCharacter(); 
		ISFSObject returnObject = new SFSObject();

		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		int stat = args.getInt("s");
		int quantity = args.getInt("q");

		// Check Points to add
		if (character.getPointsToAdd() >= quantity) {
			// Add Strenght
			if (stat == 0) {

				character.setStrenght(character.getStrenght() + quantity);
				character.setPointsToAdd(character.getPointsToAdd() - quantity);

				returnObject.putBool("result", true);
				returnObject.putInt("points", character.getPointsToAdd());
				returnObject.putInt("stat", 0);
				returnObject.putInt("quantity", character.getStrenght());

			} 
			// Add Vitality
			else if (stat == 1) {

				character.setVitality(character.getVitality() + quantity);
				character.setPointsToAdd(character.getPointsToAdd() - quantity);

				returnObject.putBool("result", true);
				returnObject.putInt("points", character.getPointsToAdd());
				returnObject.putInt("stat", 1);
				returnObject.putInt("quantity", character.getVitality());
				returnObject.putInt("tl", character.getTotalHeath());

			} 
			// Add Agility
			else if (stat == 2) {

				character.setAgility(character.getAgility() + quantity);
				character.setPointsToAdd(character.getPointsToAdd() - quantity);

				returnObject.putBool("result", true);
				returnObject.putInt("points", character.getPointsToAdd());
				returnObject.putInt("stat",2);
				returnObject.putInt("quantity", character.getAgility());

			}
			// Add Energy
			else if (stat == 3) {

				character.setEnergy(character.getEnergy() + quantity);
				character.setPointsToAdd(character.getPointsToAdd() - quantity);

				returnObject.putBool("result", true);
				returnObject.putInt("points", character.getPointsToAdd());
				returnObject.putInt("stat", 3);
				returnObject.putInt("quantity", character.getEnergy());
				returnObject.putInt("tm", character.getTotalMana());

			}
			
			character.CalculeAtributes();
			character.CalculeBaseDamage();
			
		} 
		// Return error;
		else {
			returnObject.putBool("result", false);
			returnObject.putInt("points", character.getPointsToAdd());
		}

		send("stat", returnObject, user);
	}
}

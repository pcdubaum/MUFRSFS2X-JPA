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

public class Consume extends BaseClientRequestHandler  {

	private ISFSMMOApi mmoAPi;
	
	@Override
	public void handleClientRequest(User user, ISFSObject args) {
		
		MUFRUser thisUser = ((MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser));
		MUFRCharacter character = thisUser.getSelectedCharacter(); 
		ISFSObject returnObject = new SFSObject();
		
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		int stat = args.getInt("s");
		//int quantity = args.getInt("q");
		
		if(stat == 0)
		{
			character.setLife(character.getLife() + 30);
			
			if(character.getLife() > character.getTotalHeath())
				character.setLife((long) character.getTotalHeath());
			
			returnObject.putInt("points", character.getLife().intValue());
			returnObject.putInt("stat", 0);
			
			send("consume", returnObject, user);
		}
	}
}

package logic.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.mmo.Vec3D;

import logic.MUFRExtension;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class MMORoomEvents extends BaseServerEventHandler{

private ISFSMMOApi mmoAPi;
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException
	{
		@SuppressWarnings("unchecked")
        List<UserVariable> variables = (List<UserVariable>) event.getParameter(SFSEventParam.VARIABLES);
		User thisUser = (User) event.getParameter(SFSEventParam.USER);
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();	
		
		MUFRCharacter thisCharacter = ((MUFRUser) thisUser.getSession().getProperty(MUFRExtension.mufrUser)).getSelectedCharacter();
		
		// Make a map of the variables list
		Map<String, UserVariable> varMap = new HashMap<String, UserVariable>();
		for (UserVariable var : variables)
		{
			varMap.put(var.getName(), var);
		}
		
		if (varMap.containsKey("x") && varMap.containsKey("z"))
		{
			Vec3D pos = new Vec3D
			(
				varMap.get("x").getDoubleValue().intValue(),
				1,
				varMap.get("z").getDoubleValue().intValue()
			);
					
			thisCharacter.setPosx(varMap.get("x").getDoubleValue());
			thisCharacter.setPosy(varMap.get("z").getDoubleValue());
			
			if(thisUser.getCurrentMMORoom().getMapHigherLimit().intX() < varMap.get("x").getDoubleValue().intValue() || thisUser.getCurrentMMORoom().getMapHigherLimit().intZ() < varMap.get("z").getDoubleValue().intValue())
			{
				
			}
			else
			{
				mmoAPi.setUserPosition(thisUser, pos, thisUser.getCurrentMMORoom());
			}
		}
	}
	
}

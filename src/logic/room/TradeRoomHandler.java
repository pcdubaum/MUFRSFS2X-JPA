package logic.room;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.entities.variables.Variable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import logic.MUFRExtension;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class TradeRoomHandler extends BaseServerEventHandler {

	private ISFSMMOApi mmoAPi;
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {

		@SuppressWarnings("unchecked")
		List<UserVariable> variables = (List<UserVariable>) event.getParameter(SFSEventParam.VARIABLES);
        Room room = (Room) event.getParameter(SFSEventParam.ROOM);
		User thisUser = (User) event.getParameter(SFSEventParam.USER);
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();	
		
		//MUFRCharacter thisCharacter = ((MUFRUser) thisUser.getSession().getProperty(MUFRExtension.mufrUser)).getSelectedCharacter();
		
		// Make a map of the variables list
		Map<String, UserVariable> varMap = new HashMap<String, UserVariable>();
		
		for (UserVariable var : variables)
		{
			varMap.put(var.getName(), var);
		}
		
		if(thisUser == room.getOwner())
		{
			RoomVariable chaosvar0 = null;
			
			/*if(varMap.containsKey("item0"))
			{
				for(ISFSArray obj: varMap.get("item0").getSFSArrayValue())
				{
					
				}
				
				
				chaosvar0 = new SFSRoomVariable("chaos0", varMap.get("chaos").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}*/
			
			/*if (varMap.containsKey("chaos"))
			{
				chaosvar0 = new SFSRoomVariable("chaos0", varMap.get("chaos").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
			else if (varMap.containsKey("bless"))
			{
				chaosvar0 = new SFSRoomVariable("bless0", varMap.get("bless").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
			else if (varMap.containsKey("soul"))
			{
				chaosvar0 = new SFSRoomVariable("soul0", varMap.get("soul").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
			else if (varMap.containsKey("life"))
			{
				chaosvar0 = new SFSRoomVariable("life0", varMap.get("life").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
		
		}
		
		else if(thisUser != room.getOwner())
		{
			RoomVariable chaosvar0 = null;
			
			if (varMap.containsKey("chaos"))
			{
				chaosvar0 = new SFSRoomVariable("chaos1", varMap.get("chaos").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
			else if (varMap.containsKey("bless"))
			{
				chaosvar0 = new SFSRoomVariable("bless1", varMap.get("bless").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
			else if (varMap.containsKey("soul"))
			{
				chaosvar0 = new SFSRoomVariable("soul1", varMap.get("soul").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}
			
			else if (varMap.containsKey("life"))
			{
				chaosvar0 = new SFSRoomVariable("life1", varMap.get("life").getIntValue());
				
				List<RoomVariable> thisvariables = new LinkedList<RoomVariable>();
				thisvariables.add(chaosvar0);
				
				getApi().setRoomVariables(thisUser, room, thisvariables);
			}*/
		}
	}
}
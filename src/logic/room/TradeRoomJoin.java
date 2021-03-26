package logic.room;

import java.util.LinkedList;
import java.util.List;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class TradeRoomJoin extends BaseServerEventHandler{

	@Override
	public void handleServerEvent(ISFSEvent arg0) throws SFSException {
		
		Room room = (Room) arg0.getParameter(SFSEventParam.ROOM);
		
		ISFSObject params = new SFSObject();
		params.putUtfString("reason", "Complete");
		
		if(room.getUserList().size() == 2)
		{
		
			ISFSObject items0 = new SFSObject();
			
			RoomVariable itemsvar0 = new SFSRoomVariable("items0", items0);
			RoomVariable chaosvar0 = new SFSRoomVariable("chaos0", 0);
			RoomVariable blessvar0 = new SFSRoomVariable("bless0", 0);
			RoomVariable soulvar0 = new SFSRoomVariable("soul0", 0);
			RoomVariable lifevar0 = new SFSRoomVariable("life0", 0);
			
			ISFSObject items1 = new SFSObject();
			
			RoomVariable itemsvar1 = new SFSRoomVariable("items1", items1);
			RoomVariable chaosvar1 = new SFSRoomVariable("chaos1", 0);
			RoomVariable blessvar1 = new SFSRoomVariable("bless1", 0);
			RoomVariable soulvar1 = new SFSRoomVariable("soul1", 0);
			RoomVariable lifevar1 = new SFSRoomVariable("life1", 0);
			
			RoomVariable ready0 = new SFSRoomVariable("soul1", false);
			RoomVariable ready1 = new SFSRoomVariable("life1", false);
			
			List<RoomVariable> variables = new LinkedList<RoomVariable>();
			variables.add(itemsvar0);
			variables.add(chaosvar0);
			variables.add(blessvar0);
			variables.add(soulvar0);
			variables.add(lifevar0);
			variables.add(itemsvar1);
			variables.add(chaosvar1);
			variables.add(blessvar1);
			variables.add(soulvar1);
			variables.add(lifevar1);
			
			room.setMaxRoomVariablesAllowed(10);
			
			room.setVariables(variables);
			
			getApi().setRoomVariables(null, room, variables);
			
			send("opentrade", params, room.getUserList());
		}
	}
}

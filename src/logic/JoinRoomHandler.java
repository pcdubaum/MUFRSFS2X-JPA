package logic;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import model.MUFRUser;
import model.MUFRUserVariables;

public class JoinRoomHandler extends BaseServerEventHandler{

	private ISFSMMOApi mmoAPi;
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();
		
		User thisUser = (User) event.getParameter(SFSEventParam.USER);
		Room room = (Room) event.getParameter(SFSEventParam.ROOM);
		
		MUFRUser mufruser = ((MUFRUser) thisUser.getSession().getProperty(MUFRExtension.mufrUser));
		
		if(room.getGroupId() == "Trade")
			if(room.getId() != 1)
				mufruser.getSelectedCharacter().setMap(thisUser.getCurrentMMORoom().getId());

				
	}
}

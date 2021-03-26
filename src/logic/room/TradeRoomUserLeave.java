package logic.room;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class TradeRoomUserLeave extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent arg0) throws SFSException {

		Room room = (Room) arg0.getParameter(SFSEventParam.ROOM);

		ISFSObject params = new SFSObject();
		params.putUtfString("reason", "Player Left");

		send("closetrade", params, room.getUserList());

		getApi().removeRoom(room);
	}

}

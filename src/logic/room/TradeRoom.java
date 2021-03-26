package logic.room;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.SFSRoomEvents;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class TradeRoom  extends SFSExtension{ 
	
	@Override
	public void init() {
		
		addEventHandler(SFSEventType.USER_LEAVE_ROOM, TradeRoomUserLeave.class);
		addEventHandler(SFSEventType.USER_JOIN_ROOM, TradeRoomJoin.class);
		addEventHandler(SFSEventType.ROOM_VARIABLES_UPDATE, TradeRoomHandler.class);
	}
}

package logic.room;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MMORoomExtension extends SFSExtension{

	@Override
	public void init() {
		
		addEventHandler(SFSEventType.USER_VARIABLES_UPDATE, MMORoomEvents.class);

		
	}

}

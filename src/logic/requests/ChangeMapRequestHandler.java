package logic.requests;

import javax.persistence.EntityManager;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSJoinRoomException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.Vec3D;

import logic.MUFRExtension;
import model.MUFRDatabaseManager;
import model.MUFRMap;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.MUFRUserVariables;

public class ChangeMapRequestHandler extends BaseClientRequestHandler {

	private ISFSMMOApi mmoAPi;
	private MUFRServerConfigs configs;
	
	 public ChangeMapRequestHandler(MUFRServerConfigs configs)
	 {
	        this.configs = configs;
	 }
	
	@Override
	public void handleClientRequest(User thisUser, ISFSObject params) {
		
		MUFRUser userVariables = ((MUFRUser) thisUser.getSession().getProperty(MUFRExtension.mufrUser));
		//userVariables.RegenerateVariables(user);
		
		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();	
		int nextMapId = params.getInt("id");
		boolean resendMap = params.getBool("rs");
		int currentMapId = 0; 
		Room nextRoom = getParentExtension().getParentZone().getRoomById(nextMapId);
		
		if(thisUser.getCurrentMMORoom() != null)
			currentMapId = thisUser.getCurrentMMORoom().getId();
		
		try {

			getApi().leaveRoom(thisUser, thisUser.getCurrentMMORoom());
			
			if (nextMapId == 2)
			{
				ISFSObject sfsObject = new SFSObject();
				
				if(currentMapId == 3)
				{
					userVariables.getSelectedCharacter().setPosx(122);
					userVariables.getSelectedCharacter().setPosy(118);
					//userVariables.posX = 122;
					//userVariables.posZ = 118;
				}
				else if(currentMapId == 4)
				{
					userVariables.getSelectedCharacter().setPosx(151);
					userVariables.getSelectedCharacter().setPosy(128);
					//userVariables.posX = 122;
					//userVariables.posZ = 118;
				}
				else if(currentMapId == 5)
				{
					userVariables.getSelectedCharacter().setPosx(114);
					userVariables.getSelectedCharacter().setPosy(128);
					//userVariables.posX = 122;
					//userVariables.posZ = 118;
				}
				else	
				{
					userVariables.getSelectedCharacter().setPosx(141);
					userVariables.getSelectedCharacter().setPosy(107);
					//userVariables.posX = 141;
					//userVariables.posZ = 107;
				}
				
				sfsObject.putInt("id", 2);
				sfsObject.putInt("x", (int)	userVariables.getSelectedCharacter().getPosx());
				sfsObject.putInt("z", (int) userVariables.getSelectedCharacter().getPosy());
				
				if(resendMap)
				send("LoadMap", sfsObject, thisUser);
			}
			else if (nextMapId == 3)
			{
				MUFRMap map = configs.getMapsDatabase().GetByID(nextMapId);
				
				userVariables.getSelectedCharacter().setPosx(16);
				userVariables.getSelectedCharacter().setPosy(6);
					//userVariables.posX = 8;
					//userVariables.posZ = 4;
				
				ISFSObject sfsObject = new SFSObject();
				sfsObject.putInt("id", nextMapId);
				sfsObject.putInt("x", (int)	userVariables.getSelectedCharacter().getPosx());
				sfsObject.putInt("z", (int) userVariables.getSelectedCharacter().getPosy());
				
				if(resendMap)
				send("LoadMap", sfsObject, thisUser);
			}
			else if(nextMapId == 4 || nextMapId == 5)
			{
				MUFRMap map = configs.getMapsDatabase().GetByID(nextMapId);

				userVariables.getSelectedCharacter().setPosx(15);
				userVariables.getSelectedCharacter().setPosy(4);
					//userVariables.posX = 8;
					//userVariables.posZ = 4;
				
				ISFSObject sfsObject = new SFSObject();
				sfsObject.putInt("id", nextMapId);
				sfsObject.putInt("x", (int)	userVariables.getSelectedCharacter().getPosx());
				sfsObject.putInt("z", (int) userVariables.getSelectedCharacter().getPosy());
				
				if(resendMap)
				send("LoadMap", sfsObject, thisUser);
			}
			else 
			{
				ISFSObject sfsObject = new SFSObject();
				
				if(currentMapId == 3)
				{
					userVariables.getSelectedCharacter().setPosx(122);
					userVariables.getSelectedCharacter().setPosy(118);
					//userVariables.posX = 122;
					//userVariables.posZ = 118;
				}
				else	
				{
					userVariables.getSelectedCharacter().setPosx(141);
					userVariables.getSelectedCharacter().setPosy(107);
					//userVariables.posX = 141;
					//userVariables.posZ = 107;
				}
				
				sfsObject.putInt("id", 2);
				sfsObject.putInt("x", (int)	userVariables.getSelectedCharacter().getPosx());
				sfsObject.putInt("z", (int) userVariables.getSelectedCharacter().getPosy());
				
				if(resendMap)
				send("LoadMap", sfsObject, thisUser);
			}
			
			userVariables
			.getSelectedCharacter().setMap(nextMapId);
			
			// Join the user
			getApi().joinRoom(thisUser, nextRoom);
			
		} catch (SFSJoinRoomException e) {
			e.printStackTrace();
		}
		finally
		{			
			getApi().setUserVariables(thisUser, userVariables.getSelectedCharacter().getVariables().GetUserVariables());
			userVariables.getSelectedCharacter().setRoom(thisUser.getCurrentMMORoom());
			mmoAPi.setUserPosition(thisUser, new Vec3D((int) userVariables.getSelectedCharacter().getPosx(), 0, (int) userVariables.getSelectedCharacter().getPosy()), thisUser.getCurrentMMORoom());
		}
	}
}

package logic;

import java.util.List;

import javax.persistence.EntityManager;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import infra.MUFRCharacterDAO;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class DisconnectHandler extends BaseServerEventHandler {

	private final EntityManager em;

	public DisconnectHandler(EntityManager em) {
		this.em = em;
	}

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		
		User thisUser = (User) event.getParameter(SFSEventParam.USER);
		List<Room> joinedRooms = (List<Room>) event.getParameter(SFSEventParam.JOINED_ROOMS);

		for (Room room : joinedRooms)
		{
			if (room.getId() >= 2)
			{
				MUFRCharacterDAO dao = new MUFRCharacterDAO(MUFRCharacter.class, em);

				dao.openTransaction()
				.merge(((MUFRUser) thisUser.getSession().getProperty(MUFRExtension.mufrUser)).getSelectedCharacter())
				.closeTransaction();
				
				break;
			}
		}
	}
}

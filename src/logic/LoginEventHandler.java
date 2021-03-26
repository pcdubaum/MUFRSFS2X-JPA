package logic;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.security.DefaultPermissionProfile;

import infra.MUFRUserDAO;
import model.MUFRUser;
import model.characters.MUFRJewels;

public class LoginEventHandler extends BaseServerEventHandler{
	
	private final EntityManager em;
	
    public LoginEventHandler(EntityManager em)
    {
        this.em = em;
    }
 
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
    	String userName = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
    	String cryptedPass = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
    	ISession session = (ISession) event.getParameter(SFSEventParam.SESSION);
    	
    	MUFRUserDAO dao = new MUFRUserDAO(MUFRUser.class, this.em);
    	MUFRUser user = new MUFRUser();
     	
    	user = dao.getByName(userName);
    	
    	//trace(user);
    	   	
    	if(user == null){
    		// This is the part that goes to the client
			SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
			errData.addParameter(userName);
			
			// This is logged on the server side
			throw new SFSLoginException("Bad user name: " + userName, errData);
    	}
    	
    	//make sure there is a password before you try to use the checkSecurePassword function
        if (cryptedPass.equals("")){
            SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
            data.addParameter(userName);
            
         // This is logged on the server side
            throw new SFSLoginException("You must enter a password.", data);
        }
        
        // Get some information.
        String dbPword = user.getPassword();
		Long dbId = user.getId();
		
		// Verify the secure password
		if (!getApi().checkSecurePassword(session, dbPword, cryptedPass)) {
			SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
			data.addParameter(userName);

			// This is logged on the server side
			throw new SFSLoginException("Login failed for user: " + userName, data);
		}
		
		dao.openTransaction();
		
		user.setLastLogin(new Date());
		user.setLastIp(session.getAddress());
		
		if(user.getJewels() == null)
		{
			MUFRJewels jewels = new MUFRJewels();
			jewels.setOwner(user);
			user.setJewels(jewels);
		}
		
		dao.persist(user);
		dao.closeTransaction();
		
		//MUFRExtension.users.getUsers().add(user);
		
		if(user.getAccount_level() == 0)
			session.setProperty("$permission", DefaultPermissionProfile.STANDARD);
		else if(user.getAccount_level() == 1)
			session.setProperty("$permission", DefaultPermissionProfile.MODERATOR);
		else if(user.getAccount_level() == 2)
			session.setProperty("$permission", DefaultPermissionProfile.ADMINISTRATOR);
		
    	// Store the client dbId in the session
    	session.setProperty(MUFRExtension.DATABASE_ID, dbId);
    	session.setProperty(MUFRExtension.mufrUser, user);
    }
}

package logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.smartfoxserver.v2.components.signup.ISignUpAssistantPlugin;
import com.smartfoxserver.v2.components.signup.SignUpAssistantComponent;
import com.smartfoxserver.v2.components.signup.SignUpConfiguration;
import com.smartfoxserver.v2.components.signup.SignUpValidationException;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class RegisterExtension extends SFSExtension{

private SignUpAssistantComponent suac;
	
	@Override
	public void init()
	{
		suac = new SignUpAssistantComponent();
		suac.getConfig().extraFields = Arrays.asList("creation_date", "last_login", "last_ip", "country");
		
	    suac.getConfig().signUpTable = "user_info";
	    suac.getConfig().usernameField = "user_name";
	    suac.getConfig().passwordField = "user_pwd";
	    suac.getConfig().emailField = "email";
	    suac.getConfig().checkForDuplicateEmails = true;
	    suac.getConfig().checkForDuplicateUserNames = true;
	    
	    suac.getConfig().minUserNameLength = 4;
	    suac.getConfig().maxUserNameLength = 16;
	    suac.getConfig().minPasswordLength = 31;
	    suac.getConfig().maxPasswordLength = 32;
	    
        
	    // Add a pre-process plugin for custom validation
	    suac.getConfig().preProcessPlugin = new ISignUpAssistantPlugin()
	    {
	        @Override
	        public void execute(User arg0, ISFSObject params, SignUpConfiguration arg2) throws SignUpValidationException
	        {
	        	String country = params.getUtfString("country");
	        	Date creation_date = null;;
				try {
					creation_date = new SimpleDateFormat("yyyy/MM/dd").parse(params.getUtfString("creation_date"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	        		        	
	        	if(country == null)
	        		country = "BR";
	        	
	        	trace(creation_date);
	        }
	    };
	     
		addRequestHandler(SignUpAssistantComponent.COMMAND_PREFIX, suac);
	}
}

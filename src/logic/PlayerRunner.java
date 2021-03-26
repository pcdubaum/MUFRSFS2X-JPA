package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.mmo.Vec3D;

import model.MUFRMonster;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.characters.MUFRCharacter;

public class PlayerRunner extends SFSExtension implements Runnable{

	private ISFSMMOApi mmoAPi;
	private MUFRServerConfigs configs;
	
	public PlayerRunner(ISFSMMOApi mmoAPi, MUFRServerConfigs configs) {
		super();
		this.mmoAPi = mmoAPi;
		this.configs = configs;
	}
	
	@Override
	public void run() {
		
		int i = 0;
		
		for (MUFRUser user : configs.getUsers().getUsers())
		{
			MUFRCharacter character = user.getSelectedCharacter();
			
			if(character.isAlive() == false)
			{
				character.setDeadCooldown(character.getDeadCooldown() - 0.250);
				
				if(character.getDeadCooldown() <= 0)
				{
					character.setAlive(true);
					character.setPosx(141);
					character.setPosy(107);
					character.setDeadCooldown(5.0);
					character.setLife((long) character.getTotalHeath());
					
					mmoAPi.setUserPosition(character.getUser(), new Vec3D(141, 1, 107), character.getUser().getCurrentMMORoom());
					
					List<UserVariable> changedVars = new LinkedList<UserVariable>();
					UserVariable var = new SFSUserVariable("ia", true);
					UserVariable var2 = new SFSUserVariable("x", (double)141);
					UserVariable var3 = new SFSUserVariable("z", (double)107);
					UserVariable life = new SFSUserVariable("he", user.getSelectedCharacter().getTotalHeath());
					
					changedVars.add(var);
					changedVars.add(var2);
					changedVars.add(var3);
					changedVars.add(life);
					
					try {
						character.getUser().setVariables(character.getVariables().GetUserVariables());
						getApi().setUserVariables(character.getUser(), changedVars);
					} catch (SFSVariableException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}

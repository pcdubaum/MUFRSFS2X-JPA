package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.smartfoxserver.bitswarm.clustering.handlers.SendMessageHandler;
import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.mmo.BaseMMOItem;
import com.smartfoxserver.v2.mmo.IMMOItemVariable;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;
import com.smartfoxserver.v2.mmo.Vec3D;

import model.MUFRMonster;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.enums.MonsterBehaviour;

public class NPCRunner extends SFSExtension implements Runnable {

	private ScheduledFuture<?> npcRunnerTask;
	private ISFSMMOApi mmoAPi;
	private MUFRServerConfigs configs;
	
	 //instância um objeto da classe Random usando o construtor padrão
	//ThreadLocalRandom randomGenerator = new Random();
	
	float distance;

	public NPCRunner(ISFSMMOApi mmoAPi, MUFRServerConfigs configs) {
		super();
		this.mmoAPi = mmoAPi;
		this.configs = configs;
	}

	@Override
	public void run() {

		for (MUFRMonster monster : configs.getActiveMonsterDatabase().monsters) {
		
			Update(monster);
		
			if (monster.isAlive()) {
				List<User> proximity = monster.getRoom().getProximityList(
						new Vec3D((int) monster.getPosX(), 1, (int) monster.getPosY()), new Vec3D(8, 1, 8));

				if (proximity.size() > 0) {
					for (User user : proximity) {
						
						MUFRUser thisUser = (MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser);
						
						if(thisUser.getSelectedCharacter().isAlive())
						{

						float posx = user.getVariable("x").getDoubleValue().floatValue();
						float posz = user.getVariable("z").getDoubleValue().floatValue();

						distance = (float) Math.sqrt((monster.getPosX() - posx) * (monster.getPosX() - posx)
								+ (monster.getPosY() - posz) * (monster.getPosY() - posz));
						

						if (distance <= monster.getRange()) {
							HandleAttack(monster, user);
						} else {
							Chase(monster, new Vec3D(posx, 1, posz));
						}
						}
					}
				}
				else
				{
					monster.setBehaviour(MonsterBehaviour.idle);
					
					distance = (float) Math.sqrt((monster.getPosX() - monster.getOriginalPos().floatX()) * (monster.getPosX() - monster.getOriginalPos().floatX())
							+ (monster.getPosY() - monster.getOriginalPos().floatZ()) * (monster.getPosY() - monster.getOriginalPos().floatZ()));
					
					if(monster.getOriginalPos().floatX() > monster.getPosX())
						monster.setPosX(monster.getPosX() + 0.2);
					else
						monster.setPosX(monster.getPosX() - 0.2);
					
					if(monster.getOriginalPos().floatZ() > monster.getPosY())
						monster.setPosY(monster.getPosY() + 0.2);
					else
						monster.setPosY(monster.getPosY() - 0.2);
					

					List<IMMOItemVariable> vars = new LinkedList<>();
					vars.add(new MMOItemVariable("px", monster.getPosX()));
					vars.add(new MMOItemVariable("py", monster.getPosY()));
					vars.add(new MMOItemVariable("cl", monster.getCurrentLife()));

					mmoAPi.setMMOItemPosition(monster.getItem(), new Vec3D((int) monster.getPosX(), 1, (int) monster.getPosY()),
							monster.getRoom());

					mmoAPi.setMMOItemVariables(monster.getItem(), vars);
				}
			}
			
			//monster.setDeadColldown(monster.getAttackCoolDown() - 0.1);
			
			//if(monster.getDeadColldown() <= 0.0)
			//{
			//	monster.setAlive(true);
			//	monster.setCurrentLife(monster.getLife());
			//}
		}
	}

	private void Chase(MUFRMonster monster, Vec3D newPos) {
		
		monster.setBehaviour(MonsterBehaviour.chasing);
		
		if (monster.getPosX() > newPos.floatX())
			monster.setPosX(monster.getPosX() - 0.2);
		else
			monster.setPosX(monster.getPosX() + 0.2);

		if (monster.getPosY() > newPos.floatZ())
			monster.setPosY(monster.getPosY() - 0.2);
		else
			monster.setPosY(monster.getPosY() + 0.2);

		List<IMMOItemVariable> vars = new LinkedList<>();
		vars.add(new MMOItemVariable("px", monster.getPosX()));
		vars.add(new MMOItemVariable("py", monster.getPosY()));

		mmoAPi.setMMOItemPosition(monster.getItem(), new Vec3D((int) monster.getPosX(), 1, (int) monster.getPosY()),
				monster.getRoom());

		mmoAPi.setMMOItemVariables(monster.getItem(), vars);
	}

	private void HandleAttack(MUFRMonster monster, User user) {
		monster.setBehaviour(MonsterBehaviour.attacking);
		
		double coolDown = monster.getAttackCoolDown();
		
		if (coolDown <= 0.0) {
			
			MUFRUser thisUser = (MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser);
			
			monster.setAttackCoolDown(1.0);
			
			int id = thisUser.getSelectedCharacter().getId().intValue();
			
			int diff = monster.getMaxDmg() - monster.getMinDmg();
			int damage = ThreadLocalRandom.current().nextInt(diff + 1);
			damage += monster.getMinDmg();
			
			damage -= thisUser.getSelectedCharacter().getDefense();
			
			if(damage < 1)
				damage = 1;
			
			thisUser.getSelectedCharacter().setLife(thisUser.getSelectedCharacter().getLife() - damage);
			
			if(thisUser.getSelectedCharacter().getLife() < 1)
			{
				thisUser.getSelectedCharacter().setDeadCooldown(5.0);
				thisUser.getSelectedCharacter().setLife(0L);
				thisUser.getSelectedCharacter().setAlive(false);
			}
			
			List<IMMOItemVariable> vars = new LinkedList<>();

			vars.add(new MMOItemVariable("px", monster.getPosX()));
			vars.add(new MMOItemVariable("py", monster.getPosY()));
			vars.add(new MMOItemVariable("at", id));
			vars.add(new MMOItemVariable("da", damage));
			
			List<UserVariable> changedVars = new LinkedList<UserVariable>();
			UserVariable alive = new SFSUserVariable("ia", false);
			UserVariable life = new SFSUserVariable("he", thisUser.getSelectedCharacter().getLife().intValue());
			UserVariable posx = new SFSUserVariable("px", monster.getPosX());
			UserVariable posy = new SFSUserVariable("py", monster.getPosY());
			
			if(thisUser.getSelectedCharacter().getLife() < 1)
				changedVars.add(alive);
			
			changedVars.add(life);

			mmoAPi.setMMOItemVariables(monster.getItem(), vars);
						
			try {
				thisUser.getSelectedCharacter().getUser().setVariables(changedVars);
				getApi().setUserVariables(thisUser.getSelectedCharacter().getUser(), changedVars);
			} catch (SFSVariableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			

		}
	}
	
	private void Update(MUFRMonster monster)
	{
		double coolDown = monster.getAttackCoolDown();
		
		if(coolDown > 0)
			monster.setAttackCoolDown(monster.getAttackCoolDown() - 0.1);
		
		if(!monster.isAlive())
		{
			if(monster.getDeadColldown() > 0)
			{
				monster.setDeadColldown(monster.getDeadColldown() - 0.1);
			}
			else
			{
				monster.setAlive(true);
				monster.setCurrentLife(monster.getLife());
				monster.setPosX(monster.getOriginalPos().floatX());
				monster.setPosY(monster.getOriginalPos().floatZ());
			}
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}

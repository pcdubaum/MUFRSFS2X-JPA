package logic.requests;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSVariableException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.IMMOItemVariable;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;
import com.smartfoxserver.v2.mmo.Vec3D;

import infra.DAO;
import logic.MUFRExtension;
import model.MUFRDrop;
import model.MUFRDropTable;
import model.MUFRItem;
import model.MUFRMonster;
import model.MUFRServerConfigs;
import model.MUFRUser;
import model.MUFRUserVariables;
import model.characters.MUFRCharacter;
import model.characters.MUFRDk;
import model.enums.ExcelentOptions;
import model.enums.MonsterBehaviour;

public class AttackRequestHandler extends BaseClientRequestHandler {

	private ISFSMMOApi mmoAPi;
	private final MUFRServerConfigs configs;
	
    public AttackRequestHandler(MUFRServerConfigs em)
    {
        this.configs = em;
    }
    
	@Override
	public void handleClientRequest(User user, ISFSObject params) {

		MUFRUser thisUser = (MUFRUser) user.getSession().getProperty(MUFRExtension.mufrUser);
		
		MUFRCharacter character = thisUser.getSelectedCharacter();
		ISFSObject sfsObject = new SFSObject();

		mmoAPi = SmartFoxServer.getInstance().getAPIManager().getMMOApi();

		int enemyId = params.getInt("id");
		int damage = params.getInt("dg");
		int returnLife = 0;

		for (MUFRMonster monster : configs.getActiveMonsterDatabase().monsters) {
			if (monster.getGameId() == enemyId) {
				if (monster.isAlive()) {

					damage = character.ReturnBaseDamage();

					damage -= monster.getDefense();

					if (damage < 0)
						damage = 0;

					monster.setCurrentLife(monster.getCurrentLife() - damage);

					returnLife = monster.getCurrentLife();

					List<IMMOItemVariable> vars = new LinkedList<>();
					vars.add(new MMOItemVariable("cl", monster.getCurrentLife()));

					mmoAPi.setMMOItemVariables(monster.getItem(), vars);

					if (returnLife < 1) {
						monster.setAlive(false);
						
						if(character.addXp(10 * monster.getLevel() * configs.getXpMultiplier()) == true)
						{
							List<UserVariable> changedVars = new LinkedList<UserVariable>();
							UserVariable level = new SFSUserVariable("lv", thisUser.getSelectedCharacter().getLevel());
							UserVariable points = new SFSUserVariable("rp", thisUser.getSelectedCharacter().getPointsToAdd());
							
							changedVars.add(level);
							changedVars.add(points);							
						
							getApi().setUserVariables(thisUser.getSelectedCharacter().getUser(), changedVars);
							
						}
						
						
						monster.setDeadColldown(25.0);
						monster.setCurrentLife(0);
						sfsObject.putInt("xp", character.getTotalxp().intValue());

						int droppc = ThreadLocalRandom.current().nextInt(100);

						if (droppc < MUFRExtension.configs.getDropRate()) {

							int quantity = 0;

							for (MUFRDrop drop : monster.getDrops().getDrops())
								quantity += drop.getRate();

							int item = ThreadLocalRandom.current().nextInt(quantity);

							for (MUFRDrop drop : monster.getDrops().getDrops()) {
								item -= drop.getRate();

								if (item < 0) {
									
									int luckCheck = ThreadLocalRandom.current().nextInt(100);
									int optCheck = ThreadLocalRandom.current().nextInt(100);

									boolean luck = false;
									int opt = 0;

									if (luckCheck < 10)
										luck = true;

									if (optCheck < 4)
										opt = 12;
									else if (optCheck < 10)
										opt = 8;
									else if (optCheck < 24)
										opt = 4;
									
									MMOItem mmoItem = new MMOItem();

									MMOItemVariable mmoitemType = new MMOItemVariable("ty", 2);
									MMOItemVariable mmoitemSuperType = new MMOItemVariable("sp", drop.getItem().getSuperType());
									MMOItemVariable mmoitemSubType = new MMOItemVariable("su", drop.getItem().getSubType());
									//MMOItemVariable mmoItemId = new MMOItemVariable("id", mmoItem.getId());
									MMOItemVariable mmoItemCl = new MMOItemVariable("cl", monster.getCurrentLife());
									MMOItemVariable mmoItemdi = new MMOItemVariable("ei", (int)drop.getItem().getEquipId());
									MMOItemVariable mmoItemopt = new MMOItemVariable("op", opt);
									MMOItemVariable mmoItemLvl = new MMOItemVariable("lv", 0);
									MMOItemVariable mmoItemLck = new MMOItemVariable("lc", luck);
									
									MMOItemVariable mmoItemTime = new MMOItemVariable("ti", 30 + (opt * 5));

									MUFRItem mufrItem = new MUFRItem();
								
									
									mufrItem.setSuperType(drop.getItem().getSuperType());
									mufrItem.setSubType(drop.getItem().getSubType());
									mufrItem.setEquipId((int)drop.getItem().getEquipId());
									mufrItem.setOpts(opt);
									mufrItem.setLevel(0);
									mufrItem.setLuck(luck);
									
									MMOItemVariable mmoIDBID = new MMOItemVariable("id", (int)mufrItem.getDbid());
									
									mmoItem.setVariable(mmoIDBID);
									mmoItem.setVariable(mmoitemType);
									//mmoItem.setVariable(mmoItemId);
									mmoItem.setVariable(mmoItemCl);
									mmoItem.setVariable(mmoItemdi);
									mmoItem.setVariable(mmoitemSuperType);
									mmoItem.setVariable(mmoitemSubType);
									mmoItem.setVariable(mmoItemopt);
									mmoItem.setVariable(mmoItemLvl);
									mmoItem.setVariable(mmoItemLck);
									mmoItem.setVariable(mmoItemTime);
									
									//List
	
									//mufrItem.setVariables(mmoItem.getVariables());
									
									
									if(newDropLevel() == 0)
									{
										List<ExcelentOptions> exc = new LinkedList<ExcelentOptions>();
										//exc.add(ExcelentOptions.increaseHp);
										mufrItem.setExcOpts(exc);
										MMOItemVariable mmoItemExc = new MMOItemVariable("ex", exc);
										mmoItem.setVariable(mmoItemExc);
									}
									
									
									//mmoAPi.setMMOItemVariables(mmoItem,
									mmoAPi.setMMOItemPosition(mmoItem,
											new Vec3D((int) monster.getPosX(), 1, (int) monster.getPosY()),
											user.getCurrentMMORoom());
									
									configs.getItems().add(mmoItem);

									break;
								}
							}
						}
						
					}

					sfsObject.putInt("id", enemyId);
					sfsObject.putInt("dg", damage);
					sfsObject.putInt("cl", returnLife);

					send("createDmg", sfsObject, user);
					// break;

				}
			}
		}
	}
	
	private int newDropLevel()
	{
		int quality = ThreadLocalRandom.current().nextInt(1000);
		
		if(quality <= configs.getExcDropRate() / 2 || quality >= 999 - configs.getExcDropRate() / 2)
			return 0;
		
		return 1;
	}
}

package logic;

import java.util.LinkedList;
import java.util.List;

import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.mmo.MMOItem;
import com.smartfoxserver.v2.mmo.MMOItemVariable;

import model.MUFRServerConfigs;


public class ItemRunner extends SFSExtension implements Runnable{

	private ISFSMMOApi mmoAPi;
	private MUFRServerConfigs configs;
	
	@Override
	public void init() {
		
	}

	public ItemRunner(ISFSMMOApi mmoAPi, MUFRServerConfigs _configs) {
		super();
		this.mmoAPi = mmoAPi;
		this.configs = _configs;
	}

	@Override
	public void run() {
		List<MMOItem> removedItens = new LinkedList<MMOItem>();
		
		for (MMOItem item : configs.items) {
			 int time = item.getVariable("ti").getIntValue();
			 
			 //if(item.getVariable("op").getIntValue() ==  0)
			 time--;
			 
			 if(time == 0) {
				 removedItens.add(item);
				 mmoAPi.removeMMOItem(item);
			 }
			 else {
				 
				 MMOItemVariable var = new MMOItemVariable("ti", time);
				 item.setVariable(var);
			 }
				 
		}
		
		configs.items.removeAll(removedItens);
	}
}



package model;

import java.util.ArrayList;
import java.util.List;

public class MUFRMonsterDatabase {
	public List<MUFRMonster> monsters;

	public MUFRMonsterDatabase() {
		monsters = new ArrayList<MUFRMonster>();
	}

	// Get the specified MUFRItem by index.
	public MUFRMonster Get(int index) {
		return (monsters.get(index));
	}

	// Gets the specified MUFRItem by ID.
	public MUFRMonster GetByID(int ID) {
		//for (int i = 0; i < this.monsters.size(); i++) {
			//if (this.monsters.get(i).getId() == ID)
				//return this.monsters.get(i);
		//}
		
		for(MUFRMonster monster: monsters)
		{
			if(monster.getId() == ID)
			{
				return monster;
			}
		}

		return null;
	}
}

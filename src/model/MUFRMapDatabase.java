package model;

import java.util.ArrayList;
import java.util.List;

public class MUFRMapDatabase {
	public List<MUFRMap> maps;

	public MUFRMapDatabase() {
		maps = new ArrayList<MUFRMap>();
	}

	// Get the specified MUFRItem by index.
	public MUFRMap Get(int index) {
		return (maps.get(index));
	}
	
	public MUFRMap GetByID(int ID) {
		for(MUFRMap map : maps)
		{
			if(map.getId() == ID)
			{
				return map;
			}
		}
		
		return null;
	}

	// Gets the specified MUFRItem by ID.
	/*public MUFRMap GetByID(int ID) {
		for (int i = 0; i < this.maps.size(); i++) {
			if (this.maps.get(i).getId() == ID)
				return this.maps.get(i);
		}

		return null;
	}*/
}

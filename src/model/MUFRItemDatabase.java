package model;

import java.util.ArrayList;
import java.util.List;

public class MUFRItemDatabase {

	public List<MUFRItem> items;

	public MUFRItemDatabase() {
		items = new ArrayList<MUFRItem>();
	}

	// Get the specified MUFRItem by index.
	public MUFRItem Get(int index) {
		return (items.get(index));
	}
	
	public MUFRItem GetByEquipID(int ID) {
		for(MUFRItem item : items)
		{
			if(item.getEquipId() == ID)
			{
				return item;
			}
		}
		
		return null;
	}

	// Gets the specified MUFRItem by ID.
	/*public MUFRItem GetByID(int ID) {
		for (int i = 0; i < this.items.size(); i++) {
			if (this.items.get(i).getEquipId() == ID)
				return this.items.get(i);
		}

		return null;
	}*/
}

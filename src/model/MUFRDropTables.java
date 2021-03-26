package model;

import java.util.ArrayList;
import java.util.List;

public class MUFRDropTables {

	public List<MUFRDropTable> dropTables;

	public MUFRDropTables() {
		dropTables = new ArrayList<MUFRDropTable>();
	}

	// Get the specified MUFRItem by index.
	public MUFRDropTable Get(int index) {
		return (dropTables.get(index));
	}

	// Gets the specified MUFRItem by ID.
	public MUFRDropTable GetByID(int ID) {
		for (int i = 0; i < this.dropTables.size(); i++) {
			if (this.dropTables.get(i).getId() == ID)
				return this.dropTables.get(i);
		}

		return null;
	}

	public List<MUFRDropTable> getDropTables() {
		return dropTables;
	}

	public void setDropTables(List<MUFRDropTable> dropTables) {
		this.dropTables = dropTables;
	}
}

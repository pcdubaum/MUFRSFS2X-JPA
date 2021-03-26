package model;

import java.util.LinkedList;
import java.util.List;

public class MUFRDropTable {
	private int id;
    private List<MUFRDrop> drops;
    
    public MUFRDropTable()
    {
    	drops = new LinkedList<MUFRDrop>();
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<MUFRDrop> getDrops() {
		return drops;
	}

	public void setDrops(List<MUFRDrop> drops) {
		this.drops = drops;
	}
	
}

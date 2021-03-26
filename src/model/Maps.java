package model;

import java.util.LinkedList;
import java.util.List;

public class Maps {
	private List<MUFRMap> maps;
	
	public List<MUFRMap> getMaps() {
		return maps;
	}

	public void setMaps(List<MUFRMap> maps) {
		this.maps = maps;
	}

	public Maps() {
		maps = new LinkedList<MUFRMap>();
	}
}

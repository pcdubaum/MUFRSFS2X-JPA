package model;

import java.util.LinkedList;
import java.util.List;

import com.smartfoxserver.v2.mmo.Vec3D;

public class MUFRMap {
	private int id;
	private String name;
	private Vec3D size;
	private Vec3D aoi;
	private List<EntryPoint> entryPoint;
	private boolean isGame;
	private boolean isDynamic;
	private int maxUsers;
	private Tile[][] tiles;
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vec3D getSize() {
		return size;
	}

	public void setSize(Vec3D size) {
		this.size = size;
	}

	public Vec3D getAoi() {
		return aoi;
	}

	public void setAoi(Vec3D aoi) {
		this.aoi = aoi;
	}

	public List<EntryPoint> getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(List<EntryPoint> entryPoint) {
		this.entryPoint = entryPoint;
	}

	public boolean isGame() {
		return isGame;
	}

	public void setGame(boolean isGame) {
		this.isGame = isGame;
	}

	public boolean isDynamic() {
		return isDynamic;
	}

	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public int getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	public MUFRMap() {
		entryPoint = new LinkedList<EntryPoint>();
	}

}



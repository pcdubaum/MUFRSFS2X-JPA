package model;

import com.smartfoxserver.v2.mmo.Vec3D;

public class EntryPoint {
	public int origin;
	public Vec3D entryPoint;
	
	public Vec3D getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(Vec3D entryPoint) {
		this.entryPoint = entryPoint;
	}

	public EntryPoint()
	{
		
	}
}

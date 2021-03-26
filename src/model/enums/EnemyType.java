package model.enums;

public enum EnemyType {
    Normal(0),
    Elite(1),
    Golde2(2),
    Boss(3);
    
    private final int value;
    EnemyType(int value) {
    	this.value = value;
	}

    public int getValue() {
        return value; 
    }
}

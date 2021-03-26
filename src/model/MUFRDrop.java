package model;

public class MUFRDrop {
	
	private int id;
	private int superType;
    private int subtype;
    private int jewelenum;
    private int rate;
    private boolean unique;
    private boolean cumulative;
    private MUFRItem item;
    
	public MUFRDrop() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSuperType() {
		return superType;
	}

	public void setSuperType(int superType) {
		this.superType = superType;
	}

	public int getSubtype() {
		return subtype;
	}

	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}

	public int getJewelenum() {
		return jewelenum;
	}

	public void setJewelenum(int jewelenum) {
		this.jewelenum = jewelenum;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isCumulative() {
		return cumulative;
	}

	public void setCumulative(boolean cumulative) {
		this.cumulative = cumulative;
	}

	public MUFRItem getItem() {
		return item;
	}

	public void setItem(MUFRItem item) {
		this.item = item;
	}
    
    
}

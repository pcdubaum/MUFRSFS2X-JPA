package model.characters;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("DarkKnight")
public class MUFRDk extends MUFRCharacter{
	
	@Override
 	public void CalculeBaseDamage()
 	{
 		minDamage = strenght * 6;
 		maxDamage = strenght * 4;
 	}

	public MUFRDk() {
		super();
		// TODO Auto-generated constructor stub
	}

}

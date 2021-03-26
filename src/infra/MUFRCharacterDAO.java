package infra;

import java.util.List;

import javax.persistence.EntityManager;

import model.MUFRUser;
import model.characters.MUFRCharacter;

public class MUFRCharacterDAO extends DAO<model.characters.MUFRCharacter>{

	public MUFRCharacterDAO() {
		super();
	}

	public MUFRCharacterDAO(Class<MUFRCharacter> _class, EntityManager _em) {
		super(MUFRCharacter.class, _em);
	}
	
	public List<MUFRCharacter> getByOwner(Long fk)
	{
		List<MUFRCharacter> allChars = 
				this.getEm().createNamedQuery("char.getByOwner", MUFRCharacter.class)
				.setParameter("owner", fk)
				.setMaxResults(7)
				.getResultList();
		
		return allChars;
	}
	
	public MUFRCharacter getByName(String name)
	{	
		List<MUFRCharacter> chars = 
				this.getEm().createNamedQuery("char.getByName", MUFRCharacter.class)
				.setParameter("name", name)
				.getResultList();
		
		if(chars.size() == 0)
			return null;
		else
			return chars.get(0);
	}
}

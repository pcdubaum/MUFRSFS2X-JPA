package infra;

import java.util.List;

import javax.persistence.EntityManager;

import model.MUFRItem;
import model.characters.MUFRCharacter;

public class MUFRItemDAO extends DAO<MUFRItem>{
	
	public MUFRItemDAO() {
		super();
	}

	public MUFRItemDAO(Class<MUFRItem> _class, EntityManager _em) {
		super(MUFRItem.class, _em);
	}
}

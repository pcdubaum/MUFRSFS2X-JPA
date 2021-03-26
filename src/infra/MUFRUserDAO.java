package infra;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import model.MUFRUser;

public class MUFRUserDAO extends DAO<MUFRUser>{
	
	public MUFRUserDAO() {
		super();
	}
	
	public MUFRUserDAO(Class<MUFRUser> _class, EntityManager _em) {
		super(MUFRUser.class, _em);
	}
	
	public MUFRUser getByName(String userName)
	{
		MUFRUser user = null;
		
		user = this.getEm().createNamedQuery("user.getByName", MUFRUser.class)
				.setParameter("name", userName)
				.getSingleResult();
		
		return user;
	}
	
}

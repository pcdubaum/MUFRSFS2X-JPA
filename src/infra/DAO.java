package infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.IDAO;

public class DAO<E extends IDAO> {
	
	private final EntityManager em;
	private Class<E> thisClass;
	
	public EntityManager getEm() {
		return em;
	}

	public DAO()
	{
		this.em = null;
	}
	
	public DAO (Class<E> _class, EntityManager _em)
	{
		thisClass = _class;
		em = _em;
	}
	
	public DAO<E> openTransaction()
	{
		em.getTransaction().begin();
		return this;
	}
	
	public DAO<E> closeTransaction()
	{
		em.getTransaction().commit();
		return this;
	}
	
	public DAO<E> persist(E idao)
	{
		em.persist(idao);
		return this;
	}
	
	public DAO<E> merge(E idao)
	{
		em.merge(idao);
		return this;
	}
	
	public List<E> getAll(int maxResults)
	{
		if(thisClass == null)
			throw new UnsupportedOperationException("thisClass is null.");
		
		String jpql = "select e from " + thisClass.getName() + " e";
		TypedQuery<E> query = em.createQuery(jpql, thisClass);
		query.setMaxResults(maxResults);
		
		return query.getResultList();
	}
	
	public void close() 
	{
		em.close();
	}

}

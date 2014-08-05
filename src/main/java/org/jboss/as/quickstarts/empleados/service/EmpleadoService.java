package org.jboss.as.quickstarts.empleados.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.as.quickstarts.empleados.model.Empleado;

@Stateless
public class EmpleadoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Empleado guardar(Empleado empleado) {
		return entityManager.merge(empleado);
	}

	public List<Empleado> todosLosEmpleados(Integer nroPagina, Integer tamPagina) {
		TypedQuery<Empleado> query = entityManager.createNamedQuery("TodosLosEmpleados", Empleado.class);
		query.setFirstResult(nroPagina);
		query.setMaxResults(tamPagina);
		return query.getResultList();
	}

	public Empleado buscarPorNroLegajo(Long nroLegajo) {
		Query query = entityManager.createNamedQuery("EmpleadosByNroLegajo");
		query.setParameter("nroLegajo", nroLegajo);
		return (Empleado) query.getSingleResult();
	}

	public void eliminar(Long id) {
		Empleado empleado = entityManager.find(Empleado.class, id);
		entityManager.remove(empleado);
	}
}
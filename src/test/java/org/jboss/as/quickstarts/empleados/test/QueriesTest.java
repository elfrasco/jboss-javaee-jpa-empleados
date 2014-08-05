/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.empleados.test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.empleados.model.Empleado;
import org.jboss.as.quickstarts.empleados.model.EmpleadoMockBuilder;
import org.jboss.as.quickstarts.empleados.model.Proyecto;
import org.jboss.as.quickstarts.empleados.model.ProyectoMockBuilder;
import org.jboss.as.quickstarts.empleados.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class QueriesTest {
	
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage("org.jboss.as.quickstarts.empleados.model")
        		.addClasses(Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    private EntityManager entityManager;
    
    @Inject
    private Logger log;

    @Inject
    private UserTransaction utx;
    
    private Long proyectoId;
	private Empleado adrianParedes = EmpleadoMockBuilder.crearAdrianParedes();
	private Empleado damianLezcano = EmpleadoMockBuilder.crearDamianLezcano();
	private Empleado pabloFelitti = EmpleadoMockBuilder.crearPabloFelitti();
    
    @Before
    public void init() throws Exception {
    	utx.begin();
    	adrianParedes.setId(null);
    	adrianParedes = entityManager.merge(adrianParedes);
    	damianLezcano.setId(null);
    	damianLezcano = entityManager.merge(damianLezcano);
    	pabloFelitti.setId(null);
    	pabloFelitti = entityManager.merge(pabloFelitti);    	
    	Proyecto proyecto = ProyectoMockBuilder.crearProyectoBanelco(adrianParedes,
    			Arrays.asList(adrianParedes, damianLezcano, pabloFelitti));
    	proyectoId = entityManager.merge(proyecto).getId();
    	utx.commit();
    }
    
    @After
    public void destroy() throws Exception {
    	utx.begin();
    	Proyecto proyecto = entityManager.find(Proyecto.class, proyectoId);
    	entityManager.remove(proyecto);
    	removeDetachedEntity(adrianParedes, adrianParedes.getId());
    	removeDetachedEntity(damianLezcano, damianLezcano.getId());
    	removeDetachedEntity(pabloFelitti, pabloFelitti.getId());
    	utx.commit();
    }
    
	@Test
	public void testDynamicQuery() {
		
		TypedQuery<Proyecto> query = entityManager.createQuery(
				"SELECT p FROM Proyecto p WHERE p.nombreCliente = :cliente",
				Proyecto.class);		
		query.setParameter("cliente", "Banelco");		
		Proyecto proyecto = query.getSingleResult();
		
		Assert.assertNotNull(proyecto);
		Assert.assertEquals("Banelco", proyecto.getNombreCliente());
		
		log.info("============= Nombre Proyecto: " + proyecto.getNombre());
		log.info("============= Nombre Cliente: " + proyecto.getNombreCliente());
	}
	
	@Test
	public void testNamedQuery() {
		
		TypedQuery<Empleado> query = entityManager.createNamedQuery("EmpleadosByApellido", Empleado.class);		
		query.setParameter("apellido", "Paredes");		
		List<Empleado> empleados = query.getResultList();
		
		Assert.assertNotNull(empleados);
		Assert.assertTrue(empleados.size() > 0);
		
		Empleado empleado = empleados.get(0);
		Assert.assertEquals("Paredes", empleado.getApellido());
		log.info("============== Nombre: " + empleado.getNombre());
		log.info("============== Apellido: " + empleado.getApellido());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testNativeQuery() {
		
		Query query = entityManager.createNativeQuery(
				"SELECT * FROM EMPLEADOS WHERE APELLIDO = :apellido",
				Empleado.class);
		query.setParameter("apellido", "Paredes");
		List<Empleado> empleados = query.getResultList();
		
		Assert.assertNotNull(empleados);
		Assert.assertTrue(empleados.size() > 0);
		
		Empleado empleado = empleados.get(0);
		Assert.assertEquals("Paredes", empleado.getApellido());
		log.info("================ Nombre: " + empleado.getNombre());
		log.info("================ Apellido: " + empleado.getApellido());		
	}
    
	@Test
	public void testCriteriaQuery() {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Empleado> criteria = cb.createQuery(Empleado.class);
		Root<Empleado> root = criteria.from(Empleado.class);
		Predicate p1 = cb.equal(root.get("nombre"), "Adrian");
		Predicate p2 = cb.equal(root.get("apellido"), "Paredes");		
		criteria.select(root).where(cb.and(p1, p2));
		
		TypedQuery<Empleado> query = entityManager.createQuery(criteria);
		Empleado empleado = query.getSingleResult();
		
		Assert.assertNotNull(empleado);
		Assert.assertEquals("Adrian", empleado.getNombre());
		Assert.assertEquals("Paredes", empleado.getApellido());
	}	
	
    private void removeDetachedEntity(Object object, Long id) {
    	Object obj = entityManager.find(object.getClass(), id);
    	entityManager.remove(obj);
    }

}

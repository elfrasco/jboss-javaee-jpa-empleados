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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.empleados.model.Empleado;
import org.jboss.as.quickstarts.empleados.model.EmpleadoMockBuilder;
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
public class ManagedEntitiesTest {
	
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
    private UserTransaction utx;    

	private Empleado adrianParedes = EmpleadoMockBuilder.crearAdrianParedes();
	
	@Before
	public void init() throws Exception {
    	utx.begin();
    	adrianParedes = entityManager.merge(adrianParedes);
    	utx.commit();
	}
	
	@After
	public void destroy() throws Exception {
    	utx.begin();
    	removeDetachedEntity(adrianParedes, adrianParedes.getId());
    	utx.commit();
	}

    @Test
    public void testChangeNameWithoutTransaction() throws Exception {
    	
    	Empleado empleado = findAdrian();
    	
    	Assert.assertEquals("Adrian", empleado.getNombre());
    	empleado.setNombre("Adriel");
    	
    	empleado = findAdrian();
    	Assert.assertEquals("Adrian", empleado.getNombre());
    }

    @Test
    public void testChangeNameWithTransaction() throws Exception {

    	utx.begin();
    	Empleado empleado = findAdrian();

    	Assert.assertEquals("Adrian", empleado.getNombre());
    	empleado.setNombre("Adriel");
    	utx.commit();    	
    	
    	empleado = findAdrian();
    	Assert.assertEquals("Adriel", empleado.getNombre());
    }

    @Test
    public void testChangeNameDetachEntity() throws Exception {

    	utx.begin();
    	Empleado empleado = findAdrian();

    	Assert.assertEquals("Adrian", empleado.getNombre());
    	
    	entityManager.detach(empleado);
    	empleado.setNombre("Adriel");
    	
    	utx.commit();    	
    	
    	empleado = findAdrian();
    	Assert.assertEquals("Adrian", empleado.getNombre());
    }

    @Test
    public void testChangeNameDetachedAndManagedEntity() throws Exception {

    	utx.begin();
    	Empleado empleadoManaged = findAdrian();
    	Assert.assertEquals("Adrian", empleadoManaged.getNombre());
    	empleadoManaged.setNombre("Adriel");
    	
    	Empleado empleadoDetached = findAdrian();
    	Assert.assertEquals("Adriel", empleadoDetached.getNombre());
    	
    	entityManager.detach(empleadoDetached);

    	empleadoManaged.setNombre("Aurelio");
    	empleadoDetached.setNombre("Marcos");
    	utx.commit();
    	
    	Empleado empleado = findAdrian();
    	Assert.assertEquals("Adriel", empleado.getNombre());
    }
    
    private Empleado findAdrian() {
    	TypedQuery<Empleado> query = entityManager.createNamedQuery("EmpleadosByApellido", Empleado.class);
    	query.setParameter("apellido", "Paredes");
    	Empleado empleado = query.getSingleResult();
    	return empleado;
    }
    
    private void removeDetachedEntity(Object object, Long id) {
    	Object obj = entityManager.find(object.getClass(), id);
    	entityManager.remove(obj);
    }

}

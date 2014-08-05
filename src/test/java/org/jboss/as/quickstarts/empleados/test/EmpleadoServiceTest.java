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

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.empleados.model.Empleado;
import org.jboss.as.quickstarts.empleados.model.EmpleadoMockBuilder;
import org.jboss.as.quickstarts.empleados.service.EmpleadoService;
import org.jboss.as.quickstarts.empleados.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EmpleadoServiceTest {
	
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage("org.jboss.as.quickstarts.empleados.model")
                .addClasses(EmpleadoService.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    private EmpleadoService empleadoService;

    @Inject
    private Logger log;
    
	private Empleado adrianParedes = EmpleadoMockBuilder.crearAdrianParedes();
	private Empleado damianLezcano = EmpleadoMockBuilder.crearDamianLezcano();
	private Empleado pabloFelitti = EmpleadoMockBuilder.crearPabloFelitti();    

    @Test
    public void testGuardar() {
    	
    	Empleado empleado = EmpleadoMockBuilder.crearAdrianParedes();
    	
    	empleado = empleadoService.guardar(empleado);
    	
    	Assert.assertNotNull(empleado.getId());
    	log.info("========== " + empleado.getNombre() + " was persisted with id " + empleado.getId());
        
    	// Se limpia lo que guardo el test para que pueda correr otro
    	empleadoService.eliminar(empleado.getId());
    	
    }
    
    @Test
    public void testTodosLosEmpleados() {
    	
    	adrianParedes = empleadoService.guardar(adrianParedes);
    	damianLezcano = empleadoService.guardar(damianLezcano);
    	pabloFelitti = empleadoService.guardar(pabloFelitti);
    	
    	List<Empleado> empleados = empleadoService.todosLosEmpleados(0, 10);
    	log.info("=================== Cantidad de empleados = " + empleados.size());

    	Assert.assertEquals(3, empleados.size());
    	for (Empleado empleado : empleados) {
    		Empleado expected = getExpectedEmpleado(empleado);
    		Assert.assertNotNull(expected);
    		EmpleadoMockBuilder.assertEmpleado(expected, empleado);
    	}    	
    	
    	// Se limpia lo que guardo el test para que pueda correr otro
    	empleadoService.eliminar(adrianParedes.getId());
    	empleadoService.eliminar(damianLezcano.getId());
    	empleadoService.eliminar(pabloFelitti.getId());
    	
    }
    
    @Test
    public void testBuscarPorNroLegajo() {
    	
    	Empleado empleado1 = empleadoService.guardar(EmpleadoMockBuilder.crearAdrianParedes());
    	Empleado empleado2 = empleadoService.guardar(EmpleadoMockBuilder.crearDamianLezcano());
    	Empleado empleado3 = empleadoService.guardar(EmpleadoMockBuilder.crearPabloFelitti());
    	
    	Empleado actual = empleadoService.buscarPorNroLegajo(empleado2.getNroLegajo());
    	EmpleadoMockBuilder.assertEmpleado(empleado2, actual);
    	
    	// Se limpia lo que guardo el test para que pueda correr otro
    	empleadoService.eliminar(empleado1.getId());
    	empleadoService.eliminar(empleado2.getId());
    	empleadoService.eliminar(empleado3.getId());
    }
    
    private Empleado getExpectedEmpleado(Empleado actual) {
    	if (adrianParedes.getNombre().equals(actual.getNombre())) {
    		return adrianParedes;
    	}
    	if (pabloFelitti.getNombre().equals(actual.getNombre())) {
    		return pabloFelitti;
    	}
    	if (damianLezcano.getNombre().equals(actual.getNombre())) {
    		return damianLezcano;
    	}
    	return null;
    }    

}

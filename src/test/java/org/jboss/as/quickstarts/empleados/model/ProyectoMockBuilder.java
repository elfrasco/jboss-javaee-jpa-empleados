package org.jboss.as.quickstarts.empleados.model;

import java.util.List;

import org.jboss.as.quickstarts.empleados.model.Empleado;
import org.jboss.as.quickstarts.empleados.model.Proyecto;
import org.jboss.as.quickstarts.empleados.model.TipoProyecto;

public class ProyectoMockBuilder {

	public static Proyecto crearProyectoBanelco(Empleado lider, List<Empleado> empleados) {
		
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Migracion Aplicaciones");
		proyecto.setNombreCliente("Banelco");
		proyecto.setDuracionEnMeses(12);
		proyecto.setTipo(TipoProyecto.DESARROLLO);
		
		proyecto.setLider(lider);
		
		proyecto.setEmpleados(empleados);
		
		return proyecto;
	}

}

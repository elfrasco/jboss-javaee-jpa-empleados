package org.jboss.as.quickstarts.empleados.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.jboss.as.quickstarts.empleados.model.DocumentoIdentidad;
import org.jboss.as.quickstarts.empleados.model.Empleado;
import org.jboss.as.quickstarts.empleados.model.ReciboDeSueldo;
import org.junit.Assert;

public class EmpleadoMockBuilder {

	public static Empleado crearAdrianParedes() {
		Empleado empleado = new Empleado();
		empleado.setNombre("Adrian");
		empleado.setSegundoNombre("Marcelo");
		empleado.setApellido("Paredes");
		empleado.setNroLegajo(107L);
		empleado.setFechaNacimiento(new GregorianCalendar(1982, Calendar.JUNE, 3).getTime());
		
		empleado.setEmails(Arrays.asList("adrianp@epidataconsulting.com", "adrianparedes82@gmail.com"));
		
		DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
		documentoIdentidad.setTipo("DNI");
		documentoIdentidad.setNroDocumento("29543783");
		empleado.setDocumentoIdentidad(documentoIdentidad);
		
		ReciboDeSueldo reciboOctubre = new ReciboDeSueldo();
		reciboOctubre.setSueldo(250000.0);
		reciboOctubre.setFechaPago(new GregorianCalendar(2010, Calendar.OCTOBER, 30).getTime());

		ReciboDeSueldo reciboNoviembre = new ReciboDeSueldo();
		reciboNoviembre.setSueldo(250000.0);
		reciboNoviembre.setFechaPago(new GregorianCalendar(2010, Calendar.NOVEMBER, 30).getTime());

		ReciboDeSueldo reciboDiciembre = new ReciboDeSueldo();
		reciboDiciembre.setSueldo(250000.0);
		reciboDiciembre.setFechaPago(new GregorianCalendar(2010, Calendar.DECEMBER, 30).getTime());
		
		List<ReciboDeSueldo> recibosDeSueldo = new ArrayList<ReciboDeSueldo>();
		recibosDeSueldo.add(reciboOctubre);
		recibosDeSueldo.add(reciboNoviembre);
		recibosDeSueldo.add(reciboDiciembre);
		empleado.setRecibosDeSueldo(recibosDeSueldo);
		
		return empleado;
	}

	public static Empleado crearDamianLezcano() {
		Empleado empleado = new Empleado();
		empleado.setNombre("Damian");
		empleado.setApellido("Lezcano");
		empleado.setNroLegajo(110L);
		empleado.setFechaNacimiento(new GregorianCalendar(1984, Calendar.JUNE, 3).getTime());

		empleado.setEmails(Arrays.asList("damianl@epidataconsulting.com"));
		
		DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
		documentoIdentidad.setTipo("DNI");
		documentoIdentidad.setNroDocumento("30985111");
		empleado.setDocumentoIdentidad(documentoIdentidad);
		
		return empleado;
	}

	public static Empleado crearPabloFelitti() {
		Empleado empleado = new Empleado();
		empleado.setNombre("Pablo");
		empleado.setApellido("Felitti");
		empleado.setNroLegajo(115L);
		empleado.setFechaNacimiento(new GregorianCalendar(1986, Calendar.JUNE, 3).getTime());
		
		empleado.setEmails(Arrays.asList("pablof@epidataconsulting.com"));
		
		DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
		documentoIdentidad.setTipo("DNI");
		documentoIdentidad.setNroDocumento("32555111");
		empleado.setDocumentoIdentidad(documentoIdentidad);
		
		return empleado;
	}
	
	public static void assertEmpleado(Empleado expected, Empleado actual) {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getNombre(), actual.getNombre());
		Assert.assertEquals(expected.getSegundoNombre(), actual.getSegundoNombre());
		Assert.assertEquals(expected.getApellido(), actual.getApellido());
		Assert.assertEquals(expected.getFechaNacimiento(), actual.getFechaNacimiento());
		Assert.assertEquals(expected.getNroLegajo(), actual.getNroLegajo());
		Assert.assertEquals(expected.getDocumentoIdentidad().getTipo(), actual.getDocumentoIdentidad().getTipo());
		Assert.assertEquals(expected.getDocumentoIdentidad().getNroDocumento(), actual.getDocumentoIdentidad().getNroDocumento());
	}
}

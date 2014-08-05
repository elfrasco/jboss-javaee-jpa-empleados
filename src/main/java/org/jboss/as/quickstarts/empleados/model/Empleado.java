package org.jboss.as.quickstarts.empleados.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EMPLEADOS")
@NamedQueries({
	@NamedQuery(
		name = "EmpleadosByApellido",
		query = "FROM Empleado WHERE apellido = :apellido"
	),
	@NamedQuery(
		name = "EmpleadosByNroLegajo",
		query = "FROM Empleado WHERE nroLegajo = :nroLegajo"
	),
	@NamedQuery(
		name = "TodosLosEmpleados",
		query = "FROM Empleado"
	)
})
public class Empleado implements Serializable {

	private static final long serialVersionUID = -1267016219634275270L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMPLEADO_ID")
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false)
	private String nombre;
	
	@Column(name = "SEGUNDO_NOMBRE")
	private String segundoNombre;
	
	@Column(name = "APELLIDO", nullable = false)
	private String apellido;
	
	@Column(name = "FECHA_NAC")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name = "NRO_LEGAJO", nullable = false, unique = true)
	private Long nroLegajo;
	
	@Embedded
	private DocumentoIdentidad documentoIdentidad;
	
	@OneToMany(targetEntity = ReciboDeSueldo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "EMPLEADO_ID", referencedColumnName = "EMPLEADO_ID")
	private List<ReciboDeSueldo> recibosDeSueldo;
	
	@ElementCollection
	@CollectionTable(name = "EMPLEADOS_MAILS")
	@Column(name = "MAIL")
	private Collection<String> emails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getNroLegajo() {
		return nroLegajo;
	}

	public void setNroLegajo(Long nroLegajo) {
		this.nroLegajo = nroLegajo;
	}

	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public List<ReciboDeSueldo> getRecibosDeSueldo() {
		return recibosDeSueldo;
	}

	public void setRecibosDeSueldo(List<ReciboDeSueldo> recibosDeSueldo) {
		this.recibosDeSueldo = recibosDeSueldo;
	}
	
	public Collection<String> getEmails() {
		return emails;
	}
	
	public void setEmails(Collection<String> emails) {
		this.emails = emails;
	}
}

package org.jboss.as.quickstarts.empleados.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PROYECTOS")
@NamedQuery(
		name = "ProyectoByNombre",
		query = "FROM Proyecto WHERE nombre = :nombre"
)
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1187868933384394089L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROYECTO_ID")
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false)
	private String nombre;
	
	@Column(name = "DURACION")
	private Integer duracionEnMeses;
	
	@Column(name = "NOMBRE_CLIENTE", nullable = false)
	private String nombreCliente;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="TIPO", nullable = false)
	private TipoProyecto tipo;
	
	@ManyToMany
	@JoinTable(name = "PROYECTOS_EMPLEADOS",
			joinColumns=@JoinColumn(name="PROYECTO_ID", referencedColumnName="PROYECTO_ID"),
			inverseJoinColumns=@JoinColumn(name="EMPLEADO_ID", referencedColumnName="EMPLEADO_ID"))
	private List<Empleado> empleados;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "EMPLEADO_ID", referencedColumnName = "EMPLEADO_ID")
	private Empleado lider;
	
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

	public Integer getDuracionEnMeses() {
		return duracionEnMeses;
	}

	public void setDuracionEnMeses(Integer duracionEnMeses) {
		this.duracionEnMeses = duracionEnMeses;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public TipoProyecto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProyecto tipo) {
		this.tipo = tipo;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public Empleado getLider() {
		return lider;
	}

	public void setLider(Empleado lider) {
		this.lider = lider;
	}
	
}

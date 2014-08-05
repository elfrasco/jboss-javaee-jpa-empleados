package org.jboss.as.quickstarts.empleados.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RECIBOS")
public class ReciboDeSueldo implements Serializable {

	private static final long serialVersionUID = 7390062832868633195L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECIBO_ID")
	private Long id;
	
	@Column(name = "SUELDO", nullable = false)
	private Double sueldo;
	
	@Column(name = "FECHA_PAGO", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

}

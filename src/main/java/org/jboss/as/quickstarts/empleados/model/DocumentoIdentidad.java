package org.jboss.as.quickstarts.empleados.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentoIdentidad implements Serializable {

	private static final long serialVersionUID = -1542271116980245660L;
	
	@Column(name = "TIPO_DOCUMENTO", nullable = false)
	private String tipo;
	
	@Column(name = "NRO_DOCUMENTO", nullable = false)
	private String nroDocumento;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

}


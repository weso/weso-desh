package es.uniovi.weso.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @version 0.1
 * @since 2011-11-08
 */
@Entity
public class ValueMapping implements Serializable{

	private static final long serialVersionUID = -160363715757L;
	@Id @GeneratedValue
	private Long id;
	@Version
	private Long version;
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdate;
	private String staticKey;
	private String rdfValue;
	@ManyToOne
	private StaticMapping mapping;
	
	public ValueMapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ValueMapping(Long id, String staticKey, String rdfValue) {
		super();
		this.id = id;
		this.staticKey = staticKey;
		this.rdfValue = rdfValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((rdfValue == null) ? 0 : rdfValue.hashCode());
		result = prime * result
				+ ((staticKey == null) ? 0 : staticKey.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueMapping other = (ValueMapping) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rdfValue == null) {
			if (other.rdfValue != null)
				return false;
		} else if (!rdfValue.equals(other.rdfValue))
			return false;
		if (staticKey == null) {
			if (other.staticKey != null)
				return false;
		} else if (!staticKey.equals(other.staticKey))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStaticKey() {
		return staticKey;
	}
	public void setStaticKey(String staticKey) {
		this.staticKey = staticKey;
	}
	public String getRdfValue() {
		return rdfValue;
	}
	public void setRdfValue(String rdfValue) {
		this.rdfValue = rdfValue;
	}
	public void setMapping(StaticMapping mapping) {
		this.mapping = mapping;
	}
	public StaticMapping getMapping() {
		return mapping;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Long getVersion() {
		return version;
	}
	public void setCrdate(Date crdate) {
		this.crdate = crdate;
	}
	public Date getCrdate() {
		return crdate;
	}
	
	
}

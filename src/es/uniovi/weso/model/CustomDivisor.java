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
public class CustomDivisor implements Serializable{

	private static final long serialVersionUID = -16036331575297517L;
	@Id @GeneratedValue
	private Long id;
	@Version
	private Long version;
	private String separatorField;
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdate;
	private String beforeField;
	@ManyToOne 
	private Rule rule;

	
	



	


	public CustomDivisor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public void setBeforeField(String beforeField) {
		this.beforeField = beforeField;
	}

	public String getBeforeField() {
		return beforeField;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Rule getRule() {
		return rule;
	}

	public void setSeparatorField(String separatorField) {
		this.separatorField = separatorField;
	}

	public String getSeparatorField() {
		return separatorField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beforeField == null) ? 0 : beforeField.hashCode());
		result = prime * result + ((crdate == null) ? 0 : crdate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result
				+ ((separatorField == null) ? 0 : separatorField.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		CustomDivisor other = (CustomDivisor) obj;
		if (beforeField == null) {
			if (other.beforeField != null)
				return false;
		} else if (!beforeField.equals(other.beforeField))
			return false;
		if (crdate == null) {
			if (other.crdate != null)
				return false;
		} else if (!crdate.equals(other.crdate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (separatorField == null) {
			if (other.separatorField != null)
				return false;
		} else if (!separatorField.equals(other.separatorField))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public CustomDivisor(Long id, Long version, String separatorField,
			Date crdate, String beforeField, Rule rule) {
		super();
		this.id = id;
		this.version = version;
		this.separatorField = separatorField;
		this.crdate = crdate;
		this.beforeField = beforeField;
		this.rule = rule;
	}
	

}

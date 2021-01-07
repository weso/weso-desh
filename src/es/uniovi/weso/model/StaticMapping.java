package es.uniovi.weso.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @version 0.1
 * @since 2011-11-08
 */
@Entity
public class StaticMapping implements Serializable{

	private static final long serialVersionUID = -160363783157L;
	@Id @GeneratedValue
	private Long id;
	@Version
	private Long version;
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdate;
	private String parameterName;
	@OneToMany (mappedBy="mapping")
	private Set<ValueMapping> values;
	@ManyToOne
	private Rule rule;
	
	
	
	public StaticMapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<ValueMapping> getValues() {
		return values;
	}
	public void setValues(Set<ValueMapping> values) {
		this.values = values;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	public Rule getRule() {
		return rule;
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
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterName() {
		return parameterName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crdate == null) ? 0 : crdate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((parameterName == null) ? 0 : parameterName.hashCode());
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		StaticMapping other = (StaticMapping) obj;
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
		if (parameterName == null) {
			if (other.parameterName != null)
				return false;
		} else if (!parameterName.equals(other.parameterName))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public StaticMapping(Long id, Long version, Date crdate,
			String parameterName, Set<ValueMapping> values, Rule rule) {
		super();
		this.id = id;
		this.version = version;
		this.crdate = crdate;
		this.parameterName = parameterName;
		this.values = values;
		this.rule = rule;
	}
	
	
	
}

package es.uniovi.weso.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Rule implements Serializable{

	private static final long serialVersionUID = -1637831575297517L;
	@Id @GeneratedValue
	private Long id;
	@Version
	private Long version;
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdate;
	private String keyRule;
	private String uriFrom;
	private String uriTo;
	private String sparql;
	private String endpoint;
	@OneToMany (mappedBy="rule")
	private List<CustomDivisor> divisor;
	@OneToMany (mappedBy="rule")
	private Set<StaticMapping> staticMappings;
	
	
	public Rule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSparql() {
		return sparql;
	}
	public void setSparql(String sparql) {
		this.sparql = sparql;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public List<CustomDivisor> getDivisor() {
		return divisor;
	}
	public void setDivisor(List<CustomDivisor> divisor) {
		this.divisor = divisor;
	}
	public Set<StaticMapping> getStaticMappings() {
		return staticMappings;
	}
	public void setStaticMappings(Set<StaticMapping> staticMappings) {
		this.staticMappings = staticMappings;
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
	public void setKeyRule(String keyRule) {
		this.keyRule = keyRule;
	}
	public String getKeyRule() {
		return keyRule;
	}



	public void setUriFrom(String uriFrom) {
		this.uriFrom = uriFrom;
	}

	public String getUriFrom() {
		return uriFrom;
	}

	public void setUriTo(String uriTo) {
		this.uriTo = uriTo;
	}

	public String getUriTo() {
		return uriTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crdate == null) ? 0 : crdate.hashCode());
		result = prime * result + ((divisor == null) ? 0 : divisor.hashCode());
		result = prime * result
				+ ((endpoint == null) ? 0 : endpoint.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keyRule == null) ? 0 : keyRule.hashCode());
		result = prime * result + ((sparql == null) ? 0 : sparql.hashCode());
		result = prime * result
				+ ((staticMappings == null) ? 0 : staticMappings.hashCode());
		result = prime * result + ((uriFrom == null) ? 0 : uriFrom.hashCode());
		result = prime * result + ((uriTo == null) ? 0 : uriTo.hashCode());
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
		Rule other = (Rule) obj;
		if (crdate == null) {
			if (other.crdate != null)
				return false;
		} else if (!crdate.equals(other.crdate))
			return false;
		if (divisor == null) {
			if (other.divisor != null)
				return false;
		} else if (!divisor.equals(other.divisor))
			return false;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keyRule == null) {
			if (other.keyRule != null)
				return false;
		} else if (!keyRule.equals(other.keyRule))
			return false;
		if (sparql == null) {
			if (other.sparql != null)
				return false;
		} else if (!sparql.equals(other.sparql))
			return false;
		if (staticMappings == null) {
			if (other.staticMappings != null)
				return false;
		} else if (!staticMappings.equals(other.staticMappings))
			return false;
		if (uriFrom == null) {
			if (other.uriFrom != null)
				return false;
		} else if (!uriFrom.equals(other.uriFrom))
			return false;
		if (uriTo == null) {
			if (other.uriTo != null)
				return false;
		} else if (!uriTo.equals(other.uriTo))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public Rule(Long id, Long version, Date crdate, String keyRule,
			String uriFrom, String uriTo, String sparql, String endpoint,
			List<CustomDivisor> divisor, Set<StaticMapping> staticMappings) {
		super();
		this.id = id;
		this.version = version;
		this.crdate = crdate;
		this.keyRule = keyRule;
		this.uriFrom = uriFrom;
		this.uriTo = uriTo;
		this.sparql = sparql;
		this.endpoint = endpoint;
		this.divisor = divisor;
		this.staticMappings = staticMappings;
	}
	
	
	
	
}

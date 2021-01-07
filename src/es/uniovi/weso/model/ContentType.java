package es.uniovi.weso.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Francisco Cifuentes <francisco.cifuentes@weso.es>
 * @version 0.1
 * @since 2011-11-08
 */
@Entity
public class ContentType implements Serializable{
	
	private static final long serialVersionUID = -1603637831575297517L;
	@Id @GeneratedValue
	private Long id;
	@Version
	private Long version;
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdate;
	private String label;
	private String mimetype;
	private String extension;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crdate == null) ? 0 : crdate.hashCode());
		result = prime * result
				+ ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((mimetype == null) ? 0 : mimetype.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	public ContentType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ContentType(Long id, String label, String mimetype, String extension) {
		super();
		this.id = id;
		this.label = label;
		this.mimetype = mimetype;
		this.extension = extension;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContentType other = (ContentType) obj;
		if (crdate == null) {
			if (other.crdate != null)
				return false;
		} else if (!crdate.equals(other.crdate))
			return false;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (mimetype == null) {
			if (other.mimetype != null)
				return false;
		} else if (!mimetype.equals(other.mimetype))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
	
}

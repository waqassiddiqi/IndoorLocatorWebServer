package org.redpin.server.core;

import javax.xml.bind.annotation.XmlRootElement;

import org.redpin.server.db.IEntity;

/**
 * @see org.redpin.base.core.Map
 * @author Pascal Brogle (broglep@student.ethz.ch)
 * 
 */
@XmlRootElement
public class Map extends org.redpin.base.core.Map implements IEntity<Integer> {

	private Integer id;

	/**
	 * @return the database id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
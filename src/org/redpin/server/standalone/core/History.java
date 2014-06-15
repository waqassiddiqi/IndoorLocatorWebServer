package org.redpin.server.standalone.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.redpin.server.standalone.db.IEntity;


/**
 * @see org.redpin.base.core.History
 * @author Waqas Hussain Siddiqui (waqas.siddiqi@hotmail.com)
 * 
 */
@XmlType(name="History2")
@XmlRootElement
public class History extends org.redpin.base.core.History implements IEntity<Integer> {

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

package org.redpin.server.standalone.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.redpin.server.standalone.db.IEntity;

/**
 * @see org.redpin.base.core.User
 * @author Waqas Hussain Siddiqui (waqas.siddiqi@hotmail.com)
 * 
 */
@XmlType(name="User2")
@XmlRootElement
public class User extends org.redpin.base.core.User implements IEntity<Integer> {
	
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
package org.redpin.server.standalone.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.redpin.server.standalone.db.IEntity;


/**
 * @see org.redpin.base.core.Task
 * @author Waqas Hussain Siddiqui (waqas.siddiqi@hotmail.com)
 * 
 */
@XmlType(name="Task2")
@XmlRootElement
public class Task extends org.redpin.base.core.Task implements IEntity<Integer> {

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

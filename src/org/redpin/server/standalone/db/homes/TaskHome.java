package org.redpin.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;

import org.redpin.server.standalone.core.Task;

/**
 * @see EntityHome
 * @author Waqas Hussain Siddiqui (waqas.siddiqi@hotmail.com)
 *
 */

public class TaskHome extends EntityHome<Task> {
	private static final String[] TableCols = {"jobStatus", "priority", "transportType", "comment"};
	private static final String TableName = "task"; 
	private static final String TableIdCol = "taskId";
	
	public TaskHome() {
		super();
	}
	
	/**
	 * @see EntityHome#getTableIdCol()
	 */
	@Override
	protected String getTableIdCol() {
		return TableIdCol;
	}

	/**
	 * @see EntityHome#getTableCols()
	 */
	@Override
	protected String[] getTableCols() {
		return TableCols;
	}

	/**
	 * @see EntityHome#getTableName()
	 */
	@Override	
	protected String getTableName() {
		return TableName;
	}
	
	/**
	 * @see EntityHome#parseResultRow(ResultSet, int)
	 */
	@Override
	public Task parseResultRow(final ResultSet rs, int fromIndex) throws SQLException{
		Task task = new Task();
		
		try {
			
			task.setId(rs.getInt(fromIndex));
			task.setJobStatus(rs.getString(fromIndex + 1));
			task.setPriority(rs.getString(fromIndex + 2));
			task.setTransportType(rs.getString(fromIndex + 3));
			task.setComment(rs.getString(fromIndex + 4));
			
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return task;
	}
 	
	/**
	 * @see EntityHome#getSelectSQL()
	 */
	@Override
	protected String getSelectSQL() {
		return "SELECT " + getTableColNames() + " FROM " + getTableName();
	}
	
	@Override
	public int fillInStatement(PreparedStatement ps, Task t, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] { t.getJobStatus(), t.getPriority(), t.getTransportType() , 
				t.getComment() }, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}, fromIndex);
	}
}

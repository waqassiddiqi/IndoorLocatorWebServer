package org.redpin.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;

import org.redpin.server.standalone.core.User;

/**
 * @see EntityHome
 * @author Waqas Hussain Siddiqui (waqas.siddiqi@hotmail.com)
 *
 */

public class UserHome extends EntityHome<User> {
	private static final String[] TableCols = {"userName", "name", "password"};
	private static final String TableName = "user"; 
	private static final String TableIdCol = "userId";
	
	public UserHome() {
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
	public User parseResultRow(final ResultSet rs, int fromIndex) throws SQLException{
		User user = new User();
		
		try {
			user.setId(rs.getInt(fromIndex));
			user.setUserName(rs.getString(fromIndex + 1));
			user.setName(rs.getString(fromIndex + 2));
			user.setPassword(rs.getString(fromIndex + 3));
		
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return user;
	}
	
	@Override
	public int fillInStatement(PreparedStatement ps, User u, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] { 
				u.getUserName(), u.getName(), u.getPassword() }, 
			new int[]{ Types.VARCHAR, Types.VARCHAR, Types.VARCHAR }, fromIndex);
	}
}

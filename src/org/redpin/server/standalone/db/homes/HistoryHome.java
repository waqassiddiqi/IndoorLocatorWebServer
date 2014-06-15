package org.redpin.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;

import org.redpin.server.standalone.core.History;
import org.redpin.server.standalone.core.Location;
import org.redpin.server.standalone.core.User;
import org.redpin.server.standalone.db.HomeFactory;

/**
 * @see EntityHome
 * @author Waqas Hussain Siddiqui (waqas.siddiqi@hotmail.com)
 *
 */

public class HistoryHome extends EntityHome<History> {
	private static final String[] TableCols = {"userId", "locationId", "date"};
	private static final String TableName = "history"; 
	private static final String TableIdCol = "historyId";
	
	public HistoryHome() {
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
	public History parseResultRow(final ResultSet rs, int fromIndex) throws SQLException{
		History history = new History();
		
		try {
			history.setId(rs.getInt(fromIndex));
			history.setDate(rs.getDate(fromIndex + 3));
			
			User user = HomeFactory.getUserHome().parseResultRow(rs, fromIndex + 4);
			history.setUser(user);
			
			Location location = HomeFactory.getLocationHome().parseResultRow(rs, fromIndex + 7);
			history.setLocation(location);
			
		
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return history;
	}
	
	public List<History> getByAll() {
		return getByUserId(-1);
	}
	
	public List<History> getByUserId(int id) {
		String constraint = "";
		if (id != -1) constraint += " user.userId = " + id;
		
		return get(constraint);
	}
 	
	/**
	 * @see EntityHome#getSelectSQL()
	 */
	@Override
	protected String getSelectSQL() {
		return "SELECT " + getTableColNames() + ", " + HomeFactory.getUserHome().getTableColNames() + ", "
				+ HomeFactory.getLocationHome().getTableColNames() + ", "
				+ HomeFactory.getMapHome().getTableColNames() + " FROM " + getTableName() 
				+ " INNER JOIN user ON history.userId = user.userId "
				+ " INNER JOIN location ON location.locationId = history.locationId"
				+ " INNER JOIN map ON location.mapId = map.mapId";
	}
	
	@Override
	public int fillInStatement(PreparedStatement ps, History h, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] { ((Location) h.getLocation()).getId() , 
				((User) h.getUser()).getId(), h.getDate() }, new int[]{Types.VARCHAR, Types.VARCHAR}, fromIndex);
	}
}

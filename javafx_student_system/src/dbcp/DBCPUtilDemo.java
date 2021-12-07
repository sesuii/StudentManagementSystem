package dbcp;

/**
 * @author czh
 * @date 2018年4月27日 上午7:59:36
 * @description:
 */
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * DBCP配置类
 * 
 * @author SUN
 */
public class DBCPUtilDemo {

	private static Properties properties = new Properties();
	private static DataSource dataSource;
	// 加载DBCP配置文件
	static {
		try {
//			InputStream is0 = DBCPUtilDemo.class.getClassLoader().getResourceAsStream("dbcp.properties");
			InputStream is0 = DBCPUtilDemo.class.getResourceAsStream("/db.properties");
			// System.out.println(is0.getAbsolutePath());
			// FileInputStream is0 = new FileInputStream("dbcp.properties");
			properties.load(is0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
//			dataSource = BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 从连接池中获取一个连接
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}

	public static void main(String[] args) {
		getConnection();
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 * @param stmt
//	 * @param preStmt
	 * @param rs
	 * @throws SQLException
	 */
	private static void replease2(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		// if (conn != null) {
		// conn.close();
		// conn = null;
		// }
	}

	public static void repleaseAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

	/**
	 * 利用正则表达式,获得SELECT SQL中的列名
	 * 
	 * @param sql
	 * @return
	 */
	private static List<String> getColumnsFromSelect(String sql) {
		List<String> colNames = new ArrayList<String>();
		// 取出sql中列名部分
		Pattern p = Pattern.compile("(?i)select\\s(.*?)\\sfrom.*");
		Matcher m = p.matcher(sql.trim());
		String[] tempA = null;
		if (m.matches()) {
			tempA = m.group(1).split(",");
		}
		if (tempA == null) {
			return null;
		}
		String p1 = "(\\w+)";
		String p2 = "(?:\\w+\\s(\\w+))";
		String p3 = "(?:\\w+\\sas\\s(\\w+))";
		String p4 = "(?:\\w+\\.(\\w+))";
		String p5 = "(?:\\w+\\.\\w+\\s(\\w+))";
		String p6 = "(?:\\w+\\.\\w+\\sas\\s(\\w+))";
		String p7 = "(?:.+\\s(\\w+))";
		String p8 = "(?:.+\\sas\\s(\\w+))";
		p = Pattern.compile(
				"(?:" + p1 + "||" + p2 + "||" + p3 + "||" + p4 + "||" + p5 + "||" + p6 + "||" + p7 + "||" + p8 + ")");
		for (String temp : tempA) {
			m = p.matcher(temp.trim());
			if (!m.matches()) {
				continue;
			}
			for (int i = 1; i <= m.groupCount(); i++) {
				if (m.group(i) == null || "".equals(m.group(i))) {
					continue;
				}
				colNames.add(m.group(i));
			}
		}
		return colNames;
	}

	/**
	 * 利用正则表达式,获得INSERT SQL中的列名
	 * 
	 * @param sql
	 * @return
	 */
	private static List<String> getColumnsFromInsert(String sql) {
		List<String> colNames = new ArrayList<String>();
		// 取出sql中列名部分
		Pattern p = Pattern.compile("(?i)insert\\s+into.*\\((.*)\\)\\s+values.*");
		Matcher m = p.matcher(sql.trim());
		String[] tempA = null;
		if (m.matches()) {
			tempA = m.group(1).split(",");
		}
		if (tempA == null) {
			return null;
		}
		String p1 = "(\\w+)";
		String p2 = "(?:\\w+\\s(\\w+))";
		String p3 = "(?:\\w+\\sas\\s(\\w+))";
		String p4 = "(?:\\w+\\.(\\w+))";
		String p5 = "(?:\\w+\\.\\w+\\s(\\w+))";
		String p6 = "(?:\\w+\\.\\w+\\sas\\s(\\w+))";
		String p7 = "(?:.+\\s(\\w+))";
		String p8 = "(?:.+\\sas\\s(\\w+))";
		p = Pattern.compile(
				"(?:" + p1 + "||" + p2 + "||" + p3 + "||" + p4 + "||" + p5 + "||" + p6 + "||" + p7 + "||" + p8 + ")");
		for (String temp : tempA) {
			m = p.matcher(temp.trim());
			if (!m.matches()) {
				continue;
			}
			for (int i = 1; i <= m.groupCount(); i++) {
				if (m.group(i) == null || "".equals(m.group(i))) {
					continue;
				}
				colNames.add(m.group(i));
			}
		}
		return colNames;
	}

	/**
	 * 利用正则表达式,获得UPDATE SQL中的列名, 包括WHERE字句的
	 * 
	 * @param sql
	 * @return
	 */
	private static List<String> getColumnsFromUpdate(String sql) {
		List<String> colNames = new ArrayList<String>();
		// 取出sql中列名部分
		Pattern p = Pattern.compile("(?i)update(?:.*)set(.*)(?:from.*)*where(.*(and)*.*)");
		Matcher m = p.matcher(sql.trim());
		String[] tempA = null;
		if (m.matches()) {
			tempA = m.group(1).split(",");
			if (m.groupCount() > 1) {
				String[] tmp = m.group(2).split("and");
				String[] fina = new String[tempA.length + tmp.length];
				System.arraycopy(tempA, 0, fina, 0, tempA.length);
				System.arraycopy(tmp, 0, fina, tempA.length, tmp.length);
				tempA = fina;
			}
		}
		if (tempA == null) {
			return null;
		}
		String p1 = "(?i)(\\w+)(?:\\s*\\=\\s*.*)";
		String p2 = "(?i)(?:\\w+\\.)(\\w+)(?:\\s*\\=\\s*.*)";
		p = Pattern.compile(p1 + "||" + p2);
		for (String temp : tempA) {
			m = p.matcher(temp.trim());
			if (!m.matches()) {
				continue;
			}
			for (int i = 1; i <= m.groupCount(); i++) {
				if (m.group(i) == null || "".equals(m.group(i))) {
					continue;
				}
				colNames.add(m.group(i));
			}
		}
		return colNames;
	}

	/**
	 * 为sql添加统计代码
	 * 
	 * @param sql
	 * @return
	 */
	private static String addCountSQL(String sql) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) as dataCount from (");
		sb.append(sql);
		sb.append(") as a");
		return sb.toString();
	}

	/**
	 * 为sql添加分页代码
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @return
	 */
	private static String addPagingSQL(String sql, int start, int limit) {
		StringBuffer sb = new StringBuffer();

		sb.append(sql);
		sb.append(" LIMIT ");
		sb.append(start);
		sb.append(",");
		sb.append(limit);

		return sb.toString();
	}

	/**
	 * 将RusultSet对象实例化T对象
	 * 
	 * @param <T>
	 * @param t
	 * @param rs
	 * @param sql
	 * @return t
	 * @throws Exception
	 */
	private static <T> T instance(Class<T> t, ResultSet rs, String sql) throws Exception {
		List<String> columns = getColumnsFromSelect(sql);
		T obj = t.newInstance();
		for (String col : columns) {
			try {
				Field f = t.getDeclaredField(col);
				f.setAccessible(true);
				Object v = getValue(col, f.getType().getName(), rs);
				f.set(obj, v);
			} catch (NoSuchFieldException e) {
				Field[] fields = t.getDeclaredFields();
				for (Field f : fields) {
					Column column = f.getAnnotation(Column.class);
					if (column != null && column.name().equals(col)) {
						f.setAccessible(true);
						Object v = getValue(col, f.getType().getName(), rs);
						f.set(obj, v);
					}
				}
			}
		}

		return obj;
	}

	private static Object getValue(String columnName, String type, ResultSet rs) throws SQLException {
		Object obj = null;
		// System.out.println("name="+f.getName()+",
		// type="+f.getType().getName() );
		if ("java.lang.Integer".equals(type) || "int".equals(type)) {
			obj = rs.getInt(columnName);
		} else if ("java.lang.Long".equals(type) || "long".equals(type)) {
			obj = rs.getLong(columnName);
		} else if ("java.lang.Short".equals(type) || "short".equals(type)) {
			obj = rs.getShort(columnName);
		} else if ("java.lang.Float".equals(type) || "float".equals(type)) {
			obj = rs.getFloat(columnName);
		} else if ("java.lang.Double".equals(type) || "double".equals(type)) {
			obj = rs.getDouble(columnName);
		} else if ("java.lang.Byte".equals(type) || "byte".equals(type)) {
			obj = rs.getByte(columnName);
		} else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) {
			obj = rs.getBoolean(columnName);
		} else if ("java.lang.String".equals(type)) {
			obj = rs.getString(columnName);
		} else {
			obj = rs.getObject(columnName);
		}
		// System.out.println("name="+f.getName() +",
		// type="+f.getType().getName()+", value="+(obj == null ? "NULL" :
		// obj.getClass())+",{"+columnName+":"+obj+"}");
		return obj;
	}

	/**
	 * 将param中的参数添加到pstate
	 * 
	 * @param pstate
//	 * @param columns
	 * @throws SQLException
	 */
	private static <T> void setParameters(PreparedStatement pstate, Object... params) throws Exception {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object value = params[i];
				int j = i + 1;
				if (value == null)
					pstate.setString(j, "");
				if (value instanceof String)
					pstate.setString(j, (String) value);
				else if (value instanceof Boolean)
					pstate.setBoolean(j, (Boolean) value);
				else if (value instanceof Date)
					pstate.setDate(j, (Date) value);
				else if (value instanceof Double)
					pstate.setDouble(j, (Double) value);
				else if (value instanceof Float)
					pstate.setFloat(j, (Float) value);
				else if (value instanceof Integer)
					pstate.setInt(j, (Integer) value);
				else if (value instanceof Long)
					pstate.setLong(j, (Long) value);
				else if (value instanceof Short)
					pstate.setShort(j, (Short) value);
				else if (value instanceof Time)
					pstate.setTime(j, (Time) value);
				else if (value instanceof Timestamp)
					pstate.setTimestamp(j, (Timestamp) value);
				else
					pstate.setObject(j, value);
			}
		}

	}

	/**
	 * 将param中的参数添加到pstate
	 * 
	 * @param pstate
	 * @param columns
	 * @param t
	 * @throws SQLException
	 */
	private static <T> void setParameters(PreparedStatement pstate, List<String> columns, T t) throws Exception {
		if (columns != null && columns.size() > 0) {
			for (int i = 0; i < columns.size(); i++) {
				String attr = columns.get(i);
				Object value = null;
				Class<?> c = t.getClass();
				try {
					Field f = c.getDeclaredField(attr);
					value = f.get(t);
				} catch (NoSuchFieldException e) {
					Field[] fields = c.getDeclaredFields();
					for (Field f : fields) {
						Column column = f.getAnnotation(Column.class);
						if (column != null && column.name().equals(attr))
							value = f.get(t);
					}
				}
				int j = i + 1;
				if (value == null)
					pstate.setString(j, "");
				if (value instanceof String)
					pstate.setString(j, (String) value);
				else if (value instanceof Boolean)
					pstate.setBoolean(j, (Boolean) value);
				else if (value instanceof Date)
					pstate.setDate(j, (Date) value);
				else if (value instanceof Double)
					pstate.setDouble(j, (Double) value);
				else if (value instanceof Float)
					pstate.setFloat(j, (Float) value);
				else if (value instanceof Integer)
					pstate.setInt(j, (Integer) value);
				else if (value instanceof Long)
					pstate.setLong(j, (Long) value);
				else if (value instanceof Short)
					pstate.setShort(j, (Short) value);
				else if (value instanceof Time)
					pstate.setTime(j, (Time) value);
				else if (value instanceof Timestamp)
					pstate.setTimestamp(j, (Timestamp) value);
				else
					pstate.setObject(j, value);
			}
		}

	}

	/**
	 * 执行insert操作
	 * 
	 * @param sql
	 *            预编译的sql语句
	 * @param t
	 *            sql中的参数
	 * @return 执行行数
	 * @throws Exception
	 */
	public static <T> int insert(String sql, T t) throws Exception {
		Connection conn = null;
		PreparedStatement pstate = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			List<String> columns = getColumnsFromInsert(sql);
			pstate = conn.prepareStatement(sql);
			setParameters(pstate, columns, t);
			updateCount = pstate.executeUpdate();
		} finally {
			repleaseAll(conn, pstate, null);
		}
		return updateCount;
	}

	public static int insert(String sql) throws Exception {
		Connection conn = null;
		Statement state = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			state = conn.createStatement();
			updateCount = state.executeUpdate(sql);
		} catch (Exception e) {// 插入失败，更新
			updateCount = state.executeUpdate(sql);

		} finally

		{
			repleaseAll(conn, state, null);
		}
		return updateCount;
	}

	public static int insert(String insertSql, String updateSql) throws Exception {
		Connection conn = null;
		Statement state = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			state = conn.createStatement();
			try {
				System.out.println("insertSql=" + insertSql);
				updateCount = state.executeUpdate(insertSql);
			} catch (Exception ex1) {
				try {
					System.out.println("updateSql=" + updateSql);
					updateCount = state.executeUpdate(updateSql);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {// 插入失败，更新
			e.printStackTrace();
		} finally

		{
			repleaseAll(conn, state, null);
		}
		return updateCount;
	}

	public static int update(String sql) throws Exception {
		Connection conn = null;
		Statement state = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			state = conn.createStatement();
			updateCount = state.executeUpdate(sql);

		} finally {
			repleaseAll(conn, state, null);
		}
		return updateCount;
	}

	/**
	 * 执行insert操作
	 * 
	 * @param sql
	 *            预编译的sql语句
	 * @param param
	 *            参数
	 * @return 执行行数
	 * @throws Exception
	 */
	public static <T> int insert(String sql, Object... param) throws Exception {
		Connection conn = null;
		PreparedStatement pstate = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			pstate = conn.prepareStatement(sql);
			setParameters(pstate, param);
			updateCount = pstate.executeUpdate();
		} finally {
			repleaseAll(conn, pstate, null);
		}
		return updateCount;
	}

	/**
	 * 执行update操作
	 * 
	 * @param sql
	 *            预编译的sql语句
	 * @param t
	 *            sql中的参数
	 * @return 执行行数
	 * @throws Exception
	 */
	public static <T> int update(String sql, T t) throws Exception {
		Connection conn = null;
		PreparedStatement pstate = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			List<String> columns = getColumnsFromUpdate(sql);
			pstate = conn.prepareStatement(sql);
			setParameters(pstate, columns, t);
			updateCount = pstate.executeUpdate();
		} finally {
			repleaseAll(conn, pstate, null);
		}
		return updateCount;
	}

	/**
	 * 执行update操作
	 * 
	 * @param sql
	 * @param param
	 *            参数
	 * @return 执行行数
	 * @throws Exception
	 */
	public static <T> int update(String sql, Object... param) throws Exception {
		Connection conn = null;
		PreparedStatement pstate = null;
		int updateCount = 0;
		try {
			conn = getConnection();
			pstate = conn.prepareStatement(sql);
			setParameters(pstate, param);
			updateCount = pstate.executeUpdate();
		} finally {
			repleaseAll(conn, pstate, null);
		}
		return updateCount;
	}

	/**
	 * 查询复数的对象
	 * 
	 * @param t
	 *            查询结果封装的对象类型
	 * @param sql
	 *            预编译的sql
	 * @param param
	 *            查询条件
	 * @return List<T>
	 * @throws Exception
	 */
	public static <T> List<T> queryPlural(Class<T> t, String sql, Object... param) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			setParameters(stmt, param);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(instance(t, rs, sql));
			}
		} finally {
			repleaseAll(conn, stmt, rs);
		}
		return list;
	}

	public static <T> List<T> queryPlural(Class<T> t, String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(instance(t, rs, sql));
			}
		} finally {
			repleaseAll(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * 获得结果集合的第一个字段值集合
	 * 
	 * @author czh
	 * @date 2018年3月11日 下午2:24:50
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> getColList(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} finally {
			repleaseAll(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * 获得结果集合的第一个字段非空值
	 * 
	 * @author czh
	 * @date 2018年3月11日 下午2:24:50
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static String getColFirst(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String temp = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				temp = rs.getString(1);
				if (temp != null && temp.equals("") && !(temp.equals("null"))) {
					return temp;
				}

			}
		} finally {
			repleaseAll(conn, stmt, rs);
		}
		return temp;
	}

	/**
	 * 分页查询复数的对象
	 * 
	 * @param t
	 *            查询结果封装的对象类型
	 * @param start
	 *            开始页
	 * @param limit
	 *            页大小
	 * @param sql
	 *            预编译的sql语句
	 * @param param
	 *            查询参数
	 * @throws Exception
	 */
	public static <T> List<T> queryPluralForPagging(Class<T> t, int start, int limit, String sql, Object... param)
			throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try {
			conn = getConnection();
			// 添加分页代码
			sql = addPagingSQL(sql, start, limit);
			stmt = conn.prepareStatement(sql);
			setParameters(stmt, param);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(instance(t, rs, sql));
			}
		} finally {
			repleaseAll(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * 查询单个的对象
	 * 
	 * @param t
	 *            查询结果对象
	 * @param sql
	 *            预编译的sql
	 * @param param
	 *            查询参数
	 * @return T
	 * @throws Exception
	 */
	public static <T> T querySingular(Class<T> t, String sql, Object... param) throws Exception {
		T obj = null;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstate = null;
		try {
			conn = getConnection();
			pstate = conn.prepareStatement(sql);
			setParameters(pstate, param);
			rs = pstate.executeQuery();
			if (rs.next()) {
				obj = instance(t, rs, sql);
			}
		} finally {
			repleaseAll(conn, pstate, rs);
		}
		return obj;
	}

	public static <T> T querySingular(Class<T> t, String sql) throws Exception {
		T obj = null;
		ResultSet rs = null;
		Connection conn = null;
		Statement state = null;
		try {
			conn = getConnection();
			state = conn.createStatement();

			rs = state.executeQuery(sql);
			if (rs.next()) {
				obj = instance(t, rs, sql);
			}
		} catch (Exception ex) {

		} finally {
			repleaseAll(conn, state, rs);
		}
		return obj;
	}

	/**
	 * 查询数据量
	 * 
	 * @param param
	 *            查询参数
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static int queryDataCount(String sql, Object... param) throws Exception {
		int dataCount = 0;
		Connection conn = null;
		PreparedStatement pstate = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			sql = addCountSQL(sql);
			pstate = conn.prepareStatement(sql);
			setParameters(pstate, param);
			rs = pstate.executeQuery();
			if (rs.next()) {
				dataCount = rs.getInt("dataCount");
			}
		} finally {
			repleaseAll(conn, pstate, rs);
		}
		return dataCount;
	}

	/**
	 * 属性字段的注释，用于标记该属性对应的数据库字段 例如： @Column(name="user_name"); String userName;
	 * 表示userName这个属性对应的数据库字段是user_name
	 * 
	 * 如果属性和数据库字段完全一致，则不必标注 *
	 * 
	 */
	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Column {
		String name() default "";
	}

}

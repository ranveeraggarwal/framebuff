package common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.Converter;
import org.postgresql.jdbc4.Jdbc4Array;

public class jdbc4ArrayConverter implements Converter{

	@Override
	public Object convert(Class arg0, Object arg1) {
		if(arg1 instanceof Jdbc4Array){
			try {
				ResultSet rs = ((Jdbc4Array) arg1).getResultSet();
				List<String> result = new ArrayList<String>();
				while(rs.next()){
					result.add(rs.getArray(2).toString());
					//System.out.print(rs.getArray(2));
				}
				return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
}
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MyTModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rowCount;	
	private int columnCount;
	private ArrayList<Object> data=new ArrayList<Object>();
	private ResultSet result;

	public MyTModel(ResultSet rs) throws Exception {
		 this.result=rs;
		 ResultSetMetaData metaData=rs.getMetaData();
		 rowCount=0;
		 columnCount=metaData.getColumnCount();
		 //System.out.println(columnCount);
		 
		 while(rs.next()){
			 Object[] row=new Object[columnCount];
			 for(int j=0;j<columnCount;j++){
			 row[j]=rs.getObject(j+1);
			 }			 
			 data.add(row);
			 //System.out.println(row);

			 rowCount++;
		}// while
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		 Object[] row=(Object[]) data.get(rowIndex);
		 return row[columnIndex];
	}
	public String getColumnName(int columnIndex){
		 try{
		 ResultSetMetaData metaData=result.getMetaData();
		 //System.out.println(metaData.getColumnName(columnIndex+1));
		 return metaData.getColumnName(columnIndex+1);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	 }// end getColumnName

}
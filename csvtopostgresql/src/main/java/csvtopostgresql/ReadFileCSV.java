package csvtopostgresql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReadFileCSV  {
	private final static String url = "jdbc:postgresql://[hostname]:[port]/[database]";
    	private final static String user = "[db_username]";
    	private final static String password = "[db_password]";
    
    public void ReadCSV() {
    	String csvFilePath = "[your path to the directory csv file]";
    	 
        int batchSize = 20;
 
        Connection connection = null;
        System.out.println("Menambah Data Master");
        try {
 
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
 
            String sql = "INSERT INTO invoice (item, invoiceid, userid, amount, notes, paymentstatus,date) "
            		+ "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (invoiceid) DO NOTHING";
            	
           
            PreparedStatement statement = connection.prepareStatement(sql);
            
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
 
            int count = 0;
 
            lineReader.readLine(); // skip header line
 
            while ((lineText = lineReader.readLine()) != null) {
            	//System.out.println(lineText);
                String[] data = lineText.split(",");
                String item = data[0];
                String invoiceid = data[1];
                String userid = data[2];
                String amount = data[3];
                String notes = data[4];
                String paymentstatus = data[5];
                String date = data[6];
 
                statement.setInt(1, Integer.parseInt(item));
                statement.setString(2, invoiceid);
                statement.setInt(3, Integer.parseInt(userid));
                statement.setInt(4, Integer.parseInt(amount));
                statement.setString(5, notes);
                statement.setString(6, paymentstatus);
                Timestamp sqlTimestamp = Timestamp.valueOf(date);
                statement.setTimestamp(7, sqlTimestamp);
                statement.addBatch();
 
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
 
            lineReader.close();
 
            // execute the remaining queries
            statement.executeBatch();
            System.out.println("Selesai");
            
            connection.commit();
            connection.close();
 
        }
        catch (IOException ex) 
        {
            System.err.println(ex);
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
 
            try {
            	connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

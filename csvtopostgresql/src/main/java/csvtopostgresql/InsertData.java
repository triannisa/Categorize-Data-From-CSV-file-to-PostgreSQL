package csvtopostgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
	private final static String url = "jdbc:postgresql://[hostname]:[port]/[database]";
    private final static String user = "[db_username]";
    private final static String password = "[db_password]";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            //System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
 
    public void InsertAwalTahun() {
    	 Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
 
            String sql = "INSERT INTO awal_tahun (item, invoiceid, userid, amount, notes, paymentstatus,date)" 
                    + "select item, invoiceid, userid, amount, notes, paymentstatus, date from invoice where paymentstatus "
                    + "like '%uccessfully%' AND EXTRACT(month from date)" + 
                    "between 1 AND 3  ON CONFLICT (invoiceid) DO NOTHING";
           
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.executeUpdate();
            System.out.println("Selesai Menambah Data Awal Tahun");
            
            connection.commit();
            connection.close();
        }
        catch (SQLException e) {
        	System.out.println(e.getMessage());
        }   
    }
    
    public void InsertAkhirTahun() {
   	 Connection connection = null;
       try {
           connection = DriverManager.getConnection(url, user, password);
           connection.setAutoCommit(false);

           String sql = "INSERT INTO akhir_tahun (item, invoiceid, userid, amount, notes, paymentstatus,date)" 
                   + "select item, invoiceid, userid, amount, notes, paymentstatus, date from invoice where paymentstatus "
                   + "like '%uccessfully%' AND EXTRACT(month from date)" + 
                   "between 10 AND 12 ON CONFLICT (invoiceid) DO NOTHING";
          
           PreparedStatement statement = connection.prepareStatement(sql);

           statement.executeUpdate();
           System.out.println("Selesai Menambah Data Akhir Tahun");
           
           connection.commit();
           connection.close();
       }
       catch (SQLException e) {
       	System.out.println(e.getMessage());
       }   
   }
    
    public void InsertBelumBayar() {
   	 Connection connection = null;
       try {
           connection = DriverManager.getConnection(url, user, password);
           connection.setAutoCommit(false);

           String sql = "INSERT INTO belum_bayar (item, invoiceid, userid, amount, notes, paymentstatus,date)" 
                   + "select item, invoiceid, userid, amount, notes, paymentstatus, date from invoice where paymentstatus "
                   + " not like '%uccessfully%' ON CONFLICT (invoiceid) DO NOTHING";
           
           PreparedStatement statement = connection.prepareStatement(sql);

           statement.executeUpdate();
           System.out.println("Selesai Menambah Data Belum Bayar");
           
           connection.commit();
           connection.close();
       }
       catch (SQLException e) {
       	System.out.println(e.getMessage());
       }   
   }
}

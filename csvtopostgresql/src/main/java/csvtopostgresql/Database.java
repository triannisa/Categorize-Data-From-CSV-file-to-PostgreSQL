package csvtopostgresql;

import java.sql.*;

public class Database {
	private final static String url = "jdbc:postgresql://[hostname]:[port]/[database]";
    	private final static String user = "[user]";
	private final static String password = "[password]";

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
    
    public void selectAwalTahun() {
    	
    	try {
    		Statement stmnt = null;
    		stmnt = connect().createStatement();
    		String sql = "SELECT * from awal_tahun";
    		ResultSet rs = stmnt.executeQuery(sql);
    		
    		while (rs.next()) {
    			System.out.println("Terima kasih karena Anda telah melakukan pembayaran pada invoice " + rs.getString(2) 
                +" Anda berhak mendapatkan diskon awal tahun sebesar 10%!");
    			}
    		} 
    	catch (SQLException e) {
    		System.out.println(e.getMessage());
    		}
    }
    
    public void selectAkhirTahun() {
    	try {
    		Statement stmnt = null;
    		stmnt = connect().createStatement();
    		String sql = "SELECT * from akhir_tahun";
    		ResultSet rs = stmnt.executeQuery(sql);
    		
    		while (rs.next()) {
    			System.out.println("Terima kasih karena Anda telah melakukan pembayaran pada invoice " + rs.getString(2) 
                +" Anda berhak mendapatkan diskon awal tahun sebesar 20%!");
    			}
    		} 
    	catch (SQLException e) {
    		System.out.println(e.getMessage());
    		}
    }
    
    public void selectBelumBayar() {
    	try {
    		Statement stmnt = null;
    		stmnt = connect().createStatement();
    		String sql = "SELECT * from belum_bayar";
    		ResultSet rs = stmnt.executeQuery(sql);
    		
    		while (rs.next()) {
    			System.out.println("Yuk segera bayar tagihan invoice" + rs.getString(2) 
                +", dan dapatkan diskon sebesar 15%!");
    			}
    		} 
    	catch (SQLException e) {
    		System.out.println(e.getMessage());
    		}
    }
    
    public void InsertAwalTahun() {
    	 Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
 
            String sql = "insert into awal_tahun (item, invoiceid, userid, amount, notes, paymentstatus,date)" +
            		"select item, invoiceid, userid, amount, notes, paymentstatus,date from invoice where paymentstatus like '%uccessfully%' AND EXTRACT(month from date)"
            		+"between 1 AND 3 ON CONFLICT (invoiceid) DO NOTHING";
           
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

           String sql = "insert into akhir_tahun (item, invoiceid, userid, amount, notes, paymentstatus,date)" +
           		"select item, invoiceid, userid, amount, notes, paymentstatus,date from invoice where paymentstatus like '%uccessfully%' AND EXTRACT(month from date)"
           		+"between 10 AND 12 ON CONFLICT (invoiceid) DO NOTHING";
          
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
    
    public void InsertBelumBayar() {
   	 Connection connection = null;
       try {
           connection = DriverManager.getConnection(url, user, password);
           connection.setAutoCommit(false);

           String sql = "insert into belum_bayar (item, invoiceid, userid, amount, notes, paymentstatus,date)" +
           		"select item, invoiceid, userid, amount, notes, paymentstatus,date from invoice where "
           		+ "paymentstatus like '%uccessfully%' ON CONFLICT (invoiceid) DO NOTHING";
          
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
}

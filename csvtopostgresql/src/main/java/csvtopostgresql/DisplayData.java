package csvtopostgresql;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DisplayData extends JFrame implements ActionListener {
	private final static String url = "jdbc:postgresql://localhost:5432/spotify";
    private final static String user = "postgres";
    private final static String password = "icha";
    
    JFrame frame1;
    JLabel l0, l1, l2;
    JComboBox c1;
    JButton b1;
    Connection con;
    ResultSet rs, rs1;
    Statement st, st1;
    PreparedStatement pst;
    String ids;
    static JTable table;
    String[] columnNames = {"Invoice Transaksi"};
    String from;

    public DisplayData() {
        
        l0 = new JLabel("Data Invoice");
        l0.setForeground(Color.black);
        l0.setFont(new Font("Serif", Font.BOLD, 20));
        l1 = new JLabel("Pilih Tabel");
        b1 = new JButton("submit");

        l0.setBounds(100, 50, 350, 40);
        l1.setBounds(75, 110, 75, 20);
        b1.setBounds(150, 150, 150, 20);
        b1.addActionListener(this);

        setTitle("Small Project Sekolah Big Data");
        setLayout(null);
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(l0);
        add(l1);
        add(b1);
        
        try {
        	con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
    		ResultSet rs = st.executeQuery("SELECT * from menu");
    		
    		Vector v = new Vector();
            while (rs.next()) {
                ids = rs.getString(2);
                v.add(ids);
            }

            c1 = new JComboBox(v);

            c1.setBounds(150, 110, 150, 20);

            add(c1);

            st.close();
            rs.close();
            
        } catch (Exception e) {

        }

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            showTableData();
        }
    }

    public void showTableData() {
        frame1 = new JFrame("Small Project Sekolah Big Data");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        from = (String) c1.getSelectedItem();

        String invoiceid = "";

        try {
            //pst = con.prepareStatement("select * from emp where UNAME='" + from + "'");
        	pst = con.prepareStatement("select * from " + from + "");
            System.out.println(pst);
        	ResultSet rs = pst.executeQuery();
            int i = 0;
            
            if(from.equals("awal_tahun")) {
            while (rs.next()) {
            	invoiceid = "Terima kasih karena Anda telah melakukan pembayaran pada invoice " + rs.getString("invoiceid") 
                + ". Anda berhak mendapatkan diskon awal tahun sebesar 10%!";
                model.addRow(new Object[]{ invoiceid});
                i++;
            	}
            }
            else if(from.equals("akhir_tahun")) {
                while (rs.next()) {
                	invoiceid = "Terima kasih karena Anda telah melakukan pembayaran pada invoice " + rs.getString("invoiceid") 
                    + ". Anda berhak mendapatkan diskon awal tahun sebesar 20%!";
                    model.addRow(new Object[]{ invoiceid});
                    i++;
                	}
                }
            else if(from.equals("belum_bayar")) {
                while (rs.next()) {
                	invoiceid = "Yuk segera bayar tagihan invoice " + rs.getString("invoiceid") 
                    + ". dapatkan diskon sebesar 15%!";
                    model.addRow(new Object[]{ invoiceid});
                    i++;
                	}
                }   
            
            if (i < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (i == 1) {
                System.out.println(i + " Record Found");
            } else {
                System.out.println(i + " Records Found");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(1000, 600);
    }
}
package csvtopostgresql;


public class Main {
	public static void main (String [] args) {
		Database database = new Database();
		database.connect();
		ReadFileCSV fileCSV = new ReadFileCSV();
		fileCSV.ReadCSV();
		InsertData insert = new InsertData();
		insert.InsertAwalTahun();
		insert.InsertAkhirTahun();
		insert.InsertBelumBayar();
		new DisplayData();
	}
}

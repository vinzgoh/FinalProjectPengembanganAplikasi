
import DB.CoffeeDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vincent
 */
public class NewClass {

    // buka koneksi ke database, bentuk table dan isikan dengan data default apabila
    // table masih kosong
    public Connection connectAndCreateTable(String url) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        String sql = "create table if not exists Mahasiswa ("
                + "kodepbl varchar(11) PRIMARY KEY, "
                + "namapbl varchar(255) not null"
                + "namabrg varchar(255) not null"
                + "hargabrg number(255) not null"
                + ")";
        stmt.execute(sql);

        ResultSet resultSet = stmt.executeQuery("select * from coffee");

        // jika belum ada data tambahkan data contoh
        if (!resultSet.next()) {
            stmt.execute("insert into Coffee values (\"014201800001\", \"Budi\", \"Java Chips\", \"24000\")");
            stmt.execute("insert into Coffee values (\"084201800002\", \"Susi\", \"Coffee Latte\", \"30000\")");
            stmt.execute("insert into Coffee values (\"084201800003\", \"Daniel\", \"Exspresso\", \"34000\")");
            stmt.execute("insert into Coffee values (\"114201800004\", \"Vincent\", \"Caramel Latte\", \"32000\")");
            stmt.execute("insert into Coffee values (\"134201800005\", \"Susi\", \"Coffee Latte\", \"30000\")");
        }
        return conn;
    }

    public void start(Stage stage) throws Exception {
        Connection conn = connectAndCreateTable("jdbc:sqlite:coffee.sqlite");
        CoffeeDao dao = new CoffeeDao(conn);
    }
}

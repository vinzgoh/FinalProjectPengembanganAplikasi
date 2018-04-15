/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoffeeDao {
    private Connection conn;
    private PreparedStatement stmt;

    public CoffeeDao(Connection conn) {
        this.conn = conn;
    }

    public boolean add(Coffee coffee) throws SQLException {
       String sql = "INSERT INTO Coffee VALUES (?, ?)";
       stmt = conn.prepareStatement(sql);
       stmt.setString(1, coffee.getKodepbl());
       stmt.setString(2, coffee.getNamapbl());
       stmt.setString(3, coffee.getNamabrg());
       stmt.setString(4, coffee.getHargabrg());
       return stmt.execute();
    }

    public boolean add(String kodepbl, String namapbl, String namabrg, String hargabrg) throws SQLException {
        return add(new Coffee(kodepbl, namapbl, namabrg , hargabrg));
    }

    public boolean update(String kodepbl, Coffee namapbl,Coffee namabrg,Coffee hargabrg) throws SQLException {
        String sql = "UPDATE Coffee SET namapbl=? SET namabrg=? SET hargabrg=? where kodepbl=?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, kodepbl);
        stmt.setString(2, namapbl.getNamapbl());
        stmt.setString(3, namabrg.getNamabrg());
        stmt.setString(4, hargabrg.getHargabrg());
        return stmt.execute();
    }

    public boolean update(Coffee coffee) throws SQLException {
        return update(coffee.getKodepbl(), coffee,coffee,coffee);
    }

    public boolean delete(String kode) throws SQLException {
        String sql = "DELETE FROM Coffee WHERE kodepbl=?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, kode);

        return stmt.execute();
    }

    public boolean delete(Coffee mhs) throws SQLException {
        return delete(mhs.getKodepbl());
    }

    public List<Coffee> all() throws SQLException {
        String sql = "SELECT * FROM Coffee";
        stmt = conn.prepareStatement(sql);

        List<Coffee> coffee = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            coffee.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return coffee;
    }

    public List<Coffee> findBykodepbl(String kode) throws SQLException {
        String sql = "SELECT * FROM Coffee where kodepbl like ?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, "%" + kode + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> coffee = new ArrayList<>();
        while (resultSet.next()) {
            coffee.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return coffee;
    }

    public List<Coffee> findBynamapbl(String namapbl) throws SQLException {
        String sql = "SELECT * FROM Coffee where namapbl like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + namapbl + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> coffee = new ArrayList<>();
        while (resultSet.next()) {
            coffee.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return coffee;
    }
    
        public List<Coffee> findBynamabrg(String namabrg) throws SQLException {
        String sql = "SELECT * FROM Coffee where namabrg like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + namabrg + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> coffee = new ArrayList<>();
        while (resultSet.next()) {
            coffee.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return coffee;
    }
    
    public List<Coffee> findByhargabrg(String hargabrg) throws SQLException {
        String sql = "SELECT * FROM Coffee where hargabrg like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + hargabrg + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> coffee = new ArrayList<>();
        while (resultSet.next()) {
            coffee.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return coffee;
    }


}

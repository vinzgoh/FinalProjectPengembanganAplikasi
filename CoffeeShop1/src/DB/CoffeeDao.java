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

    /**
     * menambahkan mahasiswa ke database
     * @param coffee data mahasiswa yang ingin ditambahkan
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan penambahan data
     */
    public boolean add(Coffee coffee) throws SQLException {
       String sql = "INSERT INTO Mahasiswa VALUES (?, ?)";
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

    /**
     * mengupdate data mahasiswa
     * @param kode nim data mahasiswa yang akan diupdate
     * @param coffee data mahasiswa yang akan diupdate
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public boolean update(String kodepbl, Coffee coffee, String namabrg, String hargabrg) throws SQLException {
        String sql = "UPDATE Mahasiswa SET nama=? where kode=? where qt=?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, coffee.getNamapbl());
        stmt.setString(2, kodepbl);
        stmt.setString(3, namabrg);
        stmt.setString(4, hargabrg);
        return stmt.execute();
    }

    /**
     * mengupdate data mhs
     * @param coffee data mahasiswa yang akan di-update
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kesalahan pada operasi sql
     */
    public boolean update(Coffee coffee) throws SQLException {
        return update(coffee.getKodepbl(), coffee, coffee, coffee);
    }

    /**
     * menghapus data mahasiswa
     * @param NIM nim mahasiswa yang akan dihapus
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public boolean delete(String kode) throws SQLException {
        String sql = "DELETE FROM Mahasiswa WHERE kodepbl=?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, kode);

        return stmt.execute();
    }

    /**
     * menghapus data mahasiswa
     * @param mhs mahasiswa yang akan dihapus
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public boolean delete(Coffee mhs) throws SQLException {
        return delete(mhs.getKodepbl());
    }

    /**
     * mengakses seluruh data mahasiswa
     * @return list mahasiswa
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public List<Coffee> all() throws SQLException {
        String sql = "SELECT * FROM Mahasiswa";
        stmt = conn.prepareStatement(sql);

        List<Coffee> mhs = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            mhs.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return mhs;
    }

    /**
     * mengakses data mahasiswa
     * @param kode nim mahasiswa yang akan diakses
     * @return Optional Mahasiswa
     * @throws SQLException
     */
    public List<Coffee> findBykodepbl(String kode) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa where kodepbl like ?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, "%" + kode + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> mhs = new ArrayList<>();
        while (resultSet.next()) {
            mhs.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return mhs;
    }

    /**
     * mengakses mahasiswa dengan nama tertentu %nama%
     * @param namapbl nama mahasiswa yang ingin dicari
     * @return list mahasiswa
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public List<Coffee> findBynamapbl(String namapbl) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa where namapbl like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + namapbl + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> mhs = new ArrayList<>();
        while (resultSet.next()) {
            mhs.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return mhs;
    }
    
        public List<Coffee> findBynamabrg(String namabrg) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa where namabrg like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + namabrg + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> mhs = new ArrayList<>();
        while (resultSet.next()) {
            mhs.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return mhs;
    }
    
    public List<Coffee> findByhargabrg(String hargabrg) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa where hargabrg like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + hargabrg + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Coffee> mhs = new ArrayList<>();
        while (resultSet.next()) {
            mhs.add(new Coffee(resultSet.getString("kodepbl"), resultSet.getString("namapbl"),resultSet.getString("namabrg"),resultSet.getString("hargabrg")));
        }

        return mhs;
    }

    /**
     * menghitung banyak data
     * @return banyak data mahasiswa jika tidak ada hasil yang dikembalikan
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Coffee";
        stmt = conn.prepareStatement(sql);

        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return -1;
        }
    }

    public List<Coffee> byPage(int page, int itemPerPage) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa Limit ? OFFSET ?";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, itemPerPage);
        stmt.setInt(2, itemPerPage*page);

        List<Coffee> mhs = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) mhs.add(
                new Coffee(resultSet.getString("kodepbl"),
                        resultSet.getString("namapbl"),
                        resultSet.getString("namabrg"),
                        resultSet.getString("hargabrg")));

        return mhs;
    }

    private boolean update(String kodepbl, Coffee coffee, Coffee coffee0, Coffee coffee1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.util.Objects;

public class Coffee {
    private StringProperty kodepbl;
    private StringProperty namapbl;
    private StringProperty namabrg;
    private StringProperty hargabrg;

    public Coffee(String kodepbl, String namapbl, String namabrg, String hargabrg) {
        
        this.namapbl = new SimpleStringProperty(namapbl);
        this.kodepbl = new SimpleStringProperty(kodepbl);
        this.namabrg = new SimpleStringProperty(namabrg);
        this.hargabrg = new SimpleStringProperty(hargabrg);
    }

    public String getKodepbl() {
        return kodepbl.get();
    }

    public StringProperty kodepblProperty() {
        return kodepbl;
    }

    public void setKodepbl(String kode) {
        this.kodepbl.set(kode);
    }

    public String getNamapbl() {
        return namapbl.get();
    }

    public StringProperty namapblProperty() {
        return namapbl;
    }

    public void setNamapbl(String nama) {
        this.namapbl.set(nama);
    }
    
    public String getNamabrg() {
        return namabrg.get();
    }

    public StringProperty namabrgProperty() {
        return namabrg;
    }

    public void setNamabrg(String nama) {
        this.namabrg.set(nama);
    }
    
    public String getHargabrg() {
        return hargabrg.get();
    }

    public StringProperty hargabrgProperty() {
        return hargabrg;
    }

    public void setHargabrg(String harga) {
        this.hargabrg.set(harga);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.kodepbl);
        hash = 83 * hash + Objects.hashCode(this.namapbl);
        hash = 83 * hash + Objects.hashCode(this.namabrg);
        hash = 83 * hash + Objects.hashCode(this.hargabrg);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coffee other = (Coffee) obj;
        if (!Objects.equals(this.kodepbl, other.kodepbl)) {
            return false;
        }
        if (!Objects.equals(this.namapbl, other.namapbl)) {
            return false;
        }
        if (!Objects.equals(this.namabrg, other.namabrg)) {
            return false;
        }
        if (!Objects.equals(this.hargabrg, other.hargabrg)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coffee{" + "kodepbl=" + kodepbl + ", namapbl=" + namapbl + ", namabrg=" + namabrg + ", hargabrg=" + hargabrg + '}';
    }
    

}

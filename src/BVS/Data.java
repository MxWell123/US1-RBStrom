/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BVS;

/**
 *
 * @author davidecek
 */
public class Data {

    private int kluc;

    public Data(int kluc) {
        this.kluc = kluc;
    }

    public int getKluc() {
        return kluc;
    }

    public void setKluc(int kluc) {
        this.kluc = kluc;
    }
   
    public int porovnaj(Data data) {
        if (data.getKluc() > this.getKluc()) {
            return 1;
        }
        if (data.getKluc() < this.getKluc()) {
            return -1;
        } else {
            return 0;
        }
    }
}

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
public class Vrchol {

    private IData data;
    private Vrchol lavy;
    private Vrchol pravy;
    private Vrchol otec;
    private boolean farba;
    private boolean doubleBlack;

    public Vrchol(IData data) {
        this.data = data;
        this.lavy = null;
        this.pravy = null;
        this.otec = null;
        this.farba = true;
        this.doubleBlack = false;
    }

    public Vrchol(Vrchol vrchol) {
        this(vrchol.getData());
        this.lavy = vrchol.getLavy();
        this.pravy = vrchol.getPravy();
        this.otec = vrchol.getOtec();
        this.farba = vrchol.getFarba();
        this.doubleBlack = vrchol.isDoubleBlack();
    }

    public boolean isFarba() {
        return farba;
    }

    public boolean isDoubleBlack() {
        return doubleBlack;
    }

    public void setDoubleBlack(boolean doubleBlack) {
        this.doubleBlack = doubleBlack;
    }

    public void zamaluj(boolean farba) {
        this.farba = farba;
    }

    public boolean getFarba() {
        return farba;
    }

    public void setFarba(boolean farba) {
        this.farba = farba;
    }

    public IData getData() {
        return data;
    }

    public void setData(IData data) {
        this.data = data;
    }

    public Vrchol getLavy() {
        return lavy;
    }

    public void setLavy(Vrchol lavy) {
        this.lavy = lavy;
    }

    public Vrchol getPravy() {
        return pravy;
    }

    public void setPravy(Vrchol pravy) {
        this.pravy = pravy;
    }

    public Vrchol getOtec() {
        return otec;
    }

    public void setOtec(Vrchol otec) {
        this.otec = otec;
    }

}

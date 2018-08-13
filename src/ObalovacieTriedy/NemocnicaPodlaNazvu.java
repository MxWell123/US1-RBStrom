/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObalovacieTriedy;

import BVS.IData;
import sem1.Nemocnica;

/**
 *
 * @author davidecek
 */
public class NemocnicaPodlaNazvu implements IData {

    private Nemocnica nemocnica;

    public NemocnicaPodlaNazvu(Nemocnica nemocnica) {
        this.nemocnica = nemocnica;
    }

    @Override
    public int porovnaj(IData data) {
        return nemocnica.porovnajPodlaNazvu((Nemocnica) data.dajTyp());
    }

    @Override
    public Object getKluc() {
        return nemocnica.getNemocnica();
    }

    @Override
    public Object dajTyp() {
        return this.nemocnica;
    }

}

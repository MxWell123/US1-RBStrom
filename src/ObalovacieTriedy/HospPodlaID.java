/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObalovacieTriedy;

import BVS.IData;
import sem1.Hospitalizacia;

/**
 *
 * @author davidecek
 */
public class HospPodlaID implements IData {

    private Hospitalizacia hosp;

    public HospPodlaID(Hospitalizacia hosp) {
        this.hosp = hosp;
    }
    @Override
    public int porovnaj(IData data) {
        return hosp.porovnajPodlaRC((Hospitalizacia) data.dajTyp());
    }

    @Override
    public Object getKluc() {
        return hosp.getRc_pacienta();
    }

    @Override
    public Object dajTyp() {
        return this.hosp;
    }

}

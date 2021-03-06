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
public class HospNemocnicePodlaDatumuARC implements IData {
      private Hospitalizacia hosp;

    public HospNemocnicePodlaDatumuARC(Hospitalizacia hosp) {
        this.hosp = hosp;
    }
    @Override
    public int porovnaj(IData data) {
        return hosp.porovnajPodlaDatumu((Hospitalizacia) data.dajTyp());
    }

    @Override
    public Object getKluc() {
        return hosp.getDatum_zac_hosp();
    }

    @Override
    public Object dajTyp() {
        return this.hosp;
    }
}

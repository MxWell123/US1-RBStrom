/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObalovacieTriedy;

import BVS.IData;
import sem1.Pacient;
import sem1.Poistovna;

/**
 *
 * @author davidecek
 */
public class PoistovnePodlaCisla implements IData {

    private Poistovna poistovna;

    public PoistovnePodlaCisla(Poistovna poistovna) {
        this.poistovna = poistovna;
    }

    @Override
    public int porovnaj(IData data) {
        return poistovna.porovnajPodlaCisla((Poistovna) data.dajTyp());
    }

    @Override
    public Object getKluc() {
        return poistovna.getCislo();
    }

    @Override
    public Object dajTyp() {
        return this.poistovna;
    }

}

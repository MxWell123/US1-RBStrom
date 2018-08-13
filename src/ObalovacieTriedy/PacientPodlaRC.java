/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObalovacieTriedy;

import BVS.IData;
import sem1.Pacient;

/**
 *
 * @author davidecek
 */
public class PacientPodlaRC implements IData {

    private Pacient pacient;

    public PacientPodlaRC(Pacient pacient) {
        this.pacient = pacient;
    }

    @Override
    public int porovnaj(IData data) {
        return pacient.porovnajPodlaRC((Pacient) data.dajTyp());
    }

    @Override
    public Object getKluc() {
        return pacient.getRodnecislo();
    }

    @Override
    public Object dajTyp() {
        return this.pacient;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem1;

import BVS.Vrchol;
import ObalovacieTriedy.HospNemocnicePodlaDatumuARC;
import ObalovacieTriedy.HospPoistovnePodlaDatumu;
import ObalovacieTriedy.PacientPodlaRC;
import RB.RB;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author davidecek
 */
public class Poistovna {

    private int cisloPoistovne;
    private RB hospPodlaDnaaRC;

    public Poistovna(int cislo) {
        this.cisloPoistovne = cislo;
        this.hospPodlaDnaaRC = new RB();
    }

    public int porovnajPodlaCisla(Poistovna poistovna) {
        if (poistovna.getCislo() > this.getCislo()) {
            return 1;
        } else if (poistovna.getCislo() < this.getCislo()) {
            return -1;
        }
        return 0;
    }

    public int getCislo() {
        return cisloPoistovne;
    }

    void pridajHospitalizaciu(Hospitalizacia hosp) {
        hospPodlaDnaaRC.vlozVrchol(new Vrchol(new HospPoistovnePodlaDatumu(hosp)));
    }

    void ukonciHospitalizaciu(LocalDate dat_zac, String rod_cisl, LocalDate dat_konc) {
        Vrchol hospV = hospPodlaDnaaRC.najdi(new Vrchol(new HospPoistovnePodlaDatumu(new Hospitalizacia(rod_cisl, dat_zac))));
        Hospitalizacia hosp = (Hospitalizacia) hospV.getData().dajTyp();
        hosp.setDatum_kon_hosp(dat_konc);
    }

    ArrayList<Hospitalizacia> dajVsetkyHospitalVIntervale(LocalDate odDatum, LocalDate doDatum) {
        RB hospVrcholyStrom = hospPodlaDnaaRC.inOrder(odDatum, doDatum);
        ArrayList<Vrchol> hospVrcholy = hospVrcholyStrom.inOrder();
        Hospitalizacia hosp = null;
        String rc_pacient = "";
        Vrchol pom;
        Pacient pacient = null;
        ArrayList<Hospitalizacia> hospitalizacie = new ArrayList<>();
        for (Vrchol vrchol : hospVrcholy) {
            hosp = (Hospitalizacia) vrchol.getData().dajTyp();
            hospitalizacie.add(hosp);
        }
        return hospitalizacie;
    }

    RB dajVsetkyHospitalizacie() {
        return hospPodlaDnaaRC;
    }

}

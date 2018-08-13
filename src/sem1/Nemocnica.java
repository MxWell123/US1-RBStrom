/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem1;

import BVS.Vrchol;
import ObalovacieTriedy.HospNemocnicePodlaDatumuARC;
import ObalovacieTriedy.PacientPodlaMena;
import ObalovacieTriedy.PacientPodlaRC;
import RB.RB;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author davidecek
 */
public class Nemocnica {

    private String nemocnica;
    private RB hospitalizacieNemocnice;
    private RB hospitalizovanyPacientiPodlaRC;
    private RB pacientiPodlaMena;
    private RB pacientiPodlaRC;

    public Nemocnica(String nemocnica) {
        this.nemocnica = nemocnica;
        this.hospitalizacieNemocnice = new RB();
        this.pacientiPodlaMena = new RB();
        this.pacientiPodlaRC = new RB();
        this.hospitalizovanyPacientiPodlaRC = new RB();
    }

    public ArrayList<Vrchol> dajHospitalizovanychPacientovNemocnice() {
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        vrcholy = hospitalizovanyPacientiPodlaRC.inOrder();
        return vrcholy;
    }

    public RB dajPacientovNemocnice() {
        return pacientiPodlaRC;
    }

    public Pacient najdiPacientaPodlaRC(String rod_cisl) {
        Vrchol vrchol = pacientiPodlaRC.najdi(new Vrchol(new PacientPodlaRC(new Pacient(rod_cisl))));
        return (Pacient) vrchol.getData().dajTyp();
    }

    public String getNemocnica() {
        return nemocnica;
    }

    public RB getHospitalizacieNemocnice() {
        return hospitalizacieNemocnice;
    }

    public int porovnajPodlaNazvu(Nemocnica nemocnica) {
        if (nemocnica.getNemocnica().compareToIgnoreCase(this.getNemocnica()) > 0) {
            return 1;
        } else if (nemocnica.getNemocnica().compareToIgnoreCase(this.getNemocnica()) < 0) {
            return -1;
        }
        return 0;
    }

    public void pridajPacienta(Pacient pacient) {
        pacientiPodlaRC.vlozVrchol(new Vrchol(new PacientPodlaRC(pacient)));
        pacientiPodlaMena.vlozVrchol(new Vrchol(new PacientPodlaMena(pacient)));
    }

    public void zacniHospitalizaciu(Hospitalizacia hosp, Pacient pacient) {
        this.pridajHospitalizovanehoPacienta(pacient);
        this.pridajHospNemocnice(hosp);
    }

    private void pridajHospitalizovanehoPacienta(Pacient pacient) {
        hospitalizovanyPacientiPodlaRC.vlozVrchol(new Vrchol(new PacientPodlaRC(pacient)));
    }

    private void pridajHospNemocnice(Hospitalizacia hosp) {
        hospitalizacieNemocnice.vlozVrchol(new Vrchol(new HospNemocnicePodlaDatumuARC(hosp)));

    }

    public LocalDate vymazAktualneHospPacienta(String rod_cislo, LocalDate datum_kon_hosp) {
        Vrchol pac = hospitalizovanyPacientiPodlaRC.najdi(new Vrchol(new PacientPodlaRC(new Pacient(rod_cislo))));
        if (pac != null) {
            hospitalizovanyPacientiPodlaRC.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient(rod_cislo))));
            Pacient pacient = (Pacient) pac.getData().dajTyp();
            Vrchol hosp = hospitalizacieNemocnice.najdi(new Vrchol(new HospNemocnicePodlaDatumuARC(new Hospitalizacia(pacient.getRodnecislo(), pacient.getDatumZacatiaAktualnejHosp()))));
            Hospitalizacia hospitalizacia = (Hospitalizacia) hosp.getData().dajTyp();
            hospitalizacia.setDatum_kon_hosp(datum_kon_hosp);
            return hospitalizacia.getDatum_zac_hosp();
        } else {
            return null;
        }
    }

    public void nastavKoniecHospitalizacie(String rod_cislo, LocalDate datum_zac_hosp, LocalDate datum_kon_hosp) {
        Vrchol vrchol = hospitalizacieNemocnice.najdi(new Vrchol(new HospNemocnicePodlaDatumuARC(new Hospitalizacia(rod_cislo))));
        Hospitalizacia hosp = (Hospitalizacia) vrchol.getData().dajTyp();
        hosp.setDatum_kon_hosp(datum_kon_hosp);

    }

    public ArrayList<Vrchol> najdiPacientaPodlaMena(String meno, String priezvisko) {
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        vrcholy = pacientiPodlaMena.inOrder(new Pacient(meno, priezvisko));
        return vrcholy;
    }

    public ArrayList<Vrchol> dajHospitalizovanychPacientovDanejPoistovne(int poistovna) {
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        vrcholy = hospitalizovanyPacientiPodlaRC.inOrderPoistovne(poistovna);
        return vrcholy;
    }

    ArrayList<Vrchol> dajPacientovPodlaRC() {
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        vrcholy = pacientiPodlaRC.inOrder();
        return vrcholy;
    }

    ArrayList<Vrchol> dajHospitalizacieNemocnice() {
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        vrcholy = hospitalizacieNemocnice.inOrder();
        return vrcholy;
    }

    void pridajHospitalNemocnice(Vrchol vrchol) {
        hospitalizacieNemocnice.vlozVrchol(vrchol);
    }

    void pridajPacientaVrchol(Vrchol vrchol) {
        pacientiPodlaRC.vlozVrchol(vrchol);
        pacientiPodlaMena.vlozVrchol(vrchol);
    }

    void pridajAktualneHospitPacienta(Vrchol vrchol) {
        hospitalizovanyPacientiPodlaRC.vlozVrchol(vrchol);
    }

    ArrayList<Pacient> dajVsetkyHospitalVIntervale(LocalDate odDatum, LocalDate doDatum) {
        RB hospVrcholyStrom = hospitalizacieNemocnice.inOrder(odDatum, doDatum);
        ArrayList<Vrchol> hospVrcholy = hospVrcholyStrom.inOrder();
        Hospitalizacia hosp = null;
        String rc_pacient = "";
        Vrchol pom;
        Pacient pacient = null;
        RB pacienti = new RB();
        for (Vrchol vrchol : hospVrcholy) {
            hosp = (Hospitalizacia) vrchol.getData().dajTyp();
            rc_pacient = hosp.getRc_pacienta();
            pom = pacientiPodlaRC.najdi(new Vrchol(new PacientPodlaRC(new Pacient(rc_pacient))));
            pacient = (Pacient) pom.getData().dajTyp();
            pacienti.vlozVrchol(new Vrchol(new PacientPodlaRC(pacient)));
        }
        ArrayList<Vrchol> pacientiVrcholy = pacienti.inOrder();
        ArrayList<Pacient> pacientiVysledok = new ArrayList<>();
        for (Vrchol vrchol : pacientiVrcholy) {
            Pacient pacientVysledok = (Pacient) vrchol.getData().dajTyp();
            pacientiVysledok.add(pacientVysledok);
        }
        return pacientiVysledok;
    }

    boolean najdiHospitalizaciu(Hospitalizacia hosp) {
        Vrchol vrchol = hospitalizacieNemocnice.najdi(new Vrchol(new HospNemocnicePodlaDatumuARC(hosp)));
        if (vrchol != null) {
            Hospitalizacia hospNajdena = (Hospitalizacia)vrchol.getData().dajTyp();
            if (hospNajdena.getDatum_kon_hosp() == null) {
                return true;
            }
        }
        return false;
    }

}

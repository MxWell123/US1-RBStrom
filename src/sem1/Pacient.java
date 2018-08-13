/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem1;

import BVS.Vrchol;
import ObalovacieTriedy.HospNemocnicePodlaDatumuARC;
import ObalovacieTriedy.HospPacientaPodlaDatumu;
import ObalovacieTriedy.PacientPodlaRC;
import RB.RB;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author davidecek
 */
public class Pacient {

    private String meno;
    private String priezvisko;
    private String rodnecislo;
    private LocalDate datum_narodenia;
    private int kod_ZP;
    private RB hospitalizacie;
    private LocalDate datumZacatiaAktualnejHosp;

    public Pacient(String meno, String priezvisko, String rodnecislo, LocalDate datum_narodenia, int kod_ZP) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.rodnecislo = rodnecislo;
        this.datum_narodenia = datum_narodenia;
        this.kod_ZP = kod_ZP;
        this.hospitalizacie = new RB();
        this.datumZacatiaAktualnejHosp = null;
    }

    public Pacient(String meno, String priezvisko, String rodnecislo, LocalDate datum_narodenia, int kod_ZP, LocalDate datumZacatiaAktualnejHosp) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.rodnecislo = rodnecislo;
        this.datum_narodenia = datum_narodenia;
        this.kod_ZP = kod_ZP;
        this.hospitalizacie = new RB();
        this.datumZacatiaAktualnejHosp = datumZacatiaAktualnejHosp;
    }

    public Pacient(String rodnecislo) {
        this.rodnecislo = rodnecislo;
        this.hospitalizacie = new RB();
    }

    public Pacient(String meno, String priezvisko) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.hospitalizacie = new RB();
    }

    public String getRodnecislo() {
        return rodnecislo;

    }

    public void setDatumZacatiaAktualnejHosp(LocalDate datumZacatiaAktualnejHosp) {
        this.datumZacatiaAktualnejHosp = datumZacatiaAktualnejHosp;
    }

    public LocalDate getDatumZacatiaAktualnejHosp() {
        return datumZacatiaAktualnejHosp;
    }

    public int porovnajPodlaRC(Pacient pacient) {
        if (pacient.getRodnecislo().compareToIgnoreCase(this.getRodnecislo()) > 0) {
            return 1;
        } else if (pacient.getRodnecislo().compareToIgnoreCase(this.getRodnecislo()) < 0) {
            return -1;
        }
        return 0;
    }

    public int porovnajPodlaMena(Pacient pacient) {
        if (pacient.getPriezvisko().compareToIgnoreCase(this.getPriezvisko()) > 0) {
            return 1;
        } else if (pacient.getPriezvisko().compareToIgnoreCase(this.getPriezvisko()) < 0) {
            return -1;
        } else if (pacient.getPriezvisko().compareToIgnoreCase(this.getPriezvisko()) == 0) {
            if (pacient.getMeno().compareToIgnoreCase(this.getMeno()) > 0) {
                return 1;
            } else if (pacient.getMeno().compareToIgnoreCase(this.getMeno()) < 0) {
                return -1;
            } else if (pacient.getMeno().compareToIgnoreCase(this.getMeno()) == 0) {
                if (pacient.getRodnecislo().compareToIgnoreCase(this.getRodnecislo()) > 0) {
                    return 1;
                } else if (pacient.getRodnecislo().compareToIgnoreCase(this.getRodnecislo()) < 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public String getMeno() {
        return meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public LocalDate getDatum_narodenia() {
        return datum_narodenia;
    }

    public int getKod_ZP() {
        return kod_ZP;
    }

    public ArrayList<Hospitalizacia> getHospitalizacie() {
        ArrayList<Hospitalizacia> hospitalizaciePacienta = new ArrayList<>();
        ArrayList<Vrchol> hospitalizaciePacientaVrcholy = new ArrayList<>();
        hospitalizaciePacientaVrcholy = hospitalizacie.inOrder();
        for (Vrchol vrchol : hospitalizaciePacientaVrcholy) {
            hospitalizaciePacienta.add((Hospitalizacia) vrchol.getData().dajTyp());
        }
        return hospitalizaciePacienta;
    }

    void pridajPacientoviHospitalizaciu(Hospitalizacia hosp) {
        hospitalizacie.vlozVrchol(new Vrchol(new HospPacientaPodlaDatumu(hosp)));
    }

    boolean ukonciPacientoviHospitalizaciu(LocalDate dat_konca, LocalDate dat_zac) {
        Vrchol vrchol = hospitalizacie.najdi(new Vrchol(new HospPacientaPodlaDatumu(new Hospitalizacia(dat_zac))));
        Hospitalizacia hosp = (Hospitalizacia) vrchol.getData().dajTyp();
        if (hosp.getDatum_kon_hosp() != null) {
            hosp.setDatum_kon_hosp(dat_konca);
            return true;
        } else {
            return false;
        }
    }

}

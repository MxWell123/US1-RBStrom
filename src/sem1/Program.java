/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem1;

import BVS.Generator;
import BVS.Vrchol;
import ObalovacieTriedy.HospNemocnicePodlaDatumuARC;
import ObalovacieTriedy.HospPacientaPodlaDatumu;
import ObalovacieTriedy.NemocnicaPodlaNazvu;
import ObalovacieTriedy.PacientPodlaMena;
import ObalovacieTriedy.PacientPodlaRC;
import ObalovacieTriedy.PoistovnePodlaCisla;
import RB.RB;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author davidecek
 */
public class Program {

    private RB nemocnice;
    private RB pacienti;
    private RB poistovne;
    private int id_hosp;

    public Program() {
        nemocnice = new RB();
        pacienti = new RB();
        poistovne = new RB();
        id_hosp = 0;
        generuj();
    }

    public void generuj() {
        for (int i = 0; i < 15; i++) {
            vlozNovuPoistovnu(i);
        }
    }

    public void vlozNovuPoistovnu(int cislo) {
        poistovne.vlozVrchol(new Vrchol(new PoistovnePodlaCisla(new Poistovna(cislo))));
    }

    public boolean vlozNovehoPacienta(String menoP, String priezvP, String rc, LocalDate date, int kodZP) {
        return pacienti.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient(menoP, priezvP, rc, date, kodZP))));
    }

    public void vlozNovehoPacientaNacitaj(String menoP, String priezvP, String rc, LocalDate date, int kodZP, LocalDate datum) {
        pacienti.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient(menoP, priezvP, rc, date, kodZP, datum))));
    }

    public ArrayList<Pacient> dajVsetkychPacientovNemocnice(String nazovNem) {
        Nemocnica nemocnica = najdiNemocnicu(nazovNem);
        ArrayList<Vrchol> pacientiA = null;
        ArrayList<Pacient> pacientiB = new ArrayList<>();
        pacientiA = nemocnica.dajHospitalizovanychPacientovNemocnice();
        for (Vrchol vrchol : pacientiA) {
            pacientiB.add((Pacient) vrchol.getData().dajTyp());
        }
        return pacientiB;
    }

    public void vlozNovuNemocnicu(String nazov) {
        nemocnice.vlozVrchol(new Vrchol(new NemocnicaPodlaNazvu(new Nemocnica(nazov))));
    }

    public ArrayList<Nemocnica> dajVsetkyNemocnice() {
        ArrayList<Vrchol> nemocniceA = null;
        ArrayList<Nemocnica> nemocniceB = new ArrayList<>();
        nemocniceA = nemocnice.inOrder();
        for (Vrchol vrchol : nemocniceA) {
            nemocniceB.add((Nemocnica) vrchol.getData().dajTyp());
        }
        return nemocniceB;
    }

    public Nemocnica najdiNemocnicu(String nazov) {
        Vrchol vrchol = nemocnice.najdi(new Vrchol(new NemocnicaPodlaNazvu(new Nemocnica(nazov))));
        return (Nemocnica) vrchol.getData().dajTyp();
    }

    public Poistovna najdiPoistovnu(int cislo) {
        Vrchol vrchol = poistovne.najdi(new Vrchol(new PoistovnePodlaCisla(new Poistovna(cislo))));
        return (Poistovna) vrchol.getData().dajTyp();
    }

    public ArrayList<Pacient> najdiPacientovNemocnice(String nazovNem) {
        Nemocnica nemocnica = najdiNemocnicu(nazovNem);
        ArrayList<Vrchol> pacienti = new ArrayList<>();
        pacienti = nemocnica.dajHospitalizovanychPacientovNemocnice();
        ArrayList<Pacient> vysledok = new ArrayList<>();
        for (Vrchol vrchol : pacienti) {
            vysledok.add((Pacient) vrchol.getData().dajTyp());
        }
        return vysledok;
    }

    public boolean zacniHospitalizaciuPacienta(String nazovNem, String rod_cisl, LocalDate dat_zac, String diagnoza) {
        Hospitalizacia hosp = new Hospitalizacia(rod_cisl, dat_zac, null, diagnoza, nazovNem);
        Nemocnica nemocnica = najdiNemocnicu(nazovNem);
        if (nemocnica.najdiHospitalizaciu(hosp)) {
            return false;
        }
        Pacient pacient = this.najdiPacienta(rod_cisl);
        pacient.setDatumZacatiaAktualnejHosp(dat_zac);
        nemocnica.pridajPacienta(pacient);
        nemocnica.zacniHospitalizaciu(hosp, pacient);
        pacient.pridajPacientoviHospitalizaciu(hosp);
        Poistovna poistovna = najdiPoistovnu(pacient.getKod_ZP());
        poistovna.pridajHospitalizaciu(hosp);
        return true;
    }

    private Pacient najdiPacienta(String rod_cisl) {
        Vrchol vrchol = pacienti.najdi(new Vrchol(new PacientPodlaRC(new Pacient(rod_cisl))));
        return (Pacient) vrchol.getData().dajTyp();
    }

    public Pacient najdiPacientaVNemocnici(String nazovNem, String rod_cislo) {
        Vrchol vrcholNem = nemocnice.najdi(new Vrchol(new NemocnicaPodlaNazvu(new Nemocnica(nazovNem))));
        Nemocnica nemocnica = (Nemocnica) vrcholNem.getData().dajTyp();
        Pacient pacient = nemocnica.najdiPacientaPodlaRC(rod_cislo);
        return pacient;
    }

    public boolean ukonciHospitalizaciuPacienta(String nazovNem, String rod_cisl, LocalDate dat_konca) {
        Nemocnica nemocnica = najdiNemocnicu(nazovNem);
        LocalDate dat_zac = nemocnica.vymazAktualneHospPacienta(rod_cisl, dat_konca);
        Pacient pac = nemocnica.najdiPacientaPodlaRC(rod_cisl);
        if (dat_zac == null) {
            return false;
        }
        boolean prem = pac.ukonciPacientoviHospitalizaciu(dat_konca, dat_zac);
        if (prem) {
            Poistovna poistovna = najdiPoistovnu(pac.getKod_ZP());
            poistovna.ukonciHospitalizaciu(dat_zac, rod_cisl, dat_konca);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Pacient> najdiPacientaVNemocniciPodlaMena(String nazovNem, String priezvisko, String meno) {
        ArrayList<Vrchol> pacientiVrcholy = new ArrayList<>();
        ArrayList<Pacient> pacienti = new ArrayList<>();
        Pacient pacient = null;
        Vrchol vrcholNem = nemocnice.najdi(new Vrchol(new NemocnicaPodlaNazvu(new Nemocnica(nazovNem))));
        Nemocnica nemocnica = (Nemocnica) vrcholNem.getData().dajTyp();
        pacientiVrcholy = nemocnica.najdiPacientaPodlaMena(meno, priezvisko);
        for (Vrchol vrchol : pacientiVrcholy) {
            pacient = (Pacient) vrchol.getData().dajTyp();
            pacienti.add(pacient);
        }
        return pacienti;
    }

    public ArrayList<Pacient> dajPacientovNemocniceDanejPoistovne(String nazovNem, int poistovna) {
        Nemocnica nemocnica = najdiNemocnicu(nazovNem);
        ArrayList<Vrchol> pacienti = new ArrayList<>();
        pacienti = nemocnica.dajHospitalizovanychPacientovDanejPoistovne(poistovna);
        ArrayList<Pacient> vysledok = new ArrayList<>();
        for (Vrchol vrchol : pacienti) {
            vysledok.add((Pacient) vrchol.getData().dajTyp());
        }
        return vysledok;
    }

    public void vymazNemocnicu(String nazovNem1, String nazovNem2) {
        Nemocnica nemocnica1 = najdiNemocnicu(nazovNem1);
        Nemocnica nemocnica2 = najdiNemocnicu(nazovNem2);
        ArrayList<Vrchol> pacientiAktHospit = new ArrayList<>();
        ArrayList<Vrchol> pacientiPodlaRC = new ArrayList<>();
        ArrayList<Vrchol> hospitalNemocnice = new ArrayList<>();
        pacientiAktHospit = nemocnica1.dajHospitalizovanychPacientovNemocnice();
        pacientiPodlaRC = nemocnica1.dajPacientovPodlaRC();
        hospitalNemocnice = nemocnica1.dajHospitalizacieNemocnice();

        for (Vrchol vrchol : hospitalNemocnice) {
            Hospitalizacia hosp = (Hospitalizacia) vrchol.getData().dajTyp();
//            Pacient pa = (Pacient) pacienti.najdi(new Vrchol(new PacientPodlaRC(new Pacient(hosp.getRc_pacienta())))).getData().dajTyp();            
//            Poistovna poistovna = (Poistovna) poistovne.najdi(new Vrchol(new PoistovnePodlaCisla(new Poistovna(pa.getKod_ZP())))).getData().dajTyp();            
//            Hospitalizacia h = (Hospitalizacia) poistovna.dajVsetkyHospitalizacie().najdi(new Vrchol(new HospNemocnicePodlaDatumuARC(new Hospitalizacia(hosp.getRc_pacienta(), hosp.getDatum_zac_hosp())))).getData().dajTyp();
//            h.setNemocnica(nazovNem2);
            hosp.setNemocnica(nazovNem2);
            nemocnica2.pridajHospitalNemocnice(new Vrchol(new HospPacientaPodlaDatumu(hosp)));
        }

        for (Vrchol vrchol : pacientiPodlaRC) {
            nemocnica2.pridajPacientaVrchol(vrchol);
        }
        for (Vrchol vrchol : pacientiAktHospit) {
            nemocnica2.pridajAktualneHospitPacienta(vrchol);

        }
        this.zrusNemocnicu(nemocnica1);

    }

    private void zrusNemocnicu(Nemocnica nemocnica1) {
        nemocnice.odstranVrchol(new Vrchol(new NemocnicaPodlaNazvu(nemocnica1)));
    }

    public ArrayList<Pacient> dajHospPacientovVIntervale(String nazovNem, LocalDate odDatum, LocalDate doDatum) {
        Nemocnica nemocnica = najdiNemocnicu(nazovNem);
        ArrayList<Pacient> pacientiAktHospit = null;
        pacientiAktHospit = nemocnica.dajVsetkyHospitalVIntervale(odDatum, doDatum);
        return pacientiAktHospit;
    }

    public void generujData() {
        Generator generator = new Generator();
        generator.generuj(this);
    }

    public LinkedList<String> dajPacientovVDniMesiaca(LocalDate date) {
        LinkedList<String> vypisPoistovne = new LinkedList<>();
        ArrayList<Vrchol> poistovneV = poistovne.inOrder();
        int pocetDniVMesiaci = date.lengthOfMonth();
        LocalDate datumOd = date.withDayOfMonth(1);
        LocalDate datumDo = date.withDayOfMonth(date.lengthOfMonth());
        LocalDate pomDatumOd = null;
        LocalDate pomDatumDo = null;
        StringBuilder builder = new StringBuilder(1000000000);
        for (Vrchol vrchol : poistovneV) {
            String[] dni = new String[pocetDniVMesiaci];
            int celkovyPocetDni = 0;
            float pocetDni = 0;
            int den;
            Vrchol pacientV = null;
            Pacient pacient = null;
            Poistovna poistovna = (Poistovna) vrchol.getData().dajTyp();
            ArrayList<Hospitalizacia> hospitalizacie = poistovna.dajVsetkyHospitalVIntervale(datumOd, datumDo);
            for (Hospitalizacia hosp : hospitalizacie) {
                if (hosp.getDatum_zac_hosp().isBefore(datumOd)) {
                    pomDatumOd = datumOd;
                } else {
                    pomDatumOd = hosp.getDatum_zac_hosp();
                }
                if (hosp.getDatum_kon_hosp() == null || hosp.getDatum_kon_hosp().isAfter(datumDo)) {
                    pomDatumDo = datumDo;
                } else {
                    pomDatumDo = hosp.getDatum_kon_hosp();
                }

                pocetDni = pomDatumDo.getDayOfMonth() - pomDatumOd.getDayOfMonth() + 1;
                den = pomDatumOd.getDayOfMonth();
                for (int i = den - 1; i < pocetDni - 1; i++) {
                    pacientV = pacienti.najdi(new Vrchol(new PacientPodlaRC(new Pacient(hosp.getRc_pacienta()))));
                    pacient = (Pacient) pacientV.getData().dajTyp();

                    if (dni[i] == null) {
                        dni[i] = i + 1 + "." + pomDatumOd.getMonth() + "." + pomDatumOd.getYear() + "\n\t\t" + pacient.getMeno() + "  " + pacient.getPriezvisko() + "  " + pacient.getRodnecislo() + "  " + pacient.getDatum_narodenia() + "  " + hosp.getDiagnoza() + "\n";
                    } else {
                        dni[i] += "\t\t" + pacient.getMeno() + "  " + pacient.getPriezvisko() + "  " + pacient.getRodnecislo() + "  " + pacient.getDatum_narodenia() + "  " + hosp.getDiagnoza() + "\n";;
                    }
                    celkovyPocetDni++;
                }

            }
            builder.append("Poistovna ").append(poistovna.getCislo()).append("\n");
            for (int i = 0; i < dni.length; i++) {
                if (dni[i] != null) {
                    builder.append(dni[i]);
                }
            }
            builder.append("Celkovy pocet hospitalizovanych dni: ").append(celkovyPocetDni).append("\n");
            vypisPoistovne.add(builder.toString());
            builder.setLength(0);
        }

        return vypisPoistovne;
    }

    RB dajNemocnice() {
        return nemocnice;
    }

    RB dajPacientov() {
        return pacienti;
    }

    RB dajPoistovne() {
        return poistovne;
    }

    void vlozNacitanuHospitalizaciu(int poist, String rc, LocalDate datum_zac, LocalDate datum_kon, String diagnoza, String nemocnica) {
        Hospitalizacia hosp = new Hospitalizacia(rc, datum_zac, datum_kon, diagnoza, nemocnica);
        Poistovna poistovna = najdiPoistovnu(poist);
        poistovna.pridajHospitalizaciu(hosp);
        Nemocnica nemocnica1 = najdiNemocnicu(nemocnica);
        Pacient pacient = najdiPacienta(rc);
        pacient.pridajPacientoviHospitalizaciu(hosp);
        nemocnica1.pridajPacienta(pacient);
        nemocnica1.pridajHospitalNemocnice(new Vrchol(new HospNemocnicePodlaDatumuARC(hosp)));
        if (datum_kon == null) {
            nemocnica1.pridajAktualneHospitPacienta(new Vrchol(new PacientPodlaRC(pacient)));
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem1;

import java.time.LocalDate;

/**
 *
 * @author davidecek
 */
public class Hospitalizacia {

    private String rc_pacienta;
    private LocalDate datum_zac_hosp;
    private LocalDate datum_kon_hosp;
    private String diagnoza;
    private String nemocnica;

    public Hospitalizacia(String rc_pacienta) {
        this.rc_pacienta = rc_pacienta;
    }

    public Hospitalizacia(LocalDate datum_zac_hosp) {
        this.datum_zac_hosp = datum_zac_hosp;
    }

    public Hospitalizacia(String rc_pacienta, LocalDate datum_zac_hosp) {
        this.rc_pacienta = rc_pacienta;
        this.datum_zac_hosp = datum_zac_hosp;
    }

    public Hospitalizacia(String rc_pacienta, LocalDate datum_zac_hosp, LocalDate datum_kon_hosp, String diagnoza, String nemocnica) {
        this.rc_pacienta = rc_pacienta;
        this.datum_zac_hosp = datum_zac_hosp;
        this.datum_kon_hosp = datum_kon_hosp;
        this.diagnoza = diagnoza;
        this.nemocnica = nemocnica;
    }

    public void setNemocnica(String nemocnica) {
        this.nemocnica = nemocnica;
    }

    public String getRc_pacienta() {
        return rc_pacienta;
    }

    public String getNemocnica() {
        return nemocnica;
    }

    public int porovnajPodlaRC(Hospitalizacia hosp) {
        if (hosp.getRc_pacienta().compareToIgnoreCase(this.getRc_pacienta()) > 0) {
            return 1;
        } else if (hosp.getRc_pacienta().compareToIgnoreCase(this.getRc_pacienta()) < 0) {
            return -1;
        }
        return 0;
    }

    public LocalDate getDatum_zac_hosp() {
        return datum_zac_hosp;
    }

    public void setDatum_zac_hosp(LocalDate datum_zac_hosp) {
        this.datum_zac_hosp = datum_zac_hosp;
    }

    public LocalDate getDatum_kon_hosp() {
        return datum_kon_hosp;
    }

    public void setDatum_kon_hosp(LocalDate datum_kon_hosp) {
        this.datum_kon_hosp = datum_kon_hosp;
    }

    public String getDiagnoza() {
        return diagnoza;
    }

    public void setDiagnoza(String diagnoza) {
        this.diagnoza = diagnoza;
    }

    public int porovnajPodlaDatumu(Hospitalizacia hospitalizacia) {
        if (hospitalizacia.getDatum_zac_hosp().isAfter(datum_zac_hosp)) {
            return 1;
        } else if (hospitalizacia.getDatum_zac_hosp().isBefore(datum_zac_hosp)) {
            return -1;
        } else if (hospitalizacia.getRc_pacienta().compareToIgnoreCase(rc_pacienta) == 0) {
            if (hospitalizacia.getRc_pacienta().compareToIgnoreCase(rc_pacienta) > 0) {
                return 1;
            } else if (hospitalizacia.getRc_pacienta().compareToIgnoreCase(rc_pacienta) < 0) {
                return -1;
            }
        }
        return 0;
    }

    public int porovnajIbaPodlaDatumu(Hospitalizacia hospitalizacia) {
        if (hospitalizacia.getDatum_zac_hosp().isAfter(datum_zac_hosp)) {
            return 1;
        } else if (hospitalizacia.getDatum_zac_hosp().isBefore(datum_zac_hosp)) {
            return -1;
        }
        return 0;
    }
}

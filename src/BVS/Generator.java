/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BVS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sem1.Program;

/**
 *
 * @author davidecek
 */
public class Generator {

    private Random rr;

    public Generator() {
        final java.util.Random rand = new java.util.Random();
        rr = new Random(10000);
    }

    public String generujString() {
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        final java.util.Random rand = new java.util.Random();

        final Set<String> identifiers = new HashSet<String>();

        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(1) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public LocalDate generujDatum() {

        int den = rr.nextInt(28) + 1;
        int mesiac = rr.nextInt(12) + 1;
        int rok = rr.nextInt((2017 - 1900) + 1) + 1950;
        LocalDate d = null;
        d = LocalDate.of(rok, mesiac, den);

        return d;
    }

    public String generujRC() {
        int prve = rr.nextInt(899999) + 100000;
        int druhe = rr.nextInt(8999) + 1000;
        return prve + "/" + druhe;
    }

    public void generuj(Program program) {

        LocalDate ld = null;
        rr = new Random(10000);
        int pocetNemocnic = 10000;
        int pocetPacientov = 30000;
        String rc = null;
        String nemocnica = null;
        LocalDate datum = null;
        boolean prem = false;
        int pomocna = 0;
        int pom = 0;
        int pocetHospitalizacii = 1;
        for (int i = 0; i < pocetNemocnic; i++) {
            pom = i;
            program.vlozNovuNemocnicu("" + pom);
        }
        for (int i = 0; i < 15; i++) {
            program.vlozNovuPoistovnu(i);
        }
        for (int i = 0; i < pocetPacientov; i++) {
            rc = generujRC();
            nemocnica = "" + rr.nextInt(pocetNemocnic);
            prem = program.vlozNovehoPacienta(generujString(), generujString(), rc, generujDatum(), rr.nextInt(15));
            if (prem) {
                ld = generujDatum();
                for (int j = 0; j < pocetHospitalizacii; j++) {
                    if (program.zacniHospitalizaciuPacienta(nemocnica, rc, ld.plusDays(pomocna), generujString())) {
                        program.ukonciHospitalizaciuPacienta(nemocnica, rc, ld.plusDays(rr.nextInt(300) + 1));
                    }                    
                    pomocna = rr.nextInt(5000) + 1;
                }
                if (rr.nextDouble() < 0.5) {
                    program.zacniHospitalizaciuPacienta(nemocnica, rc, ld.plusDays(pomocna), generujString());
                }
            }
        }

    }

}

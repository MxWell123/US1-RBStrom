/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BVS;

import RB.RB;
import java.util.ArrayList;
import java.util.Random;
import BVS.Vrchol;
import ObalovacieTriedy.PacientPodlaRC;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import sem1.Pacient;

/**
 *
 * @author davidecek
 */
public class Testy {

    /**
     * @param args the command line arguments
     */
    private Vrchol najdeny;

    public static void main(String[] args) {

//        Splay strom = new Splay();
//        BVS bvs = new BVS();
//        LocalDate date = LocalDate.of(2016, 5, 1);
//        
//        System.out.println(date.compareTo(LocalDate.now()));
//        ArrayList<Integer> cisla = new ArrayList<>(50000);
//        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(50000);
//        int z = 0;
//        strom.vypis(strom.getKoren());
//        System.out.println("---------------------");
//
//        Collections.sort(cisla, new CustomComparator<Integer>());
//        hashSet.addAll(cisla);
//        StringBuilder builder = new StringBuilder(10000000);
//        for (Integer integer : hashSet) {
//            builder.append(integer);
//        }
        //strom.vypis(strom.getKoren());
//        String lala = strom.vypisStromInOrder();
//        System.out.println(builder.toString().equals(lala));
//
//        StringBuilder builder2 = new StringBuilder(10000000);
//
//        for (int i = 0; i < 1000000; i++) {
//            z = rr.nextInt(1000000);
//            strom.vymaz(new Vrchol(new KnihaPodlaKluca(new Kniha(z))));
//            hashSet.remove(z);
//        }
//        for (Integer integer : hashSet) {
//            builder2.append(integer);
//        }
//
//        System.out.println(builder2.toString().equals(strom.vypisStromInOrder()));
//        
        // strom.vypis(strom.getKoren());
//        strom.vloz(new Vrchol(new KnihaPodlaKluca(new Kniha(12))));
//        strom.vloz(new Vrchol(new KnihaPodlaKluca(new Kniha(24))));
//        strom.vloz(new Vrchol(new KnihaPodlaKluca(new Kniha(15))));
//        strom.vloz(new Vrchol(new KnihaPodlaKluca(new Kniha(78))));
//        strom.vloz(new Vrchol(new KnihaPodlaKluca(new Kniha(25))));        
//        
//        strom.najdiVrchol(new Vrchol(new KnihaPodlaKluca(new Kniha(12))));
//      
        //strom.sPlay(new Vrchol(new Data(12)));
        //strom.najdiVrchol(new Vrchol(new Data(45)));
//        strom.vymazVrchol(new Vrchol(new Data(10)));
//        strom.vymazVrchol(new Vrchol(new Data(100)));
//        strom.vymazVrchol(new Vrchol(new Data(1)));
//        strom.vymazVrchol(new Vrchol(new Data(12)));
//        strom.vymazVrchol(new Vrchol(new Data(8)));
//        strom.vymazVrchol(new Vrchol(new Data(7)));
//        strom.vymazVrchol(new Vrchol(new Data(42)));
//        strom.vymazVrchol(new Vrchol(new Data(35)));
        //strom.vypis(strom.getKoren());
//        RB rb = new RB();
//        Generator gen = new Generator();
//        ArrayList<String> arrayList = new ArrayList<String>();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date d = null;
//
//        try {
//            d = sdf.parse(21 + "/" + 10 + "/" + 1992);
//        } catch (ParseException ex) {
//            Logger.getLogger(Testy.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String rodne_cislo = gen.generujString();
//        for (int i = 0; i < 100; i++) {
//            rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", rodne_cislo, d, 25))));
//            arrayList.add(rodne_cislo);
//        }
//        Random rr = new Random(arrayList.size());
//        ArrayList<Integer> pocetCiernych = new ArrayList<Integer>();
//        int counter = 1;
//        String pom = "";
//        Vrchol pomVrchol = null;
//        for (int i = 0; i < arrayList.size(); i++) {
//            rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient(arrayList.get(rr.nextInt(arrayList.size()))))));
//            pom = arrayList.get(i);
//            pomVrchol = rb.najdi(new Vrchol(new PacientPodlaRC(new Pacient(pom))));
//            if (pomVrchol != null) {
//                if (pomVrchol.getLavy() == null && pomVrchol.getPravy() == null) {
//                    while (pomVrchol != rb.getKoren()) {
//                        if (pomVrchol.getFarba() == true) {
//                            counter++;
//                        }
//                        pomVrchol = pomVrchol.getOtec();
//                    }
////                    pocetCiernych.add(counter);
//                    counter = 1;
//                }
//            }
//        }
//        System.out.println("--------------------------------");
//        rb.vypis(rb.getKoren());
//        System.out.println("--------------------------------");
//        for (Integer integer : pocetCiernych) {
//            System.out.print(integer);
//        }
////
        System.out.println("");
        Testy test = new Testy();
        test.testDelete();
//        Testy test = new Testy();
//        boolean kontrola = false;
//        RB rb = new RB();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date d = null;
//        try {
//            d = sdf.parse(21 + "/" + 10 + "/" + 1992);
//        } catch (ParseException ex) {
//            Logger.getLogger(Testy.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "Q", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "F", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "Z", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "BX", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "ZA", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "X", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "TD", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "AF", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "AX", d, 25))));
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "IQ", d, 25))));        
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "A", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "UJ", d, 25))));        
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "QS", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "P", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "JB", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "JE", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "B", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "HU", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "H", d, 25))));      
//        rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", "K", d, 25))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("P"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb);
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("Q"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb);  
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("HU"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb); 
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("A"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb); 
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("F"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb); 
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("BX"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb);  
//        rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient("MG"))));
//        rb.vypis(rb.getKoren());
//        System.out.println("-------------------------------------------------");
//        test.kontrola(rb);
    }

    public boolean testInsert() {
        RB strom = new RB();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Integer> pocetCiernych = new ArrayList<Integer>();
        boolean kontrola = false;
        String pom = "";
        Vrchol pomVrchol = null;
        Generator gen = new Generator();
        Random rr = new Random(100);
        int counter = 1;
        for (int j = 0; j < 1000000; j++) {
            String rodne_cislo = gen.generujString();
            kontrola = strom.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient(gen.generujString(), gen.generujString(), rodne_cislo, gen.generujDatum(), rr.nextInt(120)))));
            if (kontrola) {
                arrayList.add(rodne_cislo);
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            pom = arrayList.get(i);
            pomVrchol = strom.najdi(new Vrchol(new PacientPodlaRC(new Pacient(pom))));
            if (pomVrchol == null) {
                return false;
            }
            if (pomVrchol.getLavy() == null && pomVrchol.getPravy() == null) {
                while (pomVrchol != strom.getKoren()) {
                    if (pomVrchol.getFarba() == true) {
                        counter++;
                    }
                    pomVrchol = pomVrchol.getOtec();
                }
                pocetCiernych.add(counter);
                counter = 1;
            }
        }
        for (Integer integer : pocetCiernych) {
            System.out.println(integer);
        }
        System.out.println("");
        return true;
    }

    public void testDelete() {
        Random rr = new Random(10);
        int pocetVrcholov = 50000;
        int pocetVlozenych = 0;
        RB rb = new RB();
        String pom = null;
        boolean kontrola = false;
        Vrchol pomVrchol = null;
        Generator gen = new Generator();
        ArrayList<String> arrayList = new ArrayList<String>();
        int random = 0;

        LocalDate d = LocalDate.of(1992, 10, 21);

        String rodne_cislo = null;
        for (int i = 0; i < pocetVrcholov; i++) {
            rodne_cislo = gen.generujString();
            kontrola = rb.vlozVrchol(new Vrchol(new PacientPodlaRC(new Pacient("dsadsada", "dsadasdasd", rodne_cislo, d, 25))));
            if (kontrola) {
                arrayList.add(rodne_cislo);
                pocetVlozenych++;
            }
        }

        pocetVrcholov = pocetVlozenych;
        for (int i = 0; i < pocetVlozenych / 2; i++) {
            random = rr.nextInt(pocetVrcholov);
            pom = arrayList.get(random);
            pomVrchol = rb.najdi(new Vrchol(new PacientPodlaRC(new Pacient(pom))));
            if (rb.odstranVrchol(new Vrchol(new PacientPodlaRC(new Pacient(pom)))) == false) {
                System.out.println("error");
                return;
            }
            arrayList.remove(random);
            pocetVrcholov--;
            kontrola(rb);
        }
        System.out.println("testDelete uspesny");
    }

    public boolean kontrola(RB rb) {
        ArrayList<Vrchol> arrayList2 = new ArrayList<>();
        arrayList2 = rb.inOrder();
        int counter = 0;
        int pocetCiernych = 0;
        int iterator = 0;
        for (Vrchol pomVrchol : arrayList2) {
            if (pomVrchol.getLavy() == null && pomVrchol.getPravy() == null) {
                while (pomVrchol != rb.getKoren()) {
                    if (pomVrchol.getFarba() == true) {
                        counter++;
                    }
                    pomVrchol = pomVrchol.getOtec();
                }
                if (iterator == 0) {
                    pocetCiernych = counter;
                    iterator++;
                } else if (pocetCiernych != counter) {
                    System.out.println("Zly pocet ciernych");
                    return false;
                }
                counter = 0;
            }
        }
        //System.out.println("Pocet ciernych rovnaky");
        return true;
    }
}

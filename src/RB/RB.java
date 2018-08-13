/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RB;

import BVS.Vrchol;
import ObalovacieTriedy.HospNemocnicePodlaDatumuARC;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import sem1.Hospitalizacia;
import sem1.Pacient;

/**
 *
 * @author davidecek
 */
public class RB extends BVS.BVS {

    public RB() {
        super();
    }

    public boolean vlozVrchol(Vrchol vkladany) {
        boolean pom = super.vlozVrchol(vkladany);
        if (!pom) {
            return false;
        }
        Vrchol praotec = null;
        if (vkladany.getData().porovnaj(this.getKoren().getData()) == 0) {
            vkladany.zamaluj(true);
            return true;
        } else {
            vkladany.zamaluj(false);
        }
        // ak je vkladany syn korena
        if (vkladany.getOtec().getData().porovnaj(this.getKoren().getData()) == 0) {
            return true;
        }
        this.kontrola(vkladany);
        return true;
    }

    public void kontrola(Vrchol vkladany) {
        while (vkladany.getData().porovnaj(this.getKoren().getData()) != 0) {
            if (vkladany.getFarba() == false && vkladany.getOtec().getFarba() == false) {
                Vrchol praotec = vkladany.getOtec().getOtec();
                if (praotec.getLavy() != null) {
                    if (vkladany.getOtec().getData().porovnaj(praotec.getLavy().getData()) == 0) {
                        if (praotec.getPravy() == null || praotec.getPravy() == null || praotec.getLavy().getFarba() == true) {//s
                            restrukturalizacia(vkladany);
                            return;
                        } else if (praotec.getPravy().getFarba() == false) {
                            prefarbi(vkladany);
                            kontrola(vkladany);
                            return;
                        }
                    }
                }

                if (praotec.getPravy() != null) {
                    if (vkladany.getOtec().getData().porovnaj(praotec.getPravy().getData()) == 0) {
                        if (praotec.getPravy() == null || praotec.getLavy() == null || praotec.getLavy().getFarba() == true) {
                            restrukturalizacia(vkladany);
                            return;
                        } else if (praotec.getLavy().getFarba() == false) {
                            prefarbi(vkladany);
                            kontrola(vkladany);
                            return;
                        }
                    }
                }
                restrukturalizacia(vkladany);
            } else {
                vkladany = vkladany.getOtec();
            }
        }

    }

    public boolean restrukturalizacia(Vrchol vkladany) {

        Vrchol otec = vkladany.getOtec();
        Vrchol praotec = otec.getOtec();
        // vkladany nie je koren a jeho otec je cerveny (dva cervene za sebou)
        if (vkladany.getData().porovnaj(this.getKoren().getData()) != 0 && otec.getFarba() == false) {
            //pripad 1 z pravej strany lavy syn praotca je cierny (dvaja pravy synovia)
            if (vkladany.getData().porovnaj(otec.getData()) < 0 && otec.getData().porovnaj(praotec.getData()) < 0) {
                jednoduchaLavaRotacia(otec);
                praotec.setFarba(false);
                otec.setFarba(true);
                return true;
                // pripad 1 z lavej strany pravy syn praotca je cierny (dvaja lavy synovia)
            } else if (vkladany.getData().porovnaj(otec.getData()) > 0 && otec.getData().porovnaj(praotec.getData()) > 0) {
                jednoduchaPravaRotacia(otec);
                praotec.setFarba(false);
                otec.setFarba(true);
                return true;
                // pripad 1  vkladany ja pravy syn otca a otec je lavy syn praotca a pravy syn praotca je cierny  
            } else if (vkladany.getData().porovnaj(otec.getData()) < 0 && otec.getData().porovnaj(praotec.getData()) > 0) {
                jednoduchaLavaRotacia(vkladany);
                jednoduchaPravaRotacia(vkladany);
                praotec.setFarba(false);
                vkladany.setFarba(true);
                return true;
                // pripad 1 vkladany je lavy syn otca a otec je pravy syn praotca a lavy syn praotca je cierny
            } else if (vkladany.getData().porovnaj(otec.getData()) > 0 && otec.getData().porovnaj(praotec.getData()) < 0) {
                jednoduchaPravaRotacia(vkladany);
                jednoduchaLavaRotacia(vkladany);
                praotec.setFarba(false);
                vkladany.setFarba(true);
                return true;
            }
        }
        return false;
    }

    public void jednoduchaLavaRotacia(Vrchol vrchol) {
        Vrchol nLavy = vrchol.getOtec();
        Vrchol nPravy = null;
        if (vrchol.getLavy() != null) {
            nPravy = vrchol.getLavy();
            nLavy.setPravy(nPravy);
            nPravy.setOtec(nLavy);
        } else {
            nLavy.setPravy(null);
        }
        if (nLavy.getOtec() != null) {
            if (vrchol.getData().porovnaj(nLavy.getOtec().getData()) < 0) {
                nLavy.getOtec().setPravy(vrchol);
                vrchol.setOtec(nLavy.getOtec());
            } else if (vrchol.getData().porovnaj(nLavy.getOtec().getData()) > 0) {
                nLavy.getOtec().setLavy(vrchol);
                vrchol.setOtec(nLavy.getOtec());
            }
        } else {
            vrchol.setOtec(null);
            this.setKoren(vrchol);
        }
        vrchol.setLavy(nLavy);
        nLavy.setOtec(vrchol);
    }

    public void jednoduchaPravaRotacia(Vrchol vrchol) {
        Vrchol nPravy = vrchol.getOtec();
        Vrchol nLavy = null;
        if (vrchol.getPravy() != null) {
            nLavy = vrchol.getPravy();
            nPravy.setLavy(nLavy);
            nLavy.setOtec(nPravy);
        } else {
            nPravy.setLavy(null);
        }
        if (nPravy.getOtec() != null) {
            if (vrchol.getData().porovnaj(nPravy.getOtec().getData()) < 0) {
                nPravy.getOtec().setPravy(vrchol);
                vrchol.setOtec(nPravy.getOtec());
            } else if (vrchol.getData().porovnaj(nPravy.getOtec().getData()) > 0) {
                nPravy.getOtec().setLavy(vrchol);
                vrchol.setOtec(nPravy.getOtec());
            }
        } else {
            vrchol.setOtec(null);
            this.setKoren(vrchol);
        }
        vrchol.setPravy(nPravy);
        nPravy.setOtec(vrchol);
    }

    private void prefarbi(Vrchol pom) {
        Vrchol praotec = pom.getOtec().getOtec();
        if (praotec.getData().porovnaj(this.getKoren().getData()) != 0) {
            praotec.zamaluj(false);
            praotec.getLavy().zamaluj(true);
            praotec.getPravy().zamaluj(true);
        } else {
            praotec.getLavy().zamaluj(true);
            praotec.getPravy().zamaluj(true);
        }
    }

    public boolean odstranVrchol(Vrchol vrchol) {
        Vrchol bratP = null;
        Vrchol bratL = null;
        Vrchol vymazavany = null;
        Vrchol otec = null;
        Vrchol najdeny = null;
        Vrchol novyVrchol = null;
        Vrchol pom2 = null;
        Boolean farba = false;
        if (najdi(vrchol) == null) {
            return false;
        }
        najdeny = najdi(vrchol);
        farba = najdeny.getFarba();

        //ak je samostatnym korenom
        if (najdeny.getData().porovnaj(this.getKoren().getData()) == 0 && najdeny.getLavy() == null && najdeny.getPravy() == null) {
            this.setKoren(null);
            return false;
        }
        //ak nema synov a je cerveny
        if (najdeny.getLavy() == null && najdeny.getPravy() == null && farba == false) {
            vymaz(najdeny);
            return true;
        }
        // ak nema praveho syna (pretoze hladam z pravej strany najlavejsi) a je cerveny
        if (najdeny.getPravy() == null && najdeny.getFarba() == false) {
            vymaz(najdeny);
            return true;
        }
        vymaz(najdeny);
        vymazavany = this.getDoubleBlack(); // vymazavany list 
        novyVrchol = this.najdi(vymazavany);
        pom2 = vymazavany.getOtec();
        if (pom2 == null || (pom2.getData().porovnaj(najdeny.getData()) == 0)) { // vymazavany list je hned pravy syn mazaneho vrcholu
            bratP = novyVrchol.getPravy();
            bratL = novyVrchol.getLavy();
        } else {
            bratP = pom2.getPravy();
            bratL = pom2.getLavy();
        }
        if (novyVrchol == null) { // to znamena ze nema synov a je cierny
            if (bratL == null && bratP == null) { // nema brata a je cierny
                pom2.setFarba(true);
                return true;
            } else {
                otec = this.najdi(pom2);
                kontrolaDelete(otec, bratP, bratL);
                return true;
            }
        }
        if (vymazavany.getFarba() == false) { // ak je cerveny
            novyVrchol.setFarba(farba);
            return true;
        } else if (novyVrchol.getLavy() == null && novyVrchol.getPravy() == null) {
            novyVrchol.setFarba(farba);
            kontrolaDelete(novyVrchol, bratP, bratL);
            return true;
        } else if (vymazavany.getFarba() == true) {
            if (pom2.getData().porovnaj(this.getKoren().getData()) != 0 && pom2.getOtec() == null) {
                pom2.setOtec(novyVrchol.getOtec());
            }
            if (pom2.getData().porovnaj(najdeny.getData()) == 0) {
                novyVrchol.setFarba(farba);
                kontrolaDelete(novyVrchol, bratP, bratL);
                return true;
            }
            novyVrchol.setFarba(farba);
            kontrolaDelete(pom2, bratP, bratL);
            return true;
        }

        return false;
    }

    private void kontrolaDelete(Vrchol pom, Vrchol bratP, Vrchol bratL) {
        if (this.isJeLavy()) { // ak prvok je lavy syn otca
            restrukturalizaciaZlavejStrany(pom, bratP);
        } else { // ak je pravy syn
            restrukturalizaciaZPravejStrany(pom, bratL);
        }
    }

    private void restrukturalizaciaZlavejStrany(Vrchol otec, Vrchol brat) {

        // ak brat je cierny a obaja synovia su cierny alebo vrchol nema synov
        if (bratCiernyBezDeti(brat) || bratCiernyAJehoDetiCierne(brat)) {
            if (brat.getFarba() == true) {
                brat.setFarba(false);
                if (otec.getOtec() == null) {
                    return;
                }
                if (otec.getFarba() == true) {
                    otec = otec.getOtec();
                    if (brat.getOtec().getData().porovnaj(otec.getLavy().getData()) == 0) {
                        brat = otec.getPravy();
                        this.setJeLavy(true);
                        kontrolaDelete(otec, brat, null);
                        return;
                    } else {
                        brat = otec.getLavy();
                        this.setJeLavy(false);
                        kontrolaDelete(otec, null, brat);
                        return;
                    }
                } else {
                    otec.setFarba(true);
                    return;
                }
            }
        }
        //ak brat je cierny a pravy syn je cerveny
        if (brat.getPravy() != null) {
            if (brat.getFarba() == true && brat.getPravy().getFarba() == false) {
                brat.getPravy().setFarba(true);
                brat.setFarba(otec.getFarba());
                otec.setFarba(true);
                jednoduchaLavaRotacia(brat);
                return;
            }
        }
        // brat odstraneneho vrchol je cierny pravy syn cierny lavy cerveny
        if (brat.getLavy() != null) {
            if (brat.getFarba() == true && brat.getLavy().getFarba() == false) {
                brat.setFarba(false);
                brat.getLavy().setFarba(true);
                jednoduchaPravaRotacia(brat.getLavy());
                brat = otec.getPravy();
                brat.getPravy().setFarba(true);
                brat.setFarba(otec.getFarba());
                otec.setFarba(true);
                jednoduchaLavaRotacia(brat);
                return;
            }
        }
        // brat odstraneneho vrcholu je cerveny 

        if (brat.getFarba() == false) {
            otec.setFarba(false);
            brat.setFarba(true);
            jednoduchaLavaRotacia(brat);
            kontrolaDelete(otec, otec.getPravy(), null);
            return;
        }
    }

    private void restrukturalizaciaZPravejStrany(Vrchol otec, Vrchol brat) {

        // ak brat je cierny a obaja synovia su cierny alebo vrchol nema synov
        if (bratCiernyBezDeti(brat) || bratCiernyAJehoDetiCierne(brat)) {
            if (brat.getFarba() == true) {
                brat.setFarba(false);
                if (otec.getOtec() == null) {
                    return;
                }
                if (otec.getFarba() == true) {
                    otec = otec.getOtec();
                    if (brat.getOtec().getData().porovnaj(otec.getLavy().getData()) == 0) {
                        brat = otec.getPravy();
                        this.setJeLavy(true);
                        kontrolaDelete(otec, brat, null);
                        return;
                    } else {
                        brat = otec.getLavy();
                        this.setJeLavy(false);
                        kontrolaDelete(otec, null, brat);
                        return;
                    }
                } else {
                    otec.setFarba(true);
                    return;
                }
            }
        }
        //ak brat je cierny a lavy syn je cerveny
        if (brat.getLavy() != null) {
            if (brat.getFarba() == true && brat.getLavy().getFarba() == false) {
                brat.getLavy().setFarba(true);
                brat.setFarba(otec.getFarba());
                otec.setFarba(true);
                jednoduchaPravaRotacia(brat);
                return;
            }
        }

        // brat odstraneneho vrchol je cierny lavy syn cierny pravy cerveny
        if (brat.getPravy() != null) {
            if (brat.getLavy() == null || brat.getLavy().getFarba() == true) {
                if (brat.getFarba() == true && brat.getPravy().getFarba() == false) {
                    brat.setFarba(false);
                    brat.getPravy().setFarba(true);
                    jednoduchaLavaRotacia(brat.getPravy());
                    brat = otec.getLavy();
                    brat.getLavy().setFarba(true);
                    brat.setFarba(otec.getFarba());
                    otec.setFarba(true);
                    jednoduchaPravaRotacia(brat);
                    return;
                }
            }
        }
        // brat odstraneneho vrcholu je cerveny 
        if (brat.getFarba() == false) {
            otec.setFarba(false);
            brat.setFarba(true);
            jednoduchaPravaRotacia(brat);
            kontrolaDelete(otec, null, otec.getLavy());
            return;
        }
    }

    public ArrayList<Vrchol> inOrder() {
        Vrchol aktualny = getKoren();
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();
        String vyslednyRetazec = "";
        StringBuilder stringBuilder = new StringBuilder(vyslednyRetazec);
        while (!zasobnikVrcholov.empty() || aktualny != null) {
            if (aktualny != null) {
                zasobnikVrcholov.push(aktualny);
                aktualny = aktualny.getLavy();
            } else {
                aktualny = zasobnikVrcholov.pop();
                vrcholy.add(aktualny);
                aktualny = aktualny.getPravy();
            }
        }
        return vrcholy;
    }

    public ArrayList<Vrchol> inOrder(Object object) {
        Vrchol aktualny = getKoren();
        boolean najdeny = false;
        Pacient pacient = null;
        String meno = null;
        String priezvisko = null;
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();
        if (object instanceof Pacient) {
            priezvisko = ((Pacient) object).getPriezvisko();
            meno = ((Pacient) object).getMeno();
        }
        while (!zasobnikVrcholov.empty() || aktualny != null) {
            if (aktualny != null) {
                zasobnikVrcholov.push(aktualny);
                aktualny = aktualny.getLavy();
            } else {
                aktualny = zasobnikVrcholov.pop();
                pacient = (Pacient) aktualny.getData().dajTyp();
                if (pacient.getMeno().equals(meno) && pacient.getPriezvisko().equals(priezvisko)) {
                    vrcholy.add(aktualny);
                    najdeny = true;
                } else if (!pacient.getMeno().equals(meno) && pacient.getPriezvisko().equals(priezvisko) && najdeny) {
                    break;
                }
                aktualny = aktualny.getPravy();
            }
        }
        return vrcholy;
    }

    public ArrayList<Vrchol> inOrderPoistovne(int poistovna) {
        Vrchol aktualny = getKoren();
        boolean najdeny = false;
        Pacient pacient = null;
        String meno = null;
        String priezvisko = null;
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();

        while (!zasobnikVrcholov.empty() || aktualny != null) {
            if (aktualny != null) {
                zasobnikVrcholov.push(aktualny);
                aktualny = aktualny.getLavy();
            } else {
                aktualny = zasobnikVrcholov.pop();
                pacient = (Pacient) aktualny.getData().dajTyp();
                if (pacient.getKod_ZP() == poistovna) {
                    vrcholy.add(aktualny);
                }
                aktualny = aktualny.getPravy();
            }
        }
        return vrcholy;
    }

    private boolean bratCiernyBezDeti(Vrchol brat) {
        return brat.getFarba() == true && brat.getLavy() == null && brat.getPravy() == null;
    }

    private boolean bratCiernyAJehoDetiCierne(Vrchol brat) {
        return brat.getFarba() == true
                && (brat.getLavy() != null && brat.getLavy().getFarba() == true)
                && (brat.getPravy() != null && brat.getPravy().getFarba() == true);
    }

    public RB inOrder(LocalDate odDatum, LocalDate doDatum) {
        Vrchol aktualny = getKoren();
        boolean najdeny = false;
        Hospitalizacia hosp = null;
        String meno = null;
        String priezvisko = null;
        RB vrcholy = new RB();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();

        while (!zasobnikVrcholov.empty() || aktualny != null) {
            if (aktualny != null) {
                zasobnikVrcholov.push(aktualny);
                aktualny = aktualny.getLavy();
            } else {
                aktualny = zasobnikVrcholov.pop();
                hosp = (Hospitalizacia) aktualny.getData().dajTyp();
                if (hosp.getDatum_zac_hosp().isAfter(doDatum)) {
                    break;
                }
                if (hosp.getDatum_zac_hosp().isBefore(doDatum)) {
                    if (hosp.getDatum_kon_hosp() == null || hosp.getDatum_kon_hosp().isAfter(odDatum)) {
                        vrcholy.vlozVrchol(new Vrchol(new HospNemocnicePodlaDatumuARC(hosp)));
                    }
                }
                aktualny = aktualny.getPravy();
            }
        }
        return vrcholy;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Splay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import BVS.BVS;
import BVS.Vrchol;

/**
 *
 * @author davidecek
 */
public class Splay extends BVS {

    public Splay() {
        super();
    }

    public boolean sPlay(Vrchol vrcholD) {
        Vrchol vrchol = najdi(vrcholD);
        Vrchol druhyLavySyn = null;
        Vrchol druhyPravySyn = null;
        Vrchol prvyPravySyn = null;
        Vrchol prvyLavySyn = null;
        Vrchol otec = null;
        Vrchol praOtec = null;
        Vrchol napojenie = null;

        if (vrchol == null) {
        } else {
            while (vrchol.getOtec() != null) {
                if (vrchol.getOtec() != null && vrchol.getOtec().getOtec() != null) {
                    otec = vrchol.getOtec();
                    praOtec = otec.getOtec();

                } else {
                    praOtec = null;
                }
                if (vrchol.getOtec() != null && vrchol.getOtec().getOtec() != null && vrchol.getOtec().getOtec().getOtec() != null) {
                    napojenie = praOtec.getOtec();
                } else {
                    napojenie = null;
                }
                //ak je vrchol v lavom podstrome
                if (vrchol.getData().porovnaj(vrchol.getOtec().getData()) > 0) {
                    // ak nema prarodica jeho rodic je teda korenom stromu
                    if (vrchol.getOtec().getOtec() == null) {
                        otec = vrchol.getOtec();
                        druhyLavySyn = vrchol.getPravy();
                        prvyPravySyn = vrchol.getOtec();
                        druhyPravySyn = vrchol.getOtec().getPravy();
                        vrchol.setOtec(null);
                        vrchol.setPravy(prvyPravySyn);
                        prvyPravySyn.setOtec(vrchol);

                        if (druhyPravySyn != null) {
                            prvyPravySyn.setPravy(druhyPravySyn);
                            druhyPravySyn.setOtec(prvyPravySyn);
                        } else {
                            prvyPravySyn.setPravy(null);
                        }
                        if (druhyLavySyn != null) {
                            prvyPravySyn.setLavy(druhyLavySyn);
                            druhyLavySyn.setOtec(prvyPravySyn);
                        } else {
                            prvyPravySyn.setLavy(null);
                        }
                        setKoren(vrchol);
                        break;
                    }

                    //vrchol je lavy syn a jeho otec je pravym synom 
                    if (praOtec != null && praOtec.getPravy() != null && praOtec.getPravy().getLavy() != null && vrchol.getData().porovnaj(praOtec.getPravy().getLavy().getData()) == 0) {
                        //1.krok 
                        otec = vrchol.getOtec();
                        druhyLavySyn = vrchol.getPravy();
                        napojenie = praOtec.getOtec();
                        // prvy pravy syn od M
                        praOtec.setPravy(vrchol);
                        vrchol.setOtec(praOtec);
                        //druhy pravy syn 
                        vrchol.setPravy(otec);
                        otec.setOtec(vrchol);
                        if (druhyLavySyn != null) {
                            otec.setLavy(druhyLavySyn);
                            druhyLavySyn.setOtec(otec);
                        } else {
                            otec.setLavy(null);
                        }

                        //2.krok
                        druhyPravySyn = vrchol.getLavy();
                        // lavy syn vrchola
                        vrchol.setLavy(praOtec);
                        praOtec.setOtec(vrchol);
                        //druhy pravy syn
                        if (druhyPravySyn != null) {
                            praOtec.setPravy(druhyPravySyn);
                            druhyPravySyn.setOtec(praOtec);
                        } else {
                            praOtec.setPravy(null);
                        }
                        if (napojenie != null) {
                            if (napojenie.getLavy() != null && napojenie.getLavy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setLavy(vrchol);
                            } else if (napojenie.getPravy() != null && napojenie.getPravy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setPravy(vrchol);
                            }
                            vrchol.setOtec(napojenie);
                        } else {
                            vrchol.setOtec(null);
                            setKoren(vrchol);
                        }

                    }

                    // vrchol a aj jeho rodic su obaja lavymi synmi
                    if (praOtec != null && praOtec.getLavy() != null && praOtec.getLavy().getLavy() != null && vrchol.getData().porovnaj(praOtec.getLavy().getLavy().getData()) == 0) {
                        //prvy krok
                        prvyPravySyn = praOtec;
                        druhyLavySyn = vrchol.getOtec().getPravy();
                        otec = vrchol.getOtec();
                        // pravy syn
                        otec.setPravy(prvyPravySyn);
                        prvyPravySyn.setOtec(otec);
                        // druhy lavy syn               
                        if (druhyLavySyn != null) {
                            prvyPravySyn.setLavy(druhyLavySyn);
                            druhyLavySyn.setOtec(prvyPravySyn);
                        } else {
                            prvyPravySyn.setLavy(null);
                        }
                        // napojenie na predchadzajuci strom
                        if (napojenie != null) {
                            if (napojenie.getLavy() != null && napojenie.getLavy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setLavy(otec);
                            } else if (napojenie.getPravy() != null && napojenie.getPravy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setPravy(otec);
                            }
                            otec.setOtec(napojenie);
                        } else {
                            otec.setOtec(null);
                            setKoren(otec);
                        }
                        //druhy krok
                        prvyPravySyn = otec;
                        druhyLavySyn = vrchol.getPravy();
                        //prvy pravy syn
                        vrchol.setPravy(prvyPravySyn);
                        prvyPravySyn.setOtec(vrchol);
                        //druhy pravy syn
                        if (druhyLavySyn != null) {
                            prvyPravySyn.setLavy(druhyLavySyn);
                            druhyLavySyn.setOtec(prvyPravySyn);
                        } else {
                            prvyPravySyn.setLavy(null);
                        }
                        if (napojenie != null) {
                            if (napojenie.getLavy() != null && napojenie.getLavy().getData().porovnaj(otec.getData()) == 0) {
                                napojenie.setLavy(vrchol);
                            } else if (napojenie.getPravy() != null && napojenie.getPravy().getData().porovnaj(otec.getData()) == 0) {
                                napojenie.setPravy(vrchol);
                            }
                            vrchol.setOtec(napojenie);
                        } else {
                            vrchol.setOtec(null);
                            setKoren(vrchol);
                        }

                    }

                } // ak je v pravom podstrome
                else if (vrchol.getData().porovnaj(vrchol.getOtec().getData()) < 0) {
                    // ak nema prarodica jeho rodic je teda korenom stromu
                    if (vrchol.getOtec().getOtec() == null) {
                        prvyLavySyn = vrchol.getOtec();
                        druhyPravySyn = vrchol.getLavy();

                        vrchol.setOtec(null);
                        vrchol.setLavy(prvyLavySyn);
                        prvyLavySyn.setOtec(vrchol);

                        if (druhyPravySyn != null) {
                            prvyLavySyn.setPravy(druhyPravySyn);
                            druhyPravySyn.setOtec(prvyLavySyn);
                        } else {
                            prvyLavySyn.setPravy(null);
                        }
                        setKoren(vrchol);
                        break;
                    }

                    // vrchol je pravy syn a otec je lavy syn
                    if (praOtec != null && praOtec.getLavy() != null && praOtec.getLavy().getPravy() != null && vrchol.getData().porovnaj(praOtec.getLavy().getPravy().getData()) == 0) {
                        druhyPravySyn = vrchol.getLavy();
                        otec = vrchol.getOtec();

                        //1. krok
                        // nastavenie vrchola
                        praOtec.setLavy(vrchol);
                        vrchol.setOtec(praOtec);
                        // nastavenie laveho syna vrcholu
                        vrchol.setLavy(otec);
                        otec.setOtec(vrchol);

                        // nastavenie noveho praveho syna vrcholu
                        if (druhyPravySyn != null) {
                            otec.setPravy(druhyPravySyn);
                            druhyPravySyn.setOtec(otec);
                        } else {
                            otec.setPravy(null);
                        }
                        //2.krok
                        druhyLavySyn = vrchol.getPravy();
                        napojenie = praOtec.getOtec();
                        //nastavenieho praveho syna
                        vrchol.setPravy(praOtec);
                        praOtec.setOtec(vrchol);
                        // nastavenie druheho laveho syna
                        if (druhyLavySyn != null) {
                            praOtec.setLavy(druhyLavySyn);
                            druhyLavySyn.setOtec(praOtec);
                        } else {
                            praOtec.setLavy(null);
                        }
                        if (napojenie != null) {
                            if (napojenie.getLavy() != null && napojenie.getLavy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setLavy(vrchol);
                            } else if (napojenie.getPravy() != null && napojenie.getPravy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setPravy(vrchol);
                            }
                            vrchol.setOtec(napojenie);
                        } else {
                            vrchol.setOtec(null);
                            setKoren(vrchol);
                        }

                    }
                    // vrchol a aj jeho rodic su obaja pravymi synmi
                    if (praOtec != null && praOtec.getPravy() != null && praOtec.getPravy().getPravy() != null && vrchol.getData().porovnaj(praOtec.getPravy().getPravy().getData()) == 0) {
                        //prvy krok                   
                        otec = vrchol.getOtec();
                        prvyLavySyn = praOtec;
                        druhyPravySyn = otec.getLavy();
                        napojenie = praOtec.getOtec();
                        // novy lavy syn
                        otec.setLavy(prvyLavySyn);
                        prvyLavySyn.setOtec(otec);
                        // druhy pravy syn
                        if (druhyPravySyn != null) {
                            prvyLavySyn.setPravy(druhyPravySyn);
                            druhyPravySyn.setOtec(prvyLavySyn);
                        } else {
                            prvyLavySyn.setPravy(null);
                        }
                        if (napojenie != null) {
                            if (napojenie.getLavy() != null && napojenie.getLavy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setLavy(otec);
                            } else if (napojenie.getPravy() != null && napojenie.getPravy().getData().porovnaj(praOtec.getData()) == 0) {
                                napojenie.setPravy(otec);
                            }
                            otec.setOtec(napojenie);
                        } else {
                            otec.setOtec(null);
                            setKoren(otec);
                        }
                        //druhy krok
                        druhyPravySyn = vrchol.getLavy();
                        prvyLavySyn = otec;
                        //novy lavy syn                        
                        vrchol.setLavy(otec);
                        prvyLavySyn.setOtec(vrchol);
                        //druhy pravy syn
                        if (druhyPravySyn != null) {
                            prvyLavySyn.setPravy(druhyPravySyn);
                            druhyPravySyn.setOtec(prvyLavySyn);
                        } else {
                            prvyLavySyn.setPravy(null);
                        }
                        if (napojenie != null) {
                            if (napojenie.getLavy() != null && napojenie.getLavy().getData().porovnaj(otec.getData()) == 0) {
                                napojenie.setLavy(vrchol);
                            } else if (napojenie.getPravy() != null && napojenie.getPravy().getData().porovnaj(otec.getData()) == 0) {
                                napojenie.setPravy(vrchol);
                            }
                            vrchol.setOtec(napojenie);
                        } else {
                            vrchol.setOtec(null);
                            setKoren(vrchol);
                        }
                    }
                }
            }
        }
        return false;
    }

    public Vrchol najdiVrchol(Vrchol vrchol) {
        Vrchol najdeny = super.najdi(vrchol);
        Vrchol rodic = null;
        if (getKoren() == null) {
        } else if (najdeny == null) {
            rodic = najdiRodica(vrchol);
            sPlay(rodic);
            return null;
        } else {
            sPlay(najdeny);
            return najdeny;
        }
        return null;
    }

//    public Vrchol najdiVrchol2(Vrchol vrchol) {
//        Vrchol najdeny = super.najdi2(vrchol);
//        Vrchol rodic = null;
//        if (getKoren() == null) {
//        } else if (najdeny == null) {
//            rodic = najdiRodica(vrchol);
//            sPlay(rodic);
//            return null;
//        } else {
//            sPlay(najdeny);
//            return najdeny;
//        }
//        return null;
//    }

    public Vrchol najdiRodica(Vrchol vrchol) {
        Vrchol aktualny = getKoren();
        Vrchol rodic = null;
        while (aktualny != null) {
            if (aktualny.getData().porovnaj(vrchol.getData()) == 0) {
                return aktualny;
            } else if (aktualny.getData().porovnaj(vrchol.getData()) > 0) {
                rodic = aktualny;
                aktualny = aktualny.getPravy();
            } else if (aktualny.getData().porovnaj(vrchol.getData()) < 0) {
                rodic = aktualny;
                aktualny = aktualny.getLavy();
            }
        }
        return rodic;
    }

    public boolean vloz(Vrchol vrchol) {
        boolean uspesne = super.vlozVrchol(vrchol);
        if (uspesne == true) {
            sPlay(vrchol);
            return true;
        } else {
            return false;
        }
    }

    public Vrchol vymazVrchol(Vrchol vrchol) {
        Vrchol pom = super.najdi(vrchol);
        Vrchol rodic = null;
        if (pom != null) {
            if (pom.getOtec() != null) {
                rodic = pom.getOtec();
                super.vymaz(pom);
                sPlay(rodic);
            } else {
                super.vymaz(pom);
            }
        } else {

        }
        return rodic;
    }

    public String vypisStromInOrder() {
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
                stringBuilder.append(aktualny.getData().getKluc());
                aktualny = aktualny.getPravy();
            }
        }
        return stringBuilder.toString();
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

    public ArrayList<Vrchol> inOrder(Vrchol koren1) {
        Vrchol aktualny = koren1;
        ArrayList<Vrchol> vrcholy = new ArrayList<Vrchol>();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();
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

//    public void printTree() {
//
//        Queue<Vrchol> currentLevel = new LinkedList<Vrchol>();
//        Queue<Vrchol> nextLevel = new LinkedList<Vrchol>();
//
//        currentLevel.add(getKoren());
//
//        while (!currentLevel.isEmpty()) {
//            Iterator<Vrchol> iter = currentLevel.iterator();
//            while (iter.hasNext()) {
//                Vrchol currentNode = iter.next();
//                if (currentNode.getLavy() != null) {
//                    nextLevel.add(currentNode.getLavy());
//                }
//                if (currentNode.getPravy() != null) {
//                    nextLevel.add(currentNode.getPravy());
//                }
//                Kniha kniha = (Kniha)currentNode.getData().dajTyp();
//                int id  = kniha.getId();
//                System.out.println(id + " ");
//            }
//            System.out.println();
//            currentLevel = nextLevel;
//            nextLevel = new LinkedList<Vrchol>();
//        }
//
//    }

}

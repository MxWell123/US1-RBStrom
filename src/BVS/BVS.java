/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BVS;

import ObalovacieTriedy.PacientPodlaRC;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import sem1.Pacient;

/**
 *
 * @author davidecek
 */
public class BVS {

    private Vrchol koren;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private Vrchol doubleBlack;
    private boolean jeLavy;

    public BVS() {
        this.koren = null;
        this.doubleBlack = null;
        this.jeLavy = false;
    }

    public Vrchol getDoubleBlack() {
        return doubleBlack;
    }

    public boolean isJeLavy() {
        return jeLavy;
    }

    public void setJeLavy(boolean jeLavy) {
        this.jeLavy = jeLavy;
    }

    public void setDoubleBlack(Vrchol doubleBlack) {
        this.doubleBlack = doubleBlack;
    }

    public Vrchol getKoren() {
        return koren;
    }

    public void setKoren(Vrchol koren) {
        this.koren = koren;
    }

    public Vrchol najdi(Vrchol vrchol) {
        Vrchol aktualny = getKoren();
        while (aktualny != null) {
            if (aktualny.getData().porovnaj(vrchol.getData()) == 0) {
                return aktualny;
            } else if (aktualny.getData().porovnaj(vrchol.getData()) > 0) {
                aktualny = aktualny.getPravy();
            } else if (aktualny.getData().porovnaj(vrchol.getData()) < 0) {
                aktualny = aktualny.getLavy();
            }
        }
        return null;
    }

    public boolean vlozVrchol(Vrchol vrchol) {
        Vrchol aktualny = koren;
        if (koren == null) {
            setKoren(vrchol);
            return true;
        }
        if (najdi(vrchol) != null) {
            return false;
        }
        while (true) {
            if (aktualny.getData().porovnaj(vrchol.getData()) < 0) {
                if (aktualny.getLavy() == null) {
                    vrchol.setOtec(aktualny);
                    aktualny.setLavy(vrchol);
                    return true;
                } else {
                    aktualny = aktualny.getLavy();
                }
            } else if (aktualny.getData().porovnaj(vrchol.getData()) > 0) {
                if (aktualny.getPravy() == null) {
                    vrchol.setOtec(aktualny);
                    aktualny.setPravy(vrchol);
                    return true;
                } else {
                    aktualny = aktualny.getPravy();
                }
            }
        }
    }

    public boolean vymaz(Vrchol vrcholD) {
        Vrchol pomocny = null;
        Vrchol vrchol = vrcholD;
        Vrchol novyPravy = null;
        Vrchol novyLavy = null;
        Vrchol otec = null;
        // urcenie premennych --------------------------------------------
        if (vrchol.getPravy() != null) {
            novyPravy = vrchol.getPravy();
        }
        if (vrchol.getLavy() != null) {
            novyLavy = vrchol.getLavy();
        }
        if (vrchol.getOtec() != null) {
            otec = vrchol.getOtec();
        }

        // ak je vymazavany vrchol korenom 
        if (vrchol.getData().porovnaj(getKoren().getData()) == 0) {
            // ak nema ziadnych potomkov
            if (vrchol.getLavy() == null && vrchol.getPravy() == null) {
                setKoren(null);
                return true;
            } // ak novy vrchol nie je ihned jeho pravy syn
            else if (vrchol.getPravy() != null && vrchol.getPravy().getLavy() != null) {
                pomocny = vrchol.getPravy();
                novyPravy = vrchol.getPravy();
                novyLavy = vrchol.getLavy();
                while (pomocny.getLavy() != null) {
                    pomocny = pomocny.getLavy();
                }
                jeLavy = jeLavy(pomocny);
                setDoubleBlack(new Vrchol(pomocny));
                Vrchol otecNoveho = pomocny.getOtec();
                if (pomocny.getPravy() == null) {
                    otecNoveho.setLavy(null);
                } else {
                    otecNoveho.setLavy(pomocny.getPravy());
                    pomocny.getPravy().setOtec(otecNoveho);
                }
                //nastavenie praveho syna
                pomocny.setPravy(novyPravy);
                novyPravy.setOtec(pomocny);
                //nastavenie laveho syna
                if (novyLavy != null) {
                    pomocny.setLavy(novyLavy);
                    novyLavy.setOtec(pomocny);
                } else {
                    pomocny.setLavy(null);
                }

                // nastavenie otca
                pomocny.setOtec(null);
                setKoren(pomocny);
                return true;
            } // ma laveho potomka 
            else if (vrchol.getLavy() != null && vrchol.getPravy() == null) {
                pomocny = vrchol.getLavy();
                jeLavy = jeLavy(pomocny);
                setDoubleBlack(new Vrchol(pomocny));
                pomocny.setOtec(null);
                setKoren(pomocny);
                return true;

            }// novy vrchol je jeho pravy syn
            else if (vrchol.getPravy() != null && vrchol.getPravy().getLavy() == null) {
                pomocny = vrchol.getPravy();
                novyLavy = vrchol.getLavy();
                jeLavy = jeLavy(pomocny);
                setDoubleBlack(new Vrchol(pomocny));
                pomocny.setOtec(null);
                // nastavenie laveho syna
                if (novyLavy != null) {
                    pomocny.setLavy(novyLavy);
                    novyLavy.setOtec(pomocny);
                }
                setKoren(pomocny);
            }
            vrchol.setLavy(null);
            vrchol.setPravy(null);
            vrchol.setOtec(null);
        } else {
            pomocny = vrchol.getOtec();
            // nema potomkov
            if (vrchol.getLavy() == null && vrchol.getPravy() == null) {
                if (pomocny.getLavy() != null && pomocny.getLavy().getData().porovnaj(vrchol.getData()) == 0) {
                    jeLavy = true;
                    setDoubleBlack(new Vrchol(pomocny.getLavy()));
                    pomocny.setLavy(null);
                    return true;
                } else if (pomocny.getPravy() != null && pomocny.getPravy().getData().porovnaj(vrchol.getData()) == 0) {
                    jeLavy = false;
                    setDoubleBlack(new Vrchol(pomocny.getPravy()));
                    pomocny.setPravy(null);
                    return true;
                }
            } // ma laveho potomka 
            else if (vrchol.getLavy() != null && vrchol.getPravy() == null) {
                if (pomocny.getLavy() != null && pomocny.getLavy().getData().porovnaj(vrchol.getData()) == 0) {
                    novyLavy = vrchol.getLavy();
                    pomocny.setLavy(novyLavy);
                    novyLavy.setOtec(pomocny);
                    jeLavy = true;
                    setDoubleBlack(new Vrchol(pomocny.getLavy()));
                    return true;
                } else if (pomocny.getPravy() != null && pomocny.getPravy().getData().porovnaj(vrchol.getData()) == 0) {
                    pomocny.setPravy(vrchol.getLavy());
                    vrchol.getLavy().setOtec(pomocny);
                    jeLavy = false;
                    setDoubleBlack(new Vrchol(pomocny.getPravy()));
                    return true;
                }

            } //ma praveho potomka
            else if (vrchol.getLavy() == null && vrchol.getPravy() != null) {
                if (pomocny.getLavy() != null && pomocny.getLavy().getData().porovnaj(vrchol.getData()) == 0) {
                    pomocny.setLavy(vrchol.getPravy());
                    vrchol.getPravy().setOtec(pomocny);
                    jeLavy = true;
                    setDoubleBlack(new Vrchol(pomocny.getLavy()));
                    return true;
                } else if (pomocny.getPravy() != null && pomocny.getPravy().getData().porovnaj(vrchol.getData()) == 0) {
                    pomocny.setPravy(vrchol.getPravy());
                    vrchol.getPravy().setOtec(pomocny);
                    jeLavy = false;
                    setDoubleBlack(new Vrchol(pomocny.getPravy()));
                    return true;
                }

            } //ma oboch potomkov
            else if (vrchol.getLavy() != null && vrchol.getPravy() != null) {
                pomocny = vrchol.getPravy();
                while (pomocny.getLavy() != null) {
                    pomocny = pomocny.getLavy();
                }
                jeLavy = jeLavy(pomocny);
                setDoubleBlack(new Vrchol(pomocny));
                // nastavenie otca novemu vrcholu                    
                if (otec.getLavy() != null) {
                    if (otec.getLavy().getData().porovnaj(vrchol.getData()) == 0) {
                        otec.setLavy(pomocny);
                    }
                }
                if (otec.getPravy() != null) {
                    if (otec.getPravy().getData().porovnaj(vrchol.getData()) == 0) {
                        otec.setPravy(pomocny);
                    }
                }
                // ak novy vrchol nie je ihned jeho pravy syn
                if (vrchol.getPravy() != null && vrchol.getPravy().getLavy() != null) {

                    Vrchol otecNoveho = pomocny.getOtec();
                    if (pomocny.getPravy() == null) {
                        otecNoveho.setLavy(null);
                    } else {
                        otecNoveho.setLavy(pomocny.getPravy());
                        pomocny.getPravy().setOtec(otecNoveho);
                    }
                    pomocny.setOtec(otec);
                    // nastavenie laveho syna
                    pomocny.setLavy(novyLavy);
                    novyLavy.setOtec(pomocny);
                    //nastavenie praveho syna
                    pomocny.setPravy(novyPravy);
                    novyPravy.setOtec(pomocny);

                }
                // novy vrchol je jeho pravy syn
                if (pomocny.getData().porovnaj(novyPravy.getData()) == 0) {
                    pomocny.setOtec(otec);
                    // nastavenie laveho syna
                    pomocny.setLavy(novyLavy);
                    novyLavy.setOtec(pomocny);
                }
            }
            // zmazanie stareho vrchola
            vrchol.setOtec(null);
            vrchol.setLavy(null);
            vrchol.setPravy(null);

            return true;
        }
        return false;
    }

    public int vypis(Vrchol vrchol) {
        int i = 0;
        Queue<Vrchol> currentLevel = new LinkedList<Vrchol>();
        Queue<Vrchol> nextLevel = new LinkedList<Vrchol>();

        currentLevel.add(vrchol);
        if (vrchol != null) {
            while (!currentLevel.isEmpty()) {
                Iterator<Vrchol> iter = currentLevel.iterator();
                while (iter.hasNext()) {
                    Vrchol aktualny = iter.next();
                    if (aktualny.getLavy() != null) {
                        nextLevel.add(aktualny.getLavy());
                    }
                    if (aktualny.getPravy() != null) {
                        nextLevel.add(aktualny.getPravy());
                    }
                    if (aktualny.getFarba() == false) {
                        System.out.print(ANSI_RED + aktualny.getData().getKluc() + " ");
                        i++;
                    } else {
                        System.out.print(ANSI_BLACK + aktualny.getData().getKluc() + " ");
                        i++;
                    }
                }
                System.out.println();
                currentLevel = nextLevel;
                nextLevel = new LinkedList<Vrchol>();
            }
        } else {
            System.out.println("Strom je prazdny");
        }
        return i;
    }

    public void vypisStromLevelOrder() {
        if (koren != null) {
            Vrchol aktualny = koren;
            Queue<Vrchol> frontVrcholov = new ArrayDeque<>();
            String vyslednyRetazec = "";
            StringBuilder stringBuilder = new StringBuilder(vyslednyRetazec);
            frontVrcholov.add(aktualny);
            while (!frontVrcholov.isEmpty()) {
                aktualny = frontVrcholov.poll();
                stringBuilder.append(aktualny.getData().getKluc() + " ");
                if (aktualny.getLavy() != null) {
                    frontVrcholov.add(aktualny.getLavy());
                }
                if (aktualny.getPravy() != null) {
                    frontVrcholov.add(aktualny.getPravy());
                }
            }
            System.out.println(stringBuilder.toString());
        }
    }

    public void vypisInorder(Vrchol vrchol) {
        if (vrchol != null) {
            vypisInorder(vrchol.getLavy());
            System.out.print(" " + vrchol.getData().getKluc());
            vypisInorder(vrchol.getPravy());
        }
    }

    private boolean jeLavy(Vrchol pomocny) {
        if (pomocny.getOtec() == null || pomocny.getOtec().getLavy() == null) {
            return false;
        }
        if (pomocny.getOtec().getLavy().getData().porovnaj(pomocny.getData()) == 0) { // ak prvok je lavy syn otca
            return true;
        } else { // ak je pravy syn
            return false;
        }

    }

}

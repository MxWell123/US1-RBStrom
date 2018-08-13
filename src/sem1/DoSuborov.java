
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sem1;

import BVS.Vrchol;
import RB.RB;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author davidecek
 */
public class DoSuborov {

    public DoSuborov() {
    }

    private void zmazPriecinok(Path path) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void vytvorPriecinok(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uloz(Program program) {
//---------------------------------------------------- Nemocnica
        Path cesta1 = Paths.get("Nemocnice");
        if (Files.exists(cesta1) == true) {
            zmazPriecinok(cesta1);
        }
        vytvorPriecinok(cesta1);

        RB nemocnice = program.dajNemocnice();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();
        Vrchol aktualny = nemocnice.getKoren();
        cesta1 = Paths.get("Nemocnice/Nemocnice.txt");
        try (FileWriter fw = new FileWriter(cesta1.toString(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            while (!zasobnikVrcholov.empty() || aktualny != null) {
                if (aktualny != null) {
                    zasobnikVrcholov.push(aktualny);
                    aktualny = aktualny.getLavy();
                } else {
                    aktualny = zasobnikVrcholov.pop();
                    Nemocnica nemocnica = (Nemocnica) aktualny.getData().dajTyp();
                    aktualny = aktualny.getPravy();
                    out.println(nemocnica.getNemocnica());

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DoSuborov.class.getName()).log(Level.SEVERE, null, ex);
        }
        //--------------------------------------------------------------------------------- Pacienti    
        cesta1 = Paths.get("Pacienti");
        if (Files.exists(cesta1) == true) {
            zmazPriecinok(cesta1);
        }

        RB pacienti = program.dajPacientov();
        vytvorPriecinok(cesta1);
        cesta1 = Paths.get("Pacienti/Pacienti.txt");
        zasobnikVrcholov.clear();
        aktualny = pacienti.getKoren();
        try (FileWriter fw = new FileWriter(cesta1.toString(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            while (!zasobnikVrcholov.empty() || aktualny != null) {
                if (aktualny != null) {
                    zasobnikVrcholov.push(aktualny);
                    aktualny = aktualny.getLavy();
                } else {
                    aktualny = zasobnikVrcholov.pop();
                    Pacient pacient = (Pacient) aktualny.getData().dajTyp();
                    aktualny = aktualny.getPravy();
                    naplnPriecinokPacientami(pacient, out);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DoSuborov.class.getName()).log(Level.SEVERE, null, ex);
        }

//     --------------------------------------------------------------------- Hospitalizacie
        cesta1 = Paths.get("Poistovna");
        if (Files.exists(cesta1) == true) {
            zmazPriecinok(cesta1);
        }

        RB poistovne = program.dajPoistovne();
        vytvorPriecinok(cesta1);
        zasobnikVrcholov.clear();
        aktualny = poistovne.getKoren();
        while (!zasobnikVrcholov.empty() || aktualny != null) {
            if (aktualny != null) {
                zasobnikVrcholov.push(aktualny);
                aktualny = aktualny.getLavy();
            } else {
                aktualny = zasobnikVrcholov.pop();
                Poistovna poistovna = (Poistovna) aktualny.getData().dajTyp();
                aktualny = aktualny.getPravy();
                cesta1 = Paths.get("Poistovna/" + poistovna.getCislo() + ".txt");
                try (FileWriter fw = new FileWriter(cesta1.toString(), true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    naplnPriecinokHospitalizaciami(poistovna, out);
                } catch (IOException ex) {
                    Logger.getLogger(DoSuborov.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void naplnPriecinokPacientami(Pacient pacient, PrintWriter out) {

        out.println(pacient.getRodnecislo() + "," + pacient.getMeno() + "," + pacient.getPriezvisko() + "," + pacient.getDatum_narodenia()
                + "," + pacient.getKod_ZP() + "," + pacient.getDatumZacatiaAktualnejHosp());

    }

    private void naplnPriecinokHospitalizaciami(Poistovna poistovna, PrintWriter out) {
        RB hospitalizacie = poistovna.dajVsetkyHospitalizacie();
        Stack<Vrchol> zasobnikVrcholov = new Stack<>();
        Vrchol aktualny = hospitalizacie.getKoren();
        while (!zasobnikVrcholov.empty() || aktualny != null) {
            if (aktualny != null) {
                zasobnikVrcholov.push(aktualny);
                aktualny = aktualny.getLavy();
            } else {
                aktualny = zasobnikVrcholov.pop();
                Hospitalizacia hospitalizacia = (Hospitalizacia) aktualny.getData().dajTyp();
                aktualny = aktualny.getPravy();
                out.println(hospitalizacia.getRc_pacienta() + "," + hospitalizacia.getDatum_zac_hosp() + "," + hospitalizacia.getDatum_kon_hosp() + ","
                        + hospitalizacia.getDiagnoza() + "," + hospitalizacia.getNemocnica());
            }
        }
    }

    public void nacitaj(Program program) {
        nacitajNemocnice(program);
        nacitajPacientov(program);
        nacitajHospitalizacie(program);
    }

    private void nacitajNemocnice(Program program) {
        try (Stream<Path> paths = Files.walk(Paths.get("Nemocnice/nemocnice.txt"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
                        String strLine = br.readLine();
                        while (strLine != null) {
                            program.vlozNovuNemocnicu(strLine);
                            strLine = br.readLine();
                        }

                        br.close();

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DoSuborov.class
                                .getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(DoSuborov.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(DoSuborov.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nacitajPacientov(Program program) {
        try (Stream<Path> paths = Files.walk(Paths.get("Pacienti/pacienti.txt"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
                        String strLine = br.readLine();
                        String[] split;
                        while (strLine != null) {
                            split = strLine.split(",");
                            String rc = split[0];
                            String meno = split[1];
                            String priezvisko = split[2];
                            LocalDate datum_narodenia = LocalDate.parse(split[3]);
                            int poistovna = Integer.parseInt(split[4]);
                            LocalDate datum = LocalDate.parse(split[5]);
                            program.vlozNovehoPacientaNacitaj(meno, priezvisko, rc, datum_narodenia, poistovna, datum);
                            strLine = br.readLine();
                        }
                        br.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DoSuborov.class
                                .getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(DoSuborov.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(DoSuborov.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nacitajHospitalizacie(Program program) {
        for (int i = 0; i < 15; i++) {
            try (Stream<Path> paths = Files.walk(Paths.get("Poistovna/" + i + ".txt"))) {
                final int a = i;
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
                            String strLine = br.readLine();
                            String[] split;
                            while (strLine != null) {
                                split = strLine.split(",");
                                String rc = split[0];
                                LocalDate datum_zac = LocalDate.parse(split[1]);
                                LocalDate datum_kon = null;
                                if (!split[2].equals("null")) {
                                    datum_kon = LocalDate.parse(split[2]);
                                }
                                String diagnoza = split[3];
                                String nemocnica = split[4];
                                program.vlozNacitanuHospitalizaciu(a, rc, datum_zac, datum_kon, diagnoza, nemocnica);
                                strLine = br.readLine();
                            }
                            br.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(DoSuborov.class
                                    .getName()).log(Level.SEVERE, null, ex);

                        } catch (IOException ex) {
                            Logger.getLogger(DoSuborov.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            } catch (IOException ex) {
                Logger.getLogger(DoSuborov.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

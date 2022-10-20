package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Glavna {

    public static final int BROJ_PROFESORA = 2;
    public static final int BROJ_PREDMETA = 3;
    public static final int BROJ_STUDENTA = 3;
    public static final int BROJ_ISPITA = 3;


    static Student unosStudent(Scanner unos, Integer redniBroj){

        System.out.println("Unesite " + (redniBroj + 1) + ". studenta: ");
        System.out.print("Unesite ime studenta: ");
        String tempIme = unos.nextLine();

        System.out.print("Unesite prezime studenta: ");
        String tempPrezime = unos.nextLine();

        System.out.print("Unesite datum rodenja studenta " + tempIme + " " + tempPrezime + " u formatu (dd.MM.yyyy.): ");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate tempDatum = LocalDate.parse(unos.nextLine(), dateFormat);

        System.out.print("Unesite JMBAG studenta: "+ tempIme + " " + tempPrezime + ": ");
        String tempJMBAG = unos.nextLine();

        return new Student(tempIme, tempPrezime, tempJMBAG, tempDatum);


    }
    static Profesor unosProfesor(Scanner unos, Integer redniBroj){

        System.out.println("Unesite " + (redniBroj+1) + ". profesora: ");

        System.out.print("Unesite šifru profesora: ");
        String tempSifra = unos.nextLine();

        System.out.print("Unesite ime profesora: ");
        String tempIme = unos.nextLine();

        System.out.print("Unesite prezime profesora: ");
        String tempPrezime = unos.nextLine();

        System.out.print("Unesite tituli profesora: ");
        String tempTitula = unos.nextLine();

        return new Profesor(tempSifra, tempIme, tempPrezime, tempTitula);
    }
    static Predmet[] unosPredmet(Scanner unos, Profesor[] profesori, Student[] studenti){

        String[] tempSifra = new String[BROJ_PREDMETA];
        String[] tempNaziv = new String[BROJ_PREDMETA];
        Integer[] tempECTS = new Integer[BROJ_PREDMETA];
        Integer[] tempOdabirProfesora = new Integer[BROJ_PREDMETA];
        Integer[] tempBrojStudenata = new Integer[BROJ_PREDMETA];

        for(int i = 0;i<BROJ_PREDMETA;i++){
            System.out.println("Unesite "+ (i+1) + ". predmet: ");
            System.out.print("Unesite sifru predmeta: ");
            tempSifra[i] = unos.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            tempNaziv[i] = unos.nextLine();


            System.out.print("Unesite broj ECTS bodova za predmet '" + tempNaziv[i] + "': ");
            tempECTS[i] = unos.nextInt();

            unos.nextLine();

            System.out.println("Odaberite profesora: ");
            for(int j = 0;j<BROJ_PROFESORA;j++){
                System.out.println((j+1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime());
            }
            System.out.print("Odabir >> ");
            tempOdabirProfesora[i] = unos.nextInt();

            unos.nextLine();

            System.out.print("Unesite broj studenata za predmet '" + tempNaziv[i] + "': ");
            tempBrojStudenata[i] = unos.nextInt();

            unos.nextLine();
        }

        for(int i = 0;i<BROJ_STUDENTA;i++){
            studenti[i] = unosStudent(unos, i);
        }

        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        for(int i = 0;i<BROJ_PREDMETA;i++){
            predmeti[i] = new Predmet(tempSifra[i], tempNaziv[i], tempECTS[i], profesori[tempOdabirProfesora[i]-1], studenti);
        }

        return predmeti;
    }

    static Ispit unosIspit(Scanner unos, Integer redniBroj, Predmet[] predmeti, Student[] studenti){

        System.out.println("Unesite " + (redniBroj+1) + ". ispitni rok: ");
        System.out.println("Odaberite predmet: ");
        for(int i = 0;i<BROJ_PREDMETA;i++){
            System.out.println((i+1) + " " + predmeti[i].getNaziv());
        }

        System.out.print("Odabir >> ");
        Integer tempOdabirPredmet = unos.nextInt();

        unos.nextLine();

        System.out.println("Odaberite studenta: ");
        for(int i = 0;i<BROJ_STUDENTA;i++){
            System.out.println((i+1) + " " + studenti[i].getIme() + " "+ studenti[i].getPrezime());
        }

        System.out.print("Odabir >> ");
        Integer tempOdabirStudenta = unos.nextInt();
        unos.nextLine();

        System.out.print("Unesite ocjenu na ispitu (1-5): ");
        Integer tempOcjena = unos.nextInt();
        unos.nextLine();

        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        LocalDateTime tempDatum = LocalDate.parse(unos.nextLine(), dateFormat).atStartOfDay();

        return new Ispit(predmeti[tempOdabirPredmet-1], studenti[tempOdabirStudenta-1], tempOcjena, tempDatum);
    }

    public static void main(String[] args) {


        Profesor[] profesori = new Profesor[BROJ_PROFESORA];
        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        Student[] studenti = new Student[BROJ_STUDENTA];
        Ispit[] ispiti = new Ispit[BROJ_ISPITA];

        Scanner unos = new Scanner(System.in);

        for(int i = 0;i<BROJ_PROFESORA;i++){
            profesori[i] = unosProfesor(unos, i);
        }

        predmeti = unosPredmet(unos, profesori, studenti);

        for(int i = 0;i<BROJ_ISPITA;i++){
            ispiti[i] = unosIspit(unos, i, predmeti, studenti);
        }

        Student[] izvrsniStudenti;

        Integer brojIzvrsnihStudenata = 0;
        for(int i = 0;i<BROJ_ISPITA;i++){
            if(ispiti[i].getOcjena().equals(5)){
                brojIzvrsnihStudenata++;
                izvrsniStudenti = new Student[brojIzvrsnihStudenata];
                izvrsniStudenti[brojIzvrsnihStudenata - 1] = ispiti[i].getStudent();
                System.out.println("Student " + izvrsniStudenti[brojIzvrsnihStudenata-1].getIme() + " " + izvrsniStudenti[brojIzvrsnihStudenata-1].getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
            }
        }
        System.out.println("KRAJ");


    }
}
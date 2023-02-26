import java.io.*;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.*;

public class Main {
    public ArrayList<Films>allFilms;
    public ArrayList<Person> persons;
    private String peopleFile,filmFile,commandFile,outputFile;
    BufferedWriter writer;

    Main(String peopleFile,String filmFile,String commandFile,String outputFile) throws Exception {
        this.commandFile=commandFile;
        this.outputFile=outputFile;
        this.peopleFile=peopleFile;
        this.filmFile=filmFile;
        allFilms=getFilmsList();
        persons=getPersonList();

        writer = new BufferedWriter(new FileWriter("output.txt"));
    }


    public static void main(String[] args) throws Exception {
        String peopleFile = args.length > 0 ? args[0] : "people.txt";
        String filmFile = args.length > 1 ? args[1] : "films.txt";
        String commandFile = args.length > 2 ? args[2] : "commands.txt";
        String outputFile = args.length > 3 ? args[3] : "output.txt";
        Main main = new Main(peopleFile,filmFile,commandFile,outputFile);
        main.executeCommands();



    }


    public void executeCommands() throws java.io.IOException {
        BufferedReader reader;
        String line;
        reader = new BufferedReader(new FileReader(commandFile));
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\t");
            if(words[0].equals("ADD") && words[1].equals("FEATUREFILM")){
                if(isAddFeatureFilmValid(words[2],words[5],words[11],words[8])){
                FeatureFilm featureFilm=new FeatureFilm(words[2],words[3],words[4],words[5],
                        Integer.parseInt(words[6]),words[7],words[8],
                        words[9],words[10],words[11],Integer.parseInt(words[12]));
                allFilms.add(featureFilm);

                writer.write(line+"\n"+"\n");
                writer.write("FeatureFilm added successfully"+"\n"+"Film ID: "+words[2]+"\n"+"Film title: "+words[3]+"\n"+"\n");
                writer.write("-----------------------------------------------------------------------------------------------------\n");
            }else{
                    writer.write(line+"\n"+"\n");
                    writer.write("Command Failed"+"\n"+"Film ID: "+words[2]+"\n"+"Film title: "+words[3]+"\n"+"\n");
                    writer.write("-----------------------------------------------------------------------------------------------------\n");

                }

            }

            if(words[0].equals("RATE")){
                boolean isUserboolean=false;
                for (int j = 0; j < persons.size(); j++) {
                    if (words[1].equals(persons.get(j).id)) {
                    isUserboolean=isUser(persons.get(j));
                    }
                }
                if(checkID(words[2],words[1],filmFile,peopleFile)&&checkRateExist(words[1],words[2])&&isUserboolean&&
                        Integer.parseInt(words[3])<=10&&Integer.parseInt(words[3])>=1) {
                    String filmType=null;
                    String filmTitle=null;

                    for (int i = 0; i < allFilms.size(); i++) {

                        if (words[2].equals(allFilms.get(i).id)) {
                            rateCalculator(allFilms.get(i), Integer.parseInt(words[3]));
                            filmType=allFilms.get(i).toString();
                            filmTitle=allFilms.get(i).title;

                        }
                    }
                    for (int j = 0; j < persons.size(); j++) {
                        if (words[1].equals(persons.get(j).id)) {
                            for (Films film : allFilms) {
                                if (film.id.equals(words[2])) {
                                    setListOfRateOfUser(persons.get(j), film, Integer.parseInt(words[3]));


                                }
                            }
                        }
                    }

                    writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\n"+"\n");
                    writer.write("Film rated successfully"+"\n"+"Film type: "+filmType+"\n"+"Film title: "+filmTitle+"\n"+"\n");
                    writer.write("-----------------------------------------------------------------------------------------------------\n");
                }else if(!checkRateExist(words[1],words[2])&&checkID(words[2],words[1],filmFile,peopleFile)&&
                        isUserboolean&&Integer.parseInt(words[3])<=10&&Integer.parseInt(words[3])>=1){
                    writer.write(words[0]+"\t"+words[1]+" "+words[2]+" "+words[3]+"\n"+"\n");
                    writer.write("This film was earlier rated"+"\n"+"\n");
                    writer.write("-----------------------------------------------------------------------------------------------------\n");
                }else if(!(Integer.parseInt(words[3])<=10&&Integer.parseInt(words[3])>=1)){
                    System.out.println("Rate is invalid. Must be 1<=rate<=10");
                }


                else{
                       writer.write(words[0]+"\t"+words[1]+" "+words[2]+" "+words[3]+"\n"+"\n");
                       writer.write("Command Failed"+"\n"+"User ID: "+words[1]+"\n"+"Film ID: "+words[2]+"\n"+"\n");
                       writer.write("-----------------------------------------------------------------------------------------------------\n");
                   }



            }if(words[0].equals("VIEWFILM")){
                viewFilm(words[1]);
            }if(words[0].equals("LIST")&&words[1].equals("USER")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\n"+"\n");
                listUserRate(words[2]);
            }if(words[0].equals("EDIT")&&words[1].equals("RATE")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\t"+words[4]+"\n"+"\n");
                editRate(words[2],words[3],Integer.parseInt(words[4]));
            }if(words[0].equals("REMOVE")&&words[1].equals("RATE")){
               writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\n"+"\n");
               removeRate(words[2],words[3]);
            }if(words[0].equals("LIST")&&words[1].equals("FILM")&&words[2].equals("SERIES")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\n"+"\n");
                listAllTvSeries();
            }if(words[0].equals("LIST")&&words[1].equals("FILMS")&&words[2].equals("BY")&&words[3].equals("COUNTRY")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\t"+words[4]+"\n"+"\n");
                listFilmByCountry(words[4]);
            }if(words[0].equals("LIST")&&words[1].equals("FEATUREFILMS")&&words[2].equals("BEFORE")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\t"+"\n"+"\n");
                listFeatureFilmBefore(words[3]);
            }if(words[0].equals("LIST")&&words[1].equals("FEATUREFILMS")&&words[2].equals("AFTER")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\t"+"\n"+"\n");
                listFeatureFilmAfter(words[3]);
            }if(words[0].equals("LIST")&&words[1].equals("FILMS")&&words[3].equals("RATE")&&words[4].equals("DEGREE")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\t"+words[4]+"\n"+"\n");
                listFilmsDescentingOrderbyRate();
            }if(words[0].equals("LIST")&&words[1].equals("ARTISTS")&&words[2].equals("FROM")){
                writer.write(words[0]+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]+"\t"+"\n"+"\n");
                listArtistFrom(words[3]);
            }

        }

        reader.close();
        writer.close();
        }


    public void rateCalculator(Films film ,double newRate){
        if(film.rate==0){
            film.numberOfRate++;
            film.rate= newRate;

        }else{
            double rt=(film.rate*film.numberOfRate+newRate)/ (film.numberOfRate+1);
            film.numberOfRate++;
            film.rate=rt;
        }
    }

    public void rateEditCalculator(Films film,double oldRate,double newRate){
        film.rate=((film.rate*film.numberOfRate)-oldRate+newRate)/film.numberOfRate;
    }


    public void setListOfRateOfUser(Person user, Films film, double rate)
    {
        user.rateListOfUser.put(film.title,rate);
    }


    public boolean checkID(String filmID,String userID,String filmFile,String peopleFile) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(filmFile));
        String line;
        boolean existFilm=false;
        boolean existUser=false;

        while ((line = reader1.readLine()) != null) {
            String[] wordsA = line.split("\t");
            if(wordsA[1].equals(filmID)){
                existFilm=true;
            }
            for(Films i :allFilms){
                if(i.id.equals(filmID)){
                    existFilm=true;
                }
            }
        }
        reader1.close();
        BufferedReader reader2 = new BufferedReader(new FileReader(peopleFile));
        String line1;
        while ((line = reader2.readLine()) != null) {
            String[] wordsB = line.split("\t");
            if(wordsB[1].equals(userID)){
            existUser=true;
            }
        }

        reader2.close();
       return existFilm&&existUser;
    }



    public boolean isUser(Person person){
        String is= String.valueOf(person.getClass());

        return is.equals("class User");
    }

    public boolean checkRateExist(String userID,String filmID){
        boolean notYet=true;
        String filmTitle=null;
        for (int k = 0; k < allFilms.size(); k++) {
            if (filmID.equals(allFilms.get(k).id)) {
                filmTitle=allFilms.get(k).title;
            }
        }
        for (int j = 0; j < persons.size(); j++) {
            if (userID.equals(persons.get(j).id)) {
                for(String i:persons.get(j).rateListOfUser.keySet()){

                    if(i.equals(filmTitle)){
                        notYet=false;
                    }
                }
            }
        }
        return notYet;
    }

    public void editRate(String userID,String filmID,double newRate) throws IOException {
        if(isUser(findUserFromID(userID))&&checkID(filmID,userID,filmFile,peopleFile)){
            for(int i=0;i<persons.size();i++){
                if(persons.get(i).id.equals(userID)){
                   if(persons.get(i).rateListOfUser.isEmpty()){
                       writer.write("Command Failed"+"\n");
                       writer.write("User ID: "+userID+"\n"+"Film ID: "+filmID+"\n"+"\n");
                       writer.write("-----------------------------------------------------------------------------------------------------\n");
                   }else{
                       for(int j=0;j<allFilms.size();j++){
                           if(allFilms.get(j).id.equals(filmID)){
                               if(persons.get(i).rateListOfUser.get(allFilms.get(j).title)!=null) {
                                   double oldRate = persons.get(i).rateListOfUser.get(allFilms.get(j).title);
                                   rateEditCalculator(allFilms.get(j),oldRate,newRate);
                                   persons.get(i).rateListOfUser.put(allFilms.get(j).title, newRate);
                                   writer.write("New ratings done successfully"+"\n");
                                   writer.write("Film title: "+allFilms.get(j).title+"\n");
                                   writer.write("Your rating: "+Math.round(newRate)+"\n"+"\n");
                                   writer.write("-----------------------------------------------------------------------------------------------------\n");
                               }else{
                                   writer.write("Command Failed"+"\n");
                                   writer.write("User ID: "+userID+"\n"+"Film ID: "+filmID+"\n"+"\n");
                                   writer.write("-----------------------------------------------------------------------------------------------------\n");
                               }

                           }
                   }
                   }
               }
            }
        }else{
            writer.write("Command Failed"+"\n");
            writer.write("User ID: "+userID+"\n"+"Film ID: "+filmID+"\n"+"\n");
            writer.write("-----------------------------------------------------------------------------------------------------\n");
        }
    }

    public void removeRate(String userID,String filmID) throws IOException {
        if(isUser(findUserFromID(userID))&&checkID(filmID,userID,filmFile,peopleFile)){
            for(int j=0;j<persons.size();j++){
                if(persons.get(j).id.equals(userID)){
                    if(persons.get(j).rateListOfUser.containsKey(findFilmFromID(filmID).title)){
                        findFilmFromID(filmID).numberOfRate-=1;
                        persons.get(j).rateListOfUser.remove(findFilmFromID(filmID).title);
                        writer.write("Your film rating was removed successfully"+"\n");
                        writer.write("Film title: "+findFilmFromID(filmID).title+"\n"+"\n");
                        writer.write("-----------------------------------------------------------------------------------------------------\n");
                    }else{
                        writer.write("Command Failed"+"\n"+"User ID: "+userID+"\n"+"Film ID: "+filmID+"\n"+"\n");
                        writer.write("-----------------------------------------------------------------------------------------------------\n");
                    }
                }
            }
        }

    }


    public boolean isAddFeatureFilmValid(String filmID,String pardirectors,String parwriters,String parperformers) throws IOException {
        boolean filmYok=!checkFilmID(filmID);
        boolean existPeople=false;
            BufferedReader reader2 = new BufferedReader(new FileReader(peopleFile));
            String line1;
            ArrayList<String> aranacak=new ArrayList<>();
            int arrayNumOlmali=0;
            while ((line1 = reader2.readLine()) != null) {
                String[] directorsStr = pardirectors.split(",");
                String[] writersStr = parwriters.split(",");
                String[] performersStr = parperformers.split(",");
                arrayNumOlmali=directorsStr.length+writersStr.length+performersStr.length;
                String[] wordsB = line1.split("\t");
                for (String i : directorsStr) {

                    if (wordsB[1].equals(i)) {
                        aranacak.add(i);
                    }
                }

                for (String j : writersStr) {

                    if (wordsB[1].equals(j)) {
                        aranacak.add(j);
                    }
                }
                for (String k : performersStr) {
                    if (wordsB[1].equals(k)) {
                        aranacak.add(k);

                    }
                }
            }
        existPeople=aranacak.size()==arrayNumOlmali;
        reader2.close();
            return filmYok&&existPeople;
       }


       public void viewFilm(String filmID) throws IOException {
         if(checkFilmIDJustTxt(filmID)){
             BufferedReader reader1 = new BufferedReader(new FileReader(filmFile));
             String line;

             while ((line = reader1.readLine()) != null) {
                 String[] wordsC = line.split("\t");
                 if(!checkFilmID(filmID)){
                     writer.write("Command Failed"+"\n");
                     writer.write("Film ID: "+filmID+"\n");
                     writer.write("-----------------------------------------------------------------------------------------------------\n");
                 }
                 else if(wordsC[1].equals(filmID)) {
                     switch (wordsC[0]) {
                         case "FeatureFilm:":
                         case "ShortFilm:":
                             writer.write("VIEWFILM" + "    " + filmID + "\n" + "\n");
                             writer.write(wordsC[2] + " (" + wordsC[9].substring(6) + ") " + "\n");
                             if (findFilmFromID(filmID).numberOfRate != 0) {
                                 String avrg =  String.valueOf(findFilmFromID(filmID).rate);
                                 String a=null;

                                 if(!avrg.contains(".0")){
                                      a = avrg.replace(".", ",");
                                 }else{a=avrg.replace(".0","");}

                                 writer.write(wordsC[8] + "\n" + "Writers: " + findPersonNameFromID(wordsC[10]) + "\n" + "Directors: " + findPersonNameFromID(wordsC[4]) + "\n" +
                                         "Stars: " + findPersonNameFromID(wordsC[7]) + "\n" + "Ratings: " +
                                         a + "/10 " + "from " + findFilmFromID(filmID).numberOfRate + " users \n" + "\n");
                                 writer.write("-----------------------------------------------------------------------------------------------------\n");
                             } else {
                                 writer.write(wordsC[8] + "\n" + "Writers: " + findPersonNameFromID(wordsC[10]) + "\n" + "Directors: " + findPersonNameFromID(wordsC[4]) + "\n" +
                                         "Stars: " + findPersonNameFromID(wordsC[7]) + "\n" +
                                         "Awaiting for votes" + "\n" + "\n");
                                 writer.write("-----------------------------------------------------------------------------------------------------\n");
                             }
                             break;
                         case "Documentary:":
                             writer.write("VIEWFILM" + "    " + filmID + "\n" + "\n");
                             writer.write(wordsC[2] + " (" + wordsC[8].substring(6) + ") " + "\n"+"\n");
                             if (findFilmFromID(filmID).numberOfRate != 0) {
                                 String avrg =  String.valueOf(findFilmFromID(filmID).rate);
                                 String a=null;
                                 if(!avrg.contains(".0")){
                                     a = avrg.replace(".", ",");
                                 }else{a=avrg.replace(".0","");}
                                 writer.write("Directors: " + findPersonNameFromID(wordsC[4]) + "\n" + "Stars: " + findPersonNameFromID(wordsC[7]) + "\n" + "Ratings: " +
                                         a + "/10 " + "from " + findFilmFromID(filmID).numberOfRate + " users \n" + "\n");
                             } else {
                                 writer.write("Directors: " + findPersonNameFromID(wordsC[4]) + "\n" + "Stars: " + findPersonNameFromID(wordsC[7]) + "\n" +
                                         "Awaiting for votes" + "\n" + "\n");
                             }
                             writer.write("-----------------------------------------------------------------------------------------------------\n");
                             break;
                         case "TVSeries:":
                             writer.write("VIEWFILM" + "    " + filmID + "\n" + "\n");
                             writer.write(wordsC[2] + " (" + wordsC[10].substring(6) + "-" + wordsC[11].substring(6) + ") \n");
                             if (findFilmFromID(filmID).numberOfRate != 0) {
                                 String avrg =  String.valueOf(findFilmFromID(filmID).rate);
                                 String a=null;
                                 if(!avrg.contains(".0")){
                                     a = avrg.replace(".", ",");
                                 }else{a=avrg.replace(".0","");}
                                 writer.write(wordsC[12] + " seasons, " + wordsC[13] + " episodes\n" + wordsC[8] + "\n" +
                                         "Writers: " + findPersonNameFromID(wordsC[9]) + "\n" + "Directors: " + findPersonNameFromID(wordsC[4]) +
                                         "\n" + "Stars: " + findPersonNameFromID(wordsC[7]) + "\n" + "Ratings: " +
                                         a + "/10 " + "from " + findFilmFromID(filmID).numberOfRate + " users \n" + "\n");
                             } else {
                                 writer.write(wordsC[12] + " seasons, " + wordsC[13] + " episodes\n" + wordsC[8] + "\n" +
                                         "Writers: " +findPersonNameFromID(wordsC[9]) + "\n" + "Directors: " + findPersonNameFromID(wordsC[4]) +
                                         "\n" + "Stars: " + findPersonNameFromID(wordsC[7]) +"\n"+
                                         "Awaiting for votes" + "\n" + "\n");
                             }
                             writer.write("-----------------------------------------------------------------------------------------------------\n");
                             break;

                     } }


                         }
                     }
         else if(checkFilmID(filmID)){
             for(int l=0;l<allFilms.size();l++){
                 if(allFilms.get(l).id.equals(filmID)){
                     switch(findFilmTypeFromID(filmID)) {
                         case "FeatureFilm":
                         case "ShortFilm":
                             FeatureFilm film=featureFilmFromID(filmID);
                             writer.write("VIEWFILM" + "    " + filmID + "\n" + "\n");
                             writer.write(film.title + " (" + film.releaseDate.substring(6) + ") " + "\n");
                             if (findFilmFromID(filmID).numberOfRate != 0) {
                                 String avrg =  String.valueOf(allFilms.get(l).rate);
                                 String a=null;

                                 if(!avrg.contains(".0")){
                                     a = avrg.replace(".", ",");
                                 }else{a=avrg.replace(".0","");}
                                 writer.write(film.genre + "\n" + "Writers: " + findPersonNameFromID(film.writers) + "\n" + "Directors: " + findPersonNameFromID(film.directors) + "\n" +
                                         "Stars: " + findPersonNameFromID(film.cast) + "\n" + "Ratings: " +
                                         a + "/10 " + "from " + allFilms.get(l).numberOfRate + " users \n" + "\n");
                                 writer.write("-----------------------------------------------------------------------------------------------------\n");
                             } else {
                                 writer.write(film.genre + "\n" + "Writers: " + findPersonNameFromID(film.writers) + "\n" + "Directors: " + findPersonNameFromID(film.directors) + "\n" +
                                         "Stars: " + findPersonNameFromID(film.performers) + "\n" +
                                         "Awaiting for votes" + "\n" + "\n");
                                 writer.write("-----------------------------------------------------------------------------------------------------\n");
                             }
                             break;
                         case "Documentary":
                             Documentary doc=documentaryFromID(filmID);
                             writer.write("VIEWFILM" + "    " + filmID + "\n" + "\n");
                             writer.write(doc.title + " (" + doc.releaseDate.substring(6) + ") " + "\n"+"\n");
                             if (findFilmFromID(filmID).numberOfRate != 0) {
                                 String avrg =  String.valueOf(allFilms.get(l).rate);

                                 String a=null;
                                 if(!avrg.contains(".0")){
                                     a = avrg.replace(".", ",");
                                 }else{a=avrg.replace(".0","");}
                                 writer.write("Directors: " + findPersonNameFromID(doc.directors) + "\n" + "Stars: " + findPersonNameFromID(doc.performers) + "\n" + "Ratings: " +
                                         a + "/10 " + "from " + doc.numberOfRate + " users \n" + "\n");
                             } else {
                                 writer.write("Directors: " + findPersonNameFromID(doc.directors) + "\n" + "Stars: " + findPersonNameFromID(doc.performers) + "\n" +
                                         "Awaiting for votes" + "\n" + "\n");
                             }
                             writer.write("-----------------------------------------------------------------------------------------------------\n");
                             break;
                         case "TVSeries":
                             TvSeries film2=tvSeriesFromID(filmID);
                             writer.write("VIEWFILM" + "    " + filmID + "\n" + "\n");
                             writer.write(film2.title + " (" + film2.startDate.substring(6) + "-" + film2.endDate.substring(6) + ") \n");
                             if (findFilmFromID(filmID).numberOfRate != 0) {
                                 String avrg =  String.valueOf(allFilms.get(l).rate);
                                 String a=null;

                                 if(!avrg.contains(".0")){
                                     a = avrg.replace(".", ",");
                                 }else{a=avrg.replace(".0","");}
                                 writer.write(film2.numberOfSeasons + " seasons, " + film2.numberOfepisodes + " episodes\n" + film2.genre + "\n" +
                                         "Writers: " + findPersonNameFromID(film2.writers) + "\n" + "Directors: " + findPersonNameFromID(film2.directors) +
                                         "\n" + "Stars: " + findPersonNameFromID(film2.performers) + "\n" + "Ratings: " +
                                         a + "/10 " + "from " + film2.numberOfRate + " users \n" + "\n");
                             } else {
                                 writer.write(film2.numberOfSeasons + " seasons, " + film2.numberOfepisodes + " episodes\n" + film2.genre + "\n" +
                                         "Writers: " + findPersonNameFromID(film2.writers) + "\n" + "Directors: " + findPersonNameFromID(film2.directors) +
                                         "\n" + "Stars: " + findPersonNameFromID(film2.performers) +"\n"+
                                         "Awaiting for votes" + "\n" + "\n");
                             }
                             writer.write("-----------------------------------------------------------------------------------------------------\n");
                             break;
                     }
                     }
             }
         }
     }


    public boolean checkFilmID(String filmID) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(filmFile));
        String line;
        boolean existFilm=false;

        while ((line = reader1.readLine()) != null) {
            String[] wordsA = line.split("\t");
            if(wordsA[1].equals(filmID)){
                existFilm=true;
            }
        } reader1.close();
        for(Films i :allFilms){
            if(i.id.equals(filmID)){
                existFilm=true;
            }
        }

        return existFilm;
    }

    public boolean checkFilmIDJustTxt(String filmID) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(filmFile));
        String line;
        boolean existFilm=false;

        while ((line = reader1.readLine()) != null) {
            String[] wordsA = line.split("\t");
            if(wordsA[1].equals(filmID)){
                existFilm=true;
            }
        } reader1.close();
          return existFilm;
    }

    public Films findFilmFromID(String filmID){
         Films myFilm=null;
         for (int i = 0; i < allFilms.size(); i++) {
            if (filmID.equals(allFilms.get(i).id)) {
                myFilm=allFilms.get(i);
            }
            }
         return myFilm;
    }
    public FeatureFilm featureFilmFromID(String featureFilmID){
        FeatureFilm featureF=null;
        for (int i = 0; i < allFilms.size(); i++) {
            if (featureFilmID.equals(allFilms.get(i).id)) {
                featureF=(FeatureFilm)allFilms.get(i);
            }
        }
        return featureF;
    }
    public ShortFilm shortFilmFromID(String filmID){
        ShortFilm shortF=null;
        for (int i = 0; i < allFilms.size(); i++) {
            if (filmID.equals(allFilms.get(i).id)) {
                shortF=(ShortFilm)allFilms.get(i);
            }
        }
        return shortF;
    }

    public TvSeries tvSeriesFromID(String filmID){
      TvSeries tvSeries=null;
        for (int i = 0; i < allFilms.size(); i++) {
            if (filmID.equals(allFilms.get(i).id)) {
                tvSeries=(TvSeries) allFilms.get(i);
            }
        }
        return tvSeries;
    }
    public Documentary documentaryFromID(String filmID){
        Documentary documentary=null;
        for (int i = 0; i < allFilms.size(); i++) {
            if (filmID.equals(allFilms.get(i).id)) {
                documentary=(Documentary) allFilms.get(i);
            }
        }
        return documentary;
    }

    public Person findUserFromID(String userID){
        Person user=null;
        for (int i = 0; i < persons.size(); i++) {
            if (userID.equals(persons.get(i).id)) {
                user=persons.get(i);
            }
        }
        return user;
    }
    public String findPersonNameFromID(String personID){
        ArrayList person=new ArrayList<>();
        if(!personID.contains(",")){

            for(int i=0;i< persons.size();i++){
                if(personID.equals(persons.get(i).id)){
                    String personc=persons.get(i).name+" "+persons.get(i).surname;
                    person.add(personc);
                }
            }

        }
        else{

            for(String i :personID.split(",")){
                person.add(findPersonNameFromID(i));
            }

        }
        String listString = String.join(", ", person);
       return listString;
    }



    public ArrayList<Films> getFilmsList() throws Exception {
        ArrayList<Films> allFilms=new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filmFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\t");
            switch(words[0]){
                case "FeatureFilm:":
                    FeatureFilm featureFilm=new FeatureFilm(words[1],words[2],words[3],words[4],
                            Integer.parseInt(words[5]),words[6],words[7],
                            words[8],words[9],words[10],Integer.parseInt(words[11]));
                    allFilms.add(featureFilm);

                    break;

                case "ShortFilm:":
                    if(Integer.parseInt(words[5])<=40) {
                        ShortFilm shortFilm = new ShortFilm(words[1], words[2], words[3], words[4],
                                Integer.parseInt(words[5]), words[6], words[7], words[8],
                                words[9], words[10]);
                        allFilms.add(shortFilm);
                    }else{
                        System.out.println("Short film's runtime longer than 40");
                    }
                    break;

                case "Documentary:":
                    Documentary documentary= new Documentary(words[1],words[2],words[3],words[4], Integer.parseInt(words[5]),
                            words[6],words[7],words[8]);
                    allFilms.add(documentary);

                    break;

                case "TVSeries:" :
                    TvSeries tvSeries=new TvSeries(words[1],words[2],words[3],words[4],Integer.parseInt(words[5]),words[6],
                            words[7],words[8],words[9],words[10],words[11],
                            Integer.parseInt(words[12]),Integer.parseInt(words[13]));
                    allFilms.add(tvSeries);

                    break;

            }
        }
        reader.close();
        return allFilms;

    }


    public ArrayList<Person> getPersonList() throws java.io.IOException{
        ArrayList<Person> allPerson=new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(peopleFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\t");
            switch(words[0]){
                case "Actor:":
                    Actor actor=new Actor(words[1],words[2],words[3],words[4],Integer.parseInt(words[5]));
                    allPerson.add(actor);
                    break;
                case "ChildActor:":
                    ChildActor childActor=new ChildActor(words[1],words[2],words[3],words[4],Integer.parseInt(words[5]));
                    allPerson.add(childActor);
                    break;
                case "Director:":
                    Director director=new Director(words[1],words[2],words[3],words[4],words[5]);
                    allPerson.add(director);
                    break;
                case "Writer:":
                    Writer writer=new Writer(words[1],words[2],words[3],words[4],words[5]);
                    allPerson.add(writer);
                    break;
                case "User:":
                    User user=new User(words[1],words[2],words[3],words[4]);
                    allPerson.add(user);
                    break;
                case "StuntPerformer:":
                    StuntPerformer stuntPerformer=new StuntPerformer(words[1],words[2],words[3],words[4],Integer.parseInt(words[5]),words[6]);
                    allPerson.add(stuntPerformer);
                    break;

            }
        }
        reader.close();
        return allPerson;
    }

    public void listUserRate(String userID) throws IOException {

        if(findUserFromID(userID).rateListOfUser.isEmpty()&&isUser(findUserFromID(userID))){
         writer.write("There is not any ratings so far"+"\n");
         writer.write("-----------------------------------------------------------------------------------------------------\n");
        }else if(!isUser(findUserFromID(userID))){
            writer.write("Command Failed"+"\n");
            writer.write("User ID: "+userID+"\n"+"\n");
            writer.write("-----------------------------------------------------------------------------------------------------\n");
        }else if(isUser(findUserFromID(userID))){
            Map<String, Double> map =new LinkedHashMap<>(findUserFromID(userID).rateListOfUser);
            for( Map.Entry<String, Double> entry : map.entrySet() ){
                writer.write( entry.getKey() + ": " + Math.round(entry.getValue())+"\n" );
            }

            writer.write("\n"+"-----------------------------------------------------------------------------------------------------\n");

        }
    }

    public String findFilmTypeFromID(String filmID){
        String[] str =findFilmFromID(filmID).getClass().toString().split(" ");
        return str[1];
    }



    public void listAllTvSeries() throws IOException {
        int count=0;
        for(int i=0;i<allFilms.size();i++){
            if(findFilmTypeFromID(allFilms.get(i).id).equals("TvSeries")){
                TvSeries tV=tvSeriesFromID(allFilms.get(i).id);
                count+=1;
                writer.write(allFilms.get(i).title+" ("+tV.startDate.substring(6)+"-"+tV.endDate.substring(6)+")\n");
                writer.write(tV.numberOfSeasons+" seasons and "+tV.numberOfepisodes+" episodes"+"\n\n");
            }
        }
        if(count==0){
            writer.write("No result");
        }
        writer.write("-----------------------------------------------------------------------------------------------------\n");
    }

    public void listFilmByCountry(String country) throws IOException {
        int count=0;
        for(int i=0;i<allFilms.size();i++){
            if(allFilms.get(i).country.equals(country)){
                count++;
                writer.write("Film title: "+allFilms.get(i).title+"\n"+allFilms.get(i).runtime+" min"+"\n"+
                        "Language: "+allFilms.get(i).language+"\n\n");
            }
            }
        if(count==0){
            writer.write("No result\n\n");}
        writer.write("-----------------------------------------------------------------------------------------------------\n");
        }

        public void listFeatureFilmBefore(String date) throws IOException {
            int count=0;
            for(int i=0;i<allFilms.size();i++){
                if(findFilmTypeFromID(allFilms.get(i).id).equals("FeatureFilm")){
                    FeatureFilm f=featureFilmFromID(allFilms.get(i).id);
                if(Integer.parseInt(f.releaseDate.substring(6))<Integer.parseInt(date)){

                    count++;
                    writer.write("Film title: "+f.title+" ("+f.releaseDate.substring(6)+")\n"+
                    f.runtime+" min"+"\n"+ "Language: "+f.language+"\n\n");
                }}
            }
            if(count==0){
                writer.write("No result\n\n");}
            writer.write("-----------------------------------------------------------------------------------------------------\n");
        }

    public void listFeatureFilmAfter(String date) throws IOException {
        int count=0;
        for(int i=0;i<allFilms.size();i++){
            if(findFilmTypeFromID(allFilms.get(i).id).equals("FeatureFilm")){
                FeatureFilm f=featureFilmFromID(allFilms.get(i).id);
                if(Integer.parseInt(f.releaseDate.substring(6))>=Integer.parseInt(date)){

                    count++;
                    writer.write("Film title: "+f.title+" ("+f.releaseDate.substring(6)+")\n"+
                            f.runtime+" min"+"\n"+ "Language: "+f.language+"\n\n");
                }}
        }
        if(count==0){
            writer.write("No result\n\n");}
        writer.write("-----------------------------------------------------------------------------------------------------\n");
    }

    public void listFilmsDescentingOrderbyRate() throws IOException {
        //ekleme sırasına göre olmuyor!!!!!!!!!!!!!!!!!!!!!!!!!
        ArrayList<Films> listFeature = new ArrayList<>();
        ArrayList<Films> listShort = new ArrayList<>();
        ArrayList<Films> listDocumentary = new ArrayList<>();
        ArrayList<Films> listTvS=new ArrayList<>();
        for (int i = 0; i < allFilms.size(); i++) {
            if (findFilmTypeFromID(allFilms.get(i).id).equals("FeatureFilm")) {
                listFeature.add(allFilms.get(i));
            }
        }
        for (int k = 0; k < allFilms.size(); k++) {
            if (findFilmTypeFromID(allFilms.get(k).id).equals("ShortFilm")) {
                listShort.add(allFilms.get(k));
            }
        }
        for (int s = 0; s < allFilms.size(); s++) {
            if (findFilmTypeFromID(allFilms.get(s).id).equals("Documentary")) {
                listDocumentary.add(allFilms.get(s));
            }
        }
        for (int p = 0; p < allFilms.size(); p++) {
            if (findFilmTypeFromID(allFilms.get(p).id).equals("TvSeries")) {
                listTvS.add(allFilms.get(p));
            }
        }


        Collections.sort(listFeature, Comparator.comparing(o -> String.valueOf(o.rate)));
        writer.write("FeatureFilm:" + "\n");
        if(!listFeature.isEmpty()) {
            for (int j = listFeature.size() - 1; j >= 0; j--) {
                String a = null;
                String rateStr = new BigDecimal(listFeature.get(j).rate).setScale(1, BigDecimal.ROUND_HALF_UP)
                        .toString();
                if (rateStr.contains("0.0")) {
                    a = "0";

                } else if (rateStr.contains(".0")) {
                    a = rateStr.replace(".0", "");

                } else {
                    a = rateStr.replace(".", ",");

                }
                FeatureFilm f = featureFilmFromID(listFeature.get(j).id);
                writer.write(listFeature.get(j).title + " (" + f.releaseDate.substring(6) + ") " + "Ratings: " +
                        a + "/10 from " + listFeature.get(j).numberOfRate + " users\n");
            }
        }else{
            writer.write("\nNo result\n");
        }

        writer.write("\nShortFilm:\n");
        Collections.sort(listShort, Comparator.comparing(o -> String.valueOf(o.rate)));
        if(!listShort.isEmpty()){
        for (int j = listShort.size() - 1; j >= 0; j--) {
            String a = null;
            String rateStr = new BigDecimal(listShort.get(j).rate).setScale(1, BigDecimal.ROUND_HALF_UP)
                    .toString();
            if (rateStr.contains("0.0")) {
                a = "0";

            } else if (rateStr.contains(".0")) {
                a = rateStr.replace(".0", "");

            } else {
                a = rateStr.replace(".", ",");

            }
            ShortFilm shortF = shortFilmFromID(listShort.get(j).id);
            writer.write(listShort.get(j).title + " (" + shortF.releaseDate.substring(6) + ") " + "Ratings: " +
                    a + "/10 from " + listShort.get(j).numberOfRate + " users\n");
        }}else{
            writer.write("\nNo result\n");
        }

        writer.write("\nDocumentary:\n");
        Collections.sort(listDocumentary, Comparator.comparing(o -> String.valueOf(o.rate)));
        if(!listDocumentary.isEmpty()){
        for (int j = listDocumentary.size() - 1; j >= 0; j--) {
            String a = null;
            String rateStr = new BigDecimal(listDocumentary.get(j).rate).setScale(1, BigDecimal.ROUND_HALF_UP)
                    .toString();
            if (rateStr.contains("0.0")) {
                a = "0";

            } else if (rateStr.contains(".0")) {
                a = rateStr.replace(".0", "");

            } else {
                a = rateStr.replace(".", ",");
            }
            Documentary doc = documentaryFromID(listDocumentary.get(j).id);
            writer.write(listDocumentary.get(j).title + " (" + doc.releaseDate.substring(6) + ") Ratings: " +
                    a + "/10 from " + listDocumentary.get(j).numberOfRate + " users\n");
        }
        }else{
            writer.write("\nNo result\n");
        }
        writer.write("\nTVSeries:\n");
        Collections.sort(listTvS, Comparator.comparing(o -> String.valueOf(o.rate)));
        if(!listTvS.isEmpty()){
        for (int j = listTvS.size() - 1; j >= 0; j--) {
            String a = null;
            String rateStr = new BigDecimal(listTvS.get(j).rate).setScale(1, BigDecimal.ROUND_HALF_UP)
                    .toString();
            if (rateStr.contains("0.0")) {
                a = "0";

            } else if (rateStr.contains(".0")) {
                a = rateStr.replace(".0", "");

            } else {
                a = rateStr.replace(".", ",");
            }
            TvSeries tv = tvSeriesFromID(listTvS.get(j).id);
            writer.write(listTvS.get(j).title + " (" + tv.startDate.substring(6) + "-" + tv.endDate.substring(6) + ") Ratings: " +
                    a + "/10 from " + listTvS.get(j).numberOfRate + " users\n");
        }
        }else{
            writer.write("\nNo result\n");
        }
        writer.write("\n-----------------------------------------------------------------------------------------------------\n");
    }

    public void listArtistFrom(String country) throws IOException {
        ArrayList<Person> dir=new ArrayList<>();
        ArrayList<Person> wri=new ArrayList<>();
        ArrayList<Person> act=new ArrayList<>();
        ArrayList<Person> child=new ArrayList<>();
        ArrayList<Person> stunt=new ArrayList<>();
        for(Person i:persons){
            if(i.country.equals(country)){
                switch(String.valueOf(i.getClass()).split(" ")[1]){
                    case "Director":
                        dir.add(i);
                        break;
                    case "Writer":
                        wri.add(i);
                        break;
                    case "Actor":
                        act.add(i);
                        break;
                    case "ChildActor":
                        child.add(i);
                        break;
                    case "StuntPerformer":
                        stunt.add(i);
                        break;
                }
            }
        }
        writer.write("Directors:\n");
        if(!dir.isEmpty())
        for(int m=0;m<dir.size();m++){
            Director i2=(Director)dir.get(m);
            writer.write(dir.get(m).name+" "+dir.get(m).surname+" "+i2.getAgent()+"\n");
        }else{writer.write("No result\n");}

        writer.write("\nWriters:\n");
        if(!wri.isEmpty()){
            for(int t=0;t<wri.size();t++){
                Writer i3=(Writer)wri.get(t);
                writer.write(wri.get(t).name+" "+wri.get(t).surname+" "+i3.writingType+"\n");}
        }else{writer.write("No result\n");}

        writer.write("\nActors:\n");
        if(!act.isEmpty()){
            for(int y=0;y<act.size();y++){
                Actor i4=(Actor) act.get(y);
                String height=String.valueOf(i4.height);
                String q=null;
                if (height.contains(".0")) {
                    q = height.replace(".0", "");
                }
                writer.write(act.get(y).name+" "+act.get(y).surname+" "+q+" cm"+"\n");}
        }else{writer.write("No result\n");}

        writer.write("\nChildActors:\n");
            if(!child.isEmpty()){
                for(int z=0;z<child.size();z++){
                    ChildActor i5=(ChildActor) child.get(z);
                    writer.write(child.get(z).name+" "+child.get(z).surname+" "+i5.age+"\n");}
            }else{writer.write("No result\n");}

        writer.write("\nStuntPerformers:\n");
            if(!stunt.isEmpty()){
                for(int v=0;v<stunt.size();v++){
                    StuntPerformer i6=(StuntPerformer) stunt.get(v);
                    String height=String.valueOf(i6.getHeight());
                    String a=null;
                    if (height.contains(".0")) {
                        a = height.replace(".0", "");
                    }
                    writer.write(stunt.get(v).name+" "+stunt.get(v).surname+" "+ a+" cm \n");}
            }else{writer.write("No result\n");}

        writer.write("\n-----------------------------------------------------------------------------------------------------\n");
    }

}


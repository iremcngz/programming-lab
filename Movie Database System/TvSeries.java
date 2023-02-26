
public class TvSeries extends Films{
    String startDate,endDate;
    int numberOfSeasons,numberOfepisodes;
    public String genre;
    public String writers;
    double rate;
    static double numberOfRate;

    //constructor
    public TvSeries(String id, String title, String language, String directors, int runtime, String country,
                    String cast, String genreOfSeries,String writers,String startDate,
                    String endDate, int numberOfSeasons, int numberOfEpisodes) {
        super(id, title, language, country, directors, cast, runtime);
        this.genre=genreOfSeries;
        this.writers=writers;
        this.startDate=startDate;
        this.endDate=endDate;
        this.numberOfSeasons=numberOfSeasons;
        this.numberOfepisodes=numberOfEpisodes;
    }




    public String getGenreOfSeries(){return genre;}
    public void setGenreOfSeries(String genre){
        this.genre=genre;
    }

    public String getWritersOfSeries(){return writers;}
    public void setWritersOfSeries(String writerpar){
        this.writers=writerpar;
    }

    public String toString(){return "TVSeries";}
}

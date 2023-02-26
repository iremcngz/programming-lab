import java.util.ArrayList;

public class Documentary extends Films{
    public String releaseDate;
    double rate;
    static double numberOfRate;


    //constructor
    public Documentary(String id, String title, String language, String directors, int runtime, String country,
                       String cast, String releaseDate) {
        super(id, title, language, country, directors, cast, runtime);
        this.releaseDate=releaseDate;
    }



    public String toString(){
        return "Documentary";
    }
}

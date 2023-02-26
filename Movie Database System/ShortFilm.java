import java.util.ArrayList;

public class ShortFilm extends Films{
    public String releaseDate;
    public String writers;
    public int runtime;
    private String genre;
  //performer=cast
    double rate;
    static double numberOfRate;

    //constructor
    public ShortFilm(String id,String title,String language,String directors,int runtime,String country,
                     String cast, String  filmGenre, String releaseDate,String writers) throws Exception {
        super(id,title,language,country,directors,cast,runtime);

        this.genre=filmGenre;
        this.writers=writers;
        setRuntime(runtime);
        this.releaseDate=releaseDate;
    }




    public boolean setRuntime(int rt){
        if(rt<=40){
        this.runtime=rt;
        return true;
        }
        else{
            return false;
        }
    }
    public int getRuntime(){return runtime;}


    public String getFilmGenre(){return genre;}
    public void setFilmGenre(String genre){
        this.genre=genre;
    }

    public String getWriters(){return writers;}
    public void setWriters(String writerpar){
        this.writers=writerpar;
    }

    public String toString(){return "ShortFilm";}

}

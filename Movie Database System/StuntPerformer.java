public class StuntPerformer extends Performer {
    private String realActorsId;
    private int height;

    StuntPerformer(String id,String name, String surname, String country,  int height, String realActorsId) {
        super(name, surname, country, id);
        this.realActorsId=realActorsId;
        this.height=height;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }



    public String getRealActorsId() {
        return realActorsId;
    }

    public void setRealActorsId(String realActorsId) {
        this.realActorsId = realActorsId;
    }
}

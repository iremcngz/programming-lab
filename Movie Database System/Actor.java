public class Actor extends Performer {
    double height;

    Actor(String id,String name, String surname, String country, int height) {
        super(name, surname, country, id);
        this.height=height;
    }
}

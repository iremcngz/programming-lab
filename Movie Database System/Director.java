public class Director extends Artist{
    private String agent;

    Director(String id, String name, String surname, String country, String agent) {
        super(name, surname, country, id);
        this.agent=agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
    public String getAgent(){return agent;}

}

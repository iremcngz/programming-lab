public class Article {
    private int publishYear;
    private String name, publisherName, paperId;

    Article(String paperId, String name, String publisherName, int publishYear) {
        this.paperId = paperId;
        this.publisherName = publisherName;
        this.name = name;
        this.publishYear = publishYear;
    }

    public String getPaperId() {
        return paperId;
    }

    @Override
    public String toString() {
        return "+" + paperId
                + "\t" + name
                + '\t' + publisherName
                + '\t' + publishYear;
    }
}

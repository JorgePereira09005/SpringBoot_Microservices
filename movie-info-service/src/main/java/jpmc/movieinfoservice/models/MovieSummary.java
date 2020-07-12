package jpmc.movieinfoservice.models;

public class MovieSummary {

    private String overview;
    private String original_title;

    public MovieSummary() {

    }

    public MovieSummary(String overview, String original_title) {
        this.overview = overview;
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return original_title;
    }

    public void setTitle(String original_title) {
        this.original_title = original_title;
    }
}


package aleksandrkim.newsapiclient.database.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopHeadlinesResponse {

    @SerializedName("articles")
    private List<Article> articles;
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private Long totalResults;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}

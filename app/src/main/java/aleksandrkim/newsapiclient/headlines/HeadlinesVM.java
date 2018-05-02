package aleksandrkim.newsapiclient.headlines;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import aleksandrkim.newsapiclient.database.AppDatabase;
import aleksandrkim.newsapiclient.database.models.Article;
import aleksandrkim.newsapiclient.network.NetworkModule;
import aleksandrkim.newsapiclient.network.TopHeadlinesApi;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandr Kim on 02 May, 2018 11:34 PM for Newsapiclient
 */

public class HeadlinesVM extends AndroidViewModel {

    private final String TAG = "ViewModel";

    private AppDatabase db;
    private LiveData<List<Article>> articles;

    public HeadlinesVM(Application application) {
        super(application);
        init();
    }

    private void init() {
        db = AppDatabase.getDb(getApplication());
        articles = db.articleDao().getArticles();
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    public void refreshAllArticles() {
        Log.d(TAG, "refreshAllArticles: ");
        TopHeadlinesApi topHeadlinesApi = NetworkModule.getInstance().getTopHeadlinesApi();
        topHeadlinesApi.getAll("en")
                .subscribeOn(Schedulers.io())
                .map(topHeadlinesResponse -> {
                    List<Article> articles = topHeadlinesResponse.getArticles();
                    for (Article article : articles) article.inferSourceName();
                    return topHeadlinesResponse;
                })
                .subscribe((topHeadlinesResponse, throwable) -> {
                    if (throwable == null) {
                        db.articleDao().refreshAll(topHeadlinesResponse.getArticles());
                    } else {
                        Toast.makeText(getApplication(), "Error loading data\nCheck your connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

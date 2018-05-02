package aleksandrkim.newsapiclient.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import aleksandrkim.newsapiclient.database.models.Article;

/**
 * Created by Aleksandr Kim on 02 May, 2018 7:01 PM for Newsapiclient
 */

@Dao
public abstract class ArticleDao extends BaseDao<Article> {

    @Query("SELECT * FROM Article ORDER BY id DESC")
    public abstract LiveData<List<Article>> getArticles();

    @Query("DELETE FROM Article")
    public abstract void deleteAll();

    @Transaction
    public void refreshAll(List<Article> articles) {
        deleteAll();
        add(articles);
    }
}
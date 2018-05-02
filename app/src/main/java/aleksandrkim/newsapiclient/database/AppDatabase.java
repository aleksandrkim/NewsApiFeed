package aleksandrkim.newsapiclient.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import aleksandrkim.newsapiclient.database.dao.ArticleDao;
import aleksandrkim.newsapiclient.database.dao.SourceDao;
import aleksandrkim.newsapiclient.database.models.Article;
import aleksandrkim.newsapiclient.database.models.Source;

/**
 * Created by Aleksandr Kim on 02 May, 2018 6:52 PM for Newsapiclient
 */

@Database(entities = {Article.class, Source.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract SourceDao sourceDao();
    public abstract ArticleDao articleDao();

    public static AppDatabase getDb(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase").build();
        }
        return appDatabase;
    }


}

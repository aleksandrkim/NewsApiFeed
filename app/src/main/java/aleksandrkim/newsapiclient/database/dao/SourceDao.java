package aleksandrkim.newsapiclient.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import aleksandrkim.newsapiclient.database.models.Source;

/**
 * Created by Aleksandr Kim on 02 May, 2018 6:39 PM for Newsapiclient
 */

@Dao
public abstract class SourceDao extends BaseDao<Source> {

    @Query("SELECT * FROM Source ORDER BY name DESC")
    public abstract LiveData<List<Source>> getSources();

    @Query("DELETE FROM Source")
    public abstract void deleteAll();

    @Transaction
    public void refreshAll(List<Source> sources) {
        deleteAll();
        add(sources);
    }
}

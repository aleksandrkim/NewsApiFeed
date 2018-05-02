package aleksandrkim.newsapiclient.database.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Transaction;

import java.util.List;

/**
 * Created by Aleksandr Kim on 02 May, 2018 7:03 PM for Newsapiclient
 */

abstract class BaseDao<T> {

    @Insert
    public abstract void add(T t);

    @Transaction
    public void add (List<T> list) {
        for (T t : list) add(t);
    }

    @Delete
    public abstract void delete (T t);
}

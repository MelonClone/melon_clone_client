package com.devgd.melonclone.domain.user.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.devgd.melonclone.domain.user.domain.User;

import org.watermelon.framework.global.model.dao.BaseDao;

@Dao
public interface UserDao extends BaseDao {

    /**
     * Get the user from the table. Since for simplicity we only have one user in the database,
     * this query gets all users from the table, but limits the result to just the 1st user.
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM user_table LIMIT 1")
    User getUser();

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM user_table")
    void deleteAllUsers();
}

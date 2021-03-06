package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

//    @Query("SELECT * FROM task WHERE id :id")
//        Long findById(id);

//    @Query("SELECT * FROM task WHERE id IN (:TaskIds)")
//    List<Task> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM task WHERE id = :id")
     Task getTaskById(Long id);

    @Insert
    Long insertTask(Task task);


    @Query("DELETE FROM task")
    void delete();
}

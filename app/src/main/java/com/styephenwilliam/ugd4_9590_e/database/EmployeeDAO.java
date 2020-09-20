package com.styephenwilliam.ugd4_9590_e.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.styephenwilliam.ugd4_9590_e.model.Employee;

import java.util.List;

@Dao
public interface EmployeeDAO {
    @Query(value = "SELECT * FROM employee")
    List<Employee> getAll();

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);
}

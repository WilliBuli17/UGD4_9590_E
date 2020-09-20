package com.styephenwilliam.ugd4_9590_e.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.styephenwilliam.ugd4_9590_e.model.Employee;

@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDAO employeeDAO();
}


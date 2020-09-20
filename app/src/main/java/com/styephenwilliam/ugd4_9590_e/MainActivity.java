package com.styephenwilliam.ugd4_9590_e;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.styephenwilliam.ugd4_9590_e.adapter.EmpRecycleViewAdapter;
import com.styephenwilliam.ugd4_9590_e.database.DatabaseClient;
import com.styephenwilliam.ugd4_9590_e.model.Employee;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private EmpRecycleViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBtnAdd();
        setRecyclerView();
        setRefreshLayout();
        getUsers();
        setSearchView();
    }

    private void setBtnAdd(){
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment add = new AddFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, add)
                        .commit();
            }
        });
    }

    private void setRecyclerView(){
        recyclerView = findViewById(R.id.employee_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRefreshLayout(){
        refreshLayout = findViewById(R.id.swipe_refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void setSearchView(){
        searchView = findViewById(R.id.bar_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void getUsers(){
        class GetUsers extends AsyncTask<Void, Void, List<Employee>> {

            @Override
            protected List<Employee> doInBackground(Void... voids) {
                List<Employee> pegawaiList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .employeeDAO()
                        .getAll();
                return pegawaiList;
            }

            @Override
            protected void onPostExecute(List<Employee> employee) {
                super.onPostExecute(employee);
                adapter = new EmpRecycleViewAdapter(MainActivity.this, employee);
                recyclerView.setAdapter(adapter);
                if (employee.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
}
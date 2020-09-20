package com.styephenwilliam.ugd4_9590_e;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.styephenwilliam.ugd4_9590_e.database.DatabaseClient;
import com.styephenwilliam.ugd4_9590_e.model.Employee;

public class AddFragment extends Fragment {
    TextInputLayout layoutNumber, layoutName, layoutAge;
    MaterialButton btnCancel, btnAdd;
    Employee employee;

    String number, age, name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        btnCancel = view.findViewById(R.id.btnCancel);
        btnAdd = view.findViewById(R.id.btnAdd);
        layoutNumber = view.findViewById(R.id.input_number_layout);
        layoutName = view.findViewById(R.id.input_name_layout);
        layoutAge = view.findViewById(R.id.input_age_layout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancelAct(view);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                number = layoutNumber.getEditText().getText().toString();
                name = layoutName.getEditText().getText().toString();
                age = layoutAge.getEditText().getText().toString();

                if(number.isEmpty())
                    layoutNumber.setError("Please fill number correctly");

                if(name.isEmpty())
                    layoutName.setError("Please fill name correctly");

                if(age.isEmpty())
                    layoutAge.setError("Please fill age correctly");

                if(!number.isEmpty() && !name.isEmpty() && !age.isEmpty()) {
                    addUser();
                    btnCancelAct(view);
                }
            }
        });
    }

    private void btnCancelAct(View view) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(AddFragment.this).commit();
    }

    private void addUser(){

        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                employee = new Employee();
                employee.setName(name);
                employee.setAge(Integer.parseInt(age));
                employee.setNumber(number);

                DatabaseClient.getInstance(getContext())
                        .getDatabase()
                        .employeeDAO()
                        .insert(employee);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "User saved", Toast.LENGTH_SHORT).show();
            }
        }

        AddUser add = new AddUser();
        add.execute();
    }
}
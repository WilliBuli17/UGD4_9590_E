package com.styephenwilliam.ugd4_9590_e;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UpdateFragment extends Fragment {
    View view;
    Employee employee;
    TextInputLayout layoutNumber, layoutName, layoutAge;
    MaterialButton btnSave, btnDelete, btnCancel;

    String number, age, name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update, container, false);

        employee = (Employee) getArguments().getSerializable("employee");

        layoutNumber = view.findViewById(R.id.update_number_layout);
        layoutAge = view.findViewById(R.id.update_age_layout);
        layoutName = view.findViewById(R.id.update_name_layout);

        try {
            layoutNumber.getEditText().setText(employee.getNumber());
            layoutName.getEditText().setText(employee.getName());
            layoutAge.getEditText().setText(employee.getStringAge());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancleAct();
        btnSaveAct();
        btnDeleteAct();
    }

    private void cancleAct() {
        btnCancel = view.findViewById(R.id.UpdatebtnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancleAct();
            }
        });
    }

    private void btnCancleAct() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(UpdateFragment.this).commit();
    }

    private void btnSaveAct() {
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
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
                    employee.setName(name);
                    employee.setNumber(number);
                    employee.setAge(Integer.parseInt(age));

                    update(employee);
                    btnCancleAct();
                }

            }
        });
    }

    private void btnDeleteAct() {
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Ingin Menghapus?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete(employee);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                btnCancleAct();
            }
        });

    }

    private void delete(final Employee employee){
        class DeleteUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .employeeDAO()
                        .delete(employee);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
            }
        }

        DeleteUser delete = new DeleteUser();
        delete.execute();
    }

    private void update(final Employee employee){
        class UpdatePegawai extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .employeeDAO()
                        .update(employee);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User updated", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        }

        UpdatePegawai update = new UpdatePegawai();
        update.execute();
    }
}
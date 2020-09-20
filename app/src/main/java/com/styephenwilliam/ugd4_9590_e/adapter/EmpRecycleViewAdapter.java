package com.styephenwilliam.ugd4_9590_e.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.styephenwilliam.ugd4_9590_e.R;
import com.styephenwilliam.ugd4_9590_e.UpdateFragment;
import com.styephenwilliam.ugd4_9590_e.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmpRecycleViewAdapter extends RecyclerView.Adapter<EmpRecycleViewAdapter.EmpViewHolder> {

    private Context context;
    private List<Employee> employeeList;
    private List<Employee> employeeListFull = new ArrayList<>();

    public EmpRecycleViewAdapter(Context context, List<Employee> employeeList){
        this.context=context;
        this.employeeList=employeeList;
        employeeListFull.addAll(employeeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmpViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new EmpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.number_tv.setText(employee.getNumber());
        holder.name_tv.setText(employee.getName());
        holder.age_tv.setText(employee.getStringAge());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public Filter getFilter() {
        return employeeFilter;
    }

    public class EmpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView number_tv, name_tv, age_tv;

        public EmpViewHolder(@NonNull View itemView){
            super(itemView);
            number_tv = itemView.findViewById(R.id.number_text);
            name_tv = itemView.findViewById(R.id.full_name_text);
            age_tv = itemView.findViewById(R.id.age_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Employee employee = employeeList.get((getAdapterPosition()));
            Bundle bundle = new Bundle();
            bundle.putSerializable("employee", employee);
            UpdateFragment updateFragment = new UpdateFragment();
            updateFragment.setArguments(bundle);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .commit();
        }
    }

    private Filter employeeFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Employee> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filterList.addAll(employeeListFull) ;
            }
            else {
                String pattern = charSequence.toString().toLowerCase().trim();

                for(Employee E : employeeListFull) {
                    if(E.getName().toLowerCase().contains(pattern))
                        filterList.add(E);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            employeeList.clear();

            employeeList.addAll((List<Employee>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
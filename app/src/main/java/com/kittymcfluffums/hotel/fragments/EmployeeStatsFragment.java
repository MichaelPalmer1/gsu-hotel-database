package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.EmployeeStat;
import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.adapters.EmployeeStatsRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeStatsFragment extends Fragment {

    public static final ArrayList<EmployeeStat> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_stats_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        ArrayList<String> queries = new ArrayList<>();
        queries.add("SELECT 'Employee Count' as 'metric', COUNT(*) as 'value' from `Employee`;");
        queries.add("SELECT 'Max Pay / Hour' as 'metric', CONCAT('$', max(SALARY)) as 'str_value' from Employee");
        queries.add("SELECT 'Average Employee Age' as 'metric', avg(age) as 'value' from Employee");
        queries.add("select 'Employee working the most shifts' as 'metric', CONCAT(last_name, ', ', first_name) as 'str_value'" +
                " from Employee" +
                " where employee_id = (" +
                " SELECT `employee_id` as `value`" +
                " FROM `Shift_Employees` " +
                " group by `value`" +
                " order by COUNT(`employee_id`) DESC" +
                " LIMIT 1" +
                "    )");
        queries.add("SELECT " +
                "'Percent of Employees (Male)' as 'metric'," +
                " CONCAT((" +
                "        (SELECT COUNT(gender) from Employee where gender = 'M') / COUNT(gender)" +
                "    ) * 100, '%') as 'str_value'" +
                " FROM `Employee`");
        queries.add("SELECT " +
                "'Percent of Employees (Female)' as 'metric'," +
                " CONCAT((" +
                "        (SELECT COUNT(gender) from Employee where gender = 'F') / COUNT(gender)" +
                "    ) * 100, '%') as 'str_value'" +
                " FROM `Employee`");

        HotelEmpStats emp_count = new HotelEmpStats();
        String emp_query = API.buildQuery(queries.get(0));
        emp_count.execute(Constants.API_QUERY_URL, emp_query);

        HotelEmpStats max_salary = new HotelEmpStats();
        String salary_query = API.buildQuery(queries.get(1));
        max_salary.execute(Constants.API_QUERY_URL, salary_query);

        HotelEmpStats avg_age = new HotelEmpStats();
        String avg_age_query = API.buildQuery(queries.get(2));
        avg_age.execute(Constants.API_QUERY_URL, avg_age_query);

        HotelEmpStats most_frequent_employee = new HotelEmpStats();
        String most_frequent_employee_query = API.buildQuery(queries.get(3));
        most_frequent_employee.execute(Constants.API_QUERY_URL, most_frequent_employee_query);

        HotelEmpStats male_employees = new HotelEmpStats();
        String male_employees_query = API.buildQuery(queries.get(4));
        male_employees.execute(Constants.API_QUERY_URL, male_employees_query);

        HotelEmpStats female_employees = new HotelEmpStats();
        String female_employees_query = API.buildQuery(queries.get(5));
        female_employees.execute(Constants.API_QUERY_URL, female_employees_query);

        return view;
    }


    class HotelEmpStats extends API.Post {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {

                    String metric = jsonArray.getJSONObject(i).getString("metric");
                    String value = "";

                    if (jsonArray.getJSONObject(i).has("value"))
                    {
                        value = Integer.toString(jsonArray.getJSONObject(i).getInt("value"));
                    } else if (jsonArray.getJSONObject(i).has("str_value"))
                    {
                        value = jsonArray.getJSONObject(i).getString("str_value");
                    }

                    ITEMS.add(new EmployeeStat(metric, value));
                }

                recyclerView.setAdapter(new EmployeeStatsRecyclerViewAdapter(ITEMS));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

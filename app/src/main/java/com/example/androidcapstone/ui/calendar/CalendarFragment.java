package com.example.androidcapstone.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment implements OnMonthChangedListener{

    private CalendarViewModel calendarViewModel;
    private MaterialCalendarView calendarView;
    private RecyclerView calendarRecyclerView;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        //Initialize views
        calendarView = root.findViewById(R.id.calendarView);
        calendarRecyclerView = root.findViewById(R.id.calendar_feed_recycler_view);
        calendarView.setOnMonthChangedListener(this);
        displayCurrentMonthsTaskits(calendarView.getCurrentDate());
        return root;
    }

    //Converts 0-11 integer month values to String value of month name.
    private String convertIntToMonth(int i)
    {
        String month = "INVALID DIGIT GIVEN (0-11)";
        switch(i)
        {
            case 0:
                month = "January";
                break;
            case 1:
                month = "February";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "August";
                break;
            case 8:
                month = "September";
                break;
            case 9:
                month = "October";
                break;
            case 10:
                month = "November";
                break;
            case 11:
                month = "December";
                break;
        }
        return month;
    }


    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        displayCurrentMonthsTaskits(date);
    }
    
    private void displayCurrentMonthsTaskits(CalendarDay date)
    {
        List<Task> test = new ArrayList<>();
        Task t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);
        t = new Task();
        test.add(t);

        // create recycler view adapter and layout manager
        FeedAdapter adapter = new FeedAdapter(test,getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());


        // Link the adapter to the RecyclerView
        calendarRecyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        calendarRecyclerView.setLayoutManager(manager);



        list.clear();
        list.add("Now displaying all of " + convertIntToMonth(date.getMonth()) + "'s Taskits!");
        adapter.notifyDataSetChanged();

    }
}
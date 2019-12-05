package com.example.androidcapstone.ui.calendar;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class CalendarFragment extends Fragment implements OnMonthChangedListener, OnDateSelectedListener{

    private CalendarViewModel calendarViewModel;
    private MaterialCalendarView calendarView;
    private RecyclerView calendarRecyclerView;
    List<CalendarDay> dates;
    List<Task> test = new ArrayList<>();
    //private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public CalendarFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        //Initialize views
        calendarView = root.findViewById(R.id.calendarView);
        calendarRecyclerView = root.findViewById(R.id.calendar_feed_recycler_view);

        //Fill
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);

        CalendarDay day1 = CalendarDay.today();
        CalendarDay day2 = new CalendarDay(2019,9,2);
        dates = new ArrayList<>();
        dates.add(day1);
        dates.add(day2);
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {


                return dates.contains(day);
//
//                Calendar cal1 = day.getCalendar();
//                Calendar cal2 = Calendar.getInstance();
//
//                return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
//                        && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
//                        && cal1.get(Calendar.DAY_OF_YEAR) ==
//                        cal2.get(Calendar.DAY_OF_YEAR));
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.logo));
            }
        });


        displayCurrentMonthsTaskits(calendarView.getCurrentDate());
        return root;
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        displayCurrentMonthsTaskits(date);
    }

    private void displayCurrentMonthsTaskits(CalendarDay date) {

        test.clear();
        for(CalendarDay day : dates)
        {
            if(day.getMonth() == date.getMonth())
            {
                Task testTask = new Task();
                testTask.setM_CreatedOnDate(date.getDate());
                test.add(testTask);
            }
        }
        // create recycler view adapter and layout manager
        FeedAdapter adapter = new FeedAdapter(test, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());


        // Link the adapter to the RecyclerView
        calendarRecyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        calendarRecyclerView.setLayoutManager(manager);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        test.clear();

        //Pull all data with m_DueDate == date.getDate();
        //Currently pulling dummy data
        if(dates.contains(date))
        {
            Task testTask = new Task();
            testTask.setM_CreatedOnDate(date.getDate());
            test.add(testTask);
        }
        // create recycler view adapter and layout manager
        FeedAdapter adapter = new FeedAdapter(test, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());


        // Link the adapter to the RecyclerView
        calendarRecyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        calendarRecyclerView.setLayoutManager(manager);

        adapter.notifyDataSetChanged();

    }

}
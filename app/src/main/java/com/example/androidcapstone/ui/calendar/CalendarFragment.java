package com.example.androidcapstone.ui.calendar;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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

import com.example.androidcapstone.DetailedTaskActivity;
import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.example.androidcapstone.ui.public_feed.PublicFeedViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment implements OnMonthChangedListener, OnDateSelectedListener{

    private CalendarViewModel calendarViewModel;
    private MaterialCalendarView calendarView;
    private RecyclerView calendarRecyclerView;
    Date currentMonthSelected;
    List<CalendarDay> dates;
    List<Task> test = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference calTaskRef = db.collection("Task");
    private FeedAdapter calAdapter;

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
//                FirestoreRecyclerOptions<Task> tasks = setEvents();
//                return tasks.getSnapshots().

            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.logo));
            }
        });


        //displayCurrentMonthsTaskits(calendarView.getCurrentDate());
        //setUpCalRecyclerView(root);

        return root;
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        displayCurrentMonthsTaskits(date);
        currentMonthSelected = date.getDate();
    }

    private void displayCurrentMonthsTaskits(CalendarDay date) {

        test.clear();
        for(CalendarDay day : dates)
        {
            if(day.getMonth() == date.getMonth())
            {
                Task testTask = new Task();
                //testTask.setM_CreatedOnDate(date.getDate());
                test.add(testTask);

            }
        }
        // create recycler view adapter and layout manager
        /*FeedAdapter adapter = new FeedAdapter(test, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());


        // Link the adapter to the RecyclerView
        calendarRecyclerView.setAdapter(adapter);
        // Set layout for the RecyclerView
        calendarRecyclerView.setLayoutManager(manager);

        adapter.notifyDataSetChanged();*/


    }


    /** Returns the given date with time set to the end of the day */
    public static Date getEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /** Returns the given date with the time values cleared. */
    public static Date getStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private String getDate(long time)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time*1000);
        String dat = DateFormat.format("dd-MM-yyyy",cal).toString();
        return dat;
    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


        Date d = date.getDate();
        Query query = calTaskRef.whereEqualTo("m_DueDate", d);



        Log.d("TEST",getStart(d).toString() + "\n" + d.toString());
        FirestoreRecyclerOptions<Task> tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();


        Log.d("TEST","SIZE: " + tasks.getSnapshots().size());

        for(Task taskit : tasks.getSnapshots())
        {
            if(taskit.getM_DueDate() == d || taskit.getM_DueDate() != d)
            {
                calAdapter = new FeedAdapter(getContext(), tasks);

                // refer to recycler view
                RecyclerView recyclerView = getView().findViewById(R.id.calendar_feed_recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(calAdapter);
            }
        }


        calAdapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Task task = documentSnapshot.toObject(Task.class);

                String dateString = "";
                SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    dateString = sdfr.format( task.getM_DueDate() );
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
                String id = documentSnapshot.getId();
                //String id = documentSnapshot.getReference().getPath();
                Intent intent = new Intent(getContext(), DetailedTaskActivity.class);
                intent.putExtra("taskit title", task.getM_TaskName());
                intent.putExtra("taskit creator", task.getM_Creator());
                intent.putExtra("taskit priority", task.getM_Importance());
                intent.putExtra("taskit posted", task.getM_CreatedOnDate());
                intent.putExtra("taskit deadline", dateString);
                intent.putExtra("taskit description", task.getM_TaskDescription());
                intent.putExtra("taskit privacy", task.getM_Privacy());
                intent.putExtra("Document id", id);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        calAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        calAdapter.stopListening();
    }

}
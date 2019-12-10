package com.example.androidcapstone.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcapstone.DetailedTaskActivity;
import com.example.androidcapstone.FeedAdapter;
import com.example.androidcapstone.Model.Task;
import com.example.androidcapstone.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment implements OnMonthChangedListener, OnDateSelectedListener{

    private CalendarViewModel calendarViewModel;
    private MaterialCalendarView calendarView;
    Date currentMonthSelected;
    List<CalendarDay> dates;
    FirestoreRecyclerOptions<Task> tasks;
    List<Task> test = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    View root;
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference calTaskRef = db.collection("Task");
    private FeedAdapter secondAdapter;

    public CalendarFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        root = inflater.inflate(R.layout.fragment_calendar, container, false);

        //Initialize views
        calendarView = root.findViewById(R.id.calendarView);

        //Fill
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);

        // refer to recycler view
        recyclerView = root.findViewById(R.id.calendar_feed_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(secondAdapter);

        setUpCalRecyclerView();

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

    public CalendarDay getCalendarDate(Date myDate){
        CalendarDay day = CalendarDay.from(myDate.getTime());

        return day;
    }


    public Integer tsToSec8601(String timestamp){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt = sdf.parse(timestamp);
            long epoch = dt.getTime();
            return (int)(epoch/1000);
        } catch(ParseException e) {
            Toast.makeText(getActivity(), "e" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Query query = calTaskRef.whereEqualTo("m_Privacy", "Private")
                .whereGreaterThanOrEqualTo("m_DueDate",getStart(date.getDate()))
                .whereLessThanOrEqualTo("m_DueDate", getEnd(date.getDate()));

        tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();
        secondAdapter = new FeedAdapter(getContext(), tasks);
        secondAdapter.startListening();
        recyclerView.setAdapter(secondAdapter);

        secondAdapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
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

    /*
    Gets firebase info
     */
    private void setUpCalRecyclerView() {
        dates = new ArrayList<>();


        Query query = calTaskRef.whereEqualTo("m_Privacy", "Private");

        tasks = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();

        db.collection("Task").whereEqualTo("m_Privacy", "Private")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                List<Task> tasks = task.getResult().toObjects(Task.class);
                                for (Task dbDate : tasks) {
                                    //Get date from firebase
                                    //Set date to CalendarDay
                                    dates.add(CalendarDay.from(dbDate.getM_DueDate().getTime()));
                                    calendarView.addDecorator(new DayViewDecorator() {
                                        @Override
                                        public boolean shouldDecorate(CalendarDay day) {
                                            return dates.contains(day);
                                        }
                                        @Override
                                        public void decorate(DayViewFacade view) {
                                            view.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.logo));
                                        }
                                    });
                                }
                            }
                        }else {
                            Toast.makeText(getActivity(), "Error getting documents." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
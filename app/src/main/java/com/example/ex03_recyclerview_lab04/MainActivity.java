package com.example.ex03_recyclerview_lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView view;

    ArrayList<String> data = new ArrayList<>();

    MyAdapter  adapter;

    Button all, select;

    private boolean[] checkStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);

        all = findViewById(R.id.btAll);

        select = findViewById(R.id.btSelected);

        int n = new Random().nextInt(200);

        for (int i = 0; i < n; i++) {
            data.add("Item " + i);

        }
        adapter = new MyAdapter();

        view.setAdapter(adapter);

        view.setLayoutManager(new LinearLayoutManager(this));

        checkStates = new boolean[data.size()];

    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View childView = inflater.inflate(R.layout.item, null);
            return new MyViewHolder(childView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(data.get(position));

            holder.checkbox.setChecked(checkStates[position]);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"Item " +  holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
            MainActivity.this.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int size = data.size();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            data.remove(0);
                        }

                        notifyItemRangeRemoved(0, size);
                    }
                }
            });
            holder.checkbox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    CheckBox c = (CheckBox) v;

                    checkStates[ holder.getAdapterPosition()] = c.isChecked();

                }
            });
            MainActivity.this.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList items_to_delete = new ArrayList<>();
                    for (int i = 0; i < MainActivity.this.checkStates.length; ++i) {
                        if (checkStates[i]){

                            items_to_delete.add(data.get(i));
                        }
                    }

                    data.removeAll(items_to_delete);

                    notifyDataSetChanged();

                    checkStates = new boolean[data.size()];

                }
            });

        }
        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkbox;
        public MyViewHolder(View itemView) {
            super(itemView);
            // Get the layout
            textView = itemView.findViewById(R.id.textview);

            checkbox = itemView.findViewById(R.id.checkbox);


        }
    }
}
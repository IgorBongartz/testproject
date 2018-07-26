package com.example.igorbongartz.testproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Timer timer to call producers and consumers periodically
    private Timer timer = new Timer();

    // ArrayList<String> items to maintain the items producers produced and consumers consumed
    private final ArrayList<String> items = new ArrayList<>();

    // String[] item_descriptions and Random random to generate randomly items
    Random random = new Random();
    private String[] item_descriptions = new String[]{"Sushi", "Udon", "Soba", "Yakitori", "Sashimi", "Okonomiyaki", "Onigiri", "Miso soup", "Kare raisu", "Tempura"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find UI elements by id
        final ListView listView = findViewById(R.id.list_view);
        Button buttonProducer = findViewById(R.id.button_producer);
        Button buttonConsumer = findViewById(R.id.button_consumer);

        // Set up listView with simple_list_item_1
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);

        // Set up buttonProducer
        buttonProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a producer by scheduling a new TimerTask adding a randomly generated item from item_description every 3000ms to items
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                items.add(item_descriptions[random.nextInt(item_descriptions.length)]);
                                arrayAdapter.notifyDataSetChanged();

                                // Scroll to the bottom
                                listView.setSelection(arrayAdapter.getCount()-1);
                            }
                        });
                    }
                }, 0 , 3000);
            }
        });

        // Set up buttonConsumer
        buttonConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a consumer by scheduling a new TimerTask removing the last item (if available) every 4000ms from items
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!items.isEmpty()) {
                                    items.remove(items.size()-1);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }, 0 , 4000);

            }
        });
    }
}

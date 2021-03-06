package com.beakon.platty;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Beakon on 5/12/2017.
 */

public class EventAdapter extends ArrayAdapter<ParseObject> {

    public EventAdapter(Context context, List<ParseObject> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get item for this position
        Event event = new Event(getItem(position));
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        // Lookup view for data population
        View itemLayout = convertView.findViewById(R.id.eventItem);
        // Cache row position inside the button using 'setTag'
        itemLayout.setTag(position);
        // Attach the click event handler
        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                // Access the row position here to get the correct data item
                Event event = new Event(getItem(position));
                Toast.makeText(getContext(), "Event Name:" + event.getEventName(), Toast.LENGTH_SHORT).show();
            }
        });

        // Find the view for data population
        TextView eventName = (TextView) convertView.findViewById(R.id.eventTVname);
        TextView eventDate = (TextView) convertView.findViewById(R.id.eventTVDate);
        TextView eventTime = (TextView) convertView.findViewById(R.id.eventTVTime);

        // Populate the data into the views
        eventName.setText(event.getEventName());
        eventDate.setText(event.getMonth() + "-" + event.getDay() + "-" + event.getYear());
        eventTime.setText(event.getHour() + ":" + event.getMinute());

        // Return completed view to render on screen
        return convertView;
    }
}

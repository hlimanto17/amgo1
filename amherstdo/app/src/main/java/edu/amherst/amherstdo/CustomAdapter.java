package edu.amherst.amherstdo;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Hutomo Limanto on 3/5/2017.
 */



public class CustomAdapter extends ArrayAdapter implements Filterable {
    ArrayList<Model> modelItems = null;
    Context context;
    private Filter customFilter;
    ArrayList<Model> origModelItems;

    public CustomAdapter(Context context, ArrayList<Model> resource) {
        super(context, R.layout.row, resource);
        this.context = context;
        this.modelItems = resource;
        this.origModelItems = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        final TextView name = (TextView) convertView.findViewById(R.id.textView1);
        final TextView description = (TextView) convertView.findViewById(R.id.textView2);
        final TextView priority = (TextView) convertView.findViewById(R.id.textView3);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cb.isChecked()) {
                    name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    description.setPaintFlags(description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    priority.setPaintFlags(priority.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    description.setPaintFlags(description.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    priority.setPaintFlags(priority.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        name.setText(modelItems.get(position).getName());
        description.setText(modelItems.get(position).getDescription());
        priority.setText(modelItems.get(position).getPriority());

        return convertView;

    }

    @Override
    public Filter getFilter() {
        if (customFilter == null)
            customFilter = new customFilter();

        return customFilter;
    }

    public void resetData(){
        modelItems = origModelItems;
    }


    private class customFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origModelItems;
                results.count = origModelItems.size();
                Log.d(TAG, "yo, no change");
            } else {
                // We perform filtering operation
                List<Model> ItemList = new ArrayList<Model>();

                for (Model p : modelItems) {
                    if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        ItemList.add(p);
                }

                results.values = ItemList;
                results.count = ItemList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                modelItems = ((ArrayList<Model>) results.values);
                notifyDataSetChanged();
                Log.d(TAG, "datachanged");
            }

        }
    }
}




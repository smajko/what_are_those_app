package org.smajko.watapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Result> {

    public ResultAdapter(Context context, int layoutResource, List<Result> results) {
        super(context, layoutResource, results);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.result_view, null);
        }

        Result result = getItem(position);

        if (result != null) {
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView description = (TextView) view.findViewById(R.id.description);
            ImageView picture = (ImageView) view.findViewById(R.id.picture);

            if (name != null) {
                name.setText(result.name);
            }

            if (description != null) {
                description.setText(result.description);
            }

            if (picture != null) {
                picture.setImageResource(result.picture);
            }
        }

        return view;
    }
}
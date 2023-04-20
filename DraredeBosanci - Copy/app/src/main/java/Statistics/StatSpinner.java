package Statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.draredebosanci.R;

public class StatSpinner extends ArrayAdapter<String> {
    private String[] values;
    private Context context;

    public StatSpinner(Context context, int textViewResourceId, String[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.stat_spinner, parent, false);
        ImageView imageView = row.findViewById(R.id.spinnerIcon);
        TextView textView = row.findViewById(R.id.spinnerText);
        textView.setText(values[position]);
        imageView.setImageResource(R.drawable.baseline_person_24); // Replace with your desired image
        return row;
    }
}

package localhost.potlatchclient;

import java.util.ArrayList;

import localhost.potlatchclient.repository.Chain;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChainArrayAdapter extends ArrayAdapter<Chain> {
	private final Context context;
	private final ArrayList<Chain> values;

	public ChainArrayAdapter(Context context, ArrayList<Chain> values) {
		super(context, R.layout.chain_row, values);
		this.context = context;
		this.values  = values;
	    }

	    @Override
	    public void notifyDataSetChanged() {
	        // TODO Auto-generated method stub
	        super.notifyDataSetChanged();
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View rowView = null;
	        LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null)
	        	convertView = inflater.inflate(R.layout.chain_row, parent, false);
	        rowView = convertView;

	        // Displaying a textview 
	        TextView textView = (TextView) rowView.findViewById(R.id.chain_row);
	        textView.setText(values.get(position).getName());

	        return rowView;
	    }
}

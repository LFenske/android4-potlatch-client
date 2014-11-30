package localhost.potlatchclient;

import java.util.ArrayList;

import localhost.potlatchclient.repository.Media;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GiftArrayAdapter extends ArrayAdapter<Media> {

	private final Context context;
	private final ArrayList<Media> values;

	public GiftArrayAdapter(Context context, int resource, ArrayList<Media> values) {
		super(context, resource, values);
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
        	convertView = inflater.inflate(R.layout.gift_row, parent, false);
        rowView = convertView;

        Media media = values.get(position);

        // Display the image.
        ImageView imageView  = (ImageView)rowView.findViewById(R.id.gift_row_image);
        //TODO
        
        // Display the name.
        TextView nameView    = (TextView )rowView.findViewById(R.id.gift_row_name);
        nameView.setText(media.getName());
        
        // Display our touched status.
        ImageView statusView = (ImageView)rowView.findViewById(R.id.gift_row_touched);
        if (media.getLikers().contains(Config.username)) {
        	statusView.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
        	statusView.setImageResource(android.R.drawable.btn_star_big_off);
        }
        
        // Display the touch count.
        TextView countView   = (TextView )rowView.findViewById(R.id.gift_row_touch_count);
        countView.setText(media.getLikes()+"");
        
        // Display the inappropriate or obscene flag.
        ImageView inappView  = (ImageView)rowView.findViewById(R.id.gift_row_inapp);
        if (media.getFlags() != 0) {
        	inappView.setImageResource(android.R.drawable.presence_busy);
        }

        return rowView;
    }
}

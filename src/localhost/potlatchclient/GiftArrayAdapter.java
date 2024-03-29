package localhost.potlatchclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit.client.ApacheClient;
import retrofit.client.Response;
import localhost.potlatchclient.client.EasyHttpClient;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Media;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GiftArrayAdapter extends ArrayAdapter<Media> {

	private final Context context;
	private final ArrayList<Media> values;

	public GiftArrayAdapter(Context context, ArrayList<Media> values) {
		super(context, R.layout.gift_row, values);
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

        // Display the image.
        final Media media = values.get(position);

		final String TAG = "GiftArrayAdapter";
		MediaSvcApi service = new SecuredRestBuilder()
			.setClient(new ApacheClient(new EasyHttpClient()))
			.setEndpoint(Config.TEST_URL)
			.setLoginEndpoint(Config.TEST_URL + MediaSvcApi.TOKEN_PATH)
			.setUsername(Config.username)
			.setPassword(Config.PASSWORD)
			.setClientId(Config.CLIENT_ID)
			.build()
			.create(MediaSvcApi.class);
		Log.d(TAG, "service created");
	
		InputStream mediaDataStream = null;
		Response response;
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap image;
		ImageButton imageButton  = (ImageButton)rowView.findViewById(R.id.gift_row_image);

		response = service.getMediaData(media.getId());
		try {
			mediaDataStream = response.getBody().in();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(mediaDataStream, null, options);
	    options.inSampleSize = calculateInSampleSize(options, 60, 60);
	    options.inJustDecodeBounds = false;

	    response = service.getMediaData(media.getId());
		try {
			mediaDataStream = response.getBody().in();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		image = BitmapFactory.decodeStream(mediaDataStream, null, options);
		imageButton.setImageBitmap(image);
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				long mediaid = media.getId();
				intent.putExtra("mediaid", mediaid);
				intent.setClass(context, DisplayGiftActivity.class);
				Log.d(TAG, "calling DisplayGiftActivity for #"+mediaid);
				context.startActivity(intent);
			}
		});
		
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
    
    public static int calculateInSampleSize(
	            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
    }
}

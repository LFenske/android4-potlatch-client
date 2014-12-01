package localhost.potlatchclient;

import java.io.IOException;
import java.io.InputStream;

import localhost.potlatchclient.client.EasyHttpClient;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Media;
import retrofit.client.ApacheClient;
import retrofit.client.Response;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayGiftActivity extends Activity {

	final static String TAG = "DisplayGiftActivity";
	
	long mediaid;

	public DisplayGiftActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gift_display);
		if (savedInstanceState == null) {
			//TODO
		}
		Log.d(TAG, "onCreate");
		
		mediaid = getIntent().getLongExtra("mediaid", 0);

		final MediaSvcApi service = new SecuredRestBuilder()
			.setClient(new ApacheClient(new EasyHttpClient()))
			.setEndpoint(Config.TEST_URL)
			.setLoginEndpoint(Config.TEST_URL + MediaSvcApi.TOKEN_PATH)
			.setUsername(Config.username)
			.setPassword(Config.PASSWORD)
			.setClientId(Config.CLIENT_ID)
			.build()
			.create(MediaSvcApi.class);
		Log.d(TAG, "service created");

		final Media media = service.getMediaById(mediaid);

		// Display larger image.
		InputStream mediaDataStream = null;
		Response response;
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap image;
		ImageView imageView  = (ImageView)findViewById(R.id.gift_display_image);

		response = service.getMediaData(media.getId());
		try {
			mediaDataStream = response.getBody().in();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(mediaDataStream, null, options);
	    options.inSampleSize = calculateInSampleSize(options, 600, 600);
	    options.inJustDecodeBounds = false;

	    response = service.getMediaData(media.getId());
		try {
			mediaDataStream = response.getBody().in();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		image = BitmapFactory.decodeStream(mediaDataStream, null, options);
		imageView.setImageBitmap(image);
		
        // Display the name.
        TextView nameView    = (TextView )findViewById(R.id.gift_display_name);
        nameView.setText(media.getName());
        
        // Display our touched status.
        final ImageButton statusView = (ImageButton)findViewById(R.id.gift_display_touched);
        if (media.getLikers().contains(Config.username)) {
        	statusView.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
        	statusView.setImageResource(android.R.drawable.btn_star_big_off);
        }
        // Display the touch count.
        final TextView countView   = (TextView )findViewById(R.id.gift_display_touch_count);
        countView.setText(media.getLikes()+"");
        
        statusView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        Media media = service.getMediaById(mediaid);
		        if (media.getLikers().contains(Config.username)) {
					service.unlikeMedia(mediaid);
		        	statusView.setImageResource(android.R.drawable.btn_star_big_off);
		        } else {
					service.likeMedia(mediaid);
		        	statusView.setImageResource(android.R.drawable.btn_star_big_on);
		        }
		        media = service.getMediaById(mediaid);
		        countView.setText(media.getLikes()+"");
			}
		});
        
        // Display the inappropriate or obscene flag.
        final ImageButton inappView  = (ImageButton)findViewById(R.id.gift_display_inapp);
        if (media.getFlags() != 0) {
        	inappView.setImageResource(android.R.drawable.presence_busy);
        }

        inappView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Media media = service.getMediaById(mediaid);
		        if (media.getFlaggers().contains(Config.username)) {
					service.unflagMedia(mediaid);
				} else {
					service.flagMedia(mediaid);
				}
				media = service.getMediaById(mediaid);
		        if (media.getFlags() != 0) {
		        	inappView.setImageResource(android.R.drawable.presence_busy);
		        } else {
		        	inappView.setImageResource(android.R.drawable.btn_default);
		        }
			}
		});
        
		// display desc
		TextView descView     = (TextView  )findViewById(R.id.gift_display_desc);
		descView.setText(media.getDescr());

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

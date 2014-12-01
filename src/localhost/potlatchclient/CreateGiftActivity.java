package localhost.potlatchclient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.client.ApacheClient;
import retrofit.mime.TypedFile;
import localhost.potlatchclient.client.EasyHttpClient;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Media;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateGiftActivity extends Activity {

	final static String TAG = "CreateGiftActivity";

	long chainid;
	File image_file = null;

	public CreateGiftActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gift_create);
		if (savedInstanceState == null) {
			//TODO
		}
		Log.d(TAG, "onCreate");
		
		chainid = getIntent().getLongExtra("chainid", 0);

		Button cancelButton = (Button) findViewById(R.id.gift_create_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button takePictureButton = (Button) findViewById(R.id.gift_create_takepicture_button);
		takePictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				image_file = getOutputMediaFile();
				Uri  image_uri  = Uri.parse(image_file.getAbsolutePath());
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://"+image_uri));
				startActivity(intent);
			}
		});
		
		Button createGiftButton = (Button) findViewById(R.id.gift_create_create_button);
		createGiftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText nameET = (EditText) findViewById(R.id.gift_create_name);
				String name = nameET.getText().toString();

				EditText descET = (EditText) findViewById(R.id.gift_create_desc);
				String desc = descET.getText().toString();

				if (image_file == null || name.length() == 0) {
					Log.d(TAG, "image_file="+image_file+" name="+name);
					return;
				}

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
			
				Media media = new Media();
				media.setName(name);
				media.setDescr(desc);
				media.setChainid(chainid);
				media = service.addMedia(media);
				Log.d(TAG, "created media #"+media.getId());
				service.setMediaData(media.getId(), new TypedFile("image/jpeg", image_file));
				//TODO
				finish();
			}
		});
			
	}

	private static File getOutputMediaFile() {
		Log.d(TAG, "getOutputMediaFile()");
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(),
				"potlatch");
		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "failed to create directory "+mediaStorageDir);
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

}

package localhost.potlatchclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit.client.ApacheClient;
import localhost.potlatchclient.client.EasyHttpClient;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Media;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class DisplayChainActivity extends Activity {
	
	final static String TAG = "DisplayChainActivity";
	
	long chainid;

	GiftArrayAdapter adapter;
	ArrayList<Media> gift_results = new ArrayList<Media>();

	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chain_display);
		if (savedInstanceState == null) {
			//TODO
		}
		Log.d(TAG, "onCreate");
		
		chainid = getIntent().getLongExtra("chainid", 0);

		Button cancelButton = (Button) findViewById(R.id.chain_display_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button createChainButton = (Button) findViewById(R.id.gift_create_button);
		createChainButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Context context = DisplayChainActivity.this;
				intent.putExtra("chainid", chainid);
				intent.setClass(context, CreateGiftActivity.class);
				startActivity(intent);
			}
		});
		
		lv = (ListView)findViewById(R.id.gift_list);
		adapter = new GiftArrayAdapter(this, gift_results);
		
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {  //TODO What is id?
				Intent intent = new Intent();
				Context context = DisplayChainActivity.this;
				long mediaid = gift_results.get(position).getId();
				intent.putExtra("mediaid", mediaid);
				intent.setClass(context, DisplayGiftActivity.class);
				Log.d(TAG, "calling DisplayGiftActivity for #"+mediaid);
				startActivity(intent);
			}
		});

		//TODO Display rows.

	}

	@Override
	public void onResume() {
		super.onResume();
		// needs to be in another thread
		{
			final String TAG = "MainFragment:OnCreateView";
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
		
			Collection<Media> media = service.findByChainid(chainid);
			Log.d(TAG, "getMediaList succeeded");
			
			List<Media> medialist = new ArrayList<Media>(media);
			Collections.sort(medialist, new Media());
			adapter.clear();
			adapter.addAll(medialist);
			Log.d(TAG, "added");

		}

	}
	

}

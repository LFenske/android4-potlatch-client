package localhost.potlatchclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit.client.ApacheClient;
import localhost.potlatchclient.client.ChainSvcApi;
import localhost.potlatchclient.client.EasyHttpClient;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Chain;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}
		//TODO remove this abomination
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class MainFragment extends Fragment {

		ChainArrayAdapter adapter;
		ArrayList<Chain> chain_results = new ArrayList<Chain>();
		
		ListView lv;

		public MainFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			lv = (ListView)rootView.findViewById(R.id.chain_list);
			adapter = new ChainArrayAdapter(getActivity(), chain_results);
			
			// needs to be in another thread
			{
				final String TAG = "MainFragment:OnCreateView";
				ChainSvcApi service = new SecuredRestBuilder()
					.setClient(new ApacheClient(new EasyHttpClient()))
					.setEndpoint(Config.TEST_URL)
					.setLoginEndpoint(Config.TEST_URL + MediaSvcApi.TOKEN_PATH)
					.setUsername(Config.USERNAME2)
					.setPassword(Config.PASSWORD)
					.setClientId(Config.CLIENT_ID)
					.build()
					.create(ChainSvcApi.class);
				Log.d(TAG, "service created");
			
				Collection<Chain> chains = service.getChainList();
				Log.d(TAG, "getChainList succeeded");
				
				List<Chain> chainlist = new ArrayList<Chain>(chains);
				Collections.sort(chainlist, new Chain());
				adapter.clear();
				adapter.addAll(chainlist);
				Log.d(TAG, "added");

			}

			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
				}
				
			});
			return rootView;
		}
		
		@Override
		public void onResume() {
			
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			Button createChainButton = (Button) getView().findViewById(R.id.create_chain);
			createChainButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					Context context = getActivity();
					intent.setClass(context, CreateChainActivity.class);
					startActivity(intent);
				}
			});

			Button topGiversButton = (Button) getView().findViewById(R.id.top_givers);
			topGiversButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					Context context = getActivity();
					intent.setClass(context, TopGiversActivity.class);
					startActivity(intent);
				}
			});
		}
	}
}

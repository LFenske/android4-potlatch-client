/**
 * 
 */
package localhost.potlatchclient;

import localhost.potlatchclient.MainActivity.MainFragment;
import retrofit.client.ApacheClient;
//import localhost.integration.test.UnsafeHttpsClient;
import localhost.potlatchclient.client.ChainSvcApi;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Chain;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author larry
 *
 */
public class CreateChainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_chain);
		if (savedInstanceState == null) {
			//TODO
		}

		Button cancelButton = (Button) findViewById(R.id.create_chain_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button createChainButton = (Button) findViewById(R.id.create_chain_button);
		createChainButton.setOnClickListener(new OnClickListener() {

			private final String TEST_URL  = "https://localhost:8443";

	        private final String USERNAME1 = "admin";
	        private final String USERNAME2 = "user0";
	        private final String PASSWORD  = "pass";
	        private final String CLIENT_ID = "mobile";


			@Override
			public void onClick(View v) {
//				Intent intent = new Intent();
//				Context context = CreateChainActivity.this;
//				intent.setClass(context, CreateChainActivity.class);  //TODO
//				startActivity(intent);

				final String TAG = "CreateChainActivity";
				
				Log.d(TAG, "onClickView");
				
//				ChainSvcApi service = new SecuredRestBuilder()
//					.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
//					.setEndpoint(TEST_URL)
//					.setLoginEndpoint(TEST_URL + MediaSvcApi.TOKEN_PATH)
//					.setUsername(USERNAME2)
//					.setPassword(PASSWORD)
//					.setClientId(CLIENT_ID)
//					.build()
//					.create(ChainSvcApi.class);
//				Log.d(TAG, "service created");
				
				EditText nameET = (EditText) findViewById(R.id.chain_title);
				String name = nameET.getText().toString();
				Log.d(TAG, "name = "+name);
//				if (name.length() != 0) {
//					Chain chain = service.addChain(new Chain(name));
//					Log.d(TAG, "created chain #" + chain.getId());
//				}

				finish();
			}
		});

	}

}

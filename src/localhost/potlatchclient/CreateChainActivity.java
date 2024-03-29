/**
 * 
 */
package localhost.potlatchclient;

import retrofit.client.ApacheClient;
import localhost.potlatchclient.client.EasyHttpClient;
import localhost.potlatchclient.client.ChainSvcApi;
import localhost.potlatchclient.client.MediaSvcApi;
import localhost.potlatchclient.client.SecuredRestBuilder;
import localhost.potlatchclient.repository.Chain;
import android.app.Activity;
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
		setContentView(R.layout.chain_create);
		if (savedInstanceState == null) {
			//TODO
		}

		Button cancelButton = (Button) findViewById(R.id.chain_create_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button createChainButton = (Button) findViewById(R.id.chain_create_button);
		createChainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String TAG = "CreateChainActivity";
				
				Log.d(TAG, "onClickView");
				
				ChainSvcApi service = new SecuredRestBuilder()
					.setClient(new ApacheClient(new EasyHttpClient()))
					.setEndpoint(Config.TEST_URL)
					.setLoginEndpoint(Config.TEST_URL + MediaSvcApi.TOKEN_PATH)
					.setUsername(Config.username)
					.setPassword(Config.PASSWORD)
					.setClientId(Config.CLIENT_ID)
					.build()
					.create(ChainSvcApi.class);
				Log.d(TAG, "service created");
				
				EditText nameET = (EditText) findViewById(R.id.chain_title);
				String name = nameET.getText().toString();
				Log.d(TAG, "name = "+name);
				if (name.length() != 0) {
					Chain chain = service.addChain(new Chain(name));
					Log.d(TAG, "created chain #" + chain.getId());
				}

				finish();
			}
		});

	}

}

/**
 * 
 */
package localhost.potlatchclient;

//import localhost.potlatchclient.MainActivity.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Context context = CreateChainActivity.this;
				intent.setClass(context, CreateChainActivity.class);  //TODO
				startActivity(intent);
			}
		});

	}

}

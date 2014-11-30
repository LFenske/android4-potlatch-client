package localhost.potlatchclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DisplayChainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chain_display);
		if (savedInstanceState == null) {
			//TODO
		}

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
				intent.setClass(context, DisplayChainActivity.class);  //TODO
				startActivity(intent);
			}
		});

	}

}

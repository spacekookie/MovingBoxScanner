/* #########################################################################
 * Copyright (c) 2013 Random Robot Softworks
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ######################################################################### */

package de.r2soft.movescanpro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Main activity to be display on application launch. Gives access to the scan button as well as
 * credit screen. The application has no settings at this time.
 * 
 * @author Katharina
 * 
 */
public class LaunchActivity extends Activity {

  private Button scan;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	scan = (Button) this.findViewById(R.id.home_button_scan);
	scan.setOnClickListener(new OnClickListener() {

	  @Override
	  public void onClick(View v) {
		try {

		  Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		  intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

		  startActivityForResult(intent, 0);

		}
		catch (Exception e) {

		  Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
		  Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
		  startActivity(marketIntent);

		}

	  }
	});

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == 0) {

	  if (resultCode == RESULT_OK) {
		Toast.makeText(this, data.getStringExtra("SCAN_RESULT"), Toast.LENGTH_LONG).show();
	  }
	  if (resultCode == RESULT_CANCELED) {
		Toast.makeText(this, "The user aborded the scan", Toast.LENGTH_LONG).show();
	  }
	}
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
  }

}

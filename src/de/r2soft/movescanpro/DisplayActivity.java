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

import java.util.ArrayList;
import java.util.HashSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayActivity extends Activity {

  private Button name, room;
  private Bundle info;
  private ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_display);

	info = getIntent().getExtras();

	name = (Button) this.findViewById(R.id.display_name);
	room = (Button) this.findViewById(R.id.display_room);
	listView = (ListView) this.findViewById(R.id.itemList);

	name.setText(info.getString("name"));
	room.setText(info.getString("location"));

	if (info.getSerializable("item") instanceof HashSet<?>) {
	  HashSet<String> temp = (HashSet<String>) info.getSerializable("item");
	  ArrayList<String> list = new ArrayList<String>();
	  for (String item : temp) {
		list.add(item);
	  }
	  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listitem,
		  R.id.item_list_label, list);
	  listView.setAdapter(arrayAdapter);

	}
	else {
	  Toast.makeText(this, "Something went really really wrong :(", Toast.LENGTH_LONG).show();
	}

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
  }
}

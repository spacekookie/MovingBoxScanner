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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
  private NodeList list;

  /** XML parsing points */
  private String _name = "name";
  private String _location = "location";
  private String _item = "item";

  /** QR code Data */
  private String name = null, location = null;
  private HashSet<String> items;

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
		try {
		  this.readData(data.getStringExtra("SCAN_RESULT"));
		}
		catch (ParserConfigurationException e) {
		  Toast.makeText(this, R.string.toast_error_canceled, Toast.LENGTH_SHORT).show();
		  return;
		}
		catch (SAXException e) {
		  Toast.makeText(this, R.string.toast_error_readfail, Toast.LENGTH_SHORT).show();
		  return;
		}
		catch (IOException e) {
		  Toast.makeText(this, R.string.toast_error_readfail, Toast.LENGTH_SHORT).show();
		  return;
		}
	  }
	  if (resultCode == RESULT_CANCELED) {
		Toast.makeText(this, R.string.toast_error_canceled, Toast.LENGTH_LONG).show();
	  }

	  if (name != null) {
		Intent redirect = new Intent(this, DisplayActivity.class);
		Bundle info = new Bundle();
		info.putString(_name, name);
		info.putString(_location, location);
		info.putSerializable(_item, items);
		redirect.putExtras(info);

		this.wipeData();

		this.startActivity(redirect);
	  }

	}
  }

  private void wipeData() {
	name = null;
	location = null;
	items = null;
	list = null;
  }

  private void readData(String data) throws ParserConfigurationException, SAXException, IOException {

	String _name = "name";
	String _location = "location";
	String _item = "item";

	items = new HashSet<String>();

	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(new ByteArrayInputStream(data.getBytes()));
	doc.getDocumentElement().normalize();

	/** Reads the box name */
	list = doc.getElementsByTagName(_name);
	for (int i = 0; i < list.getLength(); i++) {
	  Node node = list.item(i);
	  if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
		Element element = (Element) node;
		if (_name.equals(element.getNodeName())) {
		  name = element.getTextContent();
		}
	  }
	}

	/** Reads the box location */
	list = doc.getElementsByTagName(_location);
	for (int j = 0; j < list.getLength(); j++) {
	  Node node = list.item(j);
	  if (list.item(j).getNodeType() == Node.ELEMENT_NODE) {
		Element element = (Element) node;
		if (_location.equals(element.getNodeName())) {
		  location = element.getTextContent();
		}
	  }
	}

	/** Reads the box location */
	list = doc.getElementsByTagName(_item);
	for (int k = 0; k < list.getLength(); k++) {
	  Node node = list.item(k);
	  if (list.item(k).getNodeType() == Node.ELEMENT_NODE) {
		Element element = (Element) node;
		if (_item.equals(element.getNodeName())) {
		  items.add(element.getTextContent());
		}
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

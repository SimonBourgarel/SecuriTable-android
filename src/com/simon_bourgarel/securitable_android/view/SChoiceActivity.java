/**
 *    Copyright 2014 Simon Bourgarel

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   *
   **/

package com.simon_bourgarel.securitable_android.view;

import com.securitable.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

// Class that allows the user to determine the type of password he wants : consecutive cells (linear), or non consecutive cells
// (NCC, 3 or 4).
public class SChoiceActivity extends Activity {

	private Button button_activity_schoice_linear;
	private Button button_activity_schoice_ncc3;
	private Button button_activity_schoice_ncc4;
	private Button button_activity_schoice_cancel;
	private Button button_activity_schoice_rules;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schoice);
		super.onCreate(savedInstanceState);

		// Button that opens a dialog box which briefly explains the rules
		button_activity_schoice_rules = (Button) findViewById(R.id.button_activity_schoice_rules);
		button_activity_schoice_rules.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SChoiceActivity.this);

				alertDialogBuilder
						.setCancelable(true)
						.setNeutralButton("Close", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							})
						.setMessage("Here you can choose the type of password you want: \na password composed of 3 or 4 linear cells, \na password composed of 3 non consecutives cells,\n a password composed of 4 non consecutives cells.");
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
		
		// Linear password
		button_activity_schoice_linear = (Button) findViewById(R.id.button_activity_schoice_lienar);
		button_activity_schoice_linear.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SChoiceActivity.this, SLinearActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		// Password with 3 non consecutive cells
		button_activity_schoice_ncc3 = (Button) findViewById(R.id.button_activity_schoice_ncc3);
		button_activity_schoice_ncc3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SChoiceActivity.this, S3NCCellsActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		// Password with 4 non consecutive cells
		button_activity_schoice_ncc4 = (Button) findViewById(R.id.button_activity_schoice_ncc4);
		button_activity_schoice_ncc4.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SChoiceActivity.this, S4NCCellsActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		// Button cancel, closes the program
		button_activity_schoice_cancel = (Button) findViewById(R.id.button_activity_schoice_cancel);
		button_activity_schoice_cancel.setEnabled(true);
		button_activity_schoice_cancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SystemClock.sleep(375);
				System.exit(0);
			}
		});
	}
}

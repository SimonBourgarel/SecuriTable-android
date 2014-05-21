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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.securitable.R;
import com.simon_bourgarel.securitable_android.controller.Manager;
import com.simon_bourgarel.securitable_android.model.Password;

// Unlock activity.
public class LockActivity extends Activity {

	private int[][] tab = new int[9][9]; // Creation of the table
	private Password password = new Password();
	private String passwordEntered = "";
	private String passwordGenerated = "";
	private Manager manager = new Manager();
	private Button button_activity_lock_validate_password;
	private Button button_activity_lock_help_show_password;
	private CheckBox checkBox_activity_lock_new_coordinates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the taskBar.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_lock);
	}

	@Override
	public void onStart() {
		super.onStart();

		checkBox_activity_lock_new_coordinates = (CheckBox) findViewById(R.id.checkBox_activity_lock_new_coordinates);

		Context context = getBaseContext();

		// Try to open the file
		try {
			context.openFileInput("SecuriTable_Password.txt");
			// If the file exists : unlock process
			this.tableGeneration();

			// Recovering of the stored coordinates
			manager.recover(password, context);
			
			// Generation of the password, depending on the table and the coordinates (stored in the class "Password").
			passwordGenerated = manager.generate(tab, password);
			
			button_activity_lock_validate_password = (Button) findViewById(R.id.button_activity_lock_validate_password);
			button_activity_lock_validate_password.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					EditText edittext_activity_lock_code_entered = (EditText) findViewById(R.id.edittext_activity_lock_code_entered);

					// Password entered by the user.
					passwordEntered = String.valueOf(edittext_activity_lock_code_entered.getText());

					// Comparison between the generated password and the entered password.
					if (passwordEntered.equals(passwordGenerated)) {
						if ((checkBox_activity_lock_new_coordinates).isChecked()) {	// If the user wants to change the password.
							Intent intent = new Intent(LockActivity.this, SChoiceActivity.class);
							startActivity(intent);
							finish();
						}

						SystemClock.sleep(375);
						System.exit(0);
					} else {
						Toast.makeText(LockActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
					}

				}
			});
			
			/** Button that displays the password. Do not use these lines, they are here only to help user understanding the principle of SecuriTable. **/
			button_activity_lock_help_show_password = (Button) findViewById(R.id.button_activity_lock_help_show_password);
			button_activity_lock_help_show_password.setOnClickListener(new Button.OnClickListener() {	
				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							LockActivity.this);

					alertDialogBuilder
							.setCancelable(true)
							.setNeutralButton("Close", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								})
							.setMessage("The password is: "+ passwordGenerated + ".");
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			}); /** End button help **/
			
			
		} catch (Exception e) { // / Exception : The file doesn't exist, then launching of settings.
			Intent intent = new Intent(LockActivity.this, SChoiceActivity.class);
			startActivity(intent);
			finish();
		}
	}

	// Method that generates the table with random values.
	public void tableGeneration() {
		TextView caseTab = null;
		boolean ok = false;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				do {
					tab[i][j] = (int) Math.round(Math.random() * 8 + 1);
					// Algorithm that forbids having two consecutive numbers AND ALSO forbids having generation like 101 or 636
					if (((i - 1 >= 0) && (tab[i - 1][j] == tab[i][j]))
							|| ((j - 1 >= 0) && (tab[i][j - 1] == tab[i][j]))/** Comment this lines to allow generation like 101 but still deny generation like 11 or 00. **/
							|| ((i - 2 >= 0) && (tab[i - 2][j] == tab[i][j]))
							|| ((j - 2 >= 0) && (tab[i][j - 2] == tab[i][j]))/** End comment **/
							) {
						ok = false;
					} else {
						ok = true;
					}
				} while (!ok);
				ok = false;
			}
		}

		// Filling the table.
		int[][] tabOfLayout = new int[][] {
				{ R.id.A1, R.id.A2, R.id.A3, R.id.A4, R.id.A5, R.id.A6,
					R.id.A7, R.id.A8, R.id.A9 },
					{ R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5, R.id.B6,
						R.id.B7, R.id.B8, R.id.B9 },
						{ R.id.C1, R.id.C2, R.id.C3, R.id.C4, R.id.C5, R.id.C6,
							R.id.C7, R.id.C8, R.id.C9 },
							{ R.id.D1, R.id.D2, R.id.D3, R.id.D4, R.id.D5, R.id.D6,
								R.id.D7, R.id.D8, R.id.D9 },
								{ R.id.E1, R.id.E2, R.id.E3, R.id.E4, R.id.E5, R.id.E6,
									R.id.E7, R.id.E8, R.id.E9 },
									{ R.id.F1, R.id.F2, R.id.F3, R.id.F4, R.id.F5, R.id.F6,
										R.id.F7, R.id.F8, R.id.F9 },
										{ R.id.G1, R.id.G2, R.id.G3, R.id.G4, R.id.G5, R.id.G6,
											R.id.G7, R.id.G8, R.id.G9 },
											{ R.id.H1, R.id.H2, R.id.H3, R.id.H4, R.id.H5, R.id.H6,
												R.id.H7, R.id.H8, R.id.H9 },
												{ R.id.I1, R.id.I2, R.id.I3, R.id.I4, R.id.I5, R.id.I6,
													R.id.I7, R.id.I8, R.id.I9 } };

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				caseTab = (TextView) findViewById(tabOfLayout[i][j]);
				caseTab.setText(String.valueOf(tab[i][j]));
			}
		}
	}
}

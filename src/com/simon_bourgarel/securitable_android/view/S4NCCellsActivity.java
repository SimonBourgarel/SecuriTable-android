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
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.securitable.R;
import com.simon_bourgarel.securitable_android.controller.Manager;
import com.simon_bourgarel.securitable_android.model.Password;

// Activity for configuring a password with 4 non consecutive cells.
public class S4NCCellsActivity extends Activity {

	private int[][] tab = new int[9][9];
	private Button button_activity_s4nccells_exit;
	private TextView textview_activity_s4nccells_instructions;
	private Spinner abs1, ord1, abs2, ord2, abs3, ord3, abs4, ord4;
	private Button button_activity_s4nccells_submit;
	private Password password = new Password();
	private Manager manager = new Manager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the taskBar.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_s4nccells);

		abs1 = (Spinner) findViewById(R.id.spA1);
		ord1 = (Spinner) findViewById(R.id.spO1);
		abs2 = (Spinner) findViewById(R.id.spA2);
		ord2 = (Spinner) findViewById(R.id.spO2);
		abs3 = (Spinner) findViewById(R.id.spA3);
		ord3 = (Spinner) findViewById(R.id.spO3);
		abs4 = (Spinner) findViewById(R.id.spA4);
		ord4 = (Spinner) findViewById(R.id.spO4);

		button_activity_s4nccells_exit = (Button) findViewById(R.id.button_activity_s4nccells_exit);
		button_activity_s4nccells_exit.setEnabled(false);
		button_activity_s4nccells_exit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SystemClock.sleep(375);
				System.exit(0);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

		this.tableGeneration();
		button_activity_s4nccells_submit = (Button) findViewById(R.id.button_activity_s4nccells_submit);
		button_activity_s4nccells_submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				// Deletion of the ancient file.
				Context context = getBaseContext();
				try {
					context.openFileInput("SecuriTable_Password.txt");
					context.deleteFile("SecuriTable_Password.txt");
				} catch (Exception e) {
				}

				int _abs1 = abs1.getSelectedItemPosition();
				int _ord1 = ord1.getSelectedItemPosition();
				int _abs2 = abs2.getSelectedItemPosition();
				int _ord2 = ord2.getSelectedItemPosition();
				int _abs3 = abs3.getSelectedItemPosition();
				int _ord3 = ord3.getSelectedItemPosition();
				int _abs4 = abs4.getSelectedItemPosition();
				int _ord4 = ord4.getSelectedItemPosition();

				password.set_type(2);	// Password with 4 non consecutive cells (NCC), then the type variable is equals to "2".

				if (!(((_abs1 == _abs2) && (_ord1 == _ord2))
						|| ((_abs1 == _abs3) && (_ord1 == _ord3))
						|| ((_abs1 == _abs4) && (_ord1 == _ord4))
						|| ((_abs2 == _abs3) && (_ord2 == _ord3))
						|| ((_abs2 == _abs4) && (_ord2 == _ord4))
						|| ((_abs3 == _abs4) && (_ord3 == _ord4)))) {

					password.set_abscissa1(_abs1);
					password.set_ordinate1(_ord1);
					password.set_abscissa2(_abs2);
					password.set_ordinate2(_ord2);
					password.set_abscissa3(_abs3);
					password.set_ordinate3(_ord3);
					password.set_abscissa4(_abs4);
					password.set_ordinate4(_ord4);

					if (manager.save(password, context)) {
						Toast.makeText(S4NCCellsActivity.this, "Preferences saved", Toast.LENGTH_SHORT).show();
						button_activity_s4nccells_exit = (Button) findViewById(R.id.button_activity_s4nccells_exit);
						button_activity_s4nccells_exit.setEnabled(true);
					}
				}
				// Else, the user made bad choices (at least one cell chosen several times.)
				else {
					Toast.makeText(S4NCCellsActivity.this, "Wrong coordinates", Toast.LENGTH_SHORT).show();
				}
			}
		});
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
		textview_activity_s4nccells_instructions = (TextView) findViewById(R.id.textview_activity_s4nccells_instructions);
		textview_activity_s4nccells_instructions.setText("The password will be the content of the cells choosen according to the order.\nFor example if you choose as first cell A1, then A9, I9 and finally I1, the password will be (with this randomly generated table): " + tab[0][0] + tab[8][0] + tab[8][8] + tab[0][8] + ".\nNote: the cells have to be different from each others.");
	}
}

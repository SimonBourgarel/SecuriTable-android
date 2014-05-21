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

// Activity for configuring a password with linear cells.
public class SLinearActivity extends Activity {

	private int[][] tab = new int[9][9];
	private TextView textview_activity_slinear_instructions;
	private Spinner absD, ordD, absF, ordF; // Spinners to get the coordinates.
	private Button button_activity_slinear_exit;
	private Button button_activity_slinear_submit;
	private Password password = new Password();
	private Manager manager = new Manager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the taskBar.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_slinear);
		
		absD = (Spinner) findViewById(R.id.spAD);
		ordD = (Spinner) findViewById(R.id.spOD);
		absF = (Spinner) findViewById(R.id.spAF);
		ordF = (Spinner) findViewById(R.id.spOF);

		button_activity_slinear_exit = (Button) findViewById(R.id.button_activity_slinear_exit);
		button_activity_slinear_exit.setEnabled(false);
		button_activity_slinear_exit.setOnClickListener(new Button.OnClickListener() {
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
		button_activity_slinear_submit = (Button) findViewById(R.id.button_activity_slinear_submit);
		button_activity_slinear_submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				// Deletion of the ancient file.
				Context context = getBaseContext();
				try {
					context.openFileInput("SecuriTable_Password.txt");
					context.deleteFile("SecuriTable_Password.txt");
				} catch (Exception e) {}

				int _abs1 = absD.getSelectedItemPosition();
				int _ord1 = ordD.getSelectedItemPosition();
				int _abs2 = absF.getSelectedItemPosition();
				int _ord2 = ordF.getSelectedItemPosition();
				
				password.set_type(0);	// Linear password, then the type variable is equals to "0".

				// Condition that checks if the coordinates are in the same line (and are spaced by 1 or 2 cells), or in the same column (and are spaced by 1 or 2 cells), or are in diagonal (and are spaced by 1 or 2 cells)
				
				// /| Rectiligne horizontal,4 caractères, sens normal |
				// rect hor
				// 4 char, sens inverse | Rect hor, 3 char, sens lecture
				// | rect
				// hor 3 char, sens inverse | rect vert 4 char, sens
				// descendant.
				// | rect vert 4 char, sens montant | rect vert 3 char,
				// sens
				// descendant. | rect vert 4 char, sens montant |
				if (((_abs1 == _abs2) && ((_ord1 == (_ord2 - 3))))
						|| ((_abs1 == _abs2) && ((_ord1 == (_ord2 + 3))))
						|| ((_abs1 == _abs2) && ((_ord1 == (_ord2 - 2))))
						|| ((_abs1 == _abs2) && ((_ord1 == (_ord2 + 2))))
						|| ((_abs1 == (_abs2 - 3)) && (_ord1 == _ord2))
						|| ((_abs1 == (_abs2 + 3)) && (_ord1 == _ord2))
						|| ((_abs1 == (_abs2 - 2)) && (_ord1 == _ord2))
						|| ((_abs1 == (_abs2 + 2)) && (_ord1 == _ord2))
						|| (((_abs1 - _abs2) == (_ord1 - _ord2)) && (((_abs1 - _abs2) == 2)
						|| ((_abs1 - _abs2) == 3)
						|| ((_abs1 - _abs2) == -2) || ((_abs1 - _abs2) == -3)))
						|| (((_abs1 - _abs2) == -(_ord1 - _ord2)) && (((_abs1 - _abs2) == 2)
						|| ((_abs1 - _abs2) == 3)
						|| ((_abs1 - _abs2) == -2) || ((_abs1 - _abs2) == -3)))) {
					
					password.set_abscissa1(_abs1);
					password.set_ordinate1(_ord1);
					password.set_abscissa2(_abs2);
					password.set_ordinate2(_ord2);

					if (manager.save(password, context)) {
						// Message saying that the coordinates have been correctly saved.
						Toast.makeText(SLinearActivity.this, "Preferences saved", Toast.LENGTH_SHORT).show();
						button_activity_slinear_exit = (Button) findViewById(R.id.button_activity_slinear_exit);
						// Enabling the exit button
						button_activity_slinear_exit.setEnabled(true);
					}
				}
				// Else, the user made bad choices (coordinates not linear, too long or too short)
				else {
					Toast.makeText(SLinearActivity.this, "Wrong coordinates", Toast.LENGTH_SHORT).show();
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
		textview_activity_slinear_instructions = (TextView) findViewById(R.id.textview_activity_slinear_instructions);
		textview_activity_slinear_instructions.setText("The password will be the content of the cells from the first one to the last one choosen.\nFor example if you choose as first cell A1 and as last cell A4, the password will be (with this randomly generated table): " + tab[0][0] + tab[1][0] + tab[2][0] + tab[3][0] + ".\nNote: the password's length should be of 3 or 4 cells.");
	}
}

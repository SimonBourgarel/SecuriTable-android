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

package com.simon_bourgarel.securitable_android.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.widget.Toast;

import com.simon_bourgarel.securitable_android.model.Password;

public class Manager {

	// Method that generates the password from the coordinates and the table.
	public String generate(int[][] tab, Password password){
		String string = new String();

		int abs1 = password.get_abscissa1();
		int ord1 = password.get_ordinate1();
		int abs2 = password.get_abscissa2();
		int ord2 = password.get_ordinate2();
		int abs3 = password.get_abscissa3();
		int ord3 = password.get_ordinate3();
		int abs4 = password.get_abscissa4();
		int ord4 = password.get_ordinate4();

		if(password.get_type() == 0){	// Linear

			/// If the password is vertical:
			if(abs1 == abs2){
				string = "";
				/// Up -> Down
				if(ord1 < ord2){
					for(int i = ord1 ; i < ord2 + 1 ; i++){
						string += tab[i][abs1];
					}
				}
				/// Down -> Up
				else{
					for(int i = ord1 ; i > ord2 - 1; i--){
						string += tab[i][abs1];
					}
				}
			}
			/// If the password is horizontal:
			else if(ord1 == ord2){
				string = "";
				/// Left -> Right
				if(abs1 < abs2){
					for(int i = abs1 ; i < abs2 + 1 ; i++){
						string += tab[ord1][i];
					}
				}
				/// Right -> Left
				else{
					for(int i = abs1 ; i > abs2 - 1; i--){
						string += tab[ord1][i];
					}
				}
			}
			/// If the password is in diagonal NW-SE or SE-NW
			else if((abs1 - abs2) == (ord1 - ord2)){
				string = "";
				if ((abs1 - abs2) < 0){   /// NW -> SE
					int i = abs1 - 1;
					int j = ord1 - 1;
					while((i != abs2) && (j != ord2)){
						i++;
						j++;
						string += tab[j][i];
					}
				}
				else{   /// SE -> NW
					int i = abs1 + 1;
					int j = ord1 + 1;
					while((i != abs2) && (j != ord2)){
						i--;
						j--;
						string += tab[j][i];
					}
				}
			}
			/// Else, the password is in diagonal NE-SW or SW-NE
			else{
				string = "";
				if ((abs1 - abs2) < 0){   /// SW -> NE
					int i = abs1 - 1;
					int j = ord1 + 1;
					while((i != abs2) && (j != ord2)){
						i++;
						j--;
						string += tab[j][i];
					}
				}
				else{   /// NE -> SW
					int i = abs1 + 1;
					int j = ord1 - 1;
					while((i != abs2) && (j != ord2)){
						i--;
						j++;
						string += tab[j][i];
					}
				}
			}
		}
		else if(password.get_type() == 1){	// NCC3
			string = "";
			string += tab[ord1][abs1];
			string += tab[ord2][abs2];
			string += tab[ord3][abs3];
		}
		else{	// NCC4
			string = "";
			string += tab[ord1][abs1];
			string += tab[ord2][abs2];
			string += tab[ord3][abs3];
			string += tab[ord4][abs4];
		}
		return string;
	}

	// Method that recovers the coordinates of the password from the file
	public void recover(Password password, Context context){
		String line = "";
		try{
			InputStream instream = context.openFileInput("SecuriTable_Password.txt");
			InputStreamReader inputreader = new InputStreamReader(instream);
			BufferedReader buffereader = new BufferedReader(inputreader);

			line = buffereader.readLine();
			password.set_type(Integer.parseInt(line));

			line = buffereader.readLine();
			password.set_abscissa1(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_ordinate1(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_abscissa2(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_ordinate2(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_abscissa3(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_ordinate3(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_abscissa4(Integer.parseInt(line));
			line = buffereader.readLine();
			password.set_ordinate4(Integer.parseInt(line));

			instream.close();
			buffereader.close();
		}

		catch (IOException e) { 
			Toast.makeText(context, "Fail while trying to open SecuriTable_Password.txt",Toast.LENGTH_SHORT).show();
		}
	}

	// Method that saves the data into a specific file, SecuriTable_Password.txt
	public boolean save(Password password, Context context){

		FileOutputStream fOut = null;
		OutputStreamWriter osw = null;

		try{
			// Creation of the file.
			fOut = context.openFileOutput("SecuriTable_Password.txt",32768);       
			osw = new OutputStreamWriter(fOut); 
			osw.write(String.valueOf(password.get_type())); 
			osw.write("\n");
			osw.write(String.valueOf(password.get_abscissa1()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_ordinate1()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_abscissa2()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_ordinate2()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_abscissa3()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_ordinate3()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_abscissa4()));
			osw.write("\n");
			osw.write(String.valueOf(password.get_ordinate4()));

			osw.flush(); 
		}

		catch (Exception e) { 
		}
		finally { 
			try { 
				osw.close(); 
				fOut.close(); 
			}
			catch (IOException e) { 
				return false;
			}
		}
		return true;
	}
}
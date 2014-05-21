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

package com.simon_bourgarel.securitable_android.model;

// Class that stores the password's coordinates.
public class Password {
	private int _type;	// Type of password : linear (1), 3 non consecutive cells (2), 4 non consecutive cells (3)
	private int _abscissa1;
	private int _ordinate1;
	private int _abscissa2;
	private int _ordinate2;
	private int _abscissa3;
	private int _ordinate3;
	private int _abscissa4;
	private int _ordinate4;

	public void set_type(int number){_type = number;};
	public void set_abscissa1(int number){_abscissa1 = number;};
	public void set_ordinate1(int number){_ordinate1 = number;};
	public void set_abscissa2(int number){_abscissa2 = number;};
	public void set_ordinate2(int number){_ordinate2 = number;};
	public void set_abscissa3(int number){_abscissa3 = number;};
	public void set_ordinate3(int number){_ordinate3 = number;};
	public void set_abscissa4(int number){_abscissa4 = number;};
	public void set_ordinate4(int number){_ordinate4 = number;};
	
	public int get_type(){return _type;};
	public int get_abscissa1(){return _abscissa1;};
	public int get_ordinate1(){return _ordinate1;};
	public int get_abscissa2(){return _abscissa2;};
	public int get_ordinate2(){return _ordinate2;};
	public int get_abscissa3(){return _abscissa3;};
	public int get_ordinate3(){return _ordinate3;};
	public int get_abscissa4(){return _abscissa4;};
	public int get_ordinate4(){return _ordinate4;};
	
	public Password(){
		_type = 0;
		_abscissa1 = 0;
		_ordinate1 = 0;
		_abscissa2 = 0;
		_ordinate2 = 0;
		_abscissa3 = 0;
		_ordinate3 = 0;
		_abscissa4 = 0;
		_ordinate4 = 0;
	}
}
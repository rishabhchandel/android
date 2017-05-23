package com.example.rishabhcalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{

	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	Button b7;
	Button b8;
	Button b9;
	Button b0;
	Button b_dot;
	Button b_equal;
	Button b_add;
	Button b_minus;
	Button b_mul;
	Button b_devide;
	Button b_mod;
	Button b_back;
	Button b_ac;
	TextView t;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);
		b4 = (Button)findViewById(R.id.button4);
		b5 = (Button)findViewById(R.id.button5);		
		b6 = (Button)findViewById(R.id.button6);
		b7 = (Button)findViewById(R.id.button7);
		b8 = (Button)findViewById(R.id.button8);
		b9 = (Button)findViewById(R.id.button9);
		b0 = (Button)findViewById(R.id.button0);
		
		b_dot = (Button)findViewById(R.id.buttondot);
		b_equal = (Button)findViewById(R.id.buttonequal);
		b_add = (Button)findViewById(R.id.buttonadd);
		b_minus = (Button)findViewById(R.id.buttonminus);
		b_mul = (Button)findViewById(R.id.buttonmul);
		b_devide = (Button)findViewById(R.id.buttondevi);
		b_mod = (Button)findViewById(R.id.buttonmod);
		b_back = (Button)findViewById(R.id.buttonback);
		b_ac = (Button)findViewById(R.id.buttonac);
		t = (TextView)findViewById(R.id.textView1);
		
		
	}

	public void clickButton(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button0:
			
			t.setText(t.getText().toString()+"0");
			break;
		case R.id.button1:
			t.setText(t.getText().toString()+"1");
			break;
		case R.id.button2:
			t.setText(t.getText().toString()+"2");
			break;
		case R.id.button3:
			t.setText(t.getText().toString()+"3");
			break;
		case R.id.button4:
			t.setText(t.getText().toString()+"4");
			break;
		case R.id.button5:
			t.setText(t.getText().toString()+"5");
			break;
		case R.id.button6:
			t.setText(t.getText().toString()+"6");
			break;
		case R.id.button7:
			t.setText(t.getText().toString()+"7");
			break;
		case R.id.button8:
			t.setText(t.getText().toString()+"8");
			break;
		case R.id.button9:
			t.setText(t.getText().toString()+"9");
			break;
		case R.id.buttonadd:
			t.setText(t.getText().toString()+"+");
			break;
		case R.id.buttonmul:
			t.setText(t.getText().toString()+"*");
			break;
		case R.id.buttonminus:
			t.setText(t.getText().toString()+"-");
			break;
		case R.id.buttondevi:
			t.setText(t.getText().toString()+"÷");
			break;
		case R.id.buttonac:
			t.setText("");
			break;
		case R.id.buttonequal:
			t.setText(t.getText().toString());
			String s1 =t.getText().toString();
			int d1 = 0;
			char c;
			int flag =0;
			for(int i = 0; i <s1.length(); i++){
				
				if(s1.charAt(i) !='+' && s1.charAt(i)!='-'
                        &&s1.charAt(i)!='*'&&s1.charAt(i)!='÷'&&s1.charAt(i)!='%') 
				
				{
					char c1=s1.charAt(i);
					d1=d1*10+c1-'0';
				}else{
					if(i==s1.length()){
						break;
					}else{
						char op = s1.charAt(i);
						i++;
						int d2=0;
						for(; i <s1.length(); i++){
							if(s1.charAt(i) !='+' && s1.charAt(i)!='-'
			                        &&s1.charAt(i)!='*'&&s1.charAt(i)!='÷'&&s1.charAt(i)!='%') 
							{
								char c1=s1.charAt(i);
								d2 = d2*10+c1-'0';
							}
							else break;
						}
						
						if(d2==0 && op =='÷'){
						
							t.setText("exeption devide by Zero");
							flag=1;
							break;
							
						}
						d1=fun(op,d1,d2);
						i--;
					}
					if(flag==1)break;
				}
				if(flag==1)break;
			}
			String s4= Integer.toString(d1);
			t.setText(s4);
			
			break;
		case R.id.buttonback:
				String s = t.getText().toString();
				if(s.length() ==0)t.setText("");
				else t.setText(s.substring(0, s.length()-1));
			break;
		case R.id.buttonmod:
			t.setText(t.getText().toString()+"%");
			break;
		case R.id.buttondot:
			t.setText(t.getText().toString()+".");
			break;
				
		}
		
	}
	
	public int fun(char c, int d1,int d2){
		if(c=='+'){
			return d1+d2;
		}
		else if(c=='-')return d1-d2;
		else if(c=='*')return d1*d2;
		else if(c=='÷'){
			if(d2!=0)return d1/d2;
			return 0;
			}
	
		else return d1%d2;
		
	}

	
}

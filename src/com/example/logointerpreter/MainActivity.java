package com.example.logointerpreter;


import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.logocommand.LogoCommand;
import com.example.logointerpreter.Parser;


public class MainActivity extends Activity {
	
	private MainGamePanel panel;
	private EditText editText;
	static LinkedList<Editable> taskQueue;
	static LinkedList<LogoCommand> commands;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		panel = new MainGamePanel ( this );
		requestWindowFeature ( Window . FEATURE_NO_TITLE );
		getWindow (). setFlags ( WindowManager . LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . 
				FLAG_FULLSCREEN );
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.search);
		editText.setVisibility(View.GONE);
		
		taskQueue = new LinkedList<Editable>();
		commands = new LinkedList<LogoCommand>();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void Programuj(View view) {
		if (editText.getVisibility() == View.VISIBLE) {
			editText.setVisibility(View.GONE);
		} else {
			editText.setVisibility(View.VISIBLE);
		}
			
	}
	
	public void Parse(View view) {
		if (editText.getText().toString().matches("")) {
		}
		else { 
			commands.clear();
			taskQueue.add(editText.getText());
			editText.setText("");
			Parser p = new Parser();
			p.execute();
			
			
		}
	}
	
	public void Reset(View view) {
	}

	
}
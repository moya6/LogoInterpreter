package com.example.logointerpreter;


import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
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
import com.example.turtle.TurtleSituation;


public class MainActivity extends Activity {
	
	private MainGamePanel panel;
	private EditText editText;
	static LinkedList<Editable> taskQueue;
	static LinkedList<LogoCommand> commands;
	static TurtleSituation turtle;
	//private DrawView view;
	
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
		turtle = new TurtleSituation();
		//view = new DrawView(this);
        //view.setBackgroundColor(Color.WHITE);
        //setContentView(view);
		
		
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
			this.panel.addLine(100,100,150,150);
			this.panel.addLine(150,150,150,200);
			
			commands.clear();
			taskQueue.add(editText.getText());
			//editText.setText("");
			Parser p = new Parser();
			p.execute();
			try {
				p.get(10000, TimeUnit.MILLISECONDS);
				
				//this.panel.onDraw(this.panel.getHolder().lockCanvas());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	
	public void drawResults() {
		
	}
	
	public void Reset(View view) {
	}

	
}
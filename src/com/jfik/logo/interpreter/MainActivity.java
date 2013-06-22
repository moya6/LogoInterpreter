package com.jfik.logo.interpreter;


import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.logointerpreter.R;
import com.jfik.logo.command.MarkerCommand;
import com.jfik.logo.interpreter.Parser;
import com.jfik.logo.marker.Marker;


public class MainActivity extends Activity implements OnClickListener {
	
	private MainGamePanel panel;
	private EditText editText;
	static String taskQueue;
	static LinkedList<MarkerCommand> commands;
	static Parser p;
	static boolean clrscr;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Display display = getWindowManager().getDefaultDisplay(); 
        long screenWidth = display.getWidth();
        long screenHeight = display.getHeight();
		
		requestWindowFeature ( Window . FEATURE_NO_TITLE );
		getWindow (). setFlags ( WindowManager . LayoutParams . FLAG_FULLSCREEN , WindowManager . LayoutParams . 
				FLAG_FULLSCREEN );
		
		
		FrameLayout Game = new FrameLayout(this);
        panel = new MainGamePanel (this,screenWidth,screenHeight);
        LinearLayout GameWidgets = new LinearLayout (this);

        Button pisz = new Button(this);
        pisz.setWidth(100);
        pisz.setText("Execute");
        
        editText = new EditText(this);
        editText.setVisibility(View.VISIBLE);
        
        GameWidgets.addView(pisz);       
        GameWidgets.addView(editText); 

        Game.addView(panel);
        Game.addView(GameWidgets);

        setContentView(Game);
        pisz.setOnClickListener(this);
		
        
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        
        clrscr = false;
		commands = new LinkedList<MarkerCommand>();
		p = new Parser();
			
	}
	
	public void onClick(View v) {
        parse();
   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void programuj() {
		if (editText.getVisibility() == View.VISIBLE) {
			editText.setVisibility(View.GONE);
		} else {
			editText.setVisibility(View.VISIBLE);
		}
			
	}
	
	public void parse() {
		
			taskQueue = editText.getText().toString();
			
			p.execute();
			if (clrscr == true) {
				panel.clear();
				clrscr = false;
			}
			
			panel.drawResults();
			commands.clear();
	}
	
	
	public void Reset(View view) {
	}
	
	protected void onStart() {
		super.onStart();
		panel.start();
	}
	
	public void onPause() {
	    super.onPause();
	    panel.pause();
	}
	
	public void onBackPressed(){
		super.onBackPressed();
		panel.destroy();
	}
	
	protected void onResume() {
		super.onResume();
		panel.resume();
	}
	
}
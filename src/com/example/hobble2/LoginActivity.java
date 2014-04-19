package com.example.hobble2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	protected TextView mHobbleTextView;
	protected TextView mSignInTextView;
	protected TextView mSignUpTextView;
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);
		
		Typeface Raleway = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Thin.otf");
		
		mHobbleTextView = (TextView) findViewById(R.id.login_splash_text);
		mHobbleTextView.setTypeface(Raleway);
		mSignInTextView = (TextView) findViewById(R.id.signIn); // Sign-in button
		mSignInTextView.setTypeface(Raleway);
		mSignUpTextView = (TextView) findViewById(R.id.signUp); // Sign-up button
		mSignUpTextView.setTypeface(Raleway);
		
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		
		mSignInTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString().trim();
				String password = mPassword.getText().toString().trim();
				
				if(username.isEmpty() || password.isEmpty()){
					// One of the fields was left empty
					// Create dialog alerting user
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					builder.setMessage(R.string.login_error_message) 
					.setTitle(R.string.error_title)
					.setPositiveButton(android.R.string.ok, null);
					AlertDialog alert = builder.create();
					alert.show();
				}
				else{
					// Credentials are satisfactory - login.
					setProgressBarIndeterminateVisibility(true);
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						
						@Override
						public void done(ParseUser user, ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							if(e == null){
								// User logged in successfully
								/*if(!user.getBoolean("emailVerified")){
									AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
									builder.setMessage(R.string.email_unverified)
									.setTitle(R.string.error_title)
									.setPositiveButton(android.R.string.ok, null);
									AlertDialog dialog = builder.create();
									dialog.show();
								}
								else {*/
									Intent intent = new Intent(LoginActivity.this, MainActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
								//}
							}
							else{
								// Error parsing credentials
								AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
								builder.setTitle(R.string.error_title);
								builder.setMessage(R.string.login_error_message);
							}
							
						}
					});
				}
			}
		});
		
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}

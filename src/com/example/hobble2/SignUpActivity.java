package com.example.hobble2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected String[] homepoints = new String[] { "Alice Lloyd", "Baits",
			"Betsy Barbour", "Bursley", "Couzens", "East Quad",
			"Fletcher Hall", "Markley", "Martha Cook", "MoJo", "Newberry",
			"North Quad", "Oxford", "South Quad", "Stockwell", "West Quad" };

	protected Button mSignupButton;

	protected Spinner mHomepoints;

	protected TextView mSignupSplashText;
	protected TextView mUsernameTextView;
	protected TextView mPasswordTextView;
	protected TextView mEmailTextView;
	protected TextView mHomepointsTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_sign_up);

		Typeface RalewayThin = Typeface.createFromAsset(getAssets(),
				"fonts/Raleway-Thin.otf");
		mSignupSplashText = (TextView) findViewById(R.id.signUpSplash);
		mSignupSplashText.setTypeface(RalewayThin);
		mUsernameTextView = (TextView) findViewById(R.id.signupUsernameTextView);
		mUsernameTextView.setTypeface(RalewayThin);
		mPasswordTextView = (TextView) findViewById(R.id.signupPasswordTextView);
		mPasswordTextView.setTypeface(RalewayThin);
		mEmailTextView = (TextView) findViewById(R.id.signupEmailTextView);
		mEmailTextView.setTypeface(RalewayThin);
		mHomepointsTextView = (TextView) findViewById(R.id.homepointsTextView);
		mHomepointsTextView.setTypeface(RalewayThin);

		mHomepoints = (Spinner) findViewById(R.id.homepoints);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.homepoints_spinner_layout, homepoints);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mHomepoints.setAdapter(adapter);

		mSignupButton = (Button) findViewById(R.id.signupButton);
		
		mUsername = (EditText) findViewById(R.id.signupUsernameField);
		mPassword = (EditText) findViewById(R.id.signupPasswordField);
		mEmail = (EditText) findViewById(R.id.signupEmailField);
		
		mSignupButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// getText() method returns an "Editable", need to convert to
				// String
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				String email = mEmail.getText().toString();
				String homepoint = mHomepoints.getSelectedItem().toString();

				username = username.trim();
				password = password.trim();
				email = email.trim();

				if (username.isEmpty() 
						|| password.isEmpty()
						|| email.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							SignUpActivity.this);
					builder.setMessage(R.string.empty_fields_error)
							.setTitle(R.string.error_title)
							.setPositiveButton(android.R.string.ok, null)
							.show();
				} 
				else if (!email.contains("@umich.edu")) { 
				// Checks to see if email is @umich.edu domain
					AlertDialog.Builder builder = new AlertDialog.Builder(
							SignUpActivity.this);
					builder.setMessage(R.string.invalid_email_address)
							.setTitle(R.string.error_title)
							.setPositiveButton(android.R.string.ok, null)
							.show();
				} 
				
				else {
					// Sign-up credentials accepted
					// Create new user
					setProgressBarIndeterminateVisibility(true); // Begin loading progress indiciator
																	
					ParseUser newUser = new ParseUser();
					newUser.setUsername(username);
					newUser.setPassword(password);
					newUser.setEmail(email);
					newUser.put(ParseConstants.KEY_HOMEPOINT, homepoint);
					newUser.signUpInBackground(new SignUpCallback() {

						@Override
						public void done(ParseException e) {
							// SIGN ME UP, SCOTTY
							setProgressBarIndeterminateVisibility(false);
							if (e == null) {
								String email = mEmail.getText().toString();
								// Sign-up went fine, ask user to validate his/her email
								AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
								builder.setMessage("An email has been sent to " + email + ". Please validate your email to get Hobbling!").setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
								// Navigate to login activity
								Intent intent = new Intent(SignUpActivity.this,
										LoginActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
								/*
								Intent intent = new Intent(SignUpActivity.this,
										MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
								*/
							} else {
								// YOU BLEW IT
								AlertDialog.Builder builder = new AlertDialog.Builder(
										SignUpActivity.this);
								builder.setMessage(e.getMessage())
										.setTitle(R.string.error_title)
										.setPositiveButton(android.R.string.ok,
												null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}

						}
					});

				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}

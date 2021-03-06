package com.beakon.platty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.ui.ParseLoginBuilder;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button parseLoginB = (Button)(findViewById(R.id.loginBMain));
        parseLoginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInSignUp();
            }
        });

        Button logOut = (Button) (findViewById(R.id.logOutBMain));
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
            }
        });

        Button unLinkB = (Button) findViewById(R.id.unlinkButton);
        unLinkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlink();
            }
        });

        Button linkB = (Button) findViewById(R.id.linkButton);
        linkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link();
            }
        });

        Button createEventB = (Button) findViewById(R.id.mainBCreateEvent);
        createEventB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        Button myEventB = (Button) findViewById(R.id.mainBMyEvents);
        myEventB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyEventsActivity.class);
                startActivity(intent);
            }
        });

        Button findEventB = (Button) findViewById(R.id.mainBFindEvents);
        findEventB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindEventsActivity.class);
                startActivity(intent);
            }
        });

        final LoginButton fbButton = (LoginButton) findViewById(R.id.facebook_login);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> permissions = Arrays.asList("email");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException ex) {
                        if (user == null) {
                            Log.d("Platty", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("Platty", "User signed up and logged in through Facebook!");
                        } else {
                            Log.d("Platty", "User logged in through Facebook!");
                        }
                    }
                });
            }
        });
    }

    private void logInSignUp() {
        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);
    }

    private void link() {
        ParseUser user = ParseUser.getCurrentUser();
        if (!ParseFacebookUtils.isLinked(user)) {
            List<String> permissions = Arrays.asList("email");
            ParseFacebookUtils.linkWithReadPermissionsInBackground(
                    user, MainActivity.this, permissions, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.d("Platty", "Woohoo, user logged in with Facebook!");
                        }
                    });
        }

    }

    private void unlink() {
        ParseFacebookUtils.unlinkInBackground(ParseUser.getCurrentUser(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Platty", "The user is no longer associated with their Facebook account.");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
}
package com.example.hobble2;

import java.util.List;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class BuddiesFragment extends ListFragment {

	public static final String TAG = BuddiesFragment.class.getSimpleName();
	

	protected ParseUser mCurrentUser;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected List<ParseUser> mFriends;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_buddies_fragment,
				container, false);
		return rootView;
		
	}

	@Override
	public void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		mFriendsRelation = mCurrentUser
				.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		ParseQuery<ParseUser> user = mFriendsRelation.getQuery();
		user.addAscendingOrder(ParseConstants.KEY_USERNAME);
		mFriendsRelation.getQuery().findInBackground(
				new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> friends, ParseException e) {
						getActivity().setProgressBarIndeterminateVisibility(
								false);
						if (e == null) { // Everything went ok
							mFriends = friends;

							String usernames[] = new String[mFriends.size()];
							int i = 0;
							for (ParseUser user : mFriends) {
								usernames[i] = user.getUsername();
								i++;
							}
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									getListView().getContext(),
									android.R.layout.simple_list_item_1,
									usernames);
							setListAdapter(adapter);
						} 
						else {
							// There was an error
							Log.e(TAG, e.getMessage());
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getListView().getContext());
							builder.setMessage(e.getMessage())
									.setTitle(R.string.error_title)
									.setPositiveButton(android.R.string.ok, null);
							AlertDialog dialog = builder.create();
							dialog.show();
						}
					}
				});
	}

}

package com.whats.saver;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class VideoActivity extends AppCompatActivity {
	
	private String path = "";
	private String title = "";
	private double n = 0;
	private double position = 0;
	private double exist = 0;
	private double progress = 0;
	
	private ArrayList<HashMap<String, Object>> video = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout linear1;
	
	private Intent i = new Intent();
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.video);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		linear1 = findViewById(R.id.linear1);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		
		linear1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (mMediaController.isShowing()) {
					            mMediaController.hide();
					        } else {
					            mMediaController.show();
					        }
			}
		});
	}
	
	private void initializeLogic() {
		
		if (!file.getString("data", "").equals("")) {
			data = new Gson().fromJson(file.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			
			//Window w = this.getWindow();
			//w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			//w.setStatusBarColor(Color.parseColor("#000000"));
			
		}
		path = getIntent().getStringExtra("path");
		title = getIntent().getStringExtra("title");
		video = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		n = 0;
		for(int _repeat23 = 0; _repeat23 < (int)(video.size()); _repeat23++) {
			if (video.get((int)n).get("fileName").toString().equals(title)) {
				position = n;
				break;
			}
			else {
				n++;
			}
		}
		View inflate = getLayoutInflater().inflate(R.layout.videoview, null);
		linear1.addView(inflate);
		mVideoView = (UniversalVideoView) inflate.findViewById(R.id.videoView);
		
		mMediaController = (UniversalMediaController) inflate.findViewById(R.id.media_controller);
		        mVideoView.setMediaController(mMediaController);
		ImageButton previous = (ImageButton) mMediaController.findViewById(R.id.previous);
		ImageButton next = (ImageButton) mMediaController.findViewById(R.id.next);
		final TextView textnew = (TextView) mMediaController.findViewById(R.id.textnew);
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		shape.setCornerRadii(new float[] { 30, 30, 30, 30, 30, 30, 30, 30 });
		shape.setColor(Color.parseColor("#f44336"));
		textnew.setBackgroundDrawable(shape);
		_playVideo(path, title);
		previous.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				_playPrevious();
			}
		});
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				_playNext();
			}
		});
		if (getIntent().getStringExtra("item").equals("recent")) {
			if (Double.parseDouble(getIntent().getStringExtra("new")) == 1) {
				textnew.setVisibility(View.GONE);
			}
			else {
				textnew.setVisibility(View.VISIBLE);
			}
		}
		else {
			textnew.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (mVideoView != null && mVideoView.isPlaying()) {
			            
			            mVideoView.pause();
			
			progress = mVideoView.getCurrentPosition();
			        }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (progress > 0) {
			mVideoView.seekTo((int)progress);
		}
	}
	public void _library() {
	}
	UniversalVideoView mVideoView;
	UniversalMediaController mMediaController;
	{
	}
	
	
	public void _playNext() {
		if (position == (video.size() - 1)) {
			position = 0;
			path = video.get((int)position).get("filePath").toString();
			title = video.get((int)position).get("fileName").toString();
			_playVideo(path, title);
		}
		else {
			position = position + 1;
			path = video.get((int)position).get("filePath").toString();
			title = video.get((int)position).get("fileName").toString();
			_playVideo(path, title);
		}
	}
	
	
	public void _playPrevious() {
		if (position == 0) {
			position = video.size() - 1;
			path = video.get((int)position).get("filePath").toString();
			title = video.get((int)position).get("fileName").toString();
			_playVideo(path, title);
		}
		else {
			position = position - 1;
			path = video.get((int)position).get("filePath").toString();
			title = video.get((int)position).get("fileName").toString();
			_playVideo(path, title);
		}
	}
	
	
	public void _playVideo(final String _path, final String _name) {
		mVideoView.setVideoPath(_path);
		mVideoView.requestFocus();
		mVideoView.start();
		mMediaController.setTitle(_name);
		mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override public void onCompletion(MediaPlayer mediaPlayer) {
				_playNext();
			}});
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
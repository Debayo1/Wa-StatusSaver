package com.whats.saver;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
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
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double n = 0;
	private HashMap<String, Object> mapvar = new HashMap<>();
	private double recentVideoN = 0;
	private String fileName = "";
	private String filePath = "";
	private double duration = 0;
	private String videoDuration = "";
	private double pagePosition = 0;
	private double recentImageN = 0;
	private double recentNewN = 0;
	private double notNew = 0;
	private double recentNewM = 0;
	private String testMessage = "";
	private String type = "";
	private boolean isVideo = false;
	private boolean isImage = false;
	private double dataN = 0;
	private double newN = 0;
	private double capacity_file = 0;
	private double capacity_file_tmp = 0;
	private double progress_file = 0;
	private double EnableRemove = 0;
	private double itemPosition = 0;
	private double clickPosition = 0;
	private String outPath = "";
	private String inPath = "";
	private boolean selectAll = false;
	private boolean itemChecked = false;
	private double want_delete = 0;
	private double selected_items = 0;
	private double splash = 0;
	private double newItem = 0;
	
	private ArrayList<String> allFile = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> recent = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> saved = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> shared = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> recentVideos = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allMedia = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> newData = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> recentImages = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> downloadFile = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> savedVideos = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> savedImages = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> saved_data = new ArrayList<>();
	
	private LinearLayout linear1;
	
	private SharedPreferences file;
	private AlertDialog.Builder dialog;
	private Intent i = new Intent();
	private TimerTask timer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = findViewById(R.id.linear1);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
	}
	
	private void initializeLogic() {
		
		i.setClass(getApplicationContext(), SplashActivity.class);
		splash = 0;
		startActivity(i);
		file.edit().putString("MyService", "stop").commit();
		pagePosition = 0;
		EnableRemove = 0;
		if (!file.getString("data", "").equals("")) {
			data = new Gson().fromJson(file.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		viewPager1 = new androidx.viewpager.widget.ViewPager(this);
		viewPager1.setAdapter(new MyPagerAdapter());
		viewPager1.setCurrentItem(0);
		tabLayout = new com.google.android.material.tabs.TabLayout(this);
		//tabLayout.setTabMode(com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE);
		tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
		tabLayout.setTabTextColors(Color.parseColor("#607d8b"), Color.parseColor("#ffffff"));
		tabLayout.setupWithViewPager(viewPager1);
		((com.google.android.material.appbar.AppBarLayout)_toolbar.getParent()).addView(tabLayout);
		linear1.addView(viewPager1);
		viewPager1.addOnPageChangeListener(new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
			
			public void onPageScrollStateChanged(int state) {}
			
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
			
			public void onPageSelected(int position) {
				pagePosition = (int) position;
				invalidateOptionsMenu();
			}});
	}
	
	@Override
	public void onBackPressed() {
		if (EnableRemove == 1) {
			_cancel();
		}
		else {
			if (pagePosition == 1) {
				viewPager1.setCurrentItem(0);
			}
			else {
				recentNewN = 0;
				for(int _repeat11 = 0; _repeat11 < (int)(recent.size()); _repeat11++) {
					recentNewM = 0;
					notNew = 0;
					for(int _repeat15 = 0; _repeat15 < (int)(data.size()); _repeat15++) {
						if (recent.get((int)recentNewN).get("fileName").toString().equals(data.get((int)recentNewM).get("fileName").toString())) {
							notNew = 1;
							break;
						}
						else {
							recentNewM++;
						}
					}
					if (!(notNew == 1)) {
						{
							HashMap<String, Object> _item = new HashMap<>();
							_item.put("fileName", recent.get((int)recentNewN).get("fileName").toString());
							data.add(_item);
						}
						
					}
					recentNewN++;
				}
				file.edit().putString("data", new Gson().toJson(data)).commit();
				file.edit().putString("MyService", "start").commit();
				file.edit().putString("notification", "true").commit();
				finish();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		file.edit().putString("MyService", "start").commit();
		file.edit().putString("notification", "true").commit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (splash == 0) {
			if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
			}else{
				splash = 1;
				_getMedia();
				_saveData();
				mapvar = new HashMap<>();
				filePath = "/sdcard/img3.jpg";
				mapvar.put("filePath", filePath);
				fileName = new java.io.File(filePath).getName();
				mapvar.put("fileName", fileName);
				mapvar.put("type", "image");
				mapvar.put("fileType", "ads");
				mapvar.put("download", "false");
				mapvar.put("downloadable", "false");
				recent.add((int)0, mapvar);
				file.edit().putString("lastLength", String.valueOf((long)(recent.size()))).commit();
				recentAdapter.notifyDataSetChanged();
				savedAdapter.notifyDataSetChanged();
			}
		}
	}
	
	@Override
	protected void onPostCreate(Bundle _savedInstanceState) {
		super.onPostCreate(_savedInstanceState);
		_Savedadapter();
		_Recentadapter();
	}
	public void _viewPager() {
	}
	int[] pageId = {
		R.layout.recent, R.layout.saved
	};
	
	private class MyPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
		
		
		public int getCount() {
			return pageId.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			
			String[] pageTitle = {"RECENT", "SAVED"};
			return pageTitle[position];
			
		}
		
		
		public Object instantiateItem(ViewGroup collection, int position) {
			
			View view = getLayoutInflater().inflate(pageId[position], null);
			((androidx.viewpager.widget.ViewPager)collection).addView(view, 0);
			if (position == 0) {
				LinearLayout linear1 = (LinearLayout) view.findViewById(R.id.linear2);
				View inflate = getLayoutInflater().inflate(R.layout.videorecyclerview, null);
				gridview1 = (androidx.recyclerview.widget.RecyclerView) inflate.findViewById(R.id.recycler_view);
				
				androidx.recyclerview.widget.GridLayoutManager staggeredGridLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getApplicationContext(),3);
				gridview1.setLayoutManager(staggeredGridLayoutManager); 
				gridview1.setHasFixedSize(true);
				int spanCount = 3;
				int spacing = 24;
				boolean includeEdge = true;
				
				gridview1.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
				_Recentadapter();
				gridview1.setAdapter(recentAdapter);
				linear1.addView(inflate);
			}
			if (position == 1) {
				LinearLayout linear1 = (LinearLayout) view.findViewById(R.id.linear1);
				View inflate = getLayoutInflater().inflate(R.layout.savedlayout, null);
				bottom_bar = (androidx.cardview.widget.CardView) inflate.findViewById(R.id.bottom_bar);
				checkbox1 = (CheckBox) inflate.findViewById(R.id.checkbox1);
				checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
								@Override
								public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
										final boolean _isChecked = _param2;
						selectAll = _isChecked;
						_select_all(selectAll);
					}});
				LinearLayout delete = (LinearLayout) inflate.findViewById(R.id.delete);
				delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						dialog.setTitle("Delete selected status");
						dialog.setMessage("Do you really want to remove selected saved status");
						dialog.setPositiveButton("remove", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								new AsyncTaskDelete().execute();
							}
						});
						dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog.create().show();
					}});
				gridview2 = (androidx.recyclerview.widget.RecyclerView) inflate.findViewById(R.id.recycler_view);
				
				androidx.recyclerview.widget.GridLayoutManager staggeredGridLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getApplicationContext(),3);
				gridview2.setLayoutManager(staggeredGridLayoutManager); 
				gridview2.setHasFixedSize(true);
				int spanCount = 3;
				int spacing = 24;
				boolean includeEdge = true;
				
				gridview2.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
				_Savedadapter();
				gridview2.setAdapter(savedAdapter);
				linear1.addView(inflate);
			}
			return view;
		}
		
		
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((androidx.viewpager.widget.ViewPager) arg0).removeView((View) arg2);
		}
		
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}
	}
	{
	}
	
	
	public void _library() {
	}
	androidx.viewpager.widget.ViewPager viewPager1;
	
	com.google.android.material.tabs.TabLayout tabLayout;
	
	com.google.android.material.floatingactionbutton.FloatingActionButton fab;
	
	androidx.recyclerview.widget.RecyclerView gridview1,gridview2;
	
	androidx.cardview.widget.CardView bottom_bar;
	SavedAdapter savedAdapter;
	RecentAdapter recentAdapter;
	CheckBox checkbox1;
	SquaredFrameLayout squaredFrameLayout;
	{
	}
	public static boolean isImageFile(String path) {
		String mimeType = java.net.URLConnection.guessContentTypeFromName(path);
		return mimeType != null && mimeType.startsWith("image");
	}
	{
	}
	public static boolean isVideoFile(String path) {
		    String mimeType = java.net.URLConnection.guessContentTypeFromName(path);
		    return mimeType != null && mimeType.startsWith("video");
	}
	{
	}
	private com.bumptech.glide.RequestManager initGlide(){
		
		return Glide.with(getApplicationContext());
		
	}
	{
	}
	
	
	public void _getRecentVideo() {
		recentVideoN = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allFile.size()); _repeat11++) {
			if (Uri.parse((String) allFile.get((int) recentVideoN)).getLastPathSegment().endsWith(".mp4")) {
				mapvar = new HashMap<>();
				filePath = allFile.get((int)(recentVideoN));
				mapvar.put("filePath", filePath);
				fileName = new java.io.File(filePath).getName();
				mapvar.put("fileName", fileName);
				android.media.MediaMetadataRetriever retriever = new android.media.MediaMetadataRetriever();
				
				retriever.setDataSource(filePath);
				
				duration = (double) Long.parseLong(retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION));
				
				retriever.release();
				mapvar.put("duration", String.valueOf((long)(duration)));
				mapvar.put("type", "video");
				mapvar.put("item", "recent");
				mapvar.put("download", "false");
				mapvar.put("fileType", "media");
				mapvar.put("downloadable", "true");
				allMedia.add(mapvar);
			}
			recentVideoN++;
		}
	}
	
	
	public void _getRecentImage() {
		recentImageN = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allFile.size()); _repeat11++) {
			if (Uri.parse((String) allFile.get((int) recentImageN)).getLastPathSegment().endsWith(".jpg")) {
				mapvar = new HashMap<>();
				filePath = allFile.get((int)(recentImageN));
				mapvar.put("filePath", filePath);
				fileName = new java.io.File(filePath).getName();
				mapvar.put("fileName", fileName);
				mapvar.put("type", "image");
				mapvar.put("fileType", "media");
				mapvar.put("download", "false");
				mapvar.put("downloadable", "true");
				allMedia.add(mapvar);
			}
			recentImageN++;
		}
	}
	
	
	public void _getSavedVideo() {
		recentVideoN = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allFile.size()); _repeat11++) {
			if (Uri.parse((String) allFile.get((int) recentVideoN)).getLastPathSegment().endsWith(".mp4")) {
				mapvar = new HashMap<>();
				filePath = allFile.get((int)(recentVideoN));
				mapvar.put("filePath", filePath);
				fileName = new java.io.File(filePath).getName();
				mapvar.put("fileName", fileName);
				android.media.MediaMetadataRetriever retriever = new android.media.MediaMetadataRetriever();
				
				retriever.setDataSource(filePath);
				
				duration = (double) Long.parseLong(retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION));
				
				retriever.release();
				mapvar.put("duration", String.valueOf((long)(duration)));
				mapvar.put("type", "video");
				mapvar.put("enable_remove", "false");
				mapvar.put("remove", "false");
				mapvar.put("item", "saved");
				saved.add(mapvar);
				savedVideos.add(mapvar);
			}
			recentVideoN++;
		}
	}
	
	
	public void _getSavedImage() {
		recentImageN = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allFile.size()); _repeat11++) {
			if (Uri.parse((String) allFile.get((int) recentImageN)).getLastPathSegment().endsWith(".jpg")) {
				mapvar = new HashMap<>();
				filePath = allFile.get((int)(recentImageN));
				mapvar.put("filePath", filePath);
				fileName = new java.io.File(filePath).getName();
				mapvar.put("fileName", fileName);
				mapvar.put("enable_remove", "false");
				mapvar.put("remove", "false");
				mapvar.put("type", "image");
				saved.add(mapvar);
				savedImages.add(mapvar);
			}
			recentImageN++;
		}
	}
	
	
	public void _getMedia() {
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/WhatsApp/Media/.Statuses"))) {
			allFile.clear();
			FileUtil.listDir(FileUtil.getExternalStorageDir().concat("/WhatsApp/Media/.Statuses"), allFile);
			_getRecentVideo();
			_getRecentImage();
			_getOrdered();
		}
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/WhatsSaver"))) {
			allFile.clear();
			FileUtil.listDir(FileUtil.getExternalStorageDir().concat("/WhatsSaver"), allFile);
			_getSavedVideo();
			_getSavedImage();
			n = 0;
			for(int _repeat28 = 0; _repeat28 < (int)(saved.size()); _repeat28++) {
				mapvar = saved.get((int)n);
				saved_data.add(mapvar);
				n++;
			}
		}else{
			FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/WhatsSaver/.nomedia"), "");
		}
	}
	
	
	public void _getOrdered() {
		recentNewN = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(allMedia.size()); _repeat12++) {
			recentNewM = 0;
			notNew = 0;
			for(int _repeat24 = 0; _repeat24 < (int)(data.size()); _repeat24++) {
				if (allMedia.get((int)recentNewN).get("fileName").toString().equals(data.get((int)recentNewM).get("fileName").toString())) {
					notNew = 1;
					break;
				}
				else {
					recentNewM++;
				}
			}
			if (!(notNew == 1)) {
				if (allMedia.get((int)recentNewN).get("type").toString().equals("video")) {
					mapvar = allMedia.get((int)recentNewN);
					recent.add(mapvar);
					recentVideos.add(mapvar);
				}
			}
			recentNewN++;
		}
		recentNewN = 0;
		for(int _repeat41 = 0; _repeat41 < (int)(allMedia.size()); _repeat41++) {
			recentNewM = 0;
			notNew = 0;
			for(int _repeat45 = 0; _repeat45 < (int)(data.size()); _repeat45++) {
				if (allMedia.get((int)recentNewN).get("fileName").toString().equals(data.get((int)recentNewM).get("fileName").toString())) {
					notNew = 1;
					break;
				}
				else {
					recentNewM++;
				}
			}
			if (!(notNew == 1)) {
				if (allMedia.get((int)recentNewN).get("type").toString().equals("image")) {
					mapvar = allMedia.get((int)recentNewN);
					recent.add(mapvar);
					recentImages.add(mapvar);
				}
			}
			recentNewN++;
		}
		recentNewN = 0;
		for(int _repeat101 = 0; _repeat101 < (int)(allMedia.size()); _repeat101++) {
			if (allMedia.get((int)recentNewN).get("type").toString().equals("video")) {
				recentNewM = 0;
				notNew = 0;
				for(int _repeat145 = 0; _repeat145 < (int)(recent.size()); _repeat145++) {
					if (recent.get((int)recentNewM).get("fileName").toString().equals(allMedia.get((int)recentNewN).get("fileName").toString())) {
						notNew = 1;
						break;
					}
					else {
						recentNewM++;
					}
				}
				if (!(notNew == 1)) {
					mapvar = allMedia.get((int)recentNewN);
					recent.add(mapvar);
					recentVideos.add(mapvar);
				}
			}
			recentNewN++;
		}
		recentNewN = 0;
		for(int _repeat131 = 0; _repeat131 < (int)(allMedia.size()); _repeat131++) {
			recentNewM = 0;
			notNew = 0;
			for(int _repeat168 = 0; _repeat168 < (int)(recent.size()); _repeat168++) {
				if (recent.get((int)recentNewM).get("fileName").toString().equals(allMedia.get((int)recentNewN).get("fileName").toString())) {
					notNew = 1;
					break;
				}
				else {
					recentNewM++;
				}
			}
			if (!(notNew == 1)) {
				mapvar = allMedia.get((int)recentNewN);
				recent.add(mapvar);
				recentImages.add(mapvar);
			}
			recentNewN++;
		}
	}
	
	
	public void _saveData() {
		dataN = 0;
		for(int _repeat10 = 0; _repeat10 < (int)(data.size()); _repeat10++) {
			mapvar = data.get((int)dataN);
			newData.add(mapvar);
			dataN++;
		}
		recentNewN = 0;
		for(int _repeat18 = 0; _repeat18 < (int)(recent.size()); _repeat18++) {
			recentNewM = 0;
			notNew = 0;
			for(int _repeat22 = 0; _repeat22 < (int)(newData.size()); _repeat22++) {
				if (recent.get((int)recentNewN).get("fileName").toString().equals(newData.get((int)recentNewM).get("fileName").toString())) {
					notNew = 1;
					break;
				}
				else {
					recentNewM++;
				}
			}
			if (!(notNew == 1)) {
				{
					HashMap<String, Object> _item = new HashMap<>();
					_item.put("fileName", recent.get((int)recentNewN).get("fileName").toString());
					newData.add(_item);
				}
				
			}
			recentNewN++;
		}
		file.edit().putString("data", new Gson().toJson(newData)).commit();
	}
	
	
	public void _option_menu() {
	}
	@Override
	public boolean onCreateOptionsMenu (Menu menu){
		if(EnableRemove == 0){
			if(pagePosition == 0){
				
				menu.add("Refresh").setIcon(R.drawable.ic_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				
			}else{
				
				menu.add("Remove").setIcon(R.drawable.ic_delete_white).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				
			}
			menu.add("About");
		}else{
			menu.add("Cancel").setIcon(R.drawable.ic_cancel).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		
		return true;
	}
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getTitle().toString()){
			case "Refresh":
			_refresh();
			break;
			case "Remove":
			_remove();
			break;
			case "About":
			_about();
			break;
			case "Cancel":
			_cancel();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void _refresh() {
		
	}
	
	
	public void _about() {
		i.setClass(getApplicationContext(), AboutActivity.class);
		startActivity(i);
	}
	
	
	public void _remove() {
		EnableRemove = 1;
		invalidateOptionsMenu();
		viewPager1.setEnabled(false);
		tabLayout.setEnabled(false);
		bottom_bar.setVisibility(View.VISIBLE);
		n = 0;
		for(int _repeat32 = 0; _repeat32 < (int)(saved.size()); _repeat32++) {
			saved.get((int)n).put("enable_remove", "true");
			savedAdapter.notifyItemChanged((int)n);
			n++;
		}
		_changeHeader();
	}
	
	
	public void _cancel() {
		bottom_bar.setVisibility(View.GONE);
		EnableRemove = 0;
		checkbox1.setChecked(false);
		invalidateOptionsMenu();
		viewPager1.setEnabled(true);
		tabLayout.setEnabled(true);
		n = 0;
		for(int _repeat16 = 0; _repeat16 < (int)(saved.size()); _repeat16++) {
			saved.get((int)n).put("enable_remove", "false");
			savedAdapter.notifyItemChanged((int)n);
			n++;
		}
		n = 0;
		for(int _repeat24 = 0; _repeat24 < (int)(saved.size()); _repeat24++) {
			saved.get((int)n).put("remove", "false");
			//savedAdapter.notifyItemChanged((int)n);
			n++;
		}
		_changeHeader();
	}
	
	
	public void _download(final double _position) {
		recent.get((int)_position).put("download", "true");
		recentAdapter.notifyItemChanged((int)_position);
		inPath = recent.get((int)_position).get("filePath").toString();
		outPath = FileUtil.getExternalStorageDir().concat("/WhatsSaver/".concat(recent.get((int)_position).get("fileName").toString()));
		FileUtil.writeFile(outPath, "");
		FileUtil.copyFile(inPath, outPath);
		android.media.MediaScannerConnection.scanFile(MainActivity.this, 
		new String[]{Environment.getExternalStorageDirectory().toString() },null,new android.media.MediaScannerConnection.OnScanCompletedListener() {
			
			public void onScanCompleted(String path, Uri uri) {
			}
		});
		allFile.clear();
		FileUtil.listDir(FileUtil.getExternalStorageDir().concat("/WhatsSaver"), allFile);
		saved.clear();
		savedVideos.clear();
		savedImages.clear();
		_getSavedVideo();
		_getSavedImage();
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						recent.get((int)_position).put("download", "false");
						recentAdapter.notifyItemChanged((int)_position);
						_Savedadapter();
						gridview2.setAdapter(savedAdapter);
						savedAdapter.notifyDataSetChanged();
					}
				});
			}
		};
		_timer.schedule(timer, (int)(500));
	}
	
	
	public void _Savedadapter() {
		savedAdapter = new SavedAdapter(getApplicationContext(),saved,savedVideos,savedImages,saved_data,initGlide());
		savedAdapter.setOnItemClickListener(new SavedAdapter.OnItemClickListener() {
									@Override
									public void onItemClick(int position) {
											
				if (!(EnableRemove == 1)) {
					clickPosition = position;
					if (saved.get((int)clickPosition).get("type").toString().equals("video")) {
						i.setClass(getApplicationContext(), VideoActivity.class);
						i.putExtra("path", saved.get((int)clickPosition).get("filePath").toString());
						i.putExtra("title", saved.get((int)clickPosition).get("fileName").toString());
						i.putExtra("data", new Gson().toJson(savedVideos));
						i.putExtra("item", saved.get((int)clickPosition).get("item").toString());
						startActivity(i);
					}
					else {
						i.setClass(getApplicationContext(), ImageviewActivity.class);
						newN = 0;
						for(int _repeat32 = 0; _repeat32 < (int)(savedImages.size()); _repeat32++) {
							if (savedImages.get((int)newN).get("fileName").toString().equals(saved.get((int)clickPosition).get("fileName").toString())) {
								break;
							}
							else {
								newN++;
							}
						}
						i.putExtra("position", String.valueOf((long)(newN)));
						i.putExtra("path", saved.get((int)clickPosition).get("filePath").toString());
						i.putExtra("data", new Gson().toJson(savedImages));
						startActivity(i);
					}
				}
			}
			 @Override
			public void onDownloadClick(int position) {
			}
			 @Override
			public void onItemLongClick(int position) {
				clickPosition = position;
				if (!(EnableRemove == 1)) {
					dialog.setTitle("Delete status");
					dialog.setMessage("Do you really want to remove this saved status");
					dialog.setPositiveButton("remove", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							FileUtil.deleteFile(saved.get((int)clickPosition).get("filePath").toString());
							if (saved.get((int)clickPosition).get("type").toString().equals("video")) {
								n = 0;
								for(int _repeat72 = 0; _repeat72 < (int)(savedVideos.size()); _repeat72++) {
									if (saved.get((int)clickPosition).get("fileName").toString().equals(savedVideos.get((int)n).get("fileName").toString())) {
										savedVideos.remove((int)(n));
										break;
									}
									n++;
								}
							}
							else {
								n = 0;
								for(int _repeat87 = 0; _repeat87 < (int)(savedImages.size()); _repeat87++) {
									if (saved.get((int)clickPosition).get("fileName").toString().equals(savedImages.get((int)n).get("fileName").toString())) {
										savedImages.remove((int)(n));
										break;
									}
									n++;
								}
							}
							saved.remove((int)(clickPosition));
							savedAdapter.notifyDataSetChanged();
							recentAdapter.notifyDataSetChanged();
						}
					});
					dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
			}
			 @Override
			public void onChecked(int position,boolean checked) {
				clickPosition = position;
				itemChecked = checked;
				if (itemChecked) {
					saved.get((int)clickPosition).put("remove", "true");
				}
				else {
					saved.get((int)clickPosition).put("remove", "false");
				}
				_changeHeader();
			}});
	}
	
	
	public void _Recentadapter() {
		recentAdapter = new RecentAdapter(getApplicationContext(),recent,data,saved,recentVideos,recentImages,initGlide());
		recentAdapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
									@Override
									public void onItemClick(int position, int newItem) {
											
				if (!(EnableRemove == 1)) {
					clickPosition = position;
					newItem = newItem;
					if (recent.get((int)clickPosition).get("fileType").toString().equals("media")) {
						if (recent.get((int)clickPosition).get("type").toString().equals("video")) {
							i.setClass(getApplicationContext(), VideoActivity.class);
							i.putExtra("path", recent.get((int)clickPosition).get("filePath").toString());
							i.putExtra("title", recent.get((int)clickPosition).get("fileName").toString());
							i.putExtra("data", new Gson().toJson(recentVideos));
							i.putExtra("item", recent.get((int)clickPosition).get("item").toString());
							i.putExtra("new", String.valueOf((long)(newItem)));
							startActivity(i);
						}
						else {
							i.setClass(getApplicationContext(), ImageviewActivity.class);
							newN = 0;
							for(int _repeat32 = 0; _repeat32 < (int)(recentImages.size()); _repeat32++) {
								if (recentImages.get((int)newN).get("fileName").toString().equals(recent.get((int)clickPosition).get("fileName").toString())) {
									break;
								}
								else {
									newN++;
								}
							}
							i.putExtra("position", String.valueOf((long)(newN)));
							i.putExtra("path", recent.get((int)clickPosition).get("filePath").toString());
							i.putExtra("data", new Gson().toJson(recentImages));
							startActivity(i);
						}
					}
					else {
						
					}
				}
			}
			 @Override
			public void onDownloadClick(int position) {
				if (!(EnableRemove == 1)) {
					clickPosition = position;
					_download(clickPosition);
				}
			}
			 @Override
			public void onItemLongClick(int position) {
			}});
	}
	
	
	public void _select_all(final boolean _select) {
		if (_select) {
			n = 0;
			for(int _repeat20 = 0; _repeat20 < (int)(saved.size()); _repeat20++) {
				saved.get((int)n).put("remove", "true");
				savedAdapter.notifyItemChanged((int)n);
				n++;
			}
		}
		else {
			n = 0;
			for(int _repeat27 = 0; _repeat27 < (int)(saved.size()); _repeat27++) {
				saved.get((int)n).put("remove", "false");
				savedAdapter.notifyItemChanged((int)n);
				n++;
			}
		}
		_changeHeader();
	}
	
	
	public void _ASyncTaskDelete() {
	}
	private class AsyncTaskDelete extends AsyncTask<Void, Void, Void> {
		
		    @Override
		    protected Void doInBackground(Void... arg0) {
			_delete_selected();
			return null;
			    }
		
		    @Override
		    protected void onPostExecute(Void result) {
			        
			        
			_cancel();
			savedAdapter.notifyDataSetChanged();
			recentAdapter.notifyDataSetChanged();
			return ;
			    }
		
		    @Override
		    protected void onPreExecute() {
			       
			        return ;
			    }
	}
	{
	}
	
	
	public void _delete_selected() {
		n = 0;
		want_delete = 0;
		for(int _repeat23 = 0; _repeat23 < (int)(saved.size()); _repeat23++) {
			if (saved.get((int)n).get("remove").toString().equals("true")) {
				want_delete = 1;
			}
			n++;
		}
		if (want_delete == 1) {
			itemPosition = 0;
			for(int _repeat11 = 0; _repeat11 < (int)(saved.size()); _repeat11++) {
				if (saved.get((int)itemPosition).get("remove").toString().equals("true")) {
					FileUtil.deleteFile(saved.get((int)itemPosition).get("filePath").toString());
					if (saved.get((int)itemPosition).get("type").toString().equals("video")) {
						n = 0;
						for(int _repeat48 = 0; _repeat48 < (int)(savedVideos.size()); _repeat48++) {
							if (saved.get((int)itemPosition).get("fileName").toString().equals(savedVideos.get((int)n).get("fileName").toString())) {
								savedVideos.remove((int)(n));
							}
							n++;
						}
					}
					else {
						n = 0;
						for(int _repeat60 = 0; _repeat60 < (int)(savedImages.size()); _repeat60++) {
							if (saved.get((int)itemPosition).get("fileName").toString().equals(savedImages.get((int)n).get("fileName").toString())) {
								savedImages.remove((int)(n));
							}
							n++;
						}
					}
					saved.remove((int)(itemPosition));
				}
				itemPosition++;
			}
			_delete_selected();
		}
	}
	
	
	public void _changeHeader() {
		if (EnableRemove == 1) {
			n = 0;
			selected_items = 0;
			for(int _repeat15 = 0; _repeat15 < (int)(saved.size()); _repeat15++) {
				if (saved.get((int)n).get("remove").toString().equals("true")) {
					selected_items++;
				}
				n++;
			}
			setTitle("Selected ".concat(String.valueOf((long)(selected_items)).concat("/".concat(String.valueOf((long)(saved.size()))))));
		}
		else {
			setTitle("Status Saver");
		}
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
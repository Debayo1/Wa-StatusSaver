package com.whats.saver;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class ImageviewActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String path = "";
	private double position = 0;
	
	private ArrayList<HashMap<String, Object>> images = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout linear1;
	
	private TimerTask t;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.imageview);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		linear1 = findViewById(R.id.linear1);
	}
	
	private void initializeLogic() {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			
			Window w = this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(Color.parseColor("#000000"));
			
		}
		
		path = getIntent().getStringExtra("path");
		images = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		position = Double.parseDouble(getIntent().getStringExtra("position"));
		View inflate = getLayoutInflater().inflate(R.layout.images_viewpager, null);
		viewPager1 = (androidx.viewpager.widget.ViewPager)inflate.findViewById(R.id.viewpager1);
		
		ImageAdapter adapter = new ImageAdapter(this);
		
		viewPager1.setAdapter(adapter);
		
		viewPager1.setCurrentItem((int) position);
		final TextView text_count = (TextView)inflate.findViewById(R.id.count);
				text_count.setText(viewPager1.getCurrentItem()+1+"/"+images.size());
		linear1.addView(inflate);
		viewPager1.addOnPageChangeListener(new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
			
			public void onPageScrollStateChanged(int state) {}
			
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
			
			public void onPageSelected(int position) {
				text_count.setText(viewPager1.getCurrentItem()+1+"/"+images.size());
			}});
		
	}
	
	public void _viewPager() {
	}
	public class ImageAdapter extends androidx.viewpager.widget.PagerAdapter {
		
		private Context mContext;
		ImageAdapter(Context context) {
			mContext = context;
		}
		@Override
		public int getCount() {
			return images.size();
		}
		 @Override
		
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		 @Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			path = images.get((int)position).get("filePath").toString();
			
			View inflate = getLayoutInflater().inflate(R.layout.zoomimageview, null);
			            
			ZoomImageView zoomImageView = (ZoomImageView) inflate.findViewById(R.id.zoomImageView);
							Glide.with(getApplicationContext()).load(path).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL).into(zoomImageView);
						
						((androidx.viewpager.widget.ViewPager) container).addView(inflate, 0);
			
			return inflate;
			
		}
		 @Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	{
	}
	
	
	public void _library() {
	}
	androidx.viewpager.widget.ViewPager viewPager1;
	{
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
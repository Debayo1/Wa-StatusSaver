package com.whats.saver;

import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class SplashActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double openSettings = 0;
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private TextView textview1;
	
	private TimerTask t;
	private AlertDialog.Builder allowPer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.splash);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		allowPer = new AlertDialog.Builder(this);
	}
	
	private void initializeLogic() {
		openSettings = 0;
		if(androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED
				|| androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
			t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							allowPer.setTitle("Permission required");
							allowPer.setMessage("Allow storage permission to view and save your favourite WhatsApp status with WhatsSaver.");
							allowPer.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									_requestPermission();
								}
							});
							allowPer.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									finishAffinity();
								}
							});
							allowPer.setCancelable(false);
							allowPer.create().show();
						}
					});
				}
			};
			_timer.schedule(t, (int)(500));
		}else{
			t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							
							finish();
						}
					});
				}
			};
			_timer.schedule(t, (int)(2000));
		}
	}
	
	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (openSettings == 1) {
			if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
				t = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								allowPer.setTitle("Permission denied");
								allowPer.setMessage("Without storage permission you cannot view and save your favourite WhatsApp status.\n\nOpen settings to allow storage permission");
								allowPer.setPositiveButton("Open", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
										Uri uri = Uri.fromParts("package", getPackageName(), null);
										intent.setData(uri);
										startActivityForResult(intent, 1000);
										openSettings = 1;
									}
								});
								allowPer.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										finishAffinity();
									}
								});
								allowPer.create().show();
							}
						});
					}
				};
				_timer.schedule(t, (int)(500));
			}else{
				t = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								finish();
							}
						});
					}
				};
				_timer.schedule(t, (int)(1500));
			}
		}
	}
	public void _library() {
	}
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
				if (requestCode == 1000) {
			if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
				if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)== false || shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== false){
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									allowPer.setTitle("Permission denied permanently");
									allowPer.setMessage("Without storage permission you cannot view and save your favourite WhatsApp status.\n\nOpen settings to allow storage permission");
									allowPer.setPositiveButton("Open", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
											Uri uri = Uri.fromParts("package", getPackageName(), null);
											intent.setData(uri);
											startActivityForResult(intent, 1000);
											openSettings = 1;
										}
									});
									allowPer.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											finishAffinity();
										}
									});
									allowPer.create().show();
								}
							});
						}
					};
					_timer.schedule(t, (int)(300));
				}else{
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									allowPer.setTitle("Permission denied");
									allowPer.setMessage("Without storage permission you cannot view and save your favourite WhatsApp status.");
									allowPer.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											_requestPermission();
										}
									});
									allowPer.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											finishAffinity();
										}
									});
									allowPer.create().show();
								}
							});
						}
					};
					_timer.schedule(t, (int)(300));
				}
			}else{
				t = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								finish();
							}
						});
					}
				};
				_timer.schedule(t, (int)(1500));
			}
			}
		}
	{
	}
	
	
	public void _requestPermission() {
		androidx.core.app.ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
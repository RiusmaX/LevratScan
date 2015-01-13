package in.blogspot.khurram2java;

import com.levrat.bdd.EditProductActivity;
import com.levrat.formation.db.FormationDatabaseActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import in.blogspot.khurram2java.ConnectionDetect;



public class MainActivity extends Activity {


	private static final String TAG_EAN13 = "ean13";
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ConnectionDetect cd = new ConnectionDetect(getApplicationContext());
		if (cd.isConnectingToInternet())
		{
		setContentView(R.layout.activity_main); 
		if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); } 

		
		Button scanBtn = (Button) findViewById(R.id.btnScan);
		Button access = (Button) findViewById(R.id.access);
		Button btnRef = (Button) findViewById(R.id.btnRef);
		Button btnFav = (Button) findViewById(R.id.favoris);
		final EditText codeBarre = (EditText) findViewById(R.id.codebarre);
		//Button button1 = (Button) findViewById(R.id.button1);

		//in some trigger function e.g. button press within your code you should add:
		scanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					
					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
					startActivityForResult(intent, 0);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

				}

			}
		});
		
		access.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.quincaillerie-levrat.com"));
					
					startActivity(intent);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

				}

			}
		});
		
		 btnRef.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Intent intent = new Intent(getApplicationContext(), EditProductActivity.class);
					intent.putExtra(TAG_EAN13, codeBarre.getText().toString());
					startActivity(intent);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();
				}
			}
		}); 
		
		 btnFav.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					try {
						Intent intent3 = new Intent(getApplicationContext(), FormationDatabaseActivity.class);
						startActivity(intent3);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

					}

				}
			});
		}
		
		else {
			 showAlertDialog(MainActivity.this, "Pas de connection Internet",
                     "Veuillez vous connecter à Internet.", false);
			 
		}
	}
	//In the same activity you�ll need the following to retrieve the results:
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {

			if (resultCode == RESULT_OK) {
								 Intent i = new Intent(getApplicationContext(), EditProductActivity.class);
				 i.putExtra(TAG_EAN13, intent.getStringExtra("SCAN_RESULT").toString());
				
				 startActivity(i);
				
			} else if (resultCode == RESULT_CANCELED) {
				
				
			}
		}
	}
	
	 @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	 
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(message);
	         
	        // Setting alert dialog icon
	        
	 
	        // Setting OK Button
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	 System.exit(0);
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	       
	        
	    }
	
	

}

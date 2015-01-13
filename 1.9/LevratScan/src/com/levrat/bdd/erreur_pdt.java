package com.levrat.bdd;

import in.blogspot.khurram2java.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class erreur_pdt extends Activity {
	private static final String TAG_EAN13 = "ean13";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_pdt);
        
    	
        Button scanBtn = (Button) findViewById(R.id.btnScan);
        Button btnBackMenu = (Button) findViewById(R.id.btnBackMenu);
        Button btnRef = (Button) findViewById(R.id.btnRef);
		final EditText codeBarre = (EditText) findViewById(R.id.codebarre);
      

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
        
        btnBackMenu.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
                // BackMenu
            	finish();
            }
        });
        
        btnRef.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Intent intent = new Intent(getApplicationContext(), EditProductActivity.class);
					intent.putExtra(TAG_EAN13, codeBarre.getText().toString());
					finish();
					startActivity(intent);
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

				}

			}
		}); 
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {

			if (resultCode == RESULT_OK) {
								 Intent i = new Intent(getApplicationContext(), EditProductActivity.class);
				 i.putExtra(TAG_EAN13, intent.getStringExtra("SCAN_RESULT").toString());
				 finish();
				 startActivity(i);
				
			} else if (resultCode == RESULT_CANCELED) {
				
				
			}
		}
	}
	
	
	
	
	
	
}

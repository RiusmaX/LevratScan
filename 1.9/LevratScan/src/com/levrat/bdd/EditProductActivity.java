package com.levrat.bdd;

import in.blogspot.khurram2java.R;
import com.levrat.formation.db.business.FavorisRepository;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.levrat.bdd.erreur_pdt;
import comlevrat.formation.db.adapter.Favoris;
import comlevrat.formation.db.adapter.FavorisAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
 
public class EditProductActivity extends Activity {
 
    TextView txtName;
    TextView txtPrice;
    TextView txtDesc;
    TextView txtStock;

    EditText txtCreatedAt;
    Button btnBackMenu;
    Button btnCommand;
    Button btnAddFav;
    String ean13;
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
 
    // single product url
    private static final String url_product_detials = "http://www.quincaillerie-levrat.com/LevratScan/get_product_details.php";
    
    private FavorisRepository FavorityRepository;
    private FavorisAdapter adapter;
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_EAN13 = "ean13";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description_short";
    private static final String TAG_STOCK = "available_now";
    private static final String TAG_URL = "url_produit";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        
        
        FavorityRepository = new FavorisRepository(this);
        FavorityRepository.Open();
		adapter = new FavorisAdapter(this, FavorityRepository.GetAll());
		FavorityRepository.Close();
        
        
        btnBackMenu = (Button) findViewById(R.id.btnBackMenu);
        btnCommand =  (Button) findViewById(R.id.btnCommand);
        btnAddFav =  (Button) findViewById(R.id.btnAddFav);
        // getting product details from intent
        Intent i = getIntent();
 
        // getting product id (ean13) from intent
        ean13 = i.getStringExtra(TAG_EAN13);
 
        // Getting complete product details in background thread
        new GetProductDetails().execute();
 
       
        
     // BackMenu button click event
        btnBackMenu.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // BackMenu
            	finish();
            }
        });
        
     // btnCommand button click event
        btnCommand.setOnClickListener(new View.OnClickListener() {
 
        	public void onClick(View v) {
        		int success;
				try {
					 // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("ean13", ean13));

                    // getting product details by making HTTP request
                    // Note that product details url will use GET request
                    JSONObject json = jsonParser.makeHttpRequest(
                            url_product_detials, "GET", params);

                    // check your log for json response
                    Log.d("Single Product Details", json.toString());

                    // json success tag
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        // successfully received product details
                        JSONArray productObj = json
                                .getJSONArray(TAG_PRODUCT); // JSON Array

                        // get first product object from JSON Array
                        JSONObject product = productObj.getJSONObject(0);

                        // product with this ean13 found
                        // Edit Text
					
					
					
					
					Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(product.getString(TAG_URL)));
					finish();
					startActivity(intent);}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

				}

			}
        });
 
        btnAddFav.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					AjouterItem(txtName.getText().toString(),txtPrice.getText().toString(),txtDesc.getText().toString(),ean13);
				
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

				}

			}
		}); 
        
        
        
        
    }
 
    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Chargement du produit...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("ean13", ean13));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_detials, "GET", params);
 
                        // check your log for json response
                        Log.d("Single Product Details", json.toString());
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_PRODUCT); // JSON Array
 
                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);
 
                            // product with this ean13 found
                            // Edit Text
                            txtName = (TextView) findViewById(R.id.Name);
                            txtPrice = (TextView) findViewById(R.id.Price);
                            txtDesc = (TextView) findViewById(R.id.Desc);
                            txtStock = (TextView) findViewById(R.id.Stock);
             
                            // display product data in EditText
                            txtName.setText(product.getString(TAG_NAME));
                            txtPrice.setText(Html.fromHtml(product.getString(TAG_PRICE)+"�<sup>HT</sup>"));
                            txtDesc.setText(Html.fromHtml(product.getString(TAG_DESCRIPTION)));
                            txtStock.setText(product.getString(TAG_STOCK));
                            
                        }else{
                        	
                        	Intent intent_err = new Intent(getApplicationContext(), erreur_pdt.class);        					
                        	finish();
        					startActivity(intent_err);        					
                            // product with ean13 not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
        
        
        
    }


    public  void AjouterItem(String name,String price,String description,String ean13) {

		// Cr�ation de la boite de dialogue
		
				// Insertion du produit
    	
    	try{
    		
				FavorityRepository.Open();
				FavorityRepository.Save(new Favoris(name,price,description,ean13));
				FavorityRepository.Close();
				
				UpdateAdapter();
				
				 Toast.makeText(this, "Le produit � �t� correctement ajout� aux favoris", Toast.LENGTH_LONG).show();  
    	} catch (Exception e){
    		
    	    Toast.makeText(this, "Erreur lors de l'ajout aux favoris", Toast.LENGTH_LONG).show();  
    	}
			 
				
				

	
	}

	public void UpdateAdapter() {
		FavorityRepository.Open();
		adapter.setFavoris(FavorityRepository.GetAll());
		FavorityRepository.Close();
		adapter.notifyDataSetChanged();
	}
	
	public void UpdateChecked(Favoris course) {
		FavorityRepository.Open();
		FavorityRepository.Update(course);
		FavorityRepository.Close();
	}
	
	public void DeleteItem(int id) {
		FavorityRepository.Open();
		FavorityRepository.Delete(id);
		FavorityRepository.Close();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.menu_course, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		
		switch (item.getItemId()) {
		case R.id.itemDelete:
			DeleteItem((int) info.id);
			UpdateAdapter();
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}
}
    
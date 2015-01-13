package com.levrat.bdd;

import com.levrat.formation.db.business.FavorisRepository;

import in.blogspot.khurram2java.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import comlevrat.formation.db.adapter.Favoris;




public class Product_db extends Activity {
	
	
	private static final String TAG_ID= "id";
	private static final String TAG_PRODUIT = "produit";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_EAN13 = "ean13";
	private FavorisRepository db;
	private Favoris UnProduit;
	
	TextView txtName;
	TextView txtPrice;
	TextView txtDesc;
	TextView txtEAN13;
	
	EditText txtCreatedAt;
	Button btnBackMenu;
	Button btnCommand;
	long id_long;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_db);
			Intent i = getIntent();
			id_long = (i.getLongExtra(TAG_ID,0))-1;
			//int id = ((Long) id_long).intValue();
			int id = 1;
			//db.Open();
			//UnProduit=db.GetById(id);
			//db.Close();
			UnProduit = new Favoris(i.getStringExtra(TAG_PRODUIT),i.getStringExtra(TAG_PRICE),i.getStringExtra(TAG_DESCRIPTION),i.getStringExtra(TAG_EAN13));
			//String id_string= String.valueOf(id);
			//	UnProduit.setProduit("pdr_1"); 
			//	UnProduit.setPrice(id_string); 
			//	UnProduit.setDescription("test"); 
			//	UnProduit.setEan13("0123456789"); 
			
			
			txtName = (TextView) findViewById(R.id.P_Name);
			txtName.setText(UnProduit.getProduit().toString());
			
			txtPrice = (TextView) findViewById(R.id.P_Price);
			txtPrice.setText(UnProduit.getPrice().toString());
			
			txtDesc = (TextView) findViewById(R.id.P_Desc);
			txtDesc.setText(UnProduit.getDescription().toString());
			
			txtEAN13 = (TextView) findViewById(R.id.P_Ean13);
			txtEAN13.setText(UnProduit.getEan13().toString());
			
	}


}

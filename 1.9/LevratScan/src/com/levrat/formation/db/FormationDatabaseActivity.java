package com.levrat.formation.db;

import java.util.List;

import in.blogspot.khurram2java.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.levrat.bdd.Product_db;
import com.levrat.formation.db.business.FavorisRepository;
import comlevrat.formation.db.adapter.Favoris;
import comlevrat.formation.db.adapter.FavorisAdapter;

public class FormationDatabaseActivity extends Activity {

	private static final String TAG_ID = "id";
	private static final String TAG_PRODUIT = "produit";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_EAN13 = "ean13";
	private ListView listeViewFavoris;
	private FavorisAdapter adapter;
	private FavorisRepository FavoryRepository;
	private List<Favoris> lstFav;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		// Listview
		listeViewFavoris = (ListView) findViewById(R.id.listViewCourse);
		FavoryRepository = new FavorisRepository(this);

		FavoryRepository.Open();
		adapter = new FavorisAdapter(this, FavoryRepository.GetAll());
		lstFav=FavoryRepository.GetAll();
		FavoryRepository.Close();

		listeViewFavoris.setAdapter(adapter);

		registerForContextMenu(listeViewFavoris);
		
		listeViewFavoris.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	                  int position, long id) {
	                   
	                  
	                  // Launching new Activity on selecting single List Item
	                  Intent i = new Intent(getApplicationContext(), Product_db.class);
	                  // sending data to new activity
	                  
	                  i.putExtra(TAG_ID, listeViewFavoris.getAdapter().getItemId(position));
	                  i.putExtra(TAG_PRODUIT,lstFav.get(position).getProduit().toString()) ;
	                  i.putExtra(TAG_PRICE, lstFav.get(position).getPrice().toString()) ;
	                  i.putExtra(TAG_DESCRIPTION, lstFav.get(position).getDescription().toString()) ;
	                  i.putExtra(TAG_EAN13, lstFav.get(position).getEan13().toString()) ;
	                  startActivity(i);
	                 	              }
	            });
	}

	

	private void UpdateAdapter() {
		FavoryRepository.Open();
		adapter.setFavoris(FavoryRepository.GetAll());
		FavoryRepository.Close();
		adapter.notifyDataSetChanged();
	}
	
	public void UpdateChecked(Favoris course) {
		FavoryRepository.Open();
		FavoryRepository.Update(course);
		FavoryRepository.Close();
	}
	
	public void DeleteItem(int id) {
		FavoryRepository.Open();
		FavoryRepository.Delete(id);
		FavoryRepository.Close();
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
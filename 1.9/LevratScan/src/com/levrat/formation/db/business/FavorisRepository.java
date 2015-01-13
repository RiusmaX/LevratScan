package com.levrat.formation.db.business;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import comlevrat.formation.db.adapter.Favoris;

public class FavorisRepository extends Repository<Favoris> {

	public FavorisRepository(Context context) {
		sqLiteOpenHelper = new FavorisOpenHelper(context, null);
	}

	/**
	 * Suppression d'un produit
	 * 
	 * @param id
	 */
	public void DeleteFavoris(int id) {
		maBDD.delete(FavorisOpenHelper.FAV_TABLE_NAME,
				FavorisOpenHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Récupération de la liste de tous les produits
	 */
	@Override
	public List<Favoris> GetAll() {
		// Récupération de la liste des courses
		Cursor cursor = maBDD.query(FavorisOpenHelper.FAV_TABLE_NAME,
				new String[] { FavorisOpenHelper.COLUMN_ID,
						FavorisOpenHelper.COLUMN_PRODUIT,						
						FavorisOpenHelper.COLUMN_PRICE,
						FavorisOpenHelper.COLUMN_DESCRIPTION,
						FavorisOpenHelper.COLUMN_EAN13}, null, null, null,
				null, null, null);

		return ConvertCursorToListObject(cursor);
	}

	/**
	 * Retourne un seul produit
	 */
	@Override
	public Favoris GetById(int id) {
		
		
		Cursor cursor = maBDD.query(FavorisOpenHelper.FAV_TABLE_NAME,
				new String[] { FavorisOpenHelper.COLUMN_ID,
						FavorisOpenHelper.COLUMN_PRODUIT,
						FavorisOpenHelper.COLUMN_PRICE,
						FavorisOpenHelper.COLUMN_DESCRIPTION,
						FavorisOpenHelper.COLUMN_EAN13},
				FavorisOpenHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null);
		return ConvertCursorToObject(cursor);
	}

	
	

	/**
	 * Enregistre en produit dans la base
	 */
	@Override
	public void Save(Favoris entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(FavorisOpenHelper.COLUMN_PRODUIT, entite.getProduit());
		contentValues.put(FavorisOpenHelper.COLUMN_PRICE,	entite.getPrice());
		contentValues.put(FavorisOpenHelper.COLUMN_DESCRIPTION,	entite.getDescription());
		contentValues.put(FavorisOpenHelper.COLUMN_EAN13, entite.getEan13());
		maBDD.insert(FavorisOpenHelper.FAV_TABLE_NAME, null, contentValues);
	}

	/**
	 * Met à jour un produit
	 */
	@Override
	public void Update(Favoris entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(FavorisOpenHelper.COLUMN_PRODUIT, entite.getProduit());
		contentValues.put(FavorisOpenHelper.COLUMN_PRICE, entite.getPrice());
		contentValues.put(FavorisOpenHelper.COLUMN_DESCRIPTION, entite.getDescription());
		contentValues.put(FavorisOpenHelper.COLUMN_EAN13, entite.getEan13());
		maBDD.update(FavorisOpenHelper.FAV_TABLE_NAME, contentValues,
				FavorisOpenHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(entite.getId()) });
	}

	/**
	 * Supprime un produit
	 */
	@Override
	public void Delete(int id) {
		maBDD.delete(FavorisOpenHelper.FAV_TABLE_NAME,
				FavorisOpenHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Converti un curseur en une liste de produits
	 */
	@Override
	public List<Favoris> ConvertCursorToListObject(Cursor c) {
		List<Favoris> liste = new ArrayList<Favoris>();

		// Si la liste est vide
		if (c.getCount() == 0)
			return liste;

		// position sur le premeir item
		c.moveToFirst();

		// Pour chaque item
		do {

			Favoris favoris = ConvertCursorToObject(c);

			liste.add(favoris);
		} while (c.moveToNext());

		// Fermeture du curseur
		c.close();

		return liste;
	}

	/**
	 * Méthode utilisée par ConvertCursorToObject et ConvertCursorToListObject
	 */
	@Override
	public Favoris ConvertCursorToObject(Cursor c) {

		Favoris favoris = new Favoris(
				c.getString(FavorisOpenHelper.NUM_COLUMN_PRODUIT),
				c.getString(FavorisOpenHelper.NUM_COLUMN_PRICE),
				c.getString(FavorisOpenHelper.NUM_COLUMN_DESCRIPTION),
				c.getString(FavorisOpenHelper.NUM_COLUMN_EAN13));
			favoris.setId(c.getInt(FavorisOpenHelper.NUM_COLUMN_ID));

		return favoris;
	}

	/**
	 * Converti un curseur en un produit
	 */
	@Override
	public Favoris ConvertCursorToOneObject(Cursor c) {
		c.moveToFirst();

		Favoris favoris = ConvertCursorToObject(c);

		c.close();
		return favoris;
	}
}

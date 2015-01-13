package com.levrat.formation.db.business;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FavorisOpenHelper extends SQLiteOpenHelper {

	// Version de la base de données
	private static final int DATABASE_VERSION = 5;

	// Nom de la base
	private static final String FAV_BASE_NAME = "favoris.db";

	// Nom de la table
	public static final String FAV_TABLE_NAME = "Favoris";

	// Description des colonnes
	public static final String COLUMN_ID = "ID";
	public static final int NUM_COLUMN_ID = 0;
	public static final String COLUMN_PRODUIT = "PRODUIT";
	public static final int NUM_COLUMN_PRODUIT = 1;
	public static final String COLUMN_PRICE= "PRICE";
	public static final int NUM_COLUMN_PRICE = 2;
	public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	public static final int NUM_COLUMN_DESCRIPTION = 3;
	public static final String COLUMN_EAN13 = "EAN13";
	public static final int NUM_COLUMN_EAN13 = 4;

	// Requête SQL pour la création da la base
	private static final String REQUETE_CREATION_BDD = "CREATE TABLE "
			+ FAV_TABLE_NAME + " (" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUIT
			+ " TEXT , " + COLUMN_PRICE + " TEXT , "
			+ COLUMN_DESCRIPTION + " TEXT ," + COLUMN_EAN13 + " TEXT );";

	/**
	 * Constructeur
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public FavorisOpenHelper(Context context, CursorFactory factory) {
		super(context, FAV_BASE_NAME, factory, DATABASE_VERSION);
	}

	/**
	 * Création de la base
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REQUETE_CREATION_BDD);
	}

	/**
	 * Mise à jour de la base
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Lorsque l'on change le numéro de version de la base on supprime la
		// table puis on la recrée
		if (newVersion < DATABASE_VERSION) {
			db.execSQL("DROP TABLE " + FAV_TABLE_NAME + ";");
			onCreate(db);
		}
	}

}

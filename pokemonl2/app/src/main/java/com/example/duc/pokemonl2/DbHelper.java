package com.example.duc.pokemonl2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DUC on 20/11/2016.
 */

public class DbHelper extends SQLiteAssetHelper {

    private final static String DB_NAME = "pokemon.db";
    private final static int DB_VERSION = 1;
    private static final String POKEMON_TABLE_NAME = "pokemon";
    private static final String POKEMON_COLUMN_TAG = "tag";
    private static final String POKEMON_COLUMN_NAME = "name";
    private static final String POKEMON_COLUMN_GEN = "gen";
    private static final String POKEMON_COLUMN_ID = "id";
    private static final String POKEMON_COLUMN_IMG = "img";
    private static final String POKEMON_COLUMN_COLOR = "color";
    private static final String[] POKEMON_COLUMNS =
            new String[]{
                   POKEMON_COLUMN_ID,
                    POKEMON_COLUMN_NAME,
                    POKEMON_COLUMN_TAG,
                    POKEMON_COLUMN_GEN,
                    POKEMON_COLUMN_IMG,
                    POKEMON_COLUMN_COLOR
            };

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static DbHelper instance;
    public static DbHelper getInstance(){
        return  instance;
    }

    public void insert(Pokemon pokemon){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POKEMON_COLUMN_TAG,pokemon.getTag());
        contentValues.put(POKEMON_COLUMN_NAME,pokemon.getName());
        contentValues.put(POKEMON_COLUMN_GEN,pokemon.getGen());
        contentValues.put(POKEMON_COLUMN_IMG,pokemon.getImg());
        contentValues.put(POKEMON_COLUMN_COLOR,pokemon.getColor());
        long id = db.insert(POKEMON_TABLE_NAME,"(?,?)",contentValues);
        pokemon.setId((int)id);
        db.close();
    }

    public ArrayList<Pokemon> getRandomPokeSameGen(int gen, int id){
            ArrayList<Pokemon> pokeList = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String where = "gen = "+ gen + " AND id != " + id;
            Cursor cursor = db.query(POKEMON_TABLE_NAME ,//table
                    POKEMON_COLUMNS,//column
                    where,//selection
                    null,//selectionArgs
                    null,//groupBy
                    null,//having
                    "RANDOM()",//orderBy
                    "3");//limit

            while (cursor.moveToNext()) {
                pokeList.add(createPokemon(cursor));
            }
            return pokeList;
        }


    public List<Pokemon> selectAllPokemon(){
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(POKEMON_TABLE_NAME, POKEMON_COLUMNS, null
                ,null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            pokemonList.add(createPokemon(cursor));
        }
        cursor.close();;
        db.close();
        return  pokemonList;
    }

    private Pokemon createPokemon(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(POKEMON_COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(POKEMON_COLUMN_NAME));
        String tag = cursor.getString(cursor.getColumnIndex(POKEMON_COLUMN_TAG));
        int gen = cursor.getInt(cursor.getColumnIndex(POKEMON_COLUMN_GEN));
        String img = cursor.getString(cursor.getColumnIndex(POKEMON_COLUMN_IMG));
        String color = cursor.getString(cursor.getColumnIndex(POKEMON_COLUMN_COLOR));
        Pokemon pokemon = new
                Pokemon(id, name, tag,  gen,  img, color);
        return pokemon;
    }

    public Pokemon selectRandomPokemon(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(POKEMON_TABLE_NAME, POKEMON_COLUMNS, null
                ,null,
                null,
                null,
                "RANDOM()",
                "1");
        if(cursor.moveToNext()){
            return  createPokemon(cursor);
        }
        return null;

    }

    public static void init(Context context){
        instance = new DbHelper(context);
    }


}

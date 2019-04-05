package org.smartregister.reporting.repository;

import android.content.ContentValues;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.reporting.model.IndicatorQuery;
import org.smartregister.repository.BaseRepository;
import org.smartregister.repository.Repository;

/**
 * Repository for the different indicators. The repository will store and allow retrieval of a query associated
 * with an ReportIndicator
 *
 * @author allan
 */
public class IndicatorQueryRepository extends BaseRepository {

    public static final String ID = "_id";
    public static String QUERY = "indicator_query";
    public static String INDICATOR_CODE = "indicator_code";
    public static String DB_VERSION = "db_version";
    public static String INDICATOR_QUERY_TABLE = "indicator_queries";

    public static String CREATE_TABLE_INDICATOR_QUERY = "CREATE TABLE " + INDICATOR_QUERY_TABLE + " (" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            QUERY + "TEXT NOT NULL, " + INDICATOR_CODE + "INTEGER NOT NULL, " + DB_VERSION + " INTEGER)";

    public IndicatorQueryRepository(Repository repository) {
        super(repository);
    }

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_INDICATOR_QUERY);
    }

    public void add(IndicatorQuery indicatorQuery) {
        if (indicatorQuery == null) {
            return;
        }
        SQLiteDatabase database = getWritableDatabase();
        indicatorQuery.setId(database.insert(INDICATOR_QUERY_TABLE, null, createContentValues(indicatorQuery)));
    }

    public String findQueryByIndicatorCode(String indicatorCode) {

        SQLiteDatabase database = getReadableDatabase();
        String indicatorQuery = "";
        String[] columns = {QUERY};
        String selection = QUERY + " = ?";
        String[] selectionArgs = {indicatorCode};

        Cursor cursor = database.query(INDICATOR_QUERY_TABLE, columns, selection, selectionArgs, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                indicatorQuery = cursor.getString(cursor.getColumnIndex(QUERY));
            }
        }

        return indicatorQuery;
    }

    public ContentValues createContentValues(IndicatorQuery indicatorQuery) {
        ContentValues values = new ContentValues();
        values.put(ID, indicatorQuery.getId());
        values.put(QUERY, indicatorQuery.getQuery());
        values.put(INDICATOR_CODE, indicatorQuery.getIndicatorCode());
        values.put(DB_VERSION, indicatorQuery.getDbVersion());
        return values;
    }

}

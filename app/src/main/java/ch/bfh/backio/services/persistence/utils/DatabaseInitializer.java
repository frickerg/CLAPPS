package ch.bfh.backio.services.persistence.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import ch.bfh.backio.services.persistence.database.AppDatabase;
import ch.bfh.backio.services.persistence.entity.Value;

import java.util.Date;
import java.util.List;

import static ch.bfh.backio.services.persistence.utils.Converters.dateToTimestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseInitializer.
 */
public class DatabaseInitializer {

	/** The Constant TAG. */
	private static final String TAG = DatabaseInitializer.class.getName();

	/**
	 * Populate async.
	 *
	 * @param db the db
	 */
	public static void populateAsync(@NonNull final AppDatabase db) {
		PopulateDbAsync task = new PopulateDbAsync(db);
		task.execute();
	}

	/**
	 * Populate sync.
	 *
	 * @param db the db
	 */
	public static void populateSync(@NonNull final AppDatabase db) {
		populateWithTestData(db);
	}

	/**
	 * Adds the value.
	 *
	 * @param db the db
	 * @param value the value
	 * @return the value
	 */
	private static Value addValue(final AppDatabase db, Value value) {
		db.valueDao().insertAll(value);
		return value;
	}

	/**
	 * Populate with test data.
	 *
	 * @param db the db
	 */
	private static void populateWithTestData(AppDatabase db) {
		Value value = new Value();
		value.setDatetime(dateToTimestamp(new Date()));
		value.setParameter(5);
		value.setValue(2);
		addValue(db, value);

		List<Value> valueList = db.valueDao().getAll();
		Log.d(DatabaseInitializer.TAG, "Rows Count: " + valueList.size());
	}

	/**
	 * The Class PopulateDbAsync.
	 */
	private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

		/** The m db. */
		private final AppDatabase mDb;

		/**
		 * Instantiates a new populate db async.
		 *
		 * @param db the db
		 */
		PopulateDbAsync(AppDatabase db) {
			mDb = db;
		}

		/**
		 * Do in background.
		 *
		 * @param params the params
		 * @return the void
		 */
		@Override
		protected Void doInBackground(final Void... params) {
			populateWithTestData(mDb);
			return null;
		}

	}

}

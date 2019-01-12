package ch.bfh.backio.services.persistence.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import ch.bfh.backio.services.persistence.dao.ValueDao;
import ch.bfh.backio.services.persistence.entity.Value;

@Database(entities = {Value.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase INSTANCE;

	public abstract ValueDao valueDao();

	public static AppDatabase getAppDatabase(Context context) {
		if (INSTANCE == null) {
			INSTANCE =
				Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "value-database")
					// allow queries on the main thread.
					// Don't do this on a real app! See PersistenceBasicSample for an example.
					.allowMainThreadQueries()
					.fallbackToDestructiveMigration()
					.build();
		}
		return INSTANCE;
	}

	public static void destroyInstance() {
		INSTANCE = null;
	}
}

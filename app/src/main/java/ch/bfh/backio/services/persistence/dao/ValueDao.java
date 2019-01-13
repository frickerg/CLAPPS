package ch.bfh.backio.services.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import ch.bfh.backio.services.persistence.entity.Value;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface ValueDao.
 */
@Dao
public interface ValueDao {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Query("SELECT * FROM value")
	List<Value> getAll();

	/**
	 * Count values.
	 *
	 * @return the int
	 */
	@Query("SELECT COUNT(*) from value")
	int countValues();

	/**
	 * Insert all.
	 *
	 * @param values the values
	 */
	@Insert
	void insertAll(Value... values);

	/**
	 * Delete.
	 *
	 * @param value the value
	 */
	@Delete
	void delete(Value value);
}

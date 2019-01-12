package ch.bfh.backio.services.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import ch.bfh.backio.services.persistence.entity.Value;

import java.util.List;

@Dao
public interface ValueDao {

	@Query("SELECT * FROM value")
	List<Value> getAll();

	@Query("SELECT COUNT(*) from value")
	int countValues();

	@Insert
	void insertAll(Value... values);

	@Delete
	void delete(Value value);
}

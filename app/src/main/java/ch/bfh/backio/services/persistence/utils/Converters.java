package ch.bfh.backio.services.persistence.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Converters.
 */
public class Converters {
	
	/**
	 * From timestamp.
	 *
	 * @param value the value
	 * @return the date
	 */
	@TypeConverter
	public static Date fromTimestamp(Long value) {
		return value == null ? null : new Date(value);
	}

	/**
	 * Date to timestamp.
	 *
	 * @param date the date
	 * @return the long
	 */
	@TypeConverter
	public static Long dateToTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}

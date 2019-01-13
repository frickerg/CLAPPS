package ch.bfh.backio.services.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

import static ch.bfh.backio.services.persistence.utils.Converters.dateToTimestamp;
import static ch.bfh.backio.services.persistence.utils.Converters.fromTimestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class Value.
 */
@Entity(tableName = "value")
public class Value {

	/** The uid. */
	@PrimaryKey(autoGenerate = true)
	private int uid;

	/** The datetime. */
	@ColumnInfo(name = "datetime")
	private Long datetime;

	/** The parameter. */
	@ColumnInfo(name = "parameter")
	private int parameter;

	/** The value. */
	@ColumnInfo(name = "value")
	private float value;

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 *
	 * @param uid the new uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

/*	public Date getDatetime() {
		return fromTimestamp(datetime);
	}

	public void setDatetime(Date datetime){
		this.datetime = dateToTimestamp(datetime);
	}*/

	/**
 * Gets the datetime.
 *
 * @return the datetime
 */
public Long getDatetime(){
		return datetime;
	}

	/**
	 * Sets the datetime.
	 *
	 * @param datetime the new datetime
	 */
	public void setDatetime(Long datetime) {
		this.datetime = datetime;
	}

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public int getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter.
	 *
	 * @param parameter the new parameter
	 */
	public void setParameter(int parameter) {
		this.parameter = parameter;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(float value) {
		this.value = value;
	}

}

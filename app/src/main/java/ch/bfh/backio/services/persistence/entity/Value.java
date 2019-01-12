package ch.bfh.backio.services.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

import static ch.bfh.backio.services.persistence.utils.Converters.dateToTimestamp;
import static ch.bfh.backio.services.persistence.utils.Converters.fromTimestamp;

@Entity(tableName = "value")
public class Value {

	@PrimaryKey(autoGenerate = true)
	private int uid;

	@ColumnInfo(name = "datetime")
	private Long datetime;

	@ColumnInfo(name = "parameter")
	private int parameter;

	@ColumnInfo(name = "value")
	private float value;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

/*	public Date getDatetime() {
		return fromTimestamp(datetime);
	}

	public void setDatetime(Date datetime){
		this.datetime = dateToTimestamp(datetime);
	}*/

	public Long getDatetime(){
		return datetime;
	}

	public void setDatetime(Long datetime) {
		this.datetime = datetime;
	}

	public int getParameter() {
		return parameter;
	}

	public void setParameter(int parameter) {
		this.parameter = parameter;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}

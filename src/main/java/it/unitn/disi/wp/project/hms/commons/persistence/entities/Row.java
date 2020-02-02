package it.unitn.disi.wp.project.hms.commons.persistence.entities;

import org.daypilot.date.DateTime;

import java.sql.Timestamp;
import java.util.TreeMap;

/**
 * Row bean for handle calendar
 *
 * @author <a href="https://java.daypilot.org/lite/">DayPilot Lite (Open-Source)</a>
 */
public class Row extends TreeMap<String, Object> {

	public Row() {
		super(String.CASE_INSENSITIVE_ORDER);
	}

	public String str(String field) {
		return (String) get(field);
	}

	public DateTime dateTime(String field) {
		return new DateTime((Timestamp)get(field));
	}

	public boolean isEmpty(String field) {
		if (isNull(field)) {
			return true;
		}
		if (get(field).toString().trim().equals("")) {
			return true;
		}
		return false;
	}

	public boolean isNull(String field) {
		return get(field) == null;
	}

}
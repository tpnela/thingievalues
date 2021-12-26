package com.tpnela.thingievalues.sampling;

import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class DataNode {

	private long timestamp;
	private ConcurrentHashMap<String, String> values;

	public DataNode() {
		this.timestamp = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return true, if key was newly added, false if key existed
	 */
	public synchronized boolean add(String key, String value) {
		boolean retval = !values.containsKey(key);
		values.put(key, value);
		return retval;
	}

	/**
	 * 
	 * @return Unordered array of all valuekeys
	 */
	public String[] getKeys() {
		Set<String> keys = values.keySet();
		return keys.toArray(new String[keys.size()]);
	}

	/**
	 * 
	 * @return Complete map of all available values
	 */
	public ConcurrentHashMap<String, String> getValuemap() {
		return values;
	}

	/**
	 * 
	 * @param key
	 * @return value for the passed key
	 * @throws Exception on use of a key that is not in the map
	 */
	public String getValue(String key) throws Exception {
		if (values.containsKey(key)) {
			return values.get(key);
		} else {
			throw new Exception("No value for key [" + key + "]");
		}
	}

	/**
	 * 
	 * @return timestamp of node creation in epoch milliseconds <b>and in GMT!</b>
	 */
	public long getTimestamp() {
		return timestamp;
	}

}

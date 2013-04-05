package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map providing directional methods like get(key) also in reverse
 * direction. <br>
 * ( get(key)= value  <->  reverseGet(value)= key )
 * <p></p>
 * This ability means that this map is a Bijiction of Keys and Values
 * which means there can only be one Key mapped to a unique Value and
 * vice versa.
 * <p></p>
 * The underlying datastructure consists of 2 {@linkplain HashMap}s one mapping
 * K->V and one mapping V->K.
 * @author David Haegele
 *
 * @param <K> Type of Keys
 * @param <V> Type of Values
 */
public class BidirectionalMap <K, V> implements Map<K, V>{
	
	Map<K, V> normalMap;
	
	Map<V, K> reverseMap;
	
	public BidirectionalMap() {
		normalMap = new HashMap<K, V>();
		reverseMap = new HashMap<V, K>();
	}

	@Override
	public void clear() {
		normalMap.clear();
		reverseMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return normalMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return reverseMap.containsKey(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return normalMap.entrySet();
	}
	
	/**
	 * like {@link #entrySet()} but reverse -> returns 
	 * a set of the values with mapped keys
	 * @return
	 */
	public Set<java.util.Map.Entry<V, K>> reverseEntrySet() {
		return reverseMap.entrySet();
	}

	@Override
	public V get(Object key) {
		return normalMap.get(key);
	}
	
	/** 
	 * like {@link #get(Object)} but reverse -> gets the key to the value
	 * @param value for which the key should be returned
	 * @return key the specified value is mapped to
	 */
	public K reverseGet(Object value) {
		return reverseMap.get(value);
	}

	@Override
	public boolean isEmpty() {
		return normalMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return normalMap.keySet();
	}
	
	/**
	 * like {@link #keySet()} but reverse -> returns 
	 * a set of the values
	 * @return set of values
	 */
	public Set<V> reverseKeySet() {
		return reverseMap.keySet();
	}

	@Override
	public V put(K key, V value) {
		normalMap.remove(reverseMap.put(value, key));
		V oldVal = normalMap.put(key, value);
		reverseMap.remove(oldVal);
		return oldVal;
	}
	
	/** Not supported */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		
	}

	@Override
	public V remove(Object key) {
		V val = normalMap.remove(key);
		reverseMap.remove(val);
		return val;
	}
	
	/**
	 * like {@link #remove(Object)} but reverse -> removes
	 * the key/value pair for the specified value
	 * @param value
	 * @return
	 */
	public K reverseRemove(Object value) {
		K key = reverseMap.remove(value);
		normalMap.remove(key); 
		return key;
	}
	
	@Override
	public int size() {
		return normalMap.size();
	}

	@Override
	public Collection<V> values() {
		return normalMap.values();
	}
	
	/**
	 * like {@link #values()} but reverse -> returns a
	 * Collection of the Keys
	 * @return a Collection of the Keys contained in this Map
	 */
	public Collection<K> reverseValues() {
		return reverseMap.values();
	}
	
	
	
	
}

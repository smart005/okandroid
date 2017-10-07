package com.cloud.core.beans;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-22 上午9:56:53
 * @Description: 键值对
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class MapEntry<K, V> {

	private K key;
	private V value;

	public MapEntry() {

	}

	public MapEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return 获取key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @param 设置key
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * @return 获取value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param 设置value
	 */
	public void setValue(V value) {
		this.value = value;
	}
}

package collection;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pojo.Apple;

interface MyMap<K,V>{
	void put(K k, V v);
	V get(K k);
}
/**
 * ��ʱ������null�����
 * @author WWY
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K,V> implements MyMap<K,V>{
	private final static int DEFAULTSIZE=16;
	private  static double DEFAULTFACTOR=0.75d;
	private Node<K,V>[] table;
	private int size;
	
	private MyHashMap(int tableSize){
		this.table=new Node[tableSize];
		this.size=tableSize;
	}
	public MyHashMap(){
		this(DEFAULTSIZE);
	}
	
	@Override
	public void put(K k, V v) {
		int hash=k.hashCode();
		int index = hash&this.size -1;
		if(table[index]==null) {
			table[index]=new Node<K, V>(hash, k, v, null);
			return ;
		}
		if(table[index]!=null){
			table[index].putNode(hash, k, v);
		}else table[index]=new Node<K, V>(hash, k, v, table[index]);
		
	}
	

	@Override
	public V get(K k) {
		int hash = k.hashCode();
		int index=hash&this.size -1;
		return table[index].getNode(hash, k);
	}
	

	static class Node<K,V>{
		int hash;
		K k;
		V v;
		Node<K, V> next;
		public Node(int hash, K k, V v, Node<K, V> next) {
			super();
			this.hash = hash;
			this.k = k;
			this.v = v;
			this.next = next;
		}
		
		public Node<K, V> getNext() {
			return next;
		}

		public V putNode(int hash,K k,V v) {
			if(this.hash==hash&&this.k.equals(k)) {
				V result=this.v;
				this.v=v;
				return result;
			}
			if(this.next==null) return null;
			else return this.next.putNode(hash, k,v );
		}
		
		public V getNode(int hash,K k) {
			if(hash==this.hash&&this.k==k) return this.v;
			if(next==null) return null;
			return next.getNode(hash,k);
		}
	}
	
	
	@Override
	public String toString() {
		return "MyHashMap [table=" + Arrays.toString(table) + ", size=" + size + "]";
	}

	public static void main(String[] args) {
		Apple apple = new Apple();apple.setPrice(5.0);
		Apple apple2=new Apple();
		String key="a1";String key2="a2";
//		System.out.println(key.hashCode()&15);
//		System.out.println(key2.hashCode()&15);
//		System.out.println(Objects.hashCode(key) ^ Objects.hashCode(apple));
//		System.out.println(11&11);//�����㣺01��0,11��1,00��0
		Map<String,Integer> map1=new HashMap<String, Integer>();
		map1.put(null, 120);
//		System.out.println(map1.get(null));
		
		MyHashMap<String, Integer> map=new MyHashMap<String, Integer>();
		map.put("ap", 123);map.put("a2", 2323);
		System.out.println(map.get("a2"));
		map.put("a2", 999);
		System.out.println(map.get("a2"));
	}
}


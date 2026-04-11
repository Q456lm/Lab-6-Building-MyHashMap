/**
 * Clarence Bunting
 * Course: Java II
 * Date: 04/11/2026
 *
 * This program is able to create, add to, find, and remove values from Hash Maps.
 */

import java.util.LinkedList;

/**
 * Respresents a HashMap which maps a key to an index.
 * Allows for adding and removing values from a Linked List based on that index.
 */
public class MyHashMap<K, V> {

    /**
     * Defines an entry class which allows you to map a key to a value.
     */
    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────
    LinkedList<Entry<K, V>>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 11;

    // ── Constructor ───────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    /**
     * Used to create the hashMap.
     * Creates an empty linkedList with a size of the previously defined DEFAULT_CAPACITY.
     * Sets size equal to zero.
     */
    public MyHashMap() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size  = 0;
    }

    // ── Hash helper ───────────────────────────────────────────────────────
    /**
     * Retrieves an index based on a key.
     *
     * @param key the key that the index with be computed based off.
     * @return The index found with the formula: The remainder when the absolute value of the hashCode of the key is divided by table's length.
     */
    private int hash(K key) {
        // Keys must not be null
        return Math.abs(key.hashCode()) % table.length;
    }

    // ── put ───────────────────────────────────────────────────────────────
    /**
     * Used to put values into an index in the hashMap based off the key.
     *
     * @param key The key that will be inserted into the hashMap and what the index will be based on.
     * @param value The Value that will be inserted into the hashMap that corresponds to the key.
     * @return The previous value of a key if you are changing a keys value, otherwise null.
     */
    public V put(K key, V value) {
        int index = hash(key);
        
        if (table[index] == null){
            table[index] = new LinkedList<>();
        }
        
        if (table[index].isEmpty()){
            table[index].add(new Entry<>(key,value));
            size++;
            return null;
        }
        else{
            for (Entry<K,V> point : table[index]){
                if (point.key.equals(key)){
                    V oldValue = point.value;
                    point.value = value;
                    return oldValue;
                }
            }

            table[index].add(new Entry<>(key,value));
            size++;
            return null;
        }
    }

    // ── get ───────────────────────────────────────────────────────────────
    /**
     * Used to find and get a value based off a key.
     *
     * @param key The key that is used to find the corresponding value.
     * @return The value that corresponds to the key, if there is no corresponding value than null.
     */
    public V get(K key) {
        int index = hash(key);

        if (table[index] == null){
            return null;
        }

        for (Entry<K,V> point : table[index]){
            if (point.key.equals(key)){
                return point.value;
            }
        }

        return null;
    }

    // ── containsKey ───────────────────────────────────────────────────────
    /**
     * Determines if a key exists in the hashMap.
     *
     * @param key The key that you want to check to see if it exists.
     * @return true if key exists; false if the key does not exist.
     */
    public boolean containsKey(K key) {
        int index = hash(key);

        if (table[index] == null){
            return false;
        }

        for (Entry<K,V> point : table[index]){
            if (point.key.equals(key)){
                return true;
            }
        }

        return false;
    }

    // ── remove ────────────────────────────────────────────────────────────
    /**
     * Removes a corresponding key/value pair from the hashMap based on the index of a key.
     *
     * @param key The key of the key/value pair you want to remove.
     * @return The value that corresponds to the delted key, null if key is not found.
     */
    public V remove(K key) {
        int index = hash(key);

        if (table[index].isEmpty()){
            return null; 
        }

        for (Entry<K,V> point : table[index]){
            if (point.key.equals(key)){
                V pointValue = point.value;
                table[index].remove(point);
                size--;
                return pointValue;
            }

            if (table[index].isEmpty()){
                table[index]=null;
            }
        }

        return null;
    }

    // ── size ──────────────────────────────────────────────────────────────
    /**
     * Gets the size of the HashMap that is incremented in decremented in previous methods.
     *
     * @return The size of the hashMap which is based on how many key/value pairs exist in the hashMap.
     */
    public int size() {
        return size;
    }

    // ── isEmpty ───────────────────────────────────────────────────────────
    /**
     * Determines whether or not the hashMap is empty based on whether or not size is equal to zero.
     *
     * @return true if the hashMap is empty; false if the hashMap has one or more key/value pair.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // ── Test driver ───────────────────────────────────────────────────────
    /**
     * Creates the hashMap and enters 5 starting values into it.
     * Demonstrates adding, removing, getting, checking if a key exists, and getting the size of the hashMap.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        // put -- basic insertions
        map.put("cat", 1);
        map.put("dog", 2);
        map.put("rat", 3);
        map.put("bat", 4);
        map.put("ant", 5);
        System.out.println("Size after 5 insertions: " + map.size());   // 5

        // get -- existing keys
        System.out.println("get(cat): " + map.get("cat"));              // 1
        System.out.println("get(bat): " + map.get("bat"));              // 4

        // get -- missing key
        System.out.println("get(owl): " + map.get("owl"));              // null

        // put -- duplicate key (update)
        map.put("cat", 99);
        System.out.println("get(cat) after update: " + map.get("cat")); // 99
        System.out.println("Size after update: " + map.size());         // 5

        // containsKey
        System.out.println("containsKey(dog): " + map.containsKey("dog")); // true
        System.out.println("containsKey(elk): " + map.containsKey("elk")); // false

        // remove -- existing key
        map.remove("dog");
        System.out.println("get(dog) after remove: " + map.get("dog")); // null
        System.out.println("Size after remove: " + map.size());         // 4

        // remove -- missing key
        map.remove("owl");
        System.out.println("Size after removing missing key: " + map.size()); // 4

        // null value -- a key can legitimately map to null
        map.put("fox", null);
        System.out.println("get(fox): " + map.get("fox"));              // null
        System.out.println("containsKey(fox): " + map.containsKey("fox")); // true
        System.out.println("Size with null value: " + map.size());      // 5

        // forced collision -- "AaAaAa" and "BBBaaa" have the same hashCode in Java
        MyHashMap<String, Integer> collisionMap = new MyHashMap<>();
        collisionMap.put("AaAaAa", 1);
        collisionMap.put("BBBaaa", 2);
        System.out.println("Collision get(AaAaAa): " + collisionMap.get("AaAaAa")); // 1
        System.out.println("Collision get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        collisionMap.remove("AaAaAa");
        System.out.println("After remove, get(BBBaaa): " + collisionMap.get("BBBaaa")); // 2
        System.out.println("After remove, size: " + collisionMap.size()); // 1
    }
}

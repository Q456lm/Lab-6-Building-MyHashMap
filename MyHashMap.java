import java.util.LinkedList;

public class MyHashMap<K, V> {

    // ── Inner entry class ─────────────────────────────────────────────────
    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
        }

        public V getValue(){
            return value;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────
    LinkedList<Entry<K, V>>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 11;

    // ── Constructor ───────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size  = 0;
    }

    // ── Hash helper ───────────────────────────────────────────────────────
    private int hash(K key) {
        // Keys must not be null
        return Math.abs(key.hashCode()) % table.length;
    }

    // ── put ───────────────────────────────────────────────────────────────
    public V put(K key, V value) {

        int index = hash(key);
        if (table[index] == null){
            table[index] = new LinkedList<>();
        }if (table[index].isEmpty()){
            table[index].add(new Entry<>(key,value));
            size++;
            return null;
        }
        else{
            V oldValue = table[index].getFirst().value;
            table[index].add(new Entry<>(key,value));
            return oldValue;
        }
    }

    // ── get ───────────────────────────────────────────────────────────────
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
    public V remove(K key) {

        int index = hash(key);
        if (table[index] == null){
            table[index] = new LinkedList<>();
        }if (table[index].isEmpty()){
            return null;
        }
        for (Entry<K,V> point : table[index]){
            if (point.key.equals(key)){
                table[index].remove(point);
            }
            if (table[index].isEmpty()){
                table[index]=null;
                size--;
            }
        }

        return null; // replace this
    }

    // ── size ──────────────────────────────────────────────────────────────
    public int size() {
        return size;
    }

    // ── isEmpty ───────────────────────────────────────────────────────────
    public boolean isEmpty() {
        return size == 0;
    }

    // ── Test driver ───────────────────────────────────────────────────────
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

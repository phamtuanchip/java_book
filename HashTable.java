import java.util.LinkedList;

public class HashTable {
    private static final int TABLE_SIZE = 10;
    private LinkedList<KeyValue>[] table;
    
    public HashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }
    
    private int hashFunction(int key) {
        return key % TABLE_SIZE;
    }
    
    public void insert(int key, String value) {
        int hash = hashFunction(key);
        LinkedList<KeyValue> list = table[hash];
        
        for (KeyValue kv : list) {
            if (kv.key == key) {
                kv.value = value; // Update existing key-value pair
                return;
            }
        }
        
        list.add(new KeyValue(key, value)); // Add new key-value pair
    }
    
    public String get(int key) {
        int hash = hashFunction(key);
        LinkedList<KeyValue> list = table[hash];
        
        for (KeyValue kv : list) {
            if (kv.key == key) {
                return kv.value; // Return value if key found
            }
        }
        
        return null; // Return null if key not found
    }
    
    private static class KeyValue {
        private int key;
        private String value;
        
        public KeyValue(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        hashTable.insert(1, "John");
        hashTable.insert(2, "Jane");
        hashTable.insert(11, "Mike");
        
        System.out.println(hashTable.get(2)); // Output: Jane
        System.out.println(hashTable.get(11)); // Output: Mike
        System.out.println(hashTable.get(5)); // Output: null
    }
}

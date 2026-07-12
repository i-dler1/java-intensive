public class MyHashMap<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] buckets;
    private int capacity = 16;
    private int count = 0;
    private float loadFactor = 0.75f;

    public MyHashMap() {
        buckets = new Node[capacity];
    }

    public V put(K key, V value) {
        if (key == null) {
            return null;
        }

        int index = Math.abs(key.hashCode()) % capacity;
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (key.equals(current.key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        count++;

        if (count > capacity * loadFactor) {
            resize();
        }

        return null;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newBuckets = new Node[newCapacity];

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;

            while (current != null) {
                Node<K, V> next = current.next;

                int newIndex = Math.abs(current.key.hashCode()) % newCapacity;

                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;

                current = next;
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        int index = Math.abs(key.hashCode()) % capacity;
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (key.equals(current.key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) {
            return null;
        }

        int index = Math.abs(key.hashCode()) % capacity;
        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (key.equals(current.key)) {

                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                count--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        map.put("Анна", 25);
        map.put("Петр", 30);
        map.put("Иван", 40);

        System.out.println(map.get("Анна"));
        System.out.println(map.get("Петр"));

        map.put("Анна", 99);
        System.out.println(map.get("Анна"));

        map.remove("Петр");
        System.out.println(map.get("Петр"));

        System.out.println("Размер: " + map.count);
    }
}

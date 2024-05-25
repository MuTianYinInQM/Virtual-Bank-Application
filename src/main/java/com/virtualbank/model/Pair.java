package com.virtualbank.model;

import java.time.Period;
import java.util.Objects;

/**
 * A simple generic class representing a pair of objects.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Constructs a new Pair with the specified key and value.
     *
     * @param key   the key of the pair
     * @param value the value of the pair
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key of the pair.
     *
     * @return the key of the pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Gets the value of the pair.
     *
     * @return the value of the pair
     */
    public V getValue() {
        return value;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
                Objects.equals(value, pair.value);
    }

    /**
     * Returns the hash code value for this pair.
     *
     * @return a hash code value for this pair
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    /**
     * Returns a string representation of the pair.
     *
     * @return a string representation of the pair
     */
    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

/*
 * Copyright (c) 1995, 2004, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util;

/**
 * The <code>Dictionary</code> class is the abstract parent of any
 * class, such as <code>Hashtable</code>, which maps keys to values.
 * Every key and every value is an object. In any one <tt>Dictionary</tt>
 * object, every key is associated with at most one value. Given a
 * <tt>Dictionary</tt> and a key, the associated element can be looked up.
 * Any non-<code>null</code> object can be used as a key and as a value.
 * <p>

 * 字典类基类，键值映射抽象实现类。每一个键最多只对应一个值，不管是键还是值
 * 都不允许出现null。

 * As a rule, the <code>equals</code> method should be used by
 * implementations of this class to decide if two keys are the same.

 * 使用对象的equals方法来判断两个键是否相等。

 * <p>
 * <strong>NOTE: This class is obsolete.  New implementations should
 * implement the Map interface, rather than extending this class.</strong>

 * 这个类已经被废弃了。新的键值映射类，应该实现Map接口而不是继承本类。
 *
 * @author  unascribed
 * @see     java.util.Map
 * @see     java.lang.Object#equals(java.lang.Object)
 * @see     java.lang.Object#hashCode()
 * @see     java.util.Hashtable
 * @since   JDK1.0
 */
public abstract
class Dictionary<K,V> {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     */
    public Dictionary() {
    }

    /**
     * Returns the number of entries (distinct keys) in this dictionary.

     * 该字典中，键值对的个数。

     *
     * @return  the number of keys in this dictionary.
     */
    abstract public int size();

    /**
     * Tests if this dictionary maps no keys to value. The general contract
     * for the <tt>isEmpty</tt> method is that the result is true if and only
     * if this dictionary contains no entries.

     * 判断该字典是否为空，如果一个字典不包含任何键值对，我们认为该字典是空的。

     *
     * @return  <code>true</code> if this dictionary maps no keys to values;
     *          <code>false</code> otherwise.
     */
    abstract public boolean isEmpty();

    /**
     * Returns an enumeration of the keys in this dictionary. The general
     * contract for the keys method is that an <tt>Enumeration</tt> object
     * is returned that will generate all the keys for which this dictionary
     * contains entries.

     * 以Enumeration的形式，返回所有的key。
     *
     * @return  an enumeration of the keys in this dictionary.
     * @see     java.util.Dictionary#elements()
     * @see     java.util.Enumeration
     */
    abstract public Enumeration<K> keys();

    /**
     * Returns an enumeration of the values in this dictionary. The general
     * contract for the <tt>elements</tt> method is that an
     * <tt>Enumeration</tt> is returned that will generate all the elements
     * contained in entries in this dictionary.
     
     * 以Enumeration的形式，返回所有value。

     *
     * @return  an enumeration of the values in this dictionary.
     * @see     java.util.Dictionary#keys()
     * @see     java.util.Enumeration
     */
    abstract public Enumeration<V> elements();

    /**
     * Returns the value to which the key is mapped in this dictionary.
     * The general contract for the <tt>isEmpty</tt> method is that if this
     * dictionary contains an entry for the specified key, the associated
     * value is returned; otherwise, <tt>null</tt> is returned.

     * 给定Key返回指定的value。如果不存在该键值对，则返回null。如果给定的key
     * 是null，那么抛出NullPointerException异常。

     *
     * @return  the value to which the key is mapped in this dictionary;
     * @param   key   a key in this dictionary.
     *          <code>null</code> if the key is not mapped to any value in
     *          this dictionary.
     * @exception NullPointerException if the <tt>key</tt> is <tt>null</tt>.
     * @see     java.util.Dictionary#put(java.lang.Object, java.lang.Object)
     */
    abstract public V get(Object key);

    /**
     * Maps the specified <code>key</code> to the specified
     * <code>value</code> in this dictionary. Neither the key nor the
     * value can be <code>null</code>.

     * 插入一个键值对，key和value都不能是null。

     * <p>
     * If this dictionary already contains an entry for the specified
     * <tt>key</tt>, the value already in this dictionary for that
     * <tt>key</tt> is returned, after modifying the entry to contain the
     *  new element. <p>If this dictionary does not already have an entry
     *  for the specified <tt>key</tt>, an entry is created for the
     *  specified <tt>key</tt> and <tt>value</tt>, and <tt>null</tt> is
     *  returned.

     * 如果已经存在一个具有相同key的键值对，那么将该键值对的value修改为指定
     * 的value，并将旧的value返回。

     * 如果不存在具有相同key的键值对，那么返回null。

     * <p>
     * The <code>value</code> can be retrieved by calling the
     * <code>get</code> method with a <code>key</code> that is equal to
     * the original <code>key</code>.
     *
     * @param      key     the hashtable key.
     * @param      value   the value.
     * @return     the previous value to which the <code>key</code> was mapped
     *             in this dictionary, or <code>null</code> if the key did not
     *             have a previous mapping.
     * @exception  NullPointerException  if the <code>key</code> or
     *               <code>value</code> is <code>null</code>.

     * 如果给定的key或者value是null，那么抛出NullPointerException异常。

     * @see        java.lang.Object#equals(java.lang.Object)
     * @see        java.util.Dictionary#get(java.lang.Object)
     */
    abstract public V put(K key, V value);

    /**
     * Removes the <code>key</code> (and its corresponding
     * <code>value</code>) from this dictionary. This method does nothing
     * if the <code>key</code> is not in this dictionary.

     * 根据给定的key找到相应的键值对，并返回对应的value。如果不存在这样的
     * 键值对，那么返回null。

     *
     * @param   key   the key that needs to be removed.
     * @return  the value to which the <code>key</code> had been mapped in this
     *          dictionary, or <code>null</code> if the key did not have a
     *          mapping.
     * @exception NullPointerException if <tt>key</tt> is <tt>null</tt>.

     * 如果给定的key是null，则抛出NullPointerException异常。     

     */
    abstract public V remove(Object key);
}

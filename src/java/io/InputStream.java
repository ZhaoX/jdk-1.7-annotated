/*
 * Copyright (c) 1994, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 * This abstract class is the superclass of all classes representing
 * an input stream of bytes.
 *
 * <p> Applications that need to define a subclass of <code>InputStream</code>
 * must always provide a method that returns the next byte of input.
 *
 * 所有输入流的抽象基类。
 * 所有该类的子类，必须提供一个返回输入流中下一个字节的方法。
 *
 * @author  Arthur van Hoff
 * @see     java.io.BufferedInputStream
 * @see     java.io.ByteArrayInputStream
 * @see     java.io.DataInputStream
 * @see     java.io.FilterInputStream
 * @see     java.io.InputStream#read()
 * @see     java.io.OutputStream
 * @see     java.io.PushbackInputStream
 * @since   JDK1.0
 */
public abstract class InputStream implements Closeable {

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * 读取输入流中的下一个字节。
     * 该方法是阻塞的。
     *
     * <p> A subclass must provide an implementation of this method.
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public abstract int read() throws IOException;

    /**
     * Reads some number of bytes from the input stream and stores them into
     * the buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     *
     * <p> If the length of <code>b</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at the
     * end of the file, the value <code>-1</code> is returned; otherwise, at
     * least one byte is read and stored into <code>b</code>.
     *
     * 除非整到读到了输入流末尾。不然该方法最少会读取一个字节到数据b中。
     *
     * <p> The first byte read is stored into element <code>b[0]</code>, the
     * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     * at most, equal to the length of <code>b</code>. Let <i>k</i> be the
     * number of bytes actually read; these bytes will be stored in elements
     * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[</code><i>k</i><code>]</code> through
     * <code>b[b.length-1]</code> unaffected.
     *
     * <p> The <code>read(b)</code> method for class <code>InputStream</code>
     * has the same effect as: <pre><code> read(b, 0, b.length) </code></pre>
     *
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  If the first byte cannot be read for any reason
     * other than the end of the file, if the input stream has been closed, or
     * if some other I/O error occurs.
     * @exception  NullPointerException  if <code>b</code> is <code>null</code>.
     * @see        java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from the input stream into
     * an array of bytes.  An attempt is made to read as many as
     * <code>len</code> bytes, but a smaller number may be read.
     * The number of bytes actually read is returned as an integer.
     *
     * <p> This method blocks until input data is available, end of file is
     * detected, or an exception is thrown.
     *
     * <p> If <code>len</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at end of
     * file, the value <code>-1</code> is returned; otherwise, at least one
     * byte is read and stored into <code>b</code>.
     *
     * <p> The first byte read is stored into element <code>b[off]</code>, the
     * next one into <code>b[off+1]</code>, and so on. The number of bytes read
     * is, at most, equal to <code>len</code>. Let <i>k</i> be the number of
     * bytes actually read; these bytes will be stored in elements
     * <code>b[off]</code> through <code>b[off+</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[off+</code><i>k</i><code>]</code> through
     * <code>b[off+len-1]</code> unaffected.
     *
     * <p> In every case, elements <code>b[0]</code> through
     * <code>b[off]</code> and elements <code>b[off+len]</code> through
     * <code>b[b.length-1]</code> are unaffected.
     *
     * <p> The <code>read(b,</code> <code>off,</code> <code>len)</code> method
     * for class <code>InputStream</code> simply calls the method
     * <code>read()</code> repeatedly. If the first such call results in an
     * <code>IOException</code>, that exception is returned from the call to
     * the <code>read(b,</code> <code>off,</code> <code>len)</code> method.  If
     * any subsequent call to <code>read()</code> results in a
     * <code>IOException</code>, the exception is caught and treated as if it
     * were end of file; the bytes read up to that point are stored into
     * <code>b</code> and the number of bytes read before the exception
     * occurred is returned. The default implementation of this method blocks
     * until the requested amount of input data <code>len</code> has been read,
     * end of file is detected, or an exception is thrown. Subclasses are encouraged
     * to provide a more efficient implementation of this method.
     *
     * 该方法默认的实现是阻塞的，即一直阻塞，知道读到len个字节的数据，或者遇到文件结束，或者出现异常。
     *
     * 该方法重复调用read()方法来实现批量读取的功能.
     * 如果读取第一个字节的时候发生异常，则抛出该异常给调用方;
     * 如果在读取中间某个字节时，发生异常，则返回已经读到的数据给调用方。
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in array <code>b</code>
     *                   at which the data is written.
     * @param      len   the maximum number of bytes to read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException If the first byte cannot be read for any reason
     * other than end of file, or if the input stream has been closed, or if
     * some other I/O error occurs.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @see        java.io.InputStream#read()
     */
    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from this input
     * stream. The <code>skip</code> method may, for a variety of reasons, end
     * up skipping over some smaller number of bytes, possibly <code>0</code>.
     * This may result from any of a number of conditions; reaching end of file
     * before <code>n</code> bytes have been skipped is only one possibility.
     * The actual number of bytes skipped is returned.  If <code>n</code> is
     * negative, no bytes are skipped.
     *
     * 实际skip的字节数有可能小于n，这种情况下，返回实际skip的字节数。
     *
     * <p> The <code>skip</code> method of this class creates a
     * byte array and then repeatedly reads into it until <code>n</code> bytes
     * have been read or the end of the stream has been reached. Subclasses are
     * encouraged to provide a more efficient implementation of this method.
     * For instance, the implementation may depend on the ability to seek.
     *
     * 该方法的默认实现，是通过建立一个大小为MAX_SKIP_BUFFER_SIZE的缓冲区，来不断读取，知道到达所需位置。
     * 子类可以继承该方法，提供更高效的实现。比如，对于文件系统，可以利用seek来实现skip的功能。
     *
     * 注意到read使用的参数类型是int，而skip使用的是long。
     *
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if the stream does not support seek,
     *                          or if some other I/O error occurs.
     */
    public long skip(long n) throws IOException {

        long remaining = n;
        int nr;

        if (n <= 0) {
            return 0;
        }

        int size = (int)Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
        byte[] skipBuffer = new byte[size];
        while (remaining > 0) {
            nr = read(skipBuffer, 0, (int)Math.min(size, remaining));
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }

        return n - remaining;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation
     * might be the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     *
     * <p> Note that while some implementations of {@code InputStream} will return
     * the total number of bytes in the stream, many will not.  It is
     * never correct to use the return value of this method to allocate
     * a buffer intended to hold all data in this stream.
     *
     * 返回值并不一定代表输入流的数据总量。
     *
     * <p> A subclass' implementation of this method may choose to throw an
     * {@link IOException} if this input stream has been closed by
     * invoking the {@link #close()} method.
     *
     * <p> The {@code available} method for class {@code InputStream} always
     * returns {@code 0}.
     *
     * 默认实现总是返回0。
     *
     * <p> This method should be overridden by subclasses.
     *
     * @return     an estimate of the number of bytes that can be read (or skipped
     *             over) from this input stream without blocking or {@code 0} when
     *             it reaches the end of the input stream.
     * @exception  IOException if an I/O error occurs.
     */
    public int available() throws IOException {
        return 0;
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     *
     * <p> The <code>close</code> method of <code>InputStream</code> does
     * nothing.
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public void close() throws IOException {}

    /**
     * Marks the current position in this input stream. A subsequent call to
     * the <code>reset</code> method repositions this stream at the last marked
     * position so that subsequent reads re-read the same bytes.
     *
     * Mark输入流的当前位置。
     * Mark之后，正常使用该输入流。但再执行的reset的方法，会将输入流的当前位置reset为之前mark的位置。
     *
     * <p> The <code>readlimit</code> arguments tells this input stream to
     * allow that many bytes to be read before the mark position gets
     * invalidated.
     *
     * 参数readlimit，表明了mark的有效期。即，mark之后，读取readlimit个字节之后，本次mark失效。
     *
     * <p> The general contract of <code>mark</code> is that, if the method
     * <code>markSupported</code> returns <code>true</code>, the stream somehow
     * remembers all the bytes read after the call to <code>mark</code> and
     * stands ready to supply those same bytes again if and whenever the method
     * <code>reset</code> is called.  However, the stream is not required to
     * remember any data at all if more than <code>readlimit</code> bytes are
     * read from the stream before <code>reset</code> is called.
     *
     * <p> Marking a closed stream should not have any effect on the stream.
     *
     * Mark一个已经关闭的输入流，不产生任何效果。
     *
     * <p> The <code>mark</code> method of <code>InputStream</code> does
     * nothing.
     *
     * 注意该方法是线程同步的，为什么mark, reset两个方法是同步的，而read，skip等则不是呢？
     *
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.InputStream#reset()
     */
    public synchronized void mark(int readlimit) {}

    /**
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     *
     * <p> The general contract of <code>reset</code> is:
     *
     * <p><ul>
     *
     * <li> If the method <code>markSupported</code> returns
     * <code>true</code>, then:
     * 如果markSupported方法返回true，那么：
     *
     *     <ul><li> If the method <code>mark</code> has not been called since
     *     the stream was created, or the number of bytes read from the stream
     *     since <code>mark</code> was last called is larger than the argument
     *     to <code>mark</code> at that last call, then an
     *     <code>IOException</code> might be thrown.
     *     如果在调用reset之前没有调用过mark，或者读取的字节数已经超出了mark的readLimit限制，
     *     那么，抛出IOException。
     *
     *     <li> If such an <code>IOException</code> is not thrown, then the
     *     stream is reset to a state such that all the bytes read since the
     *     most recent call to <code>mark</code> (or since the start of the
     *     file, if <code>mark</code> has not been called) will be resupplied
     *     to subsequent callers of the <code>read</code> method, followed by
     *     any bytes that otherwise would have been the next input data as of
     *     the time of the call to <code>reset</code>. </ul>
     *     如果IOException没有抛出。那么reset这个输入流到最近一次mark的位置，
     *     或者到输入流的开头（如果mark没有被调用过）。
     *
     * <li> If the method <code>markSupported</code> returns
     * <code>false</code>, then:
     * 如果markSupported方法返回false，那么：
     *
     *     <ul><li> The call to <code>reset</code> may throw an
     *     <code>IOException</code>.
     *
     *     <li> If an <code>IOException</code> is not thrown, then the stream
     *     is reset to a fixed state that depends on the particular type of the
     *     input stream and how it was created. The bytes that will be supplied
     *     to subsequent callers of the <code>read</code> method depend on the
     *     particular type of the input stream. </ul></ul>
     *
     * <p>The method <code>reset</code> for class <code>InputStream</code>
     * does nothing except throw an <code>IOException</code>.
     *
     * @exception  IOException  if this stream has not been marked or if the
     *               mark has been invalidated.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods. Whether or not <code>mark</code> and
     * <code>reset</code> are supported is an invariant property of a
     * particular input stream instance. The <code>markSupported</code> method
     * of <code>InputStream</code> returns <code>false</code>.
     *
     * 如果该输入流支持mark和reset方法，那么返回true，否则返回false。
     *
     * @return  <code>true</code> if this stream instance supports the mark
     *          and reset methods; <code>false</code> otherwise.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

}

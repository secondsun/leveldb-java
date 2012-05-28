/*
 * Copyright 2012 Andrey Vityuk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.leveldb.util;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;
import it.unimi.dsi.fastutil.bytes.ByteArrays;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ByteArrayBuilder {
	private static final int DEFAULT_INITIAL_CAPACITY = 10;
	private static final ByteEncoder DEFAULT_BYTE_ENCODER = ByteEncoder.bigEndian();

	private final ByteEncoder encoder;
	private byte[] data;
	private int size;

	public ByteArrayBuilder(ByteEncoder encoder, int initialCapacity) {
		this.encoder = encoder;
		data = new byte[initialCapacity];
	}

	public ByteArrayBuilder(ByteEncoder encoder) {
		this(encoder, DEFAULT_INITIAL_CAPACITY);
	}

	public ByteArrayBuilder(int initialCapacity) {
		this(DEFAULT_BYTE_ENCODER, initialCapacity);
	}

	public ByteArrayBuilder() {
		this(DEFAULT_BYTE_ENCODER, DEFAULT_INITIAL_CAPACITY);
	}

	public void ensureCapacity(int capacity) {
		data = ByteArrays.grow(data, capacity);
	}

	public void ensureAdditionalCapacity(int capacity) {
		ensureCapacity(size + capacity);
	}

	public void extendToSize(int size) {
		if (this.size < size) {
			this.size = size;
			ensureCapacity(size);
		}
	}

	public int size() {
		return size;
	}

	public void trimToSize() {
		data = ByteArrays.trim(data, size);
	}

	public void clear() {
		Arrays.fill(data, (byte) 0);
		size = 0;
	}

	public void setByte(int offset, byte value) {
		data[offset] = value;
	}

	public byte getByte(int offset) {
		return data[offset];
	}

	public void setShort(int offset, short value) {
		encoder.encodeShort(data, offset, value);
	}

	public short getShort(int offset) {
		return encoder.decodeShort(data, offset);
	}

	public void setInt(int offset, int value) {
		encoder.encodeInt(data, offset, value);
	}

	public int getInt(int offset) {
		return encoder.decodeInt(data, offset);
	}

	public void setLong(int offset, long value) {
		encoder.encodeLong(data, offset, value);
	}

	public long getLong(int offset) {
		return encoder.decodeLong(data, offset);
	}

	public void setBytes(int offset, byte[] value) {
		System.arraycopy(value, offset, data, size, value.length);
	}

	public void appendByte(byte value) {
		ensureAdditionalCapacity(1);
		setByte(size, value);
		size += 1;
	}

	public void appendShort(short value) {
		ensureAdditionalCapacity(Shorts.BYTES);
		setShort(size, value);
		size += Shorts.BYTES;
	}

	public void appendInt(int value) {
		ensureAdditionalCapacity(Ints.BYTES);
		setInt(size, value);
		size += Ints.BYTES;
	}

	public void appendLong(long value) {
		ensureAdditionalCapacity(Longs.BYTES);
		setLong(size, value);
		size += Longs.BYTES;
	}

	public void appendBytes(byte[] value) {
		ensureAdditionalCapacity(value.length);
		setBytes(size, value);
		size += value.length;
	}

	public byte[] toArray() {
		return Arrays.copyOf(data, size);
	}

	public ByteBuffer asByteBuffer() {
		return ByteBuffer.wrap(data, 0, size).order(encoder.getByteOrder()).asReadOnlyBuffer();
	}
}

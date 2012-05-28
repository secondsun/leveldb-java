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
 * 
 * Data format
 *
 * WriteBatch::rep_ :=
 *    sequence: fixed64
 *    count: fixed32
 *    data: record[count]
 * record :=
 *    kTypeValue varstring varstring         |
 *    kTypeDeletion varstring
 * varstring :=
 *    len: varint32
 *    data: uint8[len]
 * 
 */


package org.leveldb;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import org.leveldb.core.DataCurruptionException;
import org.leveldb.core.ValueType;
import org.leveldb.util.ByteArrayBuilder;
import org.leveldb.util.ByteEncoder;

import javax.annotation.concurrent.NotThreadSafe;
import java.nio.ByteBuffer;

@NotThreadSafe
public class WriteBatch {
	private static final ByteEncoder ENCODER = ByteEncoder.littleEndian();

	private static final int SEQUENCE_OFFSET = 0;
	private static final int SEQUENCE_LENGTH = Longs.BYTES;
	private static final int SIZE_OFFSET = SEQUENCE_OFFSET + SEQUENCE_LENGTH;
	private static final int SIZE_LENGTH = Ints.BYTES;
	private static final int HEADER_SIZE = SEQUENCE_LENGTH + SIZE_LENGTH;

	private final ByteArrayBuilder data = new ByteArrayBuilder(ENCODER, HEADER_SIZE);

	public WriteBatch() {
		initHeader();
	}

	public void put(byte[] key, byte[] value) {
		incrementCount();
		addValueType(ValueType.VALUE);
		addBytes(key);
		addBytes(value);
	}

	public void delete(byte[] key) {
		incrementCount();
		addValueType(ValueType.DELETION);
		addBytes(key);
	}

	public void clear() {
		data.clear();
		initHeader();
	}

	public void forEach(Handler handler) {
		ByteBuffer buffer = data.asByteBuffer();
		if (buffer.remaining() < HEADER_SIZE) {
			throw new DataCurruptionException("Malformed WriteBatch (too small)");
		}
		buffer.getLong();

		final int size = buffer.getInt();
		while (buffer.hasRemaining()) {
			ValueType valueType = readValueType(buffer);
			switch (valueType) {
				case VALUE:
//					buffer.
					break;
				case DELETION:
					break;
				default:
					throw new IllegalStateException();
			}
		}
	}

	private ValueType readValueType(ByteBuffer buffer) {
		try {
			return ValueType.forCode(buffer.get());
		} catch (IllegalArgumentException e) {
			throw new DataCurruptionException("Unknown value type tag", e);
		}
	}

	public ByteBuffer toByteBuffer() {
		return null;
	}

	public interface Handler {
		void put(ByteBuffer key, ByteBuffer value);

		void delete(ByteBuffer key);
	}

	private void initHeader() {
		data.appendLong(0L);
		data.appendInt(0);
	}

	private long getSequence() {
		return data.getLong(SEQUENCE_OFFSET);
	}

	private void setSequence(long sequence) {
		data.setLong(SEQUENCE_OFFSET, sequence);
	}


	private void incrementCount() {
		setCount(getCount() + 1);
	}

	private int getCount() {
		return data.getInt(SIZE_OFFSET);
	}

	private void setCount(int count) {
		data.setInt(SIZE_OFFSET, count);
	}

	private void addValueType(ValueType valueType) {
		data.appendByte(valueType.getCode());
	}

	private void addBytes(byte[] value) {
		data.ensureAdditionalCapacity(Ints.BYTES + value.length);
		data.appendInt(value.length);
		data.appendBytes(value);
	}
}

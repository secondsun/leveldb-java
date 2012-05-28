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

import java.nio.ByteOrder;

public abstract class ByteEnconder {
	private static final ByteEnconder BIG_ENDIAN = new BigEndianByteEncoding();
	private static final ByteEnconder LITTLE_ENDIAN = new LittleEndianByteEncoding();

	private ByteEnconder() {
	}

	public static ByteEnconder bigEndian() {
		return BIG_ENDIAN;
	}

	public static ByteEnconder littleEndian() {
		return LITTLE_ENDIAN;
	}

	public static ByteEnconder forByteOrder(ByteOrder byteOrder) {
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			return bigEndian();
		} else {
			return littleEndian();
		}
	}

	public abstract void encodeShort(byte[] data, int offset, short value);

	public abstract void encodeInt(byte[] data, int offset, int value);

	public abstract void encodeLong(byte[] data, int offset, long value);

	public abstract short decodeShort(byte[] data, int offset);

	public abstract int decodeInt(byte[] data, int offset);

	public abstract long decodeLong(byte[] data, int offset);

	private static final class BigEndianByteEncoding extends ByteEnconder {
		@Override
		public void encodeShort(byte[] data, int offset, short value) {
			data[offset] = (byte) (value >> 8);
			data[offset + 1] = (byte) value;
		}

		@Override
		public void encodeInt(byte[] data, int offset, int value) {
			data[offset] = (byte) (value >> 24);
			data[offset + 1] = (byte) (value >> 16);
			data[offset + 2] = (byte) (value >> 8);
			data[offset + 3] = (byte) value;
		}

		@Override
		public void encodeLong(byte[] data, int offset, long value) {
			data[offset] = (byte) (value >> 56);
			data[offset + 1] = (byte) (value >> 48);
			data[offset + 2] = (byte) (value >> 40);
			data[offset + 3] = (byte) (value >> 32);
			data[offset + 4] = (byte) (value >> 24);
			data[offset + 5] = (byte) (value >> 16);
			data[offset + 6] = (byte) (value >> 8);
			data[offset + 7] = (byte) value;
		}

		@Override
		public short decodeShort(byte[] data, int offset) {
			return (short) ((data[offset] & 0xff) << 8
					| (data[offset + 1] & 0xff));
		}

		@Override
		public int decodeInt(byte[] data, int offset) {
			return (data[offset] & 0xff) << 24
					| (data[offset + 1] & 0xff) << 16
					| (data[offset + 2] & 0xff) << 8
					| (data[offset + 3] & 0xff);
		}

		@Override
		public long decodeLong(byte[] data, int offset) {
			return (data[offset] & 0xffL) << 56
					| (data[offset + 1] & 0xffL) << 48
					| (data[offset + 2] & 0xffL) << 40
					| (data[offset + 3] & 0xffL) << 32
					| (data[offset + 4] & 0xffL) << 24
					| (data[offset + 5] & 0xffL) << 16
					| (data[offset + 6] & 0xffL) << 8
					| (data[offset + 7] & 0xffL);
		}
	}

	private static final class LittleEndianByteEncoding extends ByteEnconder {
		@Override
		public void encodeShort(byte[] data, int offset, short value) {
			data[offset + 1] = (byte) (value >> 8);
			data[offset + 0] = (byte) value;
		}

		@Override
		public void encodeInt(byte[] data, int offset, int value) {
			data[offset + 3] = (byte) (value >> 24);
			data[offset + 2] = (byte) (value >> 16);
			data[offset + 1] = (byte) (value >> 8);
			data[offset] = (byte) value;
		}

		@Override
		public void encodeLong(byte[] data, int offset, long value) {
			data[offset + 7] = (byte) (value >> 56);
			data[offset + 6] = (byte) (value >> 48);
			data[offset + 5] = (byte) (value >> 40);
			data[offset + 4] = (byte) (value >> 32);
			data[offset + 3] = (byte) (value >> 24);
			data[offset + 2] = (byte) (value >> 16);
			data[offset + 1] = (byte) (value >> 8);
			data[offset] = (byte) value;
		}

		@Override
		public short decodeShort(byte[] data, int offset) {
			return (short) ((data[offset + 1] & 0xff) << 8
					| (data[offset] & 0xff));
		}

		@Override
		public int decodeInt(byte[] data, int offset) {
			return (data[offset + 3] & 0xff) << 24
					| (data[offset + 2] & 0xff) << 16
					| (data[offset + 1] & 0xff) << 8
					| (data[offset] & 0xff);
		}

		@Override
		public long decodeLong(byte[] data, int offset) {
			return (data[offset + 7] & 0xffL) << 56
					| (data[offset + 6] & 0xffL) << 48
					| (data[offset + 5] & 0xffL) << 40
					| (data[offset + 4] & 0xffL) << 32
					| (data[offset + 3] & 0xffL) << 24
					| (data[offset + 2] & 0xffL) << 16
					| (data[offset + 1] & 0xffL) << 8
					| (data[offset] & 0xffL);
		}
	}
}

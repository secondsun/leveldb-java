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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ByteEncoderTest {
	private final ByteEncoder bigEndianEncoder = ByteEncoder.bigEndian();
	private final ByteEncoder littleEndianEncoder = ByteEncoder.littleEndian();

	@Test
	public void
	shouldEncodeShortInBigEndian() throws Exception {
		byte[] expected = {0x23, 0x45};
		byte[] actual = new byte[2];

		bigEndianEncoder.encodeShort(actual, 0, (short) 0x2345);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeShortInBigEndianWithOffset() throws Exception {
		byte[] expected = {0, (byte) 0xFE, (byte) 0xDC, 0};
		byte[] actual = new byte[4];

		bigEndianEncoder.encodeShort(actual, 1, (short) 0xFEDC);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeShortInLittleEndian() throws Exception {
		byte[] expected = {0x45, 0x23};
		byte[] actual = new byte[2];

		littleEndianEncoder.encodeShort(actual, 0, (short) 0x2345);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeShortInLittleEndianWithOffset() throws Exception {
		byte[] expected = {0, (byte) 0xDC, (byte) 0xFE, 0};
		byte[] actual = new byte[4];

		littleEndianEncoder.encodeShort(actual, 1, (short) 0xFEDC);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeIntInBigEndian() throws Exception {
		byte[] expected = {0x12, 0x13, 0x14, 0x15};
		byte[] actual = new byte[4];

		bigEndianEncoder.encodeInt(actual, 0, 0x12131415);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeIntInBigEndianWithOffset() throws Exception {
		byte[] expected = {0, (byte) 0xFF, (byte) 0xEE, (byte) 0xDD, (byte) 0xCC, 0};
		byte[] actual = new byte[6];

		bigEndianEncoder.encodeInt(actual, 1, 0xFFEEDDCC);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeIntInLittleEndian() throws Exception {
		byte[] expected = {0x15, 0x14, 0x13, 0x12};
		byte[] actual = new byte[4];

		littleEndianEncoder.encodeInt(actual, 0, 0x12131415);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeIntInLittleEndianWithOffset() throws Exception {
		byte[] expected = {0, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0};
		byte[] actual = new byte[6];

		littleEndianEncoder.encodeInt(actual, 1, 0xFFEEDDCC);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeLongInBigEndian() throws Exception {
		byte[] expected = {0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19};
		byte[] actual = new byte[8];

		bigEndianEncoder.encodeLong(actual, 0, 0x1213141516171819L);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeLongInBigEndianWithOffset() throws Exception {
		byte[] expected = {0,
				(byte) 0xFF, (byte) 0xEE, (byte) 0xDD, (byte) 0xCC,
				(byte) 0xBB, (byte) 0xAA, (byte) 0x99, (byte) 0x88, 0};
		byte[] actual = new byte[10];

		bigEndianEncoder.encodeLong(actual, 1, 0xFFEEDDCCBBAA9988L);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeLongInLittleEndian() throws Exception {
		byte[] expected = {0x19, 0x18, 0x17, 0x16, 0x15, 0x14, 0x13, 0x12};
		byte[] actual = new byte[8];

		littleEndianEncoder.encodeLong(actual, 0, 0x1213141516171819L);

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void
	shouldEncodeLongInLittleEndianWithOffset() throws Exception {
		byte[] expected = {0,
				(byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB,
				(byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0};
		byte[] actual = new byte[10];

		littleEndianEncoder.encodeLong(actual, 1, 0xFFEEDDCCBBAA9988L);

		assertThat(actual, is(equalTo(expected)));
	}
}

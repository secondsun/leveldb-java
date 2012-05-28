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

package org.leveldb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WriteBatchTest {
	private WriteBatch writeBatch;
	private WriteBatch.Handler handler;

	@Before
	public void setUp() throws Exception {
		writeBatch = new WriteBatch();
		handler = mock(WriteBatch.Handler.class);
	}

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(handler);
		writeBatch = null;
		handler = null;
	}

	@Test public void
	shouldPutValueByKey() throws Exception {
		String key = "test key";
		String value = "test value";
		writeBatch.put(key.getBytes(), value.getBytes());

		writeBatch.forEach(handler);

		verify(handler).put(ByteBuffer.wrap(key.getBytes()), ByteBuffer.wrap(value.getBytes()));
	}

	@Test
	public void testDelete() throws Exception {

	}

	@Test
	public void testClear() throws Exception {

	}

	@Test
	public void testForEach() throws Exception {

	}

	@Test
	public void testToByteBuffer() throws Exception {

	}
}

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
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

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
		writeBatch = null;
		handler = null;
	}

	@Test
	public void
	shouldRecordPutOperation() throws Exception {
		String key = "test key";
		String value = "test value";
		writeBatch.put(key.getBytes(), value.getBytes());

		writeBatch.forEach(handler);

		verify(handler).put(key.getBytes(), value.getBytes());
		verifyNoMoreInteractions(handler);
	}

	@Test
	public void
	shouldRecordDeleteOperation() throws Exception {
		String key = "test key";
		writeBatch.delete(key.getBytes());

		writeBatch.forEach(handler);

		verify(handler).delete(key.getBytes());
		verifyNoMoreInteractions(handler);

	}

	@Test
	public void
	shouldRecordMultipleOperations() throws Exception {
		InOrder inOrder = inOrder(handler);

		String key1 = "test key 1";
		String value1 = "test value 1";
		String key2 = "test key 2";
		String value2 = "test value 2";

		writeBatch.put(key1.getBytes(), value1.getBytes());
		writeBatch.delete(key1.getBytes());
		writeBatch.delete(key2.getBytes());
		writeBatch.put(key2.getBytes(), value2.getBytes());
		writeBatch.put(key1.getBytes(), value1.getBytes());

		writeBatch.forEach(handler);

		inOrder.verify(handler).put(key1.getBytes(), value1.getBytes());
		inOrder.verify(handler).delete(key1.getBytes());
		inOrder.verify(handler).delete(key2.getBytes());
		inOrder.verify(handler).put(key2.getBytes(), value2.getBytes());
		inOrder.verify(handler).put(key1.getBytes(), value1.getBytes());
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void
	shouldClearRecordsAfterClearOperation() throws Exception {
		writeBatch.put("test key".getBytes(), "test value".getBytes());
		writeBatch.delete("test key".getBytes());
		writeBatch.clear();

		writeBatch.forEach(handler);

		verifyZeroInteractions(handler);
	}
}

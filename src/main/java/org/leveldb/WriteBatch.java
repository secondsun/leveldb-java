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

import com.google.common.collect.Lists;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class WriteBatch {
	public interface Handler {
		void put(byte[] key, byte[] value);

		void delete(byte[] key);
	}

	private final List<Operation> operations = Lists.newArrayListWithCapacity(1);


	public void put(byte[] key, byte[] value) {
		addOperation(new ValueOperation(key, value));
	}

	public void delete(byte[] key) {
		addOperation(new DeleteOperation(key));
	}

	public void clear() {
		operations.clear();
	}

	public void forEach(Handler handler) {
		for (Operation operation : operations) {
			if (operation instanceof ValueOperation) {
				ValueOperation valueOperation = (ValueOperation) operation;
				handler.put(valueOperation.getKey(), valueOperation.getValue());
			} else {
				handler.delete(operation.getKey());
			}
		}
	}

	private void addOperation(Operation operation) {
		operations.add(operation);
	}

	private static abstract class Operation {
		private final byte[] key;

		Operation(byte[] key) {
			this.key = key;
		}

		public final byte[] getKey() {
			return key;
		}
	}

	private static final class ValueOperation extends Operation {
		private final byte[] value;

		ValueOperation(byte[] key, byte[] value) {
			super(key);
			this.value = value;
		}

		public byte[] getValue() {
			return value;
		}
	}

	private static final class DeleteOperation extends Operation {
		DeleteOperation(byte[] key) {
			super(key);
		}
	}
}

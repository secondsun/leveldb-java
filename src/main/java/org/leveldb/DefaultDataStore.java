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

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class DefaultDataStore implements DataStore {
	@Override
	public void put(byte[] key, byte[] value) {
		new WriteBatch().put(key, value);
	}

	@Override
	public byte[] get(byte[] key) {
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void delete(byte[] key) {
		new WriteBatch().delete(key);
	}
}

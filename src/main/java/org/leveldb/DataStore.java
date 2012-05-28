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

/**
 * Defines an interfaces for accessing data.
 */
public interface DataStore {
	/**
	 * Get the value to which the specified {@code key} is mapped.
	 *
	 * @param key - store key
	 * @return the value to which the specified {@code key} is mapped.
	 *         Value {@code null} is returned if the specified {@code key} is unknown
	 */
	byte[] get(byte[] key);

	/**
	 * Create a mapping from the specified {@code key} to the specified {@code value} in this store.
	 *
	 * @param key   - store key
	 * @param value - store value
	 */
	void put(byte[] key, byte[] value);

	/**
	 * Delete the value to which the specified {@code key} is mapped.
	 *
	 * @param key - store key
	 */
	void delete(byte[] key);
}

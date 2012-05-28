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

package org.leveldb.core;

public enum ValueType {
	DELETION((byte) 0x0),
	VALUE((byte) 0x1);

	private final byte code;

	private ValueType(byte code) {
		this.code = code;
	}

	public byte getCode() {
		return code;
	}

	public static ValueType forCode(byte code) {
		if (code == VALUE.getCode()) {
			return VALUE;
		}
		if (code == DELETION.getCode()) {
			return DELETION;
		}
		throw new IllegalArgumentException("Unsupported code");
	}
}

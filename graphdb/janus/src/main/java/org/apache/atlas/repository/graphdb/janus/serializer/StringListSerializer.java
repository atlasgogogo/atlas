/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.atlas.repository.graphdb.janus.serializer;

import java.util.ArrayList;
import java.util.List;

import org.janusgraph.core.attribute.AttributeSerializer;
import org.janusgraph.diskstorage.ScanBuffer;
import org.janusgraph.diskstorage.WriteBuffer;
import org.janusgraph.graphdb.database.idhandling.VariableLong;
import org.janusgraph.graphdb.database.serialize.attribute.StringSerializer;

/**
 * Serializer for String lists.
 */
public class StringListSerializer implements AttributeSerializer<List<String>> {

    private final StringSerializer stringSerializer = new StringSerializer();

    @Override
    public List<String> read(ScanBuffer buffer) {
        int length = (int)VariableLong.readPositive(buffer);
        List<String> result = new ArrayList<String>(length);
        for(int i = 0; i < length; i++) {
            result.add(stringSerializer.read(buffer));
        }
        return result;
    }

    @Override
    public void write(WriteBuffer buffer, List<String> attributes) {
        VariableLong.writePositive(buffer, attributes.size());
        for(String attr : attributes) {
            stringSerializer.write(buffer, attr);
        }
    }

}

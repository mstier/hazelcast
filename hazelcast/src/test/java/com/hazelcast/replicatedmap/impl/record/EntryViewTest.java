/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.replicatedmap.impl.record;

import com.hazelcast.internal.serialization.SerializationServiceBuilder;
import com.hazelcast.internal.serialization.impl.DefaultSerializationServiceBuilder;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.spi.serialization.SerializationService;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class EntryViewTest extends HazelcastTestSupport {

    @Test
    public void testEntryView() throws Exception {
        ReplicatedMapEntryView entryView = createEntryView();
        verifyFields(entryView);
    }

    @Test
    public void testEntryViewSerialization() throws Exception {
        ReplicatedMapEntryView entryView = createEntryView();
        SerializationServiceBuilder serializationServiceBuilder = new DefaultSerializationServiceBuilder();
        SerializationService serializationService = serializationServiceBuilder.build();

        Data data = serializationService.toData(entryView);
        ReplicatedMapEntryView deserialized = serializationService.toObject(data);
        verifyFields(deserialized);

    }

    private ReplicatedMapEntryView createEntryView() {
        ReplicatedMapEntryView entryView = new ReplicatedMapEntryView("foo", "bar");
        entryView.setCreationTime(1);
        entryView.setLastAccessTime(2);
        entryView.setLastUpdateTime(3);
        entryView.setHits(4);
        entryView.setTtl(5);
        return entryView;
    }

    private void verifyFields(ReplicatedMapEntryView entryView) {
        assertEquals("foo", entryView.getKey());
        assertEquals("bar", entryView.getValue());
        assertEquals(1, entryView.getCreationTime());
        assertEquals(2, entryView.getLastAccessTime());
        assertEquals(3, entryView.getLastUpdateTime());
        assertEquals(4, entryView.getHits());
        assertEquals(5, entryView.getTtl());
        assertEquals(-1, entryView.getExpirationTime());
        assertEquals(-1, entryView.getLastStoredTime());
        assertEquals(-1, entryView.getCost());
        assertEquals(-1, entryView.getVersion());
    }


}

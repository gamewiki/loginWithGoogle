/*
 * Copyright 2015 Yann Le Moigne
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.javatic.mongo.jacksonCodec.javaTime.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.undercouch.bson4jackson.BsonGenerator;
import de.undercouch.bson4jackson.serializers.BsonSerializer;

import java.io.IOException;
import java.util.Date;
import java.util.function.ToLongFunction;

public abstract class InstantSerializerBase<T> extends BsonSerializer<T> {
    private final ToLongFunction<T> getEpochMillis;

    protected InstantSerializerBase(Class<T> supportedType, ToLongFunction<T> getEpochMillis) {
        this.getEpochMillis = getEpochMillis;
    }

    @Override
    public void serialize(T o, BsonGenerator bsonGenerator, SerializerProvider serializerProvider) throws
        IOException, JsonProcessingException {
        if (o == null) {
            serializerProvider.defaultSerializeNull(bsonGenerator);
        } else {
            bsonGenerator.writeDateTime(new Date(this.getEpochMillis.applyAsLong(o)));
        }
    }
}

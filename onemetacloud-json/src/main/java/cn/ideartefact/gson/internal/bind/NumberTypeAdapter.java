/*
 * Copyright (C) 2020 Google Inc.
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

package cn.ideartefact.gson.internal.bind;

import cn.ideartefact.gson.Gson;
import cn.ideartefact.gson.JsonSyntaxException;
import cn.ideartefact.gson.ToNumberPolicy;
import cn.ideartefact.gson.ToNumberStrategy;
import cn.ideartefact.gson.TypeAdapter;
import cn.ideartefact.gson.TypeAdapterFactory;
import cn.ideartefact.gson.reflect.TypeToken;
import cn.ideartefact.gson.stream.JsonReader;
import cn.ideartefact.gson.stream.JsonToken;
import cn.ideartefact.gson.stream.JsonWriter;
import java.io.IOException;

/** Type adapter for {@link Number}. */
public final class NumberTypeAdapter extends TypeAdapter<Number> {
  /** Gson default factory using {@link ToNumberPolicy#LAZILY_PARSED_NUMBER}. */
  private static final TypeAdapterFactory LAZILY_PARSED_NUMBER_FACTORY =
      newFactory(ToNumberPolicy.LAZILY_PARSED_NUMBER);

  private final ToNumberStrategy toNumberStrategy;

  private NumberTypeAdapter(ToNumberStrategy toNumberStrategy) {
    this.toNumberStrategy = toNumberStrategy;
  }

  private static TypeAdapterFactory newFactory(ToNumberStrategy toNumberStrategy) {
    final NumberTypeAdapter adapter = new NumberTypeAdapter(toNumberStrategy);
    return new TypeAdapterFactory() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        return type.getRawType() == Number.class ? (TypeAdapter<T>) adapter : null;
      }
    };
  }

  public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
    if (toNumberStrategy == ToNumberPolicy.LAZILY_PARSED_NUMBER) {
      return LAZILY_PARSED_NUMBER_FACTORY;
    } else {
      return newFactory(toNumberStrategy);
    }
  }

  @Override
  public Number read(JsonReader in) throws IOException {
    JsonToken jsonToken = in.peek();
    switch (jsonToken) {
      case NULL:
        in.nextNull();
        return null;
      case NUMBER:
      case STRING:
        return toNumberStrategy.readNumber(in);
      default:
        throw new JsonSyntaxException(
            "Expecting number, got: " + jsonToken + "; at path " + in.getPath());
    }
  }

  @Override
  public void write(JsonWriter out, Number value) throws IOException {
    out.value(value);
  }
}

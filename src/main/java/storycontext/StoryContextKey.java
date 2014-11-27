/*
Copyright 2014 Robert Boothby

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package storycontext;

import typedmap.TypedKey;

/**
 * <p>This class is a StoryContext specific implementation of {@link typedmap.TypedKey} which defines the type of value
 * associated with this key at compile time using Generics. This implementation includes
 * a description  that is part of the {@link #toString()} method and is intended to be used when a story fails
 * and the context is logged so that the conditions of the failure can be investigated.</p>
 * <p>This class deliberately does not override the basic {@link java.lang.Object#equals(Object)} and {@link
 * Object#hashCode()} methods, this is because the intended use case for this class is as public static attributes of
 * a steps class and it makes sense that two instances with the same description are treated independently in context of
 * their steps classes.</p>
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public class StoryContextKey<T> implements TypedKey<T> {

    private final String keyDescription;

    /**
     * Construct an instance of StoryContextKey with the passed in description.
     * @param keyDescription They description to apply to the key and the object associated with the key in the
     *                       StoryContext.
     */
    public StoryContextKey(String keyDescription) {
        this.keyDescription = keyDescription;
    }

    /**
     * Get a new correctly typed instance of StoryContextKey.
     * @param keyDescription The description to apply to the key.
     * @param <T> The type of the object to be associated with this key.
     * @return a new correctly typed instance of StoryContextKey.
     */
    public static <T> StoryContextKey<T> storyContextKey(String keyDescription){
        return new StoryContextKey<T>(keyDescription);
    }

    /**
     * Get a string representation of the StoryContextKey including the description.
     * @return a string representation of the StoryContextKey including the description.
     */
    @Override
    public String toString() {
        return new StringBuilder("StoryContextKey{ '").append(keyDescription).append("' }").toString();
    }
}

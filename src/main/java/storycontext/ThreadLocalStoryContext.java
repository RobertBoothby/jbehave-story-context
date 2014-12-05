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
import typedmap.TypedMap;
import typedmap.TypedMapDecorator;

import java.util.Map;

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby
 */
public class ThreadLocalStoryContext implements StoryContext {

    private ThreadLocal<TypedMap> typedMapThreadLocal = new ThreadLocal<TypedMap>(){
        @Override
        protected TypedMap initialValue() {
            return TypedMapDecorator.typedMap();
        }
    };

    public static ThreadLocalStoryContext storyContext() {
        return new ThreadLocalStoryContext();
    }

    @Override
    public <T> T store(StoryContextKey<T> storyContextKey, T value) {
        return typedMapThreadLocal.get().putTyped(storyContextKey, value);
    }

    @Override
    public <T> T retrieve(StoryContextKey<T> storyContextKey) {
        return typedMapThreadLocal.get().getTyped(storyContextKey);
    }

    @Override
    public void reset() {
        typedMapThreadLocal.get().clear();
    }

    @Override
    public String toString() {
        return new StringBuilder("ThreadLocalStoryContext{")
                .append("typedMap={ \n").append(contentsAsString()).append("\n}}").toString();
    }

    protected String contentsAsString() {
        final StringBuilder sb = new StringBuilder();
        for(Map.Entry<TypedKey<?>, Object> entry : typedMapThreadLocal.get().entrySet()){
            sb.append(entry.getKey().toString()).append(" = '").append(entry.getValue()).append("'\n");
        }
        return sb.toString();
    }

}

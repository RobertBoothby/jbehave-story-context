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

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby
 */
public class ThreadSafeStoryContext implements StoryContext {

    private final DefaultStoryContext wrappedContext = DefaultStoryContext.storyContext();
    private final Object mutex = new Object();

    public static ThreadSafeStoryContext storyContext() {
        return new ThreadSafeStoryContext();
    }

    @Override
    public <T> T store(StoryContextKey<T> storyContextKey, T value) {
        synchronized (mutex) {
            return wrappedContext.store(storyContextKey, value);
        }
    }

    @Override
    public <T> T retrieve(StoryContextKey<T> storyContextKey) {
        synchronized (mutex) {
            return wrappedContext.retrieve(storyContextKey);
        }
    }

    @Override
    public void reset() {
        synchronized (mutex) {
            wrappedContext.reset();
        }
    }

    @Override
    public String toString() {
        synchronized (mutex) {
            return new StringBuilder(ThreadSafeStoryContext.class.getSimpleName())
                    .append("{typedMap={ \n").append(wrappedContext.contentsAsString()).append("\n}}").toString();
        }
    }
}

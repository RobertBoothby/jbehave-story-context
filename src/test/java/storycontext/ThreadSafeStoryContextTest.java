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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static storycontext.ThreadSafeStoryContext.storyContext;
import static storycontext.StoryContextKey.storyContextKey;

/**
 * <p>Unfortunately this is an extremely tough class to test the concurrency on as the unit of concurrency is the whole
 * class and is synchronized at method level. The concurrency semantic is very simple, synchronized on an internal mutex
 * object at method level but if I can't test it, I'm wondering if it has any value...</p>
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby
 */
public class ThreadSafeStoryContextTest {

    private static final StoryContextKey<String> aStringKey = storyContextKey("Storing a String");
    private static final StoryContextKey<String> anotherStringKey = storyContextKey("Storing another String");
    private static final StoryContextKey<Integer> anIntegerKey = storyContextKey("Storing an Integer");

    @Test
    public void shouldStoreAndRetrieveValueByKey() {
        //Given
        StoryContext storyContext = storyContext();

        //When
        storyContext.store(aStringKey, "A stored String");

        //Then
        assertThat(storyContext.retrieve(aStringKey), is("A stored String"));
    }

    @Test
    public void shouldStoreAndRetrieveMultipleValuesByKey() {
        //Given
        StoryContext storyContext = storyContext();

        //When
        storyContext.store(aStringKey, "A stored String");
        storyContext.store(anotherStringKey, "Another stored String");
        storyContext.store(anIntegerKey, 1);

        //Then
        assertThat(storyContext.retrieve(aStringKey), is("A stored String"));
        assertThat(storyContext.retrieve(anotherStringKey), is("Another stored String"));
        assertThat(storyContext.retrieve(anIntegerKey), is(1));
    }

    @Test
    public void shouldReset() {
        //Given
        StoryContext storyContext = storyContext();
        storyContext.store(aStringKey, "A stored String");
        storyContext.store(anotherStringKey, "Another stored String");
        storyContext.store(anIntegerKey, 1);

        //When
        storyContext.reset();

        //Then
        assertThat(storyContext.retrieve(aStringKey), is(nullValue(String.class)));
        assertThat(storyContext.retrieve(anotherStringKey), is(nullValue(String.class)));
        assertThat(storyContext.retrieve(anIntegerKey), is(nullValue(Integer.class)));
    }

    @Test
    public void shouldGenerateMeaningfulToString() {
        //Given
        StoryContext storyContext = storyContext();
        storyContext.store(aStringKey, "A stored String");
        storyContext.store(anotherStringKey, "Another stored String");
        storyContext.store(anIntegerKey, 1);

        //When
        String storyContextString = storyContext.toString();

        //Then
        assertThat(storyContextString, startsWith("ThreadSafeStoryContext{typedMap={"));
        assertThat(storyContextString, endsWith("}}"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing an Integer' } = '1'"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing another String' } = 'Another stored String'"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing a String' } = 'A stored String'"));
    }
}

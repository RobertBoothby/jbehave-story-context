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
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static storycontext.StoryContextKey.storyContextKey;

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public class StoryContextKeyTest {

    @Test
    public void shouldStoreTheKey() {
        //Given
        String description = "A Story Context Key";

        //When
        StoryContextKey<String> key = storyContextKey(description);

        //Then
        assertThat(key.toString(), is("StoryContextKey{ '"+ description + "' }"));
    }

    @Test
    public void shouldNotBeEqualEvenWithSameDescription() {
        //Given
        String description = "A Story Context Key";

        //When
        StoryContextKey<String> key = storyContextKey(description);
        StoryContextKey<String> anotherKey = storyContextKey(description);

        //Then
        assertThat(key, is(not(anotherKey)));
    }

    @Test
    public void shouldNotHaveSameHashCodeEvenWithSameDescription() {
        //Given
        String description = "A Story Context Key";

        //When
        StoryContextKey<String> key = storyContextKey(description);
        StoryContextKey<String> anotherKey = storyContextKey(description);

        //Then
        assertThat(key.hashCode(), is(not(anotherKey.hashCode())));
    }

}

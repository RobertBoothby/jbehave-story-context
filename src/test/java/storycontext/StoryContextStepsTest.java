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

import org.jbehave.core.annotations.AfterStory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
@RunWith(MockitoJUnitRunner.class)
public class StoryContextStepsTest {
    @Mock private StoryContext storyContext;

    @Test
    public void shouldResetStoryContextWhenResetStoryContextCalled() {
        //Given
        StoryContextSteps storyContextSteps = new StoryContextSteps(storyContext);

        //When
        storyContextSteps.resetStoryContext();

        //Then
        verify(storyContext).reset();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resetStoryContextMethodShouldBeAnnotatedWithAfterStory() throws NoSuchMethodException {
        //Given
        final Method resetStoryContextMethod = StoryContextSteps.class.getMethod("resetStoryContext");

        //When
        final Annotation[] annotations = resetStoryContextMethod.getDeclaredAnnotations();

        //Then
        assertThat(annotations, arrayContaining(instanceOf(AfterStory.class)));
    }
}

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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static storycontext.ThreadLocalStoryContext.storyContext;
import static storycontext.StoryContextKey.storyContextKey;

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby
 */
public class ThreadLocalStoryContextTest {

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
        assertThat(storyContextString, startsWith("ThreadLocalStoryContext{typedMap={"));
        assertThat(storyContextString, endsWith("}}"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing an Integer' } = '1'"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing another String' } = 'Another stored String'"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing a String' } = 'A stored String'"));
    }

    @Test
    public void shouldHoldDifferentValuesForDifferentThreads() throws InterruptedException {
        //Given
        final StoryContext storyContext = ThreadLocalStoryContext.storyContext();
        int concurrentThreadCount = 15;
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentThreadCount);
        try {

            List<TestCallable> work = new ArrayList<TestCallable>(concurrentThreadCount);
            final CountDownLatch startLatch = new CountDownLatch(1);
            final CountDownLatch storeLatch = new CountDownLatch(concurrentThreadCount);
            final CountDownLatch retrieveLatch = new CountDownLatch(1);
            for (int i = 0; i < concurrentThreadCount; i++) {
                work.add(new TestCallable(startLatch,storeLatch, retrieveLatch, storyContext, i));
            }
            //When
            final List<Future<Integer>> futures = new ArrayList<Future<Integer>>(concurrentThreadCount);
            for(TestCallable callable : work){
                futures.add(executorService.submit(callable));
            }
            startLatch.countDown();
            storeLatch.await(10000l, TimeUnit.MILLISECONDS);
            retrieveLatch.countDown();

            //Then
            for (int i = 0; i < concurrentThreadCount; i++) {
                final Future<Integer> integerFuture = futures.get(i);
                try {
                    assertThat(integerFuture.get(10l, TimeUnit.MILLISECONDS), is(i));
                } catch (ExecutionException e) {
                    Assert.fail("Could not execute future for worker " + i);
                } catch (TimeoutException e) {
                    Assert.fail("Ran out of time to complete worker " + i);
                }
            }
        } finally {
            executorService.shutdown();
        }
    }

    private class TestCallable implements Callable<Integer> {

        final CountDownLatch startLatch;
        final CountDownLatch storeLatch;
        final CountDownLatch retrieveLatch;
        final StoryContext storyContext;
        final int setValue;

        private TestCallable(CountDownLatch startLatch, CountDownLatch storeLatch, CountDownLatch retrieveLatch, StoryContext storyContext, int setValue) {
            this.startLatch = startLatch;
            this.storeLatch = storeLatch;
            this.retrieveLatch = retrieveLatch;
            this.storyContext = storyContext;
            this.setValue = setValue;
        }

        @Override
        public Integer call() throws Exception {
            startLatch.await();
            storyContext.store(anIntegerKey, setValue);
            storeLatch.countDown();
            retrieveLatch.await();
            return storyContext.retrieve(anIntegerKey);
        }
    }
}

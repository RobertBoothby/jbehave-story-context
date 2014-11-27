package storycontext;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static storycontext.DefaultStoryContext.storyContext;
import static storycontext.StoryContextKey.storyContextKey;

/**
 * <p>&#169; 2014 Forest View Developments Ltd.</p>
 *
 * @author robertboothby
 */
public class DefaultStoryContextTest {

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
        assertThat(storyContextString, startsWith("DefaultStoryContext{typedMap={"));
        assertThat(storyContextString, endsWith("}}"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing an Integer' } = '1'"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing another String' } = 'Another stored String'"));
        assertThat(storyContextString, containsString("StoryContextKey{ 'Storing a String' } = 'A stored String'"));
    }
}

package storycontext;

import typedmap.TypedKey;
import typedmap.TypedMap;
import typedmap.TypedMapDecorator;

import java.util.Map;

/**
 * <p>Simplest implementationm of a StoryContext that works. This implementation is not thread safe in any way and values
 * stored by one thread will be visible to another thread.</p>
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public class DefaultStoryContext implements StoryContext {

    private TypedMap typedMap = TypedMapDecorator.typedMap();

    /**
     * Get a new instance of DefaultStoryContext.
     * @return a new instance of DefaultStoryContext.
     */
    public static DefaultStoryContext storyContext() {
        return new DefaultStoryContext();
    }

    @Override
    public <T> T store(StoryContextKey<T> storyContextKey, T value){
        return typedMap.putTyped(storyContextKey, value);
    }

    @Override
    public <T> T retrieve(StoryContextKey<T> storyContextKey){
        return typedMap.getTyped(storyContextKey);
    }

    @Override
    public void reset() {
        typedMap.clear();
    }

    @Override
    public String toString() {
        return new StringBuilder(DefaultStoryContext.class.getSimpleName())
                        .append("{typedMap={ \n").append(contentsAsString()).append("\n}}").toString();
    }

    /**
     * Generate a simple textual representation of the contents of the contents of the DefaultStoryContext.
     * @return a simple textual representation of the contents.
     */
    protected String contentsAsString() {
        final StringBuilder sb = new StringBuilder();
        for(Map.Entry<TypedKey<?>, Object> entry : typedMap.entrySet()){
            sb.append(entry.getKey().toString()).append(" = '").append(entry.getValue()).append("'\n");
        }
        return sb.toString();
    }
}

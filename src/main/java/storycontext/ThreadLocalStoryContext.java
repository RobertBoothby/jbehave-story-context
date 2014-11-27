package storycontext;

import typedmap.TypedKey;
import typedmap.TypedMap;
import typedmap.TypedMapDecorator;

import java.util.Map;

/**
 * <p>&#169; 2014 Forest View Developments Ltd.</p>
 *
 * @author robertboothby
 */
public class ThreadLocalStoryContext implements StoryContext {

    private ThreadLocal<TypedMap> typedMapThreadLocal = new ThreadLocal<TypedMap>(){
        @Override
        protected TypedMap initialValue() {
            return TypedMapDecorator.typedMap();
        }
    };

    public static DefaultStoryContext storyContext() {
        return new DefaultStoryContext();
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

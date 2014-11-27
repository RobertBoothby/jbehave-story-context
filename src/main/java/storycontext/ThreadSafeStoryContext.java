package storycontext;

import static storycontext.DefaultStoryContext.storyContext;

/**
 * <p>&#169; 2014 Forest View Developments Ltd.</p>
 *
 * @author robertboothby
 */
public class ThreadSafeStoryContext implements StoryContext {

    private final DefaultStoryContext wrappedContext = storyContext();
    private final Object mutex = new Object();

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
        return new StringBuilder(ThreadSafeStoryContext.class.getSimpleName())
                .append("{typedMap={ \n").append(wrappedContext.contentsAsString()).append("\n}}").toString();
    }
}

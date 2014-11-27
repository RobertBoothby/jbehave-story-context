package storycontext;

import org.jbehave.core.annotations.AfterStory;

/**
 * <p>&#169; 2014 Forest View Developments Ltd.</p>
 *
 * @author robertboothby
 */
public class StoryContextSteps {

    private final DefaultStoryContext storyContext;

    public StoryContextSteps(DefaultStoryContext storyContext) {
        this.storyContext = storyContext;
    }

    @AfterStory
    public void resetContext(){
        storyContext.reset();
    }
}

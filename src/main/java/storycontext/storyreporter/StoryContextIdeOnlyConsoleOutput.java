package storycontext.storyreporter;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.reporters.IdeOnlyConsoleOutput;
import storycontext.StoryContext;

/**
* <p>&#169; 2014 Forest View Developments Ltd.</p>
*
* @author robertboothby
*/
public class StoryContextIdeOnlyConsoleOutput extends IdeOnlyConsoleOutput {

    protected String storyContextKeyword;
    protected StoryContext storyContext;

    public StoryContextIdeOnlyConsoleOutput(Keywords keywords, String storyContextKeyword, StoryContext storyContext) {
        super(keywords);
        this.storyContextKeyword = storyContextKeyword;
        this.storyContext = storyContext;
    }

    @Override
    public void failed(String step, Throwable cause) {
        super.failed(step, cause);
        print(format("storyContext", "{0} ({1})\n({2})\n", step, storyContextKeyword, storyContext));
    }
}

package storycontext.storyreporter;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.XmlOutput;
import storycontext.StoryContext;

/**
* <p>&#169; 2014 Forest View Developments Ltd.</p>
*
* @author robertboothby
*/
class StoryContextXmlOutput extends XmlOutput {

    protected String storyContextKeyword;
    protected StoryContext storyContext;

    public StoryContextXmlOutput(
            FilePrintStreamFactory factory, Keywords keywords, String storyContextKeyword, StoryContext storyContext) {
        super(factory.createPrintStream(), keywords);
        this.storyContextKeyword = storyContextKeyword;
        this.storyContext = storyContext;
    }

    @Override
    public void failed(String step, Throwable cause) {
        super.failed(step, cause);
        print(format("storyContext", "{0} ({1})\n({2})\n", step, storyContextKeyword, storyContext));
    }
}

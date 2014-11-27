package storycontext;

import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.HtmlOutput;
import org.jbehave.core.reporters.PostStoryStatisticsCollector;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.reporters.XmlOutput;

/**
 * <p>&#169; 2014 Forest View Developments Ltd.</p>
 *
 * @author robertboothby
 */
public class ContextualStoryReporterBuilder extends StoryReporterBuilder {

    private StoryContext storyContext;
    private String storyContextKeyword = "StoryContext";

    public ContextualStoryReporterBuilder storyContext(StoryContext storyContext) {
        this.storyContext = storyContext;
        return this;
    }

    public ContextualStoryReporterBuilder storyContextKeyword(String storyContextKeyword) {
        this.storyContextKeyword = storyContextKeyword;
        return this;
    }

    @Override
    public StoryReporter reporterFor(String storyPath, Format format) {
        FilePrintStreamFactory factory = filePrintStreamFactory(storyPath);
        switch (format) {
            case CONSOLE:
            default:
                return new ConsoleOutput(keywords()) {
                    @Override
                    public void failed(String step, Throwable cause) {
                        super.failed(step, cause);
                        print(format("storyContext", "{0} ({1})\n({2})\n", step, storyContextKeyword, storyContext));
                    }
                }.doReportFailureTrace(reportFailureTrace());
            case TXT:
                factory.useConfiguration(fileConfiguration("txt"));
                return new TxtOutput(factory.createPrintStream(), keywords()) {
                    @Override
                    public void failed(String step, Throwable cause) {
                        super.failed(step, cause);
                        print(format("storyContext", "{0} ({1})\n({2})\n", step, storyContextKeyword, storyContext));
                    }
                }.doReportFailureTrace(reportFailureTrace());
            case HTML:
                factory.useConfiguration(fileConfiguration("html"));
                return new HtmlOutput(factory.createPrintStream(), keywords()) {
                    @Override
                    public void failed(String step, Throwable cause) {
                        super.failed(step, cause);
                        print(format("storyContext", "{0} ({1})\n({2})\n", step, storyContextKeyword, storyContext));
                    }
                }.doReportFailureTrace(reportFailureTrace());
            case XML:
                factory.useConfiguration(fileConfiguration("xml"));
                return new XmlOutput(factory.createPrintStream(), keywords()) {
                    @Override
                    public void failed(String step, Throwable cause) {
                        super.failed(step, cause);
                        print(format("storyContext", "{0} ({1})\n({2})\n", step, storyContextKeyword, storyContext));
                    }
                }.doReportFailureTrace(reportFailureTrace());
            case STATS:
                factory.useConfiguration(fileConfiguration("stats"));
                return new PostStoryStatisticsCollector(factory.createPrintStream());
        }
    }
}

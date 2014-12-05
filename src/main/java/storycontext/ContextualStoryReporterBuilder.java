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

import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.HtmlOutput;
import org.jbehave.core.reporters.PostStoryStatisticsCollector;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.reporters.XmlOutput;

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby
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

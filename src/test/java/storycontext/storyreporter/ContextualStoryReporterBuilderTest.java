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
package storycontext.storyreporter;

import org.jbehave.core.reporters.PostStoryStatisticsCollector;
import org.jbehave.core.reporters.StoryReporter;
import org.junit.Test;
import storycontext.StoryContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.CONSOLE;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.HTML;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.IDE_CONSOLE;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.TXT;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.XML;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.STATS;
import static storycontext.DefaultStoryContext.storyContext;

/**
 * <p>&#169; 2014 Forest View Developments Ltd.</p>
 *
 * @author robertboothby
 */
public class ContextualStoryReporterBuilderTest {
    @Test
    public void shouldCorrectlyCreateStoryContextConsoleOutputForConsoleOutput() {
        //Given
        ContextualStoryReporterBuilder builder = new ContextualStoryReporterBuilder();
        StoryContext storyContext = storyContext();
        builder.storyContext(storyContext);

        //When
        final StoryReporter storyReporter = builder.reporterFor("dummy", CONSOLE);

        //Then
        assertThat(storyReporter, is(instanceOf(StoryContextConsoleOutput.class)));
        StoryContextConsoleOutput storyContextConsoleOutput = (StoryContextConsoleOutput) storyReporter;
        assertThat(storyContextConsoleOutput.storyContext, is(storyContext));
    }

    @Test
    public void shouldCorrectlyCreateStoryContextConsoleOutputForHtmlOutput() {
        //Given
        ContextualStoryReporterBuilder builder = new ContextualStoryReporterBuilder();
        StoryContext storyContext = storyContext();
        builder.storyContext(storyContext);

        //When
        final StoryReporter storyReporter = builder.reporterFor("dummy", HTML);

        //Then
        assertThat(storyReporter, is(instanceOf(StoryContextHtmlOutput.class)));
        StoryContextHtmlOutput storyContextHtmlOutput = (StoryContextHtmlOutput) storyReporter;
        assertThat(storyContextHtmlOutput.storyContext, is(storyContext));
    }

    @Test
    public void shouldCorrectlyCreateStoryContextConsoleOutputForIdeConsoleOutput() {
        //Given
        ContextualStoryReporterBuilder builder = new ContextualStoryReporterBuilder();
        StoryContext storyContext = storyContext();
        builder.storyContext(storyContext);

        //When
        final StoryReporter storyReporter = builder.reporterFor("dummy", IDE_CONSOLE);

        //Then
        assertThat(storyReporter, is(instanceOf(StoryContextIdeOnlyConsoleOutput.class)));
        StoryContextIdeOnlyConsoleOutput storyContextIdeOnlyConsoleOutput = (StoryContextIdeOnlyConsoleOutput) storyReporter;
        assertThat(storyContextIdeOnlyConsoleOutput.storyContext, is(storyContext));
    }

    @Test
    public void shouldCorrectlyCreateStoryContextConsoleOutputForTxtOutput() {
        //Given
        ContextualStoryReporterBuilder builder = new ContextualStoryReporterBuilder();
        StoryContext storyContext = storyContext();
        builder.storyContext(storyContext);

        //When
        final StoryReporter storyReporter = builder.reporterFor("dummy", TXT);

        //Then
        assertThat(storyReporter, is(instanceOf(StoryContextTxtOutput.class)));
        StoryContextTxtOutput storyContextTxtOutput = (StoryContextTxtOutput) storyReporter;
        assertThat(storyContextTxtOutput.storyContext, is(storyContext));
    }

    @Test
    public void shouldCorrectlyCreateStoryContextConsoleOutputForXmlOutput() {
        //Given
        ContextualStoryReporterBuilder builder = new ContextualStoryReporterBuilder();
        StoryContext storyContext = storyContext();
        builder.storyContext(storyContext);

        //When
        final StoryReporter storyReporter = builder.reporterFor("dummy", XML);

        //Then
        assertThat(storyReporter, is(instanceOf(StoryContextXmlOutput.class)));
        StoryContextXmlOutput storyContextXmlOutput = (StoryContextXmlOutput) storyReporter;
        assertThat(storyContextXmlOutput.storyContext, is(storyContext));
    }

    @Test
    public void shouldCorrectlyCreateStoryContextConsoleOutputForStatsOutput() {
        //Given
        ContextualStoryReporterBuilder builder = new ContextualStoryReporterBuilder();
        StoryContext storyContext = storyContext();
        builder.storyContext(storyContext);

        //When
        final StoryReporter storyReporter = builder.reporterFor("dummy", STATS);

        //Then
        assertThat(storyReporter, is(instanceOf(PostStoryStatisticsCollector.class)));
    }

}

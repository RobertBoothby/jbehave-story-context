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
package storycontext.bdd;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import storycontext.StoryContext;

import static storycontext.DefaultStoryContext.storyContext;

/**
 * <p>&#169; 2015 Robert Boothby.</p>
 *
 * @author robertboothby
 */
public class BaseBddStory extends JUnitStory {

    private final StoryReporter storyReporter;
    private final StoryContext storyContext = storyContext();

    public BaseBddStory(StoryReporter storyReporter) {
        this.storyReporter = storyReporter;
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                        .useDefaultStoryReporter(storyReporter);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(
                    configuration(),
                    new PersonalLibrarySteps(storyContext),
                    new BookSteps(storyContext));
    }

}

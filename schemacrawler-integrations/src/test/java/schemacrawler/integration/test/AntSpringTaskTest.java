/*
 * SchemaCrawler
 * Copyright (c) 2000-2014, Sualeh Fatehi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package schemacrawler.integration.test;


import static schemacrawler.test.utility.TestUtility.compareOutput;
import static schemacrawler.test.utility.TestUtility.copyResourceToTempFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.tools.ant.BuildFileTest;
import org.apache.tools.ant.Project;
import org.junit.Test;

import schemacrawler.test.utility.TestDatabase;
import schemacrawler.tools.options.TextOutputFormat;

public class AntSpringTaskTest
  extends BuildFileTest
{

  private static final String ANT_TEST_OUTPUT = "ant_test_output/";

  @Override
  public void setUp()
    throws Exception
  {
    final Path buildFile = copyResourceToTempFile("/build.xml");
    configureProject(buildFile.toString());

    TestDatabase.initialize();
  }

  @Test
  public void testAntTask()
    throws Exception
  {
    final String referenceFile = "ant_task_test.txt";
    final Path testOutputFile = Paths.get("scOutput.txt");

    final Path contextFile = copyResourceToTempFile("/context.xml");

    setAntProjectProperty("context-file", contextFile.toString());
    setAntProjectProperty("datasource", "datasource");
    setAntProjectProperty("executable", "executableForSchema");
    executeTarget("ant_task_test");

    // System.out.println(getFullLog());
    // System.out.println(getOutput());

    final List<String> failures = compareOutput(ANT_TEST_OUTPUT + referenceFile,
                                                testOutputFile,
                                                TextOutputFormat.text
                                                  .getFormat());
    if (failures.size() > 0)
    {
      fail(failures.toString());
    }

  }

  private void setAntProjectProperty(final String name, final String value)
  {
    final Project antProject = getProject();
    antProject.setProperty(name, value);
  }

}

/*
 * Sonar Web Plugin
 * Copyright (C) 2010 Matthijs Galesloot
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.web.checks.dependencies;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.web.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.web.checks.sonar.TestHelper;
import org.sonar.plugins.web.visitor.WebSourceCode;

import java.io.File;
import java.io.FileNotFoundException;

public class LibraryDependencyCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void illegal_fully_qualified_identifier() {
    LibraryDependencyCheck check = new LibraryDependencyCheck();
    check.libraries = new String[] {"java.sql"};

    WebSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/LibraryDependencyCheck/IllegalFullyQualifiedIdentifier.jsp"), check);

    checkMessagesVerifier.verify(sourceCode.getViolations())
        .next().atLine(1).withMessage("Using 'java.sql' library is not allowed.");
  }

  @Test
  public void illegal_import() throws FileNotFoundException {
    LibraryDependencyCheck check = new LibraryDependencyCheck();
    check.libraries = new String[] {"java.sql"};

    WebSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/LibraryDependencyCheck/IllegalImport.jsp"), check);

    checkMessagesVerifier.verify(sourceCode.getViolations())
        .next().atLine(1).withMessage("Using 'java.sql' library is not allowed.");
  }

  @Test
  public void legal_fully_qualified_identifier_and_import() throws FileNotFoundException {
    LibraryDependencyCheck check = new LibraryDependencyCheck();
    check.libraries = new String[] {"java.sql"};

    WebSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/LibraryDependencyCheck/LegalFullyQualifiedIdentifierAndImport.jsp"), check);

    checkMessagesVerifier.verify(sourceCode.getViolations());
  }

}

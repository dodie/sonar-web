/*
 * SonarWeb :: SonarQube Plugin
 * Copyright (c) 2010-2018 SonarSource SA and Matthijs Galesloot
 * sonarqube@googlegroups.com
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
package org.sonar.plugins.web.checks.scripting;

import org.sonar.check.Rule;
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.node.TagNode;

@Rule(key = "NestedJavaScriptCheck")
public class NestedJavaScriptCheck extends AbstractPageCheck {

  private boolean insideScriptElement;
  
  @Override
  public void startElement(TagNode element) {
    if (element.equalsElementName("script")) {
      insideScriptElement = true;
    }
  }
  
  @Override
  public void endElement(TagNode element) {
    if (element.equalsElementName("script")) {
      if (!insideScriptElement) {
        createViolation(element.getEndLinePosition(),
            "A </script> was found without an opening tag. This may be caused by nested script tags." +
            "Avoid nested script tags as they are not supported by the browsers.");
      }
      insideScriptElement = false;
    }
  }

}
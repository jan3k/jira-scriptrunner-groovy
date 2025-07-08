/*
 * Copyright (c) 2025 Jan Augustyniak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def locationField = customFieldManager.getCustomFieldObjectByName("Location")
def location = issue.getCustomFieldValue(locationField)?.toString()

def userManager = ComponentAccessor.getUserManager()
def assignee

switch (location) {
    case "Warszawa":
        assignee = userManager.getUserByName("warsaw_support")
        break
    case "Kraków":
        assignee = userManager.getUserByName("krakow_support")
        break
    default:
        assignee = userManager.getUserByName("default_support")
}

issue.setAssignee(assignee)
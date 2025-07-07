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
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.user.ApplicationUser

def issue = issue as MutableIssue
def userManager = ComponentAccessor.getUserManager()

// Assign issue to specific user if priority is "High"
if (issue.getPriority().getName() == "High") {
    def assignee = userManager.getUserByName("admin")
    issue.setAssignee(assignee)
    issue.store()
}
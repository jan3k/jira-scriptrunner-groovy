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
import com.atlassian.jira.event.issue.IssueEvent
import com.atlassian.jira.event.type.EventType

def event = event as IssueEvent
def issue = event.issue

// React only to issue update events
if (event.eventTypeId == EventType.ISSUE_UPDATED_ID) {
    def customFieldManager = ComponentAccessor.customFieldManager
    def statusField = customFieldManager.getCustomFieldObjectByName("Status History")
    
    if (statusField) {
        def currentHistory = issue.getCustomFieldValue(statusField) ?: ""
        def newEntry = "${new Date().format('yyyy-MM-dd HH:mm:ss')}: ${issue.status.name}"
        def updatedHistory = currentHistory ? "${currentHistory}\n${newEntry}" : newEntry
        
        issue.setCustomFieldValue(statusField, updatedHistory)
        ComponentAccessor.issueManager.updateIssue(event.user, issue, EventDispatchOption.DO_NOT_DISPATCH, false)
    }
}
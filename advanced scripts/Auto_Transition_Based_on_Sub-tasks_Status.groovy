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
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.workflow.WorkflowTransitionUtil

Issue issue = issue // Current issue context

// Check if all sub-tasks are completed
if (issue.subTaskObjects.every { it.status.name == "Done" }) {
    def transitionId = 31 // ID of "Done" transition
    def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser
    def workflowManager = ComponentAccessor.workflowManager
    
    def transitionValidationResult = workflowManager.validateTransition(issue, transitionId, issue.assigneeId, [:])
    
    if (transitionValidationResult.isValid()) {
        workflowManager.progressWorkflowAction(issue, transitionValidationResult)
        log.info("Issue ${issue.key} has been automatically moved to Done status")
    }
}
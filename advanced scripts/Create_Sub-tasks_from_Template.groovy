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
import com.atlassian.jira.issue.IssueInputParametersImpl
import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.issue.MutableIssue

def issueService = ComponentAccessor.issueService
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def parentIssue = issue

// Sub-task templates
def subTaskTemplates = [
    [summary: "Requirements Analysis", description: "Perform requirements analysis for the task"],
    [summary: "Implementation", description: "Implement the solution"],
    [summary: "Testing", description: "Test the implementation"],
    [summary: "Documentation", description: "Prepare documentation"]
]

subTaskTemplates.each { template ->
    def issueInputParameters = new IssueInputParametersImpl()
    issueInputParameters.setProjectId(parentIssue.projectObject.id)
    issueInputParameters.setIssueTypeId("5") // "Sub-task" type ID
    issueInputParameters.setSummary(template.summary)
    issueInputParameters.setDescription(template.description)
    issueInputParameters.setAssigneeId(parentIssue.assigneeId)
    issueInputParameters.setReporterId(user.key)
    
    def createValidationResult = issueService.validateCreate(user, issueInputParameters)
    
    if (createValidationResult.isValid()) {
        def createResult = issueService.create(user, createValidationResult)
        if (createResult.isValid()) {
            def newSubTask = createResult.issue
            ComponentAccessor.subTaskManager.createSubTaskIssueLink(parentIssue, newSubTask, user)
            log.info("Created sub-task: ${newSubTask.key}")
        }
    }
}
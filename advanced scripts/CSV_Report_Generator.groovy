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
import com.atlassian.jira.issue.attachment.CreateAttachmentParamsBean
import java.io.File

def issue = issue
def attachmentManager = ComponentAccessor.attachmentManager
def issueManager = ComponentAccessor.issueManager
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser

// Get all sub-tasks
def subTasks = issue.subTaskObjects

// Generate CSV content
def csvContent = new StringBuilder()
csvContent.append("Key,Summary,Status,Assignee,Created,Updated\n")

subTasks.each { subTask ->
    csvContent.append("${subTask.key},")
    csvContent.append("\"${subTask.summary}\",")
    csvContent.append("${subTask.status.name},")
    csvContent.append("${subTask.assignee?.displayName ?: 'Unassigned'},")
    csvContent.append("${subTask.created.format('yyyy-MM-dd HH:mm:ss')},")
    csvContent.append("${subTask.updated.format('yyyy-MM-dd HH:mm:ss')}\n")
}

// Create temporary file
def tempFile = File.createTempFile("subtasks-report-${issue.key}", ".csv")
tempFile.write(csvContent.toString(), "UTF-8")

try {
    // Attach file to issue
    def attachmentParams = new CreateAttachmentParamsBean.Builder()
        .file(tempFile)
        .filename("subtasks-report-${issue.key}.csv")
        .contentType("text/csv")
        .author(user)
        .issue(issue)
        .build()
    
    attachmentManager.createAttachment(attachmentParams)
    log.info("CSV report has been attached to issue ${issue.key}")
    
} finally {
    // Delete temporary file
    tempFile.delete()
}

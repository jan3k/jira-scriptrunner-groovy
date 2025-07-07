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
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.mail.Email
import java.time.LocalDate
import java.time.ZoneId

def searchProvider = ComponentAccessor.getComponent(SearchProvider)
def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
def mailServerManager = ComponentAccessor.mailServerManager
def user = ComponentAccessor.userManager.getUserByName("admin")

// Find issues with due date within 3 days
def tomorrow = LocalDate.now().plusDays(1)
def inThreeDays = LocalDate.now().plusDays(3)

def jql = "duedate >= '${tomorrow}' AND duedate <= '${inThreeDays}' AND status != Done"
def query = jqlQueryParser.parseQuery(jql)
def searchResults = searchProvider.search(query, user, PagerFilter.getUnlimitedFilter())

if (searchResults.issues.size() > 0) {
    def emailBody = new StringBuilder()
    emailBody.append("The following issues have due dates within the next 3 days:\n\n")
    
    searchResults.issues.each { issue ->
        emailBody.append("${issue.key}: ${issue.summary}\n")
        emailBody.append("Due date: ${issue.dueDate?.format('yyyy-MM-dd')}\n")
        emailBody.append("Assigned to: ${issue.assignee?.displayName ?: 'Unassigned'}\n\n")
    }
    
    def email = new Email("admin@company.com")
    email.setSubject("Due Date Reminder for Issues")
    email.setBody(emailBody.toString())
    
    def mailServer = mailServerManager.getDefaultSMTPMailServer()
    if (mailServer) {
        mailServer.send(email)
        log.info("Sent reminder about ${searchResults.issues.size()} issues")
    }
}
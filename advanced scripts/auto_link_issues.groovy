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
import com.atlassian.jira.issue.link.IssueLink
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter

def issue = issue
def issueLinkManager = ComponentAccessor.issueLinkManager
def searchProvider = ComponentAccessor.getComponent(SearchProvider)
def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser

// Find related issues based on labels
def labels = issue.labels
if (labels) {
    def labelQuery = "labels in (${labels.collect { "\"$it\"" }.join(', ')}) AND key != ${issue.key}"
    
    try {
        def query = jqlQueryParser.parseQuery(labelQuery)
        def searchResults = searchProvider.search(query, user, PagerFilter.getUnlimitedFilter())
        
        searchResults.issues.each { relatedIssue ->
            // Check if link already exists
            def existingLinks = issueLinkManager.getIssueLinks(issue.id)
            def linkExists = existingLinks.any { link ->
                (link.sourceObject.id == issue.id && link.destinationObject.id == relatedIssue.id) ||
                (link.sourceObject.id == relatedIssue.id && link.destinationObject.id == issue.id)
            }
            
            if (!linkExists) {
                // Create "relates to" link
                def linkType = ComponentAccessor.issueLinkTypeManager.getIssueLinkTypesByName("Relates").first()
                issueLinkManager.createIssueLink(issue.id, relatedIssue.id, linkType.id, 1L, user)
                log.info("Created link between ${issue.key} and ${relatedIssue.key}")
            }
        }
    } catch (Exception e) {
        log.error("Error during automatic linking: ${e.message}")
    }
}

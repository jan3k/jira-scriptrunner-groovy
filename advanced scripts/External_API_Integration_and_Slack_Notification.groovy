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

import groovyx.net.http.RESTClient
import groovy.json.JsonBuilder
import com.atlassian.jira.component.ComponentAccessor

def issue = issue
def slackWebhookUrl = "https://hooks.slack.com/services/YOUR/WEBHOOK/URL"

// Prepare data to send
def payload = [
    username: "JiraBot",
    channel: "#development",
    icon_emoji: ":jira:",
    text: "New issue has been created",
    attachments: [
        [
            color: "good",
            fields: [
                [
                    title: "Issue",
                    value: "${issue.key}: ${issue.summary}",
                    short: false
                ],
                [
                    title: "Assigned to",
                    value: issue.assignee?.displayName ?: "Unassigned",
                    short: true
                ],
                [
                    title: "Priority",
                    value: issue.priority?.name ?: "None",
                    short: true
                ]
            ]
        ]
    ]
]

try {
    def restClient = new RESTClient(slackWebhookUrl)
    def response = restClient.post(
        body: new JsonBuilder(payload).toString(),
        requestContentType: 'application/json'
    )
    
    if (response.status == 200) {
        log.info("Slack notification sent successfully for issue ${issue.key}")
    } else {
        log.error("Error sending Slack notification: ${response.status}")
    }
} catch (Exception e) {
    log.error("Slack connection error: ${e.message}")
}
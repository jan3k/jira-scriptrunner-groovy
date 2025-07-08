<<EOF
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
 *
EOF


#!/bin/bash

# Przykład webhooka uruchamianego po zakończeniu joba w GitLab
JIRA_ISSUE_KEY=$1
STATUS="In Review"

curl -u $JIRA_USER:$JIRA_TOKEN \
     -X POST \
     --data "{\"transition\":{\"id\":\"51\"}}" \
     -H "Content-Type: application/json" \
     "https://twojajira.atlassian.net/rest/api/3/issue/${JIRA_ISSUE_KEY}/transitions"
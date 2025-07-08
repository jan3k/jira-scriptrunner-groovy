# Jira ScriptRunner Groovy Scripts

A comprehensive collection of Groovy scripts for Atlassian Jira ScriptRunner, providing automation solutions for workflows, issue management, and system integration.

## 🚀 Features

- **Workflow Automation**: Validators, conditions, and post-functions for advanced workflow management
- **Issue Management**: Automatic assignment, cloning, and sub-task creation
- **External Integrations**: Slack notifications, database connections, and API integrations
- **Reporting**: CSV generation and automated report attachments
- **Business Rules**: Custom validation and field management
- **Scheduled Tasks**: Automated reminders and maintenance jobs

## 📁 Repository Structure

```
jira-scriptrunner-groovy/
├── simple-scripts/
│   ├── auto_assign_priority.groovy          # Auto assign based on priority
│   ├── mandatory_comment_validator.groovy   # Require comments on transitions
│   ├── set_due_date_custom_field.groovy     # Set due date from custom field
│   └── copy_field_values.groovy             # Copy values between fields
│   └── auto-assign-by-location.groovy       # Auto assign issue by location
│   └── jira-gitlab-integration.sh           # Integration between jira and gitlab in bash
│   └── notify-on-high-priority              # Notify based on priority (high)
├── advanced-scripts/
│   ├── auto_transition_subtasks.groovy      # Auto transition based on sub-tasks
│   ├── create_subtasks_template.groovy      # Create sub-tasks from templates
│   ├── slack_integration.groovy             # Slack webhook integration
│   ├── database_validation.groovy           # External database validation
│   ├── csv_report_generator.groovy          # Generate and attach CSV reports
│   └── auto_link_issues.groovy              # Auto link related issues
|   └── update-issue-status.py               # Update issue status via REST API
├── listeners/
│   └── status_history_listener.groovy       # Track status changes
├── scheduled-jobs/
│   └── due_date_reminders.groovy            # Send due date reminders
├── LICENSE
└── README.md
```

## 🛠️ Prerequisites

- Atlassian Jira (Cloud, Server, or Data Center)
- ScriptRunner for Jira addon
- Administrator access to Jira
- Basic understanding of Groovy scripting

## 📋 Script Categories

### Simple Scripts

#### Auto Assign Issue Based on Priority
**File:** `simple-scripts/auto-assign-priority.groovy`
**Usage:** Post-function in workflow transitions
**Description:** Automatically assigns issues with "High" priority to a specific user.

#### Mandatory Comment Validator
**File:** `simple-scripts/mandatory-comment-validator.groovy`
**Usage:** Validator in workflow transitions
**Description:** Ensures users add comments during specific transitions.

#### Set Due Date Based on Custom Field
**File:** `simple-scripts/set-due-date-custom-field.groovy`
**Usage:** Post-function in workflow transitions
**Description:** Sets the issue's due date based on a custom field value.

#### Copy Field Values
**File:** `simple-scripts/copy-field-values.groovy`
**Usage:** Post-function in workflow transitions
**Description:** Copies values between custom fields within the same issue.

### Advanced Scripts

#### Auto Transition Based on Sub-tasks Status
**File:** `advanced-scripts/auto-transition-subtasks.groovy`
**Usage:** Post-function or scheduled job
**Description:** Automatically moves parent issues to "Done" when all sub-tasks are completed.

#### Create Sub-tasks from Template
**File:** `advanced-scripts/create-subtasks-template.groovy`
**Usage:** Post-function in workflow transitions
**Description:** Creates predefined sub-tasks for issues based on templates.

#### Slack Integration
**File:** `advanced-scripts/slack-integration.groovy`
**Usage:** Post-function or listener
**Description:** Sends formatted notifications to Slack channels when issues are created or updated.

#### Database Validation
**File:** `advanced-scripts/database-validation.groovy`
**Usage:** Validator in workflow transitions
**Description:** Validates customer data against external database before allowing transitions.

#### CSV Report Generator
**File:** `advanced-scripts/csv-report-generator.groovy`
**Usage:** Post-function or scheduled job
**Description:** Generates CSV reports of sub-tasks and attaches them to parent issues.

#### Auto Link Issues
**File:** `advanced-scripts/auto-link-issues.groovy`
**Usage:** Post-function or listener
**Description:** Automatically links related issues based on shared labels.

### Listeners

#### Status History Listener
**File:** `listeners/status-history-listener.groovy`
**Usage:** Event listener
**Description:** Tracks and logs all status changes in a custom field.

### Scheduled Jobs

#### Due Date Reminders
**File:** `scheduled-jobs/due-date-reminders.groovy`
**Usage:** Scheduled job
**Description:** Sends email reminders for issues with upcoming due dates.

## 🔧 Installation and Usage

### 1. Install ScriptRunner
Ensure ScriptRunner for Jira is installed and configured in your Jira instance.

### 2. Access Script Console
Navigate to **Jira Administration** → **Add-ons** → **ScriptRunner** → **Script Console**

### 3. Configure Scripts

#### For Workflow Scripts:
1. Go to **Jira Administration** → **Issues** → **Workflows**
2. Edit the desired workflow
3. Add post-function, validator, or condition
4. Select **Custom script** and paste the script code

#### For Listeners:
1. Go to **ScriptRunner** → **Listeners**
2. Create new listener
3. Paste the script code and configure events

#### For Scheduled Jobs:
1. Go to **ScriptRunner** → **Scheduled Jobs**
2. Create new job
3. Paste the script code and set schedule

### 4. Customize Scripts
Before using, customize the following in each script:
- Field names and IDs
- User names and roles
- Email addresses
- Slack webhook URLs
- Database connection details

## ⚙️ Configuration Examples

### Slack Integration Setup
```
// Replace with your Slack webhook URL
def slackWebhookUrl = "https://hooks.slack.com/services/YOUR/WEBHOOK/URL"

// Configure channel and appearance
def payload = [
    username: "JiraBot",
    channel: "#your-channel",
    icon_emoji: ":jira:"
]
```

### Database Connection Setup
```
// Configure your database connection
def dbUrl = "jdbc:mysql://localhost:3306/your_database"
def dbUser = "your_username"
def dbPassword = "your_password"
```

## 🔍 Common Use Cases

### Retail Companies
- **Order Management**: Auto-assign support tickets based on order priority
- **Inventory Tracking**: Link issues to inventory systems via database validation
- **Customer Service**: Generate reports for customer service metrics

### Software Development
- **Release Management**: Auto-create sub-tasks for deployment processes
- **Bug Tracking**: Link related bugs based on components or labels
- **Team Notifications**: Send Slack notifications for critical issues

### IT Service Management
- **Incident Management**: Auto-escalate high-priority incidents
- **Change Management**: Validate changes against configuration databases
- **Asset Management**: Track status changes for IT assets

## 🚨 Important Notes

### Security Considerations
- Always validate user inputs in scripts
- Use secure connection methods for external integrations
- Limit script execution permissions to necessary users
- Regularly review and update authentication credentials

### Performance Tips
- Test scripts in development environment first
- Monitor script execution times
- Use JQL queries efficiently
- Consider ScriptRunner execution limits

### Troubleshooting
- Check ScriptRunner logs for error messages
- Verify field names and IDs in your Jira instance
- Ensure required permissions are granted
- Test database connections separately

## 🤝 Contributing

We welcome contributions to improve these scripts!

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-script`)
3. Commit your changes (`git commit -m 'Add amazing script'`)
4. Push to the branch (`git push origin feature/amazing-script`)
5. Open a Pull Request

### Contribution Guidelines
- Follow existing code style and commenting conventions
- Include clear descriptions of what your script does
- Add usage examples in comments
- Test scripts thoroughly before submitting
- Update this README if adding new categories

## 📝 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter issues or have questions:

1. Check the [ScriptRunner Documentation](https://scriptrunner.adaptavist.com/latest/)
2. Review Jira logs for error messages
3. Test scripts in a development environment
4. Open an issue in this repository with detailed information

## 🙏 Acknowledgments

- [Adaptavist ScriptRunner](https://scriptrunner.adaptavist.com/) team for the excellent tool
- [Apache Groovy](https://groovy-lang.org/) community for the language
- [Atlassian Community](https://community.atlassian.com/) for inspiration and best practices

## 📊 Script Statistics

- **Total Scripts**: 12
- **Simple Scripts**: 4
- **Advanced Scripts**: 6
- **Listeners**: 1
- **Scheduled Jobs**: 1
- **Supported Jira Versions**: 7.x, 8.x, 9.x
- **ScriptRunner Versions**: 6.x, 7.x, 8.x

---

**Note**: Always backup your Jira instance before implementing new scripts in production environments.

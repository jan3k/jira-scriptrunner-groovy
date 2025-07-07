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
import groovy.sql.Sql
import java.sql.DriverManager

def issue = issue
def customFieldManager = ComponentAccessor.customFieldManager
def customerIdField = customFieldManager.getCustomFieldObjectByName("Customer ID")
def customerId = issue.getCustomFieldValue(customerIdField)

if (!customerId) {
    invalidInputException = "Customer ID is required"
    return false
}

// Connection to external database
def dbUrl = "jdbc:mysql://localhost:3306/crm"
def dbUser = "jira_user"
def dbPassword = "password"

try {
    def connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)
    def sql = new Sql(connection)
    
    def rows = sql.rows("SELECT active FROM customers WHERE customer_id = ?", [customerId])
    
    if (rows.isEmpty()) {
        invalidInputException = "Customer with ID ${customerId} does not exist in CRM system"
        return false
    }
    
    if (!rows[0].active) {
        invalidInputException = "Customer with ID ${customerId} is inactive"
        return false
    }
    
    connection.close()
    log.info("Customer validation ${customerId} completed successfully")
    return true
    
} catch (Exception e) {
    log.error("Database connection error: ${e.message}")
    invalidInputException = "Cannot verify customer data"
    return false
}
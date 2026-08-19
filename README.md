

````markdown
# ⏳ Future Capsule

> **Messages beyond time.**

Future Capsule is a Java-based web application that allows users to write messages to their future selves and schedule them for delivery at a specific date and time.

Once the scheduled time arrives, the application's background scheduler automatically detects the capsule, sends the message to the user's registered email address through Gmail SMTP, and marks the capsule as delivered.

---

## 📌 Overview

Future Capsule is designed around a simple idea:

**Write something today. Receive it in the future.**

Users can:

- Create an account
- Log in securely
- Create time capsules
- Schedule capsules for a future date and time
- View all their capsules
- Open individual capsules
- Edit pending capsules
- Delete capsules
- Receive capsules automatically through email
- View delivered capsules
- Track whether a capsule is pending or delivered

The application uses a scheduled background process running with Apache Tomcat to continuously check for capsules whose delivery time has arrived.

---

## ✨ Features

### 👤 User Management

- User registration
- Duplicate email validation
- Secure password hashing
- User login
- Session-based authentication
- Logout functionality

### 📦 Time Capsules

- Create a capsule with:
  - Title
  - Message
  - Delivery date and time
- Future-date validation
- View all personal capsules
- View individual capsule details
- Edit pending capsules
- Delete capsules
- Delivered capsules become read-only

### ⏰ Automatic Delivery

The application includes a background scheduler that:

1. Checks the database for due capsules
2. Identifies capsules whose delivery time has arrived
3. Retrieves the corresponding user
4. Sends the capsule through email
5. Marks the capsule as delivered

### 📧 Email Delivery

Email delivery is implemented using:

- Jakarta Mail
- Eclipse Angus Mail
- Gmail SMTP
- SMTP authentication
- STARTTLS

The application uses environment variables for email credentials instead of storing credentials inside the source code.

### 🎨 User Interface

The application includes a dark, minimal interface designed around the concept of time and memory.

UI characteristics include:

- Dark theme
- Instrument Serif typography
- DM Sans
- DM Mono
- Orange accent color
- Responsive layouts
- Capsule cards
- Hover effects
- Delivery status indicators
- Responsive mobile styling

---

# 🏗️ Architecture

Future Capsule follows a layered Java web application architecture.

```text
                         USER
                          │
                          ▼
                  ┌───────────────┐
                  │ HTML / JSP UI │
                  └───────┬───────┘
                          │
                          ▼
                  ┌───────────────┐
                  │   Servlets    │
                  └───────┬───────┘
                          │
                          ▼
                  ┌───────────────┐
                  │   Services    │
                  └───────┬───────┘
                          │
                          ▼
                  ┌───────────────┐
                  │     DAO       │
                  └───────┬───────┘
                          │
                          ▼
                  ┌───────────────┐
                  │    MySQL      │
                  └───────────────┘


                    BACKGROUND FLOW

                  Apache Tomcat
                       │
                       ▼
                Scheduler Listener
                       │
                       ▼
               Delivery Scheduler
                       │
                       ▼
                 Due Capsules
                       │
                       ▼
                  EmailService
                       │
                       ▼
                   Gmail SMTP
                       │
                       ▼
                    📧 Email
````

---

# 🛠️ Technology Stack

| Technology             | Purpose                                |
| ---------------------- | -------------------------------------- |
| Java                   | Backend application logic              |
| Jakarta Servlet        | HTTP request handling                  |
| JSP                    | Dynamic web pages                      |
| HTML5                  | Frontend structure                     |
| CSS3                   | UI styling                             |
| MySQL                  | Persistent data storage                |
| JDBC                   | Database connectivity                  |
| Maven                  | Dependency and project management      |
| Apache Tomcat 10       | Servlet container / application server |
| Jakarta Mail           | Email API                              |
| Eclipse Angus Mail     | Jakarta Mail implementation            |
| Gmail SMTP             | Email delivery                         |
| Spring Security Crypto | Password hashing                       |
| Git                    | Version control                        |
| GitHub                 | Source code hosting                    |

---

# 📁 Project Structure

```text
future-capsule/
│
├── .gitignore
├── pom.xml
│
└── src/
    └── main/
        │
        ├── java/
        │   └── com/
        │       └── futurecapsule/
        │           │
        │           ├── dao/
        │           │   ├── TimeCapsuleDAO.java
        │           │   ├── TimeCapsuleDAOTest.java
        │           │   ├── UserDAO.java
        │           │   └── UserDAOTest.java
        │           │
        │           ├── listener/
        │           │   └── SchedulerListener.java
        │           │
        │           ├── model/
        │           │   ├── TimeCapsule.java
        │           │   ├── TimeCapsuleTest.java
        │           │   └── User.java
        │           │
        │           ├── scheduler/
        │           │   ├── CapsuleDeliveryScheduler.java
        │           │   ├── DeliveryScheduler.java
        │           │   └── DeliverySchedulerTest.java
        │           │
        │           ├── service/
        │           │   ├── AuthenticationService.java
        │           │   ├── AuthenticationTest.java
        │           │   ├── EmailService.java
        │           │   ├── RegistrationService.java
        │           │   ├── TimeCapsuleService.java
        │           │   └── TimeCapsuleServiceTest.java
        │           │
        │           ├── servlet/
        │           │   ├── CapsuleServlet.java
        │           │   ├── DashboardServlet.java
        │           │   ├── DeleteCapsuleServlet.java
        │           │   ├── EditCapsuleServlet.java
        │           │   ├── HelloServlet.java
        │           │   ├── LoginServlet.java
        │           │   ├── LogoutServlet.java
        │           │   ├── MyCapsulesServlet.java
        │           │   ├── RegisterServlet.java
        │           │   └── ViewCapsuleServlet.java
        │           │
        │           └── util/
        │               ├── ConnectionTest.java
        │               ├── DBConnection.java
        │               ├── PasswordTest.java
        │               ├── PasswordUtil.java
        │               └── WebDBTest.java
        │
        └── webapp/
            │
            ├── WEB-INF/
            │   └── views/
            │       ├── edit-capsule.jsp
            │       ├── my-capsules.jsp
            │       └── view-capsule.jsp
            │
            ├── css/
            │   └── style.css
            │
            ├── create-capsule.html
            ├── login.html
            └── register.html
```

---

# 🧩 Package Responsibilities

## `model`

Contains Java classes representing application data.

### `User.java`

Represents a registered user.

Stores:

* User ID
* Name
* Email
* Password

### `TimeCapsule.java`

Represents a time capsule.

A capsule contains information such as:

* Capsule ID
* User ID
* Title
* Message
* Creation time
* Delivery time
* Delivery status

---

# 🗄️ DAO Layer

The DAO layer handles database communication.

## `UserDAO.java`

Responsible for database operations related to users.

Main responsibilities:

* Find users by email
* Create new users
* Execute SQL queries using JDBC
* Handle database results

---

## `TimeCapsuleDAO.java`

Responsible for database operations related to capsules.

Responsibilities include:

* Creating capsules
* Finding capsules
* Finding capsules belonging to a user
* Updating capsules
* Deleting capsules
* Finding capsules that are due for delivery
* Updating delivery status

Prepared statements are used for database queries.

---

# ⚙️ Service Layer

The service layer contains application/business logic.

## `AuthenticationService.java`

Responsible for authenticating users.

Basic flow:

```text
Login request
     ↓
Find user by email
     ↓
Retrieve stored password hash
     ↓
Verify password
     ↓
Return authenticated User
```

---

## `RegistrationService.java`

Handles new user registration.

Before storing a password, the application hashes it using `PasswordUtil`.

```text
Plain password
      ↓
PasswordUtil
      ↓
Hashed password
      ↓
UserDAO
      ↓
MySQL
```

---

## `TimeCapsuleService.java`

Contains business rules for capsules.

For example, delivery dates must be in the future.

```java
if (!deliveryDate.isAfter(LocalDateTime.now())) {
    throw new IllegalArgumentException(
        "Delivery date must be in the future."
    );
}
```

It also provides operations for:

* Creating capsules
* Retrieving capsules
* Updating capsules
* Deleting capsules

---

## `EmailService.java`

Responsible for sending capsule emails.

The service:

1. Loads email credentials from environment variables
2. Configures Gmail SMTP
3. Creates the email
4. Adds recipient information
5. Adds capsule details
6. Sends the email

SMTP configuration:

```text
Host: smtp.gmail.com
Port: 587
Authentication: enabled
STARTTLS: enabled
```

Credentials are intentionally not stored in the source code.

---

# ⏰ Scheduler

The scheduler is one of the most important parts of Future Capsule.

## `SchedulerListener.java`

Starts the delivery scheduler when the web application starts and stops it when the application shuts down.

```text
Tomcat starts
     ↓
Web application starts
     ↓
SchedulerListener
     ↓
Delivery Scheduler starts
```

---

## `DeliveryScheduler.java`

Responsible for periodically checking for capsules that are ready for delivery.

Conceptually:

```text
Every scheduler cycle
        ↓
Find due capsules
        ↓
Are there capsules?
   ┌────┴────┐
   │         │
  No        Yes
   │         │
Wait      Process
again     capsules
```

---

## `CapsuleDeliveryScheduler.java`

Contains scheduler-related delivery functionality used by the application.

The scheduler works together with the DAO and email service to complete the delivery process.

---

# 📧 Automatic Email Delivery

The complete delivery pipeline is:

```text
Capsule Created
      │
      ▼
MySQL
      │
      │ status = PENDING
      ▼
Delivery Scheduler
      │
      │ delivery time reached
      ▼
TimeCapsuleDAO
      │
      ▼
Due Capsule
      │
      ▼
EmailService
      │
      ▼
Gmail SMTP
      │
      ▼
Recipient Email
      │
      ▼
Capsule Marked DELIVERED
```

---

# 🔐 Security

Future Capsule includes several security practices.

## Password Hashing

Passwords are not intended to be stored as plain text.

`PasswordUtil` is used to hash and verify passwords.

---

## Prepared Statements

Database queries use `PreparedStatement` instead of directly concatenating user input.

This helps reduce SQL injection risks.

---

## Session Authentication

Authenticated users are stored in the HTTP session.

Protected pages check whether a valid user session exists before allowing access.

---

## Capsule Ownership

A user can only access capsules belonging to their own account.

The application checks the capsule's `userId` against the logged-in user's ID.

---

## Environment Variables

Sensitive email credentials are loaded using:

```text
FUTURE_CAPSULE_EMAIL
FUTURE_CAPSULE_EMAIL_PASSWORD
```

Credentials should never be committed to GitHub.

---

# 🌐 Servlet Responsibilities

| Servlet                | Responsibility                 |
| ---------------------- | ------------------------------ |
| `LoginServlet`         | Handles login                  |
| `RegisterServlet`      | Handles registration           |
| `LogoutServlet`        | Logs users out                 |
| `DashboardServlet`     | Displays dashboard             |
| `CapsuleServlet`       | Creates capsules               |
| `MyCapsulesServlet`    | Displays user's capsules       |
| `ViewCapsuleServlet`   | Displays an individual capsule |
| `EditCapsuleServlet`   | Updates a capsule              |
| `DeleteCapsuleServlet` | Deletes a capsule              |
| `HelloServlet`         | Basic servlet/test endpoint    |

---

# 🎨 Frontend

The frontend is built using HTML, JSP and CSS.

## Main Pages

### Login

```text
login.html
```

Allows users to authenticate.

### Registration

```text
register.html
```

Allows new users to create an account.

### Create Capsule

```text
create-capsule.html
```

Allows users to create and schedule a new capsule.

### My Capsules

```text
my-capsules.jsp
```

Displays capsules belonging to the logged-in user.

### Edit Capsule

```text
edit-capsule.jsp
```

Allows modification of pending capsules.

### View Capsule

```text
view-capsule.jsp
```

Displays the capsule message and its delivery status.

---

# 🧪 Testing

The project contains several test/helper classes for validating different components.

Examples include:

```text
TimeCapsuleDAOTest
UserDAOTest
TimeCapsuleServiceTest
AuthenticationTest
PasswordTest
ConnectionTest
WebDBTest
DeliverySchedulerTest
```

---

# ✅ End-to-End Test

The complete automatic delivery functionality was tested successfully.

Test flow:

```text
1. Create capsule
        ↓
2. Set future delivery time
        ↓
3. Store capsule in MySQL
        ↓
4. Start Apache Tomcat
        ↓
5. Scheduler starts automatically
        ↓
6. Scheduler waits for delivery time
        ↓
7. Scheduler detects due capsule
        ↓
8. EmailService sends email
        ↓
9. Email received successfully
        ↓
10. Capsule marked as delivered
```

The Gmail SMTP delivery was successfully tested with a real email recipient.

---

# 🚀 Running the Project Locally

## Requirements

Install:

* Java JDK
* Apache Maven
* MySQL
* Apache Tomcat 10
* Git

---

## 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/future-capsule.git
```

```bash
cd future-capsule
```

---

## 2. Configure MySQL

Create the required database and tables used by the application.

Update the database configuration in `DBConnection.java` according to your local MySQL setup.

**Do not commit database passwords to GitHub.**

---

## 3. Configure Email

Set the following environment variables:

```text
FUTURE_CAPSULE_EMAIL=your-email@gmail.com
FUTURE_CAPSULE_EMAIL_PASSWORD=your-app-password
```

For Gmail, use a Google App Password rather than your normal Gmail password.

---

## 4. Build the project

Using Maven:

```bash
mvn clean package
```

This generates the deployable WAR file under:

```text
target/
```

---

## 5. Deploy to Tomcat

Copy the generated WAR file into:

```text
apache-tomcat/webapps/
```

Start Tomcat.

On Windows:

```bat
catalina.bat run
```

---

## 6. Open the application

Open:

```text
http://localhost:8080/future-capsule-1.0-SNAPSHOT/
```

---

# 🔄 Application Lifecycle

When Tomcat starts:

```text
Tomcat
   ↓
Deploy Future Capsule
   ↓
SchedulerListener.contextInitialized()
   ↓
Delivery Scheduler starts
   ↓
Application ready
```

When Tomcat stops:

```text
Application shutdown
       ↓
SchedulerListener.contextDestroyed()
       ↓
Scheduler stopped
       ↓
Application shutdown
```

This prevents the scheduler from continuing to run after the web application has been stopped.

---

# 📦 Deployment

The application is designed to run as a Java web application using Apache Tomcat.

For production deployment, the following services are required:

```text
Java Application
      │
      ├── Apache Tomcat
      │
      ├── MySQL Database
      │
      └── SMTP Email Service
```

Sensitive configuration should be provided through environment variables.

---

# 🔒 Important GitHub Security Rule

Never commit:

```text
.env
setenv.bat
database passwords
Gmail App Passwords
API keys
private credentials
```

The repository `.gitignore` excludes local environment and build files.

---

# 🛣️ Future Improvements

Possible future improvements include:

* Email templates with responsive HTML
* Rich text capsule messages
* Image attachments
* Multiple recipients
* Capsule categories
* Search and filtering
* Capsule reminders
* Password reset
* Email verification
* Account settings
* Rate limiting
* Improved monitoring and logging
* Cloud database deployment
* Production deployment
* Docker containerization
* Automated CI/CD
* Better scheduler persistence and recovery

---

# 🎯 Project Objective

The main objective of Future Capsule is to combine:

```text
Web Development
       +
Database Management
       +
Authentication
       +
Background Scheduling
       +
Email Automation
```

into a practical Java web application.

The project demonstrates how a user-facing web application can interact with a database, run background tasks, and communicate with an external email service.

---

# 👨‍💻 Author

**Charan**

Future Capsule was developed as a Java web application project focused on scheduled message delivery and backend automation.

---

# 📜 License

This project is intended for educational and portfolio purposes.

````

### One small thing, bro

Before you commit this README, **replace**:

```text
YOUR_USERNAME
````

with your actual GitHub username in the clone URL.

And don't put your Gmail address or App Password anywhere in the README.

After you save `README.md`, run:

```bat
git add README.md
git commit -m "Add project documentation"
git push
```


# ApiAutomationRest

A REST API automation framework using **REST Assured 5.x**, **TestNG 7.9**, **Extent Reports 5.x**, and **Log4j2**, targeting the [JSONPlaceholder](https://jsonplaceholder.typicode.com) public API.

## 🛠 Tech Stack

| Tool | Version |
|---|---|
| Java | 11 |
| REST Assured | 5.4.0 |
| TestNG | 7.9.0 |
| Extent Reports | 5.1.1 |
| Log4j2 | 2.23.1 |
| Gson | 2.10.1 |
| Maven | 3.x |

## 📁 Project Structure

```
src/
├── main/java/com/typicode/jsonplaceholder/
│   ├── utils/          → ConfigReader, RequestSpecProvider, EmailValidator
│   ├── GetUsers.java
│   ├── GetUserPosts.java
│   ├── GetPostComments.java
│   └── PostUser.java
├── test/java/com/typicode/jsonplaceholder/tests/
│   ├── BaseTestClass.java      → ExtentReports setup/teardown
│   ├── UsersApiTest.java       → GET /users
│   ├── UserPostsApiTest.java   → GET /posts?userId=
│   ├── PostCommentsApiTest.java → GET /posts/{id}/comments
│   └── PostUserApiTest.java    → POST /posts
└── test/resources/
    └── config.properties
```

## ⚙️ Configuration

All settings are managed in `src/test/resources/config.properties`:

```properties
baseUrl=https://jsonplaceholder.typicode.com
test.userName=Samantha
test.expectedUserId=3
test.postUserId=1
```

## ▶️ How to Run

```bash
# Run all tests via Maven
mvn clean test

# Run via TestNG XML
mvn test -DsuiteXmlFile=testng.xml
```

## 🐳 Docker

```bash
docker build -t api-automation .
```

## 📊 Reports

After execution, the HTML report is generated at:
```
test-output/ExtentReport.html
```

## ✅ Test Coverage

| Test Class | Endpoint | Coverage |
|---|---|---|
| UsersApiTest | GET /users | Status 200, non-empty list, user ID lookup |
| UserPostsApiTest | GET /posts?userId= | Status 200, non-empty, userId integrity |
| PostCommentsApiTest | GET /posts/{id}/comments | Status 200, field presence, email format |
| PostUserApiTest | POST /posts | Status 201, response body validation |

## 🔁 CI/CD

CircleCI is configured in `.circleci/config.yml`. On every push, it builds the project and runs all tests, storing surefire reports as artifacts.

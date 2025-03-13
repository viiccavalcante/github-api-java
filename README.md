# **Spring Boot Github API**

- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **MapStruct**
- **GitHub REST API**
- **dotenv-java**

## Getting Started

### Prerequisites

- Java 21 installed
- PostgreSQL database configured
  
### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/viiccavalcante/github-api-java.git
   ```
2. Configure DB environment variables in `.env`
   ```sh
    DB_URL=your_url
    DB_USERNAME=your_username
    DB_PASSWORD=your_password
   ```

4. Run the application:
   ```sh
   ./gradlew bootRun
   ```

## API Endpoints

### Track a repository

**POST** `/api/track`
body:

```json
{
  "email": "user@example.com",
  "repoOwner": "viiccavalcante",
  "repoName": "github-api-java",
  "alertsFor": ["COMMIT", "PULL_REQUEST"] 
}
```
PS.: The alertsFor field only accepts: COMMITS | PULLS | ISSUES | RELEASES  
But I didn't have time to implement sending the alerts to the users.

### Get tracked repositories by email

**GET** `/api/all-tracked/{email}`

### Get tracked repository by ID

**GET** `/api/tracked/{repoId}`


### Remove tracking

**DELETE** `/api/tracked/{trackedRepoId}`

## Scheduled Tasks

- The application periodically fetches repository statistics and updates the stored data.
- Uses Spring's `@Scheduled` to trigger updates every **30 seconds**. (to test)



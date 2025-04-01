<h1>BlogAI 📝🤖</h1>

<p>BlogAI is a backend service for a blogging platform that integrates AI-powered summarization. It enables users to generate concise summaries of blog posts using AI while managing content efficiently.</p>



| <h2>🚀 Features</h2>                               |
|----------------------------------------------------|
| User authentication and authorization (JWT/OAuth2) |
| Blog post creation, updating, and deletion         |
| Email notifications via JavaMailSender             |
| PostgreSQL for data persistence                    |

- 🛠 Tech Stack
    - Backend: Java, Spring Boot
    - Database: PostgreSQL
    - Email: JavaMailSender
    - AI Integration: (OpenAI API)

# Installation 
1. Clone the repository
    ```
   
   git clone https://github.com/Olatomiw/BlogAI.git
   
   ```
2. Set up PostgreSQL
   * Install PostgreSQL and create a database
   * Configure database credentials in application.properties
3. Run the application
    ```
   mvn spring-boot:run

   ```
   
# 📌 Usage
1. API endpoints allow users to create, edit, and summarize blog posts 
2. WebSocket updates notify users of new posts in real-time 
3. Emails are sent for important updates

# Contributing
Contributions are welcome! Feel free to fork the repo and submit a pull request.
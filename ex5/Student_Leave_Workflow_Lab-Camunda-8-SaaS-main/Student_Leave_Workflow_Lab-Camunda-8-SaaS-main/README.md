# Hostel Out-Pass Workflow

This is a complete Camunda 8 SaaS + Spring Boot 3 workflow application for handling Hostel Out-Pass requests.

## Setup & Running

1. Update `src/main/resources/application.yaml` with your Camunda 8 SaaS credentials:
   - `client-id`
   - `client-secret`
   - `cluster-id`
   - `region`

2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

3. Test via REST API (cURL / Postman):

   - **Auto-Approved (Day Out):**
     ```bash
     curl -X POST http://localhost:8080/outpass -H "Content-Type: application/json" -d '{"studentName": "Rahul", "regNo":"RA2311003010002", "hostelBlock": "Block A", "outDate":"2026-10-25", "returnDate":"2026-10-25", "parentPhone":"9876543210", "purpose": "Shopping"}'
     ```

   - **Warden Approval Required (Night Stay):**
     ```bash
     curl -X POST http://localhost:8080/outpass -H "Content-Type: application/json" -d '{"studentName": "Priya", "regNo":"RA2311003010003", "hostelBlock": "Block B", "outDate":"2026-10-25", "returnDate":"2026-10-27", "parentPhone":"9876543211", "purpose": "Home Visit"}'
     ```

4. Push to GitHub:
   ```bash
   git init
   git add .
   git commit -m "Initial commit: Hostel Out-Pass Camunda 8 workflow project"
   git branch -M main
   git remote add origin <YOUR_GITHUB_REPO_URL>
   git push -u origin main
   ```

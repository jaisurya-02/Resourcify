# Campus Resource Booking System

## 📌 Project Overview
The **Campus Resource Booking System** is a Java + DBMS-based application that helps students and faculty easily book and manage campus resources such as classrooms, labs, seminar halls, and sports facilities. The system ensures availability, avoids conflicts, and simplifies the approval process.

---

## 🎯 Features
- User login and authentication (Students, Faculty, Admin).
- Search and book available resources.
- View and cancel bookings.
- Admin can add/update resources.
- Admin can approve/reject booking requests.
- Database integration for storing resource and booking details.

---

## 🛠 Tech Stack
### Frontend
- HTML, CSS, JavaScript

### Backend
- Java (OOP concepts)
- Java Servlets / JSP
- JDBC for database connectivity

### Database
- MySQL (Booking & Resource tables)

---

## 📂 Project Structure
```
CampusResourceBookingSystem/
│── src/                # Java source files
│   ├── model/          # Classes (User, Resource, Booking, Admin, etc.)
│   ├── dao/            # Database access objects
│   ├── servlet/        # Servlets (Controllers)
│── web/                # JSP & static web files
│── db/                 # SQL scripts
│── README.md
```

---

## 📊 UML Diagrams
- Use Case Diagram ✅
- Class Diagram ✅
- (Add Activity + Sequence diagrams if available)

---

## 🚀 How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/campus-resource-booking-system.git
   ```
2. Import project into **Eclipse/IntelliJ**.
3. Set up MySQL database using `db/schema.sql`.
4. Run on **Tomcat server**.
5. Open in browser: `http://localhost:8080/CampusResourceBookingSystem`.

---

## 👨‍💻 Contributors
- Jai Surya S (Sri Eshwar College of Engineering)

---

## 📜 License
This project is for educational purposes only.

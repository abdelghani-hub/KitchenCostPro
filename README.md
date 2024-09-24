# Kitchen Cost Estimation Application

## Project Description

**Kitchen Cost Estimation Application** is a command-line Java application designed for professionals in the kitchen construction and renovation industry. It calculates the total cost of projects, considering both materials and labor, providing detailed cost estimations. The app also features client management, project tracking, and invoice generation.

## Features

- **Client Management**: Add, update, and manage client data (professionals or individuals).
- **Component Management**: Add and manage materials and labor with associated costs.
- **Cost Estimation**: Generate cost estimations for kitchen renovation projects based on components, labor, and taxes.
- **Quotation Generation**: Generate and manage project quotations.
- **Tax and Discount Handling**: Automatically calculate taxes (VAT) and apply discounts for professional clients.
- **Project Management**: Track multiple projects and manage their lifecycle (in-progress, completed, canceled).

## Technologies

- **Java 8**: The core programming language.
- **PostgreSQL**: Database for storing client, project, and component data.
- **Java Time API**: For managing dates in projects and quotations.
- **Streams and Optionals**: Used to process data collections effectively.
- **Design Patterns**: Utilizes Singleton and Repository patterns to structure the application.
- **JDBC**: For database interaction.
- **Git**: Version control for tracking progress.
- **JIRA**: Project management tool for issue tracking and task assignment.

## Project Structure

```bash
src/
├── main/
│   ├── java/
│   │   ├── controller/          # Contains controllers to handle client, project, and component logic.
│   │   ├── model/               # Model classes for Client, Project, Components, etc.
│   │   ├── service/             # Business logic services (ClientService, ProjectService, etc.).
│   │   ├── dao/                 # Data Access Object classes for database interaction.
│   │   └── utils/               # Helper classes (ConsoleUI for input/output handling).
│   ├── resources/
│   │   └── config/              # Database configuration file.
└── README.md                    # Project documentation.

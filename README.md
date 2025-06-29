<h1 align="left">SlotGame 🎰</h1>
<p align="left">A Spring Boot-based slot game application with RESTful API and PostgreSQL(not yet, rn H2) database.</p>

<p align="left">
  <a href="https://github.com/l4yoos/slot-game/commits/main">
    <img src="https://img.shields.io/github/last-commit/user/slot-game" alt="Last Commit">
  </a>
  <a href="https://github.com/l4yoos/slot-game">
    <img src="https://img.shields.io/github/languages/top/l4yoos/slot-game" alt="Top Language">
  </a>
  <a href="https://github.com/l4yoos/slot-game">
    <img src="https://img.shields.io/github/languages/count/l4yoos/slot-game" alt="Language Count">
  </a>
</p>

<hr/>

<h2 align="left" id="overview">🚀 Overview</h2>
<p align="left">This project is a slot game application built using Spring Boot, with a RESTful API and a PostgreSQL database. It includes a service layer for game logic and a controller layer for API endpoints.</p>
<ul align="left">
  <li>Spring Boot 3.2.0</li>
  <li>Java 17</li>
  <li>Lombok 1.18.30</li>
  <li>Testcontainers 1.19.3</li>
  <li>PostgreSQL 42.7.1 (Not yet)</li>
</ul>

<hr/>

<h2 align="left" id="built-with">📦 Built With</h2>
<p align="left">This project uses the following technologies and frameworks:</p>
<ul align="left">
  <li>Spring Boot</li>
  <li>Java</li>
  <li>Lombok</li>
  <li>Testcontainers</li>
  <li>PostgreSQL (Not yet)</li>
</ul>

<hr/>

<h2 align="left" id="table-of-contents">📚 Table of Contents</h2>
<p align="left">This README is organized into the following sections:</p>
<ul align="left">
  <li><a href="#overview">Overview</a></li>
  <li><a href="#built-with">Built With</a></li>
  <li><a href="#table-of-contents">Table of Contents</a></li>
  <li><a href="#architecture">Architecture</a></li>
  <li><a href="#prerequisites">Prerequisites</a></li>
  <li><a href="#installation">Installation</a></li>
  <li><a href="#usage">Usage</a></li>
  <li><a href="#testing">Testing</a></li>
  <li><a href="#demo">Demo</a></li>
</ul>

<hr/>

<h2 align="left" id="architecture">🏗️ Architecture</h2>
<p align="left">This project follows a layered architecture, with a clear separation of concerns between the presentation layer (controllers), service layer (services), and data access layer (repositories).</p>
<p align="left">The architecture is based on the Spring Boot framework, with a RESTful API and a PostgreSQL database.</p>

<hr/>

<h2 align="left" id="prerequisites">✅ Prerequisites</h2>
<p align="left">To run this project, you need:</p>
<ul align="left">
  <li>Java 17</li>
  <li>Maven 3.13.0</li>
  <li>PostgreSQL 42.7.1 (Not yet)</li>
</ul>

<hr/>

<h2 align="left" id="installation">🛠️ Installation</h2>
<p align="left">To install and run this project, follow these steps:</p>
<ul align="left">
  <li>Clone the repository</li>
  <li>Run `mvn clean package` to build the project</li>
  <li>Run `mvn spring-boot:run` to start the application</li>
</ul>

<hr/>

<h2 align="left" id="usage">🚀 Usage</h2>
<p align="left">The application provides two API endpoints:</p>
<ul align="left">
  <li>`POST /api/slot/spin`: Spin the slot machine</li>
  <li>`POST /api/slot/autospin`: Auto-spin the slot machine</li>
</ul>

<hr/>

<h2 align="left" id="testing">🧪 Testing</h2>
<p align="left">This project includes unit tests for the service layer and controller layer.</p>
<ul align="left">
  <li>Unit tests for the `SlotService` class</li>
  <li>Unit tests for the `SlotController` class</li>
  <li>Unit tests for the `SlotMachineFacadeTest` class</li>
</ul>

<hr/>

<h2 align="left" id="demo">🎬 Demo</h2>
<p align="left">soon...</p>

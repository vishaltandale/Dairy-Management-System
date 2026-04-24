# 🚀 MilkMate Installation Guide

Complete step-by-step instructions for setting up the MilkMate Dairy Management System development environment.

---

## 📋 Table of Contents
1. [Prerequisites](#-prerequisites)
2. [System Requirements](#-system-requirements)
3. [Installation Steps](#-installation-steps)
4. [Database Setup](#-database-setup)
5. [Environment Configuration](#-environment-configuration)
6. [Building the Project](#-building-the-project)
7. [Running the Application](#-running-the-application)
8. [Verification](#-verification)
9. [Troubleshooting](#-troubleshooting)
10. [IDE Setup](#-ide-setup)

---

## 🎯 Prerequisites

Before installing MilkMate, ensure you have the following software installed:

### Required Software

| Software | Version | Purpose | Download Link |
|----------|---------|---------|---------------|
| **Java JDK** | 17 or higher | Runtime environment | [Adoptium](https://adoptium.net/) |
| **Maven** | 3.6+ | Build tool | [Maven](https://maven.apache.org/download.cgi) |
| **Git** | 2.30+ | Version control | [Git](https://git-scm.com/downloads) |
| **MySQL** | 8.0+ (optional) | Production database | [MySQL](https://dev.mysql.com/downloads/) |

### Recommended Software

| Software | Purpose | Download Link |
|----------|---------|---------------|
| **IntelliJ IDEA** | Java IDE (Community or Ultimate) | [JetBrains](https://www.jetbrains.com/idea/) |
| **VS Code** | Lightweight code editor | [Visual Studio Code](https://code.visualstudio.com/) |
| **Postman** | API testing | [Postman](https://www.postman.com/downloads/) |
| **DBeaver** | Database management | [DBeaver](https://dbeaver.io/) |

---

## 💻 System Requirements

### Minimum Requirements
- **OS**: Windows 10/11, macOS 11+, or Ubuntu 20.04+
- **RAM**: 4 GB (8 GB recommended)
- **Disk Space**: 2 GB free space
- **CPU**: Dual-core processor

### Recommended Requirements
- **OS**: Windows 11, macOS 12+, or Ubuntu 22.04 LTS
- **RAM**: 8 GB or higher
- **Disk Space**: 5 GB free space (for dependencies and databases)
- **CPU**: Quad-core processor
- **Internet**: Required for downloading dependencies

---

## 📥 Installation Steps

### Step 1: Install Java JDK

#### Windows
1. Download JDK 17+ from [Adoptium](https://adoptium.net/)
2. Run the installer
3. Set environment variables:
   ```powershell
   # Open PowerShell as Administrator
   [System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot", "Machine")
   [System.Environment]::SetEnvironmentVariable("PATH", $env:PATH + ";%JAVA_HOME%\bin", "Machine")
   ```

#### macOS
```bash
# Using Homebrew
brew install openjdk@17

# Add to ~/.zshrc or ~/.bash_profile
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-17-jdk

# Verify installation
java -version
```

#### Verify Java Installation
```bash
java -version
javac -version
```

**Expected Output:**
```
openjdk version "17.0.x" 2024-01-16
OpenJDK Runtime Environment (build 17.0.x+x)
OpenJDK 64-Bit Server VM (build 17.0.x+x, mixed mode)
```

---

### Step 2: Install Maven

#### Windows
1. Download from [Maven Downloads](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Apache\maven`
3. Add to PATH:
   ```powershell
   [System.Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Program Files\Apache\maven", "Machine")
   [System.Environment]::SetEnvironmentVariable("PATH", $env:PATH + ";%MAVEN_HOME%\bin", "Machine")
   ```

#### macOS
```bash
brew install maven
```

#### Linux
```bash
sudo apt install maven
```

#### Verify Maven Installation
```bash
mvn -version
```

**Expected Output:**
```
Apache Maven 3.9.x
Maven home: /path/to/maven
Java version: 17.0.x, vendor: Eclipse Adoptium
```

**Note**: If Maven is not installed, you can use the included Maven Wrapper (`mvnw` or `mvnw.cmd`).

---

### Step 3: Install Git

#### Windows
1. Download from [Git SCM](https://git-scm.com/download/win)
2. Run installer with default settings
3. Verify:
   ```powershell
   git --version
   ```

#### macOS
```bash
# Xcode Command Line Tools includes Git
xcode-select --install

# Or using Homebrew
brew install git
```

#### Linux
```bash
sudo apt install git
```

---

### Step 4: Clone the Repository

```bash
# Navigate to your workspace
cd ~/projects  # or your preferred directory

# Clone the repository
git clone https://github.com/vishaltandale/Milkmate.git

# Navigate to project directory
cd Milkmate

# Verify project structure
ls -la
```

**Expected Output:**
```
.
├── .env.example
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
├── src/
└── README.md
```

---

## 🗄️ Database Setup

### Option 1: H2 Database (Development - Default)

**No setup required!** The application uses an in-memory H2 database by default. This is perfect for development and testing.

#### Access H2 Console
1. Start the application (see [Running the Application](#-running-the-application))
2. Open browser: http://localhost:8080/h2-console
3. Use these settings:
   - **JDBC URL**: `jdbc:h2:mem:dairy_management_db`
   - **Username**: `sa`
   - **Password**: (leave empty)
4. Click "Connect"

---

### Option 2: MySQL Database (Production)

#### Step 1: Install MySQL

**Windows:**
1. Download [MySQL Installer](https://dev.mysql.com/downloads/installer/)
2. Run installer
3. Select "Developer Default" setup type
4. Set root password (remember this!)
5. Complete installation

**macOS:**
```bash
brew install mysql
brew services start mysql
```

**Linux:**
```bash
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

#### Step 2: Create Database and User

```bash
# Login to MySQL
mysql -u root -p
# Enter your root password
```

```sql
-- Create database
CREATE DATABASE dairy_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user
CREATE USER 'dairy_user'@'localhost' IDENTIFIED BY 'YourSecurePassword123!';

-- Grant privileges
GRANT ALL PRIVILEGES ON dairy_management.* TO 'dairy_user'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

-- Verify database
SHOW DATABASES;

-- Exit
EXIT;
```

#### Step 3: Verify MySQL Connection

```bash
mysql -u dairy_user -p dairy_management
# Enter password: YourSecurePassword123!

# If connection succeeds, MySQL is configured correctly
EXIT;
```

---

## ⚙️ Environment Configuration

### Step 1: Create .env File

```bash
# Copy the example file
cp .env.example .env

# Or on Windows PowerShell
Copy-Item .env.example .env
```

### Step 2: Configure Environment Variables

Open `.env` file in your editor and configure:

#### For H2 Database (Development)
```properties
# Database Configuration (H2 - Development)
DB_URL=jdbc:h2:mem:dairy_management_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
DB_DRIVER=org.h2.Driver
DB_USERNAME=sa
DB_PASSWORD=

# Email Configuration (Optional - for email notifications)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# Admin Credentials
ADMIN_EMAIL=admin@gmail.com
ADMIN_PASSWORD=pass
```

#### For MySQL Database (Production)
```properties
# Database Configuration (MySQL - Production)
DB_URL=jdbc:mysql://localhost:3306/dairy_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_DRIVER=com.mysql.cj.jdbc.Driver
DB_USERNAME=dairy_user
DB_PASSWORD=YourSecurePassword123!

# Email Configuration
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_gmail_app_password

# Admin Credentials
ADMIN_EMAIL=admin@gmail.com
ADMIN_PASSWORD=pass
```

### Step 3: Gmail App Password Setup (Optional)

If you want email notifications:

1. Go to [Google Account](https://myaccount.google.com/)
2. Navigate to **Security** → **2-Step Verification**
3. Enable 2-Step Verification (if not already enabled)
4. Go to **App Passwords**
5. Select "Mail" and your device
6. Generate password (16 characters)
7. Copy this password to `.env` file as `MAIL_PASSWORD`

---

## 🔨 Building the Project

### Method 1: Using Maven Wrapper (Recommended)

**Windows:**
```powershell
.\mvnw.cmd clean install
```

**macOS/Linux:**
```bash
./mvnw clean install
```

### Method 2: Using Maven (if installed globally)

```bash
mvn clean install
```

### What This Does:
1. ✅ Downloads all dependencies
2. ✅ Compiles source code
3. ✅ Runs unit tests
4. ✅ Packages the application
5. ✅ Creates executable JAR in `target/` directory

### Expected Build Output:
```
[INFO] Scanning for projects...
[INFO] Building DairyManagement 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.3.2:clean ---
[INFO] Deleting target directory
[INFO] 
[INFO] --- maven-compiler-plugin:3.11.0:compile ---
[INFO] Compiling 50 source files
[INFO] 
[INFO] --- maven-surefire-plugin:3.2.3:test ---
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] --- maven-jar-plugin:3.3.0:jar ---
[INFO] Building jar: target/dairyManagement-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] BUILD SUCCESS
[INFO] Total time: 45.234 s
```

### Troubleshooting Build Issues

**Error: "Java version mismatch"**
```bash
# Check Java version
java -version

# Must be Java 17 or higher
# If wrong version, update JAVA_HOME environment variable
```

**Error: "Dependencies not found"**
```bash
# Force update dependencies
mvn clean install -U
```

**Error: "Tests failed"**
```bash
# Skip tests temporarily (not recommended for production)
mvn clean install -DskipTests
```

---

## 🏃 Running the Application

### Method 1: Using Maven (Recommended for Development)

**Windows:**
```powershell
.\mvnw.cmd spring-boot:run
```

**macOS/Linux:**
```bash
./mvnw spring-boot:run
```

### Method 2: Run JAR File

```bash
# Build first
mvn clean package -DskipTests

# Run the JAR
java -jar target/dairyManagement-0.0.1-SNAPSHOT.jar
```

### Method 3: From IDE

#### IntelliJ IDEA
1. Open project folder
2. Wait for Maven to import dependencies
3. Find `DairyManagementApplication.java` in `src/main/java/com/DM/dairyManagement/`
4. Right-click → **Run 'DairyManagementApplication'**

#### VS Code
1. Install extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
2. Open project folder
3. Open `DairyManagementApplication.java`
4. Click **Run** button above `main()` method

#### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select project root
3. Right-click project → Maven → Update Project
4. Run as → Spring Boot App

---

## ✅ Verification

### Step 1: Check Application Startup

After running, you should see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.4.2)

2024-xx-xx xx:xx:xx  INFO --- [main] c.DM.dairyManagement.DairyManagementApplication : Starting DairyManagementApplication
2024-xx-xx xx:xx:xx  INFO --- [main] c.DM.dairyManagement.DairyManagementApplication : Started DairyManagementApplication in 8.5 seconds
```

### Step 2: Access the Application

Open your browser and navigate to:

| Service | URL | Description |
|---------|-----|-------------|
| **Main Application** | http://localhost:8080 | Dairy Management System |
| **H2 Console** | http://localhost:8080/h2-console | Database console (development only) |
| **API Documentation** | http://localhost:8080/swagger-ui.html | Swagger UI (if enabled) |

### Step 3: Login to the Application

**Default Admin Credentials:**
- **Email**: `admin@gmail.com`
- **Password**: `pass`

### Step 4: Verify Core Features

After logging in, verify these features work:

1. ✅ **Dashboard** - Shows statistics and charts
2. ✅ **Create Bill** - Navigate to billing section
3. ✅ **Product List** - View products
4. ✅ **Company Management** - View companies
5. ✅ **Stock List** - View inventory

### Step 5: Test Database Connection

#### For H2:
1. Go to http://localhost:8080/h2-console
2. Connect with credentials from [H2 Database section](#option-1-h2-database-development---default)
3. Run query: `SELECT * FROM users;`
4. Should show admin user

#### For MySQL:
```bash
mysql -u dairy_user -p dairy_management

# Check tables
SHOW TABLES;

# Check users
SELECT * FROM users;
```

---

## 🔧 Troubleshooting

### Issue 1: Port 8080 Already in Use

**Error:**
```
Web server failed to start. Port 8080 was already in use.
```

**Solution:**

**Option A: Change Port**
```properties
# In application.properties or .env
server.port=8081
```

**Option B: Kill Process Using Port 8080**

**Windows:**
```powershell
# Find process
netstat -ano | findstr :8080
# Output: TCP 0.0.0.0:8080  0.0.0.0:0  LISTENING  12345

# Kill process
taskkill /PID 12345 /F
```

**macOS/Linux:**
```bash
# Find process
lsof -i :8080
# or
sudo netstat -tulpn | grep :8080

# Kill process
kill -9 <PID>
```

---

### Issue 2: Database Connection Failed

**Error:**
```
Communications link failure
```

**Solutions:**

1. **Check if MySQL is running:**
   ```bash
   # Windows
   services.msc → Find MySQL → Start

   # macOS
   brew services list
   brew services start mysql

   # Linux
   sudo systemctl status mysql
   sudo systemctl start mysql
   ```

2. **Verify credentials in `.env`:**
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/dairy_management?createDatabaseIfNotExist=true
   DB_USERNAME=dairy_user
   DB_PASSWORD=correct_password
   ```

3. **Test connection:**
   ```bash
   mysql -u dairy_user -p dairy_management
   ```

---

### Issue 3: Email Not Working

**Error:**
```
Authentication failed for user your_email@gmail.com
```

**Solution:**
1. Enable 2-Step Verification in Google Account
2. Generate App Password (not regular password)
3. Use 16-character app password in `.env`

---

### Issue 4: Build Fails - Dependencies Not Found

**Error:**
```
Could not resolve dependencies for project
```

**Solution:**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Rebuild with forced update
mvn clean install -U
```

---

### Issue 5: Application Starts but Cannot Login

**Solution:**
1. Check if admin user exists in database:
   ```sql
   SELECT * FROM users WHERE email='admin@gmail.com';
   ```

2. If not exists, the `DataInitializationService` should create it on startup

3. Check console logs for initialization messages

4. Verify `.env` file has correct `ADMIN_EMAIL` and `ADMIN_PASSWORD`

---

## 💻 IDE Setup

### IntelliJ IDEA (Recommended)

#### Step 1: Import Project
1. File → Open → Select `Milkmate` folder
2. Select `pom.xml` when prompted
3. Wait for "Importing Maven projects" to complete

#### Step 2: Configure Run Configuration
1. Run → Edit Configurations
2. Click **+** → Spring Boot
3. Configure:
   - **Name**: MilkMate
   - **Main class**: `com.DM.dairyManagement.DairyManagementApplication`
   - **Working directory**: `$MODULE_WORKING_DIR$`
   - **Environment variables**: Load from `.env` file

#### Step 3: Enable Hot Reload
1. Install "Spring Boot Assistant" plugin
2. Enable automatic build:
   - Settings → Build, Execution, Deployment → Compiler
   - Check "Build project automatically"
3. Enable registry:
   - Help → Find Action → "Registry"
   - Check `compiler.automake.allow.when.app.running`

---

### VS Code

#### Step 1: Install Extensions
1. **Extension Pack for Java**
2. **Spring Boot Extension Pack**
3. **Maven for Java**
4. **Project Manager for Java**

#### Step 2: Open Project
1. File → Open Folder → Select `Milkmate`
2. Wait for Java language server to initialize
3. Check Java version in status bar (bottom-right)

#### Step 3: Run Application
1. Open `DairyManagementApplication.java`
2. Click **Run** or **Debug** button above `main()` method
3. Or use Command Palette: `Ctrl+Shift+P` → "Spring Boot: Run"

---

### Eclipse

#### Step 1: Import Project
1. File → Import → Maven → Existing Maven Projects
2. Browse to `Milkmate` folder
3. Select `pom.xml`
4. Click Finish

#### Step 2: Update Project
1. Right-click project → Maven → Update Project
2. Check "Force Update of Snapshots/Releases"
3. Click OK

#### Step 3: Run Application
1. Right-click `DairyManagementApplication.java`
2. Run As → Spring Boot App

---

## 📊 Performance Tips

### 1. Increase JVM Memory (for large datasets)
```bash
java -Xmx2g -Xms1g -jar target/dairyManagement-0.0.1-SNAPSHOT.jar
```

### 2. Enable Build Cache
```bash
# In pom.xml, add:
<properties>
    <maven.compiler.useIncrementalCompilation>true</maven.compiler.useIncrementalCompilation>
</properties>
```

### 3. Use SSD Storage
- Store project on SSD for faster build times
- Maven dependencies cache on SSD (`~/.m2/repository`)

---

## 🎓 Next Steps

After successful installation:

1. 📖 Read [COMPLETE_DOCUMENTATION.md](COMPLETE_DOCUMENTATION.md) for full feature guide
2. 🏗️ Review [ARCHITECTURE.md](ARCHITECTURE.md) for system architecture
3. 💻 Check [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) for development workflows
4. 🧪 Run tests: `mvn test`
5. 🚀 Start developing new features!

---

## 📞 Support

If you encounter issues not covered here:

1. **Check Logs**: Review console output for error messages
2. **Search Issues**: https://github.com/vishaltandale/Milkmate/issues
3. **Create Issue**: Provide:
   - OS and Java version
   - Error messages
   - Steps to reproduce
   - `.env` configuration (remove passwords!)

---

<div align="center">

**Happy Coding! 🐄✨**

[Documentation](COMPLETE_DOCUMENTATION.md) • [Architecture](ARCHITECTURE.md) • [Development Guide](DEVELOPMENT_GUIDE.md)

</div>

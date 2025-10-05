# Oracle SQL Setup Guide

## **Option 1: Oracle Live SQL (Recommended for Students - FREE)**

### ✅ Advantages:
- 100% Free, no installation needed
- Works in browser
- Pre-configured Oracle environment
- Perfect for college projects

### 📝 Steps:

1. **Create Oracle Account**
   - Go to: https://livesql.oracle.com/
   - Click "Sign In" → "Create Account"
   - Use your college email
   - Verify email

2. **Run Your Project**

   **Step 1: Create Schema**
   - Click "SQL Worksheet" or "My Scripts"
   - Copy entire `relational_schema.sql` content
   - Paste and click **Run** (green play button)
   - Wait for "Tables created successfully"

   **Step 2: Insert Data**
   - Clear the worksheet
   - Copy entire `insert_data.sql` content
   - Paste and click **Run**
   - Verify: "7 rows inserted" × multiple tables

   **Step 3: Test Queries**
   - Copy queries from `queries.sql` one by one
   - Run each and verify results

### ⚠️ Important Notes:
- Oracle Live SQL **auto-commits** transactions
- Schema persists across sessions
- To reset: `DROP TABLE` all tables manually or use "Clear Schema" button

### 🔗 JDBC Connection (for Java app):
**Oracle Live SQL does NOT support external JDBC connections.**
- You can only use the web interface
- For Java app, use **Option 2** below

---

## **Option 2: Oracle XE (Local Installation)**

### ✅ Advantages:
- Full control
- JDBC support (Java app works)
- Offline access
- Professional environment

### 📦 Installation Steps:

#### **Windows:**

1. **Download Oracle XE 21c**
   ```
   https://www.oracle.com/database/technologies/xe-downloads.html
   ```
   - Choose "Oracle Database 21c Express Edition for Windows x64"
   - ~2.5 GB download

2. **Install**
   - Run installer
   - Set **System Password** (remember this!)
   - Default settings are fine
   - Installation path: `C:\app\oracle\product\21c\`

3. **Verify Installation**
   ```bash
   # Open Command Prompt
   sqlplus system/YOUR_PASSWORD@localhost:1521/XE
   ```
   - Should see: `Connected to Oracle Database 21c`
   - Exit: `EXIT;`

#### **macOS:**
```bash
# Install via Docker (Oracle XE not available natively)
docker pull gvenzl/oracle-xe:21-slim
docker run -d -p 1521:1521 -e ORACLE_PASSWORD=yourpassword gvenzl/oracle-xe:21-slim
```

#### **Linux:**
```bash
# Ubuntu/Debian
wget https://download.oracle.com/otn-pub/otn_software/db-express/oracle-database-xe-21c-1.0-1.ol7.x86_64.rpm
sudo alien -i oracle-database-xe-21c-1.0-1.ol7.x86_64.rpm
```

---

## **Option 3: Oracle SQL Developer (GUI Tool)**

### 📥 Download:
```
https://www.oracle.com/tools/downloads/sqldev-downloads.html
```
- Works with both Oracle XE and Oracle Live SQL
- Professional GUI for database management

### 🔌 Connect to Local Oracle XE:
1. Open SQL Developer
2. Click "+" (New Connection)
3. Enter:
   - Connection Name: `Retailer_DB`
   - Username: `system`
   - Password: `YOUR_SYSTEM_PASSWORD`
   - Hostname: `localhost`
   - Port: `1521`
   - SID: `XE` (or Service Name: `XEPDB1`)
4. Click "Test" → Should say "Success"
5. Click "Save" → "Connect"

---

## **Running Your Project**

### **Step-by-Step Execution:**

#### **1. Setup Database (Oracle XE or SQL Developer)**

```sql
-- Connect as system user first
sqlplus system/yourpassword@localhost:1521/XEPDB1
```

#### **2. Run Schema Creation**
```sql
-- Copy paste from relational_schema.sql
@D:\CODES\claude\Retailer_Database\Retailer_Database\relational_schema.sql
```

Or manually copy-paste the content and run.

#### **3. Insert Sample Data**
```sql
@D:\CODES\claude\Retailer_Database\Retailer_Database\insert_data.sql
```

#### **4. Verify Tables**
```sql
SELECT table_name FROM user_tables;
```
Expected output:
```
BRAND
PRODUCT
STORE
CUSTOMER
VENDOR
INVENTORY
SALE
SUPPLIES
```

#### **5. Test Queries**
```sql
-- Top selling products
SELECT p.name, SUM(s.quantity) AS total_units
FROM Sale s JOIN Product p ON s.product_id=p.product_id
GROUP BY p.name ORDER BY total_units DESC FETCH FIRST 5 ROWS ONLY;
```

---

## **Running Java Application**

### **Prerequisites:**
1. Java JDK 8+ installed
   ```bash
   java -version
   ```

2. Download JDBC Driver
   ```
   https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
   ```
   - Get `ojdbc8.jar` or `ojdbc11.jar`
   - Place in project root: `D:\CODES\claude\Retailer_Database\Retailer_Database\`

### **Update DBConnection.java:**

Edit `src/DBConnection.java`:

```java
// For Oracle XE local installation:
private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
private static final String USER = "system";
private static final String PASS = "YOUR_SYSTEM_PASSWORD"; // Change this!
```

### **Compile and Run:**

```bash
# Navigate to project directory
cd D:\CODES\claude\Retailer_Database\Retailer_Database

# Compile Java files
javac -cp ".;ojdbc8.jar" src/*.java

# Run application
java -cp ".;ojdbc8.jar" src.RetailerApp
```

**On Mac/Linux** use `:` instead of `;`:
```bash
javac -cp ".:ojdbc8.jar" src/*.java
java -cp ".:ojdbc8.jar" src.RetailerApp
```

---

## **Troubleshooting**

### 🔴 Common Issues:

#### **"Cannot connect to database"**
```sql
-- Check Oracle listener is running
lsnrctl status

-- Start listener if stopped
lsnrctl start
```

#### **"Table or view does not exist"**
- Verify you're connected to correct schema
- Check table names: `SELECT * FROM user_tables;`
- Re-run `relational_schema.sql`

#### **"Sequence does not exist"**
- Run: `CREATE SEQUENCE sale_seq START WITH 505;`

#### **Java ClassNotFoundException**
- Verify `ojdbc8.jar` is in classpath
- Check `-cp` parameter includes jar file

#### **Invalid username/password**
- Reset Oracle password:
  ```sql
  sqlplus / as sysdba
  ALTER USER system IDENTIFIED BY newpassword;
  ```

---

## **Quick Setup Commands Summary**

### **For Oracle Live SQL:**
1. Visit https://livesql.oracle.com/
2. Paste `relational_schema.sql` → Run
3. Paste `insert_data.sql` → Run
4. Test with queries from `queries.sql`

### **For Oracle XE Local:**
```bash
# 1. Install Oracle XE
# 2. Connect
sqlplus system/yourpass@localhost:1521/XEPDB1

# 3. Run setup
@relational_schema.sql
@insert_data.sql

# 4. Run Java app
javac -cp ".;ojdbc8.jar" src/*.java
java -cp ".;ojdbc8.jar" src.RetailerApp
```

---

## **Which Option Should You Choose?**

| Feature | Oracle Live SQL | Oracle XE Local |
|---------|----------------|-----------------|
| **Installation** | ✅ None needed | ⚠️ 2.5GB download |
| **Java JDBC** | ❌ Not supported | ✅ Full support |
| **Cost** | ✅ Free | ✅ Free |
| **Best For** | SQL queries only | Complete project |
| **Demo in College** | ⚠️ Needs internet | ✅ Works offline |

### **Recommendation:**
- **For SQL testing:** Use **Oracle Live SQL**
- **For full project with Java:** Install **Oracle XE**

---

## **Option 4: GitHub Codespaces with Local Oracle XE**

### ✅ Advantages:
- Cloud-based development environment
- Access from anywhere
- No local setup required for development
- Connect to your local Oracle XE database

### 📝 Setup Steps:

#### **1. Prerequisites:**
- Oracle XE installed locally on your machine
- Local Oracle XE running and accessible
- GitHub account

#### **2. Network Configuration:**

**Windows (if using GitHub Codespaces):**

You'll need to expose your local Oracle database to the Codespace. Use one of these methods:

**Option A: Using ngrok (Recommended)**
```bash
# Download ngrok from https://ngrok.com/download
# Expose Oracle port 1521
ngrok tcp 1521
```
This will give you a forwarding URL like: `tcp://0.tcp.ngrok.io:12345`

**Option B: GitHub Codespaces Port Forwarding**
- GitHub Codespaces can only forward ports FROM the codespace TO your local machine
- For connecting TO your local database FROM codespace, use ngrok or similar tunneling service

#### **3. Launch GitHub Codespace:**

1. Push your project to GitHub repository
2. Go to your repository on GitHub
3. Click **Code** → **Codespaces** → **Create codespace on main**
4. Wait for environment to load

#### **4. Install Oracle JDBC Driver in Codespace:**

```bash
# In Codespace terminal
cd /workspaces/your-repo-name

# Download Oracle JDBC driver
wget https://download.oracle.com/otn-pub/otn_software/jdbc/233/ojdbc11.jar

# Or use ojdbc8 for Java 8
wget https://download.oracle.com/otn-pub/otn_software/jdbc/217/ojdbc8.jar
```

#### **5. Configure Database Connection:**

Edit `src/DBConnection.java` in Codespace:

**If using ngrok:**
```java
// Replace with ngrok forwarding URL
private static final String URL = "jdbc:oracle:thin:@0.tcp.ngrok.io:12345/XEPDB1";
private static final String USER = "system";
private static final String PASS = "YOUR_SYSTEM_PASSWORD";
```

**Direct connection (if same network):**
```java
// Use your local machine's IP address
private static final String URL = "jdbc:oracle:thin:@YOUR_LOCAL_IP:1521/XEPDB1";
private static final String USER = "system";
private static final String PASS = "YOUR_SYSTEM_PASSWORD";
```

**To find your local IP:**
- Windows: `ipconfig` (look for IPv4 Address)
- Mac/Linux: `ifconfig` or `ip addr`

#### **6. Allow Remote Connections to Oracle XE:**

**Windows - Configure Oracle Listener:**

```bash
# Open Command Prompt as Administrator
sqlplus / as sysdba

# Check listener status
lsnrctl status

# Edit listener.ora (usually at C:\app\oracle\product\21c\network\admin\listener.ora)
# Ensure it has:
LISTENER =
  (DESCRIPTION_LIST =
    (DESCRIPTION =
      (ADDRESS = (PROTOCOL = TCP)(HOST = 0.0.0.0)(PORT = 1521))
    )
  )

# Restart listener
lsnrctl stop
lsnrctl start
```

**Allow Oracle through Windows Firewall:**
```bash
# Open Windows Defender Firewall
# Add inbound rule for port 1521
netsh advfirewall firewall add rule name="Oracle XE" dir=in action=allow protocol=TCP localport=1521
```

#### **7. Run Application in Codespace:**

```bash
# Install Java if not present
sudo apt-get update
sudo apt-get install -y default-jdk

# Compile
javac -cp ".:ojdbc11.jar" src/*.java

# Run
java -cp ".:ojdbc11.jar" src.RetailerApp
```

#### **8. Test Connection:**

```bash
# In Codespace terminal, test connectivity to your local Oracle
nc -zv YOUR_LOCAL_IP 1521

# Or if using ngrok
nc -zv 0.tcp.ngrok.io 12345
```

### ⚠️ Important Security Notes:

- **Never commit database passwords to GitHub**
- Use environment variables:
  ```java
  private static final String PASS = System.getenv("ORACLE_PASSWORD");
  ```
- Set in Codespace:
  ```bash
  export ORACLE_PASSWORD="yourpassword"
  ```
- Add to `.gitignore`:
  ```
  ojdbc*.jar
  .env
  ```

### 🔒 Best Practices for Codespaces:

1. **Use Secrets for Credentials:**
   - Go to repository Settings → Secrets and variables → Codespaces
   - Add `ORACLE_PASSWORD`, `ORACLE_USER`, `ORACLE_URL`
   - Access in code via environment variables

2. **Keep JDBC driver in .gitignore:**
   - Don't commit large jar files
   - Download during setup or use package manager

3. **Use Dev Containers (Optional):**
   Create `.devcontainer/devcontainer.json`:
   ```json
   {
     "name": "Retailer Database",
     "image": "mcr.microsoft.com/devcontainers/java:11",
     "features": {
       "ghcr.io/devcontainers/features/java:1": {
         "version": "11"
       }
     },
     "postCreateCommand": "wget https://download.oracle.com/otn-pub/otn_software/jdbc/233/ojdbc11.jar"
   }
   ```

### 📊 Architecture Diagram:

```
┌─────────────────────┐
│  GitHub Codespace   │
│  (Cloud VM)         │
│                     │
│  ┌──────────────┐   │         ┌──────────────┐
│  │ Java App     │───┼────────▶│   ngrok      │
│  │ RetailerApp  │   │         │  tunnel      │
│  └──────────────┘   │         └──────┬───────┘
└─────────────────────┘                │
                                       │ Internet
                                       │
                              ┌────────▼────────┐
                              │  Your Local PC  │
                              │                 │
                              │  Oracle XE      │
                              │  Port 1521      │
                              └─────────────────┘
```

---

## **Need Help?**

Check Oracle documentation:
- Live SQL: https://livesql.oracle.com/apex/f?p=590:1000
- XE Installation: https://docs.oracle.com/en/database/oracle/oracle-database/21/xeinl/
- GitHub Codespaces: https://docs.github.com/en/codespaces
- ngrok Documentation: https://ngrok.com/docs

Good luck with your project! 🚀

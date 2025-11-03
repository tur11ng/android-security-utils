# Android Security Utils

A comprehensive Android security testing and logging utility library for analyzing Intent-based communication, Service interactions, and data flows in Android applications.

## Overview

This project provides utilities for logging and analyzing Android IPC (Inter-Process Communication) mechanisms, making it easier to:
- Debug Intent-based communication
- Analyze security vulnerabilities in data flows
- Test service interactions
- Monitor broadcast receivers
- Inspect Parcelable objects
- Examine ContentResolver data access

## Components

### Logger Utilities

#### 1. IntentLogger
Logs comprehensive information about Android Intents including:
- Action, Data URI, Type
- Categories
- Component information
- Flags
- All extras with their types

**Usage:**
```java
Intent intent = new Intent("ACTION_NAME");
intent.putExtra("key", "value");
IntentLogger.log("MyTag", intent);
```

#### 2. BundleLogger
Logs all key-value pairs in a Bundle with type information.

**Usage:**
```java
Bundle bundle = new Bundle();
bundle.putString("key", "value");
BundleLogger.log("MyTag", bundle);
```

#### 3. MessageLogger
Logs android.os.Message objects including:
- what, arg1, arg2 fields
- obj object
- when timestamp
- target Handler
- callback Runnable
- data Bundle

**Usage:**
```java
Message msg = Message.obtain();
msg.what = 1;
msg.arg1 = 42;
MessageLogger.log("MyTag", msg);
```

#### 4. ParcelableLogger
Uses reflection to log all fields of any Parcelable object, including:
- Class information
- All instance fields (including inherited)
- Proper handling of arrays
- Recursive field logging through class hierarchy

**Usage:**
```java
Parcelable parcelable = new Intent("ACTION");
ParcelableLogger.log("MyTag", parcelable);
```

#### 5. ContentResolverLogger
Logs text content from various sources:
- URIs via ContentResolver
- InputStreams
- Intent data URIs
- URI metadata (cursors, columns, MIME types)

**Usage:**
```java
// From URI
Uri uri = Uri.parse("content://...");
ContentResolverLogger.log("MyTag", this, uri);

// From InputStream
InputStream stream = getContentResolver().openInputStream(uri);
ContentResolverLogger.log("MyTag", stream);

// From Intent
Intent intent = getIntent();
ContentResolverLogger.log("MyTag", this, intent);
```

#### 6. BroadcastReceiverLogger
Logs detailed information about BroadcastReceiver events.

**Usage:**
```java
@Override
public void onReceive(Context context, Intent intent) {
    BroadcastReceiverLogger.log("MyTag", this, context, intent);
}
```

### Activity Components

#### ServiceActivity
Comprehensive examples of Android Service communication:
- Starting services with data
- Binding to services
- Two-way communication via Messenger
- Receiving service replies
- Foreground service handling
- External service communication

See [SERVICE_ACTIVITY_README.md](SERVICE_ACTIVITY_README.md) for detailed documentation.

#### HypotheticalService
A fully functional example service demonstrating:
- Messenger-based IPC
- Message handling and routing
- Client registration/unregistration
- Sending replies to clients
- Comprehensive logging of all received data
- Foreground service capabilities

#### ExampleUsageActivity
Complete examples showing how to use all logger utilities and service communication patterns together.

## Project Structure

```
app/src/main/java/com/tur11ng/androidsecurityutils/
├── MainActivity.java                  # Main entry point with various examples
├── ExampleUsageActivity.java         # Comprehensive usage examples
├── IntentReceiverActivity.java       # Activity that receives Intents
├── IntentBroadcastReceiver.java      # Broadcast receiver
│
├── service/                           # Service-related components
│   ├── ServiceActivity.java          # Service communication examples
│   └── HypotheticalService.java      # Example service implementation
│
├── IntentLogger.java                  # Intent logging utility
├── BundleLogger.java                  # Bundle logging utility
├── MessageLogger.java                 # Message logging utility
├── ParcelableLogger.java             # Parcelable logging utility
├── ContentResolverLogger.java        # ContentResolver logging utility
├── BroadcastReceiverLogger.java      # BroadcastReceiver logging utility
│
├── AttackProvider.java               # (Additional component)
└── ...
```

## Key Features

### Comprehensive Logging
All loggers output to Android Logcat with the tag pattern: `LoggerName:YourTag`

### Security Analysis
Perfect for:
- Penetration testing Android apps
- Analyzing Intent-based vulnerabilities
- Debugging IPC issues
- Understanding data flows
- Inspecting service communication

### Reflection-Based Inspection
ParcelableLogger uses Java reflection to inspect objects, revealing:
- Private fields
- Inherited fields
- Array contents
- Complex object structures

### Service Communication Patterns
Complete examples of:
- `startService()` with data
- `bindService()` with callbacks
- Messenger-based IPC
- Request-reply patterns
- Broadcast to service patterns

## Usage Examples

### Example 1: Log an Intent Before Sending
```java
Intent intent = new Intent(this, TargetActivity.class);
intent.putExtra("user_id", 123);
intent.putExtra("session", "abc123");

IntentLogger.log("SendIntent", intent);
startActivity(intent);
```

### Example 2: Service Communication
Intent serviceIntent = new Intent(this, com.tur11ng.androidsecurityutils.service.ServiceActivity.class);
// Start ServiceActivity to see complete examples
Intent serviceIntent = new Intent(this, ServiceActivity.class);
startActivity(serviceIntent);
```

### Example 3: Log Received Broadcast
```java
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastReceiverLogger.log("MyReceiver", this, context, intent);
    }
}
```

### Example 4: Inspect Parcelable Objects
```java
CustomParcelable obj = new CustomParcelable();
ParcelableLogger.log("Inspect", obj);
// Logs all fields using reflection
```

## Android Manifest Configuration

The project includes a complete AndroidManifest.xml with:
- Service declarations
- Activity exports
- Intent filters
- Required permissions

## Security Testing Use Cases

### 1. Intent Fuzzing
Use the loggers to understand what data an app expects, then modify Intents to test for vulnerabilities.

### 2. Service Exploitation
Test service endpoints by sending various Intent combinations and analyzing responses.

### 3. Data Leakage Detection
Log all outgoing Intents to detect sensitive data exposure.

### 4. IPC Analysis
Understand complete communication patterns between components.

## Development

### Requirements
- Android Studio
- Android SDK (API level as per build.gradle.kts)
- Kotlin Gradle Plugin

### Building
```bash
./gradlew assembleDebug
```

### Running
1. Open project in Android Studio
2. Build and run on emulator or device
3. Check Logcat for output
4. Uncomment examples in activities to test

## Tips

### Viewing Logs
Use Android Studio's Logcat with filters:
- `IntentLogger:*` - See all Intent logs
- `ServiceActivity:*` - See service communication
- `*Logger:*` - See all logger output

### Customizing Examples
Most examples are commented out. Uncomment specific sections to test:
- Navigate to the example method
- Remove the `//` comment markers
- Replace placeholder values (like `TARGET_PACKAGE_NAME`)
- Run the app

### External Service Testing
To test communication with external services:
1. Replace `TARGET_PACKAGE_NAME` with actual package
2. Replace `TARGET_SERVICE_CLASS` with actual class
3. Uncomment the example code
4. Check logcat for security analysis

## License

This is a security testing and educational utility. Use responsibly and only on apps you have permission to test.

## Contributing

Contributions welcome! Areas for expansion:
- Additional logger types
- More service communication patterns
- AIDL examples
- Provider interaction examples
- Security analysis automation

## Author

tur11ng - Android Security Utils

---

**Note:** This is a security testing tool. Always ensure you have permission before testing applications you don't own.


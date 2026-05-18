<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />

    <uses-permission
        android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission android:name="android.permission.READ_MEDIA_VISUAL_USER_SELECTED" />

    <uses-feature
        android:name="android.hardware.camera"
        android:required="false" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.Nammavastra"
        android:usesCleartextTraffic="true"
        android:requestLegacyExternalStorage="true"
        tools:targetApi="31">

        <!-- Login Activity (LAUNCHER) -->
        <activity
            android:name=".ui.LoginActivity"
            android:exported="true"
            android:theme="@style/Theme.Nammavastra"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Signup Activity -->
        <activity
            android:name=".ui.SignupActivity"
            android:parentActivityName=".ui.LoginActivity"
            android:theme="@style/Theme.Nammavastra"
            android:windowSoftInputMode="adjustResize" />

        <!-- Main Activity -->
        <activity
            android:name=".MainActivity"
            android:theme="@style/Theme.Nammavastra"
            android:windowSoftInputMode="adjustResize"
            android:exported="false" />

        <!-- Chat Activity -->
        <activity
            android:name=".ui.ChatActivity"
            android:parentActivityName=".MainActivity"
            android:theme="@style/Theme.Nammavastra"
            android:windowSoftInputMode="adjustResize" />

        <!-- Payment Summary Activity -->
        <activity
            android:name=".ui.PaymentSummaryActivity"
            android:parentActivityName=".ui.ChatActivity"
            android:theme="@style/Theme.Nammavastra"
            android:windowSoftInputMode="adjustResize" />

        <!-- Review Activity -->
        <activity
            android:name=".ui.ReviewActivity"
            android:parentActivityName=".ui.PaymentSummaryActivity"
            android:theme="@style/Theme.Nammavastra"
            android:windowSoftInputMode="adjustResize" />

    </application>

</manifest>
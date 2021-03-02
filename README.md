<img src="images/banner.png" width=1280 height=300/>

# AppyHigh Utils Share Intent Module

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

*This module has been written in Kotlin but a seperate in java is also available for all the java devs out there.*

This module packages a custom designed share intent which can be used with all types of files to give a customized user
interface to the application.

The applications highlighted in the share intent are as follows:
 - WhatsApp Messenger
 - Facebook Messenger
 - Instagram
 - Facebook
 - Snapchat
 - Twitter

In addition to the highlighted apps, there is an option to show more apps which opens up the default share intent of the 
operating system.

For Texts, there is an option which allows the user to copy the text to the clipboard.



| Share Intent for Text                    | Share Intent for Files                     |
|:----------------------------------------:|:------------------------------------------:|
|![](images/ss_text.jpeg)                  | ![](images/ss_files.jpeg)                  |

<br />
<br />

#### Simple usage snippet
------
Start `CustomShare` from current `Activity` or `Fragment`:

### Specify the type of data. There are classes for two types of data. i.e. Texts and Files. So we create an object of the class `CustomShareDataText` for text data or `CustomShareFile` for files.

```kotlin

val dummy_text = "Lorem ipsum dolor sit amet"

// declaration of object for text data
val shareDataText = CustomShareDataText(dummy_text))

// declaration of object for files
val fileData = CustomShareFile(filePath, fileName, fileUri, mimeType)

```
<br />
<br />

### Required Variables for object `CustomShareDataText` :  
 - Text Data to be shared


<br/>

### Required parameters for object `CustomShareFile`:
 - `filePath`: the absolute path of the file in storage. This is used to show a preview of the file.
 - `fileName`: the name of the file to be shared. This is used to display the name of the file.
 - `fileUri`: the uri of the file. This is used to send data to the targeted application.
 - `mimetype`: the type of the file to be shared. This is used to set the mimeType of the share intent which then filters out the suitable receiving applications.

<br />
<br />

### Sample invocation of the function

```kotlin
CustomShare.getInstance(activity: this, fragmentManager: supportFragmentManager)
           .Builder()
           .setShareData(fileData)
           .isLocalFile(false) //true if file is a local file
           .show()
```

<br/><br/>

## Local / Internal Files
These are the files which are local to the app using this module and reside in the applications data folder. Applications on api level 30(Android Q) and above will throw and exception when the app tries to access uri of a file which is local to the app. To avoid this exception, the app must use the `FileProvider` api.
Fortunately for you, this module takes care of the underlying implementation. There are, however, some prerequisites if you intent to have this use case in your app.

1. Define the `FileProvider` in your AndroidManifest file. To do this, just add the following in your manifest file:
```java
 <provider
        android:name="androidx.core.content.FileProvider"
        android:authorities="${applicationId}.provider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
              android:name="android.support.FILE_PROVIDER_PATHS"
              android:resource="@xml/provider_paths" />
</provider>
```
2. Create an XML file that contains all paths that the `FileProvider` will share with other applications. For this, create a file named `provider_paths.xml` at `res/xml/` and paste the following code:
```kotlin
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="external_files" path="."/>
</paths>
```
3. Bundle a valid URI in the module invocation and activate it. The module takes care of this step. 

4. Now you just have to specify to the module that the file you want to share is a local file. For this just pass `true` to the this method in the `builder`: 
    ```kotlin
    isLocalFile(true)
    ```

<br/><br/>

## Exceptions

There a couple of custom exceptions which act as checks to help the developer using this module.

 - `IllegalConfigurationException` : This exception is thrown if the `Builder` has a null value for the configuration object
 - `IllegalDataParametersException` : This exceptions is thrown when the file is specified to be a local file but the file path is null.
<br /><br /><br />
## Developed with :heart: by Abhishek Tiwari 
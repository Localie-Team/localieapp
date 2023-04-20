# Localie | Team Application 
Localie is an application that aims to connect shoppers with their local commerce options. Based on your region, Localie will provide you with a range of discounts and coupons that fit your needs as a shopper. Select what you are looking for and play games to win discounts on your favorite items

# Alpha Release
To download our alpha release, you will need to have access to an Android device. From that device, download the APK linked below. You may have to change permissions to allow your phone to downlaod the application, this can be done in your phone settings. 

(ex. turning on Google Chrome's ability to download potentially unsafe files)

[Download the APK here](https://drive.google.com/file/d/1-30wzKlB1_ZbGaahRV09JDtk_f9KMIj2/view?usp=share_link)

# Technical Specification 
[App Technical Spec](https://docs.google.com/document/d/1NQJ_FT_1IZIlWWGe11Z9q_DDrA0ii_uCx5Fr1JNzLvA/edit?usp=sharing)

# Privacy Policy
[Inital Privacy Policy](https://docs.google.com/document/d/1QnmdhN79MzMTooC7hz4Dg3rbggzjaxnuzkdj2PgYMCA/edit?usp=sharing)

# EULA
[Inital End-User License Agreement](https://docs.google.com/document/d/1YzSeXEX_arKdgbGTRrWEan1WaNOGWndFMhiZbJNCZFM/edit?usp=sharing)

# UI/UX Mockup
[View Mockup Here.](https://www.figma.com/file/suvWwJGoBq4LxGrb9PxR8r/Localie?node-id=49%3A25445&t=RcqjHY04ynlo9ArP-1)

# Terminology/Glossary
[Dictionary found Here.](https://docs.google.com/document/d/1ViC7Sn1hPlN60eaz4q7Q2I_9FgK2SnmOBJN0kJ2fIY8/edit?usp=sharing)

# Technical Stack Information 
We are using **Android Studio Dolphin 2021.3.1 Patch 1** as our current version of Android Studio. Currently, everything is developed within the Android Studio engine. The backedn is connected to **Firebase Storage & Database** tools. 

# Initial Setup
  
- As listed above, download **Android Studio Dolphin 2021.3.1 Patch 1** for your current version of Android Studio
- Next, if you do not have **Kotlin** please install the latest version of **Kotlin**
- After Android Studio has downloaded, setup your project as follows:
  - Connect you GH an fetch the latest version of the project into a new branch *yourgithubid_featureyourworkingon*
  - Download the latest Android SDK's 
  - Download the latest emulator models for testing 
  - Ensure that **Kotlin** can be found and recognized within the Andriod Editor
  - Download/Import the JUnit testing module (JUnit 5 or 4)

Start Editing!

# Creating a New Activity/Fragment

- Be sure to create a new Kotlin file that corresponds to the piece, this will be where the backend code goes
- Next, create a corresponding .xml file, this can be editing both in code and using the design editor

# Pushing to GitHub & Development conventions

Create a new branch off dev when working on a new feature. Commmit often to your branch

Naming convention for commits:

feat - for a new feature implementation
fix - a fix of a bug, etc
doc - update to a piece of documentation 
chore - cleaning, refactoring

Example: **feat: new store page added**

Please create a new pull request when you are ready to merge the project into the repo! This ensure that our CI will also test your new code.
  
# Firebase

1. Go to Tools > Firebase 
2. Authentication > Google [KOTLIN]
  a. Sign in with Localie Dev account
  b. Add SDK
3. (Back in Android Studio) Firebase > Cloud Firestore
  a. Add SDK

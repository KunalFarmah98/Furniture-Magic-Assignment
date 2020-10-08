# Kunal Farmah
# Furniture Magik Assignment

## Made in Kotlin

### Uses RecyclerView to show the items.
### Uses firebase mobile authentication with a custom UI and OTP resending feature
### Uses MVVM pattern for the architecture (View, Viewmodel, Room and Room Repository). 
### Screen Orientation handled explicitly, no locked orientation, use in any orientation.
### Added scrollViews for landscape mode so that same layout file can scale to both orientations.
### Handled explicit intents for attatching an image via camera or via gallery.
### Run time permissions asked when using camera for adding photo of the item.
### Stored the data in Room to provide offline support.
### Everytime the app is launched, it goes to the login screen to sign in if not signed in and gets diverted to the list screen 3 if the user is already signed in.
### Explicit file provider added to support Android 11, (NOT USED usesLegacyExternalStorage temporary workaround, fully implmented it)
### If the database is empty, then a screen for empty database is shown and you can add item.
### Data is only stored offline for the time being, so a user can't delete the offer added by another user.
### User can only delete the offers added by him with a confimation Dialog.
### Added option to skip adding a new offer from database.
### Handled complete navigation with effective back button presses and home button in action bar.
### Explicitly handled input types for all edit texts.
<p> <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/signin1.jpeg" width =200 
  height = 350/>
 <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/signin2.jpeg" width =200 
  height = 350/>
  <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/screen2.jpeg" width =200 
  height = 350/>
  <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/exampleAdd.jpeg" width =200 
  height = 350/>
</p>
<p> <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/options.jpeg" width =200 
  height = 350/>
 <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/screen3.jpeg" width =200 
  height = 350/>
  <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/confirm_deletion.jpeg" width =200 
  height = 350/>
  <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/cant_delete.jpeg" width =200 
  height = 350/>
  <img hspace="10" src="https://github.com/KunalFarmah98/Furniture-Magic-Assignment/blob/main/app/src/main/res/raw/empty.jpeg" width =200 
  height = 350/>
</p>

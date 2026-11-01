🚫🍖 I want to be 100% transparent and let everyone know that this project is 100% vibe-coded. I'm just a man with an idea. I barely know how to code. AI did all of the heavy lifting here. 🍖🚫

This app has an HTML web version, an Android, and an Apple version. $\color{red}{\text{(Apple version needs a sideloading tool to install because the app is not signed. If unable, just use the web app)}}$ I do not own an apple device so if there's issues then I apologize, but you'll just have to use the web app.

You can install the web version of the app by:

**On iPhone or iPad (Safari)**

1. Tap the Share button (the square with an upward arrow).
2. Scroll down and tap Add to Home Screen (if you do not see it, scroll to the bottom, tap Edit Actions, and add it).
3. Rename the app if desired, turn on Open as Web App, and tap Add.

**On Android (Google Chrome, Firefox, Brave)**

1. Tap the More menu (three dots) on the right side of the address bar.
2. Select Install and create shortcut or Add to Home screen, then tap Install.

$\color{red}{\text{Otherwise if you want a standalone app instead of the web version, you can install}}$

$\color{red}{\text{the android or apple version of the app from the available releases.}}$

---

# MTG Randomizer

A random **Magic: The Gathering** card roller for your own custom pools. Build a query out of color identity, card types, rarities, keywords and more, watch the Scryfall query build in plain English, and **Roll** a card. Your recent rolls stay in a thumbnail history you can re-open, and a roll can be shared.

It runs as a single static web page (`index.html`, no build step, no backend) and also ships as Android and Apple wrapper apps that load the same bundled page.

> **Disclaimer:** Card data comes from [Scryfall](https://scryfall.com) (CC-BY-4.0). MTG Randomizer is an unofficial fan tool and is not affiliated with, or endorsed by, Wizards of the Coast.

---

## How it works

- Every filter is one piece of a Scryfall query, and the **query preview** under the Roll button shows the exact query that will be used, so you can fix anything before rolling.
- Rolls respect Scryfall's one-requests-per-second rate limit; if you roll too fast the app waits and tries again instead of failing.
- The result card shows the card name, colors, type line, rarity and artwork, with the full card text and a link straight to Scryfall for the official page.
- Up to 24 recent rolls are kept in the thumbnail history (stored on your device only) — tap one to bring it back, or clear the whole list.
- Dark mode is available from the header and remembered between visits.

## Where things are stored

Everything (your history, your theme choice) lives in `localStorage` on that device only. Clearing site data or using a private tab deletes it. No accounts, no sync between devices.

## Privacy and data sources

- Card data comes from [Scryfall's public API](https://scryfall.com/docs/api). The only information sent over the network is the card query you build.
- Card artwork is displayed as provided by Scryfall.
- No analytics, tracking, accounts, or advertising.

---

## Project structure

```
index.html                    The entire web app (HTML, CSS, JavaScript)
manifest.webmanifest          Lets "Add to Home screen" install it as an app
apple-touch-icon.png          Home screen icon on iPhone and iPad
icon-512.png                  App icon for the manifest
.github/workflows/            build-android.yml and build-ios.yml (cloud builds)
MTGRandomizeriOS/
  project.yml                 XcodeGen project definition
  MTGRandomizer/              Swift source, bundled page, and app icon
MTGRandomizerAndroid/
  app/src/main/
    AndroidManifest.xml
    assets/index.html         Copy of the web app
    java/com/pixeledberry/mtgrandomizer/MainActivity.kt
    res/                      App icon resources (five-dot mana mark)
  store-icon-512.png          512 px icon for store listings
  build.gradle.kts, settings.gradle.kts, gradle/wrapper/
```

## Known limitations

- Cards are random within your filter; the same card can come up twice in a row.
- Your history holds the most recent 24 rolls and is stored on that device only; it does not sync between the web app and the installed apps.
- The Apple version is not code-signed, so it needs a sideloading tool such as AltStore or Sideloadly. If you can't use one, use the web app.
- The Android project has not been published to the Play Store; it builds as a debug APK.

## Credits

Card data and artwork: Scryfall (CC-BY-4.0). Magic: The Gathering is a trademark of Wizards of the Coast.

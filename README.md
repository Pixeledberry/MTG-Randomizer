🎲🃏 This project is coded in tandem with Claude. 🃏🎲

This app has an HTML web version, an Android, and an Apple version. $\color{red}{\text{(Apple version needs a sideloading tool to install because the app is not signed. If unable, just use the web app)}}$ I do not own an apple device so if there's issues then I apologize, but you'll just have to use the web app.

Here's the public HTML for the app. https://pixeledberry.github.io/MTG-Randomizer/ Either just leave the page open in your browser or you can install the web version of the app by:

Go to the app webpage then:

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

A random card picker for **Magic: The Gathering**. Choose what to search by (a type like Zombie or Angel, a function like removal or ramp, an art tag like dragon or moon, and more), tap the **dice**, and a random card from that pool fills the screen. Turn the randomizer off and it becomes a normal card search. Build decks from the cards you find.

It runs as a single static web page (`index.html` plus the tag list `tags.json`, no build step, no backend) and also ships as Android and Apple apps that load the same page.

> **Disclaimer:** Card data and images come from [Scryfall](https://scryfall.com); function and art tags come from [Scryfall Tagger](https://tagger.scryfall.com). MTG Randomizer is unofficial Fan Content permitted under the Fan Content Policy. Not approved or endorsed by Wizards. Portions of the materials used are property of Wizards of the Coast. ©Wizards of the Coast LLC.

---

## Changelog

### v1.2.0 (latest)
- **Colors row.** Tap the mana symbols at the top of the Randomize tab (white, blue, black, red, green, colorless) and choose **Including**, **Exactly**, **At most** or **Commander** (fits a Commander deck of those colors), like Scryfall's Advanced Search.
- **Every Scryfall Advanced Search field.** New Search by choices: Power, Toughness, Loyalty, Price (dollars, euros or MTGO tickets), Block, Criteria (all of Scryfall's "is:" options plus land cycles like Fetchland and Shockland), Mana cost, Flavor text, Lore finder, Watermark, Year printed, Language and Game (Paper, Arena, MTGO). Formats can now be **Banned in** or **Restricted in**, and Rarity has ranges like **Rare or higher**.
- **Type numbers your way.** Number choices take `3`, `5+`, `2-4` or `<=2`; prices take `<5`, `20+` or `eur<10`.
- **Easier to find things.** The Search by list is now grouped, and **Clear all** removes every filter at once.
- **More sort orders** in search mode: power, toughness, euro and MTGO prices, and set review order.
- Cards rolled in another language show their printed name next to the English one.
- The old "Color" choice in Search by is replaced by the Colors row, so color filters added in v1.1.0 are removed once.

### v1.1.0
- **Rebuilt to look and work like TickedOff.** Same layout, menu and themes (Auto, Light, Default, Dark, five high contrast, five low contrast and six seasonal, plus your own colors), update notices and in-app changelog.
- **Search by Type, Function, Art and more.** Choose what to search by (Type, Function, Art, Keyword, Color, Mana value, Rarity, Set, Format, Artist, Card name, Rules text or your own Scryfall search), then type or pick a value. Suggestions appear as you type, and **▾ All** lists every value you can pick.
- **Scryfall Tagger built in.** Every function tag (otag, for example `removal` or `ramp`) and art tag (for example `dragon` or `moon`), with how many cards each one covers.
- **Stack filters.** **+ Add another filter** combines them, for example Zombie + removal. Tap a filter to switch it to "must not have".
- **The dice.** Tap it to roll a random card from your pool. The card opens full screen: tap the dice (or swipe left) to roll again, and swipe right to look back at earlier rolls.
- **Randomizer switch.** Menu → Settings → Randomizer. Turn it off and the app becomes a normal card search that shows every matching card.
- **Decks.** Create decks, add cards from the full-screen view, change counts, draw a sample hand, add random cards from your pool, and import or export deck lists.
- **History** of your last 100 rolls, and **Export / Import Settings** for backups.
- **Android updates now install over the old version** and keep your decks. If you installed v1.0.0, uninstall it once before installing v1.1.0.

### v1.0.0
- First release: roll a random card filtered by color, rarity, mana value and set.

## Features

Works in modern browsers on iPhone and iPad (Safari), Android, and desktop.

### Building a pool
- **Colors** (top of the Randomize tab): tap the white, blue, black, red, green or colorless symbols, then choose how to match them, like the color checkboxes in Scryfall's Advanced Search:

| Choice | Means | Scryfall search it makes (red + green) |
|---|---|---|
| Including | Red and green, maybe other colors too | `c>=rg` |
| Exactly | Only red and green | `c=rg` |
| At most | Nothing outside red and green (colorless counts) | `c<=rg` |
| Commander | Fits in a red-green Commander deck | `id<=rg` |

- **Search by** picks what kind of filter you're setting. It covers every field in Scryfall's Advanced Search, plus Scryfall Tagger tags and keywords:

| Search by | Example | Scryfall search it makes |
|---|---|---|
| **Most used** | | |
| Type | Zombie, Instant, Legendary | `t:"zombie"` |
| Function (oracle tag) | removal, ramp, tutor | `otag:removal` |
| Art (art tag) | dragon, moon, cat | `atag:dragon` |
| Keyword | Flying, Landfall | `keyword:"flying"` |
| Card name | bolt | `"bolt"` |
| Rules text | draw a card | `o:"draw a card"` |
| **Numbers** | | |
| Mana value | 3, 7+, 2-4, <=2 | `mv=3`, `mv>=7`, `mv>=2 mv<=4`, `mv<=2` |
| Power / Toughness | 5+, 0, * | `pow>=5`, `tou=0`, `pow=*` |
| Loyalty | 4 | `loy=4` |
| Price | Under $5, 20+, eur<10, tix>=1 | `usd<5`, `usd>=20`, `eur<10`, `tix>=1` |
| **Cards and printings** | | |
| Rarity | Mythic rare, Rare or higher | `r:mythic`, `r>=rare` |
| Set | Dominaria (DOM) | `set:dom` |
| Block or group | Khans of Tarkir | `b:ktk` |
| Criteria (is: / is not:) | Full Art, Reprint, Commander, Fetchland | `is:fullart` |
| Artist | Rebecca Guay | `a:"rebecca guay"` |
| Mana cost | {2}{R}{R} or 2RR | `m:{2}{R}{R}` |
| Flavor text | dragon | `ft:"dragon"` |
| Lore finder | Urza | `lore:"urza"` |
| Watermark | orzhov | `wm:orzhov` |
| Year printed | 2020, 2015+, <2003 | `year=2020` |
| Language | Japanese | `lang:ja` |
| **Where you can play** | | |
| Format | Commander, Banned in Modern, Restricted in Vintage | `f:commander`, `banned:modern`, `restricted:vintage` |
| Game | Paper, MTG Arena, Magic Online | `game:arena` |
| **Advanced** | | |
| Scryfall search | `t:dragon pow>=5` | used as typed |

- **Criteria** has every "is:" option from Scryfall's Advanced Search (Popular ones first), plus land cycles: dual, fetch, shock, check, pain, fast, slow, bounce, gain, filter, scry, pathway, bike, canopy, tri, tricycle, shadow, storage, tango and snarl lands.
- **Language** rolls cards printed in that language and shows the foreign card picture, with the printed name next to the English one.

- **Suggestions as you type.** Popular choices show before you type; matches show while you type. Function and art tags show how many cards they cover. Typing several words finds tags that contain all of them ("card draw" finds `draw-card`).
- **▾ All** opens the full list for the current choice (every type, every tag, every set and so on) with a filter box. Tags can be sorted by **Most cards** or **A–Z**. Anything you type that isn't in the list can still be used.
- **+ Add another filter** keeps the current choice as a filter chip and clears the box for the next one. Tap a chip to switch it between **must have** and **must not have** (Criteria chips read **Is** / **Is not**); tap **×** to remove it. **Clear all** removes every filter, the colors and the text in the box.
- Each **Search by** choice remembers what you last typed in it.
- The exact Scryfall search is shown under the dice, with a link to open it on Scryfall.
- **More options:** roll from **each card once** (default), **every artwork**, or **every printing**, and optionally include tokens, emblems and other extras.

### Rolling
- **The dice** rolls one random card from your pool and shows it full screen. With no filters, it can land on any card.
- In the full-screen view: **Flip** (double-faced cards), **Art only** / **Full card**, **+ Deck**, **Scryfall ↗**, and the dice to roll again. Swipe left to roll again, swipe right (or ‹) to look back at earlier rolls. The phone's back button or the × closes it.
- The app tries not to repeat a card you rolled recently from the same pool (small pools will still repeat).

### Search mode (randomizer off)
- **Menu → Settings → Randomizer** switches the dice for a **Search** button that shows every matching card in a grid, 175 at a time with **Show more**, sortable by name, release date, set and number, rarity, color, mana value, power, toughness, popularity (EDHREC), price in dollars, euros or MTGO tickets, artist, or set review order.
- Tap any card to see it full screen and swipe through the results.

### Decks
- **+ New deck**, then add cards from any full-screen card with **+ Deck**. The last deck you used is listed first.
- Each deck shows its cards grouped by type with mana costs, card count, lands, average mana value and colors. Use **−** and **+** to change counts; tap a card to see it full screen.
- **🎲 Sample hand** shuffles the deck and draws seven, with **Draw a card** and **New hand**.
- **🎲 Add random cards** rolls 1, 5, 10 or 20 cards from your current pool straight into the deck.
- **Export** gives a plain list (`4 Lightning Bolt`) for MTG Arena, Moxfield, Archidekt and other deck sites, to share, save or copy. **Import** takes the same kind of list, including set codes (`1 Sol Ring (C21) 263`), and tells you about any card it can't find.

### History, menu, themes and backups
- **History** keeps your last 100 rolls with what pool they came from. Tap one to see it again, remove one with ×, or clear them all.
- The hamburger button at the top right opens the menu: the **Randomizer** switch, the same themes as TickedOff (Auto, Light, Default, Dark, five high contrast, five low contrast pastel, six seasonal) or your own background and accent colors.
- **Export / Import Settings** backs up your decks, history, pool, settings and theme as a small text file, and restores them on another device. Importing adds the backup's decks to the ones you already have.

### Updates
- **Web version:** when a newer version is published, a banner offers a **Reload**.
- **Android and Apple apps:** they check GitHub for a newer release (at most every 12 hours, or any time from **Menu → Check for updates**). On Android, **Update** downloads the new version and opens the installer for you. On Apple, the notice links to the release so you can install it with your sideloading tool.
- **Not now** hides the notice for 3 days. **Don't remind me** turns reminders off; **Menu → Check for updates** still works and lets you turn them back on.
- **Menu → Show Changelog** shows what changed in recent updates.

## How decks and settings are stored

Everything personal is stored **in your own browser or app** using `localStorage`. There is no server, no account, and no cookies. Clearing site data or uninstalling the app deletes it, so use Export Settings to keep a backup. The web app and the installed apps each have their own storage.

| Key | Contents |
|---|---|
| `mtgr_decks` | Your decks and the cards in them |
| `mtgr_hist` | Your last 100 rolls |
| `mtgr_pool` | The filters on the Randomize tab |
| `mtgr_cfg` | Randomizer switch, printing options, sort order, last deck used |
| `mtgr_theme` | Your theme |
| `mtgr_cat_*` | Lists from Scryfall (types, keywords, sets, artists), refreshed every two weeks |
| `mtgr_upd` | Update check timing and reminders |

## Privacy and data sources

- Card data and images come from [Scryfall's public API](https://scryfall.com/docs/api). The only thing sent is the card search you build (and deck lists you import, to look the cards up).
- Function and art tags come from Scryfall Tagger and are bundled in `tags.json`, so suggestions work instantly.
- The apps check GitHub for new releases. No analytics, tracking, accounts or advertising.

---

## Project structure

```
index.html                    The entire web app (HTML, CSS, JavaScript). The main copy.
tags.json                     Scryfall Tagger function and art tags with card counts
manifest.webmanifest          Lets "Add to Home screen" install it as an app
apple-touch-icon.png          Home screen icon on iPhone and iPad
icon-192.png, icon-512.png    App icons for the manifest
.github/workflows/            build-android.yml, build-ios.yml (app builds and releases),
                              deploy-pages.yml (publishes the web app to GitHub Pages)
MTGRandomizerAndroid/
  app/build.gradle.kts        Version number and the fixed signing key settings
  app/mtgrandomizer.jks       Signing key, so updates install over the old version
  app/src/main/
    AndroidManifest.xml
    assets/                   Copies of index.html and tags.json
    java/com/pixeledberry/mtgrandomizer/MainActivity.kt
    res/                      App icon (card and dice) and update file settings
  store-icon-512.png          512 px icon for store listings
  build.gradle.kts, settings.gradle.kts, gradle/wrapper/
MTGRandomizeriOS/
  project.yml                 XcodeGen project definition and version number
  MTGRandomizer/              Swift source, Resources/ (copies of index.html and tags.json), app icon
```

The app builds on GitHub copy `index.html` and `tags.json` from the main folder into the apps before building, so all three versions always use the same page.

## Known limitations

- Rolls and searches need an internet connection (cards come live from Scryfall).
- The tag lists are a snapshot taken when the app was built. Brand-new tags can still be typed in by hand.
- Decks and history are stored on each device separately; use Export / Import Settings to move them.
- The Apple version is not code-signed, so it needs a sideloading tool such as AltStore or Sideloadly. If you can't use one, use the web app.
- The Android app is not on the Play Store; it installs from the .apk in the releases. v1.0.0 was signed differently, so it has to be uninstalled once before installing v1.1.0 or newer.

## Credits

Card data, images and Tagger tags: [Scryfall](https://scryfall.com). Mana symbols: Scryfall's symbol images. Magic: The Gathering is a trademark of Wizards of the Coast.
